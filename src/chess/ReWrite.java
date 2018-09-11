package chess;

import java.util.ArrayList;
import java.util.List;

/**
 * 
 * <p>Title: ReWrite.java</p>
 * <p>Description:
 *  重写象棋核心代码
 * <p>@author dedong
 * <p>@date 2018/03/08 下午10:05:24
 *
 */
public class ReWrite {
	//仅仅传入一个局面的数据,返回一个最佳步对应的新局面数据
	//alpha和beta值应该比将军的值小，否则不立即将军
	private int[][] data;
	private int[][] raw;
	private final int rows=10;
	private final int cols=9;
	private int maxDepth=2;
	private int Goi=-1;
	@SuppressWarnings("unused")
	private int score;
	private int[][][] uchhessValues=new int[8][10][9];
	private int rawScore;
//	private int contiShuai=0;//不能连续将超过3次
	
	public ReWrite() {
		// TODO Auto-generated constructor stub
		data=new int[10][9];
//		System.out.println("warning,the data may be not validated");
	}
	
	public ReWrite(int[][] data,int depth){
		this.maxDepth=depth;
		this.data=data;//从外部传入的进行初始化
		this.raw=BackData(data);
		init();
		this.rawScore=chessValue(this.raw);
	}
	
	public int chessValue(int[][] sub) {
		int nowScore=0;
		for(int i=0;i<rows;i++){
			for(int j=0;j<cols;j++){
				if(sub[i][j]>=8){
					//黑色子
					nowScore+=uchhessValues[sub[i][j]-7][rows-i-1][j];
				}else if(sub[i][j]==0){
					continue;
				}else{
					//红色子
					nowScore-=uchhessValues[sub[i][j]][i][j];
				}
			}
		}
		return nowScore;
	}
	
