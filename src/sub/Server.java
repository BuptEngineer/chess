package sub;

import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JOptionPane;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

public class Server {
	private List<Socket> clients;
	private static int[][] data;
	
	public Server() {
		// TODO Auto-generated constructor stub
		init();
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
	
	//服务器线程，处理每个连接的客户端
	class server_thread implements Runnable{
		Socket current_socket;
		
		public server_thread(Socket socket) {
			// TODO Auto-generated constructor stub
			current_socket=socket;
		}
		
		@Override
		public void run() {
			// TODO Auto-generated method stub
			DataInputStream inputStreamFromClient = null;
			DataOutputStream outputStreamToOtherClients = null;
			try {
				inputStreamFromClient=new DataInputStream(current_socket.getInputStream());
				outputStreamToOtherClients=new DataOutputStream(current_socket.getOutputStream());
				if(clients.size()==1){
					//第一个用户执红
					outputStreamToOtherClients.writeUTF("yourTurn=true");
				}else if(clients.size()==2){
					outputStreamToOtherClients.writeUTF("yourTurn=false");
				}else{
					outputStreamToOtherClients.writeUTF("role=observer");
				}
				while(true){
					//交互
					//这会引发异常
					String message=inputStreamFromClient.readUTF();//
					//修改本地data
					String[] fields=message.split(" ");
					int prerow=Integer.parseInt(fields[0]),precol=Integer.parseInt(fields[1]),
							row=Integer.parseInt(fields[2]),col=Integer.parseInt(fields[3]);
					data[row-1][col-1]=data[prerow-1][precol-1];
					data[prerow-1][precol-1]=0;//同步?
					for(Socket s:clients){
						//向其他所有连接的客户端广播
						outputStreamToOtherClients=new DataOutputStream(s.getOutputStream());
						outputStreamToOtherClients.writeUTF(Array_to_String(data));
						outputStreamToOtherClients.flush();
					}
				}
			} catch (IOException e) {
				// TODO Auto-generated catch block
				//有参与人关闭了连接
				JOptionPane.showMessageDialog(null, "有客户端退出连接，服务器将重置...");
				clients.clear();
				init();
				return;
			}finally {
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
	
	public static void main(String[] args) {
		new Server();
	}
}
