package mychess.ui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.Timer;

import mychess.function.Redo;
import mychess.function.Replay;


//该类实现象棋的图形界面,并负责通信
public class ChessDraw extends JFrame{

	private static final long serialVersionUID = 1L;
	private JButton restart=new JButton("重新开始");
	private JButton video=new JButton("播放录像");
	private JButton cancel=new JButton("悔棋");
	private Redo rd=new Redo(); //依赖悔棋的类
	private Image[] pics =new Image[15];//加载象棋图片
	private Timer timer;//动画
	private int num=0;//播放起始位置

	public ChessDraw() {
		ChessBoard panel=new ChessBoard(pics,rd);
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
		
		cancel.addActionListener(new ActionListener() {//悔棋功能
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				//两人对弈,悔一步
				if(rd.get_all_step()<1){
					JOptionPane.showMessageDialog(null, "初始局面,无法撤销");
					return;
				}
				if(panel.isYourTurn()){
					JOptionPane.showMessageDialog(null, "到你走棋了，无法撤销");
					return;
				}else{
					panel.getCc().PostMessage("Redo");
				}
			}
		});

		video.addActionListener(new ActionListener() {//录像功能
			
			@Override
			public void actionPerformed(ActionEvent e) {
				//
				String filepath=Replay.Open_Get_Filepath();
				List<int[][]> list=Replay.ReadFile(filepath);
				//播放动画
				timer=new Timer(1000, new ActionListener() {
					
					@Override
					public void actionPerformed(ActionEvent e) {
						// TODO Auto-generated method stub
						System.out.println(list.size());
						int[][] datasub=list.get(num++);
						panel.setData(datasub);
						repaint();
						if(num >= list.size()){ 
							timer.stop();
							JOptionPane.showMessageDialog(null, "Over");
						}
					}
				});
				timer.start();
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
