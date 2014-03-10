//Global defines

#ifndef GLOBALS_H
#define GLOBALS_H

typedef unsigned char u08;
typedef unsigned int u16;

#define sbi(a, b) (a) |= _BV(b)
#define cbi(a, b) (a) &= ~_BV(b)

#define LCD_E_PIN 0  //PC0
#define LCD_RS_PIN 1 //PC1
#define LCD_DATA_PORT PORTA

#define SW1_PIN 7  //PE7
#define LED0_PIN 0 //PG0
#define LED1_PIN 1 //PG1
#define USB_DETECT_PIN 2 //PG2
#define IR_PIN 0 //PD0
#define ACCEL_INT_PIN 1 //PD1

#define MOTOR0_EN_PIN 4 //PB4
#define MOTOR1_EN_PIN 7 //PB7
#define MOTOR0_DIR0_PIN 4 //PD4
#define MOTOR0_DIR1_PIN 5 //PD5
#define MOTOR1_DIR0_PIN 6 //PD6
#define MOTOR1_DIR1_PIN 7 //PD7

#define DIGITAL0_PIN 0 //PE0
#define DIGITAL1_PIN 1 //PE1
#define DIGITAL2_PIN 3 //PG3
#define DIGITAL3_PIN 4 //PG4
#define DIGITAL4_PIN 6 //PE6
#define DIGITAL5_PIN 5 //PB5
#define DIGITAL6_PIN 6 //PB6
#define DIGITAL7_PIN 5 //PC5
#define DIGITAL8_PIN 6 //PC6
#define DIGITAL9_PIN 7 //PC7
#define DIGITAL10_PIN 0 //PB0/SS
#define DIGITAL11_PIN 2 //PB2/MOSI
#define DIGITAL12_PIN 3 //PB3/MISO
#define DIGITAL13_PIN 1 //PB1/SCK

#define ANALOG0_PIN 0 //PF0
#define ANALOG1_PIN 1 //PF1
#define ANALOG2_PIN 2 //PF2
#define ANALOG3_PIN 3 //PF3
#define ANALOG4_PIN 4 //PF4
#define ANALOG5_PIN 5 //PF5
#define BATTERY_PIN 6 //PF6

#define SERVO0_PIN 2 //PC2
#define SERVO1_PIN 3 //PC3
#define SERVO2_PIN 4 //PC4
#define SERVO3_PIN 7 //PF7

void print_string(char* string, u08 num_bytes);
void clear_screen();
void init();
void init_lcd();
void init_motor();
void init_servo();
void init_adc();
void led_on(u08 num);
void led_off(u08 num);

#endif
