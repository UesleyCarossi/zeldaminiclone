package zeldaminiclone;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.io.IOException;

public class Player extends Rectangle {
	private static final long serialVersionUID = 1L;
	
	public final static int SIZE = 32;
	
	private final int SPEED = 4;
	private boolean right, left, prioritizeFirstRight;
	private boolean up, down, prioritizeFirstUp;
	
	private World world;
	
	private BufferedImage spriteSheet;
	private BufferedImage[] playerFront;
	private int currentAnimation = 0;
	private int currentFrames = 0;
	private int targetFrames = 15;
	
	public Player(World world, int positionX, int positionY) {
		super(positionX, positionY, SIZE, SIZE);
		this.world = world;
		
		try {
			spriteSheet = SpriteSheet.loadSpriteSheet("/player_spritesheet.png");

			PlayerFrontFrames[] playerFrontFrames = PlayerFrontFrames.values();
			int lengthFramesAnimation = playerFrontFrames.length;
			
			playerFront = new BufferedImage[lengthFramesAnimation];
			for (int i = 0; i < lengthFramesAnimation; i++) {
				PlayerFrontFrames currentFrame = playerFrontFrames[i];
				playerFront[i] = SpriteSheet.getSprite(spriteSheet, currentFrame);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}

	public void tick() {
		moviment();
	}
	
	private void moviment() {
		
		final int originalX = x;
		final int originalY = y;
		
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
		
		movimentAnimation(originalX != x || originalY != y);
	}
	
	private void movimentAnimation(boolean moved) {
		if (moved) {
			currentFrames++;
			if(currentFrames == targetFrames) {
				currentFrames = 0;
				currentAnimation++;
				if (currentAnimation == PlayerFrontFrames.values().length) {
					currentAnimation = 0;
				}
			}
		}
	}
	
	public void render(Graphics graphics) {
		if (playerFront != null) {
			graphics.drawImage(playerFront[currentAnimation], x, y, width, height, null);
		} else {
			graphics.setColor(Color.BLUE);
			graphics.fillRect(x, y, width, height);
		}
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
