package mychess.util;

import java.util.ArrayList;
import java.util.List;

/**
 * 这是用在给定的棋局中，对于给的一个固定位置上的棋子，该棋子所有能走的位置的类
 */
public class CanMove {
	/**
	 * 获取在(i,j)位置上的车,能在当前棋局data中所有可能移动的位置集合
	 * @param data是当前棋局数组，i是棋局中的行坐标从0开始,j是棋局中的列坐标从0开始,
	 * 		label是当前标签（车）<br>
	 * 		返回的是一个列表，这个列表包含所有的可能位置集合。可能位置由两对坐标组成，分别是移动前的坐标和
	 * 		可能移动后的坐标
	 */
	public List<int[]> che_move(int[][] data,int i,int j,int label) {
		List<int[]> retList=new ArrayList<int[]>();
		//是车
		//观察横行左边可以移动最远处
		if(j-1>=0){
			for(int h=j-1;h>=0;h--){
				if(data[i][h]==0){
					retList.add(new int[]{i,j,i,h});
				}else if((label<=7 && data[i][h]>=8) || (label>=8 && data[i][h]<=7)){
					retList.add(new int[]{i,j,i,h});
					break;//吃子
				}else{
					//自己子
					break;
				}
			}
		}
		
		//观察横行右边可以移动的最远处
		if(j+1<9){
			for(int h=j+1;h<9;h++){
				if(data[i][h]==0){
					retList.add(new int[]{i,j,i,h});
				}else if((label<=7 && data[i][h]>=8) || (label>=8 && data[i][h]<=7)){
					retList.add(new int[]{i,j,i,h});
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
					retList.add(new int[]{i,j,k,j});
				}else if((label<=7 && data[k][j]>=8) || (label>=8 && data[k][j]<=7)){
					retList.add(new int[]{i,j,k,j});
					break;
				}else {
					break;
				}
			}
		}

		//观察纵行下面最大
		if(i+1<10){
			for(int k=i+1;k<10;k++){
				if(data[k][j]==0){
					retList.add(new int[]{i,j,k,j});
				}else if((label<=7 && data[k][j]>=8) || (label>=8 && data[k][j]<=7)){
					retList.add(new int[]{i,j,k,j});
					break;
				}else {
					break;
				}
			}
		}
		return retList;
	}
	
	public List<int[]> ma_move(int[][] data,int i,int j,int label) {
		List<int[]> retList=new ArrayList<>();
		//东北方向
		if(i-2>=0 && j+1<9){
			if(data[i-1][j]==0){
				if(data[i-2][j+1]==0 || (label<=7 && data[i-2][j+1]>=8) || (label>=8 && data[i-2][j+1]<=7)){
					retList.add(new int[]{i,j,i-2,j+1});
				}
			}
		}
		//东北躺
		if(i-1>=0 && j+2<9){
			if(data[i][j+1]==0){
				if(data[i-1][j+2]==0 || (label<=7 && data[i-1][j+2]>=8) || (label>=8 && data[i-1][j+2]<=7)){
					retList.add(new int[]{i,j,i-1,j+2});
				}
			}
		}
		//东南躺
		if(i+1<10 && j+2<9){
			if(data[i][j+1]==0){
				if(data[i+1][j+2]==0 || (label<=7 && data[i+1][j+2]>=8) || (label>=8 && data[i+1][j+2]<=7)){
					retList.add(new int[]{i,j,i+1,j+2});
				}
			}
		}
		//东南跳
		if(i+2<10 && j+1<9){
			if(data[i+1][j]==0){
				if(data[i+2][j+1]==0 || (label<=7 && data[i+2][j+1]>=8) || (label>=8 && data[i+2][j+1]<=7)){
					retList.add(new int[]{i,j,i+2,j+1});
				}
			}
		}
		//西南躺
		if(i+1<9 && j-2>=0){
			if(data[i][j-1]==0){
				if(data[i+1][j-2]==0 || (label<=7 && data[i+1][j-2]>=8) || (label>=8 && data[i+1][j-2]<=7)){
					retList.add(new int[]{i,j,i+1,j-2});
				}
			}
		}
		//西南跳
		if(i+2<10 && j-1>=0){
			if(data[i+1][j]==0){
				if(data[i+2][j-1]==0 || (label<=7 && data[i+2][j-1]>=8) || (label>=8 && data[i+2][j-1]<=7)){
					retList.add(new int[]{i,j,i+2,j-1});
				}
			}
		}
		//西北躺
		if(i-1>=0 && j-2>=0){
			if(data[i][j-1]==0){
				if(data[i-1][j-2]==0 || (label<=7 && data[i-1][j-2]>=8) || (label>=8 && data[i-1][j-2]<=7)){
					retList.add(new int[]{i,j,i-1,j-2});
				}
			}
		}
		//西北跳
		if(i-2>=0 && j-1>=0){
			if(data[i-1][j]==0){
				if(data[i-2][j-1]==0 || (label<=7 && data[i-2][j-1]>=8) || (label>=8 && data[i-2][j-1]<=7)){
					retList.add(new int[]{i,j,i-2,j-1});
				}
			}
		}
		return retList;
	}
	
