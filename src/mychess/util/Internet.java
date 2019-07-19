package mychess.util;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

import mychess.entity.Message;

/**
 * 用于网络通信的类
 */
public class Internet{
	private Socket socket;
	private ObjectInputStream inputStreamFromServer;
	private ObjectOutputStream outputStreamToServer;
	
	/**
	 * 对数据服务器进行连接
	 * @param
	 */
	public Internet() {
		// TODO Auto-generated constructor stub
		try {
			socket=new Socket(ReadProperties.IP, Integer.parseInt(ReadProperties.PORT));
			outputStreamToServer=new ObjectOutputStream(socket.getOutputStream());
			inputStreamFromServer=new ObjectInputStream(socket.getInputStream());//阻塞直到对面的outputstream开启
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	/**
	 * 给数据服务器发送消息
	 * @param message是要发送给数据服务器的本地消息
	 */
	public void writeMessage(Message message) {
		try {
			outputStreamToServer.writeObject(message);
			outputStreamToServer.flush();//清空缓存区
		} catch (IOException e) {
			// TODO Auto-generated catch block
			//这里处理异常，当服务器的流关闭的时候触发该异常
			try {
				socket.close();
				inputStreamFromServer.close();
				outputStreamToServer.close();
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			System.exit(1);
		}
	}
	
	/**
	 * 从数据服务器读消息
	 * @param
	 */
	public Message readMessage() {
		Message message = null;
		try {
			message=(Message) inputStreamFromServer.readObject();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			//这里处理异常，当服务器的流关闭的时候触发该异常
			try {
				socket.close();
				inputStreamFromServer.close();
				outputStreamToServer.close();
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			System.exit(1);//发生异常退出
		}
		return message;
	}
}
