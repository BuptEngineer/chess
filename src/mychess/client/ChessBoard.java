package mychess.client;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import mychess.entity.Code;
import mychess.entity.DataMessage;
import mychess.entity.Message;
import mychess.entity.NormalMessage;
import mychess.util.Common;
import mychess.util.HasFinished;
import mychess.util.Internet;
import mychess.util.JudgeMove;

public class ChessBoard extends JPanel implements MouseListener,Runnable{
	/** serialVersionUID*/
	private static final long serialVersionUID = 1L;
	protected DataMessage message;//每次准备一个消息报文，准备消息通信
	protected boolean tip;//是否重绘的时候加上提示（也就是走棋的时候有标记）
	protected boolean isSelected;//棋子是否被选中，决定下一步点击是移动棋子还是在选择棋子
	protected int[][] data;//当前棋局的状态数组,用于重绘
	protected Image[] pics;//加载象棋的图片
	private Internet internet;//数据服务器对象
	private String result="";
	public ChessBoard() {
		// TODO Auto-generated constructor stub
	}
	
	public ChessBoard(Image[] pictures) {
		// TODO Auto-generated constructor stub
		pics=pictures;
		init();
		addMouseListener(this);//监听鼠标操作
		Thread t=new Thread(this);//数据服务交互
		t.start();
	}
	
	public void init() {
		internet=new Internet();//开启数据服务器，至于消息服务器需要手动提前开启
		message= (DataMessage) internet.readMessage();
		data=Common.String_to_Array(message.getData());
	}
	
