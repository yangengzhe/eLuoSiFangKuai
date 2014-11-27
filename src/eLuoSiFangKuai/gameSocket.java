package eLuoSiFangKuai;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Scanner;

/**
 * �����ࣨ����Ϸ�������ã�
 * @author �Ƹ���
 *
 */
public class gameSocket {
	public static int con = 0;// 0���ȴ� 1���ͻ��� 2������� -1������
	public static String ipstr = null;//���ӵ�IP��ַ
	public static boolean con_state = false;//�Ƿ�Ϊ�������Ӳ���״̬
//	public static byte a[][] = new byte[10][18];
//	public static byte b[][] = new byte[10][18];
//	public static byte a_state = 1;
//	public static byte b_state = 1;
//	public static Thread t_sever;
//	public static Thread t_client;
	

//	public static void test()
//	{
//		try {
//			InetAddress addr = InetAddress.getLocalHost();
//			String ip=addr.getHostAddress().toString();//��ñ���IP
//			System.out.println(ip);
//		} catch (UnknownHostException e1) {
//			// TODO Auto-generated catch block
//			e1.printStackTrace();
//		}
//		
////		for(int i = 0;i<10;i++)
////			for(int j=0;j<18;j++)
////				a[i][j] = (byte) (i+j);
//		
//		Sever_Socket SS = new Sever_Socket();
//		Client_Socket CS = new Client_Socket();
//		t_sever = new Thread(SS);
//		t_client = new Thread(CS);
//		
//		//con = 1;��������
//		
//		t_client.start();
//		t_sever.start();
//		
//		while(con==0 )
//		{
//			try {
//				Thread.sleep(200);//�ȴ�
//			} catch (InterruptedException e) {
//				e.printStackTrace();
//			}
//		}
//		if(con == 1) {
//			System.out.println("�ͻ��˿���");
//			SS.stopThread();
//			t_sever.interrupt();
//		}
//		else {
//			System.out.println("����������");
//			t_client.interrupt();
//		}
//	}
}
/**
 * 
 * Scoket�ͻ����߳�
 * @author �Ƹ���
 *
 */
class Client_Socket extends Thread{
	
	public void run() {
		if(gameSocket.con != 0) return;//���ǵȴ�״̬������
		
		try {
			if(gameSocket.ipstr.equals("�����ս"))
				gameSocket.ipstr ="182.254.137.53";
			Socket socket=new Socket(gameSocket.ipstr,4700);//��IP������4700�˿ڷ����ͻ�����
			System.out.println("�ͻ��˿���");
			gameSocket.con = 1;//���״̬
			gameWin.start = true;//�����Ϸ��ʼ
			
			DataOutputStream os=new DataOutputStream(socket.getOutputStream());
			//��Socket����õ��������������PrintWriter����
			DataInputStream is=new DataInputStream(socket.getInputStream());
			//��Socket����õ�����������������Ӧ��BufferedReader����
			
			while(true){
				os.writeBoolean(gameWin.start);	//�����Ϸ��ʼ״̬			
				os.writeInt(gameWin.fenShu);//�������
				if(gameWin.vsShu>0 && gameWin.vsShu%10 == 0)
				{
					gameWin.vsShu = 0;
					os.writeInt(1);//���������������
				}
				else os.writeInt(0);//���������������
				for(int i=0;i<10;i++)//�����ͼ����
					os.write(gameWin.sendMap2[i],0,gameWin.sendMap2[i].length);				
				os.flush();//ˢ���������ʹServer�����յ����ַ���

				gameWin.vsStart= is.readBoolean();//��ȡ������Ϸ��ʼ״̬
				gameWin.vs_fenshu = is.readInt();//��ȡ���ַ���
				gameWin.shu = is.readInt();//��ȡ������
				for(int i = 0;i < 10;i++)//��ȡ���ֵ�ͼ����
					for(int j = 0;j < 18;j++)
						gameWin.vsMap[i][j] = (byte)is.read();
				
				//������Ϸֹͣ����				
				if(gameSocket.con == 0 || gameWin.vsStart == false && gameWin.start == false)
					break;

				try {
					sleep(1000);
				} catch (InterruptedException e) {
					System.out.println("�����ˣ�");
					gameSocket.con=-1;
				}
			}
			
			os.close(); //�ر�Socket�����
			is.close(); //�ر�Socket������
			socket.close(); //�ر�Socket
			gameSocket.con=0;
			 
		} catch (IOException e) {
			System.out.println("�ͻ�������ʧ��");
			gameDialogVS.tipStr = "����������ʧ�ܣ�";
			gameSocket.con=-1;
			//���¿���
		}		
	}
}
/**
 * 
 * Socket������߳�
 * @author �Ƹ���
 *
 */
