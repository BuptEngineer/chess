package mychess.entity;

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
	
	public String data;//数据

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

	public void setData(String data) {
		this.data = data;
	}
	
	public String getData() {
		return data;
	}
}
