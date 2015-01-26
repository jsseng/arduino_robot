#include "globals.h"
#include <util/delay.h>
#include <avr/io.h>
#include "USI_TWI_Master.h"
#include <avr/interrupt.h>

extern u16 count;

int main(void) {
   u16 i;
   u08 data[2];
   int j=0;

   init();
   clear_screen();

   _delay_ms(100);

   for (i=0;i<1;i++) {
      led_on(1);
      _delay_ms(100);
      led_off(1);
      _delay_ms(100);
   }

   while(1) {

      //flash LED1 if IR detected
      //if (PIND & _BV(IR_PIN)) {
      if (PINE & _BV(2)) {
         led_on(1);
      } else {
         led_off(1);
         _delay_ms(20);
      }

      _delay_us(100);
   }

   return 0;
}
