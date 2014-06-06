#include <Arduino.h>

#include <avr/io.h>
#include <util/delay.h>
#include <avr/interrupt.h>

#define LED_PIN 7
/*
This page lists which interrupt #s are associated with which pins:
http://arduino.cc/en/Reference/attachInterrupt
This page lists the command values for various buttons on Sony remotes:
http://www.sbprojects.com/knowledge/ir/sirc.php
*/
#define INPUT_PIN 2 //The IR pin is D0
#define INTERRUPT 0 //Should be 2
#define MAX_BUFFER_SIZE 10

volatile int state = 0;
char buffer[MAX_BUFFER_SIZE];
char bufferSize = 0;

void initialize_timer0(void) {
    TCCR0B |= (1 << CS02);   //set timer 0 to increment at 62.5KHz
}

/**
    Returns true if a falling edge is detected before the timeout
**/
char checkForFallingEdge(int timeout){
    TCNT0 = 0;
    char a;
    char b = 0;
    while (TCNT0 < timeout){
        a = PIND & (1 << INPUT_PIN);
        if (!a && b){
            return(1);
        }
        b = a;
    }
    return(0);
}

/**
    Reads a binary value from the IR sensor
    Returns 2 if the signal is invalid
**/
char readDataBit(void){
    int i;
    if (checkForFallingEdge(75)){ //Falling edge detected within 1.2ms
        if(!(checkForFallingEdge(57))){ //No falling edge found in the next .9ms
            if ((PIND & (1 << INPUT_PIN)) != 0){
                return(0);
            }
            else{
                return(1);
            }
        }
        else{
            return(2); //invalid signal, abort
        }
    }
    else{
        return(2); //invalid signal, abort
    }
}

/**
    Converts a binary value in an array to a decimal value
**/
char getDataValue(char arr[])
{
    char num = 0;
    char i;
    for(i = 6; i >= 0; i--){
        num = num << 1;
        num += arr[i];
    }
    return num;
}

/**
    Toggles the state of the LED
**/
void blink(void)
{
    if (!checkForFallingEdge(157))
    {
        //Quit if the signal is invalid
        return;
    }

    //Start bit found, continue
    char i;
    char dataBits[7];
    char num = 0;

    for(i = 0; i < 7; i++)
    {
        //Read the data
        dataBits[i] = readDataBit();
        if(dataBits[i] == 2)
        {
            //Check for invalid bits
            return;
        }
    }

    //Read the value of the signal
    num = getDataValue(dataBits);

    //Blink the LED
    state = !state;
    if (state)
    {
        PORTD |= (1 << LED_PIN);
    }
    else
    {
        PORTD &= !(1 << LED_PIN);
    }
}

/**
    Returns the most recent value in the buffer
    and removes it from the buffer
**/
unsigned char getNextBufferValue()
{
    if (bufferSize == 0)
    {
        return 0;
    }

    bufferSize--;
    return(buffer[bufferSize]);
}

/**
    Removes all elements from the buffer
    and resets the buffer size to 0
**/
void clearBuffer()
{
    bufferSize = 0;
}

/**
    Adds a value to the end of the buffer
    Pushes out oldest value if the buffer is full
**/
void addToBuffer(unsigned char value)
{
    int i;

    if (bufferSize == MAX_BUFFER_SIZE)
    {
        //Buffer is full, shift values
        for (i = 0; i < bufferSize-1; i++)
        {
            buffer[i] = buffer[i+1];
        }
        //Drop the last element
        bufferSize--;
    }

    //Add the new value
    buffer[bufferSize] = value;
    bufferSize++;
}

//Main
int main(void)
{
    //Setup pins for input and output
    DDRD |= (1 << LED_PIN); //Sets pin 1 to output

    //Timers and interrupts
    sei(); //Enable global interrupts
    initialize_timer0();
    attachInterrupt(INTERRUPT, blink, RISING); //Set up a pin interrupt

    //Main loop
    while(1)
    {

    }

    return 0;
}








