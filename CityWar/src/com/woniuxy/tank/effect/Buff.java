package com.woniuxy.tank.effect;

import java.awt.Graphics;
import java.awt.Image;
import java.util.Timer;

import javax.swing.JPanel;

import com.woniuxy.tank.map.Map;
import com.woniuxy.tank.ui.MyGamePanel;
import com.woniuxy.tank.util.MusicUtil;

public class Buff implements Runnable{
	public int x; //Buff刷新的x轴位置
	public int y; //Buff刷新的Y轴位置
	private boolean buffIsLive = true; //用来判断当前Buff是否存活，用在线程中
	public int type; // 判断Buff的类别， 0 加强子弹， 1 清屏
	
	// 构造方法
	public Buff(int x,int y,int type) {
		this.x = x;
		this.y = y;
		this.type = type; //根据type判断是什么buff
		new Thread(this).start();
	}
	
	// 在地图上画出Buff
	public void draw(Image img, Graphics g,JPanel p) {
		if(type == 0) {
			g.drawImage(img, x << 5, y << 5, (x + 1) << 5, (y + 1) << 5, 
					0, 0, 64, 64, p);
		} else if( type == 1) {
			g.drawImage(img, x << 5, y << 5, (x + 1) << 5, (y + 1) << 5, 
					0, 0, 40, 40, p);
		}
	}
	
	// 判断玩家是否跟Buff接触
	public void collision() {
		if(MyGamePanel.player.getX() == this.x && MyGamePanel.player.getY() == this.y){
			// Buff 0 加箭数
			if(this.type == 0) {
				System.out.println("箭矢数量UP!!!");
				MusicUtil.add();
				MyGamePanel.player.arrowNumber++;
				// buffer消失
				buffIsLive = false;
				// 从数组中删除这个buff
				MyGamePanel.buffs.remove(this);
			// Buff 1 摧毁所有坦克
			} else if(this.type == 1 ) {
				System.out.println("清屏！！！！！");
				// 清屏的方法
				while(MyGamePanel.tanks.size() != 0) {
					//System.out.println(MyGamePanel.tanks.size());
					MyGamePanel.tanks.get(0).isLive = false;
					MyGamePanel.booms.add(new Boom(MyGamePanel.tanks.get(0).getX(),MyGamePanel.tanks.get(0).getY()));
					MyGamePanel.tanks.remove(0);
					MusicUtil.blast();
					buffIsLive = false;					// 从数组中删除这个buff
					MyGamePanel.buffs.remove(this);
				}
			}
		}
	}
	
	// 未完成
	public void transWall(int wallType) {
		// 0的话全变成刚墙
		if(wallType == 0) {
			//[7][23] [8][23] [9][23]
		} else if(wallType == 1) {
			
		}
	}

	@Override
	public void run() {
		while(buffIsLive) {
			collision();
			try {
				Thread.sleep(10);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

}
