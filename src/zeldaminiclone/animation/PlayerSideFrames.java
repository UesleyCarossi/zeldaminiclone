package zeldaminiclone.animation;

import zeldaminiclone.FramesAnimation;

public enum PlayerSideFrames implements FramesAnimation {
	RIGHTY_STEP(35, 11, 16, 16),
	LEFTY_STEP(52, 11, 16, 16);
	
	private final int x,y,widht,heigth;
	
	PlayerSideFrames(int x,int y, int widht, int heigth) {
		this.x = x;
		this.y = y;
		this.widht = widht;
		this.heigth = heigth;
	}
	
	@Override public int getX() { return x; }
	@Override public int getY() { return y; }
	@Override public int getWidth() { return widht; }
	@Override public int getHeight() { return heigth; }
}
