package animation.demo;
import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.JFrame;

import animation.AbstractAnimation;

/**
 * This class provides a simple demonstration of how you would implement an 
 * animation (or game!) that contains multiple animated objects.
 *
 */
public class AnimationDemo extends AbstractAnimation implements KeyListener {
    // The width of the window, in pixels.
    private static final int WINDOW_WIDTH = 600;
    
    // The height of the window, in pixels.
    private static final int WINDOW_HEIGHT = 600;
    
    // The object that moves during the animation.  You might have
    // many objects!
    private AnimatedObjectDemo shape = new AnimatedObjectDemo(this);
    
    private AffineTransformDemo triangle = new AffineTransformDemo();
    
    private boolean moving = true;
    
    /**
     * Constructs an animation and initializes it to be able to accept
     * key input.
     */
    public AnimationDemo () {
        // Allow the game to receive key input
        setFocusable(true);
        addKeyListener (this);
    }

    @Override
    /**
     * Updates the animated object for the next frame of the animation
     * and repaints the window.
     */
    protected void nextFrame() {
        if (moving) {
            shape.nextFrame();
            repaint();
            if (checkCollision (shape, triangle)) {
                moving = false;
            }
        }
    }

    /**
     * Check whether two object collide.  This tests whether their shapes intersect.
     * @param shape1 the first shape to test
     * @param shape2 the second shape to test
     * @return true if the shapes intersect
     */
    private boolean checkCollision(AnimatedObjectDemo shape1,
            AffineTransformDemo shape2) {
        return shape2.getShape().intersects(shape1.getShape().getBounds2D());
    }

    /**
     * Paint the animation by painting the objects in the animation.
     * @param g the graphic context to draw on
     */
    public void paintComponent(Graphics g) {
        // Note that your code should not call paintComponent directly.
        // Instead your code calls repaint (as shown in the nextFrame
        // method above, and repaint will call paintComponent.
        
        super.paintComponent(g);
        shape.paint((Graphics2D) g);
        triangle.paint((Graphics2D) g);
    }

    @Override
    /**
     * This is called on the downward action when the user presses a key.
     * It notifies the animated ball about presses of up arrow, right 
     * arrow, left arrow, and the space bar.  All other keys are ignored.
     * @param e information abou the key pressed
     */
    public void keyPressed(KeyEvent e) {
        int key = e.getKeyCode();
        switch (key) {
        case KeyEvent.VK_UP:
            shape.up();
            break;
        case KeyEvent.VK_RIGHT:
            shape.right();
            break;
        case KeyEvent.VK_LEFT:
            shape.left();
            break;
        case KeyEvent.VK_SPACE:
            shape.space();
            break;
        default:
            // Ignore all other keys
                
        }
    }

    @Override
    /**
     * This is called when the user releases the key after pressing it.
     * It does nothing.
     * @param e information about the key released
     */
    public void keyReleased(KeyEvent e) {
        // Nothing to do
    }

    @Override
    /**
     * This is called when the user presses and releases a key without
     * moving the mouse in between.  Does nothing.
     * @param e information about the key typed.
     */
    public void keyTyped(KeyEvent e) {
        // Nothing to do
    }

    /**
     * The main method creates a window for the animation to run in,
     * initializes the animation and starts it running.
     * @param args none
     */
    public static void main(String[] args) {
        // JFrame is the class for a window.  Create the window,
        // set the window's title and its size.
        JFrame f = new JFrame();
        f.setTitle("Animation Demo");
        f.setSize(new Dimension(WINDOW_WIDTH, WINDOW_HEIGHT));
        
        // This says that when the user closes the window, the
        // entire program should exit.
        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Create the animation.
        AnimationDemo demo = new AnimationDemo();

        // Add the animation to the window
        Container contentPane = f.getContentPane();
        contentPane.add(demo, BorderLayout.CENTER);

        // Display the window.
        f.setVisible(true);
        
        // Start the animation
        demo.start();
    }

}
