package animation;

import java.awt.*; //needed for graphics
import java.awt.event.*; //needed for event handling
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

import javax.swing.ImageIcon;
import javax.swing.JFrame;


/**
 * Game class creates Spaceship, Asteroids, Saucers and Bullets.
 * Runs the animation using these objects, levels up,
 * Checks for collisions, calculates score and saves high scores. 
 * @author Rhitom Mishra, Marwa Bouabid and Amna Shahid Majeed
 *
 */
@SuppressWarnings("serial")
public class Game extends AbstractAnimation implements Runnable, KeyListener {

	    // Declaring the state object
	    static STATE State = STATE.MENU;

	    // The width of the window, in pixels.
	    public final static int WINDOW_WIDTH = 600;

	    // The height of the window, in pixels.
	    public final static int WINDOW_HEIGHT = 600;
	    
	    // Frames counter
	    public static int frames;
	    
	    // The level we're at
	    public static int level;

	    // User's score
	    public static int score;
	    
	    //User's lives
	    private ArrayList<String> lives = new ArrayList<String>(Arrays.asList("* ", " * ", " * ", " * ", " * "));
	    
	    //isDead
	    private static boolean isDead = false;

	    // Is the ship in the frame
	    private static boolean shipInBounds; 
	    
	    // Max number of asteroids for current level
	    private static int MAX_ASTEROIDS;

	    // Max number of saucers for current level
	    private static int MAX_SAUCERS;

	    // Max acceleration of the saucer
	    private final double MAX_ACCELERATION = 0.85;

	    // Acceleration strength of the saucer
	    private final double THROTTLE_STRENGTH = 0.015;

	    // Speed of the bullets shot by the saucers
	    public static double SAUCER_BULLET_SPEED;

	    // Speed of the bullets shot by the ship
	    public static double SHIP_BULLET_SPEED;

	    // Number of bullets shot by the saucers
	    private static int SAUCER_SHOOT_AMOUNT;

	    // Is the ship accelerating
	    private boolean accelerating;

	    // The ship's acceleration
	    private double accelerationValue;

	    // Is the ship rotating towards the right
	    private boolean rotatingR;

	    // Is the ship rotating towards the left
	    private boolean rotatingL;

	    // ArrayList of bullets for saucers
	    public static ArrayList<Bullet> saucerBullets = new ArrayList<Bullet>();

	    // Bullet initialized for saucer
	    private static Bullet saucerBullet;

	    // The state of the game, are we playing or on menu/exit screen
	    enum STATE {
	        MENU, GAME, EXIT, PAUSE
	    }

	    // ArrayList of saucer
	    private static ArrayList<Saucer> saucers = new ArrayList<Saucer>();

	    // ArrayList of bullets
	    public static ArrayList<Bullet> shipBullets = new ArrayList<Bullet>();

	    // ArrayList of asteroids
	    private ArrayList<Asteroid> asteroids = new ArrayList<Asteroid>();

	    // One Spaceship
	    private static Spaceship spaceship;

	    // Bullet initialized
	    private Bullet newBullet;

	    // Checks if the game is moving
	    private boolean moving;

	    // Checks if bullet is fired
	    private boolean bulletState;

	    // Used to buffer shoot speed preventing "rapid fire"
	    private int shipShootingFrameCounter;
	    
	    // ArrayList to hold our scores
	    private static ArrayList<Integer> scores = new ArrayList<Integer>();
	    
	    // The highest score
	    public static int highScore = 0;
	    
	    // Number of collided asteroids
	    private int numCollidedAstr;
	    
	    // Number of collided saucers
	    int numCollidedSauc;
	    
	    /**
	     * Initializes instance variables using initializeGame()
	     */
	    public Game() {
	        // Initializing all game variables we need before we start
	        this.initializeGame();
	    }
	    
	    /**
	     * Initializing game variables, makes it easier to replay the game without
	     * closing GUI by simply reinitializing the game
	     */
	    public void initializeGame() {

	        MAX_ASTEROIDS = 5;
	        MAX_SAUCERS = 5;
	        SAUCER_BULLET_SPEED = 3.0;
	        SHIP_BULLET_SPEED = 7.0;
	        SAUCER_SHOOT_AMOUNT = 3;
	        shipInBounds = true;
	        frames = 10;
	        score = 0;

	        accelerating = false;
	        accelerationValue = 0;
	        rotatingR = false;
	        rotatingL = false;
	        moving = true;

	        level = 1;
	        score = 0;
	        numCollidedAstr = 0;
	        numCollidedSauc = 0;
	        isDead = false;

	        bulletState = false;
	        shipShootingFrameCounter = 0;

	        saucers = new ArrayList<Saucer>();
	        shipBullets = new ArrayList<Bullet>();
	        saucerBullets = new ArrayList<Bullet>();
	        asteroids = new ArrayList<Asteroid>();
	        spaceship = new Spaceship(this);

	        // Allow the game to receive key input
	        setFocusable(true);
	        addKeyListener(this);
	    }

	    
	    
