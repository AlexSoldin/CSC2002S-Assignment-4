# CSC2002S-Assignment-4

The aim of this assignment is to implement a multi-threaded typing game while ensuring both thread safety as well as 
suﬃcient concurrency. This assignment will provide an insight as to how complex the correct implementation of concurrency is, 
even for the case of a basic GUI application involving multiple changing variables.

The synchronised keyword, locks, the volatile keyword and the Atomic keyword were used in order to implement concurrency.

The Makefile will compile and run the WordApp.java program which takes in "totalWords noWords inputFile". This sets the 
total words to be included in the game, the number of words falling at any one point and the dictionary file containing the
words to fall.

Some commands pertaining to the Makefile include:
'make' - compiles programs
