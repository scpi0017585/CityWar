package com.woniuxy.tank.ui;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.util.Vector;

import javax.imageio.ImageIO;
import javax.swing.*;

import com.woniuxy.tank.effect.Boom;
import com.woniuxy.tank.effect.Buff;
import com.woniuxy.tank.character.Bullet;
import com.woniuxy.tank.character.Player;
import com.woniuxy.tank.character.PlayerBullet;
import com.woniuxy.tank.character.Tanker;
import com.woniuxy.tank.character.TankerBullet;
import com.woniuxy.tank.map.Map;
import com.woniuxy.tank.util.MusicUtil;

public class MyGamePanel extends JPanel implements Runnable{
	public static MyGamePanel m; //��ǰ��JPanel
	// �����Image ����
	private Image brick; // ש��ͼ  2
	//private Image ground; 
	private Image grass; // ��ͼ 4
	private Image steel; // ��ͼ 1
	private Image water; // ˮͼ 3
	private Image boss; // ����ͼ 5
	private Image player_img; // ���ͼ 
	private Image tanker; // ̹��ͼ
	private Image star; //  ��ͼ
	private Image boom; // ��ը
	private Image attack;
	private Image arrow;
	private Image arrow_left;
	private Image arrow_right;
	private Image arrow_down;
	private Image ki; // ���˵Ĺ���
	private Image ki_left;
	private Image ki_right;
	private Image ki_down;
	private Image arrowUp; // Buff 1 ������ͼ
	private Image bomb; // Buff 0  ը��ͼ
	private Image gameOver; 
	private Image whiteFlag; // ����ͼ 6
	
	
	
	// ����player, X������,Y������, ������, stepͼ��
	public static Player player = new Player(7, 23, 3,0);
	// �����жϽ�ɫ�Ƿ���
	public static boolean playerIsLive = true;
	// ��ʣ������
	public static int life = 3;
	
	// �ؿ���
	public static int level = 1;
	
	// �趨����̹������
	private int tankConut;
	
	// �趨̹�˳���λ��
	private int position;
	
	// ����һ��List������ȡtank
	public static Vector<Tanker> tanks = new Vector<Tanker>();
	
	// ����һ��List�������buff
	public static Vector<Buff> buffs = new Vector<Buff>();
	
	// ����һ��̹�˵Ķ���
	//Tanker t = new Tanker(1, 1, 0, 1);
	//Tanker t2 = new Tanker(9,1,0,1);
	//Tanker t3 = new Tanker(17,1,0,1);
	
	// ��̹�˵Ķ��󸳸�List
	public void createTank() {
		// ���̹������
		if(tanks.size() < 5) {
			Tanker t = null;
			// ͨ��position����ȡx������λ��
			if(position > 2) {
				position = 0;
			}
			// ���������̹��֮���λ��Ϊ8x + 1
			int x_axis = position*8 + 1;
			t = new Tanker(x_axis,1,0,1,tanks.size());
			tanks.add(t);
			position++;
		}
	}
	
	// �������� ��ӡ��̹��
	public void drawTank(Graphics g) {
		for(int i = 0; i < tanks.size(); i++) {
			tanks.get(i).draw(this, tanker, g, grass, star);
		}
	}
	
	// ����Boom��
	//Boom booms = new Boom(2, 2);
	public static Vector<Boom> booms = new Vector<Boom>();
	
	
	// �����ӵ���,��List�洢����
	// Bullet b = new PlayerBullet( player.x , position, position, position);
	public static Vector<PlayerBullet> pb = new Vector<PlayerBullet>();
	
	// ����̹���ӵ���,��List�洢����
	public static Vector<TankerBullet> tb = new Vector<TankerBullet>();

	
	public Vector<PlayerBullet> getPlayerBullet() {
		return this.pb;
	}

	// ����ʧ�ܣ����к�ʤ���ı���
	// ...
	public static boolean isOver = false;
	public static boolean isWinner = false;
	public static boolean isRun = true;
	
	public static int[][] map;
	
	// ý�������
	public MediaTracker mt;
	

    /** */
    /**
     * ��תͼƬΪָ���Ƕ�
     *
     * @param bufferedimage Ŀ��ͼ��
     * @param degree        ��ת�Ƕ�
     * @return
     */
    public static BufferedImage rotateImage(final BufferedImage bufferedimage,
                                            final int degree) {
        int w = bufferedimage.getWidth();// �õ�ͼƬ��ȡ�
        int h = bufferedimage.getHeight();// �õ�ͼƬ�߶ȡ�
        int type = bufferedimage.getColorModel().getTransparency();// �õ�ͼƬ͸���ȡ�
        BufferedImage img;// �յ�ͼƬ��
        Graphics2D graphics2d;// �յĻ��ʡ�
        (graphics2d = (img = new BufferedImage(w, h, type))
                .createGraphics()).setRenderingHint(
                RenderingHints.KEY_INTERPOLATION,
                RenderingHints.VALUE_INTERPOLATION_BILINEAR);
        graphics2d.rotate(Math.toRadians(degree), w / 2, h / 2);// ��ת��degree�����ͣ����������紹ֱ90�ȡ�
        graphics2d.drawImage(bufferedimage, 0, 0, null);// ��bufferedimagecopyͼƬ��img��0,0��img�����ꡣ
        graphics2d.dispose();
        return img;// ���ظ��ƺõ�ͼƬ��ԭͼƬ��Ȼû�б䣬û����ת���´λ�����ʹ�á�
    }
	
