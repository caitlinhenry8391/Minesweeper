# Minesweeper
Project Title: MineSweeper
Author: Caitlin Henry
Version: 1.0
Last Updated: 4/19/2016

Project Overview:
The project provides the basic implementation for a MineSweeper game in Java, as well as an automatic MineSweeper puzzle solver. 
The user can generate and play a Minesweeper game, or run the solver to run and solve 100,000 randomly generated instances of Minesweeper.

Files Structure:

src: contains java code for project. All code is in the default package.
  - Mine.java: Class definition for each individual mine on the Minesweeper board
  - MineBoard.java: Implementation of a Minesweeper game, main method allows you to play a game on an NxN board with X mines, taking user input from the command line
  - MineSolver.java: Randomly generate 100,000 10x10 Minsweeoer boards with 10 mines each, attempts to solve each puzzle, print out performance to command line.

Installation instructions:

Download the zip file from the Github repository and save it anywhere on your local Desktop.

To run the executable Java program from the command line: 
Navigate to the folder where MinesweeperGame.jar is saved, then enter the command: java -jar MinesweeperGame.jar
Instructions should appear in the console.

To open the project in Eclipse IDE:
Ensure you have Eclipse and Java JDK installed on your computer.
Open Eclipse and select File->Import...->General->Existing Projects to Workspace
Check Select archive file-> Browse... to select the Minesweeper-master.zip file from wherever it is saved on your computer
Make sure the MineSweeper project is selected in the Projects text box, then click finish.
Expand the src folder to see the poker and tests packages.
To run the MineBoard program, right-click MineBoard.java and select Run As... Java Application
To run the MoneSolver, right-click MineSolver.java and select Run As... Java Application
