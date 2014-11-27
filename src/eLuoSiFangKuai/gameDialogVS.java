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
 * �����࣬���ڻ�������ݣ�����
 * @author �Ƹ���
 *
 */
class gameDialogVSJP extends JPanel{

	public void paintComponent(Graphics g) {
		super.paintComponent(g);// ���ø���
		g.drawImage(new ImageIcon("src/img/tip.png").getImage(), 0, 0, null);//���ñ���
	}
}
/**
 * �Ի����࣬������ƶ�սģʽ�Ľ���
 * @author �Ƹ���
 *
 */
public class gameDialogVS extends JDialog{
	
	public static String tipStr = "�ȴ��ͻ���������";
	String[] gifStr = {"",".","..","...","....",".....","......"};
	JLabel vsLabel = new JLabel();
	JLabel ipText = new JLabel("������IP��");
	JButton vsButton = null;// ȷ����ť
	JButton qxButton = null;// ȡ����ť
	JEditorPane ip = new JEditorPane();
	static Point origin = new Point();//��������
	
	public gameDialogVS() {
		super();
		setUndecorated(true);//ȡ��������
		setSize(320, 168);//��С
		setLocation(350, 300);//λ��
		setModal(true); //����ģʽ
		setContentPane(new gameDialogVSJP());//����JPanel
		setBackground(new Color(0,0,0,0f));//����͸��
		setLayout(null);//���ò���
		
		//���ø�ʽ ���϶�
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
		
		
		
		//��ӣ�����IPTEXT��ʽ
		add(ipText);
		ipText.setFont(new   java.awt.Font("Microsoft YaHei",   1,   14));
		ipText.setBounds(45,60,80,25);
		//��ӣ�����������ʽ
		add(ip);
		ip.setText("�����ս");
		ip.setFont(new   java.awt.Font("Microsoft YaHei",   1,   14));
		ip.setBounds(120,60,150,25);
		//��ӣ����ñ�ǩ��ʽ
		add(vsLabel);
		vsLabel.setFont(new   java.awt.Font("Microsoft YaHei",   1,   18));// ��dialog���������壬1������ʽ(1�Ǵ��壬0��ƽ���ģ�15���ֺ�
		vsLabel.setForeground(Color.RED);
		vsLabel.setBounds(80,20,250,30);
		//�����̼߳�����ʾ���ֵı仯
		Thread tStart = new Thread() {
			public void run() {
				int i = 0;
				while (true) {//ѭ�������޸�����
					if(tipStr.equals("�ȴ��ͻ���������"))
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
	 * ���ð�ť����Ӧʱ��
	 * @param b1 �ύ��ť
	 * @param b2 ȡ����ť
	 * @param jp JP
	 */
	public void set(JButton b1,JButton b2,ActionListener jp)
	{
		vsButton = b1;
		qxButton = b2;
		//��Ӱ�ť������͸����ʽ
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
		//�����Ӧʱ��
		qxButton.addActionListener(jp);
		vsButton.addActionListener(jp);
	}


}
