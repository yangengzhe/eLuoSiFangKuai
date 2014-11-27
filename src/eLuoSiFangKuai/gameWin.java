package eLuoSiFangKuai;

import javax.swing.*;
import javax.swing.Timer;

import sun.audio.AudioPlayer;
import sun.audio.AudioStream;

import java.awt.*;
import java.awt.event.*;
import java.io.FileInputStream;
import java.math.BigDecimal;
import java.util.*;
import java.util.List;

/**
 * �Զ��崰�������   �̳���JPanel  ��������Ϊ����  ���̼���
 * @author ALL
 * 
 */
public class gameWin extends JPanel implements ActionListener, KeyListener {
	JButton newGame = set_button("��ʼ", new ImageIcon("src/img/b1.png"));// ������ʼ��ť
	JButton pauseGame = set_button("��ͣ", new ImageIcon("src/img/b3.png"));// ������ͣ��ť
	JButton endGame = set_button("�˳�", new ImageIcon("src/img/b5.png"));// �����˳���ť
	JButton vs = set_button("��ս", new ImageIcon("src/img/b4.png"));// ������ս��ť
	JButton closeSong = set_button("", new ImageIcon("src/img/music.png"));// �����ر���Ч��ť
	JButton closeBackgroundSong = set_button("", new ImageIcon("src/img/bgmusic.png"));// �����ر���Ч��ť
	JButton help=set_button("",new ImageIcon("src/img/bhelp.png"));//������ť
	JButton help_close=set_button("",null);//�����Ĺرհ�ť
	int speed = 0, tFenShu = 0;// ���� ���� �� �ٶȱ���
	public static int fenShu = 0;
	Random r = new Random();// ���������������������ɷ������� ����
	// gameActΪ�Զ��巽�������� �洢x��y����
	gameAct[] act = new gameAct[4];// ÿ������ռ4��
	gameAct[] act2 = new gameAct[4];// ÿ������ռ4��
	byte[][] map = new byte[10][18];// ����������꣬���������������λ��

	public static int shu;// �Լ�����������
	public static int vsShu;// ���ַ�����
	boolean shuGrowth = false;// ������λ��

	// ///////////////////////////////////////
	public static Thread t_sever;// �������߳�
	public static Thread t_client;// �ͻ����߳�
	public static Sever_Socket SS;// ������Socket
	public static int vs_fenshu = 0;// ���ַ���
	public static byte[][] vsMap = new byte[10][18];// ���ն��ֵ�����
	public static byte[][] sendMap = new byte[10][18];// �ϳ������Լ�������
	public static byte[][] sendMap2 = new byte[10][18];// �������͵�����
	// //////////////////////////////////////////////
	public static boolean start = false;// ������Ϸ״̬��ʶ
	boolean end_Game = false;// ��Ϸ������־
	public static boolean vsStart = true;// ������Ϸ״̬��ʶ
	Timer t;// ʱ�������
	byte temp;// ��һ������ķ�������
	byte last_temp;// ��ǰ�����ķ�������
	/** ��սDialog **/
	public static gameDialogVS vsDialog = new gameDialogVS();// ��ս�Ի���
	JButton vsButton = new JButton("");// ȷ�ϰ�ť
	JButton qxButton = new JButton("");// ȡ����ť
	Image fangKuai;
	Image leverup=new ImageIcon("src/img/leverup.png").getImage();
	Image helping=new ImageIcon("src/img/helping.png").getImage();
	boolean keyLock = false;// ������ ������ͣ
	boolean kaiShi = false;// ���ڿ�ʼ�� ʱ��ͣ�����ı�
	boolean gaibian[] = { false, false, false, false, false, false, false };
	AudioStream b_as;
	bgSong bgs = new bgSong();//�������ֵ��߳�
	boolean b_Song = true;// ���ڿ�����Ч
	boolean b_BackSong = true;// ���ڿ��Ʊ�������
	boolean c_help=false;//��ʾ����
	boolean LeverUp=false;//���ڿ�����ʾ����
	//���а�
	JButton JB_list = set_button("���а�",null);// ������ʼ��ť
	public static gameDialogScore ScoreDialog = new gameDialogScore();// ���а�Ի���
	