	public void init() {
		 int[][] ju={{206,208,207,213,214,213,207,208,206},
				 {206,212,209,216,233,216,209,212,206},
				 {206,208,207,214,216,214,207,208,206},
				 {206,213,213,216,216,216,213,213,206},
				 {208,211,211,214,215,214,211,211,208},
				 {208, 212, 212, 214, 215, 214, 212, 212, 208},
				 {204, 209, 204, 212, 214, 212, 204, 209, 204},
				 {198, 208, 204, 212, 212, 212, 204, 208, 198},
				 {200, 208, 206, 212, 200, 212, 206, 208, 200},
				 {194, 206, 204, 212, 200, 212, 204, 206, 194}
		 };
		 
		 int[][] ma={
		     		{90, 90, 90, 96, 90, 96, 90, 90, 90},
		    		{90, 96,103, 97, 94, 97,103, 96, 90},
		    		{92, 98, 99,103, 99,103, 99, 98, 92},
		    		{93,108,100,107,100,107,100,108, 93},
		    		{90,100, 99,103,104,103, 99,100, 90},
		    		
		    		{90, 98,101,102,103,102,101, 98, 90},
		    		{92, 94, 98, 95, 98, 95, 98, 94, 92},
		    		{93, 92, 94, 95, 92, 95, 94, 92, 93},
		    		{85, 90, 92, 93, 78, 93, 92, 90, 85},
		    		{88, 85, 90, 88, 90, 88, 90, 85, 88}
		 };
		 
		 int[][] xiang={
		        		{0, 0,20, 0, 0, 0,20, 0, 0},
		        		{0, 0, 0, 0, 0, 0, 0, 0, 0},
		        		{18, 0, 0, 0,23, 0, 0, 0, 18},
		        		{0, 0, 0, 0, 0, 0, 0, 0, 0},
		        		{0, 0,20, 0, 0, 0,20, 0, 0},
		        		
		        		{0, 0,20, 0, 0, 0,20, 0, 0},
		        		{0, 0, 0, 0, 0, 0, 0, 0, 0},
		        		{18,0, 0, 0,23, 0, 0, 0,18},
		        		{0, 0, 0, 0, 0, 0, 0, 0, 0}, 
		        		{0, 0,20, 0, 0, 0,20, 0, 0}
		 };
		 
		 int[][] shi={
		        		{0, 0, 0,20, 0,20, 0, 0, 0},
		        		{0, 0, 0, 0,23, 0, 0, 0, 0},
		        		{0, 0, 0,20, 0,20, 0, 0, 0},
		        		{0, 0, 0, 0, 0, 0, 0, 0, 0},
		        		{0, 0, 0, 0, 0, 0, 0, 0, 0},
		        		
		        		{0, 0, 0, 0, 0, 0, 0, 0, 0},
		        		{0, 0, 0, 0, 0, 0, 0, 0, 0},
		        		{0, 0, 0,20, 0,20, 0, 0, 0},
		        		{0, 0, 0, 0,23, 0, 0, 0, 0}, 
		        		{0, 0, 0,20, 0,20, 0, 0, 0}
		 };
		 
		 int[][] jiang={
		          		{0, 0, 0, 8888, 8888, 8888, 0, 0, 0},
		        		{0, 0, 0, 8888, 8888, 8888, 0, 0, 0}, 
		        		{0, 0, 0, 8888, 8888, 8888, 0, 0, 0},
		        		{0, 0, 0, 0, 0, 0, 0, 0, 0},
		        		{0, 0, 0, 0, 0, 0, 0, 0, 0},
		 
		        		{0, 0, 0, 0, 0, 0, 0, 0, 0},
		        		{0, 0, 0, 0, 0, 0, 0, 0, 0},
		        		{0, 0, 0, 8888, 8888, 8888, 0, 0, 0},
		        		{0, 0, 0, 8888, 8888, 8888, 0, 0, 0}, 
		        		{0, 0, 0, 8888, 8888, 8888, 0, 0, 0}
		 };
		 
		 int[][] pao={
		        		{100, 100,  96, 91,  90, 91,  96, 100, 100},
		        		{98,  98,  96, 92,  89, 92,  96,  98,  98},
		        		{97,  97,  96, 91,  92, 91,  96,  97,  97},
						{96,  99,  99, 98, 100, 98,  99,  99,  96},
		        		 {96,  96,  96, 96, 100, 96,  96,  96,  96}, 
		        		
		        		{95,  96,  99, 96, 100, 96,  99,  96,  95},
		        		 {96,  96,  96, 96,  96, 96,  96,  96,  96},
		        		{97,  96, 100, 99, 101, 99, 100,  96,  97},
		        		{96,  97,  98, 98,  98, 98,  98,  97,  96},
		        		{96,  96,  97, 99,  99, 99,  97,  96,  96}
		 };
		 
		 int[][] bing={
				    {9,  9,  9, 11, 13, 11,  9,  9,  9},
		    		{19, 24, 34, 42, 44, 42, 34, 24, 19},
		    		{19, 24, 32, 37, 37, 37, 32, 24, 19},
		    		{19, 23, 27, 29, 30, 29, 27, 23, 19},
		    		{14, 18, 20, 27, 29, 27, 20, 18, 14},
		    		
					{7,  0, 13,  0, 16,  0, 13,  0,  7},
		    		{7,  0,  7,  0, 15,  0,  7,  0,  7}, 
		    		{0,  0,  0,  0,  0,  0,  0,  0,  0},
		    		{0,  0,  0,  0,  0,  0,  0,  0,  0},
		    		{0,  0,  0,  0,  0,  0,  0,  0,  0}
		 };
		 
		 int[][] kong={{},{},{},{},{},{},{},{},{},{}};
		 this.uchhessValues[0]=kong;
		 this.uchhessValues[1]=ju;
		 this.uchhessValues[2]=ma;
		 this.uchhessValues[3]=xiang;
		 this.uchhessValues[4]=shi;
		 this.uchhessValues[5]=jiang;
		 this.uchhessValues[6]=pao;
		 this.uchhessValues[7]=bing;
	}
	
	public int[][] work() {
		List<List<Integer>> FirstMove=AllMove(data, true);
		int[][] subData=BackData(data);
		this.score=alphabeta(Integer.MIN_VALUE, Integer.MAX_VALUE, 0, data);
		if(Goi==-1){//找一个临时的方案
			for(int i=0;i<FirstMove.get(0).size();i+=2){
				int[][] temp=BackData(subData);
				temp[FirstMove.get(0).get(i+1)][FirstMove.get(1).get(i+1)]=
						temp[FirstMove.get(0).get(i)][FirstMove.get(1).get(i)];
				temp[FirstMove.get(0).get(i)][FirstMove.get(1).get(i)]=0;
				if(!isJiang(temp)){
					Goi=i;
					return temp;
				}
			}
			if(Goi==-1){
				return null;
			}
		}
		subData[FirstMove.get(0).get(Goi+1)][FirstMove.get(1).get(Goi+1)]=
				subData[FirstMove.get(0).get(Goi)][FirstMove.get(1).get(Goi)];
		subData[FirstMove.get(0).get(Goi)][FirstMove.get(1).get(Goi)]=0;
		return subData;
	}
	
