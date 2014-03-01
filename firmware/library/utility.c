#include "globals.h"
#include <avr/io.h>
#include <avr/interrupt.h>
#include <util/delay.h>

u08 get_sw1(void) {
   if (PORTE & _BV(SW1_PIN))
      return 0;
   return 1;
}

u08 digital(u08 num) {
   switch(num) {
   case 0:
   case 1:
      if (PORTE & _BV(num))
         return 1;
      break;
   case 2:
   case 3:
      if (PORTG & _BV(num+1))
         return 1;
      break;
   case 4:
      if (PORTE & _BV(6))
            return 1;
      break;
   case 5:
   case 6:
      if (PORTB & _BV(num))
         return 1;
      break;
   case 7:
   case 8:
   case 9:
      if (PORTC & _BV(num-2))
         return 1;
      break;
   case 10:
      if (PORTB & _BV(0))
         return 1;
      break;
   case 11:
   case 12:
      if (PORTB & _BV(num-9))
         return 1;
      break;
   case 13:
      if (PORTB & _BV(1))
         return 1;
      break;
   }

   return 0;
}

void led_on(u08 num) {
   if (num == 0) {
      sbi(PORTG,LED0_PIN);
   } else {
      sbi(PORTG,LED1_PIN);
   }
}

void led_off(u08 num) {
   if (num == 0) {
      cbi(PORTG,LED0_PIN);
   } else {
      cbi(PORTG,LED1_PIN);
   }
}

void init(void) {
   //make LED0 and LED1 outputs
   DDRG |= _BV(LED1_PIN) | _BV(LED0_PIN);

   //enable SW1 pull
   PORTE |= _BV(SW1_PIN);

   init_adc();
   init_lcd();
   init_servo();
   init_motor();
}
