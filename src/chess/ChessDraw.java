package chess;

import java.applet.AudioClip;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Scanner;

import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.Timer;
import javax.swing.filechooser.FileFilter;


import javazoom.jl.decoder.JavaLayerException;
import javazoom.jl.player.Player;

/**
 * 
 * <p>Title: ChessDraw.java</p>
 * <p>Description:
 *	首先画出一个棋盘，对应数据结构为车1,马2,象3,士4,将5,炮6,兵7,空格为0。对方在此基础上加7
 * <p>@author dedong
 * <p>@date 2017/ 下午2:15:02
 *
 */
public class ChessDraw extends JFrame{

	/** serialVersionUID*/
	private static final long serialVersionUID = 1L;
	private JButton advanced=new JButton("切换对弈水平");
	private JButton twoplayers=new JButton("切换对弈模式");
	private JButton internet=new JButton("播放录像");
	private JButton cancel=new JButton("悔棋");
	private JButton music=new JButton("播放背景音乐");
	private int maxDepth=2;
	private int ainum=1;//默认人机对弈
	private List<String> history=new ArrayList<String>();
	private List<int[][]> back=new ArrayList<>();
	private String[] name={"","车","马","象","士","将","炮","卒"};
	private String[] name2={"","车","马","相","士","帅","炮","兵"};
	private String[] numname={"","一","二","三","四","五","六","七","八","九","十"};
	private Image[] pics =new Image[15];
	private Timer timer;
	private int num;
	private int[][] raw;
	private AudioClip clip;
	private Thread thread;
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
		// TODO Auto-generated constructor stub
		raw=BackData(data);
		final Board panel=new Board();
		add(panel,BorderLayout.CENTER);
		JPanel panel2=new JPanel();
		panel2.add(advanced);
		panel2.add(twoplayers);
		panel2.add(internet);
		panel2.add(cancel);
		panel2.add(music);
		add(panel2,BorderLayout.SOUTH);
		advanced.setBackground(new Color(216,196,152));
		twoplayers.setBackground(new Color(216,196,152));
		internet.setBackground(new Color(216,196,152));
		cancel.setBackground(new Color(216,196,152));
		music.setBackground(new Color(216,196,152));
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
		back.add(BackData(data));
		