	public boolean isJiang(int[][] subuc) {
		//看是否被车将军
		int jx = -1;
		int jy = -1;
		for(int i=0;i<rows;i++){
			for(int j=0;j<cols;j++){
				if(subuc[i][j]==12){
					jx=i;
					jy=j;
				}
			}
		}
		if(jx==-1)
			return true;
		//找到将军的位置
		//横着看是否有对方的车
		for(int h=jy-1;h>=0;h--){
			if(subuc[jx][h]==0)
				continue;
			else if(subuc[jx][h]==1){
				return true;
			}else {
				break;
			}
		}
		for(int h=jy+1;h<cols;h++){
			if(subuc[jx][h]==0)
				continue;
			else if(subuc[jx][h]==1)
				return true;
			else {
				break;
			}
		}
		//竖着看
		if(jx-1>=0){
			for(int h=jx-1;h>=0;h--){
				if(subuc[h][jy]==0)
					continue;
				else if(subuc[h][jy]==1)
					return true;
				else {
					break;
				}
			}
		}
		for(int h=jx+1;h<rows;h++){
			if(subuc[h][jy]==0)
				continue;
			else if(subuc[h][jy]==1)
				return true;
			else {
				break;
			}
		}
		
		//看是否被炮将军
		for(int h=jy-1;h>=0;h--){
			if(subuc[jx][h]==0)
				continue;
			else{
				//隔一个子了
				if(h-1>=0){
					for(int k=h-1;k>=0;k--){
						if(subuc[jx][k]==0)
							continue;
						else if(subuc[jx][k]==6)
							return true;
						else {
							break;
						}
					}
				}
				break;
			}
		}
		for(int h=jy+1;h<cols;h++){
			if(subuc[jx][h]==0)
				continue;
			else{
				if(h+1<cols){
					for(int k=h+1;k<cols;k++){
						if(subuc[jx][k]==0)
							continue;
						else if(subuc[jx][k]==6)
							return true;
						else {
							break;
						}
					}
				}
				break;
			}
		}
		if(jx-1>=0){
			for(int h=jx-1;h>=0;h--){
				if(subuc[h][jy]==0)
					continue;
				else {
					if(h-1>=0){
						for(int k=h-1;k>=0;k--){
							if(subuc[k][jy]==0)
								continue;
							else if(subuc[k][jy]==6)
								return true;
							else {
								break;
							}
						}
					}
					break;
				}
			}
		}
			for(int h=jx+1;h<rows;h++){
				if(subuc[h][jy]==0)
					continue;
				else{
					if(h+1<rows){
						for(int k=h+1;k<rows;k++){
							if(subuc[k][jy]==0)
								continue;
							else if(subuc[k][jy]==6)
								return true;
							else {
								break;
							}
						}
					}
					break;
				}
			}
			
			//看是否被马将军
			if(jx-1>=0 && subuc[jx-1][jy+1]==0){
				if((jx-2>=0 && subuc[jx-2][jy+1]==2) || subuc[jx-1][jy+2]==2){
					return true;
				}
			}
		
			if(jx-1>=0 && subuc[jx-1][jy-1]==0){
				if((jx-2>=0 && subuc[jx-2][jy-1]==2) || subuc[jx-1][jy-2]==2){
					return true;
				}
			}
			
			if(subuc[jx+1][jy+1]==0){
				if(subuc[jx+2][jy+1]==2 || subuc[jx+1][jy+2]==2){
					return true;
				}
			}
			
			if(subuc[jx+1][jy-1]==0){
				if(subuc[jx+2][jy-1]==2 || subuc[jx+1][jy-2]==2){
					return true;
				}
			}
			
			//看是否被对面的帅将军
			for(int h=jx+1;h<rows;h++){
				if(subuc[h][jy]==0)
					continue;
				else if(subuc[h][jy]==5)
					return true;
				else {
					break;
				}
			}
			
			//看是否被对面的兵将军
			if(subuc[jx+1][jy]==7 || subuc[jx][jy-1]==7 || subuc[jx][jy+1]==7)
				return true;
			
			return false;
		}
	
