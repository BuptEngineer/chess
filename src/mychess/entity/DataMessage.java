package mychess.entity;

import java.awt.Image;
import java.io.Serializable;

/**
 * �ͻ��˴��͵�����˵�������Ϣ
 * 2019��1��3�� ����10:07:45
 */
public class DataMessage extends Message implements Serializable{
	private static final long serialVersionUID = 1L;

	public int prerow;//�ƶ�ǰ����������
	
	public int precol;//�ƶ�ǰ����������
	
	public int row;//��������������
	
	public int col;//��������������
	
	public int chess;//�ƶ������Ӵ���
	
	public int eatedChess;//���Ե�������
	
	public int[][] data;//����

	public Image[] pics;//ͼƬ
	
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
		data[prerow-1][precol-1]=0;//�Զ�����
	}
	
	public void resever() {
		//��ת��
		prerow=11-prerow;
		precol=11-precol;
	}
}