	    /**
	     * @return the score
	     */
	    public static int getScore() {
	        return score;
	    }

	    /**
	     * Returns list highest scores
	     * @return user's highest scores
	     */
	    public static Integer getHighScore() {
	        return Collections.max(scores);
	    }
	    
    
    /**
     * Adds to user's score by param addScore amount
     * @param addScore amount to increase score
     */
    public static void score(int addScore) {
        score += addScore;
    }
    
    /**
     * Counts the number of collided asteroids
     * returns the number of collided asteroids
     * @return numCollidedAstr
     */
    private int numCollidedAstr() {
    	 for (int i = 0; i < asteroids.size(); i++) {
             if (asteroids.get(i).collided) {
                 numCollidedAstr += 1;
             }

    	 }
    	 
    	 return numCollidedAstr;
    }
    
    /**
     * Counts the number of collided saucers
     * returns the number of collided saucers
     * @return numCollidedSauc
     */
    private int numCollidedSauc() {
   	 int numCollidedSauc = 0;
   	 for (int i = 0; i < asteroids.size(); i++) {
            if (asteroids.get(i).collided) {
                numCollidedSauc += 1;
            }

   	 }
   	 
   	 return numCollidedSauc;
   }
    
    /**
     * Increases level when preconditions are met
     * then, initializes difficulty control variables for the next level
     */
    public void levels() {
        //checking to see if the number of collided asteroids and
        //number of collided saucers reach our threshold for end of level
        if (numCollidedAstr() == MAX_ASTEROIDS
                && numCollidedSauc() == MAX_SAUCERS) {
        	//if so, initialize difficulty variables for next level
            level += 1;
            asteroids = new ArrayList<Asteroid>();
            saucers = new ArrayList<Saucer>();
            // shipBullets = new ArrayList<Bullet>();
            MAX_ASTEROIDS += 5;
            MAX_SAUCERS += 3;
            // Double amount of bullets shot every 3 levels
            SAUCER_SHOOT_AMOUNT *= (level / 3 + 1);
            Asteroid.moveAmount += 1;
            Saucer.moveAmount += 1;
            numCollidedAstr = 0;
            numCollidedSauc = 0;
        }

    }

    /**
     * Paint the background image.
     * 
     * @param g the graphic context to draw on
     */
    private static void paintBackground(Graphics g) {
        // Saving space for the bg image
        Image bg = null;

        // Importing the bg image
        Toolkit.getDefaultToolkit().createImage("asteroids_bg.jpg");
        bg = (new ImageIcon("asteroids_bg.jpg")).getImage();

        // DRawing the image in the bg
        g.drawImage(bg, 0, 0, null);

    }

    /**
     * Paint the spaceship object.
     * 
     * @param g the graphic context to draw on
     */
    private void paintSpaceship(Graphics g) {
        // ...paints spaceship
        spaceship.paint((Graphics2D) g);
    }

    /**
     * Paint the asteroid objects.
     * 
     * @param g the graphic context to draw on
     */
    private void paintAsteroids(Graphics g) {
        // Painting asteroids in random locations
        for (int i = 0; i < asteroids.size(); i++) {
            if (!(asteroids.get(i).collided)) {
                asteroids.get(i).paint((Graphics2D) g);

            }
        }
    }

    /**
     * Paint the saucer objects.
     * 
     * @param g the graphic context to draw on
     */
    private void paintSaucers(Graphics g) {
        // Painting saucers in random locations, going in random directions
        for (int i = 0; i < saucers.size(); i++) {
            if (!(saucers.get(i).collided)) {
                saucers.get(i).paint((Graphics2D) g);
            }
        }
    }

    /**
     * Paint the bullet objects shot by the spaceship.
     * 
     * @param g the graphic context to draw on
     */
    private void paintBullets(Graphics g) {
        // Painting bullets using location of spaceship
        for (int i = 0; i < shipBullets.size(); i++) {
            if ((shipBullets.get(i) != null)
                    && !(shipBullets.get(i).collided)) {
                shipBullets.get(i).paint((Graphics2D) g);
            }
        }
    }