	/**
	 * gameWin���캯��
	 */
	public gameWin() {
		// �Է����ʼ��
		for (int i = 0; i < 4; i++) {
			act[i] = new gameAct();
			act2[i] = new gameAct();
		}
		// ���������ʼ��
		for (int i = 0; i < 10; i++) {
			for (int j = 0; j < 18; j++) {
				map[i][j] = 7;
				vsMap[i][j] = 7;
				sendMap[i][j] = 7;
				sendMap2[i][j] = 7;
			}
		}
		bgs.start();//������������
		setLayout(null);// ���ò���
		add(newGame);// ��Ӱ�ť������
		set_button_style(newGame, 10, 570, 100, 100);
		add(pauseGame);
		set_button_style(pauseGame, 100, 570, 100, 100);
		add(vs);
		set_button_style(vs, 200, 570, 100, 100);
		add(endGame);
		set_button_style(endGame, 300, 570, 100, 100);
		add(closeSong);
		set_button_style(closeSong, 405, 570, 50, 100);
		add(closeBackgroundSong);
		set_button_style(closeBackgroundSong, 460, 570, 50, 100);
		add(help);
		help.addMouseListener(new MouseListener() {//������ť�ļ���
			
			@Override
			public void mouseReleased(MouseEvent arg0) {
				
			}
			
			@Override
			public void mousePressed(MouseEvent arg0) {
				
			}
			
			@Override
			public void mouseExited(MouseEvent arg0) {
				help.setIcon(new ImageIcon("src/img/bhelp.png"));
			}
			
			@Override
			public void mouseEntered(MouseEvent arg0) {
			   help.setIcon(new ImageIcon("src/img/bhelp2.png"));
			}
			
			@Override
			public void mouseClicked(MouseEvent arg0) {
				help.setIcon(new ImageIcon("src/img/bhelp.png"));
			}
		});
		set_button_style(help, 750, 570, 100, 100);
		add(help_close);
		set_button_style(help_close,610, 420, 95, 25);
		closeSong.addActionListener(this);
		closeBackgroundSong.addActionListener(this);
		newGame.addActionListener(this);// ��ť��Ӽ���
		endGame.addActionListener(this);
		pauseGame.addActionListener(this);
		help.addActionListener(this);
		help_close.addActionListener(this);
		vs.addActionListener(this);
		addKeyListener(this);// ������Ӽ���
		// ���ö�ս���ڲ���
		vsDialog.set(vsButton, qxButton, this);
		vsDialog.setVisible(false);
		// �������а�
		add(JB_list);// ��Ӱ�ť������
		set_button_style(JB_list, 370, 10, 90, 40);
		JB_list.addActionListener(this);
	}

