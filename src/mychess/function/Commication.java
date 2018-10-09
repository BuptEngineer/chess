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
			//���������ͻ��˵���Ϣ
			String message=GetMessage();
			if(message.equals("Redo")){
				//��������
				int value=JOptionPane.showConfirmDialog(null, "�Է�������壬�Ƿ�ͬ��", "Redo",
						JOptionPane.YES_NO_OPTION, JOptionPane.INFORMATION_MESSAGE);
				if(value==JOptionPane.YES_OPTION){
					//ͬ�����
					PostMessage("OK����");
				}else{
					PostMessage("NO����");
				}
			}else if(message.startsWith("OK")){
				//����
				JOptionPane.showMessageDialog(null, "�Է�ͬ�����"+message.substring(3));
				Redo rd=cb.getRd();
				rd.remove_last_step();
				int[][] data=rd.get_last_step();
				cb.setYourTurn(false);//���滹�����ݷ��񣬻Ὣ��ֵ��Ϊtrue
				cb.setRedo(true);
				cb.getInternet().writeMessage(Common.Array_to_String(data));
			}else if(message.startsWith("NO")){
				JOptionPane.showMessageDialog(null, "�Է���ͬ��"+message.substring(3));
				cb.setYourTurn(false);//���ܻ��壬���Է��ж�
				cb.setRedo(false);
			}else if(message.contains("you")){
				if(!cb.isYourTurn())
					JOptionPane.showMessageDialog(null, "Congratulate,you win.");//��Ϸ����
				else
					JOptionPane.showMessageDialog(null, "Sorry,you lose.");
				cb.setYourTurn(false);//���޷��ƶ�����
			}
		}
	}
}
