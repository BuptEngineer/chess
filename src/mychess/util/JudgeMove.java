package mychess.util;

import mychess.ui.ChessBoard;

public class JudgeMove {
	ChessBoard cb;
	private int[][] data=new int[10][9];
	private boolean isRed;
	public JudgeMove(ChessBoard cb,int[][] data,boolean isRed) {
		// TODO Auto-generated constructor stub
		this.cb=cb;
		this.data=BackData.Backup(data);
		this.isRed=isRed;
	}
	public boolean move_che(int[] aixs) {
		if((isRed && data[aixs[2]-1][aixs[3]-1]<8 && data[aixs[2]-1][aixs[3]-1]>0)
				|| (!isRed && data[aixs[2]-1][aixs[3]-1]>=8)){
			aixs[1]=aixs[3];
			aixs[0]=aixs[2];
			cb.setSelected(true);
			return false;//ͬ��ɫ
		}
		if(aixs[2]<aixs[0] && aixs[3]==aixs[1]){
			//��������
			for(int i=aixs[2]+1;i<aixs[0];i++){
				if(data[i-1][aixs[3]-1]!=0)
					return false;//���ӵ���
			}
		}else if(aixs[3]<aixs[1] && aixs[2]==aixs[0]){
			//��������
			for(int i=aixs[3]+1;i<aixs[1];i++)
				if(data[aixs[2]-1][i-1]!=0)
					return false;
		}else if(aixs[2]>aixs[0] && aixs[3]==aixs[1]){
			//��������
			for(int i=aixs[0]+1;i<aixs[2];i++)
				if(data[i-1][aixs[3]-1]!=0)
					return false;
		}else if(aixs[3]>aixs[1] && aixs[2]==aixs[0]){
			//��������
			for(int i=aixs[1]+1;i<aixs[3];i++)
				if(data[aixs[2]-1][i-1]!=0)
					return false;
		}else {
			if(data[aixs[2]-1][aixs[3]-1]==0)
				return false;
			else if((data[aixs[2]-1][aixs[3]-1]<8 && isRed) || (data[aixs[2]-1][aixs[3]-1]>=8 && !isRed)){
				//����ѡ��
				aixs[1]=aixs[3];
				aixs[0]=aixs[2];
				cb.setSelected(true);
			}
			return false;
		}
		return true;
	}
	
	public boolean move_pao(int[] aixs) {
		//����
		if(data[aixs[2]-1][aixs[3]-1]==0){
			//����ǿո���һ��
			if(aixs[2]<aixs[0] && aixs[3]==aixs[1]){
				//��������
				for(int i=aixs[2]+1;i<aixs[0];i++){
					if(data[i-1][aixs[3]-1]!=0)
						return false;//���ӵ���
				}
			}else if(aixs[3]<aixs[1] && aixs[2]==aixs[0]){
				//��������
				for(int i=aixs[3]+1;i<aixs[1];i++)
					if(data[aixs[2]-1][i-1]!=0)
						return false;
			}else if(aixs[2]>aixs[0] && aixs[3]==aixs[1]){
				//��������
				for(int i=aixs[0]+1;i<aixs[2];i++)
					if(data[i-1][aixs[3]-1]!=0)
						return false;
			}else if(aixs[3]>aixs[1] && aixs[2]==aixs[0]){
				//��������
				for(int i=aixs[1]+1;i<aixs[3];i++)
					if(data[aixs[2]-1][i-1]!=0)
						return false;
			}else {
				return false;
			}
		}else if((isRed && data[aixs[2]-1][aixs[3]-1]<8) || (!isRed && data[aixs[2]-1][aixs[3]-1]>=8)){
			aixs[1]=aixs[3];
			aixs[0]=aixs[2];
			cb.setSelected(true);
			return false;
		}else{
			//�ǶԷ�����
			//����ǡ�ø�һ��
			int num=0;
			if(aixs[2]<aixs[0] && aixs[3]==aixs[1]){
				//��
				for(int i=aixs[2]+1;i<aixs[0];i++){
					if(data[i-1][aixs[3]-1]!=0)
						num++;
				}
			}else if(aixs[2]>aixs[0] && aixs[3]==aixs[1]){
				//��
				for(int i=aixs[0]+1;i<aixs[2];i++)
					if(data[i-1][aixs[3]-1]!=0)
						num++;
			}else if (aixs[2]==aixs[0] && aixs[3]>aixs[1]) {
				//��
				for(int i=aixs[1]+1;i<aixs[3];i++)
					if(data[aixs[2]-1][i-1]!=0)
						num++;
			}else if(aixs[2]==aixs[0] && aixs[3]<aixs[1]){
				//��
				for(int i=aixs[3]+1;i<aixs[1];i++)
					if(data[aixs[2]-1][i-1]!=0)
						num++;
			}
			if(num!=1)
				return false;
		}
		return true;
	}
	
