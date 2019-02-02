package mychess.entity;

import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

import mychess.entity.Code;
import mychess.entity.DataMessage;
import mychess.entity.Message;
import mychess.entity.MyObjectOutputStream;
import mychess.entity.NormalMessage;
import mychess.util.Common;
import mychess.util.ReadProperties;
import mychess.util.Withdraw;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

/**
 * 数据服务器类
 */
public class Server {
	/**
	 * 所有已经连接到本服务器的客户端套接字，保存为一个列表
	 */
	private List<Socket> clients;
	
	/**
	 * 数据服务器的棋局状态<br>
	 * static修饰表明任何连接到本服务器的客户端能共享该数据<br>
	 * 这可以为旁观者提供及时准确的数据<br>
	 */
	private static int[][] data;
	
	private Withdraw withdraw;//撤销功能
	
	private static int time=1;//对战次数
	/**
	 * 完成初始化功能<br>
	 * 初始化连接客户端的列表,棋局的初始状态设置，悔棋列表，以及为每个连接的客户端以单独线程与之交互
	 * @param
	 */
	public Server() {
		// TODO Auto-generated constructor stub
		ServerSocket socket;
		clients=new ArrayList<Socket>();//已经连接的客户端
		init();
		try{
			//创建Socket
			socket=new ServerSocket(Integer.parseInt(ReadProperties.PORT));
			while(true){
				Socket socket_current=socket.accept();
				clients.add(socket_current);
				Thread t=new Thread(new server_thread(socket_current));
				t.start();
			}
		}catch(IOException e){
			e.printStackTrace();
		}
	}
	
	//加载数据
	private void init() {
		// TODO Auto-generated method stub
		ReadProperties.read();//读取配置文件
		
		data=new int[][]{{8,9,10,11,12,11,10,9,8},
			{0,0,0,0,0,0,0,0,0},
			{0,13,0,0,0,0,0,13,0},
			{14,0,14,0,14,0,14,0,14},
			{0,0,0,0,0,0,0,0,0},
			{0,0,0,0,0,0,0,0,0},
			{7,0,7,0,7,0,7,0,7},
			{0,6,0,0,0,0,0,6,0},
			{0,0,0,0,0,0,0,0,0},
			{1,2,3,4,5,4,3,2,1}
		};
		
		withdraw=new Withdraw();
	}

	/**
	 * 服务器线程类，处理每个连接的客户端
	 */
	class server_thread implements Runnable{
		/**
		 * 当前连接客户端的套接字
		 */
		Socket current_socket;
		
		/**
		 * 初始化套接字
		 * @param socket是当前连接的套接字
		 */
		public server_thread(Socket socket) {
			// TODO Auto-generated constructor stub
			current_socket=socket;
		}
		
		/**
		 * 于当前连接的套接字，服务器与之进行交互
		 */
		@Override
		public void run() {
			// TODO Auto-generated method stub
			ObjectInputStream inputStreamFromClient = null;
			ObjectOutputStream outputStreamToOtherClients = null;
			try {
				outputStreamToOtherClients=new ObjectOutputStream(current_socket.getOutputStream());
				inputStreamFromClient=new ObjectInputStream(current_socket.getInputStream());
				DataMessage message=new DataMessage();
				if(clients.size()==1){
					//只有一个用户
					if((time&1)==1){
						message.setRole((byte)1);
						message.setYourTurn(true);
					}else{
						message.setRole((byte)2);
						message.setYourTurn(false);
					}
					message.setCode(Code.Prepare);
					message.setData(Common.Array_to_String(data));
					outputStreamToOtherClients.writeObject(message);
					outputStreamToOtherClients.flush();
				}else if(clients.size()==2){
					//第二个用户执黑，后手
					if((time&1)==0){
						message.setRole((byte)1);
						message.setYourTurn(true);
					}else{
						message.setRole((byte)2);
						message.setYourTurn(false);
					}
					message.setCode(Code.Run);
					message.setData(Common.Array_to_String(data));
					outputStreamToOtherClients.writeObject(message);
					outputStreamToOtherClients.flush();
				}
				while(true){
					//交互
					//这会引发异常
					//不管收到什么消息，负责直接转发
					Message myMessage=(Message) inputStreamFromClient.readObject();//接收到红方的消息
					boolean restart=false;
					if(myMessage instanceof DataMessage){
						data[((DataMessage) myMessage).getRow()-1][((DataMessage) myMessage).getCol()-1]=data[((DataMessage) myMessage).getPrerow()-1][((DataMessage) myMessage).getPrecol()-1];
						data[((DataMessage) myMessage).getPrerow()-1][((DataMessage) myMessage).getPrecol()-1]=0;
						((DataMessage) myMessage).setData(Common.Array_to_String(data));
						withdraw.add(myMessage);//收到报文就加入到列表中
					}else if(myMessage instanceof NormalMessage){
						//一般消息
						if(((NormalMessage) myMessage).getAttach().equals("同意悔棋")){
							withdraw.remove();
							myMessage=(DataMessage) withdraw.getLast();
							myMessage.setValid(false);//发给所有人
							data=Common.String_to_Array(((DataMessage)myMessage).getData());//撤销后的棋局
						}else if(((NormalMessage) myMessage).getAttach().equals("游戏结束")){
							//收到该socket的通知,那么该socket是胜利方
							myMessage.setValid(false);//发给所有人
						}else if(((NormalMessage)myMessage).getAttach().equals("同意开局")){
							myMessage.setValid(false);//发送给所有人
							init();
							restart=true;
							time++;
						}else if(((NormalMessage)myMessage).getAttach().equals("同意求和")){
							myMessage.setValid(false);
						}
					}
					myMessage.setStep(withdraw.allSteps());//加上步数
					for(Socket s:clients){
						if(s==current_socket && myMessage.isValid()) continue;//如果valid为false表明发给任何人
						outputStreamToOtherClients=new MyObjectOutputStream(s.getOutputStream());
						outputStreamToOtherClients.writeObject(myMessage);
						outputStreamToOtherClients.flush();
					}
					if(restart) clients.clear();
				}
			} catch (Exception e) {
				// TODO Auto-generated catch block
				clients.clear();
				init();
			}finally {//后处理
				try {
					inputStreamFromClient.close();
					outputStreamToOtherClients.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
	}
	
	/**
	 * main方法，执行服务器运行
	 * @param
	 */
	public static void main(String[] args) {
		System.out.println("Server run...");
		new Server();
	}
}