	public MyGamePanel() {
		m=this;
		
		this.setBackground(Color.black);
		try{
			brick = ImageIO.read(new File("image/walls.gif"));
			//ground = ImageIO.read(new File("image/ground.png"));
			grass = ImageIO.read(new File("image/grass.gif"));
			//grass = Toolkit.getDefaultToolkit().createImage("image/grass.gif");
			water = ImageIO.read(new File("image/water.gif"));
			//water = Toolkit.getDefaultToolkit().createImage("image/water.gif");
			steel = ImageIO.read(new File("image/steels.gif"));
			boss = ImageIO.read(new File("image/symbol.gif"));
			player_img = ImageIO.read(new File("image/player_type4.png"));
			tanker = ImageIO.read(new File("image/tanker.png"));
			star = ImageIO.read(new File("image/star.jpg"));
			boom = ImageIO.read(new File("image/boom.png"));
			attack = ImageIO.read(new File("image/player_type3.png"));
			arrow = ImageIO.read(new File("image/arrow.png"));
			arrow_left = rotateImage((BufferedImage) arrow,90);
			arrow_down = rotateImage((BufferedImage) arrow,180);
			arrow_right = rotateImage((BufferedImage) arrow, 270);
			ki = ImageIO.read(new File("image/ki.png"));
			ki_left = rotateImage((BufferedImage) ki,270);
			ki_down = rotateImage((BufferedImage) ki,180);
			ki_right = rotateImage((BufferedImage) ki,90);
			arrowUp = ImageIO.read(new File("image/arrowNumber.png"));
			bomb = ImageIO.read(new File("image/bomb.gif"));
			gameOver = ImageIO.read(new File("image/over.gif"));
			whiteFlag = ImageIO.read(new File("image/destory.gif"));
			
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		mt = new MediaTracker(this);
		//mt.addImage(ground, 0);
		mt.addImage(steel, 1);
		mt.addImage(brick, 2);
		mt.addImage(water, 3);
		mt.addImage(grass, 4);
		mt.addImage(boss, 5);
		mt.addImage(player_img, 6);
		mt.addImage(tanker, 7);
		mt.addImage(star,8);
		mt.addImage(boom, 9);
		mt.addImage(attack, 10);
		mt.addImage(arrow, 11);
		mt.addImage(arrow_right, 12);
		mt.addImage(arrow_down, 13);
		mt.addImage(arrow_left, 14);
		mt.addImage(ki, 15);
		mt.addImage(ki_left, 16);
		mt.addImage(ki_down, 17);
		mt.addImage(ki_right, 18);
		mt.addImage(arrowUp, 19);
		mt.addImage(bomb, 20);
		mt.addImage(gameOver, 21);
		mt.addImage(whiteFlag, 22);
		
		// ��ʼ����ͼ
		map = Map.map;

		// �ȴ�����ͼƬ������
		// ....
		try{
			mt.waitForAll();
		} catch(InterruptedException e) {
			e.printStackTrace();
		}
		// ���س�ʼbuff
		/*
		buffs.add(new Buff(2,2,0));
		buffs.add(new Buff(16,20,0));
		buffs.add(new Buff(6,6,0));
		*/
		buffs.add(new Buff(2,10,1));
		
		// �۽�
		this.setFocusable(true);
		
		// ����keybroadListener
		this.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				// TODO Auto-generated method stub
				if(!isOver) player.move(e, map);
				if(!isOver) {
					player.shot(e, map, m , attack,getGraphics());
					//m.pb.add(new PlayerBullet(position, position, position, position));
				}
			}
		});
		
		// Ϊ��ǰ�࿪ʼ�̣߳� �ڴ�������ķ���ʱ�Ϳ����߳�
		new Thread(this).start();

	}
	
	public void paintComponent(Graphics g) {
		//���ø����еķ���
		super.paintComponent(g);
		// ����ͼ
		drawMap(g);
		//	�����
		player.draw(this, player_img, g,grass, attack);
		// ����ըЧ��
		drawTank(g);
		//booms.draw(boom, g, this);
		
		// ��buff
		for(int i = 0; i< buffs.size(); i++) {
			if(buffs.get(i).type == 0) {
				buffs.get(i).draw(arrowUp, g, this);
			} else if(buffs.get(i).type == 1) {
				buffs.get(i).draw(bomb, g, this);
			}
		}
		
		// ���ݳ���Ĳ�ͬ����ͬ�ļ�ͷ
		for(int i = 0; i < pb.size(); i++) {
			if(pb.get(i).getPlayerBulletDirection() == 3) {
				pb.get(i).draw(this, arrow, g, grass);
			} else if(pb.get(i).getPlayerBulletDirection() == 0) {
				pb.get(i).draw(this, arrow_down, g, grass);
			} else if (pb.get(i).getPlayerBulletDirection() == 1) {
				pb.get(i).draw(this, arrow_left, g, grass);
			} else if (pb.get(i).getPlayerBulletDirection() == 2) {
				pb.get(i).draw(this, arrow_right, g, grass);
			}
		}
		
		// ���з�̹�˵ļ�ͷ
		for(int i = 0; i < tb.size(); i++) {
			if(tb.get(i).getTankerBulletDirection() == 3) {
				tb.get(i).draw(this, ki, g, grass);
			} else if(tb.get(i).getTankerBulletDirection() == 0) {
				tb.get(i).draw(this, ki_down, g, grass);
			} else if (tb.get(i).getTankerBulletDirection() == 1) {
				tb.get(i).draw(this, ki_left, g, grass);
			} else if (tb.get(i).getTankerBulletDirection() == 2) {
				tb.get(i).draw(this, ki_right, g, grass);
			}
		}
		
		// ������ͼ�ϵı�ը
		for(int i = 0; i < booms.size(); i ++) {
			booms.get(i).draw(boom, g, this);
		}
		
		// ��Ϸ����ʱ����GameOver
		if(isOver) {
			drawGameOver(g);
		}
	}
	
	// ������ͼ
	private void drawMap(Graphics g) {
		for(int i = 0; i < Map.map.length; i++) {
			for(int j = 0; j < Map.map[i].length; j++) {
				if(Map.map[i][j] == 1) {
					g.drawImage(steel, j << 5,  i << 5, (j + 1) << 5 , (i + 1) << 5,
							0, 0, 60, 60, this);
				}
				else if(Map.map[i][j] == 2) {
					g.drawImage(brick, j << 5,  i << 5, (j + 1) << 5 , (i + 1) << 5,
							0, 0, 60, 60, this);
				}
				else if(Map.map[i][j] == 3) {
					g.drawImage(water, j << 5,  i << 5, (j + 1) << 5 , (i + 1) << 5,
							0, 0, 60, 60, this);
				}
				else if(Map.map[i][j] == 4) {
					g.drawImage(grass, j << 5,  i << 5, (j + 1) << 5 , (i + 1) << 5,
							0, 0, 60, 60, this);
				}
				else if(Map.map[i][j] == 5) {
					g.drawImage(boss, j << 5,  i << 5, (j + 1) << 5 , (i + 1) << 5,
							0, 0, 60, 60, this);
				}
				/*
				else if(Map.map[i][j] == 0) {
					g.drawImage(ground, j << 5,  i << 5, (j + 1) << 5 , (i + 1) << 5,
							0, 0, 140, 140, this);
				}
				*/
				else if(Map.map[i][j] == 6) {
					g.drawImage(whiteFlag, j << 5,  i << 5, (j + 1) << 5 , (i + 1) << 5,
							0, 0, 60, 60, this);
					isOver = true;// ����������죬��Ϸ����
					// ���������߳�
					for(int k = 0; k < tb.size(); k++) {
						tb.get(k).isDrawBullet = false;
					}
					for(int k = 0; k < tanks.size(); k++) {
						tanks.get(k).isLive = false;
					}
				}
			}
		}
	}
	
	// ��Ϸ������ʱ�򻭳���� GameOver
	public void drawGameOver(Graphics g) {
		g.drawImage(gameOver, 62,  170, 562 , 670,
				0, 0, 80, 45, this);
	}
	
	@Override
	public void run() {
		System.out.println("Run");
		while(!isOver){
			//�ж���Ϸ�Ƿ����
			if(life <= 0) {
				System.out.println("��Ϸ����");
				isOver = true;
				// ���������߳�
				for(int i = 0; i < tb.size(); i++) {
					tb.get(i).isDrawBullet = false;
				}
				for(int i = 0; i < tanks.size(); i++) {
					tanks.get(i).isLive = false;
				}
			}
			// ˢ�µ�ͼ
			this.repaint();
			// ����̹��
			createTank();
			
			// �������
			if(!playerIsLive && life > 0) {
				player = new Player(7, 23, 3,0);
				playerIsLive = true;
				life -= 1;
				System.out.println("�㻹��" + life + "����!");
			}
			
			//����70����
			try {
				Thread.sleep(70);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	
}
