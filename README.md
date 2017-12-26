# Eight_Puzzle_Solver
Program to solve the eight puzzle game.




/******************************************************************************************
Disclaimer: credit goes to the creator of the main program, Gabriel Ferrer.
https://github.com/gferrer/8-Puzzle-Solver

The reason for creating a different respository is that my additions to the 
program are not necessarily improvements and take the program in a defferent 
direction


*******************************************************************************************/


8-Puzzle Game Instruccions

The game is written in Java, so you will need to have
installed a java compiler in your system.

To use (play) the game you will also need to do it through
the terminal (shell).

Once you have located the directory (folder) where the .java
files for the game are, you can compile the files using the
following command:

	javac *.java


Once the .java files have been compiled, then you can proceed to use (play)
the game by using the following command:

	java -Xmx1024m ProblemSolver -d 1 2 3 4 5 6 7 8 0



Note: Notice that the commands are space sensitive, so use the respective spaces
		as seen in the previous examples.

		Also, the portion of the execution command (1 2 3 4 5 6 7 8 0) represents the 
		initial state for the game to solve, so you can arrange the order of this
		as you please, making sure to use the same numbers while keeping their respective space
		between them.
