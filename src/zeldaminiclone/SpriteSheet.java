package zeldaminiclone;

import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;

public class SpriteSheet {
	
	public static BufferedImage loadSpriteSheet(String path) throws IOException {
		return ImageIO.read(SpriteSheet.class.getResource(path));
	}
	
	public static BufferedImage getSprite(BufferedImage sprite, int positionX, int positionY, int width, int height) {
		return sprite.getSubimage(positionX, positionY, width, height);
	}
	
}
