#!/usr/bin/bash

for i in {1..24}
do
  java -cp .:jopt-simple-5.0.2.jar RmitCovidModelling -f sf${i}.net -o sf-k${i}list.out adjlist < khoptest5.dat
  java -cp .:jopt-simple-5.0.2.jar RmitCovidModelling -f sf${i}.net -o sf-k${i}mat.out adjmat < khoptest5.dat
  java -cp .:jopt-simple-5.0.2.jar RmitCovidModelling -f sf${i}.net -o sf-k${i}inc.out incmat < khoptest5.dat
  echo "Scale Free Iteration $i is done"
  java -cp .:jopt-simple-5.0.2.jar RmitCovidModelling -f er${i}.net -o er-k${i}list.out adjlist < khoptest5.dat
  java -cp .:jopt-simple-5.0.2.jar RmitCovidModelling -f er${i}.net -o er-k${i}mat.out adjmat < khoptest5.dat
  java -cp .:jopt-simple-5.0.2.jar RmitCovidModelling -f er${i}.net -o er-k${i}inc.out incmat < khoptest5.dat
  echo "ER Iteration $i is done."
done

echo "Combining Files..."

for j in {1..24}
do
  cat sf-k${j}list.out >> sf-final_khop_adjlist.out
  cat sf-k${j}mat.out >> sf-final_khop_adjmat.out
  cat sf-k${j}inc.out >> sf-final_khop_incmat.out
  cat er-k${j}list.out >> er-final_khop_adjlist.out
  cat er-k${j}mat.out >> er-final_khop_adjmat.out
  cat er-k${j}inc.out >> er-final_khop_incmat.out
done

unix2dos sf-final_khop_adjlist.out
unix2dos sf-final_khop_adjmat.out
unix2dos sf-final_khop_incmat.out
unix2dos er-final_khop_adjlist.out
unix2dos er-final_khop_adjmat.out
unix2dos er-final_khop_incmat.out

echo "Simulation Complete"