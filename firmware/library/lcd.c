#include "globals.h"
#include <avr/io.h>
#include <avr/interrupt.h>
#include <util/delay.h>

void e_Clk(void) {
  _delay_us(1);
  sbi(PORTC,LCD_E_PIN);
  _delay_us(1);
  cbi(PORTC,LCD_E_PIN);
  _delay_us(1);
}

void write_lcd(u08 data) {
  cli();
  LCD_DATA_PORT = data;
  e_Clk();
  sei();
}

void write_control(u08 data) {
  cbi(PORTC,LCD_RS_PIN); //set RS low
  write_lcd(data);
}

void write_data(u08 data) {
  sbi(PORTC,LCD_RS_PIN); //set RS high
  write_lcd(data);
}

void init_lcd(void) {
  write_control(0x38);  //function set
  _delay_ms(5);

  write_control(0x38);  //function set
  _delay_us(160);

  write_control(0x38);  //function set
  _delay_us(160);
  write_control(0x38);  //function set
  _delay_us(160);
  write_control(0x08);  //turn display off
  _delay_us(160);
  write_control(0x01);  //clear display
  _delay_us(4000);
  write_control(0x06);  //set entry mode
  _delay_us(160);
}

void print_string(char* string, u08 num_bytes) {
  u08 i;

  for (i=0;i<num_bytes;i++) {
    write_data(string[i]);
    _delay_us(160);
  }
}

void print_num(u16 number) {
  u08 test[5];
  u08 size;

  if (number>9999) {
    size = 5;
  } else if (number>999) {
    size = 4;
  } else if (number>99) {
    size = 3;
  } else if (number>9) {
    size = 2;
  } else {
    size = 1;
  }
  //print_string(*(utoa(number,test,10)),size);
}

void clear_screen(void) {
  write_control(0x01);  //clear display
  _delay_us(4000);
}
