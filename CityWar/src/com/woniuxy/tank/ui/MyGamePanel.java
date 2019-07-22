package com.woniuxy.tank.ui;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.util.Vector;

import javax.imageio.ImageIO;
import javax.swing.*;

import com.woniuxy.tank.effect.Boom;
import com.woniuxy.tank.effect.Buff;
import com.woniuxy.tank.character.Bullet;
import com.woniuxy.tank.character.Player;
import com.woniuxy.tank.character.PlayerBullet;
import com.woniuxy.tank.character.Tanker;
import com.woniuxy.tank.character.TankerBullet;
import com.woniuxy.tank.map.Map;
import com.woniuxy.tank.util.MusicUtil;

public class MyGamePanel extends JPanel implements Runnable{
	public static MyGamePanel m; //当前的JPanel
	// 所需的Image 对象
	private Image brick; // 砖块图  2
	//private Image ground; 
	private Image grass; // 草图 4
	private Image steel; // 钢图 1
	private Image water; // 水图 3
	private Image boss; // 老王图 5
	private Image player_img; // 玩家图 
	private Image tanker; // 坦克图
	private Image star; //  星图
	private Image boom; // 爆炸
	private Image attack;
	private Image arrow;
	private Image arrow_left;
	private Image arrow_right;
	private Image arrow_down;
	private Image ki; // 敌人的攻击
	private Image ki_left;
	private Image ki_right;
	private Image ki_down;
	private Image arrowUp; // Buff 1 提升箭图
	private Image bomb; // Buff 0  炸弹图
	private Image gameOver; 
	private Image whiteFlag; // 白旗图 6
	
	
	
	// 创建player, X轴坐标,Y轴坐标, 朝向方向, step图像
	public static Player player = new Player(7, 23, 3,0);
	// 用来判断角色是否存活
	public static boolean playerIsLive = true;
	// 还剩几条命
	public static int life = 3;
	
	// 关卡数
	public static int level = 1;
	
	// 设定击毁坦克数量
	private int tankConut;
	
	// 设定坦克出生位置
	private int position;
	
	// 创建一个List用来存取tank
	public static Vector<Tanker> tanks = new Vector<Tanker>();
	
	// 创建一个List用来存放buff
	public static Vector<Buff> buffs = new Vector<Buff>();
	
	// 创建一个坦克的对象
	//Tanker t = new Tanker(1, 1, 0, 1);
	//Tanker t2 = new Tanker(9,1,0,1);
	//Tanker t3 = new Tanker(17,1,0,1);
	
	// 把坦克的对象赋给List
	public void createTank() {
		// 最大坦克数量
		if(tanks.size() < 5) {
			Tanker t = null;
			// 通过position来获取x轴坐标位置
			if(position > 2) {
				position = 0;
			}
			// 算出来两个坦克之间的位置为8x + 1
			int x_axis = position*8 + 1;
			t = new Tanker(x_axis,1,0,1,tanks.size());
			tanks.add(t);
			position++;
		}
	}
	
	// 遍历数组 打印出坦克
	public void drawTank(Graphics g) {
		for(int i = 0; i < tanks.size(); i++) {
			tanks.get(i).draw(this, tanker, g, grass, star);
		}
	}
	
	// 创建Boom类
	//Boom booms = new Boom(2, 2);
	public static Vector<Boom> booms = new Vector<Boom>();
	
	
	// 创建子弹类,用List存储起来
	// Bullet b = new PlayerBullet( player.x , position, position, position);
	public static Vector<PlayerBullet> pb = new Vector<PlayerBullet>();
	
	// 创建坦克子弹类,用List存储起来
	public static Vector<TankerBullet> tb = new Vector<TankerBullet>();

	
	public Vector<PlayerBullet> getPlayerBullet() {
		return this.pb;
	}

	// 控制失败，运行和胜利的变量
	// ...
	public static boolean isOver = false;
	public static boolean isWinner = false;
	public static boolean isRun = true;
	
	public static int[][] map;
	
	// 媒体跟踪器
	public MediaTracker mt;
	

    /** */
    /**
     * 旋转图片为指定角度
     *
     * @param bufferedimage 目标图像
     * @param degree        旋转角度
     * @return
     */
    public static BufferedImage rotateImage(final BufferedImage bufferedimage,
                                            final int degree) {
        int w = bufferedimage.getWidth();// 得到图片宽度。
        int h = bufferedimage.getHeight();// 得到图片高度。
        int type = bufferedimage.getColorModel().getTransparency();// 得到图片透明度。
        BufferedImage img;// 空的图片。
        Graphics2D graphics2d;// 空的画笔。
        (graphics2d = (img = new BufferedImage(w, h, type))
                .createGraphics()).setRenderingHint(
                RenderingHints.KEY_INTERPOLATION,
                RenderingHints.VALUE_INTERPOLATION_BILINEAR);
        graphics2d.rotate(Math.toRadians(degree), w / 2, h / 2);// 旋转，degree是整型，度数，比如垂直90度。
        graphics2d.drawImage(bufferedimage, 0, 0, null);// 从bufferedimagecopy图片至img，0,0是img的坐标。
        graphics2d.dispose();
        return img;// 返回复制好的图片，原图片依然没有变，没有旋转，下次还可以使用。
    }
	
