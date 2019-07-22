package com.woniuxy.tank.character;

import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.KeyEvent;

import javax.swing.JPanel;

import com.woniuxy.tank.ui.MyGamePanel;
import com.woniuxy.tank.util.MusicUtil;

public class Player extends Character implements Runnable{
	// �����������ʱ�� 
	private int act;
	// ����ļ�֧��
	public int arrowNumber = 1;
	// �����ж������������
	public boolean isDrawAction = false;
	// �����ж�����Ķ���
	public boolean isShot = false;

	public Player(int x, int y, int direction, int step) {
		super(x, y, direction, step);
		// TODO Auto-generated constructor stub
		// �����߳�
		new Thread(this).start();
	}
	
	@Override
	public void draw(JPanel j,Image img, Graphics g, Image grass, Image action) {
		// �ж����߻������
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
			// ����˳��
			act++;
			if(act > 3) {
				// ÿһ��ѭ�����ö���
				act = 0;
				// ���䶯����ͼƬ������˸
				g.drawImage(img, x << 5, y << 5, (x + 1) << 5, (y + 1) << 5, 
						step * 32, direction * 48, (step + 1) * 32, (direction + 1) * 48, j);
			}
		}
		underGrass(g,j,grass);
		//drawAction(j, action, g);
	}
	
	/**
	 * 
	 * @param e ��ʾ���̼�������
	 * @param map ��ʾ��ͼ��ά����
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
				// �ж��Ϸ���û�ط�̹��
				for(int i = 0; i < MyGamePanel.tanks.size(); i++) {
					if(MyGamePanel.tanks.get(i).getX() == this.x && 
							MyGamePanel.tanks.get(i).getY() == (this.y - 1)) {
						// �Ϸ���̹�˵������Ӧ�ò�������
						allowMove = false;
					}
				}
				
				if(map[y-1][x] == 0 || map[y-1][x] == 4) {
					if(allowMove) {
						y -= 1;
					}
				}
				// �ٴΰ���ǰ������
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
						// �Ϸ���̹�˵������Ӧ�ò�������
						allowMove = false;
					}
				}
				
				if(map[y+1][x] == 0 || map[y+1][x] == 4) {
					if(allowMove) {
					y += 1;
					}
				}
			}
			// ��ǰ��
			allowMove = true;
			break;
		case KeyEvent.VK_A:
			if(direction != 1) {
				direction = 1;
			} else {
				for(int i = 0; i < MyGamePanel.tanks.size(); i++) {
					if(MyGamePanel.tanks.get(i).getX() == (this.x - 1) && 
							MyGamePanel.tanks.get(i).getY() == (this.y)) {
						// �Ϸ���̹�˵������Ӧ�ò�������
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
						// �Ϸ���̹�˵������Ӧ�ò�������
						allowMove = false;
					}
				}
				
				if(map[y][x+1] == 0 || map[y][x+1] == 4) {
					if(allowMove = true) {
						x += 1;
					}
				}
			}
			// ������ǰ��
			allowMove = true;
			break;
		default:
			
		}
	}
	
	// ���������ķ���
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
	
	// ����Ķ���
	public void shot(KeyEvent e, int[][] map, JPanel j, Image img, Graphics g) {
		switch(e.getKeyCode()) {
			case KeyEvent.VK_J:
				// �޶�һ��ֻ���伸���ӵ�,����buffer ����
				if(MyGamePanel.pb.size() < arrowNumber) {
					//ִ���������
					isDrawAction = true;
					// ���ӵ���Ϊ������List
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
				// �����Ч
				MusicUtil.shot();
				isShot = false;
			}
			// �߳�����
			try {
				Thread.sleep(10);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
}
