package com.woniuxy.tank.character;

import java.awt.Graphics;
import java.awt.Image;
import java.util.Random;

import javax.swing.JPanel;

import com.woniuxy.tank.map.Map;
import com.woniuxy.tank.ui.MyGamePanel;

public class Tanker extends Character implements Runnable{
	
	// 指定tank的移动间隔
	public int speed;
	
	// 指定这个tank的子弹
	public static TankerBullet tb;
	
	// 指定tank的id
	public int tank_id;
	
	// 设置随机数
	Random ran = new Random();
	
	// 设定坦克线程
	public static Thread tankerThread;
	
	// 判断当前坦克是否存活
	public boolean isLive;
	
	// 设定星星的样式
	private int starId;
	// 判断是否绘制星星
	public boolean isDrawStar = true;
	
	public Tanker(int x, int y, int direction, int step,int tank_id) {
		super(x, y, direction, step);
		this.tank_id = tank_id;
		// 坦克存活
		isLive = true;
		// 开始坦克的线程
		tankerThread = new Thread(this);
		tankerThread.start();
	}
	
	// 重写父类的draw方法
	public void draw(JPanel j, Image img, Graphics g, Image grass) {
		// 绘制坦克
		g.drawImage(img, x << 5, y << 5, (x+1) << 5, (y + 1) << 5,
				step << 5, direction << 5, (step + 1) << 5, (direction + 1) << 5,
				 j);
		super.step();
		super.underGrass(g, j, grass);
	};
	
	// 判断画坦克还是星星
	public void draw(JPanel j, Image img, Graphics g, Image grass, Image star) {
		if(isDrawStar) {
			drawStar(g,j,star);
		} else {
			draw(j,img,g,grass);
		}
		
	}
	
	// 绘制星星,带闪烁效果
	public void drawStar(Graphics g, JPanel p, Image star) {
		if(starId >= 11) {
			starId = 0;
		}
		g.drawImage(star, x << 5, y << 5, (x+1) << 5, (y + 1) << 5,
				starId*192, 0, starId*192 + 192, 192, 
				p);
		starId++;
	}
	
	// 移动的方法
	public void move(int[][] map) {
		/*
		 *  坦克的移动分为4个方向
		 */
		if(direction == 0) {
			for(int i = 0; i < MyGamePanel.tanks.size(); i++) {
				if((MyGamePanel.tanks.get(i).getX() == (this.x) && 
						MyGamePanel.tanks.get(i).getY() == (this.y + 1)) &&
						(MyGamePanel.player.getX() == this.x && 
						MyGamePanel.player.getY() == this.y + 1)) {
					// 上方有坦克的情况，应该不能往上
					allowMove = false;
				}
			}
			if(map[y+1][x] == 0 || map[y+1][x] == 4) {
				if(allowMove){
					y++;
				}
				direction = ran.nextInt(4);
			} else {
				direction = ran.nextInt(4);
			}
			allowMove = true;
		}
		else if(direction == 1) {
			for(int i = 0; i < MyGamePanel.tanks.size(); i++) {
				if((MyGamePanel.tanks.get(i).getX() == (this.x -1) && 
						MyGamePanel.tanks.get(i).getY() == (this.y)) &&
						(MyGamePanel.player.getX() == (this.x - 1) && 
						MyGamePanel.player.getY() == this.y)) {
					// 有坦克的情况
					allowMove = false;
				}
			}
			if(map[y][x-1] == 0 || map[y][x-1] == 4) {
				if(allowMove) {
					x--;
				}
				direction = ran.nextInt(4);
			} else {
				direction = ran.nextInt(4);
			}
			allowMove = true;
		}
		else if(direction == 2) {
			
			for(int i = 0; i < MyGamePanel.tanks.size(); i++) {
				if((MyGamePanel.tanks.get(i).getX() == (this.x + 1) && 
						MyGamePanel.tanks.get(i).getY() == (this.y)) &&
						(MyGamePanel.player.getX() == (this.x + 1) && 
						MyGamePanel.player.getY() == this.y)) {
					// 有坦克的情况
					allowMove = false;
				}
			}
			
			if(map[y][x+1] == 0 || map[y][x+1] == 4) {
				if(allowMove){
					x++;
				}
				direction = ran.nextInt(4);
			} else {
				direction = ran.nextInt(4);
			}
			allowMove = true;
		}
		else if(direction == 3) {
			
			for(int i = 0; i < MyGamePanel.tanks.size(); i++) {
				if((MyGamePanel.tanks.get(i).getX() == (this.x) && 
						MyGamePanel.tanks.get(i).getY() == (this.y - 1)) &&
						(MyGamePanel.player.getX() == (this.x) && 
						MyGamePanel.player.getY() == this.y - 1)) {
					// 有坦克的情况
					allowMove = false;
				}
			}
			
			if(map[y-1][x] == 0 || map[y-1][x] == 4 ) {
				if(allowMove){
					y--;
				}
				direction = ran.nextInt(4);
			} else {
				direction = ran.nextInt(4);
			}
			allowMove = true;
		}
	}
	
	// getter for tanker
	public int getX() {
		return this.x;
	}
	
	public int getY() {
		return this.y;
	}

	@Override
	public void run() {
		// 设置星星的时间
		int starTime = 0;
		// 射击的时间
		int shotTime = 0;
		// 如果坦克存活执行这个循环
		while(isLive) {
			if(isDrawStar) {
				starTime++;
				// 计数器到达5次过后就不画星星了
				if(starTime >= 10) {
					isDrawStar = false;
				}
			} else {
				// 射击
				shotTime++;
				// 坦克移动
				move(MyGamePanel.map);
				// 每隔一定时间射一箭
				if(shotTime > 3 ) {
					MyGamePanel.tb.add(tb = new TankerBullet(x,y,direction,step,tank_id));
					shotTime = 0;
				}
			}
			try {
				Thread.sleep(500);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

}
