#!/usr/bin/bash

for (( i = 1; i <= 27; i++ ))
do
  for (( j = 1 ; j <= 27; j++ ))
  do
    java -cp .:jopt-simple-5.0.2.jar RmitCovidModelling -f sf${i}.net -o sf-k${i}-${j}list.vout adjlist < vertexdel.dat
    java -cp .:jopt-simple-5.0.2.jar RmitCovidModelling -f sf${i}.net -o sf-k${i}-${j}mat.vout adjmat < vertexdel.dat
    java -cp .:jopt-simple-5.0.2.jar RmitCovidModelling -f sf${i}.net -o sf-k${i}-${j}inc.vout incmat < vertexdel.dat
    echo "Scale Free Iteration $i $j is done"
    java -cp .:jopt-simple-5.0.2.jar RmitCovidModelling -f er${i}.net -o er-k${i}-${j}list.vout adjlist < vertexdel.dat
    java -cp .:jopt-simple-5.0.2.jar RmitCovidModelling -f er${i}.net -o er-k${i}-${j}mat.vout adjmat < vertexdel.dat
    java -cp .:jopt-simple-5.0.2.jar RmitCovidModelling -f er${i}.net -o er-k${i}-${j}inc.vout incmat < vertexdel.dat
    echo "ER Iteration $i $j is done."
  
    cat sf-k${i}-${j}list.vout >> sf-final_vdel_adjlist.vout
    cat sf-k${i}-${j}mat.vout >> sf-final_vdel_adjmat.vout
    cat sf-k${i}-${j}inc.vout >> sf-final_vdel_incmat.vout
    cat er-k${i}-${j}list.vout >> er-final_vdel_adjlist.vout
    cat er-k${i}-${j}mat.vout >> er-final_vdel_adjmat.vout
    cat er-k${i}-${j}inc.vout >> er-final_vdel_incmat.vout

  done

done

unix2dos sf-final_vdel_adjlist.vout
unix2dos sf-final_vdel_adjmat.vout
unix2dos sf-final_vdel_incmat.vout
unix2dos er-final_vdel_adjlist.vout
unix2dos er-final_vdel_adjmat.vout
unix2dos er-final_vdel_incmat.vout

echo "Simulation Complete"