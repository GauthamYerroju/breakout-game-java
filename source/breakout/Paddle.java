package breakout;

import java.awt.event.KeyEvent;
import java.awt.event.KeyAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseAdapter;
import javax.swing.ImageIcon;

/* The Paddle class extends Sprite. It has the functionality of the paddle,
 * including event handling.
 */
public class Paddle extends Sprite implements Commons {
    
    private KeyHandler keyHandler = null; // Instance of the key event handler.
    private MouseHandler mouseHandler = null; // Instance of the mouse event handler.
    
    public Paddle() {
        setImage(new ImageIcon(this.getClass().getResource(Commons.PIC_PADDLE)).getImage());
        reset();
    }
    
    public void move() {
        x += dx; // Update regular movement.
        
        /* Keep paddle in screen.
         * NOTE!
         * The paddle is not completely in the screen: it can go off screen by
         * half it's width. This is because the paddle is divided into five
         * segments. Hitting the ball with each segment will send it in a
         * different angle, giving the player a greater degree of control.
         * So to make the middle zone of the paddle available for the player
         * when the ball is near a screen edge, the paddle can go offscreen
         * by half, i.e, width/2.
         */
        if (x <= -width/2) x = -width/2;
        if (x >= Commons.WIDTH-width/2) x = Commons.WIDTH-width/2;
    }
    
    public void reset() {
        x = Commons.WIDTH / 3;
        y = Commons.HEIGHT - 32;
    }
    
    // This inner class handles the keyboard events and updates the Paddle.
    class KeyHandler extends KeyAdapter {
        // Start moving when left or right keys are pressed.
        @Override
        public void keyPressed(KeyEvent e) {
            int key = e.getKeyCode();
            if (key == KeyEvent.VK_LEFT) dx = -2;
            if (key == KeyEvent.VK_RIGHT) dx = 2;
        }
        // Stop moving when left or right keys are released.
        @Override
        public void keyReleased(KeyEvent e) {
            int key = e.getKeyCode();
            if (key == KeyEvent.VK_LEFT) dx = 0;
            if (key == KeyEvent.VK_RIGHT) dx = 0;
        }
    }
    // Getter method for the key handler
    public KeyHandler getKeyHandler() {
        if (keyHandler == null) keyHandler = new KeyHandler();
        return keyHandler;
    }
    
    // This inner class handles the mouse events and updates the Paddle.
    class MouseHandler extends MouseAdapter {
        @Override
        public void mouseMoved(MouseEvent e) {
            int mouseX = e.getX();
            // Set paddle position according to mouse position.
            setX(mouseX - width/2);
        }
    }
    // Getter method for the key handler
    public MouseHandler getMouseHandler() {
        if (mouseHandler == null) mouseHandler = new MouseHandler();
        return mouseHandler;
    }
}
