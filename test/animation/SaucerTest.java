package animation;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

class SaucerTest {
	
	Game game = new Game();
	Saucer saucer = new Saucer(game);

	/*
	 * Purpose: Test moveGenerator and making sure the
	 * saucers never move horizontally
	 * Initialization: dir, the randomly generated angle
	 * for the direction of the saucer
	 * Correct result: Dir can never be O
	 */
	@Test
	void moveGeneratorTest0() {
		double dir = Saucer.randomMoveGenerator();
		assertFalse(dir == 0.0);
		}
	/*
	 * Purpose: Test moveGenerator and making sure the
	 * saucers never move vertically
	 * Initialization: dir, the randomly generated angle
	 * for the direction of the saucer
	 * Correct result: Dir can never be 90 degrees 
	 */
	@Test
	void moveGeneratorTest90() {
		double dir = Saucer.randomMoveGenerator();
		assertFalse(dir == Math.PI/2);
		}
	
	/*
	 * Purpose: Test moveGenerator and making sure the
	 * saucers never move horizontally
	 * Initialization: dir, the randomly generated angle
	 * for the direction of the saucer
	 * Correct result: Dir can never be 180 degrees 
	 */
	@Test
	void moveGeneratorTest180() {
		double dir = Saucer.randomMoveGenerator();
		assertFalse(dir == Math.PI);
		}
	
	/*
	 * Purpose: Test moveGenerator and making sure the
	 * saucers never move vertically
	 * Initialization: dir, the randomly generated angle
	 * for the direction of the saucer
	 * Correct result: Dir can never be 270 degrees 
	 */
	@Test
	void moveGeneratorTest270() {
		double dir = Saucer.randomMoveGenerator();
		assertFalse(dir == 3*Math.PI/2);
		}
	
	/*
	 * Purpose: Test moveGenerator and making sure the
	 * saucerss never move horizontally
	 * Initialization: dir, the randomly generated angle
	 * for the direction of the saucer
	 * Correct result: Dir can never be 360 degrees 
	 */
	@Test
	void moveGeneratorTest360() {		
		double dir = Saucer.randomMoveGenerator();
		assertFalse(dir == 2*Math.PI);
		}
	/*
	 * Purpose: Test nextFrame if saucer exits right edge
	 * Initialization: x gets window width + spaceship height
	 * Correct result: x is -20
	 */
	@Test
	void testNextFrameRightEdgeX() {
		saucer.setX(game.getWidth() + 120);
		saucer.nextFrame();
		assertEquals(-50, saucer.getX());
	}
	
	/*
	 * Purpose: Test nextFrame if spaceship exits right edge
	 * Initialization: x gets window width + spaceship height
	 * Correct result: x is -20
	 */
	@Test
	void testNextFrameLeftEdgeX() {
		saucer.setX(-52);
		saucer.nextFrame();
		assertEquals(game.getWidth() + 50, saucer.getX());
	}
	
	/*
	 * Purpose: Test nextFrame if spaceship exits right edge
	 * Initialization: x gets window width + spaceship height
	 * Correct result: x is -20
	 */
	@Test
	void testNextFrameBottom() {
		saucer.setY(679);
		saucer.nextFrame();
		assertEquals(-28, saucer.getY());
	}
	
	/*
	 * Purpose: Test nextFrame if spaceship exits right edge
	 * Initialization: x gets window width + spaceship height
	 * Correct result: x is -20
	 */
	@Test
	void testNextFrameTop() {
		saucer.setY(-30);
		saucer.nextFrame();
		assertEquals(28, saucer.getY());
	}
}
