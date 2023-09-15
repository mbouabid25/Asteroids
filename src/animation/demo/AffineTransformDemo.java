package animation.demo;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Polygon;
import java.awt.Shape;
import java.awt.geom.AffineTransform;

/**
 * This class demonstrates how shapes can be rotated and translated.
 *
 */
public class AffineTransformDemo {
    // The shape that is drawn
    private Polygon p;
    
    // The left edge of the shape
    private int x;
    
    // The top edge of the shape
    private int y;
    
    /**
     * Constructs a triangle
     */
    public AffineTransformDemo () {
        p = new Polygon();
        p.addPoint(-10, 20);
        p.addPoint(0, -20);
        p.addPoint(10, 20);
        
        x = 100;
        y = 100;
    }

    /**
     * Draws the triangle
     * @param g the graphics context to draw on
     */
    public void paint(Graphics2D g) {
        g.setColor(Color.BLACK);
        g.draw(getShape());
    }

    /**
     * Returns the shape after applying the current translation
     * and rotation
     * @return the shape located as we want it to appear
     */
    public Shape getShape() {
        // AffineTransform captures the movement and rotation we
        // want the shape to have
        AffineTransform at1 = new AffineTransform();
        
        // x, y are where the origin of the shape will be.  In this
        // case, this is the center of the triangle.  See the constructor
        // to see where the points are.
        at1.translate(x, y);
        
        // Rotate the shape 45 degrees to the left
        at1.rotate(Math.PI/4);
        AffineTransform at = at1;
        
        // Create a shape that looks like our triangle, but centered
        // and rotated as specified by the AffineTransform object.
        return at.createTransformedShape(p);
    }
}
