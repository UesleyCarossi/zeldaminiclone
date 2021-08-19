package zeldaminiclone;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.io.IOException;

public class Block extends Rectangle {
	private static final long serialVersionUID = 1L;

	public final static int SIZE = 32;
	
	private BufferedImage spriteSheet;
	private BufferedImage wall;
	
	public Block(int positionX, int positionY) {
		super(positionX, positionY, SIZE, SIZE);
		
		try {
			spriteSheet = SpriteSheet.loadSpriteSheet("/tile_spritesheet.png");
			wall = SpriteSheet.getSprite(spriteSheet, 18, 1, 16, 16);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void render(Graphics graphics) {
		
		if (wall != null) {
			graphics.drawImage(wall, x, y, width, height, null);
		} else {
			graphics.setColor(Color.MAGENTA);
			graphics.fillRect(x, y, SIZE, SIZE);
			graphics.setColor(Color.BLACK);
			graphics.drawRect(x, y, width, height);
		}
		
	}
	
}