class Sever_Socket extends Thread{
	private ServerSocket server=null;
	private Socket socket=null;
	private boolean state = true;//����Ƿ�������
	public void stopThread()
	{
		try {
			new Socket("localhost",4700);//����һ��������
			state = false;//���û������״̬
		} catch (IOException e) {
			System.out.println("�����Ӵ���ʧ��");
			e.printStackTrace();
			gameSocket.con=-1;
		}
	}
	public void run()
	{			
			try {
				gameSocket.con_state = false;//��ʼ����������״̬λ
				server=new ServerSocket(4700);//����һ��ServerSocket�ڶ˿�4700�����ͻ�����
				socket=server.accept();//ʹ��accept()�����ȴ��ͻ������пͻ��������������һ��Socket���󣬲�����ִ��
				if(!state)//û�����ӣ��رշ�����
				{
					socket.close();
					server.close();
					System.out.println("�������˹ر�");
					gameSocket.con=0;
					return ;
				}
			
			} catch (IOException e) {	
				System.out.println("��ͻ�������ʧ��(�˿���ռ�ã��������ز���״̬)");
				gameSocket.con_state = true;//���ز���״̬���
				gameDialogVS.tipStr = "��ǰֻ�����ͻ��ˣ�";
				gameSocket.con=1;
				return;
			}
			
			gameWin.vsDialog.setVisible(false);//�رն�ս����
			System.out.println("����������");
			gameSocket.con = 2;//��ʼ״̬Ϊ������
			gameWin.start = true;//��ʼ��Ϸ
			try {
				DataInputStream is=new DataInputStream(socket.getInputStream());//��Socket����õ�����������������Ӧ��BufferedReader����
				DataOutputStream os=new DataOutputStream(socket.getOutputStream());//��Socket����õ��������������PrintWriter����
			while(true){
				os.writeBoolean(gameWin.start);//�����Ϸ��ʼ״̬
				os.writeInt(gameWin.fenShu);//�������
				if(gameWin.vsShu>0 && gameWin.vsShu%10 == 0)
				{
					gameWin.vsShu = 0;
					os.writeInt(1);//���������������
				}
				else os.writeInt(0);//���������������
				for(int i=0;i<10;i++)//�����ͼ����
					os.write(gameWin.sendMap2[i],0,gameWin.sendMap2[i].length);
				os.flush();//ˢ���������ʹClient�����յ����ַ���

				gameWin.vsStart= is.readBoolean();//��ȡ������Ϸ��ʼ״̬
				gameWin.vs_fenshu = is.readInt();//��ȡ���ַ���
				gameWin.shu = is.readInt();//��ȡ������
				for(int i = 0;i < 10;i++)//��ȡ���ֵ�ͼ����
					for(int j = 0;j < 18;j++)
						gameWin.vsMap[i][j] = (byte)is.read();
				
				//������Ϸֹͣ����				
				if(gameSocket.con == 0 || gameWin.vsStart == false && gameWin.start == false)
					break;
				
				try {
					sleep(1000);
				} catch (InterruptedException e) {
					System.out.println(e);
				}
			} //����ѭ��
			os.close(); //�ر�Socket�����
			is.close(); //�ر�Socket������
			socket.close(); //�ر�Socket
			server.close(); //�ر�ServerSocket
			gameSocket.con=0;
			} catch (IOException e) {
				
				try {
					socket.close(); //�ر�Socket
					server.close(); //�ر�ServerSocket
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				gameSocket.con=-1;
				return;
			}
	}
}


