package com.woniuxy.tank.character;

import java.awt.Graphics;
import java.awt.Image;

import javax.swing.JPanel;

import com.woniuxy.tank.effect.Boom;
import com.woniuxy.tank.ui.MyGamePanel;
import com.woniuxy.tank.util.MusicUtil;

public abstract class Bullet extends Character implements Runnable{
	
	//判断是否画子弹， 子弹存活时间
	public boolean isDrawBullet = true;
	
	
	// 子弹的速度
	int speed;
	
	public Bullet(int x, int y, int direction, int step) {
		super(x, y, direction, step);
		synDirection(direction);
		// 开始线程
		new Thread(this).start();
	}
	
	// 同步子弹的方向
	public abstract void synDirection(int d);
	
	// 子弹移动方向
	
	public void move() {
		switch (direction) {
		case 0: // 向下
			if(collision(MyGamePanel.map,1,0,0,0)) {
				y++;
			}
			break;
		case 1: // 向左
			if(collision(MyGamePanel.map,0,1,0,0)) {
				x--;
			}
			break;
		case 2: //向右
			if(collision(MyGamePanel.map,0,0,1,0)) {
				x++;
			}
			break;
		case 3: // 向上
			if(collision(MyGamePanel.map,0,0,0,1)) {
				y--;
			}
			break;
		}
	}
	
	// 重载父类中的draw方法, 画出子弹
	public void draw(JPanel j, Image img, Graphics g, Image grass) {
		g.drawImage(img, x << 5, y << 5, (x + 1) << 5, (y + 1) << 5, 
				0, 0, 14, 38, j);
		underGrass(g,j,grass);
	};
	
	public boolean collision(int[][] map,int down,int left, int right, int up) {		
		switch(map[y-up+down][x-left+right]) {
		case 1: //击中钢板
			isDrawBullet = false;
			MyGamePanel.pb.remove(this);
			MyGamePanel.tb.remove(this);
			MusicUtil.hit();
			return false;
		case 2: // 如果击中砖块
			map[y-up+down][x-left+right] = 0; //把砖块变成陆地
			isDrawBullet = false;
			MyGamePanel.pb.remove(this);
			MyGamePanel.tb.remove(this);
			return false;
		case 5: //击中老王
			isDrawBullet = false;
			MyGamePanel.pb.remove(this);
			MyGamePanel.tb.remove(this);
			MyGamePanel.booms.add(new Boom(x,y));
			MusicUtil.blast();
			// 变成白旗
			map[y-up+down][x-left+right] = 6;
			// 结束游戏
			//MyGamePanel.isOver = true;
			return false;
		}
		
		// 子弹碰撞，子弹消失
		for(int i = 0; i < MyGamePanel.pb.size(); i++) {
			for(int j = 0; j < MyGamePanel.tb.size(); j++) {
				Bullet pBullet = MyGamePanel.pb.get(i);
				Bullet tBullet = MyGamePanel.tb.get(j);
					if(pBullet.x == tBullet.x && pBullet.y == tBullet.y) {
						MyGamePanel.pb.get(i).isDrawBullet = false;
						MyGamePanel.tb.get(j).isDrawBullet = false;
						MyGamePanel.pb.remove(i);
						MyGamePanel.tb.remove(j);
						MyGamePanel.booms.add(new Boom(x,y));
						return false;
					}
				}
			}
		return true;
	}
	
	
	
	@Override
	public void run() {
		while(isDrawBullet) {
			//System.out.println("父类的方法");
			move();
			try {
				Thread.sleep(100);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

}
