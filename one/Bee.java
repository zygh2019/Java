package one;

import java.awt.image.BufferedImage;
import java.util.Random;

public class Bee extends FlyingObject implements Award{
	private int xstep;
	private int ystep;
public static BufferedImage [] images;
	static {
		images=new BufferedImage[5];
		images[0]=readImage("bee0.png");
		for (int i = 1; i < images.length; i++) {
			images[i]=readImage("bom"+i+".png");
		}
	}
	public Bee() {
	super(images[0].getWidth(),images[0].getHeight());
	ystep=4;
	xstep=3;
	}
	public void step() {
		y+=ystep;
		x+=xstep;
		if(x>=World.WITCH-witch||x<=0) {
			xstep*=-1;
		}
	}
	int index=0;
	public BufferedImage getImage() {
	if(isLife()) {
		return images[0];
	}else if(isDead()) {
		index++;
		if(index==images.length-1) {
			state=REMOVE;
		}
		return images[index];
	}
		return null;
	}
	public int getAward() {
		Random ran =new Random();
		return  ran.nextInt(2);
	}
}
