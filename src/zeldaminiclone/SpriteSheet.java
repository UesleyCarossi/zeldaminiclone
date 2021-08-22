package zeldaminiclone;

import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
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
	
	public static BufferedImage getSprite(BufferedImage sprite, FramesAnimation framesAnimation) {
		return sprite.getSubimage(framesAnimation.getX(), framesAnimation.getY(), framesAnimation.getWidth(), framesAnimation.getHeight());
	}
	
	public static BufferedImage flip(BufferedImage sprite) {
		AffineTransform affineTransform = AffineTransform.getScaleInstance(-1, 1);
		affineTransform.translate(-sprite.getWidth(null), 0);
		
		AffineTransformOp affineTransformOp = new AffineTransformOp(affineTransform, AffineTransformOp.TYPE_NEAREST_NEIGHBOR);
		sprite = affineTransformOp.filter(sprite, null);
		
		return sprite;
	}
	
}
