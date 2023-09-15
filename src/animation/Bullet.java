package animation;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Shape;
import java.awt.geom.AffineTransform;
import java.awt.geom.Ellipse2D;
import java.awt.image.BufferedImage;
import java.awt.image.FilteredImageSource;
import java.awt.image.ImageFilter;
import java.awt.image.ImageProducer;
import java.awt.image.RGBImageFilter;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;

import javax.imageio.ImageIO;

import animation.AbstractAnimation;
import animation.AnimatedObject;

/**
 * Runs the animation for a bullet and contains the features and behaviors
 * implemented by the bullet in the Asteroids game.
 */
public class Bullet implements AnimatedObject {
    // The diameter of the bullet, in pixels
    private static final int BULLET_SIZE = 4;

    // The number of pixels to move on each frame of the animation.
    private static double speed;

    // The animation that this object is part of.
    private AbstractAnimation animation;

    // The bullet shape
    private Ellipse2D bullet;

    private double x;

    private double y;

    boolean collided;

    private double radians;
    
    private String type;

    /**
     * Creates the animated bullet
     * 
     * @param animation the animation the bullet is part of
     * @param x         the x-coordinates
     * @param y         the y-coordinates
     * 
     */
    public Bullet(AbstractAnimation animation, double x, double y,
            double radians) {
        this.animation = animation;
        this.x = x;
        this.y = y;
        this.radians = radians;
        bullet = new Ellipse2D.Double(0, 0, BULLET_SIZE, BULLET_SIZE);
    }

    /**
     * Draws a green circle at its current location.
     * 
     * @param g the graphics context to draw on.
     */
    public void paint(Graphics2D g) {
    	if (type == "SAUCER") {
    		g.setColor(Color.RED);
    	}
    	if (type == "SHIP") {
    		g.setColor(Color.YELLOW);
    	}
        g.fill(getShape());
    }

    /**
     * Gets the bullet that is the graphics shape.
     * 
     * @return bullet, the graphics shape being drawn
     */
    public Shape getShape() {
        // Captures movement and rotation of bullet
        AffineTransform newShape = new AffineTransform();

        // X and Y determine origin location of bullet
        newShape.translate(x, y);
        newShape.rotate(radians);

        // returns bullet at translated and rotated location
        return newShape.createTransformedShape(bullet);
    }

    // For TESTING only
    int getSize() {
        return BULLET_SIZE;
    }

    /**
     * Gets the current speed of the bullet.
     * 
     * @return speed, the speed of the bullet
     */
    public double getSpeed() {
        return speed;
    }

    /**
     * Gets the current x-axis position of bullet.
     * 
     * @return x , the x-coordinate of bullet
     */
    public double getX() {
        return x;
    }

    /**
     * Gets the current y-axis position of bullet.
     * 
     * @return y , the y-coordinate of bullet
     */
    public double getY() {
        return y;
    }

    /**
     * Moves the bullet a small amount. If it reaches the left or right edge, it
     * goes out of the frame.
     */
    public void nextFrame() {
        x += (Math.sin(radians) * speed);
        y -= (Math.cos(radians) * speed);
    }
    
    public void setSpeed(double newSpeed) {
    	speed = newSpeed;
    }
    
    public void setType(String newType) {
    	type = newType;
    	if(type == "SHIP") {
    		x += 1;
    	}
    }
    
    public void setCoord(double x, double y) {
    	this.x = x;
    	this.y = y;
    }

    /**
     * 
     */
//    public void shoot(BulletDesign bullet)
//    { 
//       bullet.nextFrame();
//    }
}

