package com.woniuxy.tank.ui;

import java.awt.*;
import java.net.MalformedURLException;

import javax.swing.JFrame;

import com.woniuxy.tank.util.MusicUtil;

public class GameFrame extends JFrame {
	public GameFrame() {
		//���ô�������
		super("̹�˴�ս");
		//���ô����С
		this.setSize(624,840);
		// ��ȡ��Ļ�ֱ��ʣ����ô������ 
		Dimension dm = Toolkit.getDefaultToolkit().getScreenSize();
		int width = (int) (dm.getWidth() - 624) / 2;
		int height = (int) (dm.getHeight() - 840) / 2;
		this.setLocation(width,height);
		this.setContentPane(new MyGamePanel());
		
		// ��ʾ����
		this.setVisible(true);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		// ��ʼ����
		try {
			MusicUtil.start();
		} catch (MalformedURLException e1) {
			e1.printStackTrace();
		}
	}
	
	public static void main(String[] args) {
		new GameFrame();
	}
}
