#include <stdio.h>
#include <unistd.h>

unsigned char analog(int pin)
{
   static int pins[15] = {0, 0, 0, 0, 0,
                          0, 0, 0, 0, 0,
                          0, 0, 0, 0, 0};
   static int randomCount = 0;
   pins[pin] = ((pins[pin] + ++randomCount) % 5) * 5;
   return pins[pin];
}

unsigned char digitalPinsOut(int pin, char value)
{
   return 0;
}

unsigned char digital(int pin)
{
   return 0;
}

unsigned char button()
{
   static int i = 0;
   return i++ < 1000000;
}

void set_servo(int s, int v)
{
   printf("Servo %d set to: %d\n", s, v);
}

void set_motor(int m, int v)
{
   printf("Motor %d set to: %d\n", m, v);
}

unsigned char accelerometer(int axis)
{
   static int x = -100;
   static int y = -100;
   static int z = -100;
   int temp;

   if (axis == 0)
   {
      temp = x;
      x = ((x + 101) % 100) - 100;
      return temp;
   }
   else if (axis == 1)
   {
      temp = y;
      y = ((y + 101) % 100) - 100;
      return temp;
   }
   else if (axis == 2)
   {
      temp = z;
      z = ((z + 101) % 100) - 100;
      return temp;
   }
   else
   {
      return -1;
   }
}

void delay_milliseconds(int milli)
{
   usleep(milli * 1000);
}

void delay_seconds(int sec)
{
   sleep(sec);
}

void lcd_clear()
{
   printf("LCD Cleared\n");
}

void lcd_cursor(int row, int col)
{
   printf("Cursor set to %d x %d\n", row, col);
}

void led1(int status)
{
   printf("LED set to %d\n", status);
}
