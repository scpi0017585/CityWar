package com.woniuxy.tank.ui;
import javax.swing.JFrame;

public class BeginClass{
	/**
	 *  Swing框架
	 */
	public void init() {
		// 设置界面名字
		JFrame j = new JFrame("坦克大战");
		// 设置大小
		j.setSize(600, 600);
		// 设置位置
		j.setLocation(300, 200);
		//设置可见度
		j.setVisible(true);
	}
	
	public static void main(String[] args) {
		new BeginClass().init();
	}
}
