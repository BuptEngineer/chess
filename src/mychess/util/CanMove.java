package mychess.util;

import java.util.ArrayList;
import java.util.List;

public class CanMove {
	public List<int[]> che_move(int[][] data,int i,int j,int label) {
		List<int[]> retList=new ArrayList<int[]>();
		//�ǳ�
		//�۲������߿����ƶ���Զ��
		if(j-1>=0){
			for(int h=j-1;h>=0;h--){
				if(data[i][h]==0){
					retList.add(new int[]{i,j,i,h});
				}else if((label<=7 && data[i][h]>=8) || (label>=8 && data[i][h]<=7)){
					retList.add(new int[]{i,j,i,h});
					break;//����
				}else{
					//�Լ���
					break;
				}
			}
		}
		
		//�۲�����ұ߿����ƶ�����Զ��
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

		//�۲������������
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

		//�۲������������
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
		//��������
		if(i-2>=0 && j+1<9){
			if(data[i-1][j]==0){
				if(data[i-2][j+1]==0 || (label<=7 && data[i-2][j+1]>=8) || (label>=8 && data[i-2][j+1]<=7)){
					retList.add(new int[]{i,j,i-2,j+1});
				}
			}
		}
		//������
		if(i-1>=0 && j+2<9){
			if(data[i][j+1]==0){
				if(data[i-1][j+2]==0 || (label<=7 && data[i-1][j+2]>=8) || (label>=8 && data[i-1][j+2]<=7)){
					retList.add(new int[]{i,j,i-1,j+2});
				}
			}
		}
		//������
		if(i+1<10 && j+2<9){
			if(data[i][j+1]==0){
				if(data[i+1][j+2]==0 || (label<=7 && data[i+1][j+2]>=8) || (label>=8 && data[i+1][j+2]<=7)){
					retList.add(new int[]{i,j,i+1,j+2});
				}
			}
		}
		//������
		if(i+2<10 && j+1<9){
			if(data[i+1][j]==0){
				if(data[i+2][j+1]==0 || (label<=7 && data[i+2][j+1]>=8) || (label>=8 && data[i+2][j+1]<=7)){
					retList.add(new int[]{i,j,i+2,j+1});
				}
			}
		}
		//������
		if(i+1<9 && j-2>=0){
			if(data[i][j-1]==0){
				if(data[i+1][j-2]==0 || (label<=7 && data[i+1][j-2]>=8) || (label>=8 && data[i+1][j-2]<=7)){
					retList.add(new int[]{i,j,i+1,j-2});
				}
			}
		}
		//������
		if(i+2<10 && j-1>=0){
			if(data[i+1][j]==0){
				if(data[i+2][j-1]==0 || (label<=7 && data[i+2][j-1]>=8) || (label>=8 && data[i+2][j-1]<=7)){
					retList.add(new int[]{i,j,i+2,j-1});
				}
			}
		}
		//������
		if(i-1>=0 && j-2>=0){
			if(data[i][j-1]==0){
				if(data[i-1][j-2]==0 || (label<=7 && data[i-1][j-2]>=8) || (label>=8 && data[i-1][j-2]<=7)){
					retList.add(new int[]{i,j,i-1,j-2});
				}
			}
		}
		//������
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
		//����
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
		//��
		//����
		if(i-2>=10/2 && j+2<9){
			if(data[i-1][j+1]==0){
				if(data[i-2][j+2]==0 ||  data[i-2][j+2]>=8){
					retList.add(new int[]{i,j,i-2,j+2});
				}
			}
		}
		//����
		if(i+2<10 && j+2<9){
			if(data[i+1][j+1]==0){
				if(data[i+2][j+2]==0 || data[i+2][j+2]>=8){
					retList.add(new int[]{i,j,i+2,j+2});
				}
			}
		}
		//����
		if(i+2<10 && j-2>=0){
			if(data[i+1][j-1]==0){
				if(data[i+2][j-2]==0 || data[i+2][j-2]>=8){
					retList.add(new int[]{i,j,i+2,j-2});
				}
			}
		}
		//����
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
		//����
		List<int[]> retList=new ArrayList<>();
		//����
		if(i-2>=0 && j+2<9){
			if(data[i-1][j+1]==0){
				if(data[i-2][j+2]<=7){
					retList.add(new int[]{i,j,i-2,j+2});
				}
			}
		}
		//����
		if(i+2<10/2 && j+2<9){
			if(data[i+1][j+1]==0){
				if(data[i+2][j+2]<=7){
					retList.add(new int[]{i,j,i+2,j+2});
				}
			}
		}
		//����
		if(i+2<10/2 && j-2>=0){
			if(data[i+1][j-1]==0){
				if(data[i+2][j-2]<=7){
					retList.add(new int[]{i,j,i+2,j-2});
				}
			}
		}
		//����
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
		//ʿ
		List<int[]> retList=new ArrayList<>();
		//����
		if(i-1>=7 && j+1<=5){
			if(data[i-1][j+1]==0 || data[i-1][j+1]>=8){
				retList.add(new int[]{i,j,i-1,j+1});
			}
		}
		//����
		if(i+1<10 && j+1<=5){
			if(data[i-1][j+1]==0 || data[i-1][j+1]>=8){
				retList.add(new int[]{i,j,i+1,j+1});
			}
		}
		//����
		if(i+1<10 && j-1>=3){
			if(data[i+1][j-1]==0 || data[i+1][j-1]>=8){
				retList.add(new int[]{i,j,i+1,j-1});
			}
		}
		//����
		if(i-1>=7 && j-1>=3){
			if(data[i-1][j-1]==0 || data[i-1][j-1]>=8){
				retList.add(new int[]{i,j,i-1,j-1});
			}
		}
		return retList;
	}
	
