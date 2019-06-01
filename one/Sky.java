package one;

import java.awt.Graphics;
import java.awt.image.BufferedImage;

public class Sky extends FlyingObject {
	private int y1;
	public static BufferedImage image;
	static {
		image=readImage("background1.png");

	}
	public Sky() {
		super(0,0,image.getWidth(),image.getHeight());
		y1=-height;
	}


	public int getscore() {
		return 1;

	}

	@Override
	public void step() {
		y+=1;
		y1+=1;
		if(y>=World.HEIGHT) {
			y*=-1;
		}
		if(y1>=World.HEIGHT) {
			y1*=-1;

		}
	}

	public void paintObject(Graphics g) {
		g.drawImage(getImage(),x,y,null);
		g.drawImage(getImage(),x,y1,null);

	}
	public BufferedImage getImage() {

		return image;

	}

}