    /**
     * Paint the bullet objects shot by the saucers.
     * 
     * @param g the graphic context to draw on
     */
    private void paintSaucerBullets(Graphics g) {
        // Painting bullets using location of spaceship
        for (int y = 0; y < saucerBullets.size(); y++) {
            if (!(saucerBullets.get(y).collided)) {
                // Painting bullets using location of spaceship
                saucerBullets.get(y).paint((Graphics2D) g);
            }
        }
    }

    /**
     * Paint the animation by painting the objects in the animation.
     * 
     * @param g the graphic context to draw on
     */
    public void paintComponent(Graphics g) {
        // calls paintComponent through superclass
        super.paintComponent(g);
        paintBackground(g);

        // If we're playing, asteroids, spaceship, and saucers get painted
        if (State == STATE.GAME) {
            // Painting the score and level we're at
            scoreDisplay.paint(g);
            // If the ship is not dead yet...
            if (!spaceship.collided) {
                paintSpaceship(g);
                paintAsteroids(g);
                paintSaucers(g);
                paintBullets(g);
                paintSaucerBullets(g);
                // If the ship dies...
            } else {
                // Exit the game
                State = STATE.EXIT;
            }
        }
        // On the exit screen...
        if (State == STATE.EXIT) {
            // Painting the score and the exit screen
            scoreDisplay.paint(g);
            Exit.paint(g);
        }
        // If not playing, painting Menu
        if (State == STATE.MENU) {
            Menu.paint(g);
        }
        // On the pause screen...
        if (State == STATE.PAUSE) {
        	super.paintComponent(g);
            paintBackground(g);
            scoreDisplay.paint(g);
        	paintSpaceship(g);
            paintAsteroids(g);
            paintSaucers(g);
            paintBullets(g);
            paintSaucerBullets(g);
            Pause.paint(g);
        }
    }

    /**
     * This is called on the downward action when the user presses a key. It
     * notifies the animated ball about presses of up arrow, right arrow, left
     * arrow, and the space bar. All other keys are ignored.
     * 
     * @param e information about the key pressed
     */
    public void keyPressed(KeyEvent e) {
    	
        // Instance variable holds value of key pressed
        int key = e.getKeyCode();

        switch (key) {
        // If up key is pressed, accelerates forward
        case KeyEvent.VK_UP:
        	// If in pause, don't do anything
        	if (State == STATE.PAUSE) {
        		return;
        	}
            accelerating = true;
            break;

        // If right key is pressed, rotates clockwise
        case KeyEvent.VK_RIGHT:
        	// If in pause, don't do anything
        	if (State == STATE.PAUSE) {
        		return;
        	}
            // Ensuring to maintain acceleration
            rotatingR = true;
            spaceship.rotateR();
            break;

        // If left key is pressed, rotates counterclockwise
        case KeyEvent.VK_LEFT:
        	// If in pause, don't do anything
        	if (State == STATE.PAUSE) {
        		return;
        	}
            // Ensuring to maintain acceleration
            rotatingL = true;
            spaceship.rotateL();
            break;

        // If down key is pressed, accelerates backward
        case KeyEvent.VK_DOWN:
            // accelerating = true;
            // TODO: figure out if ship should move backwards or not
            break;

        // Pressing Enter key sends spaceship into hyperspace
        case KeyEvent.VK_SHIFT:
        	// If in pause, don't do anything
        	if (State == STATE.PAUSE) {
        		return;
        	}
            spaceship.hyperspace(WINDOW_WIDTH, WINDOW_HEIGHT);
            break;

        // If space key is pressed, creates and shoots a bullet
        case KeyEvent.VK_SPACE:
        	// If in pause, don't do anything
        	if (State == STATE.PAUSE) {
        		return;
        	}
            bulletState = true;
            break;

        // Enter menu from exit or enter game from menu
        case KeyEvent.VK_ENTER:
        	// If in pause, don't do anything
        	if (State == STATE.PAUSE) {
        		return;
        	}
        	// If in menu, start game!
            if (State == STATE.MENU) {
                State = STATE.GAME;
            }
            // If in exit, return to menu!
            if (State == STATE.EXIT) {
                State = STATE.MENU;
                this.initializeGame();
            }
            break;
        case KeyEvent.VK_ESCAPE:
        	// If in game state, enter pause state
        	if (State == STATE.GAME) {
        		State = STATE.PAUSE;
        		break;
        	}
        	// If in pause state, enter game state
        	if (State == STATE.PAUSE) {
        		State = STATE.GAME;
        		break;
        	} 
        	
        	break;
        default:
            // Ignore all other keys
        }
    }

