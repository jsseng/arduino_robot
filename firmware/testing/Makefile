SELF_DIR := $(dir $(lastword $(MAKEFILE_LIST)))
BOARD_LIB = $(SELF_DIR)../library

main: main.c $(wildcard $(BOARD_LIB)/*.c)
	avr-gcc -I$(BOARD_LIB) -DF_CPU=20000000L -Wall -mmcu=atmega325pa -O2 -o main.elf main.c $(wildcard $(BOARD_LIB)/*.c)
	avr-objcopy -O ihex main.elf main.hex
	avr-size main.elf

ide: .test.c $(wildcard $(BOARD_LIB)/*.c)
	echo $(SELF_DIR)
	avr-gcc -I$(BOARD_LIB) -DF_CPU=20000000L -Wall -mmcu=atmega325pa -O2 -o main.elf .test.c $(wildcard $(BOARD_LIB)/*.c)
	avr-objcopy -O ihex main.elf main.hex
	avr-size main.elf

ir_test: ir_test.c $(wildcard $(BOARD_LIB)/*.c)
	avr-gcc -I$(BOARD_LIB) -DF_CPU=20000000L -Wall -mmcu=atmega325pa -O2 -o main.elf ir_test.c $(wildcard $(BOARD_LIB)/*.c)
	avr-objcopy -O ihex main.elf main.hex
	avr-size main.elf

test: test.c $(wildcard $(BOARD_LIB)/*.c)
	avr-gcc -I$(BOARD_LIB) -DF_CPU=20000000L -Wall -mmcu=atmega325pa -O2 -o main.elf test.c $(wildcard $(BOARD_LIB)/*.c)
	avr-objcopy -O ihex main.elf main.hex
	avr-size main.elf

tap_test: tap_test.c $(wildcard $(BOARD_LIB)/*.c)
	avr-gcc -I$(BOARD_LIB) -DF_CPU=20000000L -Wall -mmcu=atmega325pa -O2 -o main.elf tap_test.c $(wildcard $(BOARD_LIB)/*.c)
	avr-objcopy -O ihex main.elf main.hex
	avr-size main.elf

program: main 
	avrdude -F -pm325 -cusbtiny -E noreset -U flash:w:main.hex

#edit the device to match the device on your computer
MAC_DEVICE = `ls /dev/cu*serial*`
usbprogram: main.elf
	avrdude -pm325 -P $(MAC_DEVICE) -c arduino -F -u -U flash:w:main.hex

usbprogram_linux : main.elf
	avrdude -pm325 -P /dev/ttyUSB0 -c arduino -F -u -U flash:w:main.hex

clean:
	rm -fr *.elf *.hex *.o