	//绘图
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
		g.setFont(new Font("华文行楷", Font.BOLD, 25));
		g.drawString("楚        河", (int)(width*1.5/11), (int)(height*(4.6+1)/12));
		g.drawString("汉        界", (int)(width*7.1/11), (int)(height*(4.6+1)/12));
		g.setColor(Color.RED);
		g.drawString(result,(int)(width*4/11), (int)(height*(4.6+1)/12));
		g.setColor(Color.BLACK);
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
				if(message.getRole()==2)
					baseY=height*(11-i)/12;
				else
					baseY=height*i/12;
				g.drawImage(pics[data[i-1][j-1]], baseX-width/22, baseY-height/24, width/11, height/12, this);
			}
		}
		
		int precol=message.getPrecol();
		int prerow=message.getPrerow();
		int col=message.getCol();
		int row=message.getRow();
		if((message.isYourTurn() || message.getRole()>2) && message.getCode()==Code.Run && row!=0){//或者是旁观者
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
		if(!message.isYourTurn() || message.getRole()>2 || message.code.getDes()=="结束") return;//如果不是自己回合或者角色为旁观者或者当前游戏状态已经结束
													//则无法点击棋盘
		//确定位置，获取现在棋子的列和行
		//如果isSelected为假，那么这个列col和行row将是下次isSelected为真的时候之前的preCol和preRow
		//如果isSelected为真，那么这个列col和行row将是现在的col和row
		int width=getWidth();
		int height=getHeight();
		int x=e.getX();
		int y=e.getY();
		for(int i=1;i<=9;i++){
			if(Math.abs(width*i/11-x)<width/22){
				//col=i;
				message.setCol(i);
				break;
			}
		}
		for(int i=1;i<=10;i++){
			if(Math.abs(height*i/12-y)<height/24){
				//row=i;
				message.setRow(i);
				break;
			}
		}
		
		if(isSelected){//如果已经选中了棋子，接下来就是移动棋子操作（此时四个坐标已经确定了）
			//判断能不能到
			//先判断是否被将军
			int[] aixs=new int[4];
			if(message.getRole()==1){//红方
				aixs=new int[]{message.getPrerow(),message.getPrecol(),message.getRow(),message.getCol()};
			}else{
				aixs=new int[]{11-message.getPrerow(),message.getPrecol(),11-message.getRow(),message.getCol()};
				message.setPrerow(11-message.getPrerow());
				message.setRow(11-message.getRow());
			}
			
			message.setChess(data[message.getPrerow()-1][message.getPrecol()-1]);
			
			JudgeMove jm=new JudgeMove(this,data,message.getRole()==1);//移动规则判断对象
			
			switch (message.getChess()) {
				case 1:
				case 8:
					//是车
					if(!jm.move_che(aixs)){//移动车的规则不合理
						common_op(aixs);//撤销、恢复坐标
						return;
					}
					break;
				case 2:
				case 9:
					//是马
					if(!jm.move_ma(aixs)){//移动马的规则不合理
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
			
			//可以进行移动
			int[][] datasub=Common.Backup(data);//先备份棋局，下面进行试探性走棋
			message.setEatedChess(datasub[message.getRow()-1][message.getCol()-1]);//设置被吃掉的子
			datasub[message.getRow()-1][message.getCol()-1]=datasub[message.getPrerow()-1][message.getPrecol()-1];
			datasub[message.getPrerow()-1][message.getPrecol()-1]=0;
			if(!HasFinished.jiangTip(datasub, message.getRole()==1)){//当前被将军了，不能送将
				if(message.getRole()==2)
					message.setPrerow(11-message.getPrerow());
				isSelected=true;
				return;
			}//当前步不能走

			//到此，表明移动的步符合走棋规则、而且没有送将、以及游戏没有结束
			data=datasub;
			try {
				message.setStep(message.getStep()+1);
				internet.writeMessage(message.clone());
			} catch (CloneNotSupportedException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}//发送构造的消息
			
			//判断试探性走棋是否到达游戏结束状态
			if(new HasFinished(data).isFinished(message.getRole()==1)){//游戏结束
				NormalMessage myNormalMessage=new NormalMessage();//创建一般消息
				myNormalMessage.setRole(message.getRole());
				myNormalMessage.setAttach("游戏结束");
				internet.writeMessage(myNormalMessage);
				message.setCode(Code.Over);
			}
			
			isSelected=false;//被选中为false，这样下阶段是选择棋子，而不是移动棋子
			message.setYourTurn(!message.isYourTurn());//回合切换
			tip=false;
		}else{//选子阶段，这是isSelected为false的情况			
			message.setPrecol(message.getCol());
			message.setPrerow(message.getRow());
			if(message.getRole()==2){
				message.setPrerow(11-message.getPrerow());
				message.setRow(11-message.getRow());
			}
			if(message.getPrecol()==0 || message.getPrerow()==0)
				return;

			if(data[message.getPrerow()-1][message.getPrecol()-1]==0){
				return;
			}else if((message.getRole()==1 && data[message.getPrerow()-1][message.getPrecol()-1]>=8) || 
					(message.getRole()==2 && data[message.getPrerow()-1][message.getPrecol()-1]<=7)){
				return;
			}
			
			//到此合理选择了棋子
			tip=true;//开启提示功能
			isSelected=true;//设置isSelected为真，下一阶段是移动棋子而不是选择棋子
			if(message.getRole()==2){
				message.setPrerow(11-message.getPrerow());
				message.setRow(11-message.getRow());
			}
		}
		repaint();
	}

	public void mousePressed(MouseEvent e) {}
	public void mouseReleased(MouseEvent e) {}
	public void mouseEntered(MouseEvent e) {}
	public void mouseExited(MouseEvent e) {}

	public void setSelected(boolean isSelected) {
		this.isSelected = isSelected;
	}
	
	/**
	 * 与数据服务器和消息服务器进行交互
	 */
	@Override
	public void run() {
		// TODO Auto-generated method stub
		while(true){
			Message myMessage=internet.readMessage();
			message.setCode(Code.Run);
			if(myMessage instanceof NormalMessage){
				//如果是消息
				if(((NormalMessage) myMessage).getAttach().equals("悔棋")){
					int showConfirmDialog = JOptionPane.showConfirmDialog(null, "对方请求悔棋，是否同意");
					if(message.getRole()>2) continue;//旁观者清
					if(showConfirmDialog==JOptionPane.YES_OPTION){
						//发个同意的消息
						NormalMessage temp=new NormalMessage();
						temp.setAttach("同意悔棋");
						internet.writeMessage(temp);
					}else if(showConfirmDialog==JOptionPane.NO_OPTION){
						//不同意悔棋
						NormalMessage temp=new NormalMessage();
						temp.setAttach("对方不同意悔棋");
						internet.writeMessage(temp);
					}
				}else if(((NormalMessage) myMessage).getAttach().equals("游戏结束")){
					String role=myMessage.getRole()==1?"红方":"黑方";
					result=role+"胜利";
					message.setCode(Code.Over);
					repaint();
				}else if(((NormalMessage)myMessage).getAttach().equals("重新开局")){
					int showConfirmDialog = JOptionPane.showConfirmDialog(null, "对方请求再来一局，是否同意");
					if(message.getRole()>2) continue;//同意重新开始
					if(showConfirmDialog==JOptionPane.YES_OPTION){
						//发个同意的消息
						NormalMessage temp=new NormalMessage();
						temp.setAttach("同意开局");
						internet.writeMessage(temp);
					}else if(showConfirmDialog==JOptionPane.NO_OPTION){
						//不同意悔棋
						NormalMessage temp=new NormalMessage();
						temp.setAttach("对方不同意开局");
						internet.writeMessage(temp);
					}
				}else if(((NormalMessage)myMessage).getAttach().equals("同意开局")){
					//将自己的最初数据包发给对方
					init();
					result="";
					repaint();
				}else if(((NormalMessage)myMessage).getAttach().contains("认输")){
					result=message.getRole()==1?"红方获胜":"黑方获胜";
					message.setCode(Code.Over);
					repaint();
				}else if(((NormalMessage)myMessage).getAttach().equals("求和")){
					int showConfirmDialog = JOptionPane.showConfirmDialog(null, "对方请求求和，是否同意");
					if(showConfirmDialog==JOptionPane.YES_OPTION){
						//发个同意的消息
						NormalMessage temp=new NormalMessage();
						temp.setAttach("同意求和");
						internet.writeMessage(temp);
					}else if(showConfirmDialog==JOptionPane.NO_OPTION){
						//不同意悔棋
						NormalMessage temp=new NormalMessage();
						temp.setAttach("对方不同意求和");
						internet.writeMessage(temp);
					}
				}else if(((NormalMessage)myMessage).getAttach().equals("同意求和")){
					result="双方议和";
					message.setCode(Code.Over);
					repaint();
				}else if(((NormalMessage)myMessage).getAttach().equals("离开")){
					JOptionPane.showMessageDialog(null, "对方已经离开,即将退出");
					System.exit(1);
				}
				if(filterMessage(myMessage))
					JOptionPane.showMessageDialog(null, ((NormalMessage) myMessage).getAttach());
				continue;
			}
			//如果是数据
			message.setPrerow(((DataMessage) myMessage).getPrerow());
			message.setPrecol(((DataMessage) myMessage).getPrecol());
			message.setRow(((DataMessage) myMessage).getRow());
			message.setCol(((DataMessage) myMessage).getCol());
			message.setStep(myMessage.getStep());
			
			if(message.getRole()==2){
				message.setPrerow(11-message.getPrerow());
				message.setRow(11-message.getRow());
			}
			
			data=Common.String_to_Array(((DataMessage)myMessage).data);
			tip=false;
			repaint();
			message.setYourTurn(!message.isYourTurn());
		}
	}
	
	/**
	 * 悔棋功能的实现
	 */
	public void Redo() {
		//执行悔棋
		//向服务器写悔棋请求数据
		if(message.isYourTurn()){
			JOptionPane.showMessageDialog(null, "到你走棋了,不能悔棋");
			return;
		}
		if(message.getStep()==1){
			JOptionPane.showMessageDialog(null, "当前是第一步，不能悔棋");
			return;
		}
		NormalMessage message=new NormalMessage();
		message.setAttach("悔棋");
		internet.writeMessage(message);
	}
	
	/**
	 * 重新开始的实现
	 */
	public void restart() {
		if(message.getCode().getDes().equals("结束")){
			//游戏已经结束，可以重新开始
			NormalMessage message=new NormalMessage();
			message.setAttach("重新开局");
			message.setRole(message.getRole());
			internet.writeMessage(message);
		}else{
			JOptionPane.showMessageDialog(null, "游戏没有结束,请继续走棋");
			return;
		}
	}
	
	public void lose() {
		if(message.getStep()<20){
			JOptionPane.showMessageDialog(null, "二十步之内不能认输");
			return;
		}
		result=message.getRole()==1?"黑方胜利":"红方胜利";
		NormalMessage myMessage=new NormalMessage();
		myMessage.setAttach((message.getRole()==1?"红方":"黑方")+"认输");
		internet.writeMessage(myMessage);
		message.setCode(Code.Over);
		repaint();
	}
	
	public void peace() {
		if(message.getStep()<50){
			JOptionPane.showMessageDialog(null, "五十步之内不能求和");
			return;
		}
		NormalMessage message=new NormalMessage();
		message.setAttach("求和");
		internet.writeMessage(message);
	}
	
	public void leave() {
		NormalMessage message=new NormalMessage();
		message.setAttach("离开");
		internet.writeMessage(message);
	}
	
	//绘图的公共操作,绘制棋盘中的米字
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
	
	//用于在不合理的移动棋子时候能恢复到移动前的坐标状态
	protected void common_op(int[] aixs) {
		if(message.getRole()==1){
			message.setPrerow(aixs[0]);
			message.setRow(aixs[0]);
		}else{
			message.setPrerow(11-aixs[0]);
			message.setRow(11-aixs[0]);
		}
		message.setPrecol(aixs[1]);
		message.setCol(aixs[1]);
		repaint();
	}
	
	//过滤一些消息，使其不显示
	private boolean filterMessage(Message message) {
		//过滤消息
		if(((NormalMessage)message).getAttach().equals("悔棋") || 
				((NormalMessage)message).getAttach().equals("游戏结束") || ((NormalMessage)message).getAttach().equals("同意开局")
				|| ((NormalMessage)message).getAttach().equals("求和") || ((NormalMessage)message).getAttach().equals("同意求和"))
			return false;
		return true;
	}
}