    /**
     * This is called when the user releases space after pressing it. It stops
     * bullets shooting from the spaceship
     * 
     * @param e information about the key released
     */
    public void keyReleased(KeyEvent e) {
        // Stop shooting when space is released
        if (e.getKeyCode() == KeyEvent.VK_SPACE) {
            bulletState = false;
        }
        // Stop accelerating when forward is released
        if (e.getKeyCode() == KeyEvent.VK_UP) {
            accelerating = false;
        }
        // Stop accelerating when backward is released
        if (e.getKeyCode() == KeyEvent.VK_DOWN) {
            accelerating = false;
        }
        // Stops rotational acceleration when left is released
        if (e.getKeyCode() == KeyEvent.VK_LEFT) {
            rotatingL = false;
        }
        // Stops rotational acceleration when left is released
        if (e.getKeyCode() == KeyEvent.VK_RIGHT) {
            rotatingR = false;
        }
    }

    @Override
    /**
     * Called when the user presses and releases a key without moving the mouse
     * in between. Does nothing.
     * 
     * @param e information about the key typed.
     */
    public void keyTyped(KeyEvent e) {
        // Nothing to do
    }

    /**
     * Calculates value at which Spaceship should accelerate when forward method
     * is called.
     * 
     * @param accelerating
     * @return rate of acceleration
     */
    public double accelerate(boolean accelerating) {
    	// our move acceleration rate is also proportional to the THROTTLE_STRENGTH instance variable
    	// which describes how quickly we accelerate
        if (accelerating) {
        	// precautions taken to prevent infinite acceleration (maxed out by MAX_ACCELERATION)
            accelerationValue = Math.min(accelerationValue + THROTTLE_STRENGTH, MAX_ACCELERATION);
        } 
        else {
        	// precautions taken to prevent negative acceleration (min acceleration of zero)
            accelerationValue = Math.max(accelerationValue - THROTTLE_STRENGTH, 0);
        }
        return accelerationValue;
    }

    /**
     * Repaints spaceship, Moves Spaceship forward if accelerating, Rotates
     * Spaceship
     */
    private void updateShip() {
        if (!spaceship.collided) {
            // Paints spaceship in each frame
            spaceship.nextFrame();
        }

        // If spaceship is accelerating, calls accelerate to move ship forward
        if (accelerating) {
            spaceship.forward(accelerate(true));
        } 
        // If spaceship is not accelerating, call accelerate to slow ship momentum
        else {
            spaceship.forward(accelerate(false));
        }

        // If spaceship is rotating right, calls rotateR()
        if (rotatingR) {
            spaceship.rotateR();
        } else if (rotatingL) { // If rotating left, calls rotateL()
            spaceship.rotateL();
        }
    }

    /**
     * Repaints asteroids and adds new asteroids
     */
    private void updateAsteroids() {
        // Adds an asteroid every 175 frames
        if (asteroids.size() < MAX_ASTEROIDS && frames % 175 == 0) {
            Asteroid asteroid = new Asteroid(this);
            asteroids.add(asteroid);
        }

        // Updates asteroids on the next frame
        for (int i = 0; i < asteroids.size(); i++) {
            if (!asteroids.get(i).collided) {
                asteroids.get(i).nextFrame();

            }
        }
    }

    /**
     * Repaints saucers and adds new saucers
     */
    private void updateSaucers() {
        // Adds a saucer every 350 frames
        if (saucers.size() < MAX_SAUCERS && frames % 350 == 0) {
            Saucer saucer = new Saucer(this);
            saucers.add(saucer);
        }

        // Updates saucers on the next frame
        for (int i = 0; i < saucers.size(); i++) {
            if (!saucers.get(i).collided) {
                saucers.get(i).nextFrame();
            }
        }

    }

    // Checks for ship-asteroid, ship-saucer, and ship-bullet collisions
    private void checkShipCollisions() {
        // Checks for ship-asteroid collisions
        for (int i = 0; i < asteroids.size(); i++) {
            if (Spaceship.checkShipAsteroidCollision(asteroids.get(i), spaceship,
                    shipInBounds) && !(asteroids.get(i).collided)) {
                spaceship.collided = true;
            }
        }

        // Checks for ship-saucer collisions
        for (int i = 0; i < saucers.size(); i++) {
            if (Spaceship.checkShipSaucerCollision(saucers.get(i), spaceship) && !(saucers.get(i).collided)) {
                spaceship.collided = true;
            }
        }

        // Checks for ship-bullet collisions
        for (int i = 0; i < saucerBullets.size(); i++) {
            if (Spaceship.checkShipBulletCollision(spaceship, saucerBullets.get(i),
                    shipInBounds) && !(saucerBullets.get(i).collided)) {
                spaceship.collided = true;
                saucerBullets.get(i).collided = true;
            }

        }
    }