	public boolean move_ma(int[] aixs) {
		//����
		if((isRed && data[aixs[2]-1][aixs[3]-1]<8 && data[aixs[2]-1][aixs[3]-1]>0) 
				|| (!isRed && data[aixs[2]-1][aixs[3]-1]>=8)){
			aixs[1]=aixs[3];
			aixs[0]=aixs[2];
			cb.setSelected(true);
			return false;
		}
		if(aixs[3]-aixs[1]==2 && aixs[0]-aixs[2]==1){
			//���ն�������
			if(data[aixs[0]-1][aixs[1]]!=0)
				return false;
		}else if(aixs[3]-aixs[1]==2 && aixs[2]-aixs[0]==1){
			//���ն��Ϸ���
			if(data[aixs[0]-1][aixs[1]]!=0)
				return false;
		}else if(aixs[1]-aixs[3]==2 && aixs[2]-aixs[0]==1){
			//�������Ϸ���
			if(data[aixs[0]-1][aixs[1]-2]!=0)
				return false;
		}else if(aixs[1]-aixs[3]==2 && aixs[0]-aixs[2]==1){
			//������������
			if(data[aixs[0]-1][aixs[1]-2]!=0)
				return false;
		}else if(aixs[3]-aixs[1]==1 && aixs[0]-aixs[2]==2){
			//���ն���
			if(data[aixs[0]-2][aixs[1]-1]!=0)
				return false;
		}else if(aixs[3]-aixs[1]==1 && aixs[2]-aixs[0]==2){
			//���ն���
			if(data[aixs[0]][aixs[1]-1]!=0)
				return false;
		}else if(aixs[1]-aixs[3]==1 && aixs[2]-aixs[0]==2){
			if(data[aixs[0]][aixs[1]-1]!=0)
				return false;
		}else if(aixs[1]-aixs[3]==1 && aixs[0]-aixs[2]==2){
			if(data[aixs[0]-2][aixs[1]-1]!=0)
				return false;
		}else {
			if(data[aixs[2]-1][aixs[3]-1]==0)
				return false;
			else if((data[aixs[2]-1][aixs[3]-1]<8 && isRed) || (data[aixs[2]-1][aixs[3]-1]>=8 && !isRed)){
				aixs[1]=aixs[3];
				aixs[0]=aixs[2];
				cb.setSelected(true);
			}
			return false;
		}
		return true;
	}
	
	public boolean move_xiang(int[] aixs) {
		//����
		if((isRed && data[aixs[2]-1][aixs[3]-1]<8 && data[aixs[2]-1][aixs[3]-1]>0) || (!isRed && data[aixs[2]-1][aixs[3]-1]>=8)){
			aixs[1]=aixs[3];
			aixs[0]=aixs[2];
			cb.setSelected(true);
			return false;
			//TODO
		}
		if(isRed){
			if(aixs[2]<=5)
				return false;//���ܳ�ȥ
		}else{
			if(aixs[2]>5)
				return false;
		}
		if(aixs[3]-aixs[1]==2 && aixs[0]-aixs[2]==2){
			//����������
			if(data[aixs[0]-2][aixs[1]]!=0)
				return false;
		}else if(aixs[3]-aixs[1]==2 && aixs[2]-aixs[0]==2){
			//�����Ϸ���
			if(data[aixs[0]][aixs[1]]!=0)
				return false;
		}else if(aixs[1]-aixs[3]==2 && aixs[2]-aixs[0]==2){
			//�����Ϸ���
			if(data[aixs[0]][aixs[1]-2]!=0)
				return false;
		}else if(aixs[1]-aixs[3]==2 && aixs[0]-aixs[2]==2){
			//����������
			if(data[aixs[0]-2][aixs[1]-2]!=0)
				return false;
		}else {
			if(data[aixs[2]-1][aixs[3]-1]==0)
				return false;
			else if((data[aixs[2]-1][aixs[3]-1]<8 && isRed) || (data[aixs[2]-1][aixs[3]-1]>=8 && !isRed)){
				//����ѡ��
				aixs[1]=aixs[3];
				aixs[0]=aixs[2];
				cb.setSelected(true);
			}
			return false;
		}
		return true;
	}
	
