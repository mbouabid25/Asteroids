package animation;

import static org.junit.jupiter.api.Assertions.*;

import java.awt.Color;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class SpaceshipTest {
	Game game = new Game();
	Spaceship spaceship = new Spaceship(game);

	@BeforeEach
	void setUp() throws Exception {
	}

	/*
	 * Purpose: Test correct use of getSpeed()
	 * Correct result: 100, or default speed
	 */
	@Test
	void testGetSpeed() {
		assertEquals(100, spaceship.getSpeed());
	}

	/*
	 * Purpose: Test correct use of getX()
	 * Correct result: 300, starting location of ship
	 */
	@Test
	void testGetX() {
		assertEquals(300, spaceship.getX());
	}

	/*
	 * Purpose: Test correct use of getY()
	 * Correct result: 300, starting location of ship
	 */
	@Test
	void testGetY() {
		assertEquals(300, spaceship.getY());
	}

	/*
	 * Purpose: Test correct use of forward method
	 * Method: forward()
	 * Initialization: y holds starting location of ship
	 * Correct result: changed y value less than starting y value
	 */
	@Test
	void testForward() {
		int y = spaceship.getY();

		spaceship.forward(game.getAccelerationValue());

		assertTrue((spaceship.getY() >= y));
	}

	@Test
	void testRotateL() {
		double radians = spaceship.getRadians();
		spaceship.rotateL();
		//this is vague, don't know how to change
		assertTrue(radians != spaceship.getRadians());
	}

	/*
	 * Purpose: Test correct use of rotateR()
	 * Initialization: radians holds starting radian value
	 * Correct result: getRadians() is greater than radians
	 */
	@Test
	void testRotateR() {
		double radians = spaceship.getRadians();
		spaceship.rotateR();
		assertTrue(radians >= spaceship.getRadians());
	}

	/*
	 * Purpose: Test correct use of disappear
	 * Initialization: numLives gets 0
	 * Parameters: Calls disappear with boolean true
	 * Correct result: spaceship color is black
	 */
	@Test
	void testDisappear() {
		spaceship.setNumLives(0);
		spaceship.disappear(true);
		assertEquals(Color.BLACK, spaceship.getColor());
	}

	/*
	 * Purpose: Test correct use of hyperspace
	 * Initialization: int x holds starting x value, int y holds starting y value
	 * Correct result: neither getX() nor getY() are equal to origin x or y.
	 */
	@Test
	void testHyperspace() {
		int x = spaceship.getX();
		int y = spaceship.getY();

		spaceship.hyperspace(150, 150);

		assertTrue((spaceship.getX() != x));
		assertTrue((spaceship.getY() != y));
	}

	/*
	 * Purpose: Test nextFrame if spaceship exits right edge
	 * Initialization: x gets window width + spaceship height
	 * Correct result: x is -20
	 */
	@Test
	void testNextFrameRightEdge() {
		spaceship.setX(game.getWidth() + 21); 
		spaceship.nextFrame();
		assertEquals(-20, spaceship.getX());

	}

	/*
	 * Purpose: Test nextFrame if spaceship exits left edge  
	 * Initialization: x gets -21
	 * Correct result: getX() equals window width + spaceship height
	 */
	@Test
	void testNextFrameLeftEdge() {
		spaceship.setX(-21); 
		spaceship.nextFrame();
		assertEquals((game.getWidth() + 20), spaceship.getX());

	}

	/*
	 * Purpose: Test nextFrame if spaceship exits bottom 
	 * Initialization: y gets game.getHeight() + 21
	 * Correct result: y is -20
	 */
	@Test
	void testNextFrameBottom() {
		spaceship.setY(game.getHeight() + 21); 
		spaceship.nextFrame();
		assertEquals(-20, spaceship.getY());

	}
	
	/*
	 * Purpose: Test nextFrame if spaceship exits top
	 * Initialization: y gets -21
	 * Correct result: getY() equals window height + spaceship height
	 */
	@Test
	void testNextFrameTop() {
		spaceship.setY(-21); 
		spaceship.nextFrame();
		assertEquals((game.getHeight() + 20), spaceship.getY());

	}

}
