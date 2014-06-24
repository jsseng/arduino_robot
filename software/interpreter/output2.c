#include <stdio.h>

#define s1 0

#define s2 1

void start(void)
{
}
void repeatAlways(void)
{
}
int main(void) {
start();
set_servo(0, 11);
set_servo(0, -22);
set_servo(1, 33);
set_servo(1, -44);
while (1) {
repeatAlways();
}
return 0;
}