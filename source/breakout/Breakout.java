package breakout;

import javax.swing.JFrame;

// The main window, acts as the main entry point.
public class Breakout extends JFrame implements Commons {
    
    public Breakout() {
        add(new GamePanel());
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        // Set and fix size
        setSize(Commons.WIDTH + 8, Commons.HEIGHT + 8);
        setLocationRelativeTo(null); // Center the game panel
        setResizable(false);
        
        /* Since repaint() is manually called at desired rate in the GamePanel,
         * disable calls from the OS. This is a partial implementation of
         * the Active Rendering technique.
         */
        setIgnoreRepaint(true);
        
        // Ready to go, make window visible.
        setTitle("Breakout");
        setVisible(true);
    }
    
    public static void main(String[] args) {
        Breakout game = new Breakout();
    }
}
