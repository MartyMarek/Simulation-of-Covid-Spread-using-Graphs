Data generation was done through the use of a DataGeneration class that was created to run within the actual program (RmitCovidController).

It worked well this way as we were able to create valid vertecies and edges for deletions and ensure random generated vertices didn't already exist by checking them against the existing graph loaded. 

genfiles.sh is a unix bash script that creates input files for simulations. 

the simC.sh is an example of the simulation unix bash scripts that we used for simulations A to E.