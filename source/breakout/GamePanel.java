package breakout;

import javax.swing.JPanel;
import javax.sound.sampled.*;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;

import java.util.Timer;
import java.util.TimerTask;


/* This is the GamePanel class which extends JPanel. This is where the graphics
 * are drawn. It also has the game loop and timer functionality, which are responsible
 * for updating the game objects and drawing them to the panel.
 */
public class GamePanel extends JPanel implements Commons {
    // Variable declaration
    Timer timer;
    String message;
    Ball ball;
    Paddle paddle;
    Brick bricks[];
    int bricksRemaining;
    boolean playing = true;
    
    
    public GamePanel() {
        // Set background color
        setBackground(new Color(64, 64, 64));
        // Enable double buffering of this panel to avoid flickering.
        setDoubleBuffered(true);
        // Set the focus to the GamePanel for keyboard events to work.
        setFocusable(true);
        // Start up the game
        initGame();
    }
    
    void initGame() {
        ball = new Ball(); // Create ball object
        paddle = new Paddle(); // Create paddle object
        // The paddle's event handlers are registered as event sources.
        addKeyListener(paddle.getKeyHandler());
        addMouseMotionListener(paddle.getMouseHandler());
        
        /* Create bricks and arrange them.
         * Normally, the arrangement of the bricks is done with the help of a
         * level editor. The level editor saves files for each level and the
         * code here should parse those files to determine the layout of
         * the bricks. But for simplicity purposes of this demo, the bricks are
         * arranged in a typical rectangular manner using nested for loops.
         */
        bricks = new Brick[30];
        bricksRemaining = 0;
        for (int i = 0; i < 5; i++) {
            for (int j = 0; j < 6; j++) {
                bricks[bricksRemaining++] = new Brick(40 + j * 40, 48 + i * 12);
            }
        }
        
        /* The timer object is used to schedule the game loop.
         * scheduleAtFixedRate() is used to execute the GameLoopTask object
         * (extended from TimerTask) which contains the game loop.
         * The task runs at nearly 60 FPS. So 1000ms/60 = nearly 17 ms.
         */
        timer = new Timer();
        timer.scheduleAtFixedRate(new GameLoopTask(), 1000, 17);
    }
    
    void stopGame() {
        playing = false;
        /* Stop the timer (since any thing displayed on the screen after
         * the game is static and doesn't need to be redrawn continuously).
         */
        timer.cancel();
    }
    
    void handleCollisions() {
        // Handle collision with paddle.
        /* The paddle is not completely in the screen: it can go off screen by
         * half it's width. This is because the paddle is divided into five
         * segments. Hitting the ball with each segment will send it in a
         * different angle, giving the player a greater degree of control.
         * So to make the middle zone of the paddle available for the player
         * when the ball is near a screen edge, the paddle can go offscreen
         * by half, i.e, width/2.*/
        if (ball.getRect().intersects(paddle.getRect())) {
            // Play "bump" sound.
            playSound(Commons.SOUND_BUMP);
            // Reverse ball's Y direction.
            ball.setDy(-ball.getDy());
            /* Change ball's dx based on which part of the paddle it hits.
             * The paddle has five zones. Hitting the ball with each zone will
             * move it in a different angle, giving the player more control over
             * where the ball goes.
             * The center of the paddle makes the ball go vertically up. As it
             * moves away from the center, the speed and angle of the ball
             * increase.
             */
            int segment = paddle.getWidth() / 5;
            int first  = paddle.getX() + segment;
            int second = paddle.getX() + segment * 2;
            int third  = paddle.getX() + segment * 3;
            int fourth = paddle.getX() + segment * 4;
            int center = ball.getX() + ball.getWidth() / 2;
                    
            if (center < first) { ball.setDx(-2); }
            else if (center >= first && center < second) { ball.setDx(-1); }
            else if (center >= second && center < third) { ball.setDx(0); }
            else if (center >= third && center < fourth) { ball.setDx(1); }
            else if (center > fourth) { ball.setDx(2); }
            // Reset ball's position out of collision.
            ball.setY(paddle.getY() - ball.getHeight());
        }
        
        // Loop through the bricks array and handle collisions.
        for (Brick brick : bricks) {
            /* Perform collision check only if brick is not destroyed and
             * collision occurs.
             */
            if ((brick.destroyed == false) &&
                    (brick.getRect().intersects(ball.getRect()))) {
                // Play "ping" sound.
                playSound(Commons.SOUND_PING);
                
                int top = ball.getY();
                int bottom = ball.getY() + ball.getHeight();
                int left = ball.getX();
                int right = ball.getX() + ball.getWidth();
                
                // Set X direction depending on where collision occurs.
                if (brick.getRect().contains(right + 1, top)) {
                    int dx = ball.getDx();
                    ball.setDx(dx < 0 ? dx : -dx); // Ensure negative dx.
                } else if (brick.getRect().contains(left - 1, top)) {
                    int dx = ball.getDx();
                    ball.setDx(dx < 0 ? -dx : dx); // Ensure positive dx.
                }
                // Set Y direction depending on where collision occurs.
                if (brick.getRect().contains(left, top - 1)) {
                    int dy = ball.getDy();
                    ball.setDy(dy < 0 ? -dy : dy); // Ensure positive dy.
                } else if (brick.getRect().contains(left, bottom + 1)) {
                    int dy = ball.getDy();
                    ball.setDy(dy < 0 ? dy : -dy); // Ensure negative dy.
                }
                // Destroy the brick and update brick count.
                brick.destroyed = true;
                bricksRemaining--;
            }
        }
    }
    