	// ����
	public void paintComponent(Graphics g) {
		super.paintComponent(g);// ���ø���

		g.drawImage(new ImageIcon("src/img/main.jpg").getImage(), 0, 0, null);// ����ͼƬ
		g.setFont(new java.awt.Font("Algerian", 0, 30));// ��dialog���������壬1������ʽ(1�Ǵ��壬0��ƽ���ģ�15���ֺ�
		g.setColor(Color.white);// ������ɫ
		g.drawString(fenShu + "", 390, 345);// ����
		g.drawString(vs_fenshu + "", 700, 630);// ���ַ���
		g.drawString(speed + "", 405, 470);// �ٶ�
		// ///////////////////////////////////////

		// �����Ϸ״̬��ʶΪ��ʼ
		if (start == true || end_Game) {// ��Ϸ��ʼ������Ϸ������ͣ�������ջ���
			// ����4��
			for (int i = 0; i < 4; i++) {
				if (last_temp >= 0 && last_temp < 7) {
					fangKuai = new ImageIcon("src/img/" + last_temp + ".png")
							.getImage();
					sendMap[act[i].x][act[i].y] = last_temp;
				}
				g.drawImage(fangKuai, 25 + act[i].x * 30, 15 + act[i].y * 30,
						30, 30, null);
			}
			for (int i = 0; i < 10; i++)
				for (int j = 0; j < 18; j++)
					gameWin.sendMap2[i][j] = gameWin.sendMap[i][j];// ���ƴ��͵�����

			// �����ַ���
			for (int i = 0; i < 10; i++) {
				for (int j = 0; j < 18; j++) {
					if (vsMap[i][j] < 7 && vsMap[i][j] >= 0 || vsMap[i][j] == 8) {
						fangKuai = new ImageIcon("src/img/" + vsMap[i][j]
								+ ".png").getImage();
						g.drawImage(fangKuai, 505 + i * 30, 15 + j * 30, 30,
								30, null);
					}
				}
			}
			// ���������������λ��
			for (int i = 0; i < 10; i++) {
				for (int j = 0; j < 18; j++) {
					sendMap[i][j] = map[i][j];
					if (map[i][j] < 7 && map[i][j] >= 0 || map[i][j] == 8) {
						fangKuai = new ImageIcon("src/img/" + map[i][j]
								+ ".png").getImage();
						g.drawImage(fangKuai, 25 + i * 30, 15 + j * 30, 30, 30,
								null);
					}
				}
			}
			// ������һ��
			g.setColor(new Color(0, 0, 255));
			for (int i = 0; i < 4; i++) {
				if (temp == 0) {
					fangKuai = new ImageIcon("src/img/0.png").getImage();
					g.drawImage(fangKuai, 325 + act2[i].x * 30,
							130 + act2[i].y * 30, 30, 30, null);
				}
				if (temp == 1) {
					fangKuai = new ImageIcon("src/img/1.png").getImage();
					g.drawImage(fangKuai, 352 + act2[i].x * 30,
							115 + act2[i].y * 30, 30, 30, null);
				}
				if (temp == 2) {
					fangKuai = new ImageIcon("src/img/2.png").getImage();
					g.drawImage(fangKuai, 340 + act2[i].x * 30,
							110 + act2[i].y * 30, 30, 30, null);
				}
				if (temp == 3) {
					fangKuai = new ImageIcon("src/img/3.png").getImage();
					g.drawImage(fangKuai, 340 + act2[i].x * 30,
							110 + act2[i].y * 30, 30, 30, null);
				}
				if (temp == 4) {
					fangKuai = new ImageIcon("src/img/4.png").getImage();
					g.drawImage(fangKuai, 340 + act2[i].x * 30,
							110 + act2[i].y * 30, 30, 30, null);
				}
				if (temp == 5) {
					fangKuai = new ImageIcon("src/img/5.png").getImage();
					g.drawImage(fangKuai, 340 + act2[i].x * 30,
							100 + act2[i].y * 30, 30, 30, null);
				}
				if (temp == 6) {
					fangKuai = new ImageIcon("src/img/6.png").getImage();
					g.drawImage(fangKuai, 355 + act2[i].x * 30,
							100 + act2[i].y * 30, 30, 30, null);
				}

			}
		}
		// ������Ϸ�ķ�����ʾ
		if (end_Game) {
			g.drawImage(new ImageIcon("src/img/score.png").getImage(), 38, 100,
					276, 300, null);
			g.setColor(Color.white);
			g.drawString(fenShu + "", 150, 275);// ����
		}
		// ��ͣ������ʾ
		else if (keyLock) {
			g.drawImage(new ImageIcon("src/img/stop.png").getImage(), 38, 100,
					276, 300, null);
		}
		// ����δ���ӽ���
		if (gameSocket.con <= 0) {
			g.drawImage(new ImageIcon("src/img/nocon.png").getImage(), 504, 15,
					300, 540, null);
		}
		// ���ֽ�����Ϸ
		else if (gameWin.vsStart == false) {
			g.drawImage(new ImageIcon("src/img/score.png").getImage(), 518,
					100, 276, 300, null);
			g.setColor(Color.white);
			g.drawString(vs_fenshu + "", 630, 275);// ����
		}
		if(LeverUp){
			g.drawImage(leverup, 70, 70, 200,150, null);
            LeverUp=false;
		}
		if(c_help){
			g.drawImage(helping,515,55,281,450,null);
			
		}	
	}

