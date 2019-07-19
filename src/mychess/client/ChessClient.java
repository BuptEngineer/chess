package mychess.client;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;

import mychess.util.ReadProperties;


//该类实现象棋的图形界面,并负责通信
public class ChessClient extends JFrame{

	private static final long serialVersionUID = 1L;
	private JButton restart=new JButton("重开");
	private JButton redo=new JButton("悔棋");
	private JButton lose=new JButton("认输");
	private JButton peace=new JButton("求和");
	private Image[] pics =new Image[15];//加载象棋图片
	private ChessBoard panel;
	
	public ChessClient() {
//		pics[1]=Toolkit.getDefaultToolkit().getImage("images/车1.png");
//		pics[2]=Toolkit.getDefaultToolkit().getImage("images/马1.png");
//		pics[3]=Toolkit.getDefaultToolkit().getImage("images/相1.png");
//		pics[4]=Toolkit.getDefaultToolkit().getImage("images/士1.png");
//		pics[5]=Toolkit.getDefaultToolkit().getImage("images/帅.png");
//		pics[6]=Toolkit.getDefaultToolkit().getImage("images/炮1.png");
//		pics[7]=Toolkit.getDefaultToolkit().getImage("images/兵.png");
//		pics[8]=Toolkit.getDefaultToolkit().getImage("images/车2.png");
//		pics[9]=Toolkit.getDefaultToolkit().getImage("images/马2.png");
//		pics[10]=Toolkit.getDefaultToolkit().getImage("images/象2.png");
//		pics[11]=Toolkit.getDefaultToolkit().getImage("images/士2.png");
//		pics[12]=Toolkit.getDefaultToolkit().getImage("images/将.png");
//		pics[13]=Toolkit.getDefaultToolkit().getImage("images/炮2.png");
//		pics[14]=Toolkit.getDefaultToolkit().getImage("images/卒.png");
		
		pics[1]=Toolkit.getDefaultToolkit().getImage("images/chess11.png");
		pics[2]=Toolkit.getDefaultToolkit().getImage("images/chess10.png");
		pics[3]=Toolkit.getDefaultToolkit().getImage("images/chess9.png");
		pics[4]=Toolkit.getDefaultToolkit().getImage("images/chess8.png");
		pics[5]=Toolkit.getDefaultToolkit().getImage("images/chess7.png");
		pics[6]=Toolkit.getDefaultToolkit().getImage("images/chess12.png");
		pics[7]=Toolkit.getDefaultToolkit().getImage("images/chess13.png");
		pics[8]=Toolkit.getDefaultToolkit().getImage("images/chess4.png");
		pics[9]=Toolkit.getDefaultToolkit().getImage("images/chess3.png");
		pics[10]=Toolkit.getDefaultToolkit().getImage("images/chess2.png");
		pics[11]=Toolkit.getDefaultToolkit().getImage("images/chess1.png");
		pics[12]=Toolkit.getDefaultToolkit().getImage("images/chess0.png");
		pics[13]=Toolkit.getDefaultToolkit().getImage("images/chess5.png");
		pics[14]=Toolkit.getDefaultToolkit().getImage("images/chess6.png");
		panel=new ChessBoard(pics);
		add(panel,BorderLayout.CENTER);
		JPanel panel2=new JPanel();
		panel2.add(restart);
		panel2.add(redo);
		panel2.add(lose);
		panel2.add(peace);
		add(panel2,BorderLayout.SOUTH);
		restart.setBackground(new Color(216,196,152));
		redo.setBackground(new Color(216,196,152));
		lose.setBackground(new Color(216,196,152));
		peace.setBackground(new Color(215,196,152));
		panel2.setBackground(new Color(216,196,152));
		panel.setBackground(new Color(216,196,152));
		
		restart.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				panel.restart();
			}
		});
		
		redo.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				panel.Redo();
			}
		});
		
		lose.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				panel.lose();
			}
		});
		
		peace.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				panel.peace();
			}
		});
	}
	
	@Override
	protected void processWindowEvent(WindowEvent e) {
		// TODO Auto-generated method stub
		if(e.getID()==WindowEvent.WINDOW_CLOSING){
			panel.leave();
		}
		super.processWindowEvent(e);
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