package com.woniuxy.tank.character;

import java.awt.Graphics;
import java.awt.Image;

import javax.swing.JPanel;

import com.woniuxy.tank.effect.Boom;
import com.woniuxy.tank.ui.MyGamePanel;
import com.woniuxy.tank.util.MusicUtil;

public class TankerBullet extends Bullet{
	
	public int id; // 子弹的编号（废弃
	private int count; // 辅助子弹速度
	protected int bulletSpeed = 10; // 用来控制子弹速度
	
	// 构造方法
	public TankerBullet(int x, int y, int direction, int step, int id) {
		super(x, y, direction, step);
		// TODO Auto-generated constructor stub
		this.id = id;
	}

	@Override
	public void synDirection(int d) {
		// TODO Auto-generated method stub
		
	}
	
	@Override
	public void draw(JPanel j, Image img, Graphics g, Image grass) {
		g.drawImage(img, x << 5, y << 5, (x + 1) << 5, (y + 1) << 5, 
				0, 0, 80, 48, j);
		underGrass(g,j,grass);
	}
	
	// 子弹碰撞效果
	@Override
	public boolean collision(int[][] map, int down, int left, int right, int up) {
		if(direction == 3) {
			super.collision(map, 0, 0, 0, 1);
		} else if(direction == 0) {
			super.collision(map, 1, 0, 0, 0);
		} else if(direction == 1) {
			super.collision(map, 0, 1, 0, 0);
		} else if(direction == 2) {
			super.collision(map, 0, 0, 1, 0);
		}
		
		
		Player player = MyGamePanel.player;
		if(player.x == x && player.y == y) {
			//this.isDrawBullet = false;
			MyGamePanel.booms.add(new Boom(x,y));
			MusicUtil.blast();
			MyGamePanel.tb.remove(this);
			MyGamePanel.playerIsLive = false;
			}
		
		return true;
	}
	
	// 方向的getter
	public int getTankerBulletDirection() {
		return this.direction;
	}
	
	@Override
	public void run() {
		while(isDrawBullet) {
			if(count < bulletSpeed) {
				count++;
			} else {
				count = 0;
				move();
			}
			try {
				Thread.sleep(12);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		MyGamePanel.tb.remove(this);
	}
}
