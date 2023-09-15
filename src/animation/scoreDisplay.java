package animation;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;


/**
 * Draws out the display where score in the game
 * will be shown. 
 */
public class scoreDisplay {
    /**
     * Uses methods to display the score alongside the level.
     * Score is shown in the left corner of the screen. 
     * @param g , the graphic context to draw on
     */
    static void paint(Graphics g) {
        // Draws rectangle around level and score
        Graphics2D myGraphics = (Graphics2D) g;
        myGraphics.setColor(Color.WHITE);

        // Displays level and score
        Font scoreFont = new Font("arial", Font.BOLD, 15);
        g.setFont(scoreFont);
        g.setColor(Color.WHITE);
        g.drawString("level : " + Integer.toString(Game.level), 10, 20);
        g.drawString("score : " + Game.score, 10, 33);
        g.drawString("HIGH SCORE : " + Game.highScore, 430, 20);
    }
}