#include "globals.h"
#include <avr/io.h>
#include <avr/interrupt.h>
#include <util/delay.h>

void set_motor(u08 num, signed char direction) {
}

void init_motor(void) {
  set_motor(0,0);
  set_motor(1,0);
}

