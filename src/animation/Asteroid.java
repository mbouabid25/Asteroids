package animation;
import java.awt.Graphics2D;
import java.awt.Shape;
import java.awt.geom.Ellipse2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;

import javax.imageio.ImageIO;

/**
* Creates animated object Asteroid, which spawns at a random 
* location on the borders of the window, moves in straight line, 
* destroys ship if hits it, and is destroyed by bullet
* 
* @author Marwa Bouabid
*/
public class Asteroid implements AnimatedObject {
    
	// The size of the asteroids
    static final int ASTEROID_SIZE = 32;
    
    // The height of the window
    private static int height = 600;
    
    // The width of the window
    private static int width = 600;
    
    // An array list that holds the coordinates of the asteroids
    ArrayList<Integer> coordinates = this.randomCoordOnBorder();
    
    // x coordinate of the asteroid
    double x = coordinates.get(0);
    
    // y coordinate of the asteroid
    double y = coordinates.get(1);
    
    // The number of pixels to move on each frame of the animation.
    static double moveAmount = 1.5;

    // The animation that this object is part of.
    private AbstractAnimation animation;
    
    // The shape of the asteroid
    private Ellipse2D asteroid;
    
    // The color of the asteroid (randomly yellow or gray)
    private int color = setRandomColor();
    
    // Did the asteroid ever get shot
    public boolean collided = false;
    
    // The angle the asteroid moves in
    private double radians;

    /**
     * Creates the animated object
     * @param animation the animation this object is part of
     */
    public Asteroid(AbstractAnimation animation) {
        this.animation = animation;
        // Creating the shape of the animation
        asteroid = new Ellipse2D.Double(x, y, ASTEROID_SIZE, ASTEROID_SIZE);
        // Generating a random angle 
        radians = randomMoveGenerator();
    }
    
    /**
     * Randomly generating a number
     * (either 1 or 2) to set the color 
     * of the asteroid to grey or yellow
     */
    public static int setRandomColor() {
    	Random rand = new Random();
    	return rand.nextInt(2);
    }

    /**
     * Painting the asteroid with a "skin"
     * on top.
     */
    public void paint(Graphics2D g) {
    	// Keeping space for the 'skins'
        String image_path = null;
        // Randomly choosing the color
        switch(color) {
        // Importing the grey asteroid
        case 0: image_path = "asteroid_grey.PNG";
        	break;
        // Importing the yellow asteroid
        case 1: image_path = "asteroid_yellow.PNG";
        	break;
        }
        ;
        // Keeping space for the buffered image
        BufferedImage bi = null;
        
        try {
        	// Converting the image into a bufferd image
			bi = ImageIO.read(new File(image_path));
		// Handling the case where the image is not found
		} catch (IOException e) {
			System.out.println("IMAGE NOT FOUND");
			e.printStackTrace();
		}
        
    	//Drawing the image
    	g.drawImage(bi, (int)x - 6, (int)y - 5, null);
    }
    
    /**
     * Check whether two objects collide. This tests whether their shapes
     * intersect.
     * 
     * @param asteroid:		the first shape to test
     * @param bullet:		the second shape to test
     * @return true, if the shapes intersect
     */
    public static boolean checkAsteroidsBulletCollision(Asteroid asteroid, Bullet bullet) {
        return !asteroid.collided && !bullet.collided 
        		&& (asteroid.getShape().intersects(bullet.getShape().getBounds2D()));
    }
    
    /**
     * Randomly generates a number that will determinate
     * which direction the asteroid will move in
     * @returns result, a number that will determine the 
     * direction of the asteroid
     */
	public static double randomMoveGenerator() {
    	Random rand = new Random();
    	
    	while (true) {
    		double result = rand.nextDouble() * (2 * Math.PI);
    		if (result != 0.0 && result != Math.PI/2 && result != Math.PI 
    				&& result != 3 * Math.PI/2 && result != 2 * Math.PI) {
    			return result;
    		}
    	}
    	
    }
    
    /**
     * Helper method that ensures that the asteroids spawn on random
     * sides of the screen
     * @return coord, a random coordinate on one of the borders
     */
    static ArrayList<Integer> randomCoordOnBorder() {
    	ArrayList<Integer> coord = new ArrayList<Integer>();
    	Random rand = new Random();
    	
    	//randomizes which border to start on (0 => Left, 1 => 
    	//Top, 2 => Right, 3 => Bottom)
    	int border = rand.nextInt(4);
    	switch (border) {
    	case 0: coord.add(0 - ASTEROID_SIZE/2);
    			coord.add(rand.nextInt(height) + ASTEROID_SIZE/2);
    		break;
    	case 1: coord.add(rand.nextInt(width) + ASTEROID_SIZE/2);
    			coord.add(0 - ASTEROID_SIZE/2);
    		break;
    	case 2: coord.add(width + ASTEROID_SIZE/2);
    			coord.add(rand.nextInt(height) + ASTEROID_SIZE/2);
    		break;
    	case 3: coord.add(rand.nextInt(width) + ASTEROID_SIZE/2);
    			coord.add(height + ASTEROID_SIZE/2);
    		break;
    	}
    	
    	return coord;
    }
    
    /**
     * Moves the asteroid a small amount. If it reaches the edges, it
     * wraps to the other side of the screen
     */
    public void nextFrame() {
        // Update the x value to move in the current direction
    	x += Math.sin(radians) * moveAmount;
        y += Math.cos(radians) * moveAmount;
        // Check if the right edge of the asteroid is beyond the right
        // edge of the window. If it is, wrap to other side
        if (x - ASTEROID_SIZE > animation.getWidth()) {
            x = 0 - ASTEROID_SIZE;
        }

        // Check if the left edge of the asteroid is beyond the left
        // edge of the window. If it is, wrap to other side
        else if (x + ASTEROID_SIZE < 0) {
            x = animation.getWidth() + ASTEROID_SIZE;
        }
        
        // check if bottom of asteroid goes beyond bottom
        //of window, if so, wrap to top
        if (y - ASTEROID_SIZE > animation.getHeight()) {
        	y = 0 - ASTEROID_SIZE;
        }
        
        // check if bottom of asteroid goes beyond bottom
        //of window, if so, wrap to bottom
        else if (y + ASTEROID_SIZE < 0) {
        	y = animation.getHeight() + ASTEROID_SIZE;
        }
        
        asteroid.setFrame(x, y, ASTEROID_SIZE, ASTEROID_SIZE);
    }
    
    public Shape getShape() {
        return asteroid;
    }
    
    /**
   	 * FOR TESTING PURPOSES ONLY
   	 * Returns x
   	 * @return double x coordinate of saucer
   	 */
   	public double getX() {
   		return x;
   	}
   	
   	/**
   	 * FOR TESTING PURPOSES ONLY
   	 * Changes x
   	 */
   	public void setX(int num) {
   		 x = num;
   	}

/**
	 * FOR TESTING PURPOSES ONLY
	 * Returns y
	 * @return double y coordinate of saucer
	 */
	public double getY() {
		return y;
	}
	
	/**
   	 * FOR TESTING PURPOSES ONLY
   	 * Changes y
   	 */
   	public void setY(int num) {
   		y = num;
   	}
}