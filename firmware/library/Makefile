test: test.c adc.c servo.c motor.c lcd.c utility.c
	avr-gcc -mmcu=atmega325pa -O2 -o test.elf test.c adc.c servo.c motor.c lcd.c utility.c
	avr-objcopy -O ihex test.elf test.hex
	avr-size test.elf

download: test
	avrdude -pm32 -cstk200 -E noreset -U flash:w:test.hex

#program low fuse byte to be 0xef for crystal
fuse:
	avrdude -pm32 -cstk200 -E noreset -U lfuse:w:0xef:m
	avrdude -pm32 -cstk200 -E noreset -U hfuse:w:0xd9:m

clean:
	rm -fr *.elf *.hex *.o
