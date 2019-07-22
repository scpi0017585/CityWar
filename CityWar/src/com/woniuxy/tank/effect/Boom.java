package com.woniuxy.tank.effect;

import java.awt.Graphics;
import java.awt.Image;

import javax.swing.JPanel;

import com.woniuxy.tank.ui.MyGamePanel;

public class Boom implements Runnable{
	public int x; // ��ըλ�õ�X��
	public int y; // ��ըλ�õ�Y��
	private boolean boomIsLive = true; // �����жϵ�ǰ��ը�Ƿ����
	public int i; // �������Ʊ�ը��ʱ��
	
	public Boom(int x,int y) {
		this.x = x;
		this.y = y;
		new Thread(this).start();
	}
	
	public void draw(Image img, Graphics g,JPanel p) {
		g.drawImage(img, x << 5, y << 5, (x + 1) << 5, (y + 1) << 5, 
				64*i, 0, 64*(i+1), 64, p);
	}

	@Override
	public void run() {
		// TODO Auto-generated method stub
		while(boomIsLive) {
			i++;
			if(i > 4) {
				i = 0;
				MyGamePanel.booms.remove(this);
				boomIsLive = false;
			}
			try {
				Thread.sleep(150);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
}