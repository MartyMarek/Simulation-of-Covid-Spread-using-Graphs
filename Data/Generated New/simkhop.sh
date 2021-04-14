#!/usr/bin/bash

for i in {1..3}
do
  java -cp .:jopt-simple-5.0.2.jar RmitCovidModelling -f sf${i}.net -o k${i}list.out adjlist < khoptest234.dat
  java -cp .:jopt-simple-5.0.2.jar RmitCovidModelling -f sf${i}.net -o k${i}mat.out adjmat < khoptest234.dat
  java -cp .:jopt-simple-5.0.2.jar RmitCovidModelling -f sf${i}.net -o k${i}inc.out incmat < khoptest234.dat
  echo "Scale Free Iteration $i is done"
  java -cp .:jopt-simple-5.0.2.jar RmitCovidModelling -f er${i}.net -o k${i}list.out adjlist < khoptest234.dat
  java -cp .:jopt-simple-5.0.2.jar RmitCovidModelling -f er${i}.net -o k${i}mat.out adjmat < khoptest234.dat
  java -cp .:jopt-simple-5.0.2.jar RmitCovidModelling -f er${i}.net -o k${i}inc.out incmat < khoptest234.dat
  echo "ER Iteration $i is done."
done

echo "Combining Files..."

for j in {1..3}
do
  cat k${i}list.out >> final_khop_adjlist.out
  cat k${i}mat.out >> final_khop_adjmat.out
  cat k${i}inc.out >> final_khop_incmat.out
done

unix2dos final_khop_adjlist.out
unix2dos final_khop_adjmat.out
unix2dos final_khop_incmat.out

echo "Simulation Complete"