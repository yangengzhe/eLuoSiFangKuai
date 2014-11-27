package eLuoSiFangKuai;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.List;
import java.util.Map;

import javax.swing.ImageIcon;
import javax.swing.JDialog;
import javax.swing.JEditorPane;
import javax.swing.JLabel;
import javax.swing.JPanel;
/**
 * �����࣬���ڻ�������ݣ�����
 * @author �Ƹ���
 *
 */
class gameDialogScoreJP extends JPanel{

	public void paintComponent(Graphics g) {
		super.paintComponent(g);// ���ø���
		g.drawImage(new ImageIcon("src/img/list.png").getImage(), 0, 0, null);//���ñ���
	}
}

public class gameDialogScore extends JDialog {
	JLabel[] strText = new JLabel[10];
	ioTop io= new ioTop();//���а��ļ���д����
	List<Map.Entry<String, Integer>> mapIds;//���εĴ洢����
	static Point origin = new Point();//��������
	
	public gameDialogScore()
	{
		super();
		setResizable(false);
		setSize(310, 330);//��С
		setLocation(350, 300);//λ��
		setModal(true); //����ģʽ
		setContentPane(new gameDialogScoreJP());//����JPanel
		setLayout(null);//���ò���
		
		//���ø�ʽ ���϶�
        setLocationRelativeTo(null);
        setUndecorated(true);
        setBackground(new Color(0,0,0,0));
        addMouseListener(new MouseListener() {
			@Override
			public void mousePressed(MouseEvent arg0) {
				origin.x = arg0.getX();
				origin.y = arg0.getY();
			}

			@Override
			public void mouseClicked(MouseEvent e) {
				setVisible(false);
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
        
        
		io = new ioTop();
		for(int i = 0; i<10;i++)
		{
			strText[i] = new JLabel();
			add(strText[i]);
//			strText[i].setText(i+"");
			strText[i].setFont(new   java.awt.Font("Microsoft YaHei",   1,   22));
			strText[i].setForeground(Color.white);
			strText[i].setBounds(50+(i%2*130),75+(i/2)*40,120,25);
		}
		
	}
	void updata()
	{
		mapIds = io.get();
		for(int i = 0 ; i<mapIds.size() ; i++)
		{
			strText[i*2].setText(mapIds.get(i).getKey());
			strText[i*2+1].setText(mapIds.get(i).getValue().toString());
		}
	}
	
	void add(final int a)
	{
//		String str = "user"+ (int)(Math.random()*100);
		JLabel nameLabel = new JLabel("�������������");
		final JLabel okLabel = new JLabel("ȷ��");
		final JEditorPane strname = new JEditorPane();
		
		final JDialog getname = new JDialog();
		getname.setResizable(false);
		getname.setSize(200, 150);//��С
		getname.setLocation(350, 300);//λ��
		getname.setModal(true); //����ģʽ
		getname.setLayout(null);//���ò���
		
		getname.add(nameLabel);
		nameLabel.setBounds(10,10,120,25);
		getname.add(strname);
		strname.setBounds(20,40,150,25);
		strname.setText("user"+ (int)(Math.random()*100));
		getname.add(okLabel);
		okLabel.setBounds(80,80,30,25);
		okLabel.addMouseListener(new MouseListener() {
			
			@Override
			public void mouseReleased(MouseEvent arg0) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void mousePressed(MouseEvent arg0) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void mouseExited(MouseEvent arg0) {
				okLabel.setForeground(Color.black);
			}
			
			@Override
			public void mouseEntered(MouseEvent arg0) {
				okLabel.setForeground(new Color(0xbdbcbb));
			}
			
			@Override
			public void mouseClicked(MouseEvent arg0) {
				okLabel.setForeground(Color.black);
				io.add(strname.getText(), a);
				updata();
				getname.setVisible(false);
				gameWin.ScoreDialog.setVisible(true);
				requestFocus(true);
			}
		});
		
		getname.addWindowListener(new WindowAdapter(){
			 public void windowClosing(WindowEvent e) {//�����ر��¼�
				 io.add("user"+ (int)(Math.random()*100), a)                                                                                                                                                                                          ;
				 updata();
				 getname.setVisible(false);
				 gameWin.ScoreDialog.setVisible(true);
				 requestFocus(true);
			 }
				    
		});
		
		getname.setVisible(true);
	}
}
