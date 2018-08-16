package chess;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;

import javax.swing.JOptionPane;

/**
 * 
 * <p>Title: Chess.java</p>
 * <p>Description:
 *  开始算法象棋
 *  采用最大最小算法，深度为4
 *  定义车10,炮6,马5,兵2,象3,士2,将100 
 *  如何动态更改棋子的评估值，还有怎么才能让树达到更深的搜索，有待解决。。。
 * <p>@author dedong
 * <p>@date 2017/ 下午12:19:00
 *
 */
public class Chess {
	private int[][] data;
	private UChess root;//根节点
	private int toGo;
	private final int rows=10;
	private final int cols=9;
	private int[] ucvalue={10,5,3,2,100,8,2};
	private boolean isAI;
	
	public Chess() {
		// TODO Auto-generated constructor stub
	}
	
	public Chess(int[][] subData){
		data=BackData(subData);
	}//拷贝过来进行分析
	
	public int[][] compute(int depth) {
		//判断是否能够直接赢
		hasWin(data);
		boolean isCon=false;
		for(int i=7;i<=9;i++)
			for(int j=3;j<=5;j++)
				if(data[i][j]==5){
					isCon=true;
					break;
				}
		if(isCon){
			return new ReWrite(data,depth).work();
		}
		else{
			return data;
		}
	}
	
	public void hasWin(int[][] data) {
		for(int i=0;i<=9;i++){
			for(int j=0;j<=8;j++){
				int label=data[i][j];
				switch (label) {
				case 8:
					//是车
					for(int h=i+1;h<rows;h++){
						if(data[h][j]==0)
							continue;
						else if(data[h][j]!=5)
							break;
						else{
							//是帅
							data[i][j]=0;
							data[h][j]=label;
							break;
						}
					}
					for(int h=i-1;h>=0;h--){
						if(data[h][j]==0)
							continue;
						else if(data[h][j]!=5)
							break;
						else{
							data[i][j]=0;
							data[h][j]=label;
							break;
						}
					}
					for(int h=j-1;h>=0;h--){
						if(data[i][h]==0)
							continue;
						else if(data[i][h]!=5)
							break;
						else {
							data[i][j]=0;
							data[i][h]=label;
						}
					}
					for(int h=j+1;h<cols;h++){
						if(data[i][h]==0)
							continue;
						else if(data[i][h]!=5)//写错,误写成2
							break;
						else {
							data[i][j]=0;
							data[i][h]=label;
							break;
						}
					}
					break;
				case 13:
					//是炮
					for(int h=i+1;h<rows;h++){
						if(data[h][j]==0)
							continue;
						else{
							//隔一个子
							for(int k=h+1;k<rows;k++){
								if(data[k][j]==0)
									continue;
								else if(data[k][j]!=5)
									break;
								else {
									data[i][j]=0;
									data[k][j]=label;
									break;
								}
							}
							break;
						}
					}
					for(int h=i-1;h>=0;h--){
						if(data[h][j]==0)
							continue;
						else{
							for(int k=h-1;k>=0;k--){
								if(data[k][j]==0)
									continue;
								else if(data[k][j]!=5)
									break;
								else {
									data[i][j]=0;
									data[k][j]=label;
									break;
								}
							}
							break;
						}
					}
					for(int h=j-1;h>=0;h--){
						if(data[i][h]==0)
							continue;
						else{
							for(int k=h-1;k>=0;k--){
								if(data[i][k]==0)
									continue;
								else if(data[i][k]!=5)
									break;
								else {
									data[i][j]=0;
									data[i][k]=label;
									break;
								}
							}
							break;
						}
					}
					for(int h=j+1;h<cols;h++){
						if(data[i][h]==0)
							continue;
						else{
							for(int k=h+1;k<cols;k++){
								if(data[i][k]==0)
									continue;
								else if(data[i][k]!=5)
									break;
								else {
									data[i][j]=0;
									data[i][k]=label;
									break;
								}
							}
							break;
						}
					}
					break;
				case 9:
					//马
					if(i-1>=0 && data[i-1][j]==0){
						if(i-2>=0 && j+1<cols && data[i-2][j+1]==5){
							data[i][j]=0;
							data[i-2][j+1]=label;
						}else if(i-2>=0 && j-1>=0 && data[i-2][j-1]==5){
							data[i][j]=0;
							data[i-2][j-1]=label;
						}
					}
					if(j+1<cols && data[i][j+1]==0){
						if(i-1>=0 && j+2<cols && data[i-1][j+2]==5){
							data[i][j]=0;
							data[i-1][j+2]=label;
						}else if(i+1<rows && j+2<cols && data[i+1][j+2]==5){
							data[i][j]=0;
							data[i+1][j+2]=label;
						}
					}
					if(i+1<rows && data[i+1][j]==0){
						if(i+2<rows && j+1<cols && data[i+2][j+1]==5){
							data[i][j]=0;
							data[i+2][j+1]=label;
						}else if(i+2<rows && j-1>=0 && data[i+2][j-1]==5){
							data[i][j]=0;
							data[i+2][j-1]=label;
						}
					}
					if(j-1>=0 && data[i][j-1]==0){
						if(i-1>=0 && j-2>=0 && data[i-1][j-2]==5){
							data[i][j]=0;
							data[i-1][j-2]=label;
						}else if(i+1<rows && j-2>=0 && data[i+1][j-2]==5){
							data[i][j]=0;
							data[i+1][j-2]=label;
						}
					}
					break;
				case 12:
					//将
					for(int h=i+1;h<rows;h++){
						if(data[h][j]==0)
							continue;
						else if(data[h][j]!=5)
							break;
						else {
							data[i][j]=0;
							data[h][j]=label;
							break;
						}
					}
					break;
				case 14:
					//卒
					if(i+1<rows && data[i+1][j]==5){
						data[i][j]=0;
						data[i+1][j]=label;
					}else if(j-1>=0 && data[i][j-1]==5){
						data[i][j]=0;
						data[i][j-1]=label;
					}else if(j+1<cols && data[i][j+1]==5){
						data[i][j]=0;
						data[i][j+1]=label;
					}
					break;
				default:
					break;
				}
			}
		}
	}
	
