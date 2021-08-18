package zeldaminiclone;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;

public class Player extends Rectangle {
	private static final long serialVersionUID = 1L;
	
	public final static int SIZE = 32;
	
	private final int SPEED = 4;
	private boolean right, left, prioritizeFirstRight;
	private boolean up, down, prioritizeFirstUp;
	
	private World world;
	
	public Player(World world, int positionX, int positionY) {
		super(positionX, positionY, SIZE, SIZE);
		this.world = world;
	}

	public void tick() {
		moviment();
	}
	
	private void moviment() {
		
		if (prioritizeFirstRight) {
			if (right) {
				final int nextStep = x + SPEED;
				if (world.canMoveTo(this, nextStep, y))
					x = nextStep;
			} else if (left) {
				final int nextStep = x - SPEED;
				if (world.canMoveTo(this, nextStep, y))
					x = nextStep;
			}
		} else {
			if (left) {
				final int nextStep = x - SPEED;
				if (world.canMoveTo(this, nextStep, y))
					x = nextStep;
			} else if (right) {
				final int nextStep = x + SPEED;
				if (world.canMoveTo(this, nextStep, y))
					x = nextStep;
			}
		}
		
		if (prioritizeFirstUp) {
			if (up) {
				final int nextStep = y - SPEED;
				if (world.canMoveTo(this, x, nextStep))
					y = nextStep;
			} else if (down) {
				final int nextStep = y + SPEED;
				if (world.canMoveTo(this, x, nextStep))
					y = nextStep;
			}
		} else {
			if (down) {
				final int nextStep = y + SPEED;
				if (world.canMoveTo(this, x, nextStep))
					y = nextStep;
			} else if (up) {
				final int nextStep = y - SPEED;
				if (world.canMoveTo(this, x, nextStep))
					y = nextStep;
			}
		}
		
	}
	
	public void render(Graphics graphics) {
		graphics.setColor(Color.BLUE);
		graphics.fillRect(x, y, width, height);
	}
	
	public void moveRight() {
		right = true;
		prioritizeFirstRight = true;
	}
	
	public void moveLeft() {
		left = true;
		prioritizeFirstRight = false;
	}
	
	public void moveUp() {
		up = true;
		prioritizeFirstUp = true;
	}
	
	public void moveDown() {
		down = true;
		prioritizeFirstUp = false;
	}
	
	public void stopMoveRight() {
		right = false;
	}
	
	public void stopMoveLeft() {
		left = false;
	}
	
	public void stopMoveUp() {
		up = false;
	}
	
	public void stopMoveDown() {
		down = false;
	}
	
}