	public List<int[]> pao_move(int[][] data,int i,int j,int label) {
		//是炮
		List<int[]> retList=new ArrayList<>();
		if(j-1>=0){
			for(int h=j-1;h>=0;h--){
				if(data[i][h]==0){
					retList.add(new int[]{i,j,i,h});
				}else {
					if(h-1>=0){
						for(int k=h-1;k>=0;k--){
							if(data[i][k]==0){
								continue;
							}
							else if((label<=7 && data[i][k]>=8)||(label>=8 && data[i][k]<=7)){
								retList.add(new int[]{i,j,i,k});
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
		
		if(j+1<9){
			for(int h=j+1;h<9;h++){
				if(data[i][h]==0){
					retList.add(new int[]{i,j,i,h});
				}else{
					if(h+1<9){
						for(int k=h+1;k<9;k++){
							if(data[i][k]==0)
								continue;
							else if((label<=7 && data[i][k]>=8)||(label>=8 && data[i][k]<=7)){
								retList.add(new int[]{i,j,i,k});
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
					retList.add(new int[]{i,j,h,j});
				}else{
					if(h-1>=0){
						for(int k=h-1;k>=0;k--){
							if(data[k][j]==0)
								continue;
							else if((label<=7 && data[k][j]>=8)||(label>=8 && data[k][j]<=7)){
								retList.add(new int[]{i,j,k,j});
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
		
		if(i+1<9){
			for(int h=i+1;h<9;h++){
				if(data[h][j]==0){
					retList.add(new int[]{i,j,h,j});
				}else{
					if(h+1<9){
						for(int k=h+1;k<10;k++){
							if(data[k][j]==0)
								continue;
							else if((label<=7 && data[k][j]>=8)||(label>=8 && data[k][j]<=7)){
								retList.add(new int[]{i,j,k,j});
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
		return retList;
	}
	
	public List<int[]> xiang_1_move(int[][] data,int i,int j) {
		List<int[]> retList=new ArrayList<>();
		//相
		//东北
		if(i-2>=10/2 && j+2<9){
			if(data[i-1][j+1]==0){
				if(data[i-2][j+2]==0 ||  data[i-2][j+2]>=8){
					retList.add(new int[]{i,j,i-2,j+2});
				}
			}
		}
		//东南
		if(i+2<10 && j+2<9){
			if(data[i+1][j+1]==0){
				if(data[i+2][j+2]==0 || data[i+2][j+2]>=8){
					retList.add(new int[]{i,j,i+2,j+2});
				}
			}
		}
		//西南
		if(i+2<10 && j-2>=0){
			if(data[i+1][j-1]==0){
				if(data[i+2][j-2]==0 || data[i+2][j-2]>=8){
					retList.add(new int[]{i,j,i+2,j-2});
				}
			}
		}
		//西北
		if(i-2>=10/2 && j-2>=0){
			if(data[i-1][j-1]==0){
				if(data[i-2][j-2]==0 || data[i-2][j-2]>=8){
					retList.add(new int[]{i,j,i-2,j-2});
				}
			}
		}
		return retList;
	}
	
	public List<int[]> xiang_2_move(int[][] data,int i,int j) {
		//像蓝
		List<int[]> retList=new ArrayList<>();
		//东北
		if(i-2>=0 && j+2<9){
			if(data[i-1][j+1]==0){
				if(data[i-2][j+2]<=7){
					retList.add(new int[]{i,j,i-2,j+2});
				}
			}
		}
		//东南
		if(i+2<10/2 && j+2<9){
			if(data[i+1][j+1]==0){
				if(data[i+2][j+2]<=7){
					retList.add(new int[]{i,j,i+2,j+2});
				}
			}
		}
		//西南
		if(i+2<10/2 && j-2>=0){
			if(data[i+1][j-1]==0){
				if(data[i+2][j-2]<=7){
					retList.add(new int[]{i,j,i+2,j-2});
				}
			}
		}
		//西北
		if(i-2>=0 && j-2>=0){
			if(data[i-1][j-1]==0){
				if(data[i-2][j-2]<=7){
					retList.add(new int[]{i,j,i-2,j-2});
				}
			}
		}
		return retList;
	}
	
	public List<int[]> shi_1_move(int[][] data,int i,int j) {
		//士
		List<int[]> retList=new ArrayList<>();
		//东北
		if(i-1>=7 && j+1<=5){
			if(data[i-1][j+1]==0 || data[i-1][j+1]>=8){
				retList.add(new int[]{i,j,i-1,j+1});
			}
		}
		//东南
		if(i+1<10 && j+1<=5){
			if(data[i-1][j+1]==0 || data[i-1][j+1]>=8){
				retList.add(new int[]{i,j,i+1,j+1});
			}
		}
		//西南
		if(i+1<10 && j-1>=3){
			if(data[i+1][j-1]==0 || data[i+1][j-1]>=8){
				retList.add(new int[]{i,j,i+1,j-1});
			}
		}
		//西北
		if(i-1>=7 && j-1>=3){
			if(data[i-1][j-1]==0 || data[i-1][j-1]>=8){
				retList.add(new int[]{i,j,i-1,j-1});
			}
		}
		return retList;
	}
	
	public List<int[]> shi_2_move(int[][] data,int i,int j) {
		//蓝色士
		List<int[]> retList=new ArrayList<>();
		//东北
		if(i-1>=0 && j+1<=5){
			if(data[i-1][j+1]<=7){
				retList.add(new int[]{i,j,i-1,j+1});
			}
		}
		//东南
		if(i+1<=2 && j+1<=5){
			if(data[i+1][j+1]<=7){
				retList.add(new int[]{i,j,i+1,j+1});
			}
		}
		//西南
		if(i+1<=2 && j-1>=3){
			if(data[i+1][j-1]<=7){
				retList.add(new int[]{i,j,i+1,j-1});
			}
		}
		//西北
		if(i-1>=0 && j-1>=3){
			if(data[i-1][j-1]<=7){
				retList.add(new int[]{i,j,i-1,j-1});
			}
		}
		return retList;
	}
	
	public List<int[]> jiang_2_move(int[][] data,int i,int j) {
		//将
		List<int[]> retList=new ArrayList<>();
		//上
		if(i-1>=0){
			if(data[i-1][j]<=7){
				retList.add(new int[]{i,j,i-1,j});
			}
		}
		//下
		if(i+1<=2){
			if(data[i+1][j]<=7){
				retList.add(new int[]{i,j,i+1,j});
			}
		}
		//左
		if(j-1>=3){
			if(data[i][j-1]<=7){
				retList.add(new int[]{i,j,i,j-1});
			}
		}
		//右
		if(j+1<=5){
			if(data[i][j+1]<=7){
				retList.add(new int[]{i,j,i,j+1});
			}
		}
		return retList;
	}

	public List<int[]> jiang_1_move(int[][] data,int i,int j) {
		//帅
		List<int[]> retList=new ArrayList<>();
		//上
		if(i-1<=7){
			if(data[i-1][j]==0 || data[i-1][j]>=8){
				retList.add(new int[]{i,j,i-1,j});
			}
		}
		//下
		if(i+1<10){
			if(data[i+1][j]==0 || data[i+1][j]>=8){
				retList.add(new int[]{i,j,i+1,j});
			}
		}
		//左
		if(j-1>=3){
			if(data[i][j-1]==0 || data[i][j-1]>=8){
				retList.add(new int[]{i,j,i,j-1});
			}
		}
		//右
		if(j+1<=5){
			if(data[i][j+1]==0 || data[i][j+1]>=8){
				retList.add(new int[]{i,j,i,j+1});
			}
		}
		return retList;
	}
	
	public List<int[]> bing_1_move(int[][] data,int i,int j) {
		List<int[]> retList=new ArrayList<>();
		//兵
		if(i>4){
			//没有过河
			if(data[i-1][j]==0 || data[i-1][j]>=8){
				retList.add(new int[]{i,j,i-1,j});
			}
		}else{
			//上
			if(i-1>=0){
				if(data[i-1][j]==0 || data[i-1][j]>=8){
					retList.add(new int[]{i,j,i-1,j});
				}
			}
			//左
			if(j-1>=0){
				if(data[i][j-1]==0 || data[i][j-1]>=8){
					retList.add(new int[]{i,j,i,j-1});
				}
			}
			//右
			if(j+1<9){
				if(data[i][j+1]==0 || data[i][j+1]>=8){
					retList.add(new int[]{i,j,i,j+1});
				}
			}
		}
		return retList;
	}
	
	public List<int[]> bing_2_move(int[][] data,int i,int j) {
		List<int[]> retList=new ArrayList<>();
		//卒
		if(i<5){
			//没有过河
			if(data[i+1][j]<=7){
				retList.add(new int[]{i,j,i+1,j});
			}
		}else{
			//上
			if(i+1<10){
				if(data[i+1][j]<=7){
					retList.add(new int[]{i,j,i+1,j});
				}
			}
			//左
			if(j-1>=0){
				if(data[i][j-1]<=7){
					retList.add(new int[]{i,j,i,j-1});
				}
			}
			//右
			if(j+1<9){
				if(data[i][j+1]<=7){
					retList.add(new int[]{i,j,i,j+1});
				}
			}
		}
		return retList;
	}
}
