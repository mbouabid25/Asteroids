package animation;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

//Tests the methods in Bullet class
class BulletTest {

    //initializes required variables 
    Game game;
    Bullet bullet;
    double x;
    double y;
    double radians;
    double speed;
    double testValueX;
    double testValueY;
    
    //sets values of variables 
    @BeforeEach
    void setUp() throws Exception 
    {
        game = new Game();
        x = 100.4;
        y = 150.9;
        radians = (Math.PI)/3;
        bullet = new Bullet(game, x, y, radians);
    }

    @Test
    // Purpose: Checking whether correct speed of bullet is returned
    // Method: getSpeed() in Bullet class 
    // Parameters: none
    // Required Initialization: set a speed of 15.7
    // Correct result = 15.7
    void testGetSpeed() {
        bullet.setSpeed(15.7);
        assertEquals(15.7, bullet.getSpeed());
    }
    
    @Test
    // Purpose: Checking whether correct x-coordinate of bullet is returned
    // Method: getX() in Bullet class 
    // Parameters: none
    // Required Initialization: x value is set to 100.4 in setUp()
    // Correct result = 100.4
    void testGetX() {
        assertEquals(100.4, bullet.getX());
    }
    
    
    @Test
    // Purpose: Checking whether correct y-coordinate of bullet is returned
    // Method: getY() in Bullet class 
    // Parameters: none
    // Required Initialization: y value is set to 150.9 in setUp()
    // Correct result = 150.9
    void testGetY() {
        assertEquals(150.9, bullet.getY());
    }
    
    
    @Test
    // Purpose: Checking whether correct X value of bullet is set 
    //           in the next frame
    // Method: nextFrame() in Bullet class 
    // Parameters: none
    // Required Initialization: x value is set to 100.4 and radians is 
    //                          set to PI/3 in setUp()
    // Correct result = 113.9965988394157
    void testNextFrameX() {
        testValueX = x + (Math.sin(radians) * speed);
        
        System.out.println(testValueX);
        
        bullet.nextFrame();
        assertEquals(testValueX, bullet.getX());
        //fail("Not yet implemented");
    }
    
    @Test
    // Purpose: Checking whether correct y value of bullet is set 
    //           in the next frame
    // Method: nextFrame() in Bullet class 
    // Parameters: none
    // Required Initialization: y value is set to 150.9 and radians is 
    //                          set to PI/3 in setUp()
    // Correct result = 
    void testNextFrameY() {
        testValueY = y - (Math.cos(radians) * speed);
        
        System.out.println(testValueY);
        
        bullet.nextFrame();
        assertEquals(testValueY, bullet.getY());
        //fail("Not yet implemented");
    }
    
    
    @Test
    // Purpose: Checking whether correct speed of bullet is set
    // Method: setSpeed() in Bullet class 
    // Parameters: newSpeed
    // Required Initialization: set a speed of 25
    // Correct result = bullet's new speed is 25
    void testSetSpeed() {
        bullet.setSpeed(25);
        assertEquals(25, bullet.getSpeed());
    }
      
    
    @Test
    // Purpose: Checking whether correct x coordinate of bullet is set
    // Method: setType() in Bullet class 
    // Parameters: string (for which object the bullet is for)
    // Required Initialization: string of "SHIP"
    // Correct result = bullet's new x-coordinate is 101.4
    void testSetType() {
        bullet.setType("SHIP");
        assertEquals(101.4, bullet.getX());
    }
        
    @Test
    // Purpose: Checking whether correct x coordinate of bullet is set
    // Method: setCoord(x, y) in Bullet class 
    // Parameters: x, y
    // Required Initialization: x coordinate argument of 200.2
    // Correct result = bullet's new x-coordinate is 200.2
    void testSetCoordX() {
        x = 200.2;
        bullet.setCoord(x, y);
        assertEquals(200.2, bullet.getX());
    }     
    
    @Test
    // Purpose: Checking whether correct y coordinate of bullet is set
    // Method: setCoord(x, y) in Bullet class 
    // Parameters: x, y
    // Required Initialization: y coordinate argument of 290
    // Correct result = bullet's new y-coordinate is 290
    void testSetCoordY() {
        y = 290;
        bullet.setCoord(x, y);
        assertEquals(290, bullet.getY());
    }     
}