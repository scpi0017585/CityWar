package com.woniuxy.tank.ui;
import javax.swing.JFrame;

public class BeginClass{
	/**
	 *  Swing���
	 */
	public void init() {
		// ���ý�������
		JFrame j = new JFrame("̹�˴�ս");
		// ���ô�С
		j.setSize(600, 600);
		// ����λ��
		j.setLocation(300, 200);
		//���ÿɼ���
		j.setVisible(true);
	}
	
	public static void main(String[] args) {
		new BeginClass().init();
	}
}
