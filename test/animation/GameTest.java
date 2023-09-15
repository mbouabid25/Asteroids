package animation;
import static org.junit.jupiter.api.Assertions.*;

import java.awt.Button;
import java.awt.event.KeyEvent;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import animation.Asteroid;
import animation.Game;
import animation.Saucer;
import animation.Spaceship;

class GameTest {
    Game game = new Game();
    Spaceship ship = new Spaceship(game);

    @BeforeEach
    void setUp() throws Exception {
    }

    @Test
    void testNextFrame() {
        Game.State = Game.State.GAME;
        game.nextFrame();
        assertEquals(11, Game.frames);
    }

    @Test
    void testGetHighScore() {
        Game.getScoreList().add(10);
        Game.getScoreList().add(20);
        Game.getScoreList().add(50);
        
        assertEquals(50, Game.getHighScore());
    }

    @Test
    void testScore() {
       int addScore = 20;
       Game.score(addScore);
       assertEquals(Game.getScore(), 20);
    }

    @Test
    void testLevels() {
        for(int i = 0; i <= 5; i++) {
            Asteroid asteroid = new Asteroid(game);
            game.getAsteroids().add(asteroid);
            Saucer saucer = new Saucer(game);
            game.getSaucers().add(saucer);
            
        }

        for(int i = 0; i <= 5; i++) {
            game.getAsteroids().get(i).collided = true;
            game.getSaucers().get(i).collided = true;
        }

        game.levels();
        assertEquals(1, game.level); 
    }

    @Test
    void testKeyPressedUp() {
        Button a = new Button("testUp");
        KeyEvent up;
        up = new KeyEvent(a, 1, 20, 1, 10, 'a');
        up.setKeyCode(KeyEvent.VK_UP);
        game.keyPressed(up);
        assertTrue(game.isAccelerating());
    }
    
    @Test
    void testKeyPressedLeft() {
        Button a = new Button("testUp");
        KeyEvent left;
        left = new KeyEvent(a, 1, 20, 1, 10, 'a');
        left.setKeyCode(KeyEvent.VK_LEFT);
        game.keyPressed(left);
        assertTrue(game.isRotatingL());
    }
    
    @Test
    void testKeyPressedRight() {
        Button a = new Button("testUp");
        KeyEvent right;
        right = new KeyEvent(a, 1, 20, 1, 10, 'a');
        right.setKeyCode(KeyEvent.VK_RIGHT);
        game.keyPressed(right);
        assertTrue(game.isRotatingR());
    }
    
    @Test
    void testKeyPressedSpace() {
        Button a = new Button("testUp");
        KeyEvent shoot;
        shoot = new KeyEvent(a, 1, 20, 1, 10, 'a');
        shoot.setKeyCode(KeyEvent.VK_SPACE);
        game.keyPressed(shoot);
        assertTrue(game.getBulletState());
    }

    @Test
    void testKeyReleased() {
        fail("Not yet implemented"); // TODO
    }

    @Test
    void testAccelerateTrue() {
        game.accelerate(true);
        assertEquals(0.015, game.getAccelerationValue());
    }
    
    @Test
    void testAccelerateFalse() {
        game.accelerate(false);
        assertEquals(0.0, game.getAccelerationValue());
    }


    @Test
    void testMakeBullets() {
        game.makeBullets();
        assertFalse(game.getBulletState());
    }

    @Test
    void testIsInBounds() {
        int x = 550;
        int y = 550;
        assertTrue(Game.isInBounds(x, y));
    }

}