	public boolean isShuai(int[][] subuc) {
		//看是否被车将军
		int jx = -1;
		int jy = -1;
		for(int i=0;i<rows;i++){
			for(int j=0;j<cols;j++){
				if(subuc[i][j]==5){
					jx=i;
					jy=j;
				}
			}
		}
		if(jx==-1)
			return true;
		//找到将军的位置
		//横着看是否有对方的车
		for(int h=jy-1;h>=0;h--){
			if(subuc[jx][h]==0)
				continue;
			else if(subuc[jx][h]==8){
				return true;
			}else {
				break;
			}
		}
		for(int h=jy+1;h<cols;h++){
			if(subuc[jx][h]==0)
				continue;
			else if(subuc[jx][h]==8)
				return true;
			else {
				break;
			}
		}
		//竖着看
			for(int h=jx-1;h>=0;h--){
				if(subuc[h][jy]==0)
					continue;
				else if(subuc[h][jy]==8)
					return true;
				else {
					break;
				}
			}
		
		if(jx+1<rows){
		for(int h=jx+1;h<rows;h++){
			if(subuc[h][jy]==0)
				continue;
			else if(subuc[h][jy]==8)
				return true;
			else {
				break;
			}
		}
		}
		
		//看是否被炮将军
		for(int h=jy-1;h>=0;h--){
			if(subuc[jx][h]==0)
				continue;
			else{
				//隔一个子了
				if(h-1>=0){
					for(int k=h-1;k>=0;k--){
						if(subuc[jx][k]==0)
							continue;
						else if(subuc[jx][k]==13)
							return true;
						else {
							break;
						}
					}
				}
				break;
			}
		}
		for(int h=jy+1;h<cols;h++){
			if(subuc[jx][h]==0)
				continue;
			else{
				if(h+1<cols){
					for(int k=h+1;k<cols;k++){
						if(subuc[jx][k]==0)
							continue;
						else if(subuc[jx][k]==13)
							return true;
						else {
							break;
						}
					}
				}
				break;
			}
		}

			for(int h=jx-1;h>=0;h--){
				if(subuc[h][jy]==0)
					continue;
				else {
					if(h-1>=0){
						for(int k=h-1;k>=0;k--){
							if(subuc[k][jy]==0)
								continue;
							else if(subuc[k][jy]==13)
								return true;
							else {
								break;
							}
						}
					}
					break;
				}
			}
		
			if(jx+1<rows){
			for(int h=jx+1;h<rows;h++){
				if(subuc[h][jy]==0)
					continue;
				else{
					if(h+1<rows){
						for(int k=h+1;k<rows;k++){
							if(subuc[k][jy]==0)
								continue;
							else if(subuc[k][jy]==13)
								return true;
							else {
								break;
							}
						}
					}
					break;
				}
			}
			}
			
			//看是否被马将军
			if(subuc[jx-1][jy+1]==0){
				if(subuc[jx-2][jy+1]==9 || subuc[jx-1][jy+2]==9){
					return true;
				}
			}
		
			if(subuc[jx-1][jy-1]==0){
				if(subuc[jx-2][jy-1]==9 || subuc[jx-1][jy-2]==9){
					return true;
				}
			}
			
			if(jx+1<rows && subuc[jx+1][jy+1]==0){
				if((jx+2<rows && subuc[jx+2][jy+1]==9) || subuc[jx+1][jy+2]==9){
					return true;
				}
			}
			
			if(jx+1<rows && subuc[jx+1][jy-1]==0){
				if((jx+2<rows && subuc[jx+2][jy-1]==9) || subuc[jx+1][jy-2]==9){
					return true;
				}
			}//对方的马，这里犯错把9写成了2
			
			//看是否被对面的帅将军
			for(int h=jx-1;h>=0;h--){
				if(subuc[h][jy]==0)
					continue;
				else if(subuc[h][jy]==12)
					return true;
				else {
					break;
				}
			}
			
			//看是否被对面的兵将军
			if(subuc[jx-1][jy]==14 || subuc[jx][jy-1]==14 || subuc[jx][jy+1]==14)
				return true;
			
			return false;
	}
	