	//该函数需要重写
	public void BestMove() {
		//假设ai执行黑棋,且轮到ai行动,构建树
		root=new UChess();
		root.depth=0;
		root.innerData=BackData(data);
		//遍历地图
		Queue<UChess> all=new LinkedList<Chess.UChess>();
		int count=0;
		all.offer(root);
		while(!all.isEmpty()){
			System.out.println(++count);
			UChess uc=all.poll();
//			Move(uc, uc.depth, (uc.depth%2==0?true:false));
			for(int i=0;i<uc.chidern.size();i++){
				all.offer(uc.chidern.get(i));
				}
			}
		if(count>=200000)
			return;
		System.exit(1);
		complishSelf(root);
		writeValue(root, root.chidern);
	}
	
	//用递归进行赋值，如果到达底层的话，那么进行赋值，否则进行alphabeta判断是否剪枝
	
	
//	public void Move(UChess uc,int depth,boolean AI) {
//		//给定一个状态下的棋谱开始运行,是否轮到ai行动
//		//4层用list保存
//		isAI=AI;
//		if(depth==maxDepth){
//			//最后一层
//			uc.currentValue=evalute(uc);
//			return;
//		}
//		ArrayList<UChess> Cthis=new ArrayList<>();
//		if(AI){
//			int[][] pos=findPos(uc.innerData, 8);//找车的位置
//			if(pos[0][0]>=0){
//				//移动第一个车的可能位置
//				int[][] canPos=canMove(uc.innerData, pos[0][0], pos[0][1], 8);
//				arrange(canPos, uc, Cthis, depth, pos[0][0], pos[0][1], 8);
//			}
//			if(pos[1][0]>=0){
//				int[][] canPos=canMove(uc.innerData, pos[1][0], pos[1][1], 8);
//				arrange(canPos, uc, Cthis, depth, pos[1][0], pos[1][1], 8);
//			}
//			
//			int[][] posMa=findPos(uc.innerData, 9);//找马的位置
//			if(posMa[0][0]>=0){
//				//移动第一个马的可能位置
//				int[][] canPos=canMove(uc.innerData, posMa[0][0], posMa[0][1], 9);
//				arrange(canPos, uc, Cthis, depth, posMa[0][0], posMa[0][1], 9);
//			}
//			if(posMa[1][0]>=0){
//				int[][] canPos=canMove(uc.innerData, posMa[1][0], posMa[1][1], 9);
//				arrange(canPos, uc, Cthis, depth, posMa[1][0], posMa[1][1], 9);
//			}
//			
//			int[][] posXi=findPos(uc.innerData, 10);
//			if(posXi[0][0]>=0){
//				//移动第一个车的可能位置
//				int[][] canPos=canMove(uc.innerData, posXi[0][0], posXi[0][1], 10);
//				arrange(canPos, uc, Cthis, depth, posXi[0][0], posXi[0][1], 10);
//			}
//			if(posXi[1][0]>=0){
//				int[][] canPos=canMove(uc.innerData, posXi[1][0], posXi[1][1], 10);
//				arrange(canPos, uc, Cthis, depth, posXi[1][0], posXi[1][1], 10);
//			}
//			
//			int[][] posSh=findPos(uc.innerData, 11);
//			if(posSh[0][0]>=0){
//				//移动第一个车的可能位置
//				int[][] canPos=canMove(uc.innerData, posSh[0][0], posSh[0][1], 11);
//				arrange(canPos, uc, Cthis, depth, posSh[0][0], posSh[0][1], 11);
//			}
//			if(posSh[1][0]>=0){
//				int[][] canPos=canMove(uc.innerData, posSh[1][0], posSh[1][1], 11);
//				arrange(canPos, uc, Cthis, depth, posSh[1][0], posSh[1][1], 11);
//			}
//			
//			int[][] posJi=findPos(uc.innerData, 12);
//			if(posJi[0][0]>=0){
//				int[][] canPos=canMove(uc.innerData, posJi[0][0], posJi[0][1], 12);
//				arrange(canPos, uc, Cthis, depth, posJi[0][0], posJi[0][1], 12);
//			}
//			
//			int[][] posPa=findPos(uc.innerData, 13);
//			if(posPa[0][0]>=0){
//				//移动第一个炮的可能位置
//				int[][] canPos=canMove(uc.innerData, posPa[0][0], posPa[0][1], 13);
//				arrange(canPos, uc, Cthis, depth, posPa[0][0], posPa[0][1], 13);
//			}
//			if(posPa[1][0]>=0){
//				int[][] canPos=canMove(uc.innerData, posPa[1][0], posPa[1][1], 13);
//				arrange(canPos, uc, Cthis, depth, posPa[1][0], posPa[1][1], 13);
//			}
//			
//			int[][] posBi=findPos(uc.innerData, 14);
//			if(posBi[0][0]>=0){
//				int[][] canPos=canMove(uc.innerData, posBi[0][0], posBi[0][1], 14);
//				arrange(canPos, uc, Cthis, depth, posBi[0][0], posBi[0][1], 14);
//			}
//			if(posBi[1][0]>=0){
//				int[][] canPos=canMove(uc.innerData, posBi[1][0], posBi[1][1], 14);
//				arrange(canPos, uc, Cthis, depth, posBi[1][0], posBi[1][1], 14);
//			}
//			if(posBi[2][0]>=0){
//				int[][] canPos=canMove(uc.innerData, posBi[2][0], posBi[2][1], 14);
//				arrange(canPos, uc, Cthis, depth, posBi[2][0], posBi[2][1], 14);
//			}
//			if(posBi[3][0]>=0){
//				int[][] canPos=canMove(uc.innerData, posBi[3][0], posBi[3][1], 14);
//				arrange(canPos, uc, Cthis, depth, posBi[3][0], posBi[3][1], 14);
//			}
//			if(posBi[4][0]>=0){
//				int[][] canPos=canMove(uc.innerData, posBi[4][0], posBi[4][1], 14);
//				arrange(canPos, uc, Cthis, depth, posBi[4][0], posBi[4][1], 14);
//			}
//		}else{
//			int[][] pos=findPos(uc.innerData, 1);//找车的位置
//			if(pos[0][0]>=0){
//				//移动第一个车的可能位置
//				int[][] canPos=canMove(uc.innerData, pos[0][0], pos[0][1], 1);
//				arrange(canPos, uc, Cthis, depth, pos[0][0], pos[0][1], 1);
//			}
//			if(pos[1][0]>=0){
//				int[][] canPos=canMove(uc.innerData, pos[1][0], pos[1][1], 1);
//				arrange(canPos, uc, Cthis, depth, pos[1][0], pos[1][1], 1);
//			}
//			
//			int[][] posMa=findPos(uc.innerData, 2);//找马的位置
//			if(posMa[0][0]>=0){
//				//移动第一个马的可能位置
//				int[][] canPos=canMove(uc.innerData, posMa[0][0], posMa[0][1], 2);
//				arrange(canPos, uc, Cthis, depth, posMa[0][0], posMa[0][1], 2);
//			}
//			if(posMa[1][0]>=0){
//				int[][] canPos=canMove(uc.innerData, posMa[1][0], posMa[1][1], 2);
//				arrange(canPos, uc, Cthis, depth, posMa[1][0], posMa[1][1], 2);
//			}
//			
//			int[][] posXi=findPos(uc.innerData, 3);
//			if(posXi[0][0]>=0){
//				//移动第一个车的可能位置
//				int[][] canPos=canMove(uc.innerData, posXi[0][0], posXi[0][1], 3);
//				arrange(canPos, uc, Cthis, depth, posXi[0][0], posXi[0][1], 3);
//			}
//			if(posXi[1][0]>=0){
//				int[][] canPos=canMove(uc.innerData, posXi[1][0], posXi[1][1], 3);
//				arrange(canPos, uc, Cthis, depth, posXi[1][0], posXi[1][1], 3);
//			}
//			
//			int[][] posSh=findPos(uc.innerData, 4);
//			if(posSh[0][0]>=0){
//				//移动第一个车的可能位置
//				int[][] canPos=canMove(uc.innerData, posSh[0][0], posSh[0][1], 4);
//				arrange(canPos, uc, Cthis, depth, posSh[0][0], posSh[0][1],4);
//			}
//			if(posSh[1][0]>=0){
//				int[][] canPos=canMove(uc.innerData, posSh[1][0], posSh[1][1], 4);
//				arrange(canPos, uc, Cthis, depth, posSh[1][0], posSh[1][1], 4);
//			}
//			
//			int[][] posSu=findPos(uc.innerData, 5);
//			if(posSu[0][0]>=0){
//				int[][] canPos=canMove(uc.innerData, posSu[0][0], posSu[0][1], 5);
//				arrange(canPos, uc, Cthis, depth, posSu[0][0], posSu[0][1], 5);
//			}
//			
//			int[][] posPa=findPos(uc.innerData, 6);
//			if(posPa[0][0]>=0){
//				//移动第一个车的可能位置
//				int[][] canPos=canMove(uc.innerData, posPa[0][0], posPa[0][1], 6);
//				arrange(canPos, uc, Cthis, depth, posPa[0][0], posPa[0][1], 6);
//			}
//			if(posPa[1][0]>=0){
//				int[][] canPos=canMove(uc.innerData, posPa[1][0], posPa[1][1], 6);
//				arrange(canPos, uc, Cthis, depth, posPa[1][0], posPa[1][1], 6);
//			}
//			
//			int[][] posBi=findPos(uc.innerData, 7);
//			if(posBi[0][0]>=0){
//				int[][] canPos=canMove(uc.innerData, posBi[0][0], posBi[0][1], 7);
//				arrange(canPos, uc, Cthis, depth, posBi[0][0], posBi[0][1], 7);
//			}
//			if(posBi[1][0]>=0){
//				int[][] canPos=canMove(uc.innerData, posBi[1][0], posBi[1][1], 7);
//				arrange(canPos, uc, Cthis, depth, posBi[1][0], posBi[1][1], 7);
//			}
//			if(posBi[2][0]>=0){
//				int[][] canPos=canMove(uc.innerData, posBi[2][0], posBi[2][1], 7);
//				arrange(canPos, uc, Cthis, depth, posBi[2][0], posBi[2][1], 7);
//			}
//			if(posBi[3][0]>=0){
//				int[][] canPos=canMove(uc.innerData, posBi[3][0], posBi[3][1], 7);
//				arrange(canPos, uc, Cthis, depth, posBi[3][0], posBi[3][1], 7);
//			}
//			if(posBi[4][0]>=0){
//				int[][] canPos=canMove(uc.innerData, posBi[4][0], posBi[4][1], 7);
//				arrange(canPos, uc, Cthis, depth, posBi[4][0], posBi[4][1], 7);
//			}
//		}
//		if(Cthis.size()>0)
//			uc.chidern=Cthis;
//		else{
//			if(uc.depth%2==0){
//				uc.currentValue=-1000;
//			}else{
//				uc.currentValue=1000;
//			}
//		}
//	}
	
