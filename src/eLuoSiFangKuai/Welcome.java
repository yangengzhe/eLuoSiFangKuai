package eLuoSiFangKuai;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

import sun.audio.AudioPlayer;
import sun.audio.AudioStream;
/**
 * 欢迎界面
 * @author 董欣欣
 *
 */
public class Welcome extends JFrame implements Runnable {
	    JPanel panel=new JPanel();
	    JLabel control=new JLabel();
	    JLabel about=new JLabel();
		/* 设置星星的位置 */
		int locationY1=50;
		int locationY2=100;
		int locationY3=80;
		int locationY4=150;
		int locationY5=200;
		int locationY6=300;
		int locationY7=90;
		int locationY8=100;
		int locationY9=0;
		int locationY10=200;
		int locationY11=503;
		boolean songStop=true;
		public Welcome(){			
	        try {//设置图标
	           this.setIconImage(ImageIO.read(this.getClass().getResource("/img/dong1.png")));
	       } catch (IOException ex) {
	           Logger.getLogger(gameMain.class.getName()).log(Level.SEVERE, null, ex);
	       }
			setTitle("---欢迎进入俄罗斯方块---");//设置标题
			/* 初始化界面 */
			panel.setLayout(null);//置空布局
			add(panel);
			setVisible(true);
			setSize(836,679);
			setLocation(100, 30);
			setDefaultCloseOperation(3);
			/*设置进入图标*/
			control.setVisible(true);
			panel.add(control);
			
			control.setBounds(300,370,170,50);
			control.addMouseListener(new MouseListener(){//添加监听事件
				@Override
				public void mouseClicked(MouseEvent e) {//点击鼠标事件	
					setVisible(false);//关闭本窗口
					new gameMain().setVisible(true);//打开主窗口
					songStop=false;//停止音乐
				}
				@Override
				public void mouseEntered(MouseEvent e) {
				}
				@Override
				public void mouseExited(MouseEvent e) {
				}
				@Override
				public void mousePressed(MouseEvent e) {
					// TODO Auto-generated method stub
					
				}
				@Override
				public void mouseReleased(MouseEvent e) {
					// TODO Auto-generated method stub
					
				}
			});
			//设置关于图标
			about.setVisible(true);
			panel.add(about);
			about.setBounds(300,460,170,50);
			about.addMouseListener(new MouseListener(){//添加监听事件
				@Override
				public void mouseClicked(MouseEvent e) {//点击鼠标事件	
					setVisible(false);//关闭本窗口
					new guanyu().setVisible(true);//打开主窗口
					songStop=false;//停止音乐
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
				public void mousePressed(MouseEvent e) {
					// TODO Auto-generated method stub
					
				}
				@Override
				public void mouseReleased(MouseEvent e) {
					// TODO Auto-generated method stub
					
				}
			});
			
			} 
		public void paint(Graphics g){
			/*画背景*/
			Image background=new ImageIcon("src/img/welcome.png").getImage();		
			g.drawImage(background, 0,0,null);
		    Image dong1=new ImageIcon("src/img/dong1.png").getImage();
		    /*画星星*/
		    g.drawImage(dong1, 20, locationY1++, null);
		    g.drawImage(dong1, 100, locationY2++, null);
	        g.drawImage(dong1, 150, locationY3++, null);
		    g.drawImage(dong1, 200, locationY4++, null);
		    g.drawImage(dong1, 300, locationY5++, null);
		    g.drawImage(dong1, 350, locationY6++, null);
		    g.drawImage(dong1, 500, locationY7++, null);
		    g.drawImage(dong1, 400, locationY8++, null);
		    g.drawImage(dong1, 600, locationY9++, null);
		    g.drawImage(dong1, 550, locationY10++,null);
		    g.drawImage(dong1, 450, locationY11++,null);
		    //循环画动画
            try {
				Thread.sleep(30);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} 
		  repaint(); 
		}
		/**
		 * 播放音乐
		 */
		public void Song() {
			FileInputStream fis;
			try {
				fis = new FileInputStream("src/songs/win.wav");
				AudioStream as=new AudioStream(fis);//播放 
				AudioPlayer.player.start(as);
		    } catch (Exception e) {
			}			
		}
		@Override
		public void run() {
			while(songStop){//循环播放音乐
				Song();
				try {
					Thread.sleep(3000);
				} catch (InterruptedException e) {
				}
				
			}
			
		}
}
