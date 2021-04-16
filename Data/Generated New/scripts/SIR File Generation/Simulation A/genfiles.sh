#!/usr/bin/bash

declare -i x=1

for i in {1..25..3}
do
  java -cp .:jopt-simple-5.0.2.jar RmitCovidModelling -f sf${i}.net -o sf${i}_simA.in adjlist < gr${x}.dat
  java -cp .:jopt-simple-5.0.2.jar RmitCovidModelling -f er${i}.net -o er${i}_simA.in adjlist < gr${x}.dat
  dos2unix sf${i}_simA.in
  dos2unix er${i}_simA.in
  ((x = x + 1))
  echo "Iteration $x is done"
done  

echo "Simulation Complete"