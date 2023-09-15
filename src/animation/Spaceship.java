package animation;

import java.awt.Graphics2D;
import java.awt.Color;
import java.awt.Polygon;



import java.awt.Shape;
import java.awt.geom.AffineTransform;
import java.util.Random;

/**
 * Creates animated object Spaceship, which moves through the arrow keys
 * Includes rotate and hyperspace functions, which turn the 
 * Spaceship and move it to random locations on the map respectively.
 * 
 * @author Rhitom Mishra and Marwa Bouabid
 */
public class Spaceship {

	//Animation that will contain Spaceship 
	private AbstractAnimation animation;

	//Instance variable Polygon to draw ship
	private Polygon spaceship;

	//Height of Spaceship
	public final static int SPACESHIP_HEIGHT = 20;

	//Width of Spaceship
	public final static int SPACESHIP_WIDTH = 10;

	//Color of Spaceship
	private Color shipColor = Color.YELLOW;

	//Number of Lives Spaceship has at start of game
	private int numLives = 5;

	//Speed of Spaceship's movement
	private int speed = 150;

	//Amount Spaceship moves at each change in Frame
	private int moveAmount = 10;

	//Degrees Spaceship will rotate, if given command
	double radians = 2 * Math.PI;

	//Starting x location of the Spaceship
	private int x = 300;

	//Starting y location of the Spaceship
	private int y = 300;

	//Boolean, determines if Spaceship has collided with another object
	public boolean collided = false; 
	
	/**
	 * Creates the animated object at a given location
	 * @param animation the animation object is part of
	 */
	public Spaceship(AbstractAnimation animation) {
		//Adds Spaceship to animation
		this.animation = animation;

		//Constructs object, adds points to make Shape
		spaceship = new Polygon();

		spaceship.addPoint(0, 0);
		spaceship.addPoint(0 - SPACESHIP_WIDTH/2, SPACESHIP_HEIGHT/2);
		spaceship.addPoint(0 - SPACESHIP_WIDTH/2, SPACESHIP_HEIGHT);
		spaceship.addPoint(SPACESHIP_WIDTH/2, SPACESHIP_HEIGHT);
		spaceship.addPoint(SPACESHIP_WIDTH/2, SPACESHIP_HEIGHT/2);

	}
	
	/**
	 * Draws spaceship at current location
	 * @param g the graphics context to draw on.
	 */
	public void paint(Graphics2D g) {
		g.setColor(shipColor);
		g.fill(getShape());
	}
	
	// Shoots bullets from spaceship using Spaceship's (x, y) location
    public static void shoot() {
        // shooting bullets using location of spaceship
        for (int i = 0; i < Game.shipBullets.size(); i++) {
            if ((Game.shipBullets.get(i) != null) && !(Game.shipBullets.get(i).collided)) {
            	Game.shipBullets.get(i).setSpeed(Game.SHIP_BULLET_SPEED);
            	Game. shipBullets.get(i).nextFrame();
            }
        }
    }
    
    /**
     * Check whether two objects collide. This tests whether their shapes
     * intersect.
     * 
     * @param asteroid: 	the first shape to test
     * @param spaceship: 	the second shape to test
     * @return true, if the shapes intersect
     */
    public static boolean checkShipAsteroidCollision(Asteroid asteroid, Spaceship spaceship, 
    		boolean inBounds) {
        return !asteroid.collided && (spaceship.getShape().intersects(asteroid.getShape().getBounds2D()))
                && inBounds;
    }
    
    /**
     * Check whether two objects collide. This tests whether their shapes
     * intersect.
     * 
     * @param saucer:		the first shape to test
     * @param spaceship: 	the second shape to test
     * @return true, if the shapes intersect
     */
    public static boolean checkShipSaucerCollision(Saucer saucer, Spaceship spaceship) {
        return !saucer.collided && (spaceship.getShape().intersects(saucer.getShape().getBounds2D()));
    }
    
    /**
     * Check whether two objects collide. This tests whether their shapes
     * intersect.
     * 
     * @param spaceship:   	the first shape to test
     * @param bullet:		the second shape to test
     * @return true, if the shapes intersect
     */
    public static boolean checkShipBulletCollision(Spaceship spaceship, Bullet saucerBullet, 
    		boolean inBounds) {
        return !saucerBullet.collided && (spaceship.getShape().intersects(saucerBullet.getShape().getBounds2D())) 
        		&& inBounds;
    }

