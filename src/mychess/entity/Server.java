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
	
	private Withdraw withdraw;//��������
	
	private static int time=1;//��ս����
	/**
	 * ��ɳ�ʼ������<br>
	 * ��ʼ�����ӿͻ��˵��б�,��ֵĳ�ʼ״̬���ã������б��Լ�Ϊÿ�����ӵĿͻ����Ե����߳���֮����
	 * @param
	 */
	public Server() {
		// TODO Auto-generated constructor stub
		ServerSocket socket;
		clients=new ArrayList<Socket>();//�Ѿ����ӵĿͻ���
		init();
		try{
			//����Socket
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
	
	//��������
	private void init() {
		// TODO Auto-generated method stub
		ReadProperties.read();//��ȡ�����ļ�
		
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
					//�ڶ����û�ִ�ڣ�����
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
					//����
					//��������쳣
					//�����յ�ʲô��Ϣ������ֱ��ת��
					Message myMessage=(Message) inputStreamFromClient.readObject();//���յ��췽����Ϣ
					boolean restart=false;
					if(myMessage instanceof DataMessage){
						data[((DataMessage) myMessage).getRow()-1][((DataMessage) myMessage).getCol()-1]=data[((DataMessage) myMessage).getPrerow()-1][((DataMessage) myMessage).getPrecol()-1];
						data[((DataMessage) myMessage).getPrerow()-1][((DataMessage) myMessage).getPrecol()-1]=0;
						((DataMessage) myMessage).setData(Common.Array_to_String(data));
						withdraw.add(myMessage);//�յ����ľͼ��뵽�б���
					}else if(myMessage instanceof NormalMessage){
						//һ����Ϣ
						if(((NormalMessage) myMessage).getAttach().equals("ͬ�����")){
							withdraw.remove();
							myMessage=(DataMessage) withdraw.getLast();
							myMessage.setValid(false);//����������
							data=Common.String_to_Array(((DataMessage)myMessage).getData());//����������
						}else if(((NormalMessage) myMessage).getAttach().equals("��Ϸ����")){
							//�յ���socket��֪ͨ,��ô��socket��ʤ����
							myMessage.setValid(false);//����������
						}else if(((NormalMessage)myMessage).getAttach().equals("ͬ�⿪��")){
							myMessage.setValid(false);//���͸�������
							init();
							restart=true;
							time++;
						}else if(((NormalMessage)myMessage).getAttach().equals("ͬ�����")){
							myMessage.setValid(false);
						}
					}
					myMessage.setStep(withdraw.allSteps());//���ϲ���
					for(Socket s:clients){
						if(s==current_socket && myMessage.isValid()) continue;//���validΪfalse���������κ���
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
