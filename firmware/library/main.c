#include "globals.h"
#include <util/delay.h>
#include <avr/io.h>

int main(void) {
   u16 i;
   int j=0;

   init();

   //while(get_sw1() == 0) {}

   led_on(1);
   led_on(0);

   while(1) {
      for(i=0;i<1;i++) 
         _delay_ms(1000);

      if (j) {
         led_on(1);
         led_on(0);
         sbi(PORTC,LCD_E_PIN);
         sbi(PORTC,LCD_RS_PIN);
         PORTA = 0xff;
         j=0;
      } else {
         led_off(1);
         led_off(0);
         j=1;
         cbi(PORTC,LCD_E_PIN);
         cbi(PORTC,LCD_RS_PIN);
         PORTA = 0;
      }

   }

   return 0;
}
