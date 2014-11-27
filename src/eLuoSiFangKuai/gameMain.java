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
 * ����������
 * @author �����
 *
 */
public class gameMain extends JFrame {
	public static gameWin gw = null;//�����洰��
	static Point origin = new Point();//��������
	
	public gameMain() {
		super("---Blocks---");//������
        try {//����ͼ��
           this.setIconImage(ImageIO.read(this.getClass().getResource("/img/image1.png")));
       } catch (IOException ex) {
           Logger.getLogger(gameMain.class.getName()).log(Level.SEVERE, null, ex);
       }  
        
        
        //���ø�ʽ ���϶�
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
        
        
		gw =new gameWin();//�½��Զ�����
		add(gw);//�Զ�������ӵ���������
		setSize(836, 679);//���ô��ڴ�С
		setLocation(100, 30);//����λ��
		setResizable(false);//��������
		setDefaultCloseOperation(3);//ϵͳ�˳�	
	}
	@Override
	public void paint(Graphics g) {
		super.paint(g);
	}	
	public static void main(String[] args) {
		Thread bofang=new Thread(new Welcome());//�趨��ʼ�����߳�
		bofang.start();	//������ʼ����
	}
}