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
 * 测试类（本游戏中无作用）
 * @author 闫庚哲
 *
 */
public class gameSocket {
	public static int con = 0;// 0：等待 1：客户端 2：服务端 -1：出错
	public static String ipstr = null;//连接的IP地址
	public static boolean con_state = false;//是否为本机连接测试状态
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
//			String ip=addr.getHostAddress().toString();//获得本机IP
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
//		//con = 1;测试设置
//		
//		t_client.start();
//		t_sever.start();
//		
//		while(con==0 )
//		{
//			try {
//				Thread.sleep(200);//等待
//			} catch (InterruptedException e) {
//				e.printStackTrace();
//			}
//		}
//		if(con == 1) {
//			System.out.println("客户端开启");
//			SS.stopThread();
//			t_sever.interrupt();
//		}
//		else {
//			System.out.println("服务器开启");
//			t_client.interrupt();
//		}
//	}
}
/**
 * 
 * Scoket客户端线程
 * @author 闫庚哲
 *
 */
class Client_Socket extends Thread{
	
	public void run() {
		if(gameSocket.con != 0) return;//不是等待状态则不连接
		
		try {
			if(gameSocket.ipstr.equals("随机对战"))
				gameSocket.ipstr ="182.254.137.53";
			Socket socket=new Socket(gameSocket.ipstr,4700);//向IP发出的4700端口发出客户请求
			System.out.println("客户端开启");
			gameSocket.con = 1;//标记状态
			gameWin.start = true;//标记游戏开始
			
			DataOutputStream os=new DataOutputStream(socket.getOutputStream());
			//由Socket对象得到输出流，并构造PrintWriter对象
			DataInputStream is=new DataInputStream(socket.getInputStream());
			//由Socket对象得到输入流，并构造相应的BufferedReader对象
			
			while(true){
				os.writeBoolean(gameWin.start);	//输出游戏开始状态			
				os.writeInt(gameWin.fenShu);//输出分数
				if(gameWin.vsShu>0 && gameWin.vsShu%10 == 0)
				{
					gameWin.vsShu = 0;
					os.writeInt(1);//输出对手增长数量
				}
				else os.writeInt(0);//输出对手增长数量
				for(int i=0;i<10;i++)//输出地图数组
					os.write(gameWin.sendMap2[i],0,gameWin.sendMap2[i].length);				
				os.flush();//刷新输出流，使Server马上收到该字符串

				gameWin.vsStart= is.readBoolean();//读取对手游戏开始状态
				gameWin.vs_fenshu = is.readInt();//读取对手分数
				gameWin.shu = is.readInt();//读取困难数
				for(int i = 0;i < 10;i++)//读取对手地图数组
					for(int j = 0;j < 18;j++)
						gameWin.vsMap[i][j] = (byte)is.read();
				
				//联机游戏停止条件				
				if(gameSocket.con == 0 || gameWin.vsStart == false && gameWin.start == false)
					break;

				try {
					sleep(1000);
				} catch (InterruptedException e) {
					System.out.println("出错了！");
					gameSocket.con=-1;
				}
			}
			
			os.close(); //关闭Socket输出流
			is.close(); //关闭Socket输入流
			socket.close(); //关闭Socket
			gameSocket.con=0;
			 
		} catch (IOException e) {
			System.out.println("客户端连接失败");
			gameDialogVS.tipStr = "服务器连接失败！";
			gameSocket.con=-1;
			//重新开启
		}		
	}
}
/**
 * 
 * Socket服务端线程
 * @author 闫庚哲
 *
 */
class Sever_Socket extends Thread{
	private ServerSocket server=null;
	private Socket socket=null;
	private boolean state = true;//标记是否连接上
	public void stopThread()
	{
		try {
			new Socket("localhost",4700);//创建一个假连接
			state = false;//标记没有连接状态
		} catch (IOException e) {
			System.out.println("假连接创建失败");
			e.printStackTrace();
			gameSocket.con=-1;
		}
	}
	public void run()
	{			
			try {
				gameSocket.con_state = false;//初始化本机测试状态位
				server=new ServerSocket(4700);//创建一个ServerSocket在端口4700监听客户请求
				socket=server.accept();//使用accept()阻塞等待客户请求，有客户，请求到来则产生一个Socket对象，并继续执行
				if(!state)//没有连接，关闭服务器
				{
					socket.close();
					server.close();
					System.out.println("服务器端关闭");
					gameSocket.con=0;
					return ;
				}
			
			} catch (IOException e) {	
				System.out.println("与客户端连接失败(端口已占用，启动本地测试状态)");
				gameSocket.con_state = true;//本地测试状态标记
				gameDialogVS.tipStr = "当前只能做客户端！";
				gameSocket.con=1;
				return;
			}
			
			gameWin.vsDialog.setVisible(false);//关闭对战窗口
			System.out.println("服务器开启");
			gameSocket.con = 2;//初始状态为服务器
			gameWin.start = true;//开始游戏
			try {
				DataInputStream is=new DataInputStream(socket.getInputStream());//由Socket对象得到输入流，并构造相应的BufferedReader对象
				DataOutputStream os=new DataOutputStream(socket.getOutputStream());//由Socket对象得到输出流，并构造PrintWriter对象
			while(true){
				os.writeBoolean(gameWin.start);//输出游戏开始状态
				os.writeInt(gameWin.fenShu);//输出分数
				if(gameWin.vsShu>0 && gameWin.vsShu%10 == 0)
				{
					gameWin.vsShu = 0;
					os.writeInt(1);//输出对手增长数量
				}
				else os.writeInt(0);//输出对手增长数量
				for(int i=0;i<10;i++)//输出地图数组
					os.write(gameWin.sendMap2[i],0,gameWin.sendMap2[i].length);
				os.flush();//刷新输出流，使Client马上收到该字符串

				gameWin.vsStart= is.readBoolean();//读取对手游戏开始状态
				gameWin.vs_fenshu = is.readInt();//读取对手分数
				gameWin.shu = is.readInt();//读取困难数
				for(int i = 0;i < 10;i++)//读取对手地图数组
					for(int j = 0;j < 18;j++)
						gameWin.vsMap[i][j] = (byte)is.read();
				
				//联机游戏停止条件				
				if(gameSocket.con == 0 || gameWin.vsStart == false && gameWin.start == false)
					break;
				
				try {
					sleep(1000);
				} catch (InterruptedException e) {
					System.out.println(e);
				}
			} //继续循环
			os.close(); //关闭Socket输出流
			is.close(); //关闭Socket输入流
			socket.close(); //关闭Socket
			server.close(); //关闭ServerSocket
			gameSocket.con=0;
			} catch (IOException e) {
				
				try {
					socket.close(); //关闭Socket
					server.close(); //关闭ServerSocket
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				gameSocket.con=-1;
				return;
			}
	}
}


