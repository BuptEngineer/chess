package mychess.function;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

import javax.swing.JOptionPane;

import mychess.ui.ChessBoard;
import mychess.util.Common;

/**
 * ��������н����ݷ���������Ϣ�������ֱ���
 * ���Ƕ�Ӧ��python����Ϣ�������Ĵ����ࡣ
 */
public class Commication implements Runnable{
	private Socket socket;
	private DataInputStream inputStreamFromServer;
	private DataOutputStream outputStreamToServer;
	/**
	 * ������������
	 */
	ChessBoard cb;
	/**
	 * ����Ϣ��������������
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
	 * ����Ϣ�������л�ȡ��Ϣ
	 * @param
	 */
	public String GetMessage() {
		try {
			return inputStreamFromServer.readUTF();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;//�쳣����null
	}
	
	/**
	 * ����Ϣ������������Ϣ
	 * @param message��Ҫ���͵���Ϣ
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
	 * ִ���̣߳����ں���Ϣ���������н���
	 */
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
				//�Է�ͬ������Ĳ����������Ϣ���˷��ͷ����������˶�����յ�
				//��ˣ�ʵ���϶���ͬһ���ͻ�����˵��Redo��OKֻ����յ�һ��
				JOptionPane.showMessageDialog(null, "�Է�ͬ�����"+message.substring(3));//��ʾ�Է�ͬ��
				Redo rd=cb.getRd();//�õ��Ѿ��߹�������б�
				rd.remove_last_step();//���б����һ��ɾ�������������Ϊ�б��е����ڶ������
				int[][] data=rd.get_last_step();
				cb.setYourTurn(false);
				cb.setRedo(true);
				cb.getInternet().writeMessage(Common.Array_to_String(data));//����������ַ��͵����ݷ�������
			}else if(message.startsWith("NO")){//�Է���ͬ�����
				JOptionPane.showMessageDialog(null, "�Է���ͬ��"+message.substring(3));
				cb.setYourTurn(false);
				cb.setRedo(false);
			}else if(message.contains("you")){//�����ж���Ϸ��������Ϣ�����ҽ�����Ϸ����ʤ���ж���ʱ�򴥷�
				if(!cb.isYourTurn())
					JOptionPane.showMessageDialog(null, "Congratulate,you win.");//��Ϸ����
				else
					JOptionPane.showMessageDialog(null, "Sorry,you lose.");
				cb.setYourTurn(false);//���޷��ƶ�����
			}
		}
	}
}
