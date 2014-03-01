#include "globals.h"
#include <avr/io.h>
#include <avr/interrupt.h>
#include <util/delay.h>

u08 servo_state=0;
u08 high=0;
u16 servo_position[4];
u16 servo_low_time[4];
u08 servo_output;

void set_position(u08 servo_num, u08 position) {
}

void init_servo(void) {
}

void write_servo_output(void) {
}


ISR(TIMER1_COMPA_vect) {
}