	public void arrange(int[][] canPos,UChess uc,ArrayList<UChess> Cthis,int depth,int ir,int jc,int label) {
		for(int i=0;i<canPos.length;i++){
			int[][] subData=BackData(uc.innerData);
			subData[ir][jc]=0;
			subData[canPos[i][0]][canPos[i][1]]=label;
			if((isJiang(subData) && isAI) || (isShuai(subData) && !isAI))//存在问题
				continue;//必应步
			UChess subuc=new UChess();
			subuc.parent=uc;
			subuc.depth=uc.depth+1;
			subuc.innerData=subData;
			Cthis.add(subuc);
		}
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
				if(subuc[jx-2][jy+1]==2 || subuc[jx-1][jy+2]==2){
					return true;
				}
			}
		
			if(subuc[jx-1][jy-1]==0){
				if(subuc[jx-2][jy-1]==2 || subuc[jx-1][jy-2]==2){
					return true;
				}
			}
			
			if(jx+1<rows && subuc[jx+1][jy+1]==0){
				if((jx+2<rows && subuc[jx+2][jy+1]==2) || subuc[jx+1][jy+2]==2){
					return true;
				}
			}
			
			if(jx+1<rows && subuc[jx+1][jy-1]==0){
				if((jx+2<rows && subuc[jx+2][jy-1]==2) || subuc[jx+1][jy-2]==2){
					return true;
				}
			}
			
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
	
