F_CPU = 16000000
f = irTest
p = com8
INTERRUPT = "C:\Program Files (x86)\Arduino\hardware\arduino\cores\arduino\WInterrupts.c"

default:
	avr-gcc -mmcu=atmega328p -DF_CPU=$(F_CPU) -O2 -o $f.elf $f.c $(INTERRUPT)
	avr-objcopy -O ihex $f.elf $f.hex
	avr-size $f.elf

clean:
	rm -rf *.elf *.o *.hex

download_mac:
	#may need to change /dev/tty to correct port on your computer
	avrdude -p m328p -P /dev/tty.usbmodem1411 -c arduino -F -u -U flash:w:$f.hex

download_linux:
	#may need to change /dev/tty to correct port on your computer
	avrdude -p m328p -P /dev/ttyACM0 -c arduino -F -u -U flash:w:$f.hex

download_windows:
	avrdude -p m328p -P $p -c arduino -F -u -U flash:w:$f.hex 