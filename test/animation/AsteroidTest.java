package animation;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.Random;

import org.junit.jupiter.api.Test;

class AsteroidTest {

	Game game = new Game();
	Asteroid asteroid = new Asteroid(game);
	
	/*
	 * Purpose: Test randomColorTest and making sure 
	 * it only returns either 0 or 1
	 * Initialization: randomColor, a randomly 
	 * generated integer between 0 and 2 exclusive
	 * Correct result: True for either 0 or 1
	 */
	@Test
	void randomColorTest() {
		int randomColor = Asteroid.setRandomColor();
		assertTrue(randomColor == 0 || randomColor == 1);
	}
	
	/*
	 * Purpose: Test randomColorTest and making sure 
	 * it can never returns something other than 0 and 1
	 * Initialization: rand, a randomly generated 
	 * integer between min = 3 and max = 100
	 * generated integer between 0 and 2 exclusive
	 * Correct result: return value can never be a number between 3
	 * and 100
	 */
	@Test
	void randomColorTest2() {
		int max = 100;
		int min = 3;
		double rand = Math.random() * (max - min + 1) + min;
		assertFalse(Asteroid.setRandomColor() == rand );
	}

/*
 * Purpose: Test moveGenerator and making sure the
 * asteroids never move horizontally
 * Initialization: dir, the randomly generated angle
 * for the direction of the asteroid
 * Correct result: Dir can never be O
 */
@Test
void moveGeneratorTest0() {
	double dir = Asteroid.randomMoveGenerator();
	assertFalse(dir == 0.0);
	}

/*
 * Purpose: Test moveGenerator and making sure the
 * asteroids never move vertically
 * Initialization: dir, the randomly generated angle
 * for the direction of the asteroid
 * Correct result: Dir can never be 90 degrees 
 */
@Test
void moveGeneratorTest90() {
	double dir = Asteroid.randomMoveGenerator();
	assertFalse(dir == Math.PI/2);
	}

/*
 * Purpose: Test moveGenerator and making sure the
 * asteroids never move horizontally
 * Initialization: dir, the randomly generated angle
 * for the direction of the asteroid
 * Correct result: Dir can never be 180 degrees 
 */
@Test
void moveGeneratorTest180() {
	double dir = Asteroid.randomMoveGenerator();
	assertFalse(dir == Math.PI);
	}

/*
 * Purpose: Test moveGenerator and making sure the
 * asteroids never move vertically
 * Initialization: dir, the randomly generated angle
 * for the direction of the asteroid
 * Correct result: Dir can never be 270 degrees 
 */
@Test
void moveGeneratorTest270() {
	double dir = Asteroid.randomMoveGenerator();
	assertFalse(dir == 3*Math.PI/2);
	}

/*
 * Purpose: Test moveGenerator and making sure the
 * asteroids never move horizontally
 * Initialization: dir, the randomly generated angle
 * for the direction of the asteroid
 * Correct result: Dir can never be 360 degrees 
 */
@Test
void moveGeneratorTest360() {
	double dir = Asteroid.randomMoveGenerator();
	assertFalse(dir == 2*Math.PI);
	}

/*
 * Purpose: Test nextFrame if asteroid exits right edge
 * Initialization: x gets window width + 90
 * Correct result: x is -32
 */
@Test
void testNextFrameRightEdgeX() {
	asteroid.setX(game.getWidth() + 90);
	asteroid.nextFrame();
	assertEquals(-32, asteroid.getX());
	}

/*
 * Purpose: Test nextFrame if asteroid exits right edge
 * Initialization: x gets -35
 * Correct result: x is 32 
 */
@Test
void testNextFrameLeftEdgeX() {
	asteroid.setX(-35);
	asteroid.nextFrame();
	assertEquals(game.getWidth() + 32, asteroid.getX());
	}

/*
 * Purpose: Test nextFrame if asteroid exits right edge
 * Initialization: x gets window width + 35 (a little bit
 * more than the size of the asteroid
 * Correct result: x is -32
 */
@Test
void testNextFrameBottom() {
	asteroid.setY(635);
	asteroid.nextFrame();
	assertEquals(-32, asteroid.getY());
	}

/*
 * Purpose: Test nextFrame if asteroid exits top edge
 * Initialization: Y gets -35 (a little bit more than the 
 * asteroid's size outside of the frame)
 * Correct result: x is 32
 */
@Test
void testNextFrameTop() {
	asteroid.setY(-35);
	asteroid.nextFrame();
	assertEquals(32, asteroid.getY());
	}
/*
 * Purpose: Test randomCoordOnBorder and make sure 
 * it generates a coordinate on one of the borders of
 * our window.
 * Initialization: coord, a randomly 
 * generated coordinate pair 
 * Correct result: True for any coordinate pair on a
 * border of our window
 *           note: the asteroids are generated on the
 *           borders but slightly off frame to create
 *           a cleaner looking game (hence the reason
 *           for +- ASTEROID_SIZE/2)
 */

@Test
void randomCoordOnBorderTest() {
	ArrayList<Integer> coord = new ArrayList<Integer>();
	coord = Asteroid.randomCoordOnBorder();
	assertTrue(isOnBorder(coord.get(0), coord.get(1)));
}

boolean isOnBorder(int x, int y) {
	return (x == 0 - Asteroid.ASTEROID_SIZE/2 || x == Game.WINDOW_WIDTH + Asteroid.ASTEROID_SIZE/2) 
			|| (y == 0 - Asteroid.ASTEROID_SIZE/2 || y == Game.WINDOW_HEIGHT + Asteroid.ASTEROID_SIZE/2);
	}


}

