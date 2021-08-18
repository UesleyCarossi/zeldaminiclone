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
		final int maxBlocksDirection = 15;
		
		for (int currentBlock = 0; currentBlock < maxBlocksDirection; currentBlock++) {
			blocks.add(buildLeftWall(currentBlock));
			blocks.add(buildTopWall(currentBlock));
			blocks.add(buildRightWall(currentBlock));
			blocks.add(buildBottomWall(currentBlock));
		}
	}
	
	private Block buildRightWall(int currentBlock) {
		int positionXBlock = 0;
		int positionYBlock = currentBlock * Block.SIZE;
		return new Block(positionXBlock, positionYBlock);
	}
	
	private Block buildLeftWall(int currentBlock) {
		int positionXBlock = Game.WIDTH - Block.SIZE;
		int positionYBlock = currentBlock * Block.SIZE;
		return new Block(positionXBlock, positionYBlock);
	}
	
	private Block buildTopWall(int currentBlock) {
		int positionXBlock = currentBlock * Block.SIZE;
		int positionYBlock = 0;
		return new Block(positionXBlock, positionYBlock);
	}
	
	private Block buildBottomWall(int currentBlock) {
		int positionXBlock = currentBlock * Block.SIZE;
		int positionYBlock = Game.HEIGHT - Block.SIZE;
		return new Block(positionXBlock, positionYBlock);
	}
}
