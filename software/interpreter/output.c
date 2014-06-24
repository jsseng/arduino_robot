#include <stdio.h>

#define m1 

#define m2 

void start(void)
{
}
void repeatAlways(void)
{
}
int main(void) {
start();
motor(0, 50);
motor(0, -70);
motor(1, 0);
motor(1, -22);
while (1) {
repeatAlways();
}
return 0;
}