#include <stdio.h>
#include <stdlib.h>

#include "globals.h"
#include <util/delay.h>
#include <avr/io.h>
#include <avr/interrupt.h>

#define BUFFER_LEN 128

int _arrayCheck(int size, int index) { if (index >= size) { fprintf(stderr, "Index out of bounds\n"); exit(0); } return index; }
#define motor0 0

#define motor1 1

#define servo1 1

#define dig1 1
static int chng_dig1 = 0;

int chng_temp;
char _printBuffer[64];
void start(void)
{
int x = 500;
delay_ms(x);
}
void repeatAlways(void)
{
}
int main(void) {
init();
start();
while (1) {
repeatAlways();
}
return 0;
}
