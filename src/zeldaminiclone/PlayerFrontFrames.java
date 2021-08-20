package zeldaminiclone;

public enum PlayerFrontFrames implements FramesAnimation {
	RIGHTY_STEP(1, 11, 16, 16),
	LEFTY_STEP(18, 11, 16, 16);
	
	private final int x,y,widht,heigth;
	
	PlayerFrontFrames(int x,int y, int widht, int heigth) {
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
