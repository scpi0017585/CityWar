package com.woniuxy.tank.character;

import java.awt.Graphics;
import java.awt.Image;
import java.util.Random;

import javax.swing.JPanel;

import com.woniuxy.tank.map.Map;
import com.woniuxy.tank.ui.MyGamePanel;

public class Tanker extends Character implements Runnable{
	
	// ָ��tank���ƶ����
	public int speed;
	
	// ָ�����tank���ӵ�
	public static TankerBullet tb;
	
	// ָ��tank��id
	public int tank_id;
	
	// ���������
	Random ran = new Random();
	
	// �趨̹���߳�
	public static Thread tankerThread;
	
	// �жϵ�ǰ̹���Ƿ���
	public boolean isLive;
	
	// �趨���ǵ���ʽ
	private int starId;
	// �ж��Ƿ��������
	public boolean isDrawStar = true;
	
	public Tanker(int x, int y, int direction, int step,int tank_id) {
		super(x, y, direction, step);
		this.tank_id = tank_id;
		// ̹�˴��
		isLive = true;
		// ��ʼ̹�˵��߳�
		tankerThread = new Thread(this);
		tankerThread.start();
	}
	
	// ��д�����draw����
	public void draw(JPanel j, Image img, Graphics g, Image grass) {
		// ����̹��
		g.drawImage(img, x << 5, y << 5, (x+1) << 5, (y + 1) << 5,
				step << 5, direction << 5, (step + 1) << 5, (direction + 1) << 5,
				 j);
		super.step();
		super.underGrass(g, j, grass);
	};
	
	// �жϻ�̹�˻�������
	public void draw(JPanel j, Image img, Graphics g, Image grass, Image star) {
		if(isDrawStar) {
			drawStar(g,j,star);
		} else {
			draw(j,img,g,grass);
		}
		
	}
	
	// ��������,����˸Ч��
	public void drawStar(Graphics g, JPanel p, Image star) {
		if(starId >= 11) {
			starId = 0;
		}
		g.drawImage(star, x << 5, y << 5, (x+1) << 5, (y + 1) << 5,
				starId*192, 0, starId*192 + 192, 192, 
				p);
		starId++;
	}
	
	// �ƶ��ķ���
	public void move(int[][] map) {
		/*
		 *  ̹�˵��ƶ���Ϊ4������
		 */
		if(direction == 0) {
			for(int i = 0; i < MyGamePanel.tanks.size(); i++) {
				if((MyGamePanel.tanks.get(i).getX() == (this.x) && 
						MyGamePanel.tanks.get(i).getY() == (this.y + 1)) &&
						(MyGamePanel.player.getX() == this.x && 
						MyGamePanel.player.getY() == this.y + 1)) {
					// �Ϸ���̹�˵������Ӧ�ò�������
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
					// ��̹�˵����
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
					// ��̹�˵����
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
					// ��̹�˵����
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
		// �������ǵ�ʱ��
		int starTime = 0;
		// �����ʱ��
		int shotTime = 0;
		// ���̹�˴��ִ�����ѭ��
		while(isLive) {
			if(isDrawStar) {
				starTime++;
				// ����������5�ι���Ͳ���������
				if(starTime >= 10) {
					isDrawStar = false;
				}
			} else {
				// ���
				shotTime++;
				// ̹���ƶ�
				move(MyGamePanel.map);
				// ÿ��һ��ʱ����һ��
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
