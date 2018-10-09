package mychess.function;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

import javax.swing.JOptionPane;

import mychess.ui.ChessBoard;
import mychess.util.Common;

public class Commication implements Runnable{
	private Socket socket;
	private DataInputStream inputStreamFromServer;
	private DataOutputStream outputStreamToServer;
	ChessBoard cb;
	public Commication(ChessBoard cb) {
		// TODO Auto-generated constructor stub
		this.cb=cb;
		try {
			socket=new Socket("127.0.0.1", 12456);
			inputStreamFromServer=new DataInputStream(socket.getInputStream());
			outputStreamToServer=new DataOutputStream(socket.getOutputStream());
		} catch (Exception e) {
			// TODO: handle exception
		}
	}
	
	public String GetMessage() {
		try {
			return inputStreamFromServer.readUTF();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
	
	public void PostMessage(String message) {
		try {
			outputStreamToServer.writeUTF(message);
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
	}

	@Override
	public void run() {
		// TODO Auto-generated method stub
		while(true){
			//接收其他客户端的消息
			String message=GetMessage();
			if(message.equals("Redo")){
				//撤销操作
				int value=JOptionPane.showConfirmDialog(null, "对方请求悔棋，是否同意", "Redo",
						JOptionPane.YES_NO_OPTION, JOptionPane.INFORMATION_MESSAGE);
				if(value==JOptionPane.YES_OPTION){
					//同意悔棋
					PostMessage("OK悔棋");
				}else{
					PostMessage("NO悔棋");
				}
			}else if(message.startsWith("OK")){
				//操作
				JOptionPane.showMessageDialog(null, "对方同意悔棋"+message.substring(3));
				Redo rd=cb.getRd();
				rd.remove_last_step();
				int[][] data=rd.get_last_step();
				cb.setYourTurn(false);//后面还有数据服务，会将该值设为true
				cb.setRedo(true);
				cb.getInternet().writeMessage(Common.Array_to_String(data));
			}else if(message.startsWith("NO")){
				JOptionPane.showMessageDialog(null, "对方不同意"+message.substring(3));
				cb.setYourTurn(false);//不能悔棋，到对方行动
				cb.setRedo(false);
			}else if(message.contains("you")){
				if(!cb.isYourTurn())
					JOptionPane.showMessageDialog(null, "Congratulate,you win.");//游戏结束
				else
					JOptionPane.showMessageDialog(null, "Sorry,you lose.");
				cb.setYourTurn(false);//都无法移动棋子
			}
		}
	}
}
