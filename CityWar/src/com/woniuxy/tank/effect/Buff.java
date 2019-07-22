package com.woniuxy.tank.effect;

import java.awt.Graphics;
import java.awt.Image;
import java.util.Timer;

import javax.swing.JPanel;

import com.woniuxy.tank.map.Map;
import com.woniuxy.tank.ui.MyGamePanel;
import com.woniuxy.tank.util.MusicUtil;

public class Buff implements Runnable{
	public int x; //Buffˢ�µ�x��λ��
	public int y; //Buffˢ�µ�Y��λ��
	private boolean buffIsLive = true; //�����жϵ�ǰBuff�Ƿ�������߳���
	public int type; // �ж�Buff����� 0 ��ǿ�ӵ��� 1 ����
	
	// ���췽��
	public Buff(int x,int y,int type) {
		this.x = x;
		this.y = y;
		this.type = type; //����type�ж���ʲôbuff
		new Thread(this).start();
	}
	
	// �ڵ�ͼ�ϻ���Buff
	public void draw(Image img, Graphics g,JPanel p) {
		if(type == 0) {
			g.drawImage(img, x << 5, y << 5, (x + 1) << 5, (y + 1) << 5, 
					0, 0, 64, 64, p);
		} else if( type == 1) {
			g.drawImage(img, x << 5, y << 5, (x + 1) << 5, (y + 1) << 5, 
					0, 0, 40, 40, p);
		}
	}
	
	// �ж�����Ƿ��Buff�Ӵ�
	public void collision() {
		if(MyGamePanel.player.getX() == this.x && MyGamePanel.player.getY() == this.y){
			// Buff 0 �Ӽ���
			if(this.type == 0) {
				System.out.println("��ʸ����UP!!!");
				MusicUtil.add();
				MyGamePanel.player.arrowNumber++;
				// buffer��ʧ
				buffIsLive = false;
				// ��������ɾ�����buff
				MyGamePanel.buffs.remove(this);
			// Buff 1 �ݻ�����̹��
			} else if(this.type == 1 ) {
				System.out.println("��������������");
				// �����ķ���
				while(MyGamePanel.tanks.size() != 0) {
					//System.out.println(MyGamePanel.tanks.size());
					MyGamePanel.tanks.get(0).isLive = false;
					MyGamePanel.booms.add(new Boom(MyGamePanel.tanks.get(0).getX(),MyGamePanel.tanks.get(0).getY()));
					MyGamePanel.tanks.remove(0);
					MusicUtil.blast();
					buffIsLive = false;					// ��������ɾ�����buff
					MyGamePanel.buffs.remove(this);
				}
			}
		}
	}
	
	// δ���
	public void transWall(int wallType) {
		// 0�Ļ�ȫ��ɸ�ǽ
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
