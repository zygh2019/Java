package one;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.util.Random;

import javax.imageio.ImageIO;

public  abstract class FlyingObject {
protected int x;
protected int y;
protected int witch;
protected int height;
public final static int  LIFE=0;
public final static int REMOVE=1;
public final static int DEAD=2;
public  int state =LIFE;

public FlyingObject(int witch, int height) {
	Random ran=new Random();
	this.x = ran.nextInt(World.WITCH-witch);
	this.y = -height;
	this.witch = witch;
	this.height = height;
}
public FlyingObject() {

}
public FlyingObject(int x, int y, int witch, int height) {
	super();
	this.x = x;
	this.y = y;
	this.witch = witch;
	this.height = height;
}
public boolean hit(FlyingObject other ) {
	int x1=this.x;
	int y1=this.y;
	int x2=other.x+this.witch;
	int y2=other.y+this.height;
	
	return x2>=x1-other.witch&&x2<=x1+this.witch
			&&
			y2>=y1-other.height&&y2<=y1+this.height+other.height;
	
}

public boolean  isLife() {
	return state==LIFE;
}
public boolean isRemove() {
	return state==REMOVE;
}
public boolean isDead() {
	return state==DEAD;
}
public  boolean  outofBound() {
	return y>=World.HEIGHT;
	
}
public void goDead() {
	state=DEAD;
}
public void paintObject(Graphics g) {
	g.drawImage(getImage(),x,y,null);
	
}
public static BufferedImage readImage(String fileName) {
	try {
	BufferedImage img=ImageIO.read(FlyingObject.class.getResource(fileName));
	return img;
	}catch(Exception e) {
		e.printStackTrace();
		throw new RuntimeException(e);
	}
}
public abstract void step();
public abstract BufferedImage getImage();
}
