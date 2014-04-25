#include <stdio.h>

int auto_old, auto_new;
void start(void)
{
float i = 1.0;
int j = 3;
{
int repeatCounter = 0;
for (repeatCounter = 0; repeatCounter < 5; repeatCounter++)
{
printf("%s %f\n","hello",i);
i = (i + 1);
{
int repeatCounter = 0;
for (repeatCounter = 0; repeatCounter < 10; repeatCounter++)
{
i = (i + 1);
}
}
}
}
j = i;
printf("%d\n",j);
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