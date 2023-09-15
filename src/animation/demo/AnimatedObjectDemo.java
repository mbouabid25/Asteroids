package animation.demo;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Shape;
import java.awt.geom.Ellipse2D;

import animation.AbstractAnimation;
import animation.AnimatedObject;

/**
 * A demo of what an animated object might look like. In this case, we have a
 * small black circle that moves from left to right across the window. When it
 * reaches the right edge of the window, it bounces and goes to the left edge.
 * At the left edge, it bounces and goes right.
 *
 */
public class AnimatedObjectDemo implements AnimatedObject {
    // The diameter of the ball, in pixels
    private static final int BALL_SIZE = 10;

    // The starting left edge of the ball
    private int x = 0;

    // The starting top of the ball, measured in pixels from the
    // top of the window.
    private int y = 100;

    // The number of pixels to move on each frame of the animation.
    private int moveAmount = 1;

    // The animation that this object is part of.
    private AbstractAnimation animation;
    
    // The ball shape
    private Ellipse2D ball;

    /**
     * Creates the animated object
     * 
     * @param animation the animation this object is part of
     */
    public AnimatedObjectDemo(AbstractAnimation animation) {
        this.animation = animation;
        ball = new Ellipse2D.Double(x, y, BALL_SIZE, BALL_SIZE);
    }

    /**
     * Draws a black circle at its current location.
     * 
     * @param g the graphics context to draw on.
     */
    public void paint(Graphics2D g) {
        g.setColor(Color.BLACK);
        g.fill(ball);
    }

    /**
     * Moves the ball a small amount. If it reaches the left or right edge, it
     * bounces.
     */
    public void nextFrame() {
        // Update the x value to move in the current direction
        x = x + moveAmount;

        // Check if the right edge of the ball is beyond the right
        // edge of the window. If it is, move it to the right edge
        // and change the direction, so it will move left on its
        // next move.
        if (x + BALL_SIZE > animation.getWidth()) {
            x = animation.getWidth() - BALL_SIZE;
            moveAmount = moveAmount * -1;
        }

        // Check if the left edge of the ball is beyond the left
        // edge of the window. If it is, move it to the left edge
        // and chante the direction, so it will move right on its
        // next move.
        else if (x < 0) {
            x = 0;
            moveAmount = moveAmount * -1;
        }
        
        ball.setFrame(x, y, BALL_SIZE, BALL_SIZE);
    }
    
    /**
     * Demo of action to take when the user clicks the up arrow button.
     * This moves the ball up slightly and prints Up on the console.
     */
    public void up() {
        y = y - 10;
        System.out.println ("Up");
    }

    /**
     * Demo of action to take when the user clicks the right arrow button.
     * This prints Right on the console.
     */
    public void right() {
        System.out.println ("Right");
    }

    /**
     * Demo of action to take when the user clicks the left arrow button.
     * This prints Left on the console.
     */
    public void left() {
        System.out.println ("Left");
    }

    /**
     * Demo of action to take when the user clicks the space key.
     * This prints Space on the console.
     */
    public void space() {
        System.out.println("Space");
    }
    
    /**
     * Returns the ball that is the graphics shape 
     * @return the ball that is the graphics shape being drawn
     */
    public Shape getShape() {
        return ball;
    }

    // For TESTING only
    int getX() {
        return x;
    }
    
    // For TESTING only
    void setX(int x) {
        this.x = x;
    }
    
    // For TESTING only
    int getMoveAmount() {
        return moveAmount;
    }
    
    // For TESTING only
    void setMoveAmount(int amount) {
        moveAmount = amount;
    }
    
    // For TESTING only
    int getSize() {
        return BALL_SIZE;
    }
    


}
