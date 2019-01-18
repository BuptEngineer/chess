package mychess.ui;

import java.awt.Image;
import java.awt.event.MouseEvent;

import javax.swing.JOptionPane;

import mychess.entity.Code;
import mychess.entity.DataMessage;
import mychess.entity.NormalMessage;
import mychess.function.Computer;
import mychess.script.ReadProperties;
import mychess.util.Common;
import mychess.util.ExpertSystem;
import mychess.util.HasFinished;
import mychess.util.JudgeMove;

public class StandAloneChessBoard extends ChessBoard{
	private static final long serialVersionUID = 1L;

	public StandAloneChessBoard(Image[] pictures) {
		// TODO Auto-generated constructor stub
		pics=pictures;
		//构造全局Message
		DataMessage message=new DataMessage();
		message.setData(Common.Array_to_String(ExpertSystem.initData()));
		message.setYourTurn(ReadProperties.YOURTURN);
		message.setCode(Code.Run);
		message.setRole((byte)1);//红方
		data=Common.String_to_Array(message.getData());
		super.message=message;
		addMouseListener(this);
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		// TODO Auto-generated method stub
		if(!message.isYourTurn() || message.code.getDes()=="结束") return;//如果不是自己回合或者角色为旁观者或者当前游戏状态已经结束
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
			int[] aixs=new int[]{message.getPrerow(),message.getPrecol(),message.getRow(),message.getCol()};
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
			
			//判断试探性走棋是否到达游戏结束状态
			if(new HasFinished(data).isFinished(message.getRole()==1)){//游戏结束
				//TODO
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
		
		//轮到计算机走棋
		if(!message.isYourTurn()){
			int[] axis=new Computer().work(Common.Backup(data));
			message.setPrerow(axis[0]+1);
			message.setPrecol(axis[1]+1);
			message.setRow(axis[2]+1);
			message.setCol(axis[3]+1);
			message.setYourTurn(!message.isYourTurn());
			data[axis[2]][axis[3]]=data[axis[0]][axis[1]];
			data[axis[0]][axis[1]]=0;
			if(new HasFinished(data).isFinished(message.getRole()!=1)){
				message.setCode(Code.Over);
			}
			repaint();
		}
	}
}
