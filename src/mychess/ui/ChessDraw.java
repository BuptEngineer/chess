package mychess.ui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import mychess.function.Redo;
import mychess.util.BackData;


//该类实现象棋的图形界面,并负责通信
public class ChessDraw extends JFrame{

	private static final long serialVersionUID = 1L;
	private JButton twoplayers=new JButton("切换对弈模式");
	private JButton internet=new JButton("播放录像");
	private JButton cancel=new JButton("悔棋");
	private Redo rd=new Redo();
	private List<int[][]> back=new ArrayList<>();
	private Image[] pics =new Image[15];
	
	private int[][] data={{8,9,10,11,12,11,10,9,8},
			{0,0,0,0,0,0,0,0,0},
			{0,13,0,0,0,0,0,13,0},
			{14,0,14,0,14,0,14,0,14},
			{0,0,0,0,0,0,0,0,0},
			{0,0,0,0,0,0,0,0,0},
			{7,0,7,0,7,0,7,0,7},
			{0,6,0,0,0,0,0,6,0},
			{0,0,0,0,0,0,0,0,0},
			{1,2,3,4,5,4,3,2,1}
	};

	public ChessDraw() {
		BackData.Backup(data);
		ChessBoard panel=new ChessBoard(data,pics,rd);
		add(panel,BorderLayout.CENTER);
		JPanel panel2=new JPanel();
		panel2.add(twoplayers);
		panel2.add(cancel);
		panel2.add(internet);
		add(panel2,BorderLayout.SOUTH);
		twoplayers.setBackground(new Color(216,196,152));
		internet.setBackground(new Color(216,196,152));
		cancel.setBackground(new Color(216,196,152));
		panel2.setBackground(new Color(216,196,152));
		panel.setBackground(new Color(216,196,152));
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
		back.add(BackData.Backup(data));
		
		twoplayers.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				for(int i=0;i<back.size()-1;i++)
					back.remove(back.size()-1);
				//TODO
				data=new int[][]{{8,9,10,11,12,11,10,9,8},
						{0,0,0,0,0,0,0,0,0},
						{0,13,0,0,0,0,0,13,0},
						{14,0,14,0,14,0,14,0,14},
						{0,0,0,0,0,0,0,0,0},
						{0,0,0,0,0,0,0,0,0},
						{7,0,7,0,7,0,7,0,7},
						{0,6,0,0,0,0,0,6,0},
						{0,0,0,0,0,0,0,0,0},
						{1,2,3,4,5,4,3,2,1}
				};
				repaint();
			}
		});
		
		cancel.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				//两人对弈,悔一步
				if(rd.get_all_step()<1){
					JOptionPane.showMessageDialog(null, "初始局面,无法撤销");
					return;
				}
			}
		});
		
		internet.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				//
			}
		});
	}
	
	public static void main(String[] args) {
		JFrame frame=new ChessDraw();
		frame.setTitle("象棋");
		frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setLocationRelativeTo(null);
		frame.setVisible(true);
	}
}