	public int evalute(UChess uc) {
		//相对于ai来说的评估函数
		int[] init=new int[15];
		int[] now=new int[15];
		for(int i=0;i<rows;i++){
			for(int j=0;j<cols;j++){
				init[data[i][j]]++;
			}
		}
		for(int i=0;i<rows;i++){
			for(int j=0;j<cols;j++){
				now[uc.innerData[i][j]]++;
			}
		}
		
		int value=ucvalue[0]*(now[8]-init[8])+ucvalue[1]*(now[9]-init[9])+ucvalue[2]*(now[10]-init[10])+ucvalue[3]*(now[11]-init[11])+
				ucvalue[4]*(now[12]-init[12])
				+ucvalue[5]*(now[13]-init[13])+ucvalue[6]*(now[14]-init[14])-ucvalue[0]*(now[1]-init[1])-ucvalue[1]*(now[2]-init[2])-
				ucvalue[2]*(now[3]-init[3])-ucvalue[3]*(now[4]-init[4])
				-ucvalue[4]*(now[5]-init[5])-ucvalue[5]*(now[6]-init[6])-ucvalue[6]*(now[7]-init[7]);
		return value;
	}
	
	private int[][] findPos(int[][] data,int label) {
		int[][] pos=new int[5][2];
		for(int i=0;i<pos.length;i++)
			pos[i][0]=-1;
		int more=0;
		for(int i=0;i<rows;i++){
			for(int j=0;j<cols;j++){
				if(data[i][j]==label){
					pos[more][0]=i;
					pos[more++][1]=j;
				}
			}
		}
		return pos;
	}
	
