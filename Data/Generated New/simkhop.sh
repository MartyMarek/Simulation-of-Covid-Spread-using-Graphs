#!/usr/bin/bash

for i in {1..9}
do
  java -cp .:jopt-simple-5.0.2.jar RmitCovidModelling -f sf${i}.net -o k${i}list.out adjlist < khoptest2345.dat
  java -cp .:jopt-simple-5.0.2.jar RmitCovidModelling -f sf${i}.net -o k${i}mat.out adjmat < khoptest2345.dat
  java -cp .:jopt-simple-5.0.2.jar RmitCovidModelling -f sf${i}.net -o k${i}inc.out incmat < khoptest2345.dat
  echo "Scale Free Iteration $i is done"
  java -cp .:jopt-simple-5.0.2.jar RmitCovidModelling -f er${i}.net -o k${i}list.out adjlist < khoptest2345.dat
  java -cp .:jopt-simple-5.0.2.jar RmitCovidModelling -f er${i}.net -o k${i}mat.out adjmat < khoptest2345.dat
  java -cp .:jopt-simple-5.0.2.jar RmitCovidModelling -f er${i}.net -o k${i}inc.out incmat < khoptest2345.dat
  echo "ER Iteration $i is done."
done