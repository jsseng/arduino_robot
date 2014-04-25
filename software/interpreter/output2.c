#include <stdio.h>

int auto_old, auto_new;
float a = 1.5;
int b = 0;
void start(void)
{
printf("%s %f %d %f %s\n","omg",0.5,1,(0.5 + 1),"this better work!");
a = (a + 1);
b = (b + 1);
printf("%s %f %d %f %s\n","This is a string",a,b,(a + b),"this better work!");
}
void repeatAlways(void)
{
}
int main(void) {
start();
while (1) {
repeatAlways();
}
return 0;
}