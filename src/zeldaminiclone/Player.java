package zeldaminiclone;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import zeldaminiclone.animation.PlayerBackFrames;
import zeldaminiclone.animation.PlayerFrontFrames;
import zeldaminiclone.animation.PlayerSideFrames;

public class Player extends Rectangle {
	private static final long serialVersionUID = 1L;
	
	public final static int SIZE = 32;
	
	private final int SPEED = 4;
	private boolean right, left, up, down;
	private Direction direction = Direction.SOUTH;
	
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
	
	private List<Bullet> bullets = new ArrayList<>();
	private boolean shoot = false;
	private int shootDirectionX = 0;
	private int shootDirectionY = 0;
	
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
		shooting();
	}
	
	private void shooting() {
		if (shoot) {
			shoot = false;
			
			final int bulletShootFromHalfOfPlayer = SIZE / 2;
			bullets.add(new Bullet(x + bulletShootFromHalfOfPlayer, 
					y + bulletShootFromHalfOfPlayer, shootDirectionX,
					shootDirectionY));
		}
		
		for (Bullet bullet : new ArrayList<Bullet>(bullets)) {
			bullet.tick();
			if (bullet.canBeRemove()) {
				bullets.remove(bullet);
			}
		}
	}
	
	private void moviment() {
		
		final int originalX = x;
		final int originalY = y;
		FramesAnimation []framesAnimation = PlayerFrontFrames.values();

		
		int nextStepX = x;
		int nextStepY = y;
		
		if (right || left || up || down)
			switch (direction) {
				case NORTH:
					nextStepY = y - SPEED;
					if (world.canMoveTo(this, x, nextStepY)) {
						y = nextStepY;
						framesAnimation = PlayerBackFrames.values();
						currentFramesAnimation = playerBack;
					}
					shootDirectionX = 0;
					shootDirectionY = -1;
					break;
				case NORTH_EAST:
					nextStepX = x + SPEED;
					nextStepY = y - SPEED;
					if (world.canMoveTo(this, nextStepX, nextStepY)) {
						x = nextStepX;
						y = nextStepY;
						framesAnimation = PlayerBackFrames.values();
						currentFramesAnimation = playerBack;
					}
					shootDirectionX = 1;
					shootDirectionY = -1;
					break;
				case EAST:
					nextStepX = x + SPEED;
					if (world.canMoveTo(this, nextStepX, y)) {
						x = nextStepX;
						framesAnimation = PlayerSideFrames.values();
						currentFramesAnimation = playerSideRight;
					}
					shootDirectionX = 1;
					shootDirectionY = 0;
					break;
				case SOUTH_EAST:
					nextStepX = x + SPEED;
					nextStepY = y + SPEED;
					if (world.canMoveTo(this, nextStepX, nextStepY)) {
						x = nextStepX;
						y = nextStepY;
						framesAnimation = PlayerFrontFrames.values();
						currentFramesAnimation = playerFront;
					}
					shootDirectionX = 1;
					shootDirectionY = 1;
					break;
				case SOUTH:
					nextStepY = y + SPEED;
					if (world.canMoveTo(this, x, nextStepY)) {
						y = nextStepY;
						framesAnimation = PlayerFrontFrames.values();
						currentFramesAnimation = playerFront;
					}
					shootDirectionX = 0;
					shootDirectionY = 1;
					break;
				case SOUTH_WEST:
					nextStepX = x - SPEED;
					nextStepY = y + SPEED;
					if (world.canMoveTo(this, nextStepX, nextStepY)) {
						x = nextStepX;
						y = nextStepY;
						framesAnimation = PlayerFrontFrames.values();
						currentFramesAnimation = playerFront;
					}
					shootDirectionX = -1;
					shootDirectionY = 1;
					break;
				case WEST:
					nextStepX = x - SPEED;
					if (world.canMoveTo(this, nextStepX, y)) {
						x = nextStepX;
						framesAnimation = PlayerSideFrames.values();
						currentFramesAnimation = playerSideLeft;
					}
					shootDirectionX = -1;
					shootDirectionY = 0;
					break;
				case NORTH_WEST:
					nextStepX = x - SPEED;
					nextStepY = y - SPEED;
					if (world.canMoveTo(this, nextStepX, nextStepY)) {
						x = nextStepX;
						y = nextStepY;
						framesAnimation = PlayerBackFrames.values();
						currentFramesAnimation = playerBack;
					}
					shootDirectionX = -1;
					shootDirectionY = -1;
					break;
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
		
		for (Bullet bullet : bullets) {
			bullet.render(graphics);
		}
	}
	
	public void moveRight() {
		right = true;
		if (up)
			direction = Direction.NORTH_EAST;
		else if (down)
			direction = Direction.SOUTH_EAST;
		else
			direction = Direction.EAST;
	}
	
	public void moveLeft() {
		left = true;
		if (up)
			direction = Direction.NORTH_WEST;
		else if (down)
			direction = Direction.SOUTH_WEST;
		else
			direction = Direction.WEST;
	}
	
	public void moveUp() {
		up = true;
		if (right)
			direction = Direction.NORTH_EAST;
		else if (left)
			direction = Direction.NORTH_WEST;
		else
			direction = Direction.NORTH;
	}
	
	public void moveDown() {
		down = true;
		if (right)
			direction = Direction.SOUTH_EAST;
		else if (left)
			direction = Direction.SOUTH_WEST;
		else
			direction = Direction.SOUTH;
	}
	
	public void stopMoveRight() {
		right = false;
		if (left)
			if (up)
				direction = Direction.NORTH_WEST;
			else if (down)
				direction = Direction.SOUTH_WEST;
			else
				direction = Direction.WEST;
		else if (up)
			direction = Direction.NORTH;
		else
			direction = Direction.SOUTH;
	}
	
	public void stopMoveLeft() {
		left = false;
		if (right)
			if (up)
				direction = Direction.NORTH_EAST;
			else if (down)
				direction = Direction.SOUTH_EAST;
			else
				direction = Direction.EAST;
		else if (up)
			direction = Direction.NORTH;
		else
			direction = Direction.SOUTH;
	}
	
	public void stopMoveUp() {
		up = false;
		if (down)
			if (right)
				direction = Direction.SOUTH_EAST;
			else if (left)
				direction = Direction.SOUTH_WEST;
			else
				direction = Direction.SOUTH;
		else if (right)
			direction = Direction.EAST;
		else
			direction = Direction.WEST;
	}
	
	public void stopMoveDown() {
		down = false;
		if (up)
			if (right)
				direction = Direction.NORTH_EAST;
			else if (left)
				direction = Direction.NORTH_WEST;
			else
				direction = Direction.NORTH;
		else if (right)
			direction = Direction.EAST;
		else
			direction = Direction.WEST;
	}
	
	public void shoot() {
		shoot = true;
	}
	
}
