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
			return false;//同颜色
		}
		if(aixs[2]<aixs[0] && aixs[3]==aixs[1]){
			//想往上走
			for(int i=aixs[2]+1;i<aixs[0];i++){
				if(data[i-1][aixs[3]-1]!=0)
					return false;//有子挡了
			}
		}else if(aixs[3]<aixs[1] && aixs[2]==aixs[0]){
			//想往左走
			for(int i=aixs[3]+1;i<aixs[1];i++)
				if(data[aixs[2]-1][i-1]!=0)
					return false;
		}else if(aixs[2]>aixs[0] && aixs[3]==aixs[1]){
			//想往下走
			for(int i=aixs[0]+1;i<aixs[2];i++)
				if(data[i-1][aixs[3]-1]!=0)
					return false;
		}else if(aixs[3]>aixs[1] && aixs[2]==aixs[0]){
			//想往右走
			for(int i=aixs[1]+1;i<aixs[3];i++)
				if(data[aixs[2]-1][i-1]!=0)
					return false;
		}else {
			if(data[aixs[2]-1][aixs[3]-1]==0)
				return false;
			else if((data[aixs[2]-1][aixs[3]-1]<8 && isRed) || (data[aixs[2]-1][aixs[3]-1]>=8 && !isRed)){
				//换子选择
				aixs[1]=aixs[3];
				aixs[0]=aixs[2];
				cb.setSelected(true);
			}
			return false;
		}
		return true;
	}
	
	public boolean move_pao(int[] aixs) {
		//是炮
		if(data[aixs[2]-1][aixs[3]-1]==0){
			//如果是空格，像车一样
			if(aixs[2]<aixs[0] && aixs[3]==aixs[1]){
				//想往上走
				for(int i=aixs[2]+1;i<aixs[0];i++){
					if(data[i-1][aixs[3]-1]!=0)
						return false;//有子挡了
				}
			}else if(aixs[3]<aixs[1] && aixs[2]==aixs[0]){
				//想往左走
				for(int i=aixs[3]+1;i<aixs[1];i++)
					if(data[aixs[2]-1][i-1]!=0)
						return false;
			}else if(aixs[2]>aixs[0] && aixs[3]==aixs[1]){
				//想往下走
				for(int i=aixs[0]+1;i<aixs[2];i++)
					if(data[i-1][aixs[3]-1]!=0)
						return false;
			}else if(aixs[3]>aixs[1] && aixs[2]==aixs[0]){
				//想往右走
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
			//是对方的子
			//必须恰好隔一个
			int num=0;
			if(aixs[2]<aixs[0] && aixs[3]==aixs[1]){
				//上
				for(int i=aixs[2]+1;i<aixs[0];i++){
					if(data[i-1][aixs[3]-1]!=0)
						num++;
				}
			}else if(aixs[2]>aixs[0] && aixs[3]==aixs[1]){
				//下
				for(int i=aixs[0]+1;i<aixs[2];i++)
					if(data[i-1][aixs[3]-1]!=0)
						num++;
			}else if (aixs[2]==aixs[0] && aixs[3]>aixs[1]) {
				//右
				for(int i=aixs[1]+1;i<aixs[3];i++)
					if(data[aixs[2]-1][i-1]!=0)
						num++;
			}else if(aixs[2]==aixs[0] && aixs[3]<aixs[1]){
				//左
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
		//是马
		if((isRed && data[aixs[2]-1][aixs[3]-1]<8 && data[aixs[2]-1][aixs[3]-1]>0) 
				|| (!isRed && data[aixs[2]-1][aixs[3]-1]>=8)){
			aixs[1]=aixs[3];
			aixs[0]=aixs[2];
			cb.setSelected(true);
			return false;
		}
		if(aixs[3]-aixs[1]==2 && aixs[0]-aixs[2]==1){
			//躺日东北方向
			if(data[aixs[0]-1][aixs[1]]!=0)
				return false;
		}else if(aixs[3]-aixs[1]==2 && aixs[2]-aixs[0]==1){
			//躺日东南方向
			if(data[aixs[0]-1][aixs[1]]!=0)
				return false;
		}else if(aixs[1]-aixs[3]==2 && aixs[2]-aixs[0]==1){
			//躺日西南方向
			if(data[aixs[0]-1][aixs[1]-2]!=0)
				return false;
		}else if(aixs[1]-aixs[3]==2 && aixs[0]-aixs[2]==1){
			//躺日西北方向
			if(data[aixs[0]-1][aixs[1]-2]!=0)
				return false;
		}else if(aixs[3]-aixs[1]==1 && aixs[0]-aixs[2]==2){
			//跳日东北
			if(data[aixs[0]-2][aixs[1]-1]!=0)
				return false;
		}else if(aixs[3]-aixs[1]==1 && aixs[2]-aixs[0]==2){
			//跳日东南
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
		//是相
		if((isRed && data[aixs[2]-1][aixs[3]-1]<8 && data[aixs[2]-1][aixs[3]-1]>0) || (!isRed && data[aixs[2]-1][aixs[3]-1]>=8)){
			aixs[1]=aixs[3];
			aixs[0]=aixs[2];
			cb.setSelected(true);
			return false;
			//TODO
		}
		if(isRed){
			if(aixs[2]<=5)
				return false;//不能出去
		}else{
			if(aixs[2]>5)
				return false;
		}
		if(aixs[3]-aixs[1]==2 && aixs[0]-aixs[2]==2){
			//往东北方向
			if(data[aixs[0]-2][aixs[1]]!=0)
				return false;
		}else if(aixs[3]-aixs[1]==2 && aixs[2]-aixs[0]==2){
			//往东南方向
			if(data[aixs[0]][aixs[1]]!=0)
				return false;
		}else if(aixs[1]-aixs[3]==2 && aixs[2]-aixs[0]==2){
			//往西南方向
			if(data[aixs[0]][aixs[1]-2]!=0)
				return false;
		}else if(aixs[1]-aixs[3]==2 && aixs[0]-aixs[2]==2){
			//往西北方向
			if(data[aixs[0]-2][aixs[1]-2]!=0)
				return false;
		}else {
			if(data[aixs[2]-1][aixs[3]-1]==0)
				return false;
			else if((data[aixs[2]-1][aixs[3]-1]<8 && isRed) || (data[aixs[2]-1][aixs[3]-1]>=8 && !isRed)){
				//换子选择
				aixs[1]=aixs[3];
				aixs[0]=aixs[2];
				cb.setSelected(true);
			}
			return false;
		}
		return true;
	}
	
	public boolean move_shi(int[] aixs) {
		//士
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
			//往东南方向
		}else if(aixs[3]-aixs[1]==1 && aixs[0]-aixs[2]==1){
			//往东北方向
		}else if(aixs[1]-aixs[3]==1 && aixs[2]-aixs[0]==1){
			//往西南方向
		}else if(aixs[1]-aixs[3]==1 && aixs[0]-aixs[2]==1){
			//往西北方向
		}else {
			if(data[aixs[2]-1][aixs[3]-1]==0)
				return false;
			else if((data[aixs[2]-1][aixs[3]-1]<8 && isRed) || (data[aixs[2]-1][aixs[3]-1]>=8 && !isRed)){
				//换子选择
				aixs[1]=aixs[3];
				aixs[0]=aixs[2];
				cb.setSelected(true);
			}
			return false;
		}
		return true;
	}
	
	public boolean move_jiang(int[] aixs) {
		//是将
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
			//右边
		}
		else if(aixs[3]==aixs[1] && aixs[0]-aixs[2]==1){
			//上
		}
		else if(aixs[1]-aixs[3]==1 && aixs[0]==aixs[2]){
			//左
		}else if(aixs[1]==aixs[3] && aixs[2]-aixs[0]==1){
			//下
		}else {
			if(data[aixs[2]-1][aixs[3]-1]==0)
				return false;
			else if((data[aixs[2]-1][aixs[3]-1]<8 && isRed) || (data[aixs[2]-1][aixs[3]-1]>=8 && !isRed)){
				//换子选择
				aixs[1]=aixs[3];
				aixs[0]=aixs[2];
				cb.setSelected(true);
			}
			return false;
		}
		return true;
	}
	
	public boolean move_bing(int[] aixs) {
		//是兵
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
				//在自己的阵地上 不能左右移动
				if(aixs[0]-aixs[2]==1 && aixs[1]==aixs[3]){
					//前进
				}else if(data[aixs[2]-1][aixs[3]-1]<8){
					aixs[1]=aixs[3];
					aixs[0]=aixs[2];
					cb.setSelected(true);
					return false;
				}else{
					return false;
				}
			}else{
				//在对方阵地上
				if(aixs[0]-aixs[2]==1 && aixs[1]==aixs[3]){
					//进
				}else if(aixs[0]==aixs[2] && aixs[1]-aixs[3]==1){
					//左
				}else if(aixs[0]==aixs[2] && aixs[3]-aixs[1]==1){
					//右
				}else {
					aixs[1]=aixs[3];
					aixs[0]=aixs[2];
					cb.setSelected(true);
					return false;
				}
			}
		}else{
			if(aixs[0]<=5){
				//在自己的阵地上 不能左右移动
				if(aixs[0]-aixs[2]==-1 && aixs[1]==aixs[3]){
					//前进
				}else if(data[aixs[2]-1][aixs[3]-1]>=8){
					aixs[1]=aixs[3];
					aixs[0]=aixs[2];
					cb.setSelected(true);
					return false;
				}else {
					return false;
				}
			}else{
				//在对方阵地上
				if(aixs[0]-aixs[2]==-1 && aixs[1]==aixs[3]){
					//进
				}else if(aixs[0]==aixs[2] && aixs[1]-aixs[3]==1){
					//左
				}else if(aixs[0]==aixs[2] && aixs[3]-aixs[1]==1){
					//右
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
