#include <stdio.h>

unsigned char analog(int pin)
{
   return 0;
}

unsigned char digitalPinsOut(int pin, char value)
{
   return 0;
}

unsigned char digitalPinsIn(int pin)
{
   return 0;
}

void set_servo(int s)
{
}

void motor(int m)
{
}

unsigned char accelerometer(int axis)
{
   if (axis == 0)
   {
      return 0;
   }
   else if (axis == 1)
   {
      return 0;
   }
   else if (axis == 2)
   {
      return 0;
   }
   else
   {
      return -1;
   }
}

void delay_milliseconds(int milli)
{
}

void delay_seconds(int sec)
{
}
void lcd_clear()
{
}
void lcd_cursor(int row, int col)
{
}
unsigned char button()
{
   return 0;
}

void led1(int status)
{
}
