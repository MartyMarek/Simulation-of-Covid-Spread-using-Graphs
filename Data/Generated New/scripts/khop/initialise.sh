#!/usr/bin/bash

find . -type f -print0 | xargs -0 -n 1 -P 4 dos2unix
javac -cp .:jopt-simple-5.0.2.jar *.java
chmod +x simkhop5.sh
