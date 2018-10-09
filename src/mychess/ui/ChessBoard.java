package mychess.ui;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.swing.JOptionPane;
import javax.swing.JPanel;

import mychess.function.Commication;
import mychess.function.Internet;
import mychess.function.Redo;
import mychess.function.Replay;
import mychess.util.Common;
import mychess.util.HasFinished;
import mychess.util.JudgeMove;


public class ChessBoard extends JPanel implements MouseListener,Runnable{
	/** serialVersionUID*/
	private static final long serialVersionUID = 1L;
	private int col;
	private int row;
	private boolean tip;
	private boolean isSelected;
	private int precol;
	private int prerow;
	private boolean yourTurn;
	private int[][] data;
	private Image[] pics;
	private Redo rd;
	private Internet internet;
	private boolean observer;
	private boolean isRed;//是否是红方
	private boolean isRedo;//是否悔棋
	private Commication cc;//消息服务
	private byte state=0;//状态0表示空闲, 1表示准备,2表示游戏开始,3表示游戏进行中,4表示游戏结束
	
	public ChessBoard(Image[] pics,Redo rd) {
		// TODO Auto-generated constructor stub
		this.pics=pics;
		this.rd=rd;
		internet=new Internet();
		//判断角色
		String message=internet.readMessage();
		String message_array=internet.readMessage();
		data=Common.String_to_Array(message_array);
		rd.add_one_step(data);
		if(message.endsWith("true")){
			isRed=true;
			state=1;
			yourTurn=true;
		}
		else if(message.endsWith("false")) state=2;
		else observer=true;
		
		addMouseListener(this);
		Thread t=new Thread(this);//数据服务
		t.start();
		cc=new Commication(this);
		Thread t2=new Thread(cc);//消息服务
		t2.start();
	}
	
