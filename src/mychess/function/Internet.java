package mychess.function;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

/**
 * 和Commication类相似，这是对应于数据服务器的类
 */
public class Internet{
	private Socket socket;
	private DataInputStream inputStreamFromServer;
	private DataOutputStream outputStreamToServer;
	
	/**
	 * 对数据服务器进行连接
	 * @param
	 */
	public Internet() {
		// TODO Auto-generated constructor stub
		try {
			socket=new Socket("127.0.0.1", 12356);
			inputStreamFromServer=new DataInputStream(socket.getInputStream());
			outputStreamToServer=new DataOutputStream(socket.getOutputStream());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	/**
	 * 给数据服务器发送消息
	 * @param message是要发送给数据服务器的本地消息
	 */
	public void writeMessage(String message) {
		try {
			outputStreamToServer.writeUTF(message);
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
			System.exit(1);//发生异常退出
		}
	}
	
	/**
	 * 从数据服务器读消息
	 * @param
	 */
	public String readMessage() {
		String message = null;
		try {
			message=inputStreamFromServer.readUTF();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			//对方关闭了连接,当服务器关闭连接的时候触发
			try {
				socket.close();
				inputStreamFromServer.close();
				outputStreamToServer.close();
				System.exit(1);
			} catch (IOException ex) {
				// TODO Auto-generated catch block
				ex.printStackTrace();
			}
		}
		return message;
	}
}
