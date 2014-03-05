#include "globals.h"
#include <util/delay.h>
#include <avr/io.h>
#include "USI_TWI_Master.h"


int main(void) {
   u16 i;
   int j=0;

   init();
   USI_TWI_Master_Initialize();

   while(get_sw1() == 0) {}

   DDRD |= _BV(IR_PIN);

   led_on(1);
   led_on(0);

   clear_screen();
   print_string("test",4);

   while(1) {
      _delay_ms(1500);

      if (j) {
         clear_screen();
         print_string("test",4);
         led_on(1);
         led_off(0);
         //sbi(PORTC,LCD_E_PIN);
         //sbi(PORTC,LCD_RS_PIN);
         //PORTA = 0xff;

         sbi(PORTD,IR_PIN); //turn on IR pin

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
         print_string("program",7);
         j=1;
         //cbi(PORTC,LCD_E_PIN);
         //cbi(PORTC,LCD_RS_PIN);
         //PORTA = 0;

         cbi(PORTD,IR_PIN); //turn off IR pin

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
