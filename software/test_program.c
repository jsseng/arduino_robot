//test program
#include<avr/io.h> //include files for the hardware
#include<avr/interrupt.h>

int main(void) {
  int i;
  int reading1;
  int reading2;
  int address;
  int test_array[100];

  for(i=0;i<100;i++) {
    motor(0,i);  //spin the left motor forward
    motor(1,i);  //spin the right motor forward
  }

  i=0;
  while(i>-100) {
    motor(0,i);  //spin the left motor backwards
    motor(1,i);  //spin the right motor backwards
    i--;
  }

  i=50;
  set_servo(0,i);  //set servo motor 0 to move to 50 degrees
  set_servo(3,i);  //set servo motor 3 to move to 50 degrees

  delay_milliseconds(100);  //pause 100 milliseconds
  delay_seconds(1);  //pause 1 second

  lcd_clear();
  lcd_cursor(0,0);
  printf ("Test1\n"); //the LCD will be 8x2 (8chars x 2lines)
  printf ("Test2\n");

  reading1 = analog(0);  //get a reading from analog pin 0
  reading2 = analog(5);  //get a reading from analog pin 5
  reading1 = digital(0);  //get a reading from digital pin 0
  reading2 = digital(1);  //get a reading from digital pin 1

  if (reading1 > 100) {
    printf ("%d\n", reading1);
  }

  reading1 = accelerometer(0);  //read x-axis
  reading2 = accelerometer(1);  //read y-axis
  reading1 = accelerometer(2);  //read z-axis

  reading1 = battery_voltage();  //battery voltage

  reading1 = read_serial_port();  //get a byte from the serial port
  write_serial_port(reading1);  //send a byte on the serial port

  led1(1);  //turn on on-board led1
  led1(0);  //turn off on-board led1

  reading1 = read_ir();  //get a reading from the IR receiver

  reset();  //reset the board

  write_eeprom(address, reading1);  //write a value to the non-volatile eeprom (these values will be stored across resets)
  reading1 = read_eeprom(address);  //get a reading from the non-volatile eeprom

  reading1 = button(); //read the state of the on-board button

  return 0;
}
