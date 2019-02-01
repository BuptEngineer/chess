package mychess.ui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;

import mychess.script.ReadProperties;


//该类实现象棋的图形界面,并负责通信
public class ChessClient extends JFrame{

	private static final long serialVersionUID = 1L;
	private JButton restart=new JButton("重新开始");
	private JButton video=new JButton("播放录像");
	private JButton cancel=new JButton("悔棋");
	private Image[] pics =new Image[15];//加载象棋图片

	public ChessClient() {
		pics[1]=Toolkit.getDefaultToolkit().getImage("images/车1.png");
		pics[2]=Toolkit.getDefaultToolkit().getImage("images/马1.png");
		pics[3]=Toolkit.getDefaultToolkit().getImage("images/相1.png");
		pics[4]=Toolkit.getDefaultToolkit().getImage("images/士1.png");
		pics[5]=Toolkit.getDefaultToolkit().getImage("images/帅.png");
		pics[6]=Toolkit.getDefaultToolkit().getImage("images/炮1.png");
		pics[7]=Toolkit.getDefaultToolkit().getImage("images/兵.png");
		pics[8]=Toolkit.getDefaultToolkit().getImage("images/车2.png");
		pics[9]=Toolkit.getDefaultToolkit().getImage("images/马2.png");
		pics[10]=Toolkit.getDefaultToolkit().getImage("images/象2.png");
		pics[11]=Toolkit.getDefaultToolkit().getImage("images/士2.png");
		pics[12]=Toolkit.getDefaultToolkit().getImage("images/将.png");
		pics[13]=Toolkit.getDefaultToolkit().getImage("images/炮2.png");
		pics[14]=Toolkit.getDefaultToolkit().getImage("images/卒.png");
		final ChessBoard panel=new ChessBoard(pics);
		add(panel,BorderLayout.CENTER);
		JPanel panel2=new JPanel();
		panel2.add(restart);
		panel2.add(cancel);
		panel2.add(video);
		add(panel2,BorderLayout.SOUTH);
		restart.setBackground(new Color(216,196,152));
		video.setBackground(new Color(216,196,152));
		cancel.setBackground(new Color(216,196,152));
		panel2.setBackground(new Color(216,196,152));
		panel.setBackground(new Color(216,196,152));
		
		//绑定事件
		cancel.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				panel.Redo();
			}
		});
	}
	
	public static void main(String[] args) {
		ReadProperties.read();//读取配置文件
		JFrame frame=new ChessClient();
		frame.setTitle("象棋");
		frame.setSize(900,700);
//		frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setLocationRelativeTo(null);
		frame.setVisible(true);
	}
}
