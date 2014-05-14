#include "arduinolib/Arduino.h"
#include <avr/io.h>
#include <util/delay.h>
#include <avr/interrupt.h>

#define LED_PIN 7
/*
This page lists which intterupt #s are associated with which pins: 
http://arduino.cc/en/Reference/attachInterrupt
*/
#define INPUT_PIN 2 //The IR pin is D0
#define INTERRUPT 0 //Should be 2
#define MAX_BUFFER_SIZE 10

volatile int state = 0;
unsigned char buffer[MAX_BUFFER_SIZE];
unsigned char bufferSize = 0;

//initialize timer 0
/*void lab4_initialize_timer0(void)
{
    TCCR0B |= (1 << CS02);   //set timer 0 to increment at 62.5KHz
}*/

//Initialize Timer 1
/*void initialize_timer(void)
{
    TCCR1B = 2;                  //increment timer at 2MHz
    TIMSK1 |= (1 << OCIE1A); //enable output compare match
    sei();                   //enable global interrupts
}*/


//Interrupt Funciton
/*ISR(TIMER1_COMPA_vect)
{
    //OCR1A +=speed_1;
}*/


//Check Falling Edge
char checkForFallingEdge(int timeInMilsec)
{
    TCNT0 = 0;
    //int timeInTicks = 0;
    //timeInTicks = ((63) * timeInTicks);

    char previousRead = 0;
    char currentRead;
    //int fallOccuredInTime = 0;

    while (TCNT0 < timeInMilsec)
    {
        currentRead = PIND & (1 << INPUT_PIN);
        // currentRead = (currentRead >> 2);

        if (!currentRead && previousRead)
        {
            //A Fall Occured
            return(1);
        }
        previousRead = currentRead;
    }
    return(0);
}

//Read Data Bit
char readDataBit(void)
{
    if (checkForFallingEdge(75))
    {
        //Fall Detected. Continue.
        if (!(checkForFallingEdge(57)))
        {
            //Fall Not Detected. Continue.
            if ((PIND & (1 << INPUT_PIN)) != 0)
            {
                return(0);
            }
            else
            {
                return(1);
            }
        }
        else
        {
            //Fall  Detected. Abort.
            return(2);
        }
    }
    else
    {
        //Fall Not Detected
        return(2);
    }
}

//Wait for Starting Bit
void waitForStartBit(void)
{
    char previousRead = 0;
    char currentRead;

    while(1)
    {
        currentRead = PIND & (1 << INPUT_PIN);
        //currentRead = (currentRead >> 2);

        if (!currentRead && previousRead)
        {
            //Falling Edge Detected
            if (!checkForFallingEdge(157))
            {
                //Fall Did Not Occur. Continue.
                break;
            }
        }
        previousRead = currentRead;
    }
}

/**
    Toggles the state of the LED
*/
void blink(void)
{
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
*/
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
*/
void clearBuffer()
{
    bufferSize = 0;
}

/**
    Adds a value to the end of the buffer
    Pushes out oldest value if the buffer is full
*/
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
    char dataBitArray[7];

    //Setup pins for input and output
    DDRD |= (1 << LED_PIN); //Sets pin 1 to output
    sei(); //Enable global interrupts
    attachInterrupt(INTERRUPT, blink, FALLING); //Set up a pin interrupt

    //Main loop
    while(1)
    {

    }

    return 0;
}








