#include "globals.h"
#include <util/delay.h>
#include <avr/io.h>
#include "USI_TWI_Master.h"
#include <avr/interrupt.h>

int main(void) {
   init();

   /* Type your new code here */

   digital_dir(0,1); //set pin 0 to output

   while(1) {
      digital_out(0,0); //set pin 0 to low
      _delay_ms(500);

      digital_out(0,1); //set pin 0 to high
      _delay_ms(500);
   }


   return 0;
}
