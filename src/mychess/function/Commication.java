package mychess.function;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

import javax.swing.JOptionPane;

import mychess.ui.ChessBoard;
import mychess.util.Common;

/**
 * 由于设计中将数据服务器和消息服务器分别处理。
 * 这是对应于python的消息服务器的处理类。
 */
public class Commication implements Runnable{
	private Socket socket;
	private DataInputStream inputStreamFromServer;
	private DataOutputStream outputStreamToServer;
	/**
	 * 依赖于棋盘类
	 */
	ChessBoard cb;
	/**
	 * 对消息服务器进行连接
	 * @param
	 */
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
	
	/**
	 * 从消息服务器中获取消息
	 * @param
	 */
	public String GetMessage() {
		try {
			return inputStreamFromServer.readUTF();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;//异常返回null
	}
	
	/**
	 * 向消息服务器发送消息
	 * @param message是要发送的消息
	 */
	public void PostMessage(String message) {
		try {
			outputStreamToServer.writeUTF(message);
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
	}

	/**
	 * 执行线程，用于和消息服务器进行交互
	 */
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
				//对方同意悔棋后的操作，这个消息除了发送方以外其他人都会接收到
				//因此，实际上对于同一个客户端来说，Redo和OK只会接收到一个
				JOptionPane.showMessageDialog(null, "对方同意悔棋"+message.substring(3));//提示对方同意
				Redo rd=cb.getRd();//得到已经走过的棋局列表
				rd.remove_last_step();//将列表最后一个删除，并将棋局置为列表中倒数第二个棋局
				int[][] data=rd.get_last_step();
				cb.setYourTurn(false);
				cb.setRedo(true);
				cb.getInternet().writeMessage(Common.Array_to_String(data));//悔棋后的新棋局发送到数据服务器中
			}else if(message.startsWith("NO")){//对方不同意悔棋
				JOptionPane.showMessageDialog(null, "对方不同意"+message.substring(3));
				cb.setYourTurn(false);
				cb.setRedo(false);
			}else if(message.contains("you")){//这是判断游戏结束的消息，当且仅当游戏到达胜负判定的时候触发
				if(!cb.isYourTurn())
					JOptionPane.showMessageDialog(null, "Congratulate,you win.");//游戏结束
				else
					JOptionPane.showMessageDialog(null, "Sorry,you lose.");
				cb.setYourTurn(false);//都无法移动棋子
			}
		}
	}
}
