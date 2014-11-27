package eLuoSiFangKuai;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.imageio.ImageIO;
import javax.swing.JFrame;
/**
 * 程序主界面
 * @author 李鸿鹏
 *
 */
public class gameMain extends JFrame {
	public static gameWin gw = null;//主界面窗体
	static Point origin = new Point();//窗口坐标
	
	public gameMain() {
		super("---Blocks---");//窗口名
        try {//设置图标
           this.setIconImage(ImageIO.read(this.getClass().getResource("/img/image1.png")));
       } catch (IOException ex) {
           Logger.getLogger(gameMain.class.getName()).log(Level.SEVERE, null, ex);
       }  
        
        
        //设置格式 可拖动
        setLocationRelativeTo(null);
//        setUndecorated(true);
        addMouseListener(new MouseListener() {
			@Override
			public void mousePressed(MouseEvent arg0) {
				// TODO Auto-generated method stub
				origin.x = arg0.getX();
				origin.y = arg0.getY();
			}

			@Override
			public void mouseClicked(MouseEvent e) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void mouseEntered(MouseEvent e) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void mouseExited(MouseEvent e) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void mouseReleased(MouseEvent e) {
				// TODO Auto-generated method stub
				
			}
			
		});
        
        addMouseMotionListener(new MouseMotionListener() {
			
			@Override
			public void mouseMoved(MouseEvent arg0) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void mouseDragged(MouseEvent arg0) {
				// TODO Auto-generated method stub
				Point p = getLocation();
				setLocation(p.x + arg0.getX() - origin.x, p.y + arg0.getY()- origin.y);
			}
		});
        
        
		gw =new gameWin();//新建自定义类
		add(gw);//自定义类添加到主窗口上
		setSize(836, 679);//设置窗口大小
		setLocation(100, 30);//设置位置
		setResizable(false);//不可伸缩
		setDefaultCloseOperation(3);//系统退出	
	}
	@Override
	public void paint(Graphics g) {
		super.paint(g);
	}	
	public static void main(String[] args) {
		Thread bofang=new Thread(new Welcome());//设定开始界面线程
		bofang.start();	//启动开始界面
	}
}