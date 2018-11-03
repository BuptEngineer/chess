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
 * ���ݷ�������
 */
public class Server {
	/**
	 * �����Ѿ����ӵ����������Ŀͻ����׽��֣�����Ϊһ���б�
	 */
	private List<Socket> clients;
	/**
	 * ���ݷ����������״̬<br>
	 * static���α����κ����ӵ����������Ŀͻ����ܹ��������<br>
	 * �����Ϊ�Թ����ṩ��ʱ׼ȷ������<br>
	 */
	private static int[][] data;
	
	/**
	 * ��ɳ�ʼ������<br>
	 * ��ʼ�����ӿͻ��˵��б�,��ֵĳ�ʼ״̬���ã������б��Լ�Ϊÿ�����ӵĿͻ����Ե����߳���֮����
	 * @param
	 */
	public Server() {
		// TODO Auto-generated constructor stub
		init();
		new Redo();
		ServerSocket socket;
		clients=new ArrayList<Socket>();//�Ѿ����ӵĿͻ���
		try{
			//����Socket
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
	 * �������������ڳ�ʼ����ֵ����״̬
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
	 * �������߳��࣬����ÿ�����ӵĿͻ���
	 */
	class server_thread implements Runnable{
		/**
		 * ��ǰ���ӿͻ��˵��׽���
		 */
		Socket current_socket;
		
		/**
		 * ��ʼ���׽���
		 * @param socket�ǵ�ǰ���ӵ��׽���
		 */
		public server_thread(Socket socket) {
			// TODO Auto-generated constructor stub
			current_socket=socket;
		}
		
		/**
		 * �ڵ�ǰ���ӵ��׽��֣���������֮���н���
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
					//��һ���û�ִ�죬����
					outputStreamToOtherClients.writeUTF("yourTurn=true");
					outputStreamToOtherClients.writeUTF(Array_to_String(data));
				}else if(clients.size()==2){
					//�ڶ����û�ִ�ڣ�����
					outputStreamToOtherClients.writeUTF("yourTurn=false");
					outputStreamToOtherClients.writeUTF(Array_to_String(data));
				}else{
					//�����û��趨Ϊ�Թ���
					outputStreamToOtherClients.writeUTF("role=observer");
					outputStreamToOtherClients.writeUTF(Array_to_String(data));
				}
				
				while(true){
					//����
					//��������쳣
					String message=inputStreamFromClient.readUTF();//
					
					if(message.equals("Congratulate,you win.")){
						//��Ϸ��������ÿ�����ӵ��û�������Ϸʤ����Ϣ
						for(Socket s:clients){
							outputStreamToOtherClients=new DataOutputStream(s.getOutputStream());
							//����ǵ�ǰ�Ŀͻ��ˣ�������Ϣ�������ģ��������ʤ����������и�
							if(s==current_socket) outputStreamToOtherClients.writeUTF("Congratulate,you win.");
							else outputStreamToOtherClients.writeUTF("Sorry,you lose.");
							outputStreamToOtherClients.flush();
						}
						continue;
					}
					
					if(message.indexOf("\n")>=0){//���Ǵ���ͻ��˷��͹����Ķ�ά����data
						//��data
						data=String_to_Array(message);
					}else{//���Ǵ���ͻ��˷��͹�����һά���飨�ĸ����꣩
						//�޸ı���data
						String[] fields=message.split(" ");
						int prerow=Integer.parseInt(fields[0]),precol=Integer.parseInt(fields[1]),
								row=Integer.parseInt(fields[2]),col=Integer.parseInt(fields[3]);
						data[row-1][col-1]=data[prerow-1][precol-1];
						data[prerow-1][precol-1]=0;
					}
					for(Socket s:clients){
						//�������������ӵĿͻ��˹㲥���µ����data
						outputStreamToOtherClients=new DataOutputStream(s.getOutputStream());
						outputStreamToOtherClients.writeUTF(Array_to_String(data));
						outputStreamToOtherClients.flush();
					}
				}
			} catch (IOException e) {
				// TODO Auto-generated catch block
				//�в����˹ر�������
				//JOptionPane.showMessageDialog(null, "�пͻ����˳����ӣ�������������...");
				clients.clear();
				init();
				return;
			}finally {//����
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
	 * ������ת��Ϊ�ַ��������ڷ������Ϳͻ���֮�䴫��
	 * @param data���������
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
	 * ���ַ���ת��Ϊ���飬���ϸ������������
	 * @param temp�ǽ��յ����ַ���
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
	 * main������ִ�з���������
	 * @param
	 */
	public static void main(String[] args) {
		new Server();
	}
}
