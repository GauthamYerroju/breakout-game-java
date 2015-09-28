Open "Play Breakout.exe" to play the game.
If the file is named "Play Breakout.dat", change the extension to exe ("Play Breakout.exe") and run it.

======= OBJECTIVE =======

The objective of the game is to use the paddle to bounce the ball towards the bricks and destroy them. You win when all the bricks are destroyed. But if the ball falls below the paddle into the fiery pits of doom, you lose the game.

The paddle has "zones" which bounce the ball in different angles. Try out the game and you will notice it. If the ball bounces at the center, it goes straight up. As it goes to the sides, it's angle and speed increase. This gives you a certain degree of control over the direction of the ball.


======== CONTROLS ========

Left Arrow: Move paddle left.
Right Arrow: Move paddle right.

While the mouse is inside the game window, the paddle will follow the mouse.
Playing with the mouse gives the best sense of control, as using the keyboard limits the speed at which you can move the paddle.

Also note that the paddle is capable of going out of the screen by about half it's width. Take advantage of this.


==== Planned features ====

The following features were planned (in order of priority), but were not implemented due to lack of time:

1. Implement Pause
2. Implement Lives and Scores
3. Make mouse cursor invisible when in game window
4. Implement powerups, powerdowns and score multipliers
5. Implement level loading from text files (create your own levels!)


======= KNOWN BUGS =======

1. The first time a sound effect is played, there is a short delay, perhaps due to the Audio System loading for the first time.


======= OTHER NOTES =======

- Please check the source code for verbose comments and detailed explanations/justifications of code.

- The class with the main() method is "Breakout".

- The game was compiled using JDK 1.7.0. It should still run fine with JRE 1.6.0 but in case there is any error or unexpected behaviour, please install JRE`7:
http://www.oracle.com/technetwork/java/javase/downloads/jre-7u2-download-1377135.html




======= REFERENCES =======

Oracle's Documentation on Java

http://www.brackeen.com/javagamebook/

http://www3.ntu.edu.sg/home/ehchua/programming/java/J8c_PlayingSound.html

http://www.roseindia.net/java/example/java/awt/RanderingHintsGraphics.shtml

http://www.gamedev.net/page/resources/_/technical/general-programming/java-games-active-rendering-r2418


========= CREDITS =========

Graphics: Gautham Yerroju

Sounds: http://www.grsites.com/archive/sounds/

------ Special Thanks ------

- NetBeans and Google, which drastically cut down the development time.
- JSmooth, which was used to generate the exe wrapper.
- Photoshop, which was used to make all the graphics.
- Audacity, which was used to edit the sound files.