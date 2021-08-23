package zeldaminiclone.animation;

import zeldaminiclone.FramesAnimation;

public enum EnemySideFrames implements FramesAnimation {
	RIGHTY_STEP(52, 90, 16, 16),
	LEFTY_STEP(69, 90, 16, 16);
	
	private final int x,y,widht,heigth;
	
	EnemySideFrames(int x,int y, int widht, int heigth) {
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
