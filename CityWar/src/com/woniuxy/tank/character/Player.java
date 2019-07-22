package com.woniuxy.tank.character;

import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.KeyEvent;

import javax.swing.JPanel;

import com.woniuxy.tank.ui.MyGamePanel;
import com.woniuxy.tank.util.MusicUtil;

public class Player extends Character implements Runnable{
	// 算射箭动作的时间 
	private int act;
	// 射箭的箭支数
	public int arrowNumber = 1;
	// 用于判断射箭还是行走
	public boolean isDrawAction = false;
	// 用于判断射箭的动作
	public boolean isShot = false;

	public Player(int x, int y, int direction, int step) {
		super(x, y, direction, step);
		// TODO Auto-generated constructor stub
		// 开启线程
		new Thread(this).start();
	}
	
	@Override
	public void draw(JPanel j,Image img, Graphics g, Image grass, Image action) {
		// 判断行走还是射击
		if(!isDrawAction) {
			g.drawImage(img, x << 5, y << 5, (x + 1) << 5, (y + 1) << 5, 
				step * 32, direction * 48, (step + 1) * 32, (direction + 1) * 48, j);
			super.step();
		} else {
			if(act < 3) {
				drawAction(j, action, g);
			} else {
				isDrawAction = false;
			}
			// 动作顺序
			act++;
			if(act > 3) {
				// 每一个循环重置动作
				act = 0;
				// 补充动作让图片不会闪烁
				g.drawImage(img, x << 5, y << 5, (x + 1) << 5, (y + 1) << 5, 
						step * 32, direction * 48, (step + 1) * 32, (direction + 1) * 48, j);
			}
		}
		underGrass(g,j,grass);
		//drawAction(j, action, g);
	}
	
	/**
	 * 
	 * @param e 表示键盘监听对象
	 * @param map 表示地图二维数组
	 * 0 ground 
	 * 1 steel
	 * 2 brick 
	 * 3 water 
	 * 4 grass 
	 * 5 boss
	 */
	public void move(KeyEvent e, int[][] map) {
		switch(e.getKeyCode()) {
		case KeyEvent.VK_W: 
			if(direction != 3) {
				direction = 3;
			} else {
				// 判断上方有没地方坦克
				for(int i = 0; i < MyGamePanel.tanks.size(); i++) {
					if(MyGamePanel.tanks.get(i).getX() == this.x && 
							MyGamePanel.tanks.get(i).getY() == (this.y - 1)) {
						// 上方有坦克的情况，应该不能往上
						allowMove = false;
					}
				}
				
				if(map[y-1][x] == 0 || map[y-1][x] == 4) {
					if(allowMove) {
						y -= 1;
					}
				}
				// 再次把能前进调正
				allowMove = true;
			}
			break;
		case KeyEvent.VK_S:
			if(direction != 0) {
				direction = 0;
			} else {
				for(int i = 0; i < MyGamePanel.tanks.size(); i++) {
					if(MyGamePanel.tanks.get(i).getX() == this.x && 
							MyGamePanel.tanks.get(i).getY() == (this.y + 1)) {
						// 上方有坦克的情况，应该不能往上
						allowMove = false;
					}
				}
				
				if(map[y+1][x] == 0 || map[y+1][x] == 4) {
					if(allowMove) {
					y += 1;
					}
				}
			}
			// 能前进
			allowMove = true;
			break;
		case KeyEvent.VK_A:
			if(direction != 1) {
				direction = 1;
			} else {
				for(int i = 0; i < MyGamePanel.tanks.size(); i++) {
					if(MyGamePanel.tanks.get(i).getX() == (this.x - 1) && 
							MyGamePanel.tanks.get(i).getY() == (this.y)) {
						// 上方有坦克的情况，应该不能往上
						allowMove = false;
					}
				}
				
				if(map[y][x-1] == 0 || map[y][x-1] == 4) {
					if(allowMove){
						x -= 1;
					}
				}
			}
			allowMove = true;
			break;
		case KeyEvent.VK_D:
			if(direction != 2) {
				direction = 2;
			} else {
				for(int i = 0; i < MyGamePanel.tanks.size(); i++) {
					if(MyGamePanel.tanks.get(i).getX() == this.x + 1 && 
							MyGamePanel.tanks.get(i).getY() == (this.y)) {
						// 上方有坦克的情况，应该不能往上
						allowMove = false;
					}
				}
				
				if(map[y][x+1] == 0 || map[y][x+1] == 4) {
					if(allowMove = true) {
						x += 1;
					}
				}
			}
			// 重置能前进
			allowMove = true;
			break;
		default:
			
		}
	}
	
	// 画出动作的方法
	public void drawAction(JPanel j,Image img, Graphics g) {
			if(act == 0) {
				g.drawImage(img, x << 5, y << 5, (x + 1) << 5, (y + 1) << 5, 
					9, direction * 50, 41, (direction + 1) * 50, j);
			} else if(act == 1) {
				g.drawImage(img, x << 5, y << 5, (x + 1) << 5, (y + 1) << 5, 
					59, direction * 50, 91, (direction + 1) * 50, j);
			} else if(act == 2) {
				g.drawImage(img, x << 5, y << 5, (x + 1) << 5, (y + 1) << 5, 
						109, direction * 50, 141, (direction + 1) * 50, j);
			} else if(act == 3) {
				g.drawImage(img, x << 5, y << 5, (x + 1) << 5, (y + 1) << 5, 
						159, direction * 50, 191, (direction + 1) * 50, j);
			}
	}
	
	// 射击的动作
	public void shot(KeyEvent e, int[][] map, JPanel j, Image img, Graphics g) {
		switch(e.getKeyCode()) {
			case KeyEvent.VK_J:
				// 限定一次只发射几发子弹,可由buffer 提升
				if(MyGamePanel.pb.size() < arrowNumber) {
					//执行射箭动作
					isDrawAction = true;
					// 把子弹作为对象存进List
					MyGamePanel.pb.add(new PlayerBullet(x,y,direction,step));
					isShot = true;
				}
				break;
			default:
				
		}
	}
	
	// getter of the player
	public int getX() {
		return this.x;
	}
	
	public int getY() {
		return this.y;
	}

	@Override
	public void run() {
		while(MyGamePanel.playerIsLive) {
			if(isShot) {
				// 射箭音效
				MusicUtil.shot();
				isShot = false;
			}
			// 线程休眠
			try {
				Thread.sleep(10);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
}
