#!/usr/bin/bash

for (( i = 1; i <= 3; i++ ))
do
  for (( j = 1 ; j <= 2; j++ ))
  do
    java -cp .:jopt-simple-5.0.2.jar RmitCovidModelling -f sf${i}.net -o sf-k${i}-${j}list.eout adjlist < edgeadd.dat
    java -cp .:jopt-simple-5.0.2.jar RmitCovidModelling -f sf${i}.net -o sf-k${i}-${j}mat.eout adjmat < edgeadd.dat
    java -cp .:jopt-simple-5.0.2.jar RmitCovidModelling -f sf${i}.net -o sf-k${i}-${j}inc.eout incmat < edgeadd.dat
    echo "Scale Free Iteration $i $j is done"
    java -cp .:jopt-simple-5.0.2.jar RmitCovidModelling -f er${i}.net -o er-k${i}-${j}list.eout adjlist < edgeadd.dat
    java -cp .:jopt-simple-5.0.2.jar RmitCovidModelling -f er${i}.net -o er-k${i}-${j}mat.eout adjmat < edgeadd.dat
    java -cp .:jopt-simple-5.0.2.jar RmitCovidModelling -f er${i}.net -o er-k${i}-${j}inc.eout incmat < edgeadd.dat
    echo "ER Iteration $i $j is done."
  
    cat sf-k${i}-${j}list.eout >> sf-final_eadd_adjlist.eout
    cat sf-k${i}-${j}mat.eout >> sf-final_eadd_adjmat.eout
    cat sf-k${i}-${j}inc.eout >> sf-final_eadd_incmat.eout
    cat er-k${i}-${j}list.eout >> er-final_eadd_adjlist.eout
    cat er-k${i}-${j}mat.eout >> er-final_eadd_adjmat.eout
    cat er-k${i}-${j}inc.eout >> er-final_eadd_incmat.eout

  done

done

unix2dos sf-final_eadd_adjlist.eout
unix2dos sf-final_eadd_adjmat.eout
unix2dos sf-final_eadd_incmat.eout
unix2dos er-final_eadd_adjlist.eout
unix2dos er-final_eadd_adjmat.eout
unix2dos er-final_eadd_incmat.eout

echo "Simulation Complete"