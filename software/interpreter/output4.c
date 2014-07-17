#include <stdio.h>

void start(void)
{
int j = 0;
{
int rptCnt;
for (rptCnt = 0; rptCnt < 5; rptCnt++)
{
j = (j + 1);
}
}
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