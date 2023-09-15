package animation;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
//import java.awt.Rectangle;
//import java.awt.image.BufferedImage;
//import java.io.File;
//import java.io.IOException;
//
//import javax.imageio.ImageIO;

public class Pause {
	static void paint(Graphics g) {
		
//		Graphics2D myGraphics = (Graphics2D) g;
		
		//Font color and size of the title
		Font gameOverFnt = new Font("arial", Font.BOLD, 20);
//		Font AgainFnt = new Font("arial", Font.BOLD, 10);
		g.setFont(gameOverFnt);
		g.setColor(Color.WHITE);
//		g.drawString("GAME OVER", 250, 300);
//		g.setFont(AgainFnt);
		g.drawString("PAUSE", 267, 290);
		
		Font helpFnt = new Font("arial", Font.BOLD, 10);
		g.setFont(helpFnt);
		g.drawString("press  ESC  to resume", 248, 500);
		
//		Font scoreFnt = new Font("arial", Font.BOLD, 10);
//		g.setFont(scoreFnt);
		
	}
}