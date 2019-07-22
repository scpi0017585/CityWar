package com.woniuxy.tank.character;

import java.awt.Graphics;
import java.awt.Image;

import javax.swing.JPanel;

import com.woniuxy.tank.effect.Boom;
import com.woniuxy.tank.ui.MyGamePanel;
import com.woniuxy.tank.util.MusicUtil;

public abstract class Bullet extends Character implements Runnable{
	
	//�ж��Ƿ��ӵ��� �ӵ����ʱ��
	public boolean isDrawBullet = true;
	
	
	// �ӵ����ٶ�
	int speed;
	
	public Bullet(int x, int y, int direction, int step) {
		super(x, y, direction, step);
		synDirection(direction);
		// ��ʼ�߳�
		new Thread(this).start();
	}
	
	// ͬ���ӵ��ķ���
	public abstract void synDirection(int d);
	
	// �ӵ��ƶ�����
	
	public void move() {
		switch (direction) {
		case 0: // ����
			if(collision(MyGamePanel.map,1,0,0,0)) {
				y++;
			}
			break;
		case 1: // ����
			if(collision(MyGamePanel.map,0,1,0,0)) {
				x--;
			}
			break;
		case 2: //����
			if(collision(MyGamePanel.map,0,0,1,0)) {
				x++;
			}
			break;
		case 3: // ����
			if(collision(MyGamePanel.map,0,0,0,1)) {
				y--;
			}
			break;
		}
	}
	
	// ���ظ����е�draw����, �����ӵ�
	public void draw(JPanel j, Image img, Graphics g, Image grass) {
		g.drawImage(img, x << 5, y << 5, (x + 1) << 5, (y + 1) << 5, 
				0, 0, 14, 38, j);
		underGrass(g,j,grass);
	};
	
	public boolean collision(int[][] map,int down,int left, int right, int up) {		
		switch(map[y-up+down][x-left+right]) {
		case 1: //���иְ�
			isDrawBullet = false;
			MyGamePanel.pb.remove(this);
			MyGamePanel.tb.remove(this);
			MusicUtil.hit();
			return false;
		case 2: // �������ש��
			map[y-up+down][x-left+right] = 0; //��ש����½��
			isDrawBullet = false;
			MyGamePanel.pb.remove(this);
			MyGamePanel.tb.remove(this);
			return false;
		case 5: //��������
			isDrawBullet = false;
			MyGamePanel.pb.remove(this);
			MyGamePanel.tb.remove(this);
			MyGamePanel.booms.add(new Boom(x,y));
			MusicUtil.blast();
			// ��ɰ���
			map[y-up+down][x-left+right] = 6;
			// ������Ϸ
			//MyGamePanel.isOver = true;
			return false;
		}
		
		// �ӵ���ײ���ӵ���ʧ
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
			//System.out.println("����ķ���");
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