    /**
     * Checks for bullet-asteroid collisions Bullet disappears if it hits
     * asteroid
     */
    // Checks for asteroid-bullet collisions 
    private void checkAsteroidsCollisions() {
        // Checks if there is a bullet object
//        if (!(newBullet == null)) {
            // Checks for bullet-asteroid collisions
            // Bullet disappears if it hits an object
            for (int i = 0; i < asteroids.size(); i++) {
                for (int y = 0; y < shipBullets.size(); y++) {
                    if (!(asteroids.get(i).collided) && !(shipBullets.get(y).collided)) {
                        if (Asteroid.checkAsteroidsBulletCollision(asteroids.get(i), shipBullets.get(y))) {
                            asteroids.get(i).collided = true;
                            shipBullets.get(y).collided = true;
                            score(10);
                        }
                    }
                }
                // Checks for saucer-asteroid collisions
                for (int y = 0; y < saucerBullets.size(); y++) {
                    if (!(asteroids.get(i).collided)
                            && !(saucerBullets.get(y).collided)) {
                        if (Asteroid.checkAsteroidsBulletCollision(asteroids.get(i), saucerBullets.get(y))) {
                            asteroids.get(i).collided = true;
                            saucerBullets.get(y).collided = true;
                        }
                    }

                }
            }

//        }
    }

    /**
     * Checks for saucer-asteroid collisions and saucer-bullet collisions
     * Bullets disappear if they hit saucers
     */
    private void checkSaucersCollisions() {
        // Checks for saucer-asteroid collisions
        for (int i = 0; i < asteroids.size(); i++) {
            for (int y = 0; y < saucers.size(); y++) {
                if (!saucers.get(y).collided 
                		&& Saucer.checkAsteroidSaucerCollision(asteroids.get(i), saucers.get(y))) {
                    saucers.get(y).collided = true;
                }
            }
        }

        // Checks for bullet-saucer collisions
        // Bullet disappears if it hits an object
        for (int i = 0; i < saucers.size(); i++) {
            for (int y = 0; y < shipBullets.size(); y++) {
                if (!saucers.get(i).collided && !shipBullets.get(y).collided
                		&& Saucer.checkSaucersBulletCollision(saucers.get(i), shipBullets.get(y))) {
                	saucers.get(i).collided = true;
                	shipBullets.get(y).collided = true;
                	score(20);
                }
            }
        }
    }
    /**
     * Makes new bullets if bulletState is true
     */
    public void makeBullets() {
        if (bulletState && shipShootingFrameCounter % 3 == 0) {
            // Creating a bullet object using the location of the spaceship
            newBullet = new Bullet(this, (spaceship.getX() - 3),
                    (spaceship.getY() - 6), (spaceship.getRadians()));
            newBullet.setSpeed(SHIP_BULLET_SPEED);
            newBullet.setType("SHIP");
            // Adding to the array of bullets
            shipBullets.add(newBullet);
            bulletState = false;
        }

        for (int i = 0; i < shipBullets.size(); i++) {
            if (!isInBounds(shipBullets.get(i).getX(),
                    shipBullets.get(i).getY())) {
                shipBullets.remove(i);
            }
        }
        shipShootingFrameCounter++;
    }
    
    /**
     * Checks if ship is within window boundaries
     * @param i_x x value of ship's location
     * @param i_y y value of ship's location
     * @return true if in bounds, else false
     */
    public static boolean isInBounds(double i_x, double i_y) {
        return i_x >= 0 && i_x <= WINDOW_WIDTH && i_y >= 0
                && i_y <= WINDOW_HEIGHT;
    }

    private static double radiansBetweenTwoPoints(double x1, double y1, double x2,
            double y2) {
        return Math.atan2(y2 - y1, x2 - x1) + Math.PI / 2;
    }

