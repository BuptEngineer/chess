package mychess.function;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

public class Internet{
	private Socket socket;
	private DataInputStream inputStreamFromServer;
	private DataOutputStream outputStreamToServer;
	
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
	
	public void writeMessage(String message) {
		try {
			outputStreamToServer.writeUTF(message);
			outputStreamToServer.flush();
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
	
	public String readMessage() {
		String message = null;
		try {
			message=inputStreamFromServer.readUTF();//自动触发重绘
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
