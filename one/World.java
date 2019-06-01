package one;

import java.awt.Graphics;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.util.Arrays;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

import javax.swing.JFrame;
import javax.swing.JPanel;



public class World extends JPanel{
	private static  BufferedImage start;//开始图片
	private static  BufferedImage pause;//暂停图片
	private static BufferedImage gameover;//结束图片
	static {
		//将三个图片读取到图片对象中
		start=FlyingObject.readImage("start.png");
		pause=FlyingObject.readImage("pause.png");
		gameover=FlyingObject.readImage("gameover.png");
	}
	public final static int WITCH=400;
	public final static int HEIGHT=700;
	public  final static int START=0;
	public final static int RUNNING=1;
	public final static int PAUSE=2;
	public final static int GAME_OVER=3;
	public int state=START;
	public int score=0;
	Hero hero =new Hero();
	Sky sky=new Sky();
	FlyingObject [] enemy={};
	Bullet [] bullet = {};
	public void timer() {
		Timer t=new Timer();
		TimerTask tt=new TimerTask() {

			public void run() {
				if(state==RUNNING) {
					enemyenter();
					bulletenter();
					stepenter();
					herohitAction();
					outOfBound();
					checkGameOverAction();
					hitenter();
				}
				repaint();
			}
		};
		t.schedule(tt, 0,20);
	}
	public FlyingObject  onenext() {  //生成随机飞行物
		Random ran =new  Random();
		int a =ran.nextInt(30);
		if(a<5) {
			return new Bee();
		}else if(a<15) {
			return new Airplane();
		}else {
			return new BigAirplane();
		}
	}
	int index=0;
	public void enemyenter() { //飞行物入场
		index++;
		if(index%1==0) {
			enemy=Arrays.copyOf(enemy,enemy.length+1);
			enemy[enemy.length-1]=onenext();
		}
	}
	int index1=0;
	public void bulletenter() { //子弹入场
		index1++;
		Bullet [] b=hero.getBullet();
		if(index1%1==0) {
			bullet=Arrays.copyOf(bullet,bullet.length+b.length);
			System.arraycopy(b, 0, bullet, bullet.length-b.length, b.length);
		}
	}
	public void stepenter() {  //行走
		sky.step();

		for (int i = 0; i < bullet.length; i++) {
			bullet[i].step();	
		}
		for (int i = 0; i < enemy.length; i++) {
			enemy[i].step();
		}
	}
	public void  outOfBound() {
		int index2=0;//越界检测
		FlyingObject [] fly= new FlyingObject[enemy.length];
		for (int i = 0; i < fly.length; i++) {
			if(!enemy[i].outofBound()&&!enemy[i].isDead()) {
				fly[index2]=enemy[i];
				index2++;
			}
		}
		enemy=Arrays.copyOf(fly, index2);
		index2=0;//越界检测
		Bullet [] live= new Bullet[bullet.length];
		for (int i = 0; i < bullet.length; i++) {
			if(!bullet[i].outofBound()&&!bullet[i].isDead()) {
				live[index2]=bullet[i];
				index2++;
			}
		}
		bullet=Arrays.copyOf(live, index2);
	}
	public void herohitAction() {
		for (int i = 0; i < enemy.length; i++) {
			FlyingObject f = enemy[i];
			if(hero.isLife()&&f.isLife()&&f.hit(hero)) {
				f.goDead();
				hero.sublife();
				
			}
		}
	}
	public void hitenter() {
		for (int i = 0; i < bullet.length; i++) {
			Bullet b = bullet[i];
			for (int j = 0;  j< enemy.length; j++) {
				FlyingObject e = enemy[j];
				if(b.hit(e)&&b.isLife()&&e.isLife()) {
					b.goDead();

					e.goDead();
					if(e instanceof Score) {
						Score s=(Score)e;
						score+=s.getscore();
					}
					if(e instanceof Award) {
						Award a=(Award)e;
						switch (a.getAward()) {
							case  Award.DOUBLE_FIRE:
							hero.addDoubleFire();
							break;
							case Award.LIFE:
							hero.addLife();;
							break;

						}
					}
				}
			}
		}
	}
	public void paint(Graphics g) {
		sky.paintObject(g);  //画北京
		hero.paintObject(g);//画英雄
		for (int i = 0; i < bullet.length; i++) {  //画子弹
			bullet[i].paintObject(g);	
		}
		for (int i = 0; i <enemy.length; i++) {  //画飞行物
			enemy[i].paintObject(g);	
		}
		g.drawString("SCORE:"+score,10, 20);
		g.drawString("LIFE:"+hero.getLife(), 10,40);
		switch (state) {
		case START:
			g.drawImage(start,0,0,null);
			break;

		case PAUSE:
			g.drawImage(pause,0,0,null);
			break;

		case GAME_OVER:
			g.drawImage(gameover,0,0,null);
		}

	}

	public void checkGameOverAction() {
		//如果英雄机生命值为0
		if(hero.getLife()<=0) {
			state=GAME_OVER;
		}
	}
	public void action(){
		MouseAdapter l = new MouseAdapter() {  //鼠标   
			public void mouseMoved(MouseEvent e) { // 鼠标移动
				// 运行时移动英雄机
				int x = e.getX();
				int y = e.getY();
				if(state==RUNNING) {
					hero.moveTo(x, y);//鼠标的位置与英雄集对影响上
				}
			}
			//监听鼠标点击事件
			@Override
			public void mouseClicked(MouseEvent e) {
				// TODO Auto-generated method stub
				super.mouseClicked(e);
				switch (state) {
				case START:
					state=RUNNING;	
					break;
				case GAME_OVER: //如果是游戏结束状态
					state=START; //将状态改为开始
					//重置游戏窗口
					score=0;
					hero =new Hero();
					sky=new Sky();
					enemy=new FlyingObject[0];
					bullet =new Bullet[0];
					break;
				}
			}
			//监听鼠标移除事件
			@Override
			public void mouseExited(MouseEvent e) {
				//如果游戏运行时,鼠标移除
				if(state==RUNNING) {
					state=PAUSE;  //进入暂停状态

				}

			}
			public void mouseEntered(MouseEvent e) {
				if(state==PAUSE) {
					state=RUNNING; //游戏继续
				}
			}
		};
		this.addMouseListener(l); // 处理鼠标点击操作
		this.addMouseMotionListener(l);
		timer();
	}
	public static void main(String[] args) {
		World w=new World();
		//创建一个窗口标题为fly
		JFrame frame =new JFrame("Fly");
		//设置窗口的宽度
		frame.add(w);
		frame.setSize(WITCH,HEIGHT);
		//设置窗口关闭时程序结束
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		//设置窗口居中
		frame.setLocationRelativeTo(null);
		//窗口显示
		frame.setVisible(true);
		w.action();
	}
}
