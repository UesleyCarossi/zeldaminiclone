package zeldaminiclone;

import java.awt.Graphics;
import java.awt.Rectangle;
import java.util.ArrayList;
import java.util.List;

public class World {
	
	private List<Block> blocks = new ArrayList<Block>();
	
	public World() {
		buildWalls();
	}
	
	public boolean canMoveTo(Rectangle object, int positionX, int positionY) {
		Rectangle cloneObject = object.getBounds();
		cloneObject.x = positionX;
		cloneObject.y = positionY;
		return checkCollide(cloneObject);
	}
	
	
	private boolean checkCollide(Rectangle object) {
		for (Block block : blocks) {
			if (block.intersects(object.getBounds())) {
				return false;
			}
		}
		return true;
	}
	
	public void render(Graphics graphics) {
		for (Block block : blocks) {
			block.render(graphics);
		}
	}
	
	private void buildWalls() {
		final int maxBlocksDirectionX = Game.WIDTH / Block.SIZE;
		final int maxBlocksDirectionY = Game.HEIGHT / Block.SIZE;
		
		for (int currentBlock = 0; currentBlock < maxBlocksDirectionX; currentBlock++) {
			blocks.add(buildTopWall(currentBlock));
			blocks.add(buildBottomWall(currentBlock));
		}
		
		for (int currentBlock = 0; currentBlock < maxBlocksDirectionY; currentBlock++) {
			blocks.add(buildLeftWall(currentBlock));
			blocks.add(buildRightWall(currentBlock));
		}
	}
	
	private Block buildRightWall(int currentBlock) {
		final int positionXBlock = 0;
		final int positionYBlock = currentBlock * Block.SIZE;
		return new Block(positionXBlock, positionYBlock);
	}
	
	private Block buildLeftWall(int currentBlock) {
		final int positionXBlock = Game.WIDTH - Block.SIZE;
		final int positionYBlock = currentBlock * Block.SIZE;
		return new Block(positionXBlock, positionYBlock);
	}
	
	private Block buildTopWall(int currentBlock) {
		final int positionXBlock = currentBlock * Block.SIZE;
		final int positionYBlock = 0;
		return new Block(positionXBlock, positionYBlock);
	}
	
	private Block buildBottomWall(int currentBlock) {
		final int positionXBlock = currentBlock * Block.SIZE;
		final int positionYBlock = Game.HEIGHT - Block.SIZE;
		return new Block(positionXBlock, positionYBlock);
	}
}