	public MyGamePanel() {
		m=this;
		
		this.setBackground(Color.black);
		try{
			brick = ImageIO.read(new File("image/walls.gif"));
			//ground = ImageIO.read(new File("image/ground.png"));
			grass = ImageIO.read(new File("image/grass.gif"));
			//grass = Toolkit.getDefaultToolkit().createImage("image/grass.gif");
			water = ImageIO.read(new File("image/water.gif"));
			//water = Toolkit.getDefaultToolkit().createImage("image/water.gif");
			steel = ImageIO.read(new File("image/steels.gif"));
			boss = ImageIO.read(new File("image/symbol.gif"));
			player_img = ImageIO.read(new File("image/player_type4.png"));
			tanker = ImageIO.read(new File("image/tanker.png"));
			star = ImageIO.read(new File("image/star.jpg"));
			boom = ImageIO.read(new File("image/boom.png"));
			attack = ImageIO.read(new File("image/player_type3.png"));
			arrow = ImageIO.read(new File("image/arrow.png"));
			arrow_left = rotateImage((BufferedImage) arrow,90);
			arrow_down = rotateImage((BufferedImage) arrow,180);
			arrow_right = rotateImage((BufferedImage) arrow, 270);
			ki = ImageIO.read(new File("image/ki.png"));
			ki_left = rotateImage((BufferedImage) ki,270);
			ki_down = rotateImage((BufferedImage) ki,180);
			ki_right = rotateImage((BufferedImage) ki,90);
			arrowUp = ImageIO.read(new File("image/arrowNumber.png"));
			bomb = ImageIO.read(new File("image/bomb.gif"));
			gameOver = ImageIO.read(new File("image/over.gif"));
			whiteFlag = ImageIO.read(new File("image/destory.gif"));
			
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		mt = new MediaTracker(this);
		//mt.addImage(ground, 0);
		mt.addImage(steel, 1);
		mt.addImage(brick, 2);
		mt.addImage(water, 3);
		mt.addImage(grass, 4);
		mt.addImage(boss, 5);
		mt.addImage(player_img, 6);
		mt.addImage(tanker, 7);
		mt.addImage(star,8);
		mt.addImage(boom, 9);
		mt.addImage(attack, 10);
		mt.addImage(arrow, 11);
		mt.addImage(arrow_right, 12);
		mt.addImage(arrow_down, 13);
		mt.addImage(arrow_left, 14);
		mt.addImage(ki, 15);
		mt.addImage(ki_left, 16);
		mt.addImage(ki_down, 17);
		mt.addImage(ki_right, 18);
		mt.addImage(arrowUp, 19);
		mt.addImage(bomb, 20);
		mt.addImage(gameOver, 21);
		mt.addImage(whiteFlag, 22);
		
		// 初始化地图
		map = Map.map;

		// 等待所有图片加载完
		// ....
		try{
			mt.waitForAll();
		} catch(InterruptedException e) {
			e.printStackTrace();
		}
		// 加载初始buff
		/*
		buffs.add(new Buff(2,2,0));
		buffs.add(new Buff(16,20,0));
		buffs.add(new Buff(6,6,0));
		*/
		buffs.add(new Buff(2,10,1));
		
		// 聚焦
		this.setFocusable(true);
		
		// 创建keybroadListener
		this.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				// TODO Auto-generated method stub
				if(!isOver) player.move(e, map);
				if(!isOver) {
					player.shot(e, map, m , attack,getGraphics());
					//m.pb.add(new PlayerBullet(position, position, position, position));
				}
			}
		});
		
		// 为当前类开始线程： 在创建该类的方法时就开启线程
		new Thread(this).start();

	}
	
	public void paintComponent(Graphics g) {
		//调用父类中的方法
		super.paintComponent(g);
		// 画地图
		drawMap(g);
		//	画玩家
		player.draw(this, player_img, g,grass, attack);
		// 画爆炸效果
		drawTank(g);
		//booms.draw(boom, g, this);
		
		// 画buff
		for(int i = 0; i< buffs.size(); i++) {
			if(buffs.get(i).type == 0) {
				buffs.get(i).draw(arrowUp, g, this);
			} else if(buffs.get(i).type == 1) {
				buffs.get(i).draw(bomb, g, this);
			}
		}
		
		// 根据朝向的不同画不同的箭头
		for(int i = 0; i < pb.size(); i++) {
			if(pb.get(i).getPlayerBulletDirection() == 3) {
				pb.get(i).draw(this, arrow, g, grass);
			} else if(pb.get(i).getPlayerBulletDirection() == 0) {
				pb.get(i).draw(this, arrow_down, g, grass);
			} else if (pb.get(i).getPlayerBulletDirection() == 1) {
				pb.get(i).draw(this, arrow_left, g, grass);
			} else if (pb.get(i).getPlayerBulletDirection() == 2) {
				pb.get(i).draw(this, arrow_right, g, grass);
			}
		}
		
		// 画敌方坦克的箭头
		for(int i = 0; i < tb.size(); i++) {
			if(tb.get(i).getTankerBulletDirection() == 3) {
				tb.get(i).draw(this, ki, g, grass);
			} else if(tb.get(i).getTankerBulletDirection() == 0) {
				tb.get(i).draw(this, ki_down, g, grass);
			} else if (tb.get(i).getTankerBulletDirection() == 1) {
				tb.get(i).draw(this, ki_left, g, grass);
			} else if (tb.get(i).getTankerBulletDirection() == 2) {
				tb.get(i).draw(this, ki_right, g, grass);
			}
		}
		
		// 画出地图上的爆炸
		for(int i = 0; i < booms.size(); i ++) {
			booms.get(i).draw(boom, g, this);
		}
		
		// 游戏结束时画出GameOver
		if(isOver) {
			drawGameOver(g);
		}
	}
	
	// 画出地图
	private void drawMap(Graphics g) {
		for(int i = 0; i < Map.map.length; i++) {
			for(int j = 0; j < Map.map[i].length; j++) {
				if(Map.map[i][j] == 1) {
					g.drawImage(steel, j << 5,  i << 5, (j + 1) << 5 , (i + 1) << 5,
							0, 0, 60, 60, this);
				}
				else if(Map.map[i][j] == 2) {
					g.drawImage(brick, j << 5,  i << 5, (j + 1) << 5 , (i + 1) << 5,
							0, 0, 60, 60, this);
				}
				else if(Map.map[i][j] == 3) {
					g.drawImage(water, j << 5,  i << 5, (j + 1) << 5 , (i + 1) << 5,
							0, 0, 60, 60, this);
				}
				else if(Map.map[i][j] == 4) {
					g.drawImage(grass, j << 5,  i << 5, (j + 1) << 5 , (i + 1) << 5,
							0, 0, 60, 60, this);
				}
				else if(Map.map[i][j] == 5) {
					g.drawImage(boss, j << 5,  i << 5, (j + 1) << 5 , (i + 1) << 5,
							0, 0, 60, 60, this);
				}
				/*
				else if(Map.map[i][j] == 0) {
					g.drawImage(ground, j << 5,  i << 5, (j + 1) << 5 , (i + 1) << 5,
							0, 0, 140, 140, this);
				}
				*/
				else if(Map.map[i][j] == 6) {
					g.drawImage(whiteFlag, j << 5,  i << 5, (j + 1) << 5 , (i + 1) << 5,
							0, 0, 60, 60, this);
					isOver = true;// 如果画到白旗，游戏结束
					// 结束所有线程
					for(int k = 0; k < tb.size(); k++) {
						tb.get(k).isDrawBullet = false;
					}
					for(int k = 0; k < tanks.size(); k++) {
						tanks.get(k).isLive = false;
					}
				}
			}
		}
	}
	
	// 游戏结束的时候画出这个 GameOver
	public void drawGameOver(Graphics g) {
		g.drawImage(gameOver, 62,  170, 562 , 670,
				0, 0, 80, 45, this);
	}
	
	@Override
	public void run() {
		System.out.println("Run");
		while(!isOver){
			//判断游戏是否结束
			if(life <= 0) {
				System.out.println("游戏结束");
				isOver = true;
				// 结束所有线程
				for(int i = 0; i < tb.size(); i++) {
					tb.get(i).isDrawBullet = false;
				}
				for(int i = 0; i < tanks.size(); i++) {
					tanks.get(i).isLive = false;
				}
			}
			// 刷新地图
			this.repaint();
			// 加载坦克
			createTank();
			
			// 重生玩家
			if(!playerIsLive && life > 0) {
				player = new Player(7, 23, 3,0);
				playerIsLive = true;
				life -= 1;
				System.out.println("你还有" + life + "条命!");
			}
			
			//休眠70毫秒
			try {
				Thread.sleep(70);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	
}
