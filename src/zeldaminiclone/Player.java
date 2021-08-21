package zeldaminiclone;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.io.IOException;

import zeldaminiclone.animation.PlayerBackFrames;
import zeldaminiclone.animation.PlayerFrontFrames;
import zeldaminiclone.animation.PlayerSideFrames;

public class Player extends Rectangle {
	private static final long serialVersionUID = 1L;
	
	public final static int SIZE = 32;
	
	private final int SPEED = 4;
	private boolean right, left, prioritizeFirstRight;
	private boolean up, down, prioritizeFirstUp;
	
	private World world;
	
	private BufferedImage spriteSheet;
	private BufferedImage[] playerFront;
	private BufferedImage[] playerBack;
	private BufferedImage[] playerSideRight;
	private BufferedImage[] playerSideLeft;
	private BufferedImage[] currentFramesAnimation;
	private int currentAnimation = 0;
	private int currentFrames = 0;
	private int targetFrames = 15;
	
	public Player(World world, int positionX, int positionY) {
		super(positionX, positionY, SIZE, SIZE);
		this.world = world;
		
		try {
			spriteSheet = SpriteSheet.loadSpriteSheet("/player_spritesheet.png");
			playerFront = loadFramesAnimation(PlayerFrontFrames.values(), playerFront);
			playerBack = loadFramesAnimation(PlayerBackFrames.values(), playerBack);
			playerSideRight = loadFramesAnimation(PlayerSideFrames.values(), playerSideRight);
			playerSideLeft = loadFramesAnimation(PlayerSideFrames.values(), playerSideLeft, true);
			
			currentFramesAnimation = playerFront;
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}
	
	private BufferedImage[] loadFramesAnimation(FramesAnimation[] framesAnimation,
			BufferedImage[] playerFramesAnimation) {
		return loadFramesAnimation(framesAnimation, playerFramesAnimation, false);
	}
	
	private BufferedImage[] loadFramesAnimation(FramesAnimation[] framesAnimation,
			BufferedImage[] playerFramesAnimation, Boolean flip) {
		int lengthFramesAnimation = framesAnimation.length;
		
		playerFramesAnimation = new BufferedImage[lengthFramesAnimation];
		for (int i = 0; i < lengthFramesAnimation; i++) {
			FramesAnimation currentFrame = framesAnimation[i];
			playerFramesAnimation[i] = flip
					? SpriteSheet.flip(SpriteSheet.getSprite(spriteSheet, currentFrame))
					: SpriteSheet.getSprite(spriteSheet, currentFrame);
		}
		
		return playerFramesAnimation;
	}

	public void tick() {
		moviment();
	}
	
	private void moviment() {
		
		final int originalX = x;
		final int originalY = y;
		FramesAnimation []framesAnimation = PlayerFrontFrames.values();
		
		if (prioritizeFirstRight) {
			if (right) {
				final int nextStep = x + SPEED;
				if (world.canMoveTo(this, nextStep, y)) {
					x = nextStep;
					framesAnimation = PlayerSideFrames.values();
					currentFramesAnimation = playerSideRight;
				}
			} else if (left) {
				final int nextStep = x - SPEED;
				if (world.canMoveTo(this, nextStep, y)) {
					x = nextStep;
					framesAnimation = PlayerSideFrames.values();
					currentFramesAnimation = playerSideLeft;
				}
			}
		} else {
			if (left) {
				final int nextStep = x - SPEED;
				if (world.canMoveTo(this, nextStep, y)) {
					x = nextStep;
					framesAnimation = PlayerSideFrames.values();
					currentFramesAnimation = playerSideLeft;
				}
			} else if (right) {
				final int nextStep = x + SPEED;
				if (world.canMoveTo(this, nextStep, y)) {
					x = nextStep;
					framesAnimation = PlayerSideFrames.values();
					currentFramesAnimation = playerSideRight;
				}
			}
		}
		
		if (prioritizeFirstUp) {
			if (up) {
				final int nextStep = y - SPEED;
				if (world.canMoveTo(this, x, nextStep)) {
					y = nextStep;
					framesAnimation = PlayerBackFrames.values();
					currentFramesAnimation = playerBack;
				}
			} else if (down) {
				final int nextStep = y + SPEED;
				if (world.canMoveTo(this, x, nextStep)) {
					y = nextStep;
					framesAnimation = PlayerFrontFrames.values();
					currentFramesAnimation = playerFront;
				}
			}
		} else {
			if (down) {
				final int nextStep = y + SPEED;
				if (world.canMoveTo(this, x, nextStep)) {
					y = nextStep;
					framesAnimation = PlayerFrontFrames.values();
					currentFramesAnimation = playerFront;
				}
			} else if (up) {
				final int nextStep = y - SPEED;
				if (world.canMoveTo(this, x, nextStep)) {
					y = nextStep;
					framesAnimation = PlayerBackFrames.values();
					currentFramesAnimation = playerBack;
				}
			}
		}
		
		movimentAnimation(originalX != x || originalY != y, framesAnimation);
	}
	
	private void movimentAnimation(boolean moved, FramesAnimation []framesAnimation) {
		if (moved) {
			currentFrames++;
			if(currentFrames == targetFrames) {
				currentFrames = 0;
				currentAnimation++;
				if (currentAnimation == framesAnimation.length)
					currentAnimation = 0;
			}
		}
	}
	
	public void render(Graphics graphics) {
		if (currentFramesAnimation != null) {
			graphics.drawImage(currentFramesAnimation[currentAnimation], x, y, width, height, null);
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