    @Override
    public void paint(Graphics g) {
        super.paint(g);
        /* Draw using a Graphics2D object because Graphics doesn't support
         * anti-aliasing.
         */
        Graphics2D g2d = (Graphics2D) g;
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
                RenderingHints.VALUE_ANTIALIAS_ON);
        
        // If not lost or won (still playing), draw objects.
        if (playing) {
            // Draw the ball
            g2d.drawImage(ball.getImage(), ball.getX(), ball.getY(),
                    ball.getWidth(), ball.getHeight(), this);
            
            // Draw the paddle
            g2d.drawImage(paddle.getImage(), paddle.getX(), paddle.getY(),
                    paddle.getWidth(), paddle.getHeight(), this);
            
            // Draw the bricks
            for (Brick brick : bricks) {
                // If brick is destroyed, continue to next brick.
                if (brick.destroyed) continue;
                
                // Else, draw the brick.
                g2d.drawImage(brick.getImage(), brick.getX(), brick.getY(),
                        brick.getWidth(), brick.getHeight(), this);
            }
        }
        //If won or lost, display the message instead of the objects.
        else {
            Font font = new Font("Arial", Font.ITALIC, 20);
            
            g2d.setColor(Color.WHITE);
            g2d.setFont(font);
            g2d.drawString(message,
                    Commons.WIDTH / 2 -
                    this.getFontMetrics(font).stringWidth(message) / 2,
                    Commons.HEIGHT / 2);
        }
        
        /* Since g2d was not obtained by the system but created by the coder, 
         * it should be disposed. g on the other hand will be taken care of by
         * the JVM.
         * The g2d object will also be eventually garbage collected,
         * but it is recommended to dispose user-created graphic objects, as
         * specified in the Java documentation.
         */
        g2d.dispose();
    }
    
    class GameLoopTask extends TimerTask {
        /* This is where the game loop logic goes. Here, the objects are
         * updated and then drawn to the panel.
         * Code for updating game objects should be separate from the code for
         * rendering them. That's why, the code for updating the ball and the
         * paddle and collision handling is not included in the paint() method.
         */
        @Override
        public void run() {
            ball.move(); // Update ball's position.
            paddle.move(); // Update the paddle's position.
            
            // If ball goes below screen, lose game.
            if (ball.getY() > Commons.HEIGHT) {
                message = "Game Over";
                stopGame();
            }
            // Else if all bricks are destroyed, win game.
            else if (bricksRemaining == 0) {
                message = "Congratulations! You won!";
                stopGame();
            }
            // Detect and handle collisions between different objects.
            handleCollisions();
            // Render objects to panel.
            repaint();
        }
    }
    
    public void playSound(String filename) {
        try {
            // Open an audio input stream.
            //File sndFile = new File(this.getClass().getResource(filename));
            AudioInputStream audioIn = AudioSystem.getAudioInputStream(this.getClass().getResource(filename));
            // Get a sound clip object.
            Clip clip = AudioSystem.getClip();
            // Play the sound.
            clip.open(audioIn);
            clip.start();
        } catch (Exception e) {
            // Display the stack trace in case of an exception.
            e.printStackTrace();
        }
    }
}
