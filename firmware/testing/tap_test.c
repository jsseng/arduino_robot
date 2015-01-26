#include "globals.h"
#include <util/delay.h>
#include <avr/io.h>
#include "USI_TWI_Master.h"
#include <avr/interrupt.h>

void i2c_regwrite(u08 address, u08 data);
u08 i2c_regread(u08 address);

int main(void) {
   u16 counter = 0;
   init();
   clear_screen();
   

   //step 1
   i2c_regwrite(0x2a,0x08); //400Hz, Standby mode
   
   //step 2
   //i2c_regwrite(0x21,0x15);
   i2c_regwrite(0x21,0x55);

   //step 3
   i2c_regwrite(0x23,0x19);
   i2c_regwrite(0x24,0x19);
   i2c_regwrite(0x25,0x2a);

   //step 4
   i2c_regwrite(0x26,0x50);

   //step 5
   i2c_regwrite(0x27,0xf0);

   //step 6
   i2c_regwrite(0x2c, 0x01);  //make interrupt pin open drain
   i2c_regwrite(0x2d, 0x08);
   i2c_regwrite(0x2e, 0x08);

   //step 7
   u08 CTRL_REG1_Data = i2c_regread(0x2a);
   CTRL_REG1_Data |= 1;
   i2c_regwrite(0x2a, CTRL_REG1_Data);

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
         i2c_regread(0x22); //read status register
         lcd_cursor(0,1);
         print_num(counter);
         lcd_cursor(0,0);
         print_string("tap");
         //_delay_ms(400);
      }
      //print_num(i2c_regread(0x1));
      //_delay_ms(300);
      //clear_screen();
   }





   //do not type after this line

   return 0;
}
