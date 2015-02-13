#include "globals.h"
#include <util/delay.h>
#include <avr/io.h>
#include "USI_TWI_Master.h"
#include <avr/interrupt.h>

// FXOS8700CQ internal register addresses
#define FXOS8700CQ_STATUS 0x00
#define FXOS8700CQ_WHOAMI 0x00
#define FXOS8700CQ_XYZ_DATA_CFG 0x0E
#define FXOS8700CQ_CTRL_REG1 0x2A
#define FXOS8700CQ_M_CTRL_REG1 0x5B
#define FXOS8700CQ_M_CTRL_REG2 0x5C
#define FXOS8700CQ_WHOAMI_VAL 0xC7

void i2c_regwrite(u08 dev_addr, u08 address, u08 data);
u08 i2c_regread(u08 dev_addr, u08 address);

void config_compass() {
   //write 0 to ctrl_reg_1
   i2c_regwrite(COMPASS_ADDR, FXOS8700CQ_CTRL_REG1, 0x00);

   //write 0x1F to magnetometer control register 1
   i2c_regwrite(COMPASS_ADDR, FXOS8700CQ_M_CTRL_REG1, 0x1F);

   //write 0x20 to magnetometer control register 2
   i2c_regwrite(COMPASS_ADDR, FXOS8700CQ_M_CTRL_REG2, 0x20);

   //write 0x1 to XYZ data config register
   i2c_regwrite(COMPASS_ADDR, FXOS8700CQ_XYZ_DATA_CFG, 0x01);

   //write 0x0D to accelerometer control register 1
   i2c_regwrite(COMPASS_ADDR, FXOS8700CQ_CTRL_REG1, 0x0D);
}

u16 x_compass_read() {
   u16 x;
   x = i2c_regread(COMPASS_ADDR, 0x33);
   x = x << 8;
   x += i2c_regread(COMPASS_ADDR, 0x34);
   return x;
}

int main(void) {
   u16 counter = 0;
   init();
   clear_screen();
   config_compass();
   
   while(1) {
   led_on(1);
   _delay_ms(50);
   clear_screen();
   //step 1
   print_num(x_compass_read());
   
   led_off(1);

}

   //step 1
   i2c_regwrite(MMA8453_ADDR,0x2a,0x08); //400Hz, Standby mode
   
   led_on(1);
   _delay_ms(1000);
   led_off(1);

   //step 2
   //i2c_regwrite(0x21,0x15);
   i2c_regwrite(MMA8453_ADDR,0x21,0x55);

   //step 3
   i2c_regwrite(MMA8453_ADDR,0x23,0x19);
   i2c_regwrite(MMA8453_ADDR,0x24,0x19);
   i2c_regwrite(MMA8453_ADDR,0x25,0x2a);

   //step 4
   i2c_regwrite(MMA8453_ADDR,0x26,0x50);

   //step 5
   i2c_regwrite(MMA8453_ADDR,0x27,0xf0);

   //step 6
   i2c_regwrite(MMA8453_ADDR,0x2c, 0x01);  //make interrupt pin open drain
   i2c_regwrite(MMA8453_ADDR,0x2d, 0x08);
   i2c_regwrite(MMA8453_ADDR,0x2e, 0x08);

   //step 7
   u08 CTRL_REG1_Data = i2c_regread(MMA8453_ADDR,0x2a);
   CTRL_REG1_Data |= 1;
   i2c_regwrite(MMA8453_ADDR,0x2a, CTRL_REG1_Data);

   //enable pullup on PD1
   //do this after configuring the MMA8453
   PORTD |= _BV(ACCEL_INT_PIN);

   lcd_cursor(0,1);
   print_num(counter);

   while(1) {
      //u08 reading;

      //reading = i2c_regread(0x0c);
      if ((PIND & _BV(ACCEL_INT_PIN)) == 0) {
      //if ((reading & 0x08) != 0) {
         clear_screen();
         counter++;
         i2c_regread(MMA8453_ADDR,0x22); //read status register
         lcd_cursor(0,1);
         print_num(counter);
         lcd_cursor(0,0);
         print_string("tap");

	 led_on(1);
         _delay_ms(400);
	 led_off(1);
      }
      //print_num(i2c_regread(0x1));
      //_delay_ms(300);
      //clear_screen();
   }





   //do not type after this line

   return 0;
}
