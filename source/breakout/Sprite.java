package breakout;

import java.awt.Image;
import java.awt.Rectangle;

/* The Sprite class is the object that represents graphics on the screen.
 * It has information like the X and Y position of the graphic, the Image
 * object containing the graphic, etc.
 */
public class Sprite {
    
    /* These attributes are protected so that subclasses can use them freely.
     * NOTE!
     * Despite having direct access to these attributes by other classes
     * in this package, I have used the setter and getter methods for the
     * most part as a habit.
     */
    protected int x;
    protected int y;
    protected int dx;
    protected int dy;
    protected Image image;
    protected int width;
    protected int height;
    
    /* Two constructors for setting up oinitial values and for easy
     * initialization.
     */
    public Sprite() {
        x = y = 0;
        dx = dy = 0;
        width = height = 0;
        image = null;
    }
    public Sprite(int x, int y) {
        this.x = x;
        this.y = y;
        dx = dy = 0;
        width = height = 0;
        image = null;
    }
    
    // Setter and getter methods for the X attribute
    public void setX(int x) { this.x = x; }
    public int getX() { return x; }
    
    // Setter and getter methods for the Y attribute
    public void setY(int y) { this.y = y; }
    public int getY() { return y; }
    
    // Setter and getter methods for the DX attribute
    public void setDx(int dx) { this.dx = dx; }
    public int getDx() { return dx; }
    
    // Setter and getter methods for the DY attribute
    public void setDy(int dy) { this.dy = dy; }
    public int getDy() { return dy; }
    
    /* Setter and getter method for the image attribute.
     * The setter method has the additional task of assigning the width
     * and height variables based on the image.
     */
    public void setImage(Image img) {
        image = img;
        width = image.getWidth(null);
        height = image.getHeight(null);
    }
    public Image getImage() { return image; }
    
    /* Getter methods for the width and height attributes.
     * There are no setter methods because they are determined by the
     * Image dimensions. But we still have width and height variables for
     * efficiency reasons. They are only changed when a new image is set. So
     * caching the width and height values will reduce the calls to Image.get_()
     * methods.
     */
    public int getWidth() {return width; }
    public int getHeight() { return height; }
    
    /* Getter method for sprite rectangle. It is typically the rectangle of the
     * image, but strictly speaking it is the bounding box of the sprite, used
     * for collision detection.
     */
    public Rectangle getRect() {
        return new Rectangle(x, y, image.getWidth(null), image.getHeight(null));
    }
}
