package mychess.script;

import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JOptionPane;

import mychess.entity.Code;
import mychess.entity.DataMessage;
import mychess.entity.Message;
import mychess.entity.MyObjectOutputStream;
import mychess.entity.NormalMessage;

import java.awt.Image;
import java.awt.Toolkit;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.EOFException;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

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
	
	private Image[] pics;//��������ͼƬ
	
	/**
	 * ��ɳ�ʼ������<br>
	 * ��ʼ�����ӿͻ��˵��б�,��ֵĳ�ʼ״̬���ã������б��Լ�Ϊÿ�����ӵĿͻ����Ե����߳���֮����
	 * @param
	 */
	public Server() {
		// TODO Auto-generated constructor stub
		init();
		//new Redo();
		ServerSocket socket;
		clients=new ArrayList<Socket>();//�Ѿ����ӵĿͻ���
		try{
			//����Socket
			socket=new ServerSocket(12357);
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
	
	//��������
	private void init() {
		// TODO Auto-generated method stub
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
			ObjectInputStream inputStreamFromClient = null;
			ObjectOutputStream outputStreamToOtherClients = null;
			try {
				outputStreamToOtherClients=new ObjectOutputStream(current_socket.getOutputStream());
				inputStreamFromClient=new ObjectInputStream(current_socket.getInputStream());
				DataMessage message=new DataMessage();
				if(clients.size()==1){
					//ֻ��һ���û�
					message.setRole((byte)1);
					message.setYourTurn(true);
					message.setCode(Code.Prepare);
					message.setData(data);
					message.setPics(pics);
					outputStreamToOtherClients.writeObject(message);
					outputStreamToOtherClients.flush();
				}else if(clients.size()==2){
					//�ڶ����û�ִ�ڣ�����
					message.setRole((byte)2);
					message.setYourTurn(false);
					message.setCode(Code.Run);
					message.setData(data);
					message.setPics(pics);
					outputStreamToOtherClients.writeObject(message);
					outputStreamToOtherClients.flush();
				}else{
					//�����û��趨Ϊ�Թ���
					message.setRole((byte)3);
					message.setData(data);
					message.setPics(pics);
					outputStreamToOtherClients.writeObject(message);
					outputStreamToOtherClients.flush();
				}
				
				while(true){
					//����
					//��������쳣
					//�����յ�ʲô��Ϣ������ֱ��ת��
					Message myMessage=(Message) inputStreamFromClient.readObject();//���յ��췽����Ϣ
					//Message myMessage=getLastMessage(inputStreamFromClient);
					//JOptionPane.showMessageDialog(null, ((DataMessage) myMessage).getPrerow()+","+((DataMessage) myMessage).getPrecol()+","+((DataMessage) myMessage).getRow()+","+((DataMessage) myMessage).getCol()+","+current_socket.getRemoteSocketAddress());
					if(myMessage instanceof DataMessage){
						((DataMessage) myMessage).setData(data);
						((DataMessage) myMessage).changeArray();
						data=((DataMessage) myMessage).getData();//����˸�������
					}
					
					for(Socket s:clients){
						if(s==current_socket) continue;
						outputStreamToOtherClients=new MyObjectOutputStream(s.getOutputStream());//
						outputStreamToOtherClients.writeObject(myMessage);
						outputStreamToOtherClients.flush();
					}
				}
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				clients.clear();
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
	 * main������ִ�з���������
	 * @param
	 */
	public static void main(String[] args) {
		System.out.println("Server run...");
		new Server();
	}
}
