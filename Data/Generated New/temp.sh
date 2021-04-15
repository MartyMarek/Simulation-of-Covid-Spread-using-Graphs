#!/usr/bin/bash

for j in {17..23}
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

echo "Complete"