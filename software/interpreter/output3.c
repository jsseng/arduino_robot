#include <stdio.h>

int auto_old, auto_new;
#define thermometer analog(0)
int auto_thermometer = analog(0);

#define leftServo 0
int auto_leftServo = 0;

#define rightServo 1
int auto_rightServo = 1;

void start(void)
{
printf("%s\n","Hello! Machine is starting up");
}
void repeatAlways(void)
{
printf("%s %d\n","Thermometer reading is: ",thermometer);
set_servo(leftServo, 15);
set_servo(rightServo, 75);
printf("%s %d\n","Temperature in Celcius is: ",(((thermometer - 32) * 5) /(float) 9));
}
int main(void) {
start();
while (1) {
repeatAlways();
}
return 0;
}