	public List<int[]> shi_2_move(int[][] data,int i,int j) {
		//��ɫʿ
		List<int[]> retList=new ArrayList<>();
		//����
		if(i-1>=0 && j+1<=5){
			if(data[i-1][j+1]<=7){
				retList.add(new int[]{i,j,i-1,j+1});
			}
		}
		//����
		if(i+1<=2 && j+1<=5){
			if(data[i+1][j+1]<=7){
				retList.add(new int[]{i,j,i+1,j+1});
			}
		}
		//����
		if(i+1<=2 && j-1>=3){
			if(data[i+1][j-1]<=7){
				retList.add(new int[]{i,j,i+1,j-1});
			}
		}
		//����
		if(i-1>=0 && j-1>=3){
			if(data[i-1][j-1]<=7){
				retList.add(new int[]{i,j,i-1,j-1});
			}
		}
		return retList;
	}
	
	public List<int[]> jiang_2_move(int[][] data,int i,int j) {
		//��
		List<int[]> retList=new ArrayList<>();
		//��
		if(i-1>=0){
			if(data[i-1][j]<=7){
				retList.add(new int[]{i,j,i-1,j});
			}
		}
		//��
		if(i+1<=2){
			if(data[i+1][j]<=7){
				retList.add(new int[]{i,j,i+1,j});
			}
		}
		//��
		if(j-1>=3){
			if(data[i][j-1]<=7){
				retList.add(new int[]{i,j,i,j-1});
			}
		}
		//��
		if(j+1<=5){
			if(data[i][j+1]<=7){
				retList.add(new int[]{i,j,i,j+1});
			}
		}
		return retList;
	}

	public List<int[]> jiang_1_move(int[][] data,int i,int j) {
		//˧
		List<int[]> retList=new ArrayList<>();
		//��
		if(i-1<=7){
			if(data[i-1][j]==0 || data[i-1][j]>=8){
				retList.add(new int[]{i,j,i-1,j});
			}
		}
		//��
		if(i+1<10){
			if(data[i+1][j]==0 || data[i+1][j]>=8){
				retList.add(new int[]{i,j,i+1,j});
			}
		}
		//��
		if(j-1>=3){
			if(data[i][j-1]==0 || data[i][j-1]>=8){
				retList.add(new int[]{i,j,i,j-1});
			}
		}
		//��
		if(j+1<=5){
			if(data[i][j+1]==0 || data[i][j+1]>=8){
				retList.add(new int[]{i,j,i,j+1});
			}
		}
		return retList;
	}
	
	public List<int[]> bing_1_move(int[][] data,int i,int j) {
		List<int[]> retList=new ArrayList<>();
		//��
		if(i>4){
			//û�й���
			if(data[i-1][j]==0 || data[i-1][j]>=8){
				retList.add(new int[]{i,j,i-1,j});
			}
		}else{
			//��
			if(i-1>=0){
				if(data[i-1][j]==0 || data[i-1][j]>=8){
					retList.add(new int[]{i,j,i-1,j});
				}
			}
			//��
			if(j-1>=0){
				if(data[i][j-1]==0 || data[i][j-1]>=8){
					retList.add(new int[]{i,j,i,j-1});
				}
			}
			//��
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
		//��
		if(i<5){
			//û�й���
			if(data[i+1][j]<=7){
				retList.add(new int[]{i,j,i+1,j});
			}
		}else{
			//��
			if(i+1<10){
				if(data[i+1][j]<=7){
					retList.add(new int[]{i,j,i+1,j});
				}
			}
			//��
			if(j-1>=0){
				if(data[i][j-1]<=7){
					retList.add(new int[]{i,j,i,j-1});
				}
			}
			//��
			if(j+1<9){
				if(data[i][j+1]<=7){
					retList.add(new int[]{i,j,i,j+1});
				}
			}
		}
		return retList;
	}
}
