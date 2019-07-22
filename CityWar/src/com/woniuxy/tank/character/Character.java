package com.woniuxy.tank.character;

import java.awt.Graphics;
import java.awt.Image;

import javax.swing.JPanel;

import com.woniuxy.tank.ui.MyGamePanel;

/**
 * 
 * ���н�ɫ�ĸ���
 * @author Administrator
 *
 */
public class Character {
	
	protected int x; // ��ʾ��ͼ�ϵ�x����
	protected int y; // ��ʾ��ͼ�ϵ�y����
	protected int direction; // ԭͼƬ�ϵķ���
	protected int step; // ��ʾ��ʼ�Ų���ͼƬλ��,��ʾ��,��ֵ��ʾ�ĺ���: ��Һ͵з�̹�˶���ȡ��stepΪ7��ͼƬ
	
	// �����ж��ܲ���ǰ��
	public boolean allowMove = true;
	
	// ���췽��,���������ʱ��Ͱ�����ֵ���õ�
	public Character(int x,int y,int direction, int step) {
		this.x = x;
		this.y = y;
		this.direction = direction;
		this.step = step;
	}
	
	//����ͼƬ
	/**
	 * ����Ļ���ͼƬ����
	 * @param j ��ʾ������Ƶ����
	 * @param img ��ʾ�����Ƶ�ͼƬ
	 * @param g	��ʾ����
	 */
	public void draw(JPanel j,Image img, Graphics g, Image grass, Image action) {
		g.drawImage(img, x << 5, y << 5, (x + 1) << 5, (y + 1) << 5, 
				step * 32, direction * 48, (step + 1) * 32, (direction + 1) * 48, j);
		step();
	}
	
	// �ڲݴ�����
	public void underGrass(Graphics g,JPanel p,Image grass) {
		if(MyGamePanel.map[y][x] == 4) {
			g.drawImage(grass, (x << 5) - 3, (y << 5) - 3, 
					((x+1) << 5) + 3, ((y+1) << 5) + 3, 0, 0, 60, 60, p);
		}
	}
	
	
	/*
	 * �жϲ���
	 */
	public void step() {
		step++;
		if(step == 3) {
			step = 0;
		}
	}

}
