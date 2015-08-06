#!/bin/bash

echo "test"
echo "ACTION==\"add\", ATTRS{idVendor}==\"04b4\", ATTRS{idProduct}==\"0003\", SYMLINK+=\"bumblebee\"" > /etc/udev/rules.d/10-local.rules

udevadm control --reload-rules
udevadm trigger