	//alphabeta剪枝函数
	public int alphabeta(int alpha,int beta,int depth,int[][] data) {
		if(depth==maxDepth)
			return evluate(data);
		//所有走法
		List<List<Integer>> tmp=AllMove(data, depth%2==0?true:false);
		int best=depth%2==0?-Integer.MIN_VALUE:Integer.MAX_VALUE;
		//选择其中一种走法
		for(int i=0;i<tmp.get(0).size();i+=2){
			int ox=tmp.get(0).get(i);
			int oy=tmp.get(1).get(i);
			int nx=tmp.get(0).get(i+1);
			int ny=tmp.get(1).get(i+1);
			int[][] sub=BackData(data);
			sub[ox][oy]=0;
			sub[nx][ny]=data[ox][oy];
			if(depth%2==0){
				if(isJiang(sub)){
					continue;
				}
			}else{
				if(isShuai(sub)){
					continue;
				}
			}
			int value=alphabeta(alpha, beta, depth+1, sub);
			if(depth%2==0){
				//max
				if(value>best){
					best=value;
					alpha=value;
					if(depth==0)
						Goi=i;
				}
			}else{
				//min
				if(value<best){
					best=value;
					beta=value;
				}
			}
			if(alpha>=beta)
				return best;
		}
		return best;
	}
	
	//深度复制函数
	public int[][] BackData(int[][] data) {
		int[][] sub=new int[data.length][data[0].length];
		for(int i=0;i<data.length;i++){
			for(int j=0;j<data[0].length;j++){
				sub[i][j]=data[i][j];//深度复制
			}
		}
		return sub;
	}
	
	public int evluate(int[][] sub) {
		//局面评价函数
		return chessValue(sub)-rawScore;
	}
	