		advanced.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				if(ainum!=1)
					return;
				history.clear();
				for(int i=0;i<back.size()-1;i++)
					back.remove(back.size()-1);
				panel.tip=false;
				panel.isSelected=false;
				panel.yourTurn=true;
				maxDepth=maxDepth==2?4:2;
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
				if(maxDepth==4)
					JOptionPane.showMessageDialog(null, "当前中级水平");
				else
					JOptionPane.showMessageDialog(null, "当前初级水平");
			}
		});
		
		twoplayers.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				history.clear();
				for(int i=0;i<back.size()-1;i++)
					back.remove(back.size()-1);
				ainum=(ainum+1)%2;
				panel.tip=false;
				panel.isSelected=false;
				panel.yourTurn=true;
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
				if(ainum==0)
					JOptionPane.showMessageDialog(null, "两人对弈模式");
				else if(ainum==1)
					JOptionPane.showMessageDialog(null, "人机对弈模式");
			}
		});
		
		cancel.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				panel.tip=false;
				panel.isSelected=false;
				if(ainum==0){
					//两人对弈,悔一步
					if(back.size()<=1){
						JOptionPane.showMessageDialog(null, "初始局面,无法撤销");
						return;
					}
					back.remove(back.size()-1);
					history.remove(history.size()-1);
					data=back.get(back.size()-1);
					panel.yourTurn=!panel.yourTurn;
					repaint();
				}else if(ainum==1){
					//悔两步
					if(back.size()<=2){
						JOptionPane.showMessageDialog(null, "初始局面,无法撤销");
						return;
					}
					back.remove(back.size()-1);
					back.remove(back.size()-1);
					history.remove(history.size()-1);
					history.remove(history.size()-1);
					data=back.get(back.size()-1);
					repaint();
				}
			}
		});
		
		internet.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				panel.tip=false;
				panel.isSelected=false;
				panel.yourTurn=true;
				history.clear();
				if(num>0){
					//准备暂停
					timer.stop();
					num=0;
					data=BackData(raw);
					repaint();
					return;
				}
				final List<int[][]> dataReturn=open();
				timer=new Timer(1000, new ActionListener() {
					
					@Override
					public void actionPerformed(ActionEvent e) {
						// TODO Auto-generated method stub
						if(num>=dataReturn.size()){
							return;
						}
						data=dataReturn.get(num);
						num=num+1;
						repaint();
					}
				});
				timer.start();
			}
		});
		
		music.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				if(thread!=null && thread.isAlive()){
					thread.stop();
					return;
				}
				File dir=new File("music");
				File[] files=dir.listFiles();
				thread=new AudioPlayer(files);
				thread.start();
			}
		});
	}
	
	private List<int[][]> open() {
		List<int[][]> datas=new ArrayList<>();
		JFileChooser jf=new JFileChooser(new File("chessFile"));
		jf.setFileFilter(new ChooseFile());
		if(jf.showOpenDialog(this)==JFileChooser.APPROVE_OPTION){
			File file2=jf.getSelectedFile();
			try {
				Scanner in=new Scanner(file2);
				int num=0;
				int[][] tmp = new int[10][9];
				while(in.hasNextLine()){
					if(num%10==0 && num!=0){
						datas.add(tmp);
						num%=10;
						tmp=new int[10][9];
					}
					String line=in.nextLine();
					String[] parts=line.split(" ");
					for(int i=0;i<parts.length;i++){
						tmp[num][i]=Integer.parseInt(parts[i]);
					}
					num++;
				}
				in.close();
			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return datas;
	}
	
	public static int[][] BackData(int[][] data) {
		int[][] sub=new int[data.length][data[0].length];
		for(int i=0;i<data.length;i++){
			for(int j=0;j<data[0].length;j++){
				sub[i][j]=data[i][j];//深度复制
			}
		}
		return sub;
	}
	
	private class ChooseFile extends FileFilter{

		@Override
		public boolean accept(File f) {
			// TODO Auto-generated method stub
			return f.getName().endsWith(".chess");
		}

		@Override
		public String getDescription() {
			// TODO Auto-generated method stub
			return "*.chess";
		}
		
	}
	
	/*
	 * 画出一个棋盘
	 */
	private class Board extends JPanel implements MouseListener{
		/** serialVersionUID*/
		private static final long serialVersionUID = 1L;
		private int col;
		private int row;
		private boolean tip;
		private boolean isSelected;
		private int precol;
		private int prerow;
		private boolean preIsRed;
		private boolean yourTurn=true;
		public Board() {
			// TODO Auto-generated constructor stub
			addMouseListener(this);
		}
		private void moreDraw(int row,int col,int width,int height,Graphics g) {
			//1/2 2/3 1/3
			int baseX=width*col/11;
			int baseY=height*row/12;
			int wlen=width/110;
			int hlen=height/120;
			int w4len=width/44;
			int h4len=height/48;
			
			if(col!=9){
				g.drawLine(baseX+wlen, baseY-hlen, baseX+wlen, baseY-h4len);
				g.drawLine(baseX+wlen, baseY-hlen, baseX+w4len, baseY-hlen);
			
				g.drawLine(baseX+wlen, baseY+hlen, baseX+w4len, baseY+hlen);
				g.drawLine(baseX+wlen, baseY+hlen,baseX+wlen,baseY+h4len);
			}
			
			if(col!=1){
				g.drawLine(baseX-wlen, baseY-hlen, baseX-wlen,baseY-h4len);
				g.drawLine(baseX-wlen, baseY-hlen, baseX-w4len, baseY-hlen);
			
				g.drawLine(baseX-wlen, baseY+hlen, baseX-w4len, baseY+hlen);
				g.drawLine(baseX-wlen, baseY+hlen, baseX-wlen, baseY+h4len);
			}
		}
		@Override
		protected void paintComponent(Graphics g) {
			// TODO Auto-generated method stub
			super.paintComponent(g);
			int height=getHeight();
			int width=getWidth();
			//先画数线9个，横线10
			g.drawLine(width/11, height/12, width/11, height*10/12);
			g.drawLine(width*9/11, height/12, width*9/11, height*10/12);
			for(int i=0;i<7;i++){
				g.drawLine(width*(i+2)/11, height/12, width*(i+2)/11, height*5/12);
				g.drawLine(width*(i+2)/11, height/2, width*(i+2)/11, height*10/12);
			}
			for(int i=0;i<10;i++){
				g.drawLine(width/11, height*(i+1)/12, width*9/11, height*(i+1)/12);
			}
			
			//画楚河汉界
			g.setFont(new Font("华文行楷", Font.BOLD, 30));
			g.drawString("楚        河", 200, 330);
			g.drawString("汉        界", 900, 330 );
			
			//画士的线
			g.drawLine(width*4/11, height/12, width*6/11, height/4);
			g.drawLine(width*6/11, height/12, width*4/11, height/4);
			g.drawLine(width*4/11, height*10/12, width*6/11, height*8/12);
			g.drawLine(width*6/11, height*10/12, width*4/11, height*8/12);
			
			moreDraw(3, 2, width, height, g);
			moreDraw(3, 8, width, height, g);
			moreDraw(8, 2, width, height, g);
			moreDraw(8, 8, width, height, g);//炮
			
			moreDraw(4, 1, width, height, g);
			moreDraw(4, 3, width, height, g);
			moreDraw(4, 5, width, height, g);
			moreDraw(4, 7, width, height, g);
			moreDraw(4, 9, width, height, g);
			moreDraw(7, 1, width, height, g);
			moreDraw(7, 3, width, height, g);
			moreDraw(7, 5, width, height, g);
			moreDraw(7, 7, width, height, g);
			moreDraw(7, 9, width, height, g);//兵
			
			//画图像
			for(int i=1;i<=10;i++){
				for(int j=1;j<=9;j++){
					int baseX=width*j/11;
					int baseY=height*i/12;
					g.drawImage(pics[data[i-1][j-1]], baseX-width/22, baseY-height/24, width/11, height/12, this);
				}
			}
			
			//画一个提示
			if(tip){
				g.setColor(Color.green);
				g.drawLine(col*width/11-width/22, row*height/12-height/24, col*width/11-width/44, row*height/12-height/24);
				g.drawLine(col*width/11-width/22, row*height/12-height/24, col*width/11-width/22, row*height/12-height/48);
				g.drawLine(col*width/11+width/22, row*height/12-height/24, col*width/11+width/44, row*height/12-height/24);
				g.drawLine(col*width/11+width/22, row*height/12-height/24, col*width/11+width/22, row*height/12-height/48);
				g.drawLine(col*width/11+width/22, row*height/12+height/24, col*width/11+width/44, row*height/12+height/24);
				g.drawLine(col*width/11+width/22, row*height/12+height/24, col*width/11+width/22, row*height/12+height/48);
				g.drawLine(col*width/11-width/22, row*height/12+height/24, col*width/11-width/44, row*height/12+height/24);
				g.drawLine(col*width/11-width/22, row*height/12+height/24, col*width/11-width/22, row*height/12+height/48);
				g.setColor(Color.black);
			}
			
			
			//画记录
			g.setFont(new Font("隶书", Font.BOLD, 28));
			if(history.size()>0 && history.size()<=20){
				for(int i=0;i<history.size();i++){
					if(i%2==0){
						//红方
						g.setColor(Color.red);
						g.drawString(history.get(i), 1180, 100+30*i);
					}else{
						g.setColor(Color.blue);
						g.drawString(history.get(i), 1230, 100+30*i);
					}
				}
			}else if(history.size()>20){
				for(int i=history.size()-20;i<history.size();i++){
					if(i%2==0){
						//红方
						g.setColor(Color.red);
						g.drawString(history.get(i), 1180, 60+30*(i+20-history.size()));
					}else{
						g.setColor(Color.blue);
						g.drawString(history.get(i), 1230, 60+30*(i+20-history.size()));
					}
				}
			}
		}
		
		@Override
		public void mouseClicked(MouseEvent e) {
			// TODO Auto-generated method stub
			if(!yourTurn && ainum==1)
				return;
			
			//确定位置
			int width=getWidth();
			int height=getHeight();
			int x=e.getX();
			int y=e.getY();
			//判断x的col
			for(int i=1;i<=9;i++){
				if(Math.abs(width*i/11-x)<width/22){
					col=i;
					break;
				}
			}
			//判断y的row
			for(int i=1;i<=10;i++){
				if(Math.abs(height*i/12-y)<height/24){
					row=i;
					break;
				}
			}
			if(isSelected){
				//要想去这里
				//只要判断能不能到即可
				int label=data[prerow-1][precol-1];
				int rawlabel=data[row-1][col-1];
				switch (label) {
				case 1:
				case 8:
					//是车
					if((preIsRed && data[row-1][col-1]<8 && data[row-1][col-1]>0) || (!preIsRed && data[row-1][col-1]>=8)){
						isSelected=true;
						precol=col;
						prerow=row;
						preIsRed=data[prerow-1][precol-1]<8?true:false;
						repaint();
						return;//同颜色
					}
					if(row<prerow && col==precol){
						//想往上走
						for(int i=row+1;i<prerow;i++){
							if(data[i-1][col-1]!=0)
								return;//有子挡了
						}
					}else if(col<precol && row==prerow){
						//想往左走
						for(int i=col+1;i<precol;i++)
							if(data[row-1][i-1]!=0)
								return;
					}else if(row>prerow && col==precol){
						//想往下走
						for(int i=prerow+1;i<row;i++)
							if(data[i-1][col-1]!=0)
								return;
					}else if(col>precol && row==prerow){
						//想往右走
						for(int i=precol+1;i<col;i++)
							if(data[row-1][i-1]!=0)
								return;
					}else {
						if(data[row-1][col-1]==0)
							return;
						else if((data[row-1][col-1]<8 && preIsRed) || (data[row-1][col-1]>=8 && preIsRed)){
							//换子选择
							isSelected=true;
							precol=col;
							prerow=row;
							preIsRed=data[prerow-1][precol-1]<8?true:false;
							repaint();
						}
						return;
					}
					//下面是正常情况
					data[row-1][col-1]=data[prerow-1][precol-1];
					data[prerow-1][precol-1]=0;
					repaint();
					break;
				case 2:
				case 9:
					//是马
					if((preIsRed && data[row-1][col-1]<8 && data[row-1][col-1]>0) || (!preIsRed && data[row-1][col-1]>=8)){
						isSelected=true;
						precol=col;
						prerow=row;
						preIsRed=data[prerow-1][precol-1]<8?true:false;
						repaint();
						return;
					}
					if(col-precol==2 && prerow-row==1){
						//躺日东北方向
						if(data[prerow-1][precol]!=0)
							return;
					}else if(col-precol==2 && row-prerow==1){
						//躺日东南方向
						if(data[prerow-1][precol]!=0)
							return;
					}else if(precol-col==2 && row-prerow==1){
						//躺日西南方向
						if(data[prerow-1][precol-2]!=0)
							return;
					}else if(precol-col==2 && prerow-row==1){
						//躺日西北方向
						if(data[prerow-1][precol-2]!=0)
							return;
					}else if(col-precol==1 && prerow-row==2){
						//跳日东北
						if(data[prerow-2][precol-1]!=0)
							return;
					}else if(col-precol==1 && row-prerow==2){
						//跳日东南
						if(data[prerow][precol-1]!=0)
							return;
					}else if(precol-col==1 && row-prerow==2){
						if(data[prerow][precol-1]!=0)
							return;
					}else if(precol-col==1 && prerow-row==2){
						if(data[prerow-2][precol-1]!=0)
							return;
					}else {
						if(data[row-1][col-1]==0)
							return;
						else if((data[row-1][col-1]<8 && preIsRed) || (data[row-1][col-1]>=8 && preIsRed)){
							//换子选择
							isSelected=true;
							precol=col;
							prerow=row;
							preIsRed=data[prerow-1][precol-1]<8?true:false;
							repaint();
						}
						return;
					}
					data[row-1][col-1]=data[prerow-1][precol-1];
					data[prerow-1][precol-1]=0;
					repaint();
					break;
				case 3:
				case 10:
					//是相
					if((preIsRed && data[row-1][col-1]<8 && data[row-1][col-1]>0) || (!preIsRed && data[row-1][col-1]>=8)){
						isSelected=true;
						precol=col;
						prerow=row;
						preIsRed=data[prerow-1][precol-1]<8?true:false;
						repaint();
						return;
					}
					if(preIsRed){
						if(row<=5)
							return;//不能出去
					}else{
						if(row>5)
							return;
					}
					if(col-precol==2 && prerow-row==2){
						//往东北方向
						if(data[prerow-2][precol]!=0)
							return;
					}else if(col-precol==2 && row-prerow==2){
						//往东南方向
						if(data[prerow][precol]!=0)
							return;
					}else if(precol-col==2 && row-prerow==2){
						//往西南方向
						if(data[prerow][precol-2]!=0)
							return;
					}else if(precol-col==2 && prerow-row==2){
						//往西北方向
						if(data[prerow-2][precol-2]!=0)
							return;
					}else {
						if(data[row-1][col-1]==0)
							return;
						else if((data[row-1][col-1]<8 && preIsRed) || (data[row-1][col-1]>=8 && preIsRed)){
							//换子选择
							isSelected=true;
							precol=col;
							prerow=row;
							preIsRed=data[prerow-1][precol-1]<8?true:false;
							repaint();
						}
						return;
					}
					data[row-1][col-1]=data[prerow-1][precol-1];
					data[prerow-1][precol-1]=0;
					repaint();
					break;
				case 4:
				case 11:
					//士
					if((preIsRed && data[row-1][col-1]<8 && data[row-1][col-1]>0) || (!preIsRed && data[row-1][col-1]>=8)){
						isSelected=true;
						precol=col;
						prerow=row;
						preIsRed=data[prerow-1][precol-1]<8?true:false;
						repaint();
						return;
					}
					if(col>6 || col<4)
						return;
					if(preIsRed){
						if(row<8)
							return;
					}else{
						if(row>3)
							return;
					}
					if(col-precol==1 && row-prerow==1){
						//往东南方向
					}else if(col-precol==1 && prerow-row==1){
						//往东北方向
					}else if(precol-col==1 && row-prerow==1){
						//往西南方向
					}else if(precol-col==1 && prerow-row==1){
						//往西北方向
					}else {
						if(data[row-1][col-1]==0)
							return;
						else if((data[row-1][col-1]<8 && preIsRed) || (data[row-1][col-1]>=8 && preIsRed)){
							//换子选择
							isSelected=true;
							precol=col;
							prerow=row;
							preIsRed=data[prerow-1][precol-1]<8?true:false;
							repaint();
						}
						return;
					}
					data[row-1][col-1]=data[prerow-1][precol-1];
					data[prerow-1][precol-1]=0;
					repaint();
					break;
				case 5:
				case 12:
					//是将
					if((preIsRed && data[row-1][col-1]<8 && data[row-1][col-1]>0) || (!preIsRed && data[row-1][col-1]>=8)){
						isSelected=true;
						precol=col;
						prerow=row;
						preIsRed=data[prerow-1][precol-1]<8?true:false;
						repaint();
						return;
					}
					if(col>6 || col<4)
						return;
					if(preIsRed){
						if(row<8)
							return;
					}else{
						if(row>3)
							return;
					}
					if(col-precol==1 && row==prerow){
						//右边
					}
					else if(col==precol && prerow-row==1){
						//上
					}
					else if(precol-col==1 && prerow==row){
						//左
					}else if(precol==col && row-prerow==1){
						//下
					}else {
						if(data[row-1][col-1]==0)
							return;
						else if((data[row-1][col-1]<8 && preIsRed) || (data[row-1][col-1]>=8 && preIsRed)){
							//换子选择
							isSelected=true;
							precol=col;
							prerow=row;
							preIsRed=data[prerow-1][precol-1]<8?true:false;
							repaint();
						}
						return;
					}
					data[row-1][col-1]=data[prerow-1][precol-1];
					data[prerow-1][precol-1]=0;
					repaint();
					break;
				case 6:
				case 13:
					//是炮
					if(data[row-1][col-1]==0){
						//如果是空格，像车一样
						if(row<prerow && col==precol){
							//想往上走
							for(int i=row+1;i<prerow;i++){
								if(data[i-1][col-1]!=0)
									return;//有子挡了
							}
						}else if(col<precol && row==prerow){
							//想往左走
							for(int i=col+1;i<precol;i++)
								if(data[row-1][i-1]!=0)
									return;
						}else if(row>prerow && col==precol){
							//想往下走
							for(int i=prerow+1;i<row;i++)
								if(data[i-1][col-1]!=0)
									return;
						}else if(col>precol && row==prerow){
							//想往右走
							for(int i=precol+1;i<col;i++)
								if(data[row-1][i-1]!=0)
									return;
						}else {
							return;
						}
					}else if((preIsRed && data[row-1][col-1]<8) || (!preIsRed && data[row-1][col-1]>=8)){
							isSelected=true;
							precol=col;
							prerow=row;
							preIsRed=data[prerow-1][precol-1]<8?true:false;
							repaint();
							return;
					}
					else{
						//是对方的子
						//必须恰好隔一个
						int num=0;
						if(row<prerow && col==precol){
							//上
							for(int i=row+1;i<prerow;i++){
								if(data[i-1][col-1]!=0)
									num++;
							}
						}else if(row>prerow && col==precol){
							//下
							for(int i=prerow+1;i<row;i++)
								if(data[i-1][col-1]!=0)
									num++;
						}else if (row==prerow && col>precol) {
							//右
							for(int i=precol+1;i<col;i++)
								if(data[row-1][i-1]!=0)
									num++;
						}else if(row==prerow && col<precol){
							//左
							for(int i=col+1;i<precol;i++)
								if(data[row-1][i-1]!=0)
									num++;
						}
						if(num!=1)
							return;
					}
					//现在可以了
					data[row-1][col-1]=data[prerow-1][precol-1];
					data[prerow-1][precol-1]=0;
					repaint();
					break;
				case 7:
				case 14:
					//是兵
					if((preIsRed && data[row-1][col-1]<8 && data[row-1][col-1]>0) || (!preIsRed && data[row-1][col-1]>=8)){
						isSelected=true;
						precol=col;
						prerow=row;
						preIsRed=data[prerow-1][precol-1]<8?true:false;
						repaint();
						return;
					}
					if(preIsRed){
						if(prerow>5){
							//在自己的阵地上 不能左右移动
							if(prerow-row==1 && precol==col){
								//前进
							}else if(data[row-1][col-1]<8){
								isSelected=true;
								precol=col;
								prerow=row;
								preIsRed=data[prerow-1][precol-1]<8?true:false;
								repaint();
								return;
							}else{
								return;
							}
						}else{
							//在对方阵地上
							if(prerow-row==1 && precol==col){
								//进
							}else if(prerow==row && precol-col==1){
								//左
							}else if(prerow==row && col-precol==1){
								//右
							}else {
								isSelected=true;
								precol=col;
								prerow=row;
								preIsRed=data[prerow-1][precol-1]<8?true:false;
								repaint();
								return;
							}
						}
					}else{
						if(prerow<=5){
							//在自己的阵地上 不能左右移动
							if(prerow-row==-1 && precol==col){
								//前进
							}else if(data[row-1][col-1]>=8){
								isSelected=true;
								precol=col;
								prerow=row;
								preIsRed=data[prerow-1][precol-1]<8?true:false;
								repaint();
								return;
							}else {
								return;
							}
						}else{
							//在对方阵地上
							if(prerow-row==-1 && precol==col){
								//进
							}else if(prerow==row && precol-col==1){
								//左
							}else if(prerow==row && col-precol==1){
								//右
							}else {
								isSelected=true;
								precol=col;
								prerow=row;
								preIsRed=data[prerow-1][precol-1]<8?true:false;
								repaint();
								return;
							}
						}
					}
					data[row-1][col-1]=data[prerow-1][precol-1];
					data[prerow-1][precol-1]=0;
					repaint();
					break;
				default:
					break;
				}
				isSelected=false;
				if(ainum==1){
					yourTurn=false;
					String tmp="";
					tmp+=name2[label];
						switch (label) {
						case 1:
						case 6:
						case 7:
						case 5:
							//车炮
							if(prerow==row){
								//平
								tmp+=numname[10-precol]+"平"+numname[10-col];
							}else if(precol==col){
								if(prerow>row){
									tmp+=numname[11-prerow]+"进"+numname[11-row];
								}else{
									tmp+=numname[11-prerow]+"退"+numname[11-row];
								}
							}
							break;
						default:
							if(prerow>row){
								tmp+=numname[10-precol]+"进"+numname[10-col];
							}else{
								tmp+=numname[10-precol]+"退"+numname[10-col];
							}
							break;
						}
						//红方描述
					history.add(tmp);
					back.add(BackData(data));
					data=new Chess(data).compute(maxDepth);
					if(data==null){
						data=back.get(back.size()-1);
						try {
							PrintWriter out=new PrintWriter("chessFile/"+new Date().toLocaleString().replace(" ","").replace(":", "")+".chess");
							for(int i=0;i<back.size();i++){
								for(int j=0;j<back.get(i).length;j++){
									for(int k=0;k<back.get(i)[j].length;k++){
										out.print(back.get(i)[j][k]+" ");
									}
									out.println();
								}
							}
							out.close();//保存
						} catch (FileNotFoundException e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						}
						JOptionPane.showMessageDialog(null, "congratulate,you win.");
						return;
					}else{
						boolean isShuai=false;
						for(int i=7;i<=9;i++)
							for(int j=3;j<=5;j++)
								if(data[i][j]==5){
									isShuai=true;
								}
						if(!isShuai){
							JOptionPane.showMessageDialog(null, "sorry,you lose.");
							try {
								PrintWriter out=new PrintWriter("chessFile/"+new Date().toLocaleString().replace(" ", "").replace(":", "")+".chess");
								back.add(BackData(data));
								for(int i=0;i<back.size();i++){
									for(int j=0;j<back.get(i).length;j++){
										for(int k=0;k<back.get(i)[j].length;k++){
											out.print(back.get(i)[j][k]+" ");
										}
										out.println();
									}
								}
								out.close();//保存
							} catch (FileNotFoundException e1) {
								// TODO Auto-generated catch block
								e1.printStackTrace();
							}
							back.remove(back.size()-1);
							return;
						}
					}
					history.add(findName(back.get(back.size()-1), data));
					back.add(BackData(data));
					tip=false;
					repaint();
					yourTurn=true;
				}else if(ainum==0){
					if(yourTurn){
						//红方
						String tmp="";
						tmp+=name2[label];
						if(rawlabel==0 || rawlabel>=8){
							yourTurn=!yourTurn;
							switch (label) {
							case 1:
							case 6:
							case 7:
							case 5:
								//车炮
								if(prerow==row){
									//平
									tmp+=numname[10-precol]+"平"+numname[10-col];
								}else if(precol==col){
									if(prerow>row){
										tmp+=numname[11-prerow]+"进"+numname[11-row];
									}else{
										tmp+=numname[11-prerow]+"退"+numname[11-row];
									}
								}
								break;
							default:
								if(prerow>row){
									tmp+=numname[10-precol]+"进"+numname[10-col];
								}else{
									tmp+=numname[10-precol]+"退"+numname[10-col];
								}
								break;
							}
							history.add(tmp);
							back.add(BackData(data));
						}
					}else{
						//黑方
						String tmp="";
						tmp+=name[label-7];
						if(rawlabel<=7){
							yourTurn=!yourTurn;
							switch (label) {
							case 8:
							case 13:
							case 14:
							case 12:
								//车炮
								if(prerow==row){
									//平
									tmp+=numname[precol]+"平"+numname[col];
								}else if(precol==col){
									if(prerow<row){
										tmp+=numname[prerow]+"进"+numname[row];
									}else{
										tmp+=numname[prerow]+"退"+numname[row];
									}
								}
								break;
							default:
								if(prerow<row){
									tmp+=numname[precol]+"进"+numname[col];
								}else{
									tmp+=numname[precol]+"退"+numname[col];
								}
								break;
							}
							history.add(tmp);
							back.add(BackData(data));
						}
					}
				}
				//
				return;
			}
			precol=col;
			prerow=row;
			if(precol==0 || prerow==0)
				return;
			preIsRed=data[prerow-1][precol-1]<8?true:false;
			if(ainum==1){//只能执红
				if(data[prerow-1][precol-1]==0 || data[prerow-1][precol-1]>=8)
					return;
			}else if(ainum==0){//两人对弈
				if(data[prerow-1][precol-1]==0){
					return;
				}else if(!yourTurn && data[prerow-1][precol-1]<=7){
					return;
				}else if(yourTurn && data[prerow-1][precol-1]>=8){
					return;
				}
			}
			tip=true;
			isSelected=true;
			repaint();//解决了选中问题
		}

		@Override
		public void mousePressed(MouseEvent e) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void mouseReleased(MouseEvent e) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void mouseEntered(MouseEvent e) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void mouseExited(MouseEvent e) {
			// TODO Auto-generated method stub
			
		}
		
	}
	//pao存在问题
	
	public String findName(int[][] data,int[][] sub) {
		int prerow=-1,precol=-1,row=-1,col=-1,label=-1;
		for(int i=0;i<10;i++){
			for(int j=0;j<9;j++){
				if(sub[i][j]==0 && data[i][j]>=8){
					prerow=i+1;
					precol=j+1;
					label=data[prerow-1][precol-1];
				}else if(data[i][j]<=7 && sub[i][j]>=8){
					row=i+1;
					col=j+1;
				}
			}
		}
		String tmp="";
		tmp+=name[label-7];
			switch (label) {
			case 8:
			case 13:
			case 14:
			case 12:
				//车炮
				if(prerow==row){
					//平
					tmp+=numname[precol]+"平"+numname[col];
				}else if(precol==col){
					if(prerow<row){
						tmp+=numname[prerow]+"进"+numname[row];
					}else{
						tmp+=numname[prerow]+"退"+numname[row];
					}
				}
				break;
			default:
				if(prerow<row){
					tmp+=numname[precol]+"进"+numname[col];
				}else{
					tmp+=numname[precol]+"退"+numname[col];
				}
				break;
			}
			return tmp;
	}
	//存在循环走步，注意看最后一个文件
	//而且效益到后期感觉不明显
	//多次将军问题，以及不吃帅的情况
	
	private class AudioPlayer extends Thread{
		Player player;
		File[] musicFile;
		int time=0;
		
		public AudioPlayer(File[] file) {
			// TODO Auto-generated constructor stub
			musicFile=file;
		}
		
		@Override
		public void run() {
			// TODO Auto-generated method stub
			super.run();
			try {
				while(true){
					play();
					time=(time+1)%musicFile.length;
				}
			} catch (Exception e) {
				// TODO: handle exception
				e.printStackTrace();
			}
		}
		
		public void play() throws FileNotFoundException,JavaLayerException{
			BufferedInputStream inputStream=new BufferedInputStream(
					new FileInputStream(musicFile[time]));
			player=new Player(inputStream);
			player.play();
		}
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
//需要完善
//1.加入对评价不大的则随机走棋
//2.加入列表来播放mp3音乐
//3.加入让子功能
//4.加入谁先走的功能
//5.做出两AI对弈，使得算法更完善
//6.解决3步不能连将情况
//7.加入将军奖励机制、配合策略、战术策略等等
//8.解决录像最后一步不显示的问题
//9.解决莫名其妙的步法