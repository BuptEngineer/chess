package mychess.entity;

import java.awt.Image;
import java.io.Serializable;

/**
 * 客户端传送到服务端的数据消息
 * 2019年1月3日 下午10:07:45
 */
public class DataMessage extends Message implements Serializable{
	private static final long serialVersionUID = 1L;

	public int prerow;//移动前棋子所在行
	
	public int precol;//移动前棋子所在列
	
	public int row;//现在棋子所在行
	
	public int col;//现在棋子所在列
	
	public int chess;//移动的棋子代号
	
	public int eatedChess;//被吃掉的棋子
	
	public int[][] data;//数据

	public Image[] pics;//图片
	
	public int getPrerow() {
		return prerow;
	}

	public void setPrerow(int prerow) {
		this.prerow = prerow;
	}

	public int getPrecol() {
		return precol;
	}

	public void setPrecol(int precol) {
		this.precol = precol;
	}

	public int getRow() {
		return row;
	}

	public void setRow(int row) {
		this.row = row;
	}

	public int getCol() {
		return col;
	}

	public void setCol(int col) {
		this.col = col;
	}

	public int getChess() {
		return chess;
	}

	public void setChess(int chess) {
		this.chess = chess;
	}

	public int getEatedChess() {
		return eatedChess;
	}

	public void setEatedChess(int eatedChess) {
		this.eatedChess = eatedChess;
	}

	public int[][] getData() {
		return data;
	}

	public void setData(int[][] data) {
		this.data = data;
	}
	
	public void setPics(Image[] pics) {
		this.pics = pics;
	}
	
	public Image[] getPics() {
		return pics;
	}
	
	public void changeArray() {
		data[row-1][col-1]=data[prerow-1][precol-1];
		data[prerow-1][precol-1]=0;//自动更新
	}
	
	public void resever() {
		//反转行
		prerow=11-prerow;
		precol=11-precol;
	}
}
