#include "globals.h"
#include <avr/io.h>
#include <avr/interrupt.h>
#include <util/delay.h>

//at 16MHz, 5000 cycles is 2.5ms with a prescalar of /8
#define MAX_PERIOD 5000 

u08 servo_state=0;
u08 high=0;
u16 servo_position[4];
u16 servo_low_time[4];
u08 servo_output;

void set_position(u08 servo_num, u08 position) {
  //This function set the position of the servos.  The first 
  //parameter (0 or 1) selects the servo.  The second 
  //parameter (0-255) sets the servo position

  u16 temp;

  //position runs from 0 to 255
  if (position > 250) {
    position = 250;
  }

  temp = 8 * position;
  servo_position[servo_num] = 2000 + temp;
  servo_low_time[servo_num] = MAX_PERIOD - servo_position[servo_num];
}

void init_servo(void) {
}

void write_servo_output(void) {
  cli();  //disable interrupts
  sbi(PORTD,4);  //disable digital inputs
  DDRC = 0xff;

  PORTC = servo_output;
  _delay_loop_1(100);
  sbi(PORTD,6);  //clock in the servo output
  _delay_loop_1(100);
  cbi(PORTD,6);  //clock in the servo output
  _delay_loop_1(100);
  //DDRC = 0x00;
  sei();  //enable interrupts
}


ISR(TIMER1_COMPA_vect) {
  //This is the interrupt routine to control 8 servos
  u16 last_time;
  u16 temp_time;

  last_time = OCR1AH;
  last_time = last_time << 8;
  last_time += OCR1AL;

  if (high==1) {
    servo_output = 0;
    temp_time = servo_low_time[servo_state];
    high=0;
    write_servo_output();
  } else {
    servo_state++;
    servo_state &= 4; //mask to get only lower 3 bits

    servo_output = 0;
    servo_output |= _BV(servo_state);
    temp_time = servo_position[servo_state];
    high=1;
    write_servo_output();
  }

  last_time += temp_time;
  OCR1AH = last_time >> 8;
  OCR1AL = last_time & 0xFF;
}
