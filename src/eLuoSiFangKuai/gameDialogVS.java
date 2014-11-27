package eLuoSiFangKuai;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Insets;
import java.awt.Point;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JEditorPane;
import javax.swing.JLabel;
import javax.swing.JPanel;
/**
 * 画笔类，用于画面板内容，背景
 * @author 闫庚哲
 *
 */
class gameDialogVSJP extends JPanel{

	public void paintComponent(Graphics g) {
		super.paintComponent(g);// 调用父类
		g.drawImage(new ImageIcon("src/img/tip.png").getImage(), 0, 0, null);//设置背景
	}
}
/**
 * 对话框类，用于设计对战模式的界面
 * @author 闫庚哲
 *
 */
public class gameDialogVS extends JDialog{
	
	public static String tipStr = "等待客户端连接中";
	String[] gifStr = {"",".","..","...","....",".....","......"};
	JLabel vsLabel = new JLabel();
	JLabel ipText = new JLabel("服务器IP：");
	JButton vsButton = null;// 确定按钮
	JButton qxButton = null;// 取消按钮
	JEditorPane ip = new JEditorPane();
	static Point origin = new Point();//窗口坐标
	
	public gameDialogVS() {
		super();
		setUndecorated(true);//取消标题栏
		setSize(320, 168);//大小
		setLocation(350, 300);//位置
		setModal(true); //设置模式
		setContentPane(new gameDialogVSJP());//设置JPanel
		setBackground(new Color(0,0,0,0f));//背景透明
		setLayout(null);//设置布局
		
		//设置格式 可拖动
        addMouseListener(new MouseListener() {
			@Override
			public void mousePressed(MouseEvent arg0) {
				origin.x = arg0.getX();
				origin.y = arg0.getY();
			}

			@Override
			public void mouseClicked(MouseEvent e) {
			}

			@Override
			public void mouseEntered(MouseEvent e) {
			}

			@Override
			public void mouseExited(MouseEvent e) {
			}

			@Override
			public void mouseReleased(MouseEvent e) {
				
			}
			
		});
        
        addMouseMotionListener(new MouseMotionListener() {
			
			@Override
			public void mouseMoved(MouseEvent arg0) {
			}
			@Override
			public void mouseDragged(MouseEvent arg0) {
				Point p = getLocation();
				setLocation(p.x + arg0.getX() - origin.x, p.y + arg0.getY()- origin.y);
			}
		});
		
		
		
		//添加，设置IPTEXT格式
		add(ipText);
		ipText.setFont(new   java.awt.Font("Microsoft YaHei",   1,   14));
		ipText.setBounds(45,60,80,25);
		//添加，设置输入框格式
		add(ip);
		ip.setText("随机对战");
		ip.setFont(new   java.awt.Font("Microsoft YaHei",   1,   14));
		ip.setBounds(120,60,150,25);
		//添加，设置标签格式
		add(vsLabel);
		vsLabel.setFont(new   java.awt.Font("Microsoft YaHei",   1,   18));// “dialog”代表字体，1代表样式(1是粗体，0是平常的）15是字号
		vsLabel.setForeground(Color.RED);
		vsLabel.setBounds(80,20,250,30);
		//启动线程监听提示文字的变化
		Thread tStart = new Thread() {
			public void run() {
				int i = 0;
				while (true) {//循环监听修改文字
					if(tipStr.equals("等待客户端连接中"))
					{
						vsLabel.setText(tipStr+gifStr[i]);
						i = ++i%7;
					}
					else vsLabel.setText(tipStr);
					repaint();
					try {
						Thread.sleep(1000);
					} catch (InterruptedException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
				}
			}
		};
		tStart.start();
		
	}
	/**
	 * 设置按钮等响应时间
	 * @param b1 提交按钮
	 * @param b2 取消按钮
	 * @param jp JP
	 */
	public void set(JButton b1,JButton b2,ActionListener jp)
	{
		vsButton = b1;
		qxButton = b2;
		//添加按钮并设置透明格式
		add(vsButton);
		vsButton.setBounds(50,120,100,30);
		vsButton.setOpaque(false);  
		vsButton.setContentAreaFilled(false);  
		vsButton.setMargin(new Insets(0, 0, 0, 0));  
		vsButton.setFocusPainted(false);  
		vsButton.setBorderPainted(false);  
		vsButton.setBorder(null); 
		add(qxButton);
		qxButton.setBounds(170,120,100,30);
		qxButton.setOpaque(false);  
		qxButton.setContentAreaFilled(false);  
		qxButton.setMargin(new Insets(0, 0, 0, 0));  
		qxButton.setFocusPainted(false);  
		qxButton.setBorderPainted(false);  
		qxButton.setBorder(null); 
		//添加响应时间
		qxButton.addActionListener(jp);
		vsButton.addActionListener(jp);
	}


}
