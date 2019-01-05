package mychess.function;

import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

import javax.swing.JOptionPane;

import mychess.entity.Message;

/**
 * ��������ͨ�ŵ���
 */
public class Internet{
	private Socket socket;
	private ObjectInputStream inputStreamFromServer;
	private ObjectOutputStream outputStreamToServer;
	
	/**
	 * �����ݷ�������������
	 * @param
	 */
	public Internet() {
		// TODO Auto-generated constructor stub
		try {
			socket=new Socket("127.0.0.1", 12357);
			outputStreamToServer=new ObjectOutputStream(socket.getOutputStream());
			inputStreamFromServer=new ObjectInputStream(socket.getInputStream());//����ֱ�������outputstream����
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	/**
	 * �����ݷ�����������Ϣ
	 * @param message��Ҫ���͸����ݷ������ı�����Ϣ
	 */
	public void writeMessage(Message message) {
		try {
			outputStreamToServer.writeObject(message);
			outputStreamToServer.flush();//��ջ�����
		} catch (IOException e) {
			// TODO Auto-generated catch block
			//���ﴦ���쳣���������������رյ�ʱ�򴥷����쳣
			try {
				socket.close();
				inputStreamFromServer.close();
				outputStreamToServer.close();
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			System.exit(1);//�����쳣�˳�
		}
	}
	
	/**
	 * �����ݷ���������Ϣ
	 * @param
	 */
	public Message readMessage() {
		Message message = null;
		try {
			message=(Message) inputStreamFromServer.readObject();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			//�Է��ر�������,���������ر����ӵ�ʱ�򴥷�
//			try {
//				socket.close();
//				inputStreamFromServer.close();
//				outputStreamToServer.close();
//				System.exit(1);
//			} catch (IOException ex) {
//				// TODO Auto-generated catch block
//				ex.printStackTrace();
//			}
			e.printStackTrace();
		}
		return message;
	}
}
