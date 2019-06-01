package one;

import java.awt.image.BufferedImage;

public class BigAirplane extends FlyingObject implements Score{
	private int step;
	public static BufferedImage [] images;
	static {
		images=new BufferedImage[5];
		images[0]=readImage("bigairplane0.png");
		for (int i = 1; i < images.length; i++) {
			images[i]=readImage("bom"+i+".png");
		}
	}
	public BigAirplane() {
		super(images[0].getWidth(),images[0].getHeight());
		step=4;



	}


	public int getscore() {
		return 2;

	}

	@Override
	public void step() {
		y+=step;

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

}

