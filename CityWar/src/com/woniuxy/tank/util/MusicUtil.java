package com.woniuxy.tank.util;

import java.applet.Applet;
import java.applet.AudioClip;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.MalformedURLException;

import sun.audio.AudioPlayer;


public class MusicUtil {   
	// 开始游戏音效
   public static void start() throws MalformedURLException { 
	 try {
		 FileInputStream fileInputStream = new FileInputStream(new File("music/start3.wav"));
		AudioPlayer.player.start(fileInputStream);
		try {
			Thread.sleep(5000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		AudioPlayer.player.stop(fileInputStream);
	} catch (FileNotFoundException e) {
		
		e.printStackTrace();
	}
   }
   
   // 射箭音效
   public static void shot() {
	   try {
			 FileInputStream fileInputStream = new FileInputStream(new File("music/shot.wav"));
			AudioPlayer.player.start(fileInputStream);
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			AudioPlayer.player.stop(fileInputStream);
		} catch (FileNotFoundException e) {
			
			e.printStackTrace();
		}
   }
   
   // 砖块，子弹碰撞音效
   public static void hit(){
	   try {
			 FileInputStream fileInputStream = new FileInputStream(new File("image/hit.wav"));
			AudioPlayer.player.start(fileInputStream);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
   }
   
   // 爆炸音
   public static void blast() {
	   try {
			 FileInputStream fileInputStream = new FileInputStream(new File("image/blast.wav"));
			AudioPlayer.player.start(fileInputStream);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
   }
   
   // 得到buff音效
   public static void add() {
	   try {
			 FileInputStream fileInputStream = new FileInputStream(new File("image/add.wav"));
			AudioPlayer.player.start(fileInputStream);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
   }
}