	private int[][] canMove(int[][] data,int i,int j,int label) {
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
						listy.add(h);
					}else if((label<=7 && data[i][h]>=8) || (label>=8 && data[i][h]<=7)){
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
						listy.add(h);
					}else if((label<=7 && data[i][h]>=8) || (label>=8 && data[i][h]<=7)){
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
						listx.add(k);
						listy.add(j);
					}else if((label<=7 && data[k][j]>=8) || (label>=8 && data[k][j]<=7)){
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
						listx.add(k);
						listy.add(j);
					}else if((label<=7 && data[k][j]>=8) || (label>=8 && data[k][j]<=7)){
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
						listy.add(h);
					}else {
						if(h-1>=0){
							for(int k=h-1;k>=0;k--){
								if(data[i][k]==0){
									continue;
								}
								else if((label<=7 && data[i][k]>=8)||(label>=8 && data[i][k]<=7)){
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
						listy.add(h);
					}else{
						if(h+1<cols){
							for(int k=h+1;k<cols;k++){
								if(data[i][k]==0)
									continue;
								else if((label<=7 && data[i][k]>=8)||(label>=8 && data[i][k]<=7)){
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
						listx.add(h);
						listy.add(j);
					}else{
						if(h-1>=0){
							for(int k=h-1;k>=0;k--){
								if(data[k][j]==0)
									continue;
								else if((label<=7 && data[k][j]>=8)||(label>=8 && data[k][j]<=7)){
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
						listx.add(h);
						listy.add(j);
					}else{
						if(h+1<cols){
							for(int k=h+1;k<rows;k++){
								if(data[k][j]==0)
									continue;
								else if((label<=7 && data[k][j]>=8)||(label>=8 && data[k][j]<=7)){
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
						listx.add(i-2);
						listy.add(j+1);
					}
				}
			}
			//东北躺
			if(i-1>=0 && j+2<cols){
				if(data[i][j+1]==0){
					if(data[i-1][j+2]==0 || (label<=7 && data[i-1][j+2]>=8) || (label>=8 && data[i-1][j+2]<=7)){
						listx.add(i-1);
						listy.add(j+2);
					}
				}
			}
			//东南躺
			if(i+1<rows && j+2<cols){
				if(data[i][j+1]==0){
					if(data[i+1][j+2]==0 || (label<=7 && data[i+1][j+2]>=8) || (label>=8 && data[i+1][j+2]<=7)){
						listx.add(i+1);
						listy.add(j+2);
					}
				}
			}
			//东南跳
			if(i+2<rows && j+1<cols){
				if(data[i+1][j]==0){
					if(data[i+2][j+1]==0 || (label<=7 && data[i+2][j+1]>=8) || (label>=8 && data[i+2][j+1]<=7)){
						listx.add(i+2);
						listy.add(j+1);
					}
				}
			}
			//西南躺
			if(i+1<rows && j-2>=0){
				if(data[i][j-1]==0){
					if(data[i+1][j-2]==0 || (label<=7 && data[i+1][j-2]>=8) || (label>=8 && data[i+1][j-2]<=7)){
						listx.add(i+1);
						listy.add(j-2);
					}
				}
			}
			//西南跳
			if(i+2<rows && j-1>=0){
				if(data[i+1][j]==0){
					if(data[i+2][j-1]==0 || (label<=7 && data[i+2][j-1]>=8) || (label>=8 && data[i+2][j-1]<=7)){
						listx.add(i+2);
						listy.add(j-1);
					}
				}
			}
			//西北躺
			if(i-1>=0 && j-2>=0){
				if(data[i][j-1]==0){
					if(data[i-1][j-2]==0 || (label<=7 && data[i-1][j-2]>=8) || (label>=8 && data[i-1][j-2]<=7)){
						listx.add(i-1);
						listy.add(j-2);
					}
				}
			}
			//西北跳
			if(i-2>=0 && j-1>=0){
				if(data[i-1][j]==0){
					if(data[i-2][j-1]==0 || (label<=7 && data[i-2][j-1]>=8) || (label>=8 && data[i-2][j-1]<=7)){
						listx.add(i-2);
						listy.add(j-1);//不同子
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
						listx.add(i-2);
						listy.add(j+2);
					}
				}
			}
			//东南
			if(i+2<rows/2 && j+2<cols){
				if(data[i+1][j+1]==0){
					if(data[i+2][j+2]<=7){
						listx.add(i+2);
						listy.add(j+2);
					}
				}
			}
			//西南
			if(i+2<rows/2 && j-2>=0){
				if(data[i+1][j-1]==0){
					if(data[i+2][j-2]<=7){
						listx.add(i+2);
						listy.add(j-2);
					}
				}
			}
			//西北
			if(i-2>=0 && j-2>=0){
				if(data[i-1][j-1]==0){
					if(data[i-2][j-2]<=7){
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
						listx.add(i-2);
						listy.add(j+2);
					}
				}
			}
			//东南
			if(i+2<rows && j+2<cols){
				if(data[i+1][j+1]==0){
					if(data[i+2][j+2]==0 || data[i+2][j+2]>=8){
						listx.add(i+2);
						listy.add(j+2);
					}
				}
			}
			//西南
			if(i+2<rows && j-2>=0){
				if(data[i+1][j-1]==0){
					if(data[i+2][j-2]==0 || data[i+2][j-2]>=8){
						listx.add(i+2);
						listy.add(j-2);
					}
				}
			}
			//西北
			if(i-2>=rows/2 && j-2>=0){
				if(data[i-1][j-1]==0){
					if(data[i-2][j-2]==0 || data[i-2][j-2]>=8){
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
					listx.add(i-1);
					listy.add(j+1);
				}
			}
			//东南
			if(i+1<rows && j+1<=5){
				if(data[i-1][j+1]==0 || data[i-1][j+1]>=8){
					listx.add(i+1);
					listy.add(j+1);
				}
			}
			//西南
			if(i+1<rows && j-1>=3){
				if(data[i+1][j-1]==0 || data[i+1][j-1]>=8){
					listx.add(i+1);
					listy.add(j-1);
				}
			}
			//西北
			if(i-1>=7 && j-1>=3){
				if(data[i-1][j-1]==0 || data[i-1][j-1]>=8){
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
					listx.add(i-1);
					listy.add(j+1);
				}
			}
			//东南
			if(i+1<=2 && j+1<=5){
				if(data[i+1][j+1]<=7){
					listx.add(i+1);
					listy.add(j+1);
				}
			}
			//西南
			if(i+1<=2 && j-1>=3){
				if(data[i+1][j-1]<=7){
					listx.add(i+1);
					listy.add(j-1);
				}
			}
			//西北
			if(i-1>=0 && j-1>=3){
				if(data[i-1][j-1]<=7){
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
					listx.add(i-1);
					listy.add(j);
				}
			}
			//下
			if(i+1<rows){
				if(data[i+1][j]==0 || data[i+1][j]>=8){
					listx.add(i+1);
					listy.add(j);
				}
			}
			//左
			if(j-1>=3){
				if(data[i][j-1]==0 || data[i][j-1]>=8){
					listx.add(i);
					listy.add(j-1);
				}
			}
			//右
			if(j+1<=5){
				if(data[i][j+1]==0 || data[i][j+1]>=8){
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
					listx.add(i-1);
					listy.add(j);
				}
			}
			//下
			if(i+1<=2){
				if(data[i+1][j]<=7){
					listx.add(i+1);
					listy.add(j);
				}
			}
			//左
			if(j-1>=3){
				if(data[i][j-1]<=7){
					listx.add(i);
					listy.add(j-1);
				}
			}
			//右
			if(j+1<=5){
				if(data[i][j+1]<=7){
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
					listx.add(i-1);
					listy.add(j);
				}
			}else{
				//上
				if(i-1>=0){
					if(data[i-1][j]==0 || data[i-1][j]>=8){
						listx.add(i-1);
						listy.add(j);
					}
				}
				//左
				if(j-1>=0){
					if(data[i][j-1]==0 || data[i][j-1]>=8){
						listx.add(i);
						listy.add(j-1);
					}
				}
				//右
				if(j+1<cols){
					if(data[i][j+1]==0 || data[i][j+1]>=8){
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
					listx.add(i+1);
					listy.add(j);
				}
			}else{
				//上
				if(i+1<rows){
					if(data[i+1][j]<=7){
						listx.add(i+1);
						listy.add(j);
					}
				}
				//左
				if(j-1>=0){
					if(data[i][j-1]<=7){
						listx.add(i);
						listy.add(j-1);
					}
				}
				//右
				if(j+1<cols){
					if(data[i][j+1]<=7){
						listx.add(i);
						listy.add(j+1);
					}
				}
			}
			break;
		default:
			break;
		}
		int len=listx.size();
		int[][] furMov=new int[len][2];
		for(int h=0;h<len;h++){
			furMov[h][0]=listx.get(h);
			furMov[h][1]=listy.get(h);
		}
		return furMov;
	}
	
	public int[][] BackData(int[][] data) {
		int[][] sub=new int[data.length][data[0].length];
		for(int i=0;i<data.length;i++){
			for(int j=0;j<data[0].length;j++){
				sub[i][j]=data[i][j];//深度复制
			}
		}
		return sub;
	}
	
	public void complishSelf(UChess ch) {
		//类似后序遍历
		for(int i=0;i<ch.chidern.size();i++){
			if(ch.chidern.get(i).chidern.size()>0 && ch.chidern.get(i).chidern.get(0).currentValue==2<<15){
				//非叶子节点或者没有赋值
				complishSelf(ch.chidern.get(i));//左
			}
			//否则已经赋值了开始action
			if(ch.chidern.get(i).currentValue==2<<15){
				writeValue(ch.chidern.get(i), ch.chidern.get(i).chidern);
//				System.out.println("层数"+ch.chidern.get(i).depth+" 第"+i+"个"+" value="+ch.chidern.get(i).currentValue);
			}
		}
	}
	
	public void writeValue(UChess currentCh,ArrayList<UChess> chidern) {//取孩子中的值
		int value;
		toGo=-1;
		if(currentCh.depth%2==0){//取最大值
			value=Integer.MIN_VALUE;
			for(int i=0;i<chidern.size();i++){
				if(chidern.get(i).currentValue>value){
					value=chidern.get(i).currentValue;
					toGo=i;
				}
			}
		}else{
			//取最小值
			value=Integer.MAX_VALUE;
			for(int i=0;i<chidern.size();i++){
				if(chidern.get(i).currentValue<value){
					value=chidern.get(i).currentValue;
					toGo=i;
				}
			}
		}
		currentCh.currentValue=value;
		currentCh.step=toGo;
	}
	
	private class UChess{
		//棋子
		private ArrayList<UChess> chidern=new ArrayList<>();//孩子
		private int currentValue=2<<15;//当前值
		private int depth;//当前深度，假设初始的深度为0,偶数取最大值否则取最小值
		@SuppressWarnings("unused")
		private UChess parent;//父节点
		private int[][] innerData;//每个棋子移动对应一个图数据
		private int step;//走第几步合理
	}
	
//	public void test() {
//		UChess root=new UChess();
//		UChess f1=new UChess();
//		UChess f2=new UChess();
//		UChess f3=new UChess();
//		UChess s1=new UChess();
//		UChess s2=new UChess();
//		UChess s3=new UChess();
////		UChess s4=new UChess();
////		UChess s5=new UChess();
////		UChess s6=new UChess();
//		UChess t1=new UChess();
//		UChess t2=new UChess();
//		UChess t3=new UChess();
//		UChess t4=new UChess();
////		UChess t5=new UChess();
////		UChess t6=new UChess();
////		UChess t7=new UChess();
////		UChess t8=new UChess();
////		UChess t9=new UChess();
////		UChess  t10=new UChess();
////		UChess t11=new UChess();
////		UChess t12=new UChess();
//		t1.currentValue=-7;
//		t1.parent=s1;
//		t2.currentValue=2;
//		t2.parent=s1;
//		t3.currentValue=3;
//		t3.parent=s3;
//		t4.currentValue=-1;
//		t4.parent=s3;
////		t5.currentValue=1;
////		t5.parent=s3;
////		t6.currentValue=-5;
////		t6.parent=s3;
////		t7.currentValue=7;
////		t7.parent=s4;
////		t8.currentValue=-2;
////		t8.parent=s4;
////		t9.currentValue=3;
////		t9.parent=s5;
////		t10.currentValue=-2;
////		t10.parent=s5;
////		t11.currentValue=-3;
////		t11.parent=s6;
////		t12.currentValue=4;
////		t12.parent=s6;
//		t1.depth=t2.depth=t3.depth=t4.depth=3;
//		s1.chidern.add(t1);
//		s1.chidern.add(t2);
//		s3.chidern.add(t3);
//		s3.chidern.add(t4);
//		s2.currentValue=-1000;
////		s3.chidern.add(t5);
////		s3.chidern.add(t6);
////		s4.chidern.add(t7);
////		s4.chidern.add(t8);
////		s5.chidern.add(t9);
////		s5.chidern.add(t10);
////		s6.chidern.add(t11);
////		s6.chidern.add(t12);
//		s1.parent=f1;
//		s2.parent=f1;
//		s3.parent=f3;
////		s4.parent=f2;
////		s5.parent=f3;
////		s6.parent=f3;
//		s1.depth=s2.depth=s3.depth=2;
//		f1.chidern.add(s1);
//		f1.chidern.add(s2);
//		f3.chidern.add(s3);
////		f2.chidern.add(s4);
////		f3.chidern.add(s5);
////		f3.chidern.add(s6);
//		f1.parent=root;
//		f2.parent=root;
//		f2.currentValue=1000;
//		f3.parent=root;
//		f1.depth=f2.depth=f3.depth=1;
//		root.depth=0;
//		root.chidern.add(f1);
//		root.chidern.add(f2);
//		root.chidern.add(f3);
//		this.root=root;
//		complishSelf(root);
//		writeValue(root, root.chidern);
//		System.out.println("==========");
//	}
}
