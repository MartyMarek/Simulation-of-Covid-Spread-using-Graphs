Automated Testing for Khop in Unix

Steps.

1. Copy contents of this directory and the code source into a directory in Unix
2. Login to unix go to the folder then type the following commands
   -> dos2unix initialise.sh
   -> chmod +x initialise.sh
3. in simkhop.sh file change the for loop numbers to reflect the files you want to run (ie. for graphs 10 to 15 put {10..15} into BOTH for loops) - you can also change this before you upload all the files into unix.
4. now type -> ./initialise.sh (this will compile the program and get everything ready)
5. now type -> ./simkhop.sh