#include "globals.h"
#include <util/delay.h>
#include <avr/io.h>
#include "USI_TWI_Master.h"
#include <avr/interrupt.h>

u16 count;

int main(void) {
   u16 i;
   u08 data[2];
   int j=0;

   init();
   test_motor();
   clear_screen();

/*   while(1){
      set_position(0,0);
      set_position(1,0);
      set_position(2,0);
      set_position(3,0);
      _delay_ms(1000);

      set_position(0,255);
      set_position(1,255);
      set_position(2,255);
      set_position(3,255);
      _delay_ms(1000);
   }
   */



   _delay_ms(100);

   for (i=0;i<1;i++) {
      led_on(1);
      _delay_ms(100);
      led_off(1);
      _delay_ms(100);
   }

   data[0] = 0x1; //change to WAKE mode
   send_address(0x2A,0);
   write_register(&data[0], 1);
   _delay_ms(100);
   unlock_bus();
   //send_address(0x2);
   //read_register(&data[1], 1);
   print_num(data[0]);
   //while(1) {}
   while(1) {
      //X
      send_address(0x1,1);
      read_register(&(data[0]), 1);
      print_num(data[0]);
      print_string(" ");

      //Y
      send_address(0x3,1);
      read_register(&(data[0]), 1);
      print_num(data[0]);
      print_string(" ");

      //Z
      lcd_cursor(0,1);
      send_address(0x5,1);
      read_register(&(data[0]), 1);
      print_num(data[0]);
      print_string(" ");
      print_num(count);
      count++;
      _delay_ms(50);
      clear_screen();
      OCR2A = 28;

   }

   while(get_sw() == 0) {}

   led_on(1);
   led_on(0);

   clear_screen();

   while(1) {
      _delay_ms(1500);

      if (j) {
         clear_screen();
         print_string("test");
         led_on(1);
         led_off(0);
         //sbi(PORTC,LCD_E_PIN);
         //sbi(PORTC,LCD_RS_PIN);
         //PORTA = 0xff;

         sbi(PORTC,SERVO0_PIN); //set servo pins
         sbi(PORTC,SERVO1_PIN);
         sbi(PORTC,SERVO2_PIN);
         sbi(PORTF,SERVO3_PIN);

         //test i2c pins
         //sbi(PORTE,4);
         //sbi(PORTE,5);
         
         //motor test
         sbi(PORTB,MOTOR0_EN_PIN);
         sbi(PORTB,MOTOR1_EN_PIN);
         sbi(PORTD,MOTOR0_DIR0_PIN); 
         sbi(PORTD,MOTOR0_DIR1_PIN);
         sbi(PORTD,MOTOR1_DIR0_PIN);
         sbi(PORTD,MOTOR1_DIR1_PIN);
         j=0;
      } else {
         led_off(1);
         led_on(0);
         clear_screen();
         print_string("program");
         j=1;
         //cbi(PORTC,LCD_E_PIN);
         //cbi(PORTC,LCD_RS_PIN);
         //PORTA = 0;

         cbi(PORTC,SERVO0_PIN); //clear servo pins
         cbi(PORTC,SERVO1_PIN);
         cbi(PORTC,SERVO2_PIN);
         cbi(PORTF,SERVO3_PIN);
         
         //test i2c pins
         //cbi(PORTE,4);
         //cbi(PORTE,5);
         
         //motor test
         cbi(PORTB,MOTOR0_EN_PIN);
         cbi(PORTB,MOTOR1_EN_PIN);
         cbi(PORTD,MOTOR0_DIR0_PIN); 
         cbi(PORTD,MOTOR0_DIR1_PIN);
         cbi(PORTD,MOTOR1_DIR0_PIN);
         cbi(PORTD,MOTOR1_DIR1_PIN);
      }

   }

   return 0;
}
