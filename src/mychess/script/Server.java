package mychess.script;

import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

import mychess.function.Redo;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

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
	
	/**
	 * 完成初始化功能<br>
	 * 初始化连接客户端的列表,棋局的初始状态设置，悔棋列表，以及为每个连接的客户端以单独线程与之交互
	 * @param
	 */
	public Server() {
		// TODO Auto-generated constructor stub
		init();
		new Redo();
		ServerSocket socket;
		clients=new ArrayList<Socket>();//已经连接的客户端
		try{
			//创建Socket
			socket=new ServerSocket(12356);
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
	
	/**
	 * 辅助方法，用于初始化棋局的最初状态
	 * @param
	 */
	private void init() {
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
			DataInputStream inputStreamFromClient = null;
			DataOutputStream outputStreamToOtherClients = null;
			try {
				inputStreamFromClient=new DataInputStream(current_socket.getInputStream());
				outputStreamToOtherClients=new DataOutputStream(current_socket.getOutputStream());
				if(clients.size()==1){
					//第一个用户执红，先手
					outputStreamToOtherClients.writeUTF("yourTurn=true");
					outputStreamToOtherClients.writeUTF(Array_to_String(data));
				}else if(clients.size()==2){
					//第二个用户执黑，后手
					outputStreamToOtherClients.writeUTF("yourTurn=false");
					outputStreamToOtherClients.writeUTF(Array_to_String(data));
				}else{
					//其余用户设定为旁观者
					outputStreamToOtherClients.writeUTF("role=observer");
					outputStreamToOtherClients.writeUTF(Array_to_String(data));
				}
				
				while(true){
					//交互
					//这会引发异常
					String message=inputStreamFromClient.readUTF();//
					
					if(message.equals("Congratulate,you win.")){
						//游戏结束，给每个连接的用户发送游戏胜负消息
						for(Socket s:clients){
							outputStreamToOtherClients=new DataOutputStream(s.getOutputStream());
							//如果是当前的客户端，由于消息是他发的，因此他获胜，其余玩家判负
							if(s==current_socket) outputStreamToOtherClients.writeUTF("Congratulate,you win.");
							else outputStreamToOtherClients.writeUTF("Sorry,you lose.");
							outputStreamToOtherClients.flush();
						}
						continue;
					}
					
					if(message.indexOf("\n")>=0){//这是处理客户端发送过来的二维数组data
						//传data
						data=String_to_Array(message);
					}else{//这是处理客户端发送过来的一维数组（四个坐标）
						//修改本地data
						String[] fields=message.split(" ");
						int prerow=Integer.parseInt(fields[0]),precol=Integer.parseInt(fields[1]),
								row=Integer.parseInt(fields[2]),col=Integer.parseInt(fields[3]);
						data[row-1][col-1]=data[prerow-1][precol-1];
						data[prerow-1][precol-1]=0;
					}
					for(Socket s:clients){
						//向其他所有连接的客户端广播最新的棋局data
						outputStreamToOtherClients=new DataOutputStream(s.getOutputStream());
						outputStreamToOtherClients.writeUTF(Array_to_String(data));
						outputStreamToOtherClients.flush();
					}
				}
			} catch (IOException e) {
				// TODO Auto-generated catch block
				//有参与人关闭了连接
				//JOptionPane.showMessageDialog(null, "有客户端退出连接，服务器将重置...");
				clients.clear();
				init();
				return;
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
	 * 将数组转换为字符串，以在服务器和客户端之间传输
	 * @param data是棋局数组
	 */
	public static String Array_to_String(int[][] data) {
		String temp="";
		for(int i=0;i<data.length;i++){
			for(int j=0;j<data[i].length;j++){
				temp+=data[i][j]+" ";
			}
			temp+="\n";
		}
		return temp;
	}
	
	/**
	 * 将字符串转换为数组，是上个方法的逆操作
	 * @param temp是接收到的字符串
	 */
	public static int[][] String_to_Array(String temp) {
		String[] lines=temp.split("\n");
		int[][] datasub=new int[10][9];
		for(int i=0;i<lines.length;i++){
			String[] inner=lines[i].trim().split(" ");
			for(int j=0;j<inner.length;j++){
				datasub[i][j]=Integer.parseInt(inner[j]);
			}
		}
		return datasub;
	}
	
	/**
	 * main方法，执行服务器运行
	 * @param
	 */
	public static void main(String[] args) {
		new Server();
	}
}