	/**
	 * Returns spaceship shape, translated to given x/y values
	 * @return Spaceship Shape
	 */
	public Shape getShape() {
		//Captures movement and rotation of Shape
		AffineTransform at1 = new AffineTransform();

		//X and Y determine origin location of Shape
		at1.translate(x, y);

		at1.rotate(radians);

		//returns shape at translated and rotated location
		return at1.createTransformedShape(spaceship);
	}
	
	/**
	 * Returns speed
	 * @return Speed of Spaceship
	 */
	public int getSpeed() {
		return speed;
	}

	/**
	 * Returns x
	 * @return int x coordinate of Spaceship
	 */
	public int getX() {
		return x;
	}
	
	/**
	 * Returns y
	 * @return int y coordinate of Spaceship
	 */
	public int getY() {
		return y;
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
	 * Changes y
	 */
	public void setY(int num) {
		y = num;
	}
	
	/**
	 * FOR TESTING PURPOSES
	 * @return color of Spaceship
	 */
	public Color getColor() {
		return shipColor;
	}
	
	/**
	 * FOR TESTING PURPOSES
	 * sets numLives to 0
	 */
	public void setNumLives(int num) {
		numLives = num;
	}
	
	/**
	 * FOR TESTING PURPOSES
	 * returns radians
	 */
	public double getRadians() {
		return radians;
	}

	/**
	 * Moves Spaceship forward when the user clicks the up arrow button.
	 * sin(radians) is x displacement , cos(radians) is y displacement
	 */
	public void forward(double acceleration_value) {
				
		//changes x coordinate by displacement value
		x += Math.sin(radians) * moveAmount * acceleration_value;

		//changes y coordinate by displacement value
		y -= Math.cos(radians) * moveAmount * acceleration_value;

	}

	/**
	 * Rotates Spaceship by π/12 degrees to left 
	 */
	public void rotateL() {
		radians = (radians - Math.PI / 32) % (Math.PI * 2);
	}

	/**
	 * Rotates Spaceship by π/12 degrees to right
	 */
	public void rotateR() {
		radians = (radians + Math.PI / 32) % (Math.PI * 2);
	}

	/**
	 * If spaceship collides with object and numLives is 0, spaceship disappears
	 * @param boolean collided tells method if collision has occurred
	 */
	public void disappear(boolean collided) {
		if(collided && (numLives == 0)) {
			//TODO: There should be a more effective way to do this?
			shipColor = Color.BLACK;
		}
	}

	/**
	 * Moves spaceship to random locations on the map using x and y 
	 * @param width upper bound for x; width of window
	 * @param height upper bound for y; height of window
	 */
	public void hyperspace(int width, int height) {
		//Constructs Random object to use for generating random integer
		Random rand = new Random();
		//Assigns random values to x and y of Spaceship object
		x = rand.nextInt((width - SPACESHIP_WIDTH) + 1) + SPACESHIP_WIDTH;
		y = rand.nextInt((height - SPACESHIP_HEIGHT) + 1) + SPACESHIP_HEIGHT;
	}

	/**
	 * Moves the Spaceship a small amount. If it reaches the left, right, 
	 * top or bottom edge, it wraps to the other side.
	 */
	public void nextFrame() {
		// Check if ship is beyond the right edge of the window. If it is, wrap to other side
		if (x > animation.getWidth() + SPACESHIP_HEIGHT) {
			x = 0 - SPACESHIP_HEIGHT;
		}

		// Check if the left edge of the ship is beyond the left
		// edge of the window. If it is, wrap to other side
		else if (x < 0 - SPACESHIP_HEIGHT) {
			x = animation.getWidth() + SPACESHIP_HEIGHT;
		}

		// check if ship goes beyond bottom of window, if so, wrap to top
		if (y > animation.getHeight() + SPACESHIP_HEIGHT) {
			y = 0 - SPACESHIP_HEIGHT;
		}

		// check if bottom of ship goes beyond bottom
		//of window, if so, wrap to bottom
		else if (y < 0 - SPACESHIP_HEIGHT) {
			y = animation.getHeight() + SPACESHIP_HEIGHT;
		}
	}

}
