package com.woniuxy.tank.character;

import com.woniuxy.tank.effect.Boom;
import com.woniuxy.tank.effect.Buff;
import com.woniuxy.tank.ui.MyGamePanel;
import com.woniuxy.tank.util.MusicUtil;
import java.util.*;

public class PlayerBullet extends Bullet{
	
	// 用于判断是否集中tank
	public boolean isHit = false;

	public PlayerBullet(int x, int y, int direction, int step) {
		super(x, y, direction, step);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void synDirection(int d) {
		// TODO Auto-generated method stub
		
	}
	
	@Override
	public boolean collision(int[][] map,int down, int left, int right, int up) {
		if(direction == 3) {
			super.collision(map, 0, 0, 0, 1);
		} else if(direction == 0) {
			super.collision(map, 1, 0, 0, 0);
		} else if(direction == 1) {
			super.collision(map, 0, 1, 0, 0);
		} else if(direction == 2) {
			super.collision(map, 0, 0, 1, 0);
		}
		
		for(int i = 0; i < MyGamePanel.tanks.size();i++) {
			Tanker tank = MyGamePanel.tanks.get(i);
			if(tank.x == x && tank.y == y && !tank.isDrawStar) {
				this.isDrawBullet = false;
				MyGamePanel.booms.add(new Boom(x,y));
				MusicUtil.blast();
				MyGamePanel.pb.remove(this);
				tank.isLive = false;
				MyGamePanel.tanks.remove(i);
				// 用于判断是否集中tank
				isHit = true;
			}
		}
		return true;
	}
	
	// 击毁坦克掉落buff的方法
	public void dropBuff() {
		Random r = new Random();
		Random x_axis = new Random();
		Random y_axis = new Random();
		int x_point = x_axis.nextInt(17) + 1;
		int y_point = y_axis.nextInt(24) + 1;
		
		int chance = r.nextInt(100); //代表一个总几率
		int hitPoint = MyGamePanel.map[y_point][x_point];
		//几率掉落buffer
		if(chance <= 10) {
			while(hitPoint != 0 && hitPoint != 4) {
				x_point = x_axis.nextInt(17) + 1;
				y_point = y_axis.nextInt(24) + 1;
				hitPoint = MyGamePanel.map[y_point][x_point];
			}
			// 掉落加子弹buff
			MyGamePanel.buffs.addElement(new Buff(x_point,y_point,0));
			System.out.println("子弹buff刷新！！");
		} else if(chance > 10 && chance <= 17) {
			while(hitPoint != 0 && hitPoint != 4) {
				x_point = x_axis.nextInt(17) + 1;
				y_point = y_axis.nextInt(24) + 1;
				hitPoint = MyGamePanel.map[y_point][x_point];
			}
			// 掉落清屏buff
			MyGamePanel.buffs.addElement(new Buff(x_point,y_point,1));
		}
		
	}

	public int getPlayerBulletDirection() {
		return this.direction;
	}
	
	@Override
	public void run() {
		// TODO Auto-generated method stub
		super.run();
		MyGamePanel.pb.remove(this);
		if(isHit) {
			dropBuff();
			isHit = false;
		}
	}
}
