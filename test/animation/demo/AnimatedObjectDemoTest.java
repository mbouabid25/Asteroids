package animation.demo;
import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class AnimatedObjectDemoTest {

    private AnimationDemo demo = new AnimationStub();
    private AnimatedObjectDemo obj = new AnimatedObjectDemo(demo);

    @BeforeEach
    void setUp() throws Exception {
    }

    @Test
    void testNextFrame() {
        // Test what happens when the ball is at the left edge and moving right.
        assertEquals(0,obj.getX());
        obj.nextFrame();
        assertEquals(obj.getMoveAmount(), obj.getX());
    }

    @Test
    void testBounceLeft() {
        // Test what happens when the ball is past the right edge
        
        // Initialize the object to be beyond the right edge
        int move = obj.getMoveAmount();
        obj.setX(demo.getWidth()-1);
        
        // Move the ball
        obj.nextFrame();
        
        // Check that the move amount value has been negated
        assertEquals(-1 * move, obj.getMoveAmount());
        
        // Check that the ball is at the right edge of the window
        assertEquals(demo.getWidth()-obj.getSize(), obj.getX());
        
        // Move the ball again
        obj.nextFrame();
        
        // Check that it is further to the left.
        assertEquals(demo.getWidth()-obj.getSize()-move, obj.getX());
    }

    @Test
    void testBounceRight() {
        // Test what happens when the ball reaches the left edge
        
        // Initialize the ball to be left of the left edge and the move
        // amount to be negative, so the ball is moving left
        int amount = -1 * obj.getMoveAmount();
        obj.setMoveAmount(amount);
        obj.setX(-1);
        
        // Move the ball
        obj.nextFrame();
        
        // Check that the ball is at the left edge
        assertEquals (0, obj.getX());
        
        // Check that the move amount has been negated
        assertEquals(-1 * amount, obj.getMoveAmount());
        
        // Move the ball again
        obj.nextFrame();
        
        // Check that it is further to the right.
        assertEquals(Math.abs(amount), obj.getX());

    }

}
