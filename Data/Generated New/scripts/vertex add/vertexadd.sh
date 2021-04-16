#!/usr/bin/bash

for (( i = 1; i <= 3; i++ ))
do
  for (( j = 1 ; j <= 3; j++ ))
  do
    java -cp .:jopt-simple-5.0.2.jar RmitCovidModelling -f sf${i}.net -o sf-k${i}-${j}list.vout adjlist < vertexadd.dat
    java -cp .:jopt-simple-5.0.2.jar RmitCovidModelling -f sf${i}.net -o sf-k${i}-${j}mat.vout adjmat < vertexadd.dat
    java -cp .:jopt-simple-5.0.2.jar RmitCovidModelling -f sf${i}.net -o sf-k${i}-${j}inc.vout incmat < vertexadd.dat
    echo "Scale Free Iteration $i is done"
    java -cp .:jopt-simple-5.0.2.jar RmitCovidModelling -f er${i}.net -o er-k${i}-${j}list.vout adjlist < vertexadd.dat
    java -cp .:jopt-simple-5.0.2.jar RmitCovidModelling -f er${i}.net -o er-k${i}-${j}mat.vout adjmat < vertexadd.dat
    java -cp .:jopt-simple-5.0.2.jar RmitCovidModelling -f er${i}.net -o er-k${i}-${j}inc.vout incmat < vertexadd.dat
    echo "ER Iteration $i is done."
  
    cat sf-k${i}-${j}list.vout >> sf-final_vadd_adjlist.vout
    cat sf-k${i}-${j}mat.vout >> sf-final_vadd_adjmat.vout
    cat sf-k${i}-${j}inc.vout >> sf-final_vadd_incmat.vout
    cat er-k${i}-${j}list.vout >> er-final_vadd_adjlist.vout
    cat er-k${i}-${j}mat.vout >> er-final_vadd_adjmat.vout
    cat er-k${i}-${j}inc.vout >> er-final_vadd_incmat.vout

  done

done

unix2dos sf-final_vadd_adjlist.vout
unix2dos sf-final_vadd_adjmat.vout
unix2dos sf-final_vadd_incmat.vout
unix2dos er-final_vadd_adjlist.vout
unix2dos er-final_vadd_adjmat.vout
unix2dos er-final_vadd_incmat.vout

echo "Simulation Complete"