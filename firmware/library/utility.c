#include "globals.h"
#include <avr/io.h>
#include <avr/interrupt.h>
#include <util/delay.h>

u08 get_sw1(void) {
   return 0;
}

u08 digital(u08 num) {
   return 0;
}

void init(void) {
  init_adc();
  init_lcd();
  init_servo();
  init_motor();
}
