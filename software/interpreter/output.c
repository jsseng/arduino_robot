#include <stdio.h>
#include <stdlib.h>

#define m1 0

#define m2 1

int a = 1;
int b = 1;
void start(void)
{
}
void repeatAlways(void)
{
}
int main(void) {
start();
motor(m1, 50);
motor(m1, -70);
motor(m2, 0);
motor(m2, -22);
if ((a != b))
{
exit(0);
}
else
{
}
exit(0);
while (1) {
repeatAlways();
}
return 0;
}