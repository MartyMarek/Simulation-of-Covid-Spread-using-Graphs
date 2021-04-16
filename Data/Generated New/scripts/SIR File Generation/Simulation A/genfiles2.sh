#!/usr/bin/bash

for i in {1..25..3}
do
  for j in {1..9}
  do
    java -cp .:jopt-simple-5.0.2.jar RmitCovidModelling -f sf${i}.net -o sf${i}_${j}_simA.in adjlist < gr${j}.dat
    java -cp .:jopt-simple-5.0.2.jar RmitCovidModelling -f er${i}.net -o er${i}_${j}_simA.in adjlist < gr${j}.dat
    dos2unix sf${i}_${j}_simA.in
    dos2unix er${i}_${j}_simA.in
    echo "Iteration $i $j is done"
  done
done  

echo "Simulation Complete"