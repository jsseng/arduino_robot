#include <stdio.h>

float anotherFunction()
{
return 0;
}

float aFunction(float anID)
{
if ((anID < 0))
{
return 0;
}
else
{
return 1;
}
}

float yetAnotherFunction(float anId, float anotherID, float yetAnotherID)
{
return 1;
}

void start(void)
{
}
void repeatAlways(void)
{
}
int main(void) {
start();
printf("%f\n",(aFunction( 1) + yetAnotherFunction( -4,  6.6,  0)));
while (1) {
repeatAlways();
}
return 0;
}