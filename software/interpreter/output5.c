#include <stdio.h>

void start(void)
{
float i = 1.0;
int j = 3;
{
int rptCnt;
for (rptCnt = 0; rptCnt < 5; rptCnt++)
{
printf("%s%f\n","hello",i);
i = (i + 1);
{
int rptCnt;
for (rptCnt = 0; rptCnt < 10; rptCnt++)
{
i = (i + 1);
}
}
}
}
j = i;
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