	// �²�������
	private boolean newAct() {
		vsShu++;
		if (b_Song) {//�����·��������
			FileInputStream fis;
			try {
				fis = new FileInputStream("src/songs/add.wav");
				AudioStream as = new AudioStream(fis);// ����
				AudioPlayer.player.start(as);
			} catch (Exception e) {
			}
		}
		switch (temp) {
		case 0:
			act[0].x = 1;
			act[0].y = 0;
			act[1].x = 2;
			act[1].y = 0;
			act[2].x = 3;
			act[2].y = 0;
			act[3].x = 4;
			act[3].y = 0;
			break;
		case 1:
			act[0].x = 1;
			act[0].y = 0;
			act[1].x = 2;
			act[1].y = 0;
			act[2].x = 1;
			act[2].y = 1;
			act[3].x = 2;
			act[3].y = 1;
			break;
		case 2:
			act[0].x = 1;
			act[0].y = 0;
			act[1].x = 2;
			act[1].y = 0;
			act[2].x = 2;
			act[2].y = 1;
			act[3].x = 3;
			act[3].y = 1;
			break;
		case 3:
			act[0].x = 1;
			act[0].y = 1;
			act[1].x = 2;
			act[1].y = 0;
			act[2].x = 2;
			act[2].y = 1;
			act[3].x = 3;
			act[3].y = 0;
			break;
		case 4:
			act[0].x = 1;
			act[0].y = 0;
			act[1].x = 2;
			act[1].y = 0;
			act[2].x = 3;
			act[2].y = 0;
			act[3].x = 2;
			act[3].y = 1;
			break;
		case 5:
			act[0].x = 1;
			act[0].y = 0;
			act[1].x = 2;
			act[1].y = 0;
			act[2].x = 2;
			act[2].y = 1;
			act[3].x = 2;
			act[3].y = 2;
			break;
		case 6:
			act[0].x = 1;
			act[0].y = 0;
			act[1].x = 2;
			act[1].y = 0;
			act[2].x = 1;
			act[2].y = 1;
			act[3].x = 1;
			act[3].y = 2;
			break;

		}
		// �����жϣ���������߽粻�����·���
		for (int i = 0; i < 4; i++) {
			if (maxYes(act[i].x, act[i].y)) {
				return false;
			}
		}
		return true;
	}

	// ��һ��
		public void growth() {
			
			if (shu > 0) {
				shu = 0;
				for (int i = 0; i < 17; i++) {
					for (int j = 0; j < 10; j++) {
						map[j][i] = map[j][i + 1];
					}
				}
				if (!shuGrowth) {
					for (int i = 0; i < 10; i = i + 2) {
						map[i][17] = 8;
						map[i + 1][17] = 7;
					}
				} else {
					for (int i = 0; i < 10; i = i + 2) {
						map[i][17] = 7;
						map[i + 1][17] = 8;
					}
				}
				shuGrowth = !shuGrowth;
			}
			
		}
		
	// ����
	// ������������
	public int delete() {
		int line = 0;
		// ��18�н��б���
		for (int i = 0; i < 18; i++) {
			// ��10�б�����7����û��
			int j;
			for (j = 0; j < 10; j++) {
				if (map[j][i] == 7) {
					break;
				}
			}
			// ���10�о��м�����
			if (j == 10) {
				line += 1;// ������+1
				if (i != 0) {
					// �����˲�����һ��
					for (int j2 = i - 1; j2 > 0; j2--) {
						for (int k = 0; k < 10; k++) {
							map[k][j2 + 1] = map[k][j2];
						}
					}
					// ������һ�н��пհ��ػ�
					for (int j2 = 0; j2 < 10; j2++) {
						map[0][j2] = 7;
					}
				}
			}
		}
		repaint();
		return line;

	}

