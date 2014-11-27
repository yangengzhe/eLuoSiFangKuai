package eLuoSiFangKuai;

import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class guanyu extends JFrame implements ActionListener{
	
	JPanel panel1 = new JPanel();
	JLabel back = new JLabel();
	public guanyu(){
		setTitle("---关于俄罗斯方块---");//设置标题
		add(panel1);
		panel1.setLayout(null);//置空布局
		setSize(550,550);
		setLocation(300, 100);
		setDefaultCloseOperation(3);
		panel1.add(back);
		back.setBounds(400,400, 200, 200);
		back.addMouseListener(new MouseListener() {
			
			@Override
			public void mouseReleased(MouseEvent e) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void mousePressed(MouseEvent e) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void mouseExited(MouseEvent e) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void mouseEntered(MouseEvent e) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void mouseClicked(MouseEvent e) {
				setVisible(false);
			    new Welcome().setVisible(true);
				
			}
		});
		
	}
	

	public void paint(Graphics g){
		/*画背景*/
		Image background1=new ImageIcon("src/img/about.png").getImage();		
		g.drawImage(background1, 0,0,null);
		
		
	   
	 
	}


	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		
	}

}