	public boolean move_shi(int[] aixs) {
		//ʿ
		if((isRed && data[aixs[2]-1][aixs[3]-1]<8 && data[aixs[2]-1][aixs[3]-1]>0)
				|| (!isRed && data[aixs[2]-1][aixs[3]-1]>=8)){
			aixs[1]=aixs[3];
			aixs[0]=aixs[2];
			cb.setSelected(true);
			return false;
		}
		if(aixs[3]>6 || aixs[3]<4)
			return false;
		if(isRed){
			if(aixs[2]<8)
				return false;
		}else{
			if(aixs[2]>3)
				return false;
		}
		if(aixs[3]-aixs[1]==1 && aixs[2]-aixs[0]==1){
			//�����Ϸ���
		}else if(aixs[3]-aixs[1]==1 && aixs[0]-aixs[2]==1){
			//����������
		}else if(aixs[1]-aixs[3]==1 && aixs[2]-aixs[0]==1){
			//�����Ϸ���
		}else if(aixs[1]-aixs[3]==1 && aixs[0]-aixs[2]==1){
			//����������
		}else {
			if(data[aixs[2]-1][aixs[3]-1]==0)
				return false;
			else if((data[aixs[2]-1][aixs[3]-1]<8 && isRed) || (data[aixs[2]-1][aixs[3]-1]>=8 && !isRed)){
				//����ѡ��
				aixs[1]=aixs[3];
				aixs[0]=aixs[2];
				cb.setSelected(true);
			}
			return false;
		}
		return true;
	}
	
	public boolean move_jiang(int[] aixs) {
		//�ǽ�
		if((isRed && data[aixs[2]-1][aixs[3]-1]<8 && data[aixs[2]-1][aixs[3]-1]>0)
				|| (!isRed && data[aixs[2]-1][aixs[3]-1]>=8)){
			aixs[1]=aixs[3];
			aixs[0]=aixs[2];
			cb.setSelected(true);
			return false;
		}
		if(aixs[3]>6 || aixs[3]<4)
			return false;
		if(isRed){
			if(aixs[2]<8)
				return false;
		}else{
			if(aixs[2]>3)
				return false;
		}
		if(aixs[3]-aixs[1]==1 && aixs[2]==aixs[0]){
			//�ұ�
		}
		else if(aixs[3]==aixs[1] && aixs[0]-aixs[2]==1){
			//��
		}
		else if(aixs[1]-aixs[3]==1 && aixs[0]==aixs[2]){
			//��
		}else if(aixs[1]==aixs[3] && aixs[2]-aixs[0]==1){
			//��
		}else {
			if(data[aixs[2]-1][aixs[3]-1]==0)
				return false;
			else if((data[aixs[2]-1][aixs[3]-1]<8 && isRed) || (data[aixs[2]-1][aixs[3]-1]>=8 && !isRed)){
				//����ѡ��
				aixs[1]=aixs[3];
				aixs[0]=aixs[2];
				cb.setSelected(true);
			}
			return false;
		}
		return true;
	}
	
	public boolean move_bing(int[] aixs) {
		//�Ǳ�
		if((isRed && data[aixs[2]-1][aixs[3]-1]<8 && data[aixs[2]-1][aixs[3]-1]>0)
				|| (!isRed && data[aixs[2]-1][aixs[3]-1]>=8)){
			aixs[1]=aixs[3];
			aixs[0]=aixs[2];
			cb.setSelected(true);
			return false;
			//TODO
		}
		if(isRed){
			if(aixs[0]>5){
				//���Լ�������� ���������ƶ�
				if(aixs[0]-aixs[2]==1 && aixs[1]==aixs[3]){
					//ǰ��
				}else if(data[aixs[2]-1][aixs[3]-1]<8){
					aixs[1]=aixs[3];
					aixs[0]=aixs[2];
					cb.setSelected(true);
					return false;
				}else{
					return false;
				}
			}else{
				//�ڶԷ������
				if(aixs[0]-aixs[2]==1 && aixs[1]==aixs[3]){
					//��
				}else if(aixs[0]==aixs[2] && aixs[1]-aixs[3]==1){
					//��
				}else if(aixs[0]==aixs[2] && aixs[3]-aixs[1]==1){
					//��
				}else {
					aixs[1]=aixs[3];
					aixs[0]=aixs[2];
					cb.setSelected(true);
					return false;
				}
			}
		}else{
			if(aixs[0]<=5){
				//���Լ�������� ���������ƶ�
				if(aixs[0]-aixs[2]==-1 && aixs[1]==aixs[3]){
					//ǰ��
				}else if(data[aixs[2]-1][aixs[3]-1]>=8){
					aixs[1]=aixs[3];
					aixs[0]=aixs[2];
					cb.setSelected(true);
					return false;
				}else {
					return false;
				}
			}else{
				//�ڶԷ������
				if(aixs[0]-aixs[2]==-1 && aixs[1]==aixs[3]){
					//��
				}else if(aixs[0]==aixs[2] && aixs[1]-aixs[3]==1){
					//��
				}else if(aixs[0]==aixs[2] && aixs[3]-aixs[1]==1){
					//��
				}else {
					aixs[1]=aixs[3];
					aixs[0]=aixs[2];
					cb.setSelected(true);
					return false;
				}
			}
		}
		return true;
	}
}