	private void down() {
		if (b_Song) {
			new DownSong().start();
		}
		if (minYes(0, 1)) {
			// �ھ����������ƶ�һ��
			for (int i = 0; i < 4; i++) {
				act[i].y += 1;
			}
			repaint();
		} else {
			t.stop();// ����ʱ ��ʱ�������ֹͣ
			// ��������ʱ���̶�����
			for (int i = 0; i < 4; i++) {
				map[act[i].x][act[i].y] = last_temp;
				sendMap[act[i].x][act[i].y] = last_temp;
			}
			// �����ж�����
			int line = delete();
			if (line != 0) {
				fenShu = fenShu + (10 * line * line);// �µ÷���Ϊ����ƽ������10
				// �ٶ���ʱ����
				if (fenShu - tFenShu >= 300) {
					tFenShu = fenShu;
					if (speed <= 9) {
						speed++;
						LeverUp=true;
					}
				}
				if (b_Song) {
					FileInputStream fis;
					try {
						fis = new FileInputStream("src/songs/delete.wav");
						AudioStream as = new AudioStream(fis);// ����
						AudioPlayer.player.start(as);
						AudioPlayer.player.stop();
					} catch (Exception e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
				}
			}
			// ������Բ�������
			if (!newAct()) {
				last_temp = temp;
				temp = (byte) r.nextInt(7);// 0-6����7�ֲ�ͬ����
				nextAct();
				t.start();// ʱ���������ʼ
			}
			// �������������
			else {
				// ����
				t.stop();// ʱ�������ֹͣ
				end_Game = true;
				start = false;
				if (b_Song) {
					LostSong();
				}
				ScoreDialog.add(fenShu);//������а�
			}
			repaint();
		}

	}

	public void up() {
		// ��ʱ�������淽��
		if (b_Song) {
			new UpSong().start();
		}
		repaint();
		gameAct[] tt = new gameAct[4];
		for (int i = 0; i < 4; i++) {
			tt[i] = new gameAct();
			tt[i].x = act[i].x;
			tt[i].y = act[i].y;
		}
		int cx = (tt[0].x + tt[1].x + tt[2].x + tt[3].x) / 4;
		int cy = (tt[0].y + tt[1].y + tt[2].y + tt[3].y) / 4;

		// �������0 2 3 4��ı�����״̬
		if (last_temp == 0 || last_temp == 2 || last_temp == 3
				|| last_temp == 4)
			gaibian[last_temp] = !gaibian[last_temp];

		// ������ת
		for (int i = 0; i < 4; i++) {
			tt[i].x = cx + cy - act[i].y + 1;
			tt[i].y = cy - cx + act[i].x;
			// ���ڶ�����ת0 2 3 4��x��һ
			if (last_temp == 0 || last_temp == 2 || last_temp == 3
					|| last_temp == 4)
				if (gaibian[last_temp])
					tt[i].x--;
		}

		// �߽��ж����Ƿ�����ת
		for (int i = 0; i < 4; i++) {
			if (!maxYes(tt[i].x, tt[i].y)) {
				return;
			}
		}

		// ��ת���ֵ��������
		for (int i = 0; i < 4; i++) {
			act[i].x = tt[i].x;
			act[i].y = tt[i].y;
		}
		repaint();
	}

	// �Է�������ƶ�
	public void Move(int x, int y) {
		// �ڱ߽��ڽ����ƶ�
		if (minYes(x, y)) {
			for (int i = 0; i < 4; i++) {
				act[i].x += x;
				act[i].y += y;
			}
		}

		repaint();
	}

	// �߽��ж� �ж�move�����Ƿ����״̬
	public boolean minYes(int x, int y) {
		for (int i = 0; i < 4; i++) {
			if (!maxYes(act[i].x + x, act[i].y + y)) {
				return false;
			}
		}
		return true;
	}

	// �߽��ж� ȷ��x��y��Χ
	public boolean maxYes(int x, int y) {
		if (x < 0 || x >= 10 || y < 0 || y >= 18) {
			return false;
		}
		// ����ķ����±߽��ж�
		if (map[x][y] >= 0 && map[x][y] <= 6 || map[x][y] == 8) {
			return false;
		}
		return true;
	}

	// ���̼���
	public void keyPressed(KeyEvent e) {
		if (!keyLock) {
			if (start) {
				switch (e.getKeyCode()) {
				case KeyEvent.VK_DOWN:
					down();
					break;
				case KeyEvent.VK_UP:
					up();
					break;
				case KeyEvent.VK_LEFT:
					Move(-1, 0);
					break;
				case KeyEvent.VK_RIGHT:
					Move(1, 0);
					break;
				case KeyEvent.VK_SPACE:
					for (int i = 0; i < 16; i++) {
						if(Fast() == 0) break;
					}
					down();
					break;
				default:
					break;
				}
			}
		}
	}
	public int Fast(){
			
		if (minYes(0, 1)) {
			// �ھ����������ƶ�һ��
			for (int i = 0; i < 4; i++) {
				act[i].y += 1;
			}
			return 1;
//			repaint();
		}
		else return 0;
		
	}


	public void nextAct() {
		switch (temp) {
		case 0:
			act2[0].x = 1;
			act2[0].y = 0;
			act2[1].x = 2;
			act2[1].y = 0;
			act2[2].x = 3;
			act2[2].y = 0;
			act2[3].x = 4;
			act2[3].y = 0;
			break;
		case 1:
			act2[0].x = 1;
			act2[0].y = 0;
			act2[1].x = 2;
			act2[1].y = 0;
			act2[2].x = 1;
			act2[2].y = 1;
			act2[3].x = 2;
			act2[3].y = 1;
			break;
		case 2:
			act2[0].x = 1;
			act2[0].y = 0;
			act2[1].x = 2;
			act2[1].y = 0;
			act2[2].x = 2;
			act2[2].y = 1;
			act2[3].x = 3;
			act2[3].y = 1;
			break;
		case 3:
			act2[0].x = 1;
			act2[0].y = 1;
			act2[1].x = 2;
			act2[1].y = 0;
			act2[2].x = 2;
			act2[2].y = 1;
			act2[3].x = 3;
			act2[3].y = 0;
			break;
		case 4:
			act2[0].x = 1;
			act2[0].y = 0;
			act2[1].x = 2;
			act2[1].y = 0;
			act2[2].x = 3;
			act2[2].y = 0;
			act2[3].x = 2;
			act2[3].y = 1;
			break;
		case 5:
			act2[0].x = 1;
			act2[0].y = 0;
			act2[1].x = 2;
			act2[1].y = 0;
			act2[2].x = 2;
			act2[2].y = 1;
			act2[3].x = 2;
			act2[3].y = 2;
			break;
		case 6:
			act2[0].x = 1;
			act2[0].y = 0;
			act2[1].x = 2;
			act2[1].y = 0;
			act2[2].x = 1;
			act2[2].y = 1;
			act2[3].x = 1;
			act2[3].y = 2;
			break;
		}

	}

	// ��Ϊ����
	public void actionPerformed(ActionEvent e) {
		// ��ð�ť��Ϊ
		if (e.getSource() == newGame) {
			// ��ť���ַ����ȶ�
			// �����ʼ��ť
			if (e.getActionCommand().equals("��ʼ")) {

				start_button();
				requestFocus(true);
			}// ������ð�ť
			else {
				if (gameSocket.con > 0) // ��սģʽ����Ч
					if (start == false)// �Լ�����
						gameSocket.con = 0;// �������� ���¿�ʼ
					else
					{
						requestFocus(true);
						return;
					}
				vs_fenshu = 0;
				end_Game = false;
				kaiShi = true;
				start = false;
				keyLock = false;
				newGame.setText("��ʼ");
				pauseGame.setText("��ͣ");
				newGame.setIcon(new ImageIcon("src/img/b1.png"));
				pauseGame.setIcon(new ImageIcon("src/img/b3.png"));
				fenShu = 0;
				tFenShu = 0;
				speed = 0;
				// �������
				for (int i = 0; i < 10; i++) {
					for (int j = 0; j < 18; j++) {
						map[i][j] = 7;
					}
				}
				newGame.setText("��ʼ");
				newGame.setIcon(new ImageIcon("src/img/b1.png"));
				t.stop();
				repaint();
				requestFocus(true);
			}
		}
		// �����ͣ��ť
		if (e.getSource() == pauseGame) {
			if (kaiShi || end_Game || gameSocket.con > 0) {
				// ��Ч
				requestFocus(true);// �ͷŽ���
				return;
			} else {
				if (e.getActionCommand().equals("��ͣ")) {
					if (start) {
						t.stop();
						keyLock = true;
						pauseGame.setText("����");
						pauseGame.setIcon(new ImageIcon("src/img/b1.png"));
						repaint();
						requestFocus(true);// �ͷŽ���
					} else {
						requestFocus(true);// �ͷŽ���
						return;
					}
				} else {
					pauseGame.setText("��ͣ");
					pauseGame.setIcon(new ImageIcon("src/img/b3.png"));
					t.start();
					keyLock = false;
					repaint();
					requestFocus(true);// �ͷŽ���
				}
			}

		}
		if (e.getSource() == closeSong) {// �ر�������ť
			if (b_Song) {
				b_Song = false;
				closeSong.setIcon(new ImageIcon("src/img/nomusic.png"));
				requestFocus(true);
			} else {
				b_Song = true;
				closeSong.setIcon(new ImageIcon("src/img/music.png"));
				requestFocus(true);
			}
		}
		if (e.getSource() == closeBackgroundSong) {// �رձ������ְ�ť
			if (b_BackSong) {
				b_BackSong = false;
				closeBackgroundSong.setIcon(new ImageIcon("src/img/nobgmusic.png"));
				AudioPlayer.player.stop(b_as); 
    			bgs.interrupt();
				requestFocus(true);
			} else {
				b_BackSong = true;
				closeBackgroundSong.setIcon(new ImageIcon("src/img/bgmusic.png"));
				bgs = new bgSong();
				bgs.start();
				requestFocus(true);	
			}
		}
		
		if (e.getSource() == JB_list) {// ���а�ť
			ScoreDialog.updata();
			ScoreDialog.setVisible(true);
			requestFocus(true);
		}
		
		if (e.getSource() == vs) {//�����ս
			if (!start) {
				gameDialogVS.tipStr = "�ȴ��ͻ���������";
				if (gameSocket.con == -1)
					gameSocket.con = 0;// ״̬����
				// ���������
				SS = new Sever_Socket();
				t_sever = new Thread(SS);
				t_sever.start();

				Thread tStart = new Thread() {
					public void run() {
						while (gameSocket.con == 0) {
							try {
								Thread.sleep(100);
							} catch (InterruptedException e1) {
								// TODO Auto-generated catch block
								e1.printStackTrace();
							}
						}
						if (gameSocket.con == 2)
							start_button();// ��ʼ��Ϸ

					}
				};
				tStart.start();
				vsDialog.setVisible(true);// ��ʾ�Ի���

				requestFocus(true);// �ͷŽ���
			} else {
				requestFocus(true);// �ͷŽ���
				return;
			}
		}
		if(e.getSource()==help){//�������
			c_help=!c_help;
		    repaint();
		    requestFocus(true);
		  
		}
		if(e.getSource()==help_close){//��������Ĺر�
			c_help=false;
		    repaint();
		    requestFocus(true);
		  
		}
		
		// ȷ�϶�ս
		if (e.getSource() == vsButton) {
			// ���Ӷ��ִ���
			if (!gameSocket.con_state && gameSocket.con == 0)// ����������һ��������ҵ�һ������ʱ���ر�
				SS.stopThread();// �رշ����
			else {
				// ��ʾ��ǰΪ��������״̬��ֻ����Ϊ�ͻ���ʹ��

			}
			gameSocket.con = 0;//��ʼ��״̬
			if (gameSocket.con == -1)
				gameSocket.con = 0;// ״̬����
			// �����ͻ���
			gameSocket.ipstr = vsDialog.ip.getText();//��ȡIP
			Client_Socket CS = new Client_Socket();
			t_client = new Thread(CS);
			t_client.start();

			while (gameSocket.con == 0) {
				try {
					Thread.sleep(100);
				} catch (InterruptedException e1) {
					e1.printStackTrace();
				}
			}
			if (gameSocket.con == 1) {
				vsDialog.setVisible(false);// �رմ���
				// �Զ���ʼ��Ϸ
				start_button();
			}
			// else if(gameSocket.con == -1)
			// {
			// //����
			// // vsDialog.setVisible(false);// �رմ���
			// }
			requestFocus(true);// �ͷŽ���
		}

		// ���ȡ����ս��ť
		if (e.getSource() == qxButton) {
			if (gameSocket.con == 0)
				SS.stopThread();// �رշ������˼���
			gameSocket.con = 0;// ��ʼ������״̬
			vsDialog.setVisible(false);
			requestFocus(true);// �ͷŽ���
		}

		if (e.getSource() == endGame) {
			System.exit(0);
		}
	}

	// ʱ�����
	public class myTimer implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			if (start) {
				growth();
				down();

			}
		}
	}

	public void keyTyped(KeyEvent e) {
		// TODO Auto-generated method stub

	}

	public void keyReleased(KeyEvent e) {
		// TODO Auto-generated method stub

	}

	/**
	 * ���ð�ť����ʽ
	 * 
	 * @param button
	 *            �����õİ�ť
	 * @param x
	 *            x����
	 * @param y
	 *            y����
	 * @param xx
	 *            ���
	 * @param yy
	 *            �߶�
	 */
	public void set_button_style(final JButton button, int x, int y, int xx, int yy) {
		button.setBounds(x, y, xx, yy);
		// ��dialog���������壬1������ʽ(1�Ǵ��壬0��ƽ���ģ�15���ֺ�
		button.setFont(new java.awt.Font("Microsoft YaHei", 0, 25));
		button.setForeground(Color.black);
		button.addMouseListener(new MouseListener() {
			
			@Override
			public void mouseReleased(MouseEvent arg0) {
				
			}
			
			@Override
			public void mousePressed(MouseEvent arg0) {
				
			}
			
			@Override
			public void mouseExited(MouseEvent arg0) {
				button.setForeground(Color.black);
			}
			
			@Override
			public void mouseEntered(MouseEvent arg0) {
				button.setForeground(Color.gray);
			}
			
			@Override
			public void mouseClicked(MouseEvent arg0) {
				button.setForeground(Color.black);
				
			}
		});
	}

	/**
	 * �½�һ����ͼƬ���ֵİ�ť
	 * 
	 * @param name
	 *            ��ť�ϵ�����
	 * @param img
	 *            ��ť�ϵ�ͼƬ
	 * @return ��ť
	 */
	public JButton set_button(String name, ImageIcon img) {
		JButton temp_button = new JButton(name, img);
		temp_button.setOpaque(false);
		temp_button.setContentAreaFilled(false);
		temp_button.setMargin(new Insets(0, 0, 0, 0));
		temp_button.setFocusPainted(false);
		temp_button.setBorderPainted(false);
		temp_button.setBorder(null);
		return temp_button;
	}

	public void start_button() {
		if (b_Song) {
			FileInputStream fis;
			try {
				fis = new FileInputStream("src/songs/readygo.wav");
				AudioStream as = new AudioStream(fis);// ����
				AudioPlayer.player.start(as);
				AudioPlayer.player.stop();
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		shu = 0;
		vsShu = 0;
		end_Game = false;
		kaiShi = false;
		keyLock = false;
		newGame.setText("����");
		pauseGame.setText("��ͣ");
		newGame.setIcon(new ImageIcon("src/img/b2.png"));
		pauseGame.setIcon(new ImageIcon("src/img/b3.png"));
		temp = (byte) r.nextInt(7);// 0-6����7�ֲ�ͬ����
		last_temp = temp;
		requestFocus(true);// �ͷŽ���
		start = true;// ��Ϸ״̬ ��ʼ
		vsStart = true;// ����״̬��ʼ
		for (int i = 0; i < 10; i++) {
			for (int j = 0; j < 18; j++) {
				map[i][j] = 7;
				vsMap[i][j] = 7;
				sendMap[i][j] = 7;
				sendMap2[i][j] = 7;
			}
		}
		if (!newAct()) {
			// �ɲ�������
			t = new Timer(1000 - (100 * speed), new myTimer());// ʱ�������
			t.start();
			last_temp = temp;
			temp = (byte) r.nextInt(7);// 0-6����7�ֲ�ͬ����
			gaibian[last_temp] = false;// ��ʼ���ı�״̬Ϊfalse
			nextAct();// ������һ����ʾ
			repaint();// �ػ�
		} else {
			// ������ʧ�ܵ������
			return;
		}
	}

	public void LostSong() {
		FileInputStream fis;
		try {
			fis = new FileInputStream("src/songs/lost.wav");
			AudioStream as = new AudioStream(fis);// ����
			AudioPlayer.player.start(as);
			AudioPlayer.player.stop();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	public class UpSong extends Thread {
		public void run() {
			FileInputStream fis;
			try {
				fis = new FileInputStream("src/songs/turnover.wav");
				AudioStream as = new AudioStream(fis);// ����
				AudioPlayer.player.start(as);
				AudioPlayer.player.stop();
				sleep(1000);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	class bgSong extends Thread{	
		public void run(){
			while(b_BackSong){
				try {
					FileInputStream b_fis = new FileInputStream("src/songs/gameBg.wav");
					b_as=new AudioStream(b_fis);//���� 
					AudioPlayer.player.start(b_as);
					Thread.sleep(60000);
			    } catch (Exception e) {
				}	
			}
			
		}	
	}
	public class DownSong extends Thread {
		public void run() {
			FileInputStream fis;
			try {
				fis = new FileInputStream("src/songs/down.wav");
				AudioStream as = new AudioStream(fis);// ����
				AudioPlayer.player.start(as);
				AudioPlayer.player.stop();
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			try {
				sleep(1000 - (100 * speed));
				// System.out.println("xiumian"+(1000 - (100 * speed)));
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

	}
}
