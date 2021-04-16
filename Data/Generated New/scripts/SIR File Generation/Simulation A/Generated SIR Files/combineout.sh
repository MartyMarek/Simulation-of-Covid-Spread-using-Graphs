#!/usr/bin/bash

for i in {1..25..3}
do
  for j in {1..1}
  do    
    sftime=$( tail -n 2 sf${i}_${j}_simA.out )
    ertime=$( tail -n 2 er${i}_${j}_simA.out )

    sftotal=$( tail -n 1 sf${i}_${j}_simA.out )
    ertotal=$( tail -n 1 er${i}_${j}_simA.out )

    sftime >> sftime.out
    ertime >> ertime.out

    sftotal >> sftotal.out
    ertotal >> ertotal.out
    
    echo "Iteration $i $j is done"
  done
done  

echo "Simulation Complete"