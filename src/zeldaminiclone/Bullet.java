package zeldaminiclone;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;

public class Bullet extends Rectangle {
	private static final long serialVersionUID = 1L;
	
	private static int SIZE = 10;
	private int SPEED = 8;
	
	private int frames = 0;
	
	private int directionX = 0;
	private int directionY = 0;
	
	public Bullet(int positionX, int positionY, int directionX, int directionY) {
		super(positionX, positionY, SIZE, SIZE);
		this.directionX = directionX;
		this.directionY = directionY;
	}
	
	public void tick() {
		x += SPEED * directionX;
		y += SPEED * directionY;
		frames++;
	}
	
	public void render(Graphics graphics) {
		graphics.setColor(Color.YELLOW);
		graphics.fillOval(x, y, width, height);
	}
	
	public boolean canBeRemove() {
		return frames >= 60;
	}
}
