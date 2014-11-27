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
 * ��ӭ����
 * @author ������
 *
 */
public class Welcome extends JFrame implements Runnable {
	    JPanel panel=new JPanel();
	    JLabel control=new JLabel();
	    JLabel about=new JLabel();
		/* �������ǵ�λ�� */
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
	        try {//����ͼ��
	           this.setIconImage(ImageIO.read(this.getClass().getResource("/img/dong1.png")));
	       } catch (IOException ex) {
	           Logger.getLogger(gameMain.class.getName()).log(Level.SEVERE, null, ex);
	       }
			setTitle("---��ӭ�������˹����---");//���ñ���
			/* ��ʼ������ */
			panel.setLayout(null);//�ÿղ���
			add(panel);
			setVisible(true);
			setSize(836,679);
			setLocation(100, 30);
			setDefaultCloseOperation(3);
			/*���ý���ͼ��*/
			control.setVisible(true);
			panel.add(control);
			
			control.setBounds(300,370,170,50);
			control.addMouseListener(new MouseListener(){//��Ӽ����¼�
				@Override
				public void mouseClicked(MouseEvent e) {//�������¼�	
					setVisible(false);//�رձ�����
					new gameMain().setVisible(true);//��������
					songStop=false;//ֹͣ����
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
			//���ù���ͼ��
			about.setVisible(true);
			panel.add(about);
			about.setBounds(300,460,170,50);
			about.addMouseListener(new MouseListener(){//��Ӽ����¼�
				@Override
				public void mouseClicked(MouseEvent e) {//�������¼�	
					setVisible(false);//�رձ�����
					new guanyu().setVisible(true);//��������
					songStop=false;//ֹͣ����
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
			/*������*/
			Image background=new ImageIcon("src/img/welcome.png").getImage();		
			g.drawImage(background, 0,0,null);
		    Image dong1=new ImageIcon("src/img/dong1.png").getImage();
		    /*������*/
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
		    //ѭ��������
            try {
				Thread.sleep(30);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} 
		  repaint(); 
		}
		/**
		 * ��������
		 */
		public void Song() {
			FileInputStream fis;
			try {
				fis = new FileInputStream("src/songs/win.wav");
				AudioStream as=new AudioStream(fis);//���� 
				AudioPlayer.player.start(as);
		    } catch (Exception e) {
			}			
		}
		@Override
		public void run() {
			while(songStop){//ѭ����������
				Song();
				try {
					Thread.sleep(3000);
				} catch (InterruptedException e) {
				}
				
			}
			
		}
}
