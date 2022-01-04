# 2048
A sliding tile puzzle game, where the goal is to reach 2048.

# How to run and play
Run the 'Main.java' file. Input to the console the number of tiles that will cover each side of the board, the original version is 4. (If you desperately want to win, run 'MockGame.java'). 

Use the arrow keys to play. Press 'enter' to restart the game and/or change the number of tiles per board-side. To quit, press 'q'.

# Design
The game interface is structured as a Model-View-Controller design pattern. The Control element receives inputs from the user and updates the game Model as well as the View graphics. Running the file 'Test.java' outputs only simple graphics to the terminal and is a sketch for the final version.
