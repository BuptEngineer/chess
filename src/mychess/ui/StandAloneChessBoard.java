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
		//����ȫ��Message
		DataMessage message=new DataMessage();
		message.setData(Common.Array_to_String(ExpertSystem.initData()));
		message.setYourTurn(ReadProperties.YOURTURN);
		message.setCode(Code.Run);
		message.setRole((byte)1);//�췽
		data=Common.String_to_Array(message.getData());
		super.message=message;
		addMouseListener(this);
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		// TODO Auto-generated method stub
		if(!message.isYourTurn() || message.code.getDes()=="����") return;//��������Լ��غϻ��߽�ɫΪ�Թ��߻��ߵ�ǰ��Ϸ״̬�Ѿ�����
													//���޷��������
		//ȷ��λ�ã���ȡ�������ӵ��к���
		//���isSelectedΪ�٣���ô�����col����row�����´�isSelectedΪ���ʱ��֮ǰ��preCol��preRow
		//���isSelectedΪ�棬��ô�����col����row�������ڵ�col��row
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
		
		if(isSelected){//����Ѿ�ѡ�������ӣ������������ƶ����Ӳ�������ʱ�ĸ������Ѿ�ȷ���ˣ�
			//�ж��ܲ��ܵ�
			//���ж��Ƿ񱻽���
			int[] aixs=new int[]{message.getPrerow(),message.getPrecol(),message.getRow(),message.getCol()};
			message.setChess(data[message.getPrerow()-1][message.getPrecol()-1]);
			
			JudgeMove jm=new JudgeMove(this,data,message.getRole()==1);//�ƶ������ж϶���
			
			switch (message.getChess()) {
				case 1:
				case 8:
					//�ǳ�
					if(!jm.move_che(aixs)){//�ƶ����Ĺ��򲻺���
						common_op(aixs);//�������ָ�����
						return;
					}
					break;
				case 2:
				case 9:
					//����
					if(!jm.move_ma(aixs)){//�ƶ���Ĺ��򲻺���
						common_op(aixs);
						return;
					}
					break;
				case 3:
				case 10:
					//����
					if(!jm.move_xiang(aixs)){
						common_op(aixs);
						return;
					}
					break;
				case 4:
				case 11:
					//ʿ
					if(!jm.move_shi(aixs)){
						common_op(aixs);
						return;
					}
					break;
				case 5:
				case 12:
					//�ǽ�
					if(!jm.move_jiang(aixs)){
						common_op(aixs);
						return;
					}
					break;
				case 6:
				case 13:
					//����
					if(!jm.move_pao(aixs)){
						common_op(aixs);
						return;
					}
					break;
				case 7:
				case 14:
					//�Ǳ�
					if(!jm.move_bing(aixs)){
						common_op(aixs);
						return;
					}
					break;
				default:
					break;
			}
			
			//���Խ����ƶ�
			int[][] datasub=Common.Backup(data);//�ȱ�����֣����������̽������
			message.setEatedChess(datasub[message.getRow()-1][message.getCol()-1]);//���ñ��Ե�����
			datasub[message.getRow()-1][message.getCol()-1]=datasub[message.getPrerow()-1][message.getPrecol()-1];
			datasub[message.getPrerow()-1][message.getPrecol()-1]=0;
			if(!HasFinished.jiangTip(datasub, message.getRole()==1)){//��ǰ�������ˣ������ͽ�
				if(message.getRole()==2)
					message.setPrerow(11-message.getPrerow());
				isSelected=true;
				return;
			}//��ǰ��������

			//���ˣ������ƶ��Ĳ�����������򡢶���û���ͽ����Լ���Ϸû�н���
			data=datasub;
			
			//�ж���̽�������Ƿ񵽴���Ϸ����״̬
			if(new HasFinished(data).isFinished(message.getRole()==1)){//��Ϸ����
				//TODO
				message.setCode(Code.Over);
			}
			
			isSelected=false;//��ѡ��Ϊfalse�������½׶���ѡ�����ӣ��������ƶ�����
			message.setYourTurn(!message.isYourTurn());//�غ��л�
			tip=false;
		}else{//ѡ�ӽ׶Σ�����isSelectedΪfalse�����			
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
			
			//���˺���ѡ��������
			tip=true;//������ʾ����
			isSelected=true;//����isSelectedΪ�棬��һ�׶����ƶ����Ӷ�����ѡ������
			if(message.getRole()==2){
				message.setPrerow(11-message.getPrerow());
				message.setRow(11-message.getRow());
			}
		}
		repaint();
		
		//�ֵ����������
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