	private void drawLines(int row,int col,int width,int height,Graphics g) {
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
		
		drawLines(3, 2, width, height, g);
		drawLines(3, 8, width, height, g);
		drawLines(8, 2, width, height, g);
		drawLines(8, 8, width, height, g);//炮
		
		drawLines(4, 1, width, height, g);
		drawLines(4, 3, width, height, g);
		drawLines(4, 5, width, height, g);
		drawLines(4, 7, width, height, g);
		drawLines(4, 9, width, height, g);
		drawLines(7, 1, width, height, g);
		drawLines(7, 3, width, height, g);
		drawLines(7, 5, width, height, g);
		drawLines(7, 7, width, height, g);
		drawLines(7, 9, width, height, g);//兵
		
		//画图像
		for(int i=1;i<=10;i++){
			for(int j=1;j<=9;j++){
				if(data[i-1][j-1]==0) continue;
				int baseX=width*j/11;
				int baseY;
				if(!isRed)
					baseY=height*(11-i)/12;
				else
					baseY=height*i/12;
				g.drawImage(pics[data[i-1][j-1]], baseX-width/22, baseY-height/24, width/11, height/12, this);
			}
		}
		
		if(yourTurn && !isRedo && state==3){
			//画对方的提示
			g.setColor(Color.GREEN);
			g.drawLine(precol*width/11-width/22, prerow*height/12-height/24, precol*width/11-width/44, prerow*height/12-height/24);
			g.drawLine(precol*width/11-width/22, prerow*height/12-height/24, precol*width/11-width/22, prerow*height/12-height/48);
			g.drawLine(precol*width/11+width/22, prerow*height/12-height/24, precol*width/11+width/44, prerow*height/12-height/24);
			g.drawLine(precol*width/11+width/22, prerow*height/12-height/24, precol*width/11+width/22, prerow*height/12-height/48);
			g.drawLine(precol*width/11+width/22, prerow*height/12+height/24, precol*width/11+width/44, prerow*height/12+height/24);
			g.drawLine(precol*width/11+width/22, prerow*height/12+height/24, precol*width/11+width/22, prerow*height/12+height/48);
			g.drawLine(precol*width/11-width/22, prerow*height/12+height/24, precol*width/11-width/44, prerow*height/12+height/24);
			g.drawLine(precol*width/11-width/22, prerow*height/12+height/24, precol*width/11-width/22, prerow*height/12+height/48);
			g.setColor(Color.black);
			g.setColor(Color.BLUE);
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
	}
	
	//鼠标事件
	@Override
	public void mouseClicked(MouseEvent e) {
		// TODO Auto-generated method stub
		if(!yourTurn || observer || state==4) return;
		//确定位置
		int width=getWidth();
		int height=getHeight();
		int x=e.getX();
		int y=e.getY();
		for(int i=1;i<=9;i++){
			if(Math.abs(width*i/11-x)<width/22){
				col=i;
				break;
			}
		}
		for(int i=1;i<=10;i++){
			if(Math.abs(height*i/12-y)<height/24){
				row=i;
				break;
			}
		}
		
		if(isSelected){
			//判断能不能到
			//先判断是否被将军
			int[] aixs=new int[4];
			if(isRed){//调整坐标
				aixs=new int[]{prerow,precol,row,col};
			}else{
				aixs=new int[]{11-prerow,precol,11-row,col};
				prerow=11-prerow;
				row=11-row;
			}
			
			int label=data[prerow-1][precol-1];//选中的是什么棋
			
			JudgeMove jm=new JudgeMove(this,data,isRed);
			switch (label) {
				case 1:
				case 8:
					//是车
					if(!jm.move_che(aixs)){
						common_op(aixs);
						return;
					}
					break;
				case 2:
				case 9:
					//是马
					if(!jm.move_ma(aixs)){
						common_op(aixs);
						return;
					}
					break;
				case 3:
				case 10:
					//是相
					if(!jm.move_xiang(aixs)){
						common_op(aixs);
						return;
					}
					break;
				case 4:
				case 11:
					//士
					if(!jm.move_shi(aixs)){
						common_op(aixs);
						return;
					}
					break;
				case 5:
				case 12:
					//是将
					if(!jm.move_jiang(aixs)){
						common_op(aixs);
						return;
					}
					break;
				case 6:
				case 13:
					//是炮
					if(!jm.move_pao(aixs)){
						common_op(aixs);
						return;
					}
					break;
				case 7:
				case 14:
					//是兵
					if(!jm.move_bing(aixs)){
						common_op(aixs);
						return;
					}
					break;
				default:
					break;
			}
			
			
			//在此处验证是否被将军
			int[][] datasub=Common.Backup(data);
			datasub[row-1][col-1]=datasub[prerow-1][precol-1];
			datasub[prerow-1][precol-1]=0;
			if(new HasFinished(datasub).isFinished(isRed)){
				data=datasub;
				tip=false;
				isRedo=true;
				repaint();
				internet.writeMessage("Congratulate,you win.");
				//写入
				rd.add_one_step(Common.Backup(datasub));
				SimpleDateFormat sf=new SimpleDateFormat("YYYYMMDD_HHmmss");
				Replay.WriteFile(rd.getMove_down(), "file/"+sf.format(new Date())+".chess");
				state=4;
				return;
			}
			
			if(!HasFinished.jiangTip(datasub, isRed)){
				if(!isRed)
					prerow=11-prerow;//恢复，解决被将军后移动其他子问题
				isSelected=true;
				return;
			}//当前步不能走
//			rd.add_one_step(Common.Backup(data));//将当前步加入列表中
			internet.writeMessage(prerow+" "+precol+" "+row+" "+col);//向服务器写消息
			isSelected=false;
			isRedo=false;
		}else{//选子阶段
			precol=col;
			prerow=row;
			if(!isRed){
				prerow=11-prerow;
				row=11-row;
			}
			if(precol==0 || prerow==0)
				return;
			//两人对弈
			if(data[prerow-1][precol-1]==0){
				return;
			}else if((isRed && data[prerow-1][precol-1]>=8) || 
					(!isRed && data[prerow-1][precol-1]<=7)){
				return;
			}
			tip=true;
			isSelected=true;
			//恢复
			if(!isRed){
				prerow=11-prerow;
				row=11-row;
			}
			repaint();//解决了选中问题
		}
	}

	public void mousePressed(MouseEvent e) {}
	public void mouseReleased(MouseEvent e) {}
	public void mouseEntered(MouseEvent e) {}
	public void mouseExited(MouseEvent e) {}

	public boolean isSelected() {
		return isSelected;
	}

	public void setSelected(boolean isSelected) {
		this.isSelected = isSelected;
	}

	public boolean isYourTurn() {
		return yourTurn;
	}

	public void setYourTurn(boolean yourTurn) {
		this.yourTurn = yourTurn;
	}

	public int[][] getData() {
		return data;
	}

	public void setData(int[][] data) {
		this.data = data;
	}

	public Internet getInternet() {
		return internet;
	}

	public Redo getRd() {
		return rd;
	}

	public void setRedo(boolean isRedo) {
		this.isRedo = isRedo;
	}

	public Commication getCc() {
		return cc;
	}
	
	public ChessBoard Refresh() {
		return new ChessBoard(pics, rd);
	}

	@Override
	public void run() {
		// TODO Auto-generated method stub
		while(true){
			String message_array=internet.readMessage();
			state=3;//游戏中
			if(message_array.indexOf(",")>0){
				//结束消息
				JOptionPane.showMessageDialog(null, message_array);
				continue;
			}
		    int[][]	datasub=Common.String_to_Array(message_array);
		    int[] aixs=Common.FindDiff(data, datasub,isRed);
		    prerow=aixs[0];
		    if(!isRed) prerow=11-prerow;
		    precol=aixs[1];
		    row=aixs[2];
		    if(!isRed)
		    	row=11-row;
		    col=aixs[3];
		    data=datasub;
		    rd.add_one_step(Common.Backup(data));
			tip=false;
			repaint();
			yourTurn=!yourTurn;
		}
	}
	
	private void common_op(int[] aixs) {
		if(isRed){
			prerow=row=aixs[0];
		}else{
			prerow=row=11-aixs[0];
		}
		precol=col=aixs[1];
		repaint();
	}
}
//黑色子能吃己方子问题解决....