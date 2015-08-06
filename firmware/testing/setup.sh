#!/bin/bash

echo "test"
echo "ACTION==\"add\", ATTRS{idVendor}==\"04b4\", ATTRS{idProduct}==\"0003\", SYMLINK+=\"bumblebee\"" > /etc/udev/rules.d/10-local.rules

udevadm control --reload-rules
udevadm trigger

sudo mv /usr/share/dbus-1/system-services/org.freedesktop.ModemManager1.service /usr/share/dbus-1/system-services/org.freedesktop.ModemManager1.service.disabled

sudo sed -i '/exit 0/c\' /etc/rc.local
sudo sed -i '/killall/c\' /etc/rc.local
echo "killall ModemManager" >> /etc/rc.local
echo "exit 0" >> /etc/rc.local

