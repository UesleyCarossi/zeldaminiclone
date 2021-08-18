package zeldaminiclone;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;

public class Block extends Rectangle {
	private static final long serialVersionUID = 1L;

	public final static int SIZE = 32;
	
	public Block(int positionX, int positionY) {
		super(positionX, positionY, SIZE, SIZE);
	}
	
	public void render(Graphics graphics) {
		graphics.setColor(Color.MAGENTA);
		graphics.fillRect(x, y, SIZE, SIZE);
		graphics.setColor(Color.BLACK);
		graphics.drawRect(x, y, width, height);
	}
	
}
