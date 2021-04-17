#!/usr/bin/bash

for i in {1..25..3}
do
  for j in {1..9}
  do
    java -cp .:jopt-simple-5.0.2.jar RmitCovidModelling -f sf${i}.net -o sf${i}_${j}_simA-al.out adjlist < sf${i}_${j}_simA.in
    java -cp .:jopt-simple-5.0.2.jar RmitCovidModelling -f er${i}.net -o er${i}_${j}_simA-al.out adjlist < er${i}_${j}_simA.in
    ( tail -n 2 sf${i}_${j}_simA-al.out | head -n1 ) >> sftime-al.out
    ( tail -n 2 er${i}_${j}_simA-al.out | head -n1 ) >> ertime-al.out
    ( tail -n 1 sf${i}_${j}_simA-al.out | head -n1 ) >> sftotal-al.out
    ( tail -n 1 er${i}_${j}_simA-al.out | head -n1 ) >> ertotal-al.out
    echo "Iteration $i $j - adjlist is done"

    java -cp .:jopt-simple-5.0.2.jar RmitCovidModelling -f sf${i}.net -o sf${i}_${j}_simA-am.out adjmat < sf${i}_${j}_simA.in
    java -cp .:jopt-simple-5.0.2.jar RmitCovidModelling -f er${i}.net -o er${i}_${j}_simA-am.out adjmat < er${i}_${j}_simA.in
    ( tail -n 2 sf${i}_${j}_simA-am.out | head -n1 ) >> sftime-am.out
    ( tail -n 2 er${i}_${j}_simA-am.out | head -n1 ) >> ertime-am.out
    ( tail -n 1 sf${i}_${j}_simA-am.out | head -n1 ) >> sftotal-am.out
    ( tail -n 1 er${i}_${j}_simA-am.out | head -n1 ) >> ertotal-am.out
    echo "Iteration $i $j - adjmat is done"

    java -cp .:jopt-simple-5.0.2.jar RmitCovidModelling -f sf${i}.net -o sf${i}_${j}_simA-im.out incmat < sf${i}_${j}_simA.in
    java -cp .:jopt-simple-5.0.2.jar RmitCovidModelling -f er${i}.net -o er${i}_${j}_simA-im.out incmat < er${i}_${j}_simA.in
    ( tail -n 2 sf${i}_${j}_simA-im.out | head -n1 ) >> sftime-im.out
    ( tail -n 2 er${i}_${j}_simA-im.out | head -n1 ) >> ertime-im.out
    ( tail -n 1 sf${i}_${j}_simA-im.out | head -n1 ) >> sftotal-im.out
    ( tail -n 1 er${i}_${j}_simA-im.out | head -n1 ) >> ertotal-im.out
    echo "Iteration $i $j - incmat is done"

  done
done  

echo "Simulation Complete"