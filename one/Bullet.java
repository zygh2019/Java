package one;

import java.awt.image.BufferedImage;

public class Bullet extends FlyingObject {
	private int step;
public static BufferedImage  image;
	static {
		image=readImage("bullet.png");
	}
	public Bullet(int x ,int y) {
	super(x,y,image.getWidth(),image.getHeight());
	step=20;
	}
	

	public  boolean  outofBound() {
		return y<=0;
		
	}
	public void step() {
		y-=step;
		
	}

	int index=0;
	public BufferedImage getImage() {
	if(isLife()) {
		return image;

	}
	return null;

}
}

