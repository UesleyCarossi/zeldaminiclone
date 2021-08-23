package zeldaminiclone;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import zeldaminiclone.animation.EnemyBackFrames;
import zeldaminiclone.animation.EnemyFrontFrames;
import zeldaminiclone.animation.EnemySideFrames;

public class Enemy extends Rectangle {
	private static final long serialVersionUID = 1L;
	
	public final static int SIZE = 32;
	
	private final int SPEED = 2;
	private boolean right, left, up, down;
	private Direction direction = Direction.SOUTH;
	
	private World world;
	
	private BufferedImage spriteSheet;
	private BufferedImage[] enemyFront;
	private BufferedImage[] enemyBack;
	private BufferedImage[] enemySideRight;
	private BufferedImage[] enemySideLeft;
	private BufferedImage[] currentFramesAnimation;
	private int currentAnimation = 0;
	private int currentFrames = 0;
	private int targetFrames = 15;
	
	private List<Bullet> bullets = new ArrayList<>();
	private boolean shoot = false;
	private int shootDirectionX = 0;
	private int shootDirectionY = 0;
	
	public Enemy(World world, int positionX, int positionY) {
		super(positionX, positionY, SIZE, SIZE);
		this.world = world;
		
		try {
			spriteSheet = SpriteSheet.loadSpriteSheet("/enemies_spritesheet.png");
			enemyFront = loadFramesAnimation(EnemyFrontFrames.values(), enemyFront);
			enemyBack = loadFramesAnimation(EnemyBackFrames.values(), enemyBack, true, true);
			enemySideRight = loadFramesAnimation(EnemySideFrames.values(), enemySideRight);
			enemySideLeft = loadFramesAnimation(EnemySideFrames.values(), enemySideLeft, true, false);
			
			currentFramesAnimation = enemyFront;
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}
	
	private BufferedImage[] loadFramesAnimation(FramesAnimation[] framesAnimation,
			BufferedImage[] enemyFramesAnimation) {
		return loadFramesAnimation(framesAnimation, enemyFramesAnimation, false, false);
	}
	
	private BufferedImage[] loadFramesAnimation(FramesAnimation[] framesAnimation,
			BufferedImage[] enemyFramesAnimation, Boolean flip, Boolean flipOnlyLast) {
		int lengthFramesAnimation = framesAnimation.length;
		
		enemyFramesAnimation = new BufferedImage[lengthFramesAnimation];
		for (int i = 0; i < lengthFramesAnimation; i++) {
			FramesAnimation currentFrame = framesAnimation[i];
			
			enemyFramesAnimation[i] = SpriteSheet.getSprite(spriteSheet, currentFrame);
			if (flip)
				if (!flipOnlyLast || (flipOnlyLast && i+1 == lengthFramesAnimation))
					enemyFramesAnimation[i] = SpriteSheet.flip(enemyFramesAnimation[i]);
		}
		
		return enemyFramesAnimation;
	}

	public void tick() {
		moviment();
		shooting();
	}
	
	private void shooting() {
		if (shoot) {
			shoot = false;
			
			final int bulletShootFromHalfOfEnemy = SIZE / 2;
			bullets.add(new Bullet(x + bulletShootFromHalfOfEnemy, 
					y + bulletShootFromHalfOfEnemy, shootDirectionX,
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
		FramesAnimation []framesAnimation = EnemyFrontFrames.values();

		
		int nextStepX = x;
		int nextStepY = y;
		
		if (right || left || up || down)
			switch (direction) {
				case NORTH:
					nextStepY = y - SPEED;
					if (world.canMoveTo(this, x, nextStepY)) {
						y = nextStepY;
						framesAnimation = EnemyBackFrames.values();
						currentFramesAnimation = enemyBack;
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
						framesAnimation = EnemyBackFrames.values();
						currentFramesAnimation = enemyBack;
					}
					shootDirectionX = 1;
					shootDirectionY = -1;
					break;
				case EAST:
					nextStepX = x + SPEED;
					if (world.canMoveTo(this, nextStepX, y)) {
						x = nextStepX;
						framesAnimation = EnemySideFrames.values();
						currentFramesAnimation = enemySideRight;
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
						framesAnimation = EnemyFrontFrames.values();
						currentFramesAnimation = enemyFront;
					}
					shootDirectionX = 1;
					shootDirectionY = 1;
					break;
				case SOUTH:
					nextStepY = y + SPEED;
					if (world.canMoveTo(this, x, nextStepY)) {
						y = nextStepY;
						framesAnimation = EnemyFrontFrames.values();
						currentFramesAnimation = enemyFront;
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
						framesAnimation = EnemyFrontFrames.values();
						currentFramesAnimation = enemyFront;
					}
					shootDirectionX = -1;
					shootDirectionY = 1;
					break;
				case WEST:
					nextStepX = x - SPEED;
					if (world.canMoveTo(this, nextStepX, y)) {
						x = nextStepX;
						framesAnimation = EnemySideFrames.values();
						currentFramesAnimation = enemySideLeft;
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
						framesAnimation = EnemyBackFrames.values();
						currentFramesAnimation = enemyBack;
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
