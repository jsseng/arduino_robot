#include <stdio.h>

#define thermometer 0

#define leftServo 0

#define rightServo 1

void start(void)
{
printf("%s\n","Hello! Machine is starting up");
}
void repeatAlways(void)
{
printf("%s%d\n","Thermometer reading is: ",analog(thermometer));
set_servo(leftServo, 15);
set_servo(rightServo, 75);
printf("%s%d\n","Temperature in Celcius is: ",(((analog(thermometer) - 32) * 5) /(float) 9));
}
int main(void) {
start();
while (1) {
repeatAlways();
}
return 0;
}