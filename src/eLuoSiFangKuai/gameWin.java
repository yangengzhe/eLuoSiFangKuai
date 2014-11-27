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
 * 自定义窗口组件类   继承于JPanel  并设置行为监听  键盘监听
 * @author ALL
 * 
 */
public class gameWin extends JPanel implements ActionListener, KeyListener {
	JButton newGame = set_button("开始", new ImageIcon("src/img/b1.png"));// 创建开始按钮
	JButton pauseGame = set_button("暂停", new ImageIcon("src/img/b3.png"));// 创建暂停按钮
	JButton endGame = set_button("退出", new ImageIcon("src/img/b5.png"));// 创建退出按钮
	JButton vs = set_button("对战", new ImageIcon("src/img/b4.png"));// 创建对战按钮
	JButton closeSong = set_button("", new ImageIcon("src/img/music.png"));// 创建关闭音效按钮
	JButton closeBackgroundSong = set_button("", new ImageIcon("src/img/bgmusic.png"));// 创建关闭音效按钮
	JButton help=set_button("",new ImageIcon("src/img/bhelp.png"));//帮助按钮
	JButton help_close=set_button("",null);//帮助的关闭按钮
	int speed = 0, tFenShu = 0;// 声明 分数 与 速度变量
	public static int fenShu = 0;
	Random r = new Random();// 设置随机数，用于随机生成方块生成 代号
	// gameAct为自定义方块坐标类 存储x，y坐标
	gameAct[] act = new gameAct[4];// 每个方块占4块
	gameAct[] act2 = new gameAct[4];// 每个方块占4块
	byte[][] map = new byte[10][18];// 定义矩形坐标，画出下落物体最后位置

	public static int shu;// 自己产生方块数
	public static int vsShu;// 对手方块数
	boolean shuGrowth = false;// 增长的位置

	// ///////////////////////////////////////
	public static Thread t_sever;// 服务器线程
	public static Thread t_client;// 客户端线程
	public static Sever_Socket SS;// 服务器Socket
	public static int vs_fenshu = 0;// 对手分数
	public static byte[][] vsMap = new byte[10][18];// 接收对手的坐标
	public static byte[][] sendMap = new byte[10][18];// 合成制作自己的坐标
	public static byte[][] sendMap2 = new byte[10][18];// 真正发送的坐标
	// //////////////////////////////////////////////
	public static boolean start = false;// 本机游戏状态标识
	boolean end_Game = false;// 游戏结束标志
	public static boolean vsStart = true;// 对手游戏状态标识
	Timer t;// 时间监听器
	byte temp;// 下一块产生的方块种类
	byte last_temp;// 当前产生的方块种类
	/** 对战Dialog **/
	public static gameDialogVS vsDialog = new gameDialogVS();// 对战对话框
	JButton vsButton = new JButton("");// 确认按钮
	JButton qxButton = new JButton("");// 取消按钮
	Image fangKuai;
	Image leverup=new ImageIcon("src/img/leverup.png").getImage();
	Image helping=new ImageIcon("src/img/helping.png").getImage();
	boolean keyLock = false;// 键盘锁 用于暂停
	boolean kaiShi = false;// 用于开始键 时暂停键不改变
	boolean gaibian[] = { false, false, false, false, false, false, false };
	AudioStream b_as;
	bgSong bgs = new bgSong();//背景音乐的线程
	boolean b_Song = true;// 用于控制音效
	boolean b_BackSong = true;// 用于控制背景音乐
	boolean c_help=false;//显示帮助
	boolean LeverUp=false;//用于控制显示升级
	//排行榜
	JButton JB_list = set_button("排行榜",null);// 创建开始按钮
	public static gameDialogScore ScoreDialog = new gameDialogScore();// 排行榜对话框
	
