package com.woniuxy.tank.ui;

import java.awt.*;
import java.net.MalformedURLException;

import javax.swing.JFrame;

import com.woniuxy.tank.util.MusicUtil;

public class GameFrame extends JFrame {
	public GameFrame() {
		//设置窗口名字
		super("坦克大战");
		//设置窗体大小
		this.setSize(624,840);
		// 获取屏幕分辩率，设置窗体居中 
		Dimension dm = Toolkit.getDefaultToolkit().getScreenSize();
		int width = (int) (dm.getWidth() - 624) / 2;
		int height = (int) (dm.getHeight() - 840) / 2;
		this.setLocation(width,height);
		this.setContentPane(new MyGamePanel());
		
		// 显示窗体
		this.setVisible(true);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		// 开始音乐
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