	//产生所有走法的函数
	public List<List<Integer>> AllMove(int[][] data,boolean isAI) {
		List<List<Integer>> total=new ArrayList<>();
		total.add(new ArrayList<Integer>());
		total.add(new ArrayList<Integer>());
		for(int i=0;i<rows;i++){
			for(int j=0;j<cols;j++){
				if(isAI){
					if(data[i][j]>=8){
						List<List<Integer>> part=canMove(data, i, j, data[i][j]);
						total.get(0).addAll(part.get(0));
						total.get(1).addAll(part.get(1));
					}
				}else{
					if(data[i][j]>0 && data[i][j]<=7){
						List<List<Integer>> part=canMove(data, i, j, data[i][j]);
						total.get(0).addAll(part.get(0));
						total.get(1).addAll(part.get(1));
					}
				}
			}
		}
		return total;
	}
	
private List<List<Integer>> canMove(int[][] data,int i,int j,int label) {
	//发现可以移动的点
	java.util.List<Integer> listx=new ArrayList<>();
	java.util.List<Integer> listy=new ArrayList<>();
	switch (label) {
	case 8:
	case 1:
		//是车
		//观察横行左边可以移动最远处
		if(j-1>=0){
		for(int h=j-1;h>=0;h--){
				if(data[i][h]==0){
					listx.add(i);
					listy.add(j);
					listx.add(i);
					listy.add(h);
				}else if((label<=7 && data[i][h]>=8) || (label>=8 && data[i][h]<=7)){
					listx.add(i);
					listy.add(j);
					listx.add(i);
					listy.add(h);
					break;//吃子
				}else{
					//自己子
					break;
				}
			}
		}
		
		//观察横行右边可以移动的最远处
		if(j+1<cols){
			for(int h=j+1;h<cols;h++){
				if(data[i][h]==0){
					listx.add(i);
					listy.add(j);
					listx.add(i);
					listy.add(h);
				}else if((label<=7 && data[i][h]>=8) || (label>=8 && data[i][h]<=7)){
					listx.add(i);
					listy.add(j);
					listx.add(i);
					listy.add(h);
					break;
				}else {
					break;
				}
			}
		}

		//观察纵行上面最大
		if(i-1>=0){
			for(int k=i-1;k>=0;k--){
				if(data[k][j]==0){
					listx.add(i);
					listy.add(j);
					listx.add(k);
					listy.add(j);
				}else if((label<=7 && data[k][j]>=8) || (label>=8 && data[k][j]<=7)){
					listx.add(i);
					listy.add(j);
					listx.add(k);
					listy.add(j);
					break;
				}else {
					break;
				}
			}
		}

		//观察纵行下面最大
		if(i+1<rows){
			for(int k=i+1;k<rows;k++){
				if(data[k][j]==0){
					listx.add(i);
					listy.add(j);
					listx.add(k);
					listy.add(j);
				}else if((label<=7 && data[k][j]>=8) || (label>=8 && data[k][j]<=7)){
					listx.add(i);
					listy.add(j);
					listx.add(k);
					listy.add(j);
					break;
				}else {
					break;
				}
			}
		}
		break;
		
	case 6:
	case 13:
		//是炮
		if(j-1>=0){
			for(int h=j-1;h>=0;h--){
				if(data[i][h]==0){
					listx.add(i);
					listy.add(j);
					listx.add(i);
					listy.add(h);
				}else {
					if(h-1>=0){
						for(int k=h-1;k>=0;k--){
							if(data[i][k]==0){
								continue;
							}
							else if((label<=7 && data[i][k]>=8)||(label>=8 && data[i][k]<=7)){
								listx.add(i);
								listy.add(j);
								listx.add(i);
								listy.add(k);
								break;
							}else{
								break;
							}
						}
					}
					break;
				}
			}
		}
		
		if(j+1<cols){
			for(int h=j+1;h<cols;h++){
				if(data[i][h]==0){
					listx.add(i);
					listy.add(j);
					listx.add(i);
					listy.add(h);
				}else{
					if(h+1<cols){
						for(int k=h+1;k<cols;k++){
							if(data[i][k]==0)
								continue;
							else if((label<=7 && data[i][k]>=8)||(label>=8 && data[i][k]<=7)){
								listx.add(i);
								listy.add(j);
								listx.add(i);
								listy.add(k);
								break;
							}else {
								break;
							}
						}
					}
					break;
				}
			}
		}
		
		if(i-1>=0){
			for(int h=i-1;h>=0;h--){
				if(data[h][j]==0){
					listx.add(i);
					listy.add(j);
					listx.add(h);
					listy.add(j);
				}else{
					if(h-1>=0){
						for(int k=h-1;k>=0;k--){
							if(data[k][j]==0)
								continue;
							else if((label<=7 && data[k][j]>=8)||(label>=8 && data[k][j]<=7)){
								listx.add(i);
								listy.add(j);
								listx.add(k);
								listy.add(j);
								break;
							}else {
								break;
							}
						}
					}
					break;
				}
			}
		}
		
		if(i+1<rows){
			for(int h=i+1;h<rows;h++){
				if(data[h][j]==0){
					listx.add(i);
					listy.add(j);
					listx.add(h);
					listy.add(j);
				}else{
					if(h+1<cols){
						for(int k=h+1;k<rows;k++){
							if(data[k][j]==0)
								continue;
							else if((label<=7 && data[k][j]>=8)||(label>=8 && data[k][j]<=7)){
								listx.add(i);
								listy.add(j);
								listx.add(k);
								listy.add(j);
								break;
							}else {
								break;
							}
						}
					}
					break;
				}
			}
		}
		break;
		
	case 2:
	case 9:
		//是马
		//东北方向
		if(i-2>=0 && j+1<cols){
			if(data[i-1][j]==0){
				if(data[i-2][j+1]==0 || (label<=7 && data[i-2][j+1]>=8) || (label>=8 && data[i-2][j+1]<=7)){
					listx.add(i);
					listy.add(j);
					listx.add(i-2);
					listy.add(j+1);
				}
			}
		}
		//东北躺
		if(i-1>=0 && j+2<cols){
			if(data[i][j+1]==0){
				if(data[i-1][j+2]==0 || (label<=7 && data[i-1][j+2]>=8) || (label>=8 && data[i-1][j+2]<=7)){
					listx.add(i);
					listy.add(j);
					listx.add(i-1);
					listy.add(j+2);
				}
			}
		}
		//东南躺
		if(i+1<rows && j+2<cols){
			if(data[i][j+1]==0){
				if(data[i+1][j+2]==0 || (label<=7 && data[i+1][j+2]>=8) || (label>=8 && data[i+1][j+2]<=7)){
					listx.add(i);
					listy.add(j);
					listx.add(i+1);
					listy.add(j+2);
				}
			}
		}
		//东南跳
		if(i+2<rows && j+1<cols){
			if(data[i+1][j]==0){
				if(data[i+2][j+1]==0 || (label<=7 && data[i+2][j+1]>=8) || (label>=8 && data[i+2][j+1]<=7)){
					listx.add(i);
					listy.add(j);
					listx.add(i+2);
					listy.add(j+1);
				}
			}
		}
		//西南躺
		if(i+1<rows && j-2>=0){
			if(data[i][j-1]==0){
				if(data[i+1][j-2]==0 || (label<=7 && data[i+1][j-2]>=8) || (label>=8 && data[i+1][j-2]<=7)){
					listx.add(i);
					listy.add(j);
					listx.add(i+1);
					listy.add(j-2);
				}
			}
		}
		//西南跳
		if(i+2<rows && j-1>=0){
			if(data[i+1][j]==0){
				if(data[i+2][j-1]==0 || (label<=7 && data[i+2][j-1]>=8) || (label>=8 && data[i+2][j-1]<=7)){
					listx.add(i);
					listy.add(j);
					listx.add(i+2);
					listy.add(j-1);
				}
			}
		}
		//西北躺
		if(i-1>=0 && j-2>=0){
			if(data[i][j-1]==0){
				if(data[i-1][j-2]==0 || (label<=7 && data[i-1][j-2]>=8) || (label>=8 && data[i-1][j-2]<=7)){
					listx.add(i);
					listy.add(j);
					listx.add(i-1);
					listy.add(j-2);
				}
			}
		}
		//西北跳
		if(i-2>=0 && j-1>=0){
			if(data[i-1][j]==0){
				if(data[i-2][j-1]==0 || (label<=7 && data[i-2][j-1]>=8) || (label>=8 && data[i-2][j-1]<=7)){
					listx.add(i);
					listy.add(j);
					listx.add(i-2);
					listy.add(j-1);// 不同子
				}
			}
		}
		break;
		
	case 10:
		//像蓝
		//东北
		if(i-2>=0 && j+2<cols){
			if(data[i-1][j+1]==0){
				if(data[i-2][j+2]<=7){
					listx.add(i);
					listy.add(j);
					listx.add(i-2);
					listy.add(j+2);
				}
			}
		}
		//东南
		if(i+2<rows/2 && j+2<cols){
			if(data[i+1][j+1]==0){
				if(data[i+2][j+2]<=7){
					listx.add(i);
					listy.add(j);
					listx.add(i+2);
					listy.add(j+2);
				}
			}
		}
		//西南
		if(i+2<rows/2 && j-2>=0){
			if(data[i+1][j-1]==0){
				if(data[i+2][j-2]<=7){
					listx.add(i);
					listy.add(j);
					listx.add(i+2);
					listy.add(j-2);
				}
			}
		}
		//西北
		if(i-2>=0 && j-2>=0){
			if(data[i-1][j-1]==0){
				if(data[i-2][j-2]<=7){
					listx.add(i);
					listy.add(j);
					listx.add(i-2);
					listy.add(j-2);
				}
			}
		}
		break;
	case 3:
		//相
		//东北
		if(i-2>=rows/2 && j+2<cols){
			if(data[i-1][j+1]==0){
				if(data[i-2][j+2]==0 ||  data[i-2][j+2]>=8){
					listx.add(i);
					listy.add(j);
					listx.add(i-2);
					listy.add(j+2);
				}
			}
		}
		//东南
		if(i+2<rows && j+2<cols){
			if(data[i+1][j+1]==0){
				if(data[i+2][j+2]==0 || data[i+2][j+2]>=8){
					listx.add(i);
					listy.add(j);
					listx.add(i+2);
					listy.add(j+2);
				}
			}
		}
		//西南
		if(i+2<rows && j-2>=0){
			if(data[i+1][j-1]==0){
				if(data[i+2][j-2]==0 || data[i+2][j-2]>=8){
					listx.add(i);
					listy.add(j);
					listx.add(i+2);
					listy.add(j-2);
				}
			}
		}
		//西北
		if(i-2>=rows/2 && j-2>=0){
			if(data[i-1][j-1]==0){
				if(data[i-2][j-2]==0 || data[i-2][j-2]>=8){
					listx.add(i);
					listy.add(j);
					listx.add(i-2);
					listy.add(j-2);
				}
			}
		}
		break;
	case 4:
		//士
		//东北
		if(i-1>=7 && j+1<=5){
			if(data[i-1][j+1]==0 || data[i-1][j+1]>=8){
				listx.add(i);
				listy.add(j);
				listx.add(i-1);
				listy.add(j+1);
			}
		}
		//东南
		if(i+1<rows && j+1<=5){
			if(data[i-1][j+1]==0 || data[i-1][j+1]>=8){
				listx.add(i);
				listy.add(j);
				listx.add(i+1);
				listy.add(j+1);
			}
		}
		//西南
		if(i+1<rows && j-1>=3){
			if(data[i+1][j-1]==0 || data[i+1][j-1]>=8){
				listx.add(i);
				listy.add(j);
				listx.add(i+1);
				listy.add(j-1);
			}
		}
		//西北
		if(i-1>=7 && j-1>=3){
			if(data[i-1][j-1]==0 || data[i-1][j-1]>=8){
				listx.add(i);
				listy.add(j);
				listx.add(i-1);
				listy.add(j-1);
			}
		}
		break;
	case 11:
		//蓝色士
		//东北
		if(i-1>=0 && j+1<=5){
			if(data[i-1][j+1]<=7){
				listx.add(i);
				listy.add(j);
				listx.add(i-1);
				listy.add(j+1);
			}
		}
		//东南
		if(i+1<=2 && j+1<=5){
			if(data[i+1][j+1]<=7){
				listx.add(i);
				listy.add(j);
				listx.add(i+1);
				listy.add(j+1);
			}
		}
		//西南
		if(i+1<=2 && j-1>=3){
			if(data[i+1][j-1]<=7){
				listx.add(i);
				listy.add(j);
				listx.add(i+1);
				listy.add(j-1);
			}
		}
		//西北
		if(i-1>=0 && j-1>=3){
			if(data[i-1][j-1]<=7){
				listx.add(i);
				listy.add(j);
				listx.add(i-1);
				listy.add(j-1);
			}
		}
		break;
	case 5:
		//帅
		//上
		if(i-1<=7){
			if(data[i-1][j]==0 || data[i-1][j]>=8){
				listx.add(i);
				listy.add(j);
				listx.add(i-1);
				listy.add(j);
			}
		}
		//下
		if(i+1<rows){
			if(data[i+1][j]==0 || data[i+1][j]>=8){
				listx.add(i);
				listy.add(j);
				listx.add(i+1);
				listy.add(j);
			}
		}
		//左
		if(j-1>=3){
			if(data[i][j-1]==0 || data[i][j-1]>=8){
				listx.add(i);
				listy.add(j);
				listx.add(i);
				listy.add(j-1);
			}
		}
		//右
		if(j+1<=5){
			if(data[i][j+1]==0 || data[i][j+1]>=8){
				listx.add(i);
				listy.add(j);
				listx.add(i);
				listy.add(j+1);
			}
		}
		break;
	case 12:
		//将
		//上
		if(i-1>=0){
			if(data[i-1][j]<=7){
				listx.add(i);
				listy.add(j);
				listx.add(i-1);
				listy.add(j);
			}
		}
		//下
		if(i+1<=2){
			if(data[i+1][j]<=7){
				listx.add(i);
				listy.add(j);
				listx.add(i+1);
				listy.add(j);
			}
		}
		//左
		if(j-1>=3){
			if(data[i][j-1]<=7){
				listx.add(i);
				listy.add(j);
				listx.add(i);
				listy.add(j-1);
			}
		}
		//右
		if(j+1<=5){
			if(data[i][j+1]<=7){
				listx.add(i);
				listy.add(j);
				listx.add(i);
				listy.add(j+1);
			}
		}
		break;
	case 7:
		//兵
		if(i>4){
			//没有过河
			if(data[i-1][j]==0 || data[i-1][j]>=8){
				listx.add(i);
				listy.add(j);
				listx.add(i-1);
				listy.add(j);
			}
		}else{
			//上
			if(i-1>=0){
				if(data[i-1][j]==0 || data[i-1][j]>=8){
					listx.add(i);
					listy.add(j);
					listx.add(i-1);
					listy.add(j);
				}
			}
			//左
			if(j-1>=0){
				if(data[i][j-1]==0 || data[i][j-1]>=8){
					listx.add(i);
					listy.add(j);
					listx.add(i);
					listy.add(j-1);
				}
			}
			//右
			if(j+1<cols){
				if(data[i][j+1]==0 || data[i][j+1]>=8){
					listx.add(i);
					listy.add(j);
					listx.add(i);
					listy.add(j+1);
				}
			}
		}
		break;
	case 14:
		//卒
		if(i<5){
			//没有过河
			if(data[i+1][j]<=7){
				listx.add(i);
				listy.add(j);
				listx.add(i+1);
				listy.add(j);
			}
		}else{
			//上
			if(i+1<rows){
				if(data[i+1][j]<=7){
					listx.add(i);
					listy.add(j);
					listx.add(i+1);
					listy.add(j);
				}
			}
			//左
			if(j-1>=0){
				if(data[i][j-1]<=7){
					listx.add(i);
					listy.add(j);
					listx.add(i);
					listy.add(j-1);
				}
			}
			//右
			if(j+1<cols){
				if(data[i][j+1]<=7){
					listx.add(i);
					listy.add(j);
					listx.add(i);
					listy.add(j+1);
				}
			}
		}
		break;
	default:
		break;
	}
	List<List<Integer>> res=new ArrayList<>();
	res.add(listx);
	res.add(listy);
	return res;
  }
}
