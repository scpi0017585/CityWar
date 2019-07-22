package com.woniuxy.tank.character;

import java.awt.Graphics;
import java.awt.Image;

import javax.swing.JPanel;

import com.woniuxy.tank.ui.MyGamePanel;

/**
 * 
 * 所有角色的父类
 * @author Administrator
 *
 */
public class Character {
	
	protected int x; // 表示地图上的x坐标
	protected int y; // 表示地图上的y坐标
	protected int direction; // 原图片上的方向
	protected int step; // 表示起始脚步的图片位置,表示列,赋值表示的含义: 玩家和敌方坦克都是取的step为7的图片
	
	// 用来判断能不能前进
	public boolean allowMove = true;
	
	// 构造方法,创建对象的时候就把坐标值给拿到
	public Character(int x,int y,int direction, int step) {
		this.x = x;
		this.y = y;
		this.direction = direction;
		this.step = step;
	}
	
	//绘制图片
	/**
	 * 父类的绘制图片方法
	 * @param j 表示所需绘制的面板
	 * @param img 表示所绘制的图片
	 * @param g	表示画笔
	 */
	public void draw(JPanel j,Image img, Graphics g, Image grass, Image action) {
		g.drawImage(img, x << 5, y << 5, (x + 1) << 5, (y + 1) << 5, 
				step * 32, direction * 48, (step + 1) * 32, (direction + 1) * 48, j);
		step();
	}
	
	// 在草丛里面
	public void underGrass(Graphics g,JPanel p,Image grass) {
		if(MyGamePanel.map[y][x] == 4) {
			g.drawImage(grass, (x << 5) - 3, (y << 5) - 3, 
					((x+1) << 5) + 3, ((y+1) << 5) + 3, 0, 0, 60, 60, p);
		}
	}
	
	
	/*
	 * 判断步伐
	 */
	public void step() {
		step++;
		if(step == 3) {
			step = 0;
		}
	}

}
