#!/usr/bin/bash

for i in {1..25..3}
do
  for j in {1..9}
  do
    java -cp .:jopt-simple-5.0.2.jar RmitCovidModelling -f sf${i}.net -o sf${i}_${j}_simA-al.out adjlist < sf${i}_${j}_simA.in
    java -cp .:jopt-simple-5.0.2.jar RmitCovidModelling -f er${i}.net -o er${i}_${j}_simA-al.out adjlist < er${i}_${j}_simA.in
    
    cat sf${i}_${j}_simA-al.out >> sf-final.out
    cat er${i}_${j}_simA-al.out >> er-final.out

    echo "Iteration $i $j - adjlist is done"

  done
done  

echo "Simulation Complete"