    /**
     * Makes saucers shoot bullets by making and adding bullets to the saucers'
     * bullet array. Saucers shoot in two different directions.
     */
    private void saucersMakeBullets() {
        for (int i = 0; i < SAUCER_SHOOT_AMOUNT; i++) {
            if (frames % (120 + 15 * i) == 0) {
                if (saucers.size() != 0) {
                    for (int y = 0; y < saucers.size(); y++) {
                    	//makes sure saucers can only shoot when in bounds
                        if (isInBounds(saucers.get(y).getX(),
                                saucers.get(y).getY())
                                && !(saucers.get(y).collided)) {
                            // Creating a bullet object using the location of
                            // the saucer
                            Bullet saucerBullet = new Bullet(this,
                                    (saucers.get(y).getX() - 3),
                                    (saucers.get(y).getY() - 6),
                                    radiansBetweenTwoPoints(
                                            saucers.get(y).getX(),
                                            saucers.get(y).getY(),
                                            spaceship.getX(),
                                            spaceship.getY()));
                            saucerBullet.setSpeed(SAUCER_BULLET_SPEED);
                            saucerBullet.setType("SAUCER");
                            // Adding to the array of bullets (if a lot of
                            // bullets are shot at once)
                            saucerBullets.add(saucerBullet);
                        }
                    }

                }
            }
        }
    }
    
    /**
	 * Returns x
	 * @return int x coordinate of Spaceship
	 */
	public static int shipGetX() {
		return spaceship.getX();
	}
	
	/**
	 * Returns y
	 * @return int y coordinate of Spaceship
	 */
	public static int shipGetY() {
		return spaceship.getY();
	}
	
	/**
     * FOR TESTING PURPOSES ONLY
     * @return acceleration value
     */
    public double getAccelerationValue() {
        return accelerationValue;
    }

    /**
     * FOR TESTING PURPOSES ONLY 
     * sets state to game
     */
    public static void setState() {
        State = STATE.GAME;
    }
    
    /**
     * FOR TESTING PURPOSESES ONLY
     * @return bulletState
     */
    public boolean getBulletState() {
        return bulletState;
    }
    
    public boolean isAccelerating() {
        return accelerating;
    }
   
    public boolean isRotatingR() {
        return rotatingR;
    }
    
    public boolean isRotatingL() {
        return rotatingL;
    }
    
    /**
     * FOR TESTING PURPOSESES ONLY
     * @return bulletState
     */
    public static ArrayList<Integer> getScoreList() {
        return scores;
    }
    
    /**
     * FOR TESTING PURPOSESES ONLY
     * @return bulletState
     */
    public ArrayList<Asteroid> getAsteroids() {
        return asteroids;
    }
    
    /**
     * FOR TESTING PURPOSESES ONLY
     * @return bulletState
     */
    public ArrayList<Saucer> getSaucers() {
        return saucers;
    }

    @Override
    /**
     * Updates the animated objects for the next frame of the animation and
     * repaints the window.
     */
    protected void nextFrame() {
        // Only taking action if we're playing
        if (State == STATE.GAME) {
            if (spaceship.getX() <= WINDOW_WIDTH + Spaceship.SPACESHIP_WIDTH
                    && spaceship.getY() <= WINDOW_HEIGHT
                            + Spaceship.SPACESHIP_HEIGHT) {
                shipInBounds = true;
            } else {
                shipInBounds = false;
            }

            if (moving) {

                levels();
                // Repaints all existing objects
                updateShip();
                updateAsteroids(); // adds new Asteroids every 175 frames
                updateSaucers(); // adds new Saucers every 350 frames

                // Checks for collisions between objects
                checkShipCollisions();
                checkSaucersCollisions();
                checkAsteroidsCollisions();

                // Creates and shoots Bullets
                makeBullets();
                saucersMakeBullets();
                Spaceship.shoot();
                Saucer.shoot();
            }
            // Updates frames
            frames++;
            // Repaints the animations
            repaint();
        }
    }

    /**
     * Creates animation window, creates animation, Adds animation to window and
     * displays window Starts animation with Menu
     * 
     * @param args
     */
    public static void main(String[] args) {
        // JFrame is the class for a window. Create the window,
        // set the window's title and its size.
        JFrame f = new JFrame();
        f.setTitle("Asteroids Game!");
        f.setSize(new Dimension(WINDOW_WIDTH, WINDOW_HEIGHT));

        // This says that when the user closes the window, the
        // entire program should exit.
        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Create the animation.
        Game newGame = new Game();

        // Add the animation to the window
        Container contentPane = f.getContentPane();
        contentPane.add(newGame, BorderLayout.CENTER);

        // Display the window.
        f.setVisible(true);

        // Start the animation
        newGame.start();

    }
    
    

}
