package zeldaminiclone;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferStrategy;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Random;

import javax.swing.JFrame;

public class Game extends Canvas implements Runnable, KeyListener {
	private static final long serialVersionUID = 1L;
	
	public static final int WIDTH = 640, HEIGHT = 480;
	public static final int SCALE = 3;
	
	private int frames = 0;
	
	private World world;
	private List<Enemy> enemies = new ArrayList<Enemy>();
	private Player player;
	
	public Game() {
		addKeyListener(this);
		setPreferredSize(new Dimension(WIDTH, HEIGHT));
		
		world = new World();
		
		final int initialPositionEnemiesX = 576;
		final int initialPositionEnemiesY = 416;
		enemies.add(new Enemy(world, initialPositionEnemiesX, initialPositionEnemiesY));
		enemies.add(new Enemy(world, initialPositionEnemiesX, initialPositionEnemiesY));
		
		final int initialPositionPlayer = 32;
		player = new Player(world, initialPositionPlayer, initialPositionPlayer);
		
	}
	
	public void tick() {
		frames++;
		player.tick();
		for (Enemy enemy : enemies) {
			enemy.tick();
		}
	}
	
	private void render() {
		makeBufferStrategy().ifPresent((final BufferStrategy bufferStrategy) -> drawGraphics(bufferStrategy));
	}
	
	private void drawGraphics(BufferStrategy bufferStrategy) {
		Graphics graphics = bufferStrategy.getDrawGraphics();
		addRender(graphics);
		bufferStrategy.show();
	}
	
	private void addRender(Graphics graphics) {
		backgroundRender(graphics);
		world.render(graphics);
		player.render(graphics);
		for (Enemy enemy : enemies) {
			enemy.render(graphics);
		}
	}
	
	
	private Optional<BufferStrategy> makeBufferStrategy() {
		BufferStrategy bufferStrategy = getBufferStrategy();
		if (bufferStrategy == null) {
			final int buffersEnough = 3;
			createBufferStrategy(buffersEnough);
			return Optional.empty();
		}
		return Optional.ofNullable(bufferStrategy);
	}
	
	private void backgroundRender(Graphics graphics) {
		final Color green = new Color(0,135,13);
		graphics.setColor(green);
		graphics.fillRect(0, 0, WIDTH*SCALE, HEIGHT*SCALE);
	}

	public static void main(String ...args) {
		Game game = new Game();
		
		final Component alignCenter = null;
		JFrame frame = new JFrame();
		frame.add(game);
		frame.setTitle("Mini Zelda");
		frame.pack();
		frame.setLocationRelativeTo(alignCenter);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setVisible(true);
		
		new Thread(game).start();
	}
	
	@Override
	public void run() {
		Random randomGenerator = new Random();
		while(true) {
			automaticControlEnemies(randomGenerator);
			tick();
			render();
			limitFps();
		}
	}
	
	private void limitFps() {
		try {
			final int fps60 = 16;
			Thread.sleep(fps60);
		} catch (InterruptedException interruptedException) {
			interruptedException.printStackTrace();
		}
	}
	
	private void automaticControlEnemies(Random randomGenerator) {
		if (frames >= 60) {
			frames = 0;
			for (Enemy enemy : enemies) {
				final int randomNumberHorizontal = randomGenerator.nextInt(4);
				if (randomNumberHorizontal == 0)
					enemy.moveRight();
				else if (randomNumberHorizontal == 1)
					enemy.moveLeft();
				else if (randomNumberHorizontal == 2)
					enemy.stopMoveRight();
				else if (randomNumberHorizontal == 3)
					enemy.stopMoveLeft();
				
				final int randomNumberVertical = randomGenerator.nextInt(4);
				if (randomNumberVertical == 0)
					enemy.moveUp();
				else if (randomNumberVertical == 1)
					enemy.moveDown();
				else if (randomNumberVertical == 2)
					enemy.stopMoveUp();
				else if (randomNumberVertical == 3)
					enemy.stopMoveDown();
				
				if (randomGenerator.nextInt(2) == 0)
					enemy.shoot();
			}
		}
		
	}

	@Override
	public void keyTyped(KeyEvent keyEvent) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void keyPressed(KeyEvent keyEvent) {
		switch (keyEvent.getKeyCode()) {
			case KeyEvent.VK_RIGHT:
				player.moveRight();
				break;
			case KeyEvent.VK_LEFT:
				player.moveLeft();
				break;
			case KeyEvent.VK_UP:
				player.moveUp();
				break;
			case KeyEvent.VK_DOWN:
				player.moveDown();
				break;
			case KeyEvent.VK_Z:
				player.shoot();
				break;
		}
	}

	@Override
	public void keyReleased(KeyEvent keyEvent) {
		switch (keyEvent.getKeyCode()) {
			case KeyEvent.VK_RIGHT:
				player.stopMoveRight();
				break;
			case KeyEvent.VK_LEFT:
				player.stopMoveLeft();
				break;
			case KeyEvent.VK_UP:
				player.stopMoveUp();
				break;
			case KeyEvent.VK_DOWN:
				player.stopMoveDown();
				break;
		}
	}

}
