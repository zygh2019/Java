package one;

import java.awt.image.BufferedImage;

public class Hero extends FlyingObject {
	private int life;
	private int doublefire;
	public static BufferedImage [] images;
	static {
		images=new BufferedImage[2];
		images[0]=readImage("hero0.png");
		images[1]=readImage("hero1.png");


	}
	public Hero() {
		super(World.WITCH/2-images[0].getWidth()/2,450,images[0].getWidth(),images[0].getHeight());
		life=5;
		 doublefire=1000;
	}
	
	public Bullet [] getBullet() {
		int a =this.witch/4;	
		if(doublefire>0) {
			Bullet[] bullet=new Bullet[2];
			bullet[0]=new Bullet(this.x+a,this.y );
			bullet[1]=new Bullet(this.x+a*3,this.y);
			doublefire--;
			return bullet;
		}
		if(doublefire==0) {
			Bullet[] bullet=new Bullet[1];
			bullet[0]=new Bullet(this.x+a*2,this.y );
			return bullet;
		}
		return null;
	}
	
	

public void sublife() {
	life--;
}
	

	@Override
	public void step() {

	}
	
public void addLife() {
	life++;
}
public void addDoubleFire() {
	doublefire++;
}
	
	public int getLife() {
		return life;
	}



	int index=0;
	public BufferedImage getImage() {
		index++;
		return images[index%2];

	}

	public void moveTo(int x, int y) {
		this.x=x-witch/2;
		this.y=y-height/2;
		
	}

}