	/**
	 * gameWin构造函数
	 */
	public gameWin() {
		// 对方快初始化
		for (int i = 0; i < 4; i++) {
			act[i] = new gameAct();
			act2[i] = new gameAct();
		}
		// 矩形坐标初始化
		for (int i = 0; i < 10; i++) {
			for (int j = 0; j < 18; j++) {
				map[i][j] = 7;
				vsMap[i][j] = 7;
				sendMap[i][j] = 7;
				sendMap2[i][j] = 7;
			}
		}
		bgs.start();//启动背景音乐
		setLayout(null);// 设置布局
		add(newGame);// 添加按钮到窗口
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
		help.addMouseListener(new MouseListener() {//帮助按钮的监听
			
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
		newGame.addActionListener(this);// 按钮添加监听
		endGame.addActionListener(this);
		pauseGame.addActionListener(this);
		help.addActionListener(this);
		help_close.addActionListener(this);
		vs.addActionListener(this);
		addKeyListener(this);// 键盘添加监听
		// 设置对战窗口参数
		vsDialog.set(vsButton, qxButton, this);
		vsDialog.setVisible(false);
		// 设置排行榜
		add(JB_list);// 添加按钮到窗口
		set_button_style(JB_list, 370, 10, 90, 40);
		JB_list.addActionListener(this);
	}

	// 画笔
	public void paintComponent(Graphics g) {
		super.paintComponent(g);// 调用父类

		g.drawImage(new ImageIcon("src/img/main.jpg").getImage(), 0, 0, null);// 背景图片
		g.setFont(new java.awt.Font("Algerian", 0, 30));// “dialog”代表字体，1代表样式(1是粗体，0是平常的）15是字号
		g.setColor(Color.white);// 字体颜色
		g.drawString(fenShu + "", 390, 345);// 分数
		g.drawString(vs_fenshu + "", 700, 630);// 对手分数
		g.drawString(speed + "", 405, 470);// 速度
		// ///////////////////////////////////////

		// 如果游戏状态标识为开始
		if (start == true || end_Game) {// 游戏开始或者游戏死掉后停留在最终画面
			// 画出4块
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
					gameWin.sendMap2[i][j] = gameWin.sendMap[i][j];// 复制传送的数组

			// 画对手方块
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
			// 画出方块下落最后位置
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
			// 画出下一块
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
		// 结束游戏的分数提示
		if (end_Game) {
			g.drawImage(new ImageIcon("src/img/score.png").getImage(), 38, 100,
					276, 300, null);
			g.setColor(Color.white);
			g.drawString(fenShu + "", 150, 275);// 分数
		}
		// 暂停界面提示
		else if (keyLock) {
			g.drawImage(new ImageIcon("src/img/stop.png").getImage(), 38, 100,
					276, 300, null);
		}
		// 对手未连接界面
		if (gameSocket.con <= 0) {
			g.drawImage(new ImageIcon("src/img/nocon.png").getImage(), 504, 15,
					300, 540, null);
		}
		// 对手结束游戏
		else if (gameWin.vsStart == false) {
			g.drawImage(new ImageIcon("src/img/score.png").getImage(), 518,
					100, 276, 300, null);
			g.setColor(Color.white);
			g.drawString(vs_fenshu + "", 630, 275);// 分数
		}
		if(LeverUp){
			g.drawImage(leverup, 70, 70, 200,150, null);
            LeverUp=false;
		}
		if(c_help){
			g.drawImage(helping,515,55,281,450,null);
			
		}	
	}

	// 新产生方块
	private boolean newAct() {
		vsShu++;
		if (b_Song) {//增加新方块的音乐
			FileInputStream fis;
			try {
				fis = new FileInputStream("src/songs/add.wav");
				AudioStream as = new AudioStream(fis);// 播放
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
		// 结束判断，如果超出边界不产生新方块
		for (int i = 0; i < 4; i++) {
			if (maxYes(act[i].x, act[i].y)) {
				return false;
			}
		}
		return true;
	}

	// 涨一层
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
		
	// 消行
	// 返回消除行数
	public int delete() {
		int line = 0;
		// 对18行进行遍历
		for (int i = 0; i < 18; i++) {
			// 对10列遍历，7代表没有
			int j;
			for (j = 0; j < 10; j++) {
				if (map[j][i] == 7) {
					break;
				}
			}
			// 如果10列均有即消除
			if (j == 10) {
				line += 1;// 消除行+1
				if (i != 0) {
					// 消除了并下移一行
					for (int j2 = i - 1; j2 > 0; j2--) {
						for (int k = 0; k < 10; k++) {
							map[k][j2 + 1] = map[k][j2];
						}
					}
					// 最上面一行进行空白重画
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
			// 在矩形内向下移动一格
			for (int i = 0; i < 4; i++) {
				act[i].y += 1;
			}
			repaint();
		} else {
			t.stop();// 消行时 ，时间监听器停止
			// 不再下移时画固定方块
			for (int i = 0; i < 4; i++) {
				map[act[i].x][act[i].y] = last_temp;
				sendMap[act[i].x][act[i].y] = last_temp;
			}
			// 进行判定消行
			int line = delete();
			if (line != 0) {
				fenShu = fenShu + (10 * line * line);// 新得分数为行数平方乘以10
				// 速度随时间变快
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
						AudioStream as = new AudioStream(fis);// 播放
						AudioPlayer.player.start(as);
						AudioPlayer.player.stop();
					} catch (Exception e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
				}
			}
			// 如果可以产生方块
			if (!newAct()) {
				last_temp = temp;
				temp = (byte) r.nextInt(7);// 0-6代表7种不同方块
				nextAct();
				t.start();// 时间监听器开始
			}
			// 如果不产生方块
			else {
				// 死了
				t.stop();// 时间监听器停止
				end_Game = true;
				start = false;
				if (b_Song) {
					LostSong();
				}
				ScoreDialog.add(fenShu);//添加排行榜
			}
			repaint();
		}

	}

	public void up() {
		// 临时变量代替方块
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

		// 如果遇到0 2 3 4则改变数组状态
		if (last_temp == 0 || last_temp == 2 || last_temp == 3
				|| last_temp == 4)
			gaibian[last_temp] = !gaibian[last_temp];

		// 中心旋转
		for (int i = 0; i < 4; i++) {
			tt[i].x = cx + cy - act[i].y + 1;
			tt[i].y = cy - cx + act[i].x;
			// 若第二次旋转0 2 3 4则x减一
			if (last_temp == 0 || last_temp == 2 || last_temp == 3
					|| last_temp == 4)
				if (gaibian[last_temp])
					tt[i].x--;
		}

		// 边界判定，是否能旋转
		for (int i = 0; i < 4; i++) {
			if (!maxYes(tt[i].x, tt[i].y)) {
				return;
			}
		}

		// 旋转后的值付给方块
		for (int i = 0; i < 4; i++) {
			act[i].x = tt[i].x;
			act[i].y = tt[i].y;
		}
		repaint();
	}

	// 对方快进行移动
	public void Move(int x, int y) {
		// 在边界内进行移动
		if (minYes(x, y)) {
			for (int i = 0; i < 4; i++) {
				act[i].x += x;
				act[i].y += y;
			}
		}

		repaint();
	}

	// 边界判定 判断move（）是否调用状态
	public boolean minYes(int x, int y) {
		for (int i = 0; i < 4; i++) {
			if (!maxYes(act[i].x + x, act[i].y + y)) {
				return false;
			}
		}
		return true;
	}

	// 边界判定 确定x，y范围
	public boolean maxYes(int x, int y) {
		if (x < 0 || x >= 10 || y < 0 || y >= 18) {
			return false;
		}
		// 下落的方块新边界判定
		if (map[x][y] >= 0 && map[x][y] <= 6 || map[x][y] == 8) {
			return false;
		}
		return true;
	}

	// 键盘监听
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
			// 在矩形内向下移动一格
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

	// 行为监听
	public void actionPerformed(ActionEvent e) {
		// 获得按钮行为
		if (e.getSource() == newGame) {
			// 按钮与字符串比对
			// 点击开始按钮
			if (e.getActionCommand().equals("开始")) {

				start_button();
				requestFocus(true);
			}// 点击重置按钮
			else {
				if (gameSocket.con > 0) // 对战模式中无效
					if (start == false)// 自己输了
						gameSocket.con = 0;// 结束联机 重新开始
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
				newGame.setText("开始");
				pauseGame.setText("暂停");
				newGame.setIcon(new ImageIcon("src/img/b1.png"));
				pauseGame.setIcon(new ImageIcon("src/img/b3.png"));
				fenShu = 0;
				tFenShu = 0;
				speed = 0;
				// 方块清除
				for (int i = 0; i < 10; i++) {
					for (int j = 0; j < 18; j++) {
						map[i][j] = 7;
					}
				}
				newGame.setText("开始");
				newGame.setIcon(new ImageIcon("src/img/b1.png"));
				t.stop();
				repaint();
				requestFocus(true);
			}
		}
		// 点击暂停按钮
		if (e.getSource() == pauseGame) {
			if (kaiShi || end_Game || gameSocket.con > 0) {
				// 无效
				requestFocus(true);// 释放焦点
				return;
			} else {
				if (e.getActionCommand().equals("暂停")) {
					if (start) {
						t.stop();
						keyLock = true;
						pauseGame.setText("继续");
						pauseGame.setIcon(new ImageIcon("src/img/b1.png"));
						repaint();
						requestFocus(true);// 释放焦点
					} else {
						requestFocus(true);// 释放焦点
						return;
					}
				} else {
					pauseGame.setText("暂停");
					pauseGame.setIcon(new ImageIcon("src/img/b3.png"));
					t.start();
					keyLock = false;
					repaint();
					requestFocus(true);// 释放焦点
				}
			}

		}
		if (e.getSource() == closeSong) {// 关闭声音按钮
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
		if (e.getSource() == closeBackgroundSong) {// 关闭背景音乐按钮
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
		
		if (e.getSource() == JB_list) {// 排行榜按钮
			ScoreDialog.updata();
			ScoreDialog.setVisible(true);
			requestFocus(true);
		}
		
		if (e.getSource() == vs) {//点击对战
			if (!start) {
				gameDialogVS.tipStr = "等待客户端连接中";
				if (gameSocket.con == -1)
					gameSocket.con = 0;// 状态重置
				// 启动服务端
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
							start_button();// 开始游戏

					}
				};
				tStart.start();
				vsDialog.setVisible(true);// 显示对话框

				requestFocus(true);// 释放焦点
			} else {
				requestFocus(true);// 释放焦点
				return;
			}
		}
		if(e.getSource()==help){//点击帮助
			c_help=!c_help;
		    repaint();
		    requestFocus(true);
		  
		}
		if(e.getSource()==help_close){//点击帮助的关闭
			c_help=false;
		    repaint();
		    requestFocus(true);
		  
		}
		
		// 确认对战
		if (e.getSource() == vsButton) {
			// 连接对手代码
			if (!gameSocket.con_state && gameSocket.con == 0)// 当本机开了一个服务端且第一次连接时，关闭
				SS.stopThread();// 关闭服务端
			else {
				// 提示当前为本机测试状态，只能作为客户端使用

			}
			gameSocket.con = 0;//初始化状态
			if (gameSocket.con == -1)
				gameSocket.con = 0;// 状态重置
			// 开启客户端
			gameSocket.ipstr = vsDialog.ip.getText();//获取IP
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
				vsDialog.setVisible(false);// 关闭窗口
				// 自动开始游戏
				start_button();
			}
			// else if(gameSocket.con == -1)
			// {
			// //出错
			// // vsDialog.setVisible(false);// 关闭窗口
			// }
			requestFocus(true);// 释放焦点
		}

		// 点击取消对战按钮
		if (e.getSource() == qxButton) {
			if (gameSocket.con == 0)
				SS.stopThread();// 关闭服务器端监听
			gameSocket.con = 0;// 初始化连接状态
			vsDialog.setVisible(false);
			requestFocus(true);// 释放焦点
		}

		if (e.getSource() == endGame) {
			System.exit(0);
		}
	}

	// 时间监听
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
	 * 设置按钮的样式
	 * 
	 * @param button
	 *            带设置的按钮
	 * @param x
	 *            x坐标
	 * @param y
	 *            y坐标
	 * @param xx
	 *            宽度
	 * @param yy
	 *            高度
	 */
	public void set_button_style(final JButton button, int x, int y, int xx, int yy) {
		button.setBounds(x, y, xx, yy);
		// “dialog”代表字体，1代表样式(1是粗体，0是平常的）15是字号
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
	 * 新建一个带图片文字的按钮
	 * 
	 * @param name
	 *            按钮上的文字
	 * @param img
	 *            按钮上的图片
	 * @return 按钮
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
				AudioStream as = new AudioStream(fis);// 播放
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
		newGame.setText("重置");
		pauseGame.setText("暂停");
		newGame.setIcon(new ImageIcon("src/img/b2.png"));
		pauseGame.setIcon(new ImageIcon("src/img/b3.png"));
		temp = (byte) r.nextInt(7);// 0-6代表7种不同方块
		last_temp = temp;
		requestFocus(true);// 释放焦点
		start = true;// 游戏状态 开始
		vsStart = true;// 对手状态开始
		for (int i = 0; i < 10; i++) {
			for (int j = 0; j < 18; j++) {
				map[i][j] = 7;
				vsMap[i][j] = 7;
				sendMap[i][j] = 7;
				sendMap2[i][j] = 7;
			}
		}
		if (!newAct()) {
			// 可产生方块
			t = new Timer(1000 - (100 * speed), new myTimer());// 时间监听器
			t.start();
			last_temp = temp;
			temp = (byte) r.nextInt(7);// 0-6代表7种不同方块
			gaibian[last_temp] = false;// 初始化改变状态为false
			nextAct();// 产生下一块提示
			repaint();// 重画
		} else {
			// 这里是失败的情况；
			return;
		}
	}

	public void LostSong() {
		FileInputStream fis;
		try {
			fis = new FileInputStream("src/songs/lost.wav");
			AudioStream as = new AudioStream(fis);// 播放
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
				AudioStream as = new AudioStream(fis);// 播放
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
					b_as=new AudioStream(b_fis);//播放 
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
				AudioStream as = new AudioStream(fis);// 播放
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
