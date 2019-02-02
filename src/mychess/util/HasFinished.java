package mychess.util;

import java.util.ArrayList;
import java.util.List;

import javax.swing.JOptionPane;

/**
 * �����ж���Ϸ�Ƿ��������
 */
public class HasFinished {
	private int[][] data;

	/**
	 * ��ʼ����ǰ���״̬����
	 * @param data�Ǵ��������������
	 */
	public HasFinished(int[][] data) {
		// TODO Auto-generated constructor stub
		this.data = data;
	}
	
	/**
	 * �ж���Ϸ�Ƿ����
	 * @param isRed�ǵ�ǰִ��Ľ�ɫ
	 */
	public boolean isFinished(boolean isRed) {
		//��������
		if(isRed && !Shuai_exists(data)){
			return true;
		}else if(!isRed && !Jiang_exists(data)){
			return true;
		}
		//�������
		List<List<int[]>> all=all_move(Common.Backup(data),isRed);
		for(int i=0;i<all.size();i++){
			for(int j=0;j<all.get(i).size();j++){
				int[] current=all.get(i).get(j);
				int[][] datasub=Common.Backup(data);
				datasub[current[2]][current[3]]=datasub[current[0]][current[1]];
				datasub[current[0]][current[1]]=0;
				if((isRed && isJiang(datasub)) || 
						(!isRed && isShuai(datasub))) continue;
				else return false;
			}
		}
		return true;
	}

	/**
	 * ��ȡ��ǰ��ɫ���п����ƶ�����ļ���
	 * @param isRed�ǵ�ǰ��ɫ,
	 * 			���ص���List��List,�����List�Ƕ���ÿ����ǩ�������ƶ����꼯�ϣ������List�����б�ǩ���ƶ�����
	 */
	public static List<List<int[]>> all_move(int[][] datasub,boolean isRed) {// �췽����������ʱ�ܶ���������
		List<List<int[]>> all = new ArrayList<>();
		CanMove cm = new CanMove();
		//int[][] datasub = Common.Backup(data);
		for (int i = 0; i < datasub.length; i++) {
			for (int j = 0; j < datasub[i].length; j++) {
				if ((!isRed && datasub[i][j] > 7) ||
						(isRed && datasub[i][j]<=7))
					continue;// ���Ӳ����Լ���,��Ϊ��ǰ��ִ���굽��һ��������ֱ�ӿ��ǵ���һ����
				switch (datasub[i][j]) {
					case 1:// ��
					case 8:
						all.add(cm.che_move(datasub, i, j, datasub[i][j]));
						break;
					case 2:// ��
					case 9:
						all.add(cm.ma_move(datasub, i, j, datasub[i][j]));
						break;
					case 3:// ��
						all.add(cm.xiang_1_move(datasub, i, j));
						break;
					case 4:// ʿ
						all.add(cm.shi_1_move(datasub, i, j));
						break;
					case 5:// ˧
						all.add(cm.jiang_1_move(datasub, i, j));
						break;
					case 6:// ��
					case 13:
						all.add(cm.pao_move(datasub, i, j, datasub[i][j]));
						break;
					case 7:// ��
						all.add(cm.bing_1_move(datasub, i, j));
						break;
					case 10:// ��
						all.add(cm.xiang_2_move(datasub, i, j));
						break;
					case 11:// ʿ
						all.add(cm.shi_2_move(datasub, i, j));
						break;
					case 12:// ��
						all.add(cm.jiang_2_move(datasub, i, j));
						break;
					case 14:// ��
						all.add(cm.bing_2_move(datasub, i, j));
						break;
					default:
						break;
				}
			}
		}
		return all;
	}

	/**
	 * �Ƿ񱻽���
	 * @param subuc�ǵ�ǰ�������ı�������
	 */
	public static boolean isJiang(int[][] subuc) {
		int jx = -1;
		int jy = -1;
		for (int i = 0; i < 10; i++) {
			for (int j = 0; j < 9; j++) {
				if (subuc[i][j] == 12) {
					jx = i;
					jy = j;
				}
			}
		}
		if (jx == -1)
			return true;
		// �ҵ�������λ��
		// ���ſ��Ƿ��жԷ��ĳ�
		for (int h = jy - 1; h >= 0; h--) {
			if (subuc[jx][h] == 0)
				continue;
			else if (subuc[jx][h] == 1) {
				return true;
			} else {
				break;
			}
		}
		for (int h = jy + 1; h < 9; h++) {
			if (subuc[jx][h] == 0)
				continue;
			else if (subuc[jx][h] == 1)
				return true;
			else {
				break;
			}
		}
		// ���ſ�
		if (jx - 1 >= 0) {
			for (int h = jx - 1; h >= 0; h--) {
				if (subuc[h][jy] == 0)
					continue;
				else if (subuc[h][jy] == 1)
					return true;
				else {
					break;
				}
			}
		}
		for (int h = jx + 1; h < 9; h++) {
			if (subuc[h][jy] == 0)
				continue;
			else if (subuc[h][jy] == 1)
				return true;
			else {
				break;
			}
		}

		// ���Ƿ��ڽ���
		for (int h = jy - 1; h >= 0; h--) {
			if (subuc[jx][h] == 0)
				continue;
			else {
				// ��һ������
				if (h - 1 >= 0) {
					for (int k = h - 1; k >= 0; k--) {
						if (subuc[jx][k] == 0)
							continue;
						else if (subuc[jx][k] == 6)
							return true;
						else {
							break;
						}
					}
				}
				break;
			}
		}
		for (int h = jy + 1; h < 9; h++) {
			if (subuc[jx][h] == 0)
				continue;
			else {
				if (h + 1 < 9) {
					for (int k = h + 1; k < 9; k++) {
						if (subuc[jx][k] == 0)
							continue;
						else if (subuc[jx][k] == 6)
							return true;
						else {
							break;
						}
					}
				}
				break;
			}
		}
		if (jx - 1 >= 0) {
			for (int h = jx - 1; h >= 0; h--) {
				if (subuc[h][jy] == 0)
					continue;
				else {
					if (h - 1 >= 0) {
						for (int k = h - 1; k >= 0; k--) {
							if (subuc[k][jy] == 0)
								continue;
							else if (subuc[k][jy] == 6)
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
		for (int h = jx + 1; h < 10; h++) {
			if (subuc[h][jy] == 0)
				continue;
			else {
				if (h + 1 < 10) {
					for (int k = h + 1; k < 10; k++) {
						if (subuc[k][jy] == 0)
							continue;
						else if (subuc[k][jy] == 6)
							return true;
						else {
							break;
						}
					}
				}
				break;
			}
		}

		// ���Ƿ�����
		if (jx - 1 >= 0 && subuc[jx - 1][jy + 1] == 0) {
			if ((jx - 2 >= 0 && subuc[jx - 2][jy + 1] == 2) || subuc[jx - 1][jy + 2] == 2) {
				return true;
			}
		}

		if (jx - 1 >= 0 && subuc[jx - 1][jy - 1] == 0) {
			if ((jx - 2 >= 0 && subuc[jx - 2][jy - 1] == 2) || subuc[jx - 1][jy - 2] == 2) {
				return true;
			}
		}

		if (subuc[jx + 1][jy + 1] == 0) {
			if (subuc[jx + 2][jy + 1] == 2 || subuc[jx + 1][jy + 2] == 2) {
				return true;
			}
		}

		if (subuc[jx + 1][jy - 1] == 0) {
			if (subuc[jx + 2][jy - 1] == 2 || subuc[jx + 1][jy - 2] == 2) {
				return true;
			}
		}

		// ���Ƿ񱻶����˧����
		for (int h = jx + 1; h < 10; h++) {
			if (subuc[h][jy] == 0)
				continue;
			else if (subuc[h][jy] == 5)
				return true;
			else {
				break;
			}
		}

		// ���Ƿ񱻶���ı�����
		if (subuc[jx + 1][jy] == 7 || subuc[jx][jy - 1] == 7 || subuc[jx][jy + 1] == 7)
			return true;

		return false;
	}

	/**
	 * ˧�Ƿ񱻽���
	 * @param subuc����ֱ�������
	 */
	public static boolean isShuai(int[][] subuc) {
		// ���Ƿ񱻳�����
		int jx = -1;
		int jy = -1;
		for (int i = 0; i < 10; i++) {
			for (int j = 0; j < 9; j++) {
				if (subuc[i][j] == 5) {
					jx = i;
					jy = j;
				}
			}
		}
		if (jx == -1)
			return true;
		// �ҵ�������λ��
		// ���ſ��Ƿ��жԷ��ĳ�
		for (int h = jy - 1; h >= 0; h--) {
			if (subuc[jx][h] == 0)
				continue;
			else if (subuc[jx][h] == 8) {
				return true;
			} else {
				break;
			}
		}
		for (int h = jy + 1; h < 9; h++) {
			if (subuc[jx][h] == 0)
				continue;
			else if (subuc[jx][h] == 8)
				return true;
			else {
				break;
			}
		}
		// ���ſ�
		for (int h = jx - 1; h >= 0; h--) {
			if (subuc[h][jy] == 0)
				continue;
			else if (subuc[h][jy] == 8)
				return true;
			else {
				break;
			}
		}

		if (jx + 1 < 10) {
			for (int h = jx + 1; h < 10; h++) {
				if (subuc[h][jy] == 0)
					continue;
				else if (subuc[h][jy] == 8)
					return true;
				else {
					break;
				}
			}
		}

		// ���Ƿ��ڽ���
		for (int h = jy - 1; h >= 0; h--) {
			if (subuc[jx][h] == 0)
				continue;
			else {
				// ��һ������
				if (h - 1 >= 0) {
					for (int k = h - 1; k >= 0; k--) {
						if (subuc[jx][k] == 0)
							continue;
						else if (subuc[jx][k] == 13)
							return true;
						else {
							break;
						}
					}
				}
				break;
			}
		}
		for (int h = jy + 1; h < 9; h++) {
			if (subuc[jx][h] == 0)
				continue;
			else {
				if (h + 1 < 9) {
					for (int k = h + 1; k < 9; k++) {
						if (subuc[jx][k] == 0)
							continue;
						else if (subuc[jx][k] == 13)
							return true;
						else {
							break;
						}
					}
				}
				break;
			}
		}

		for (int h = jx - 1; h >= 0; h--) {
			if (subuc[h][jy] == 0)
				continue;
			else {
				if (h - 1 >= 0) {
					for (int k = h - 1; k >= 0; k--) {
						if (subuc[k][jy] == 0)
							continue;
						else if (subuc[k][jy] == 13)
							return true;
						else {
							break;
						}
					}
				}
				break;
			}
		}

		if (jx + 1 < 10) {
			for (int h = jx + 1; h < 10; h++) {
				if (subuc[h][jy] == 0)
					continue;
				else {
					if (h + 1 < 10) {
						for (int k = h + 1; k < 10; k++) {
							if (subuc[k][jy] == 0)
								continue;
							else if (subuc[k][jy] == 13)
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

		// ���Ƿ�����
		if (subuc[jx - 1][jy + 1] == 0) {
			if (subuc[jx - 2][jy + 1] == 9 || subuc[jx - 1][jy + 2] == 9) {
				return true;
			}
		}

		if (subuc[jx - 1][jy - 1] == 0) {
			if (subuc[jx - 2][jy - 1] == 9 || subuc[jx - 1][jy - 2] == 9) {
				return true;
			}
		}

		if (jx + 1 < 10 && subuc[jx + 1][jy + 1] == 0) {
			if ((jx + 2 < 10 && subuc[jx + 2][jy + 1] == 9) || subuc[jx + 1][jy + 2] == 9) {
				return true;
			}
		}

		if (jx + 1 < 10 && subuc[jx + 1][jy - 1] == 0) {
			if ((jx + 2 < 10 && subuc[jx + 2][jy - 1] == 9) || subuc[jx + 1][jy - 2] == 9) {
				return true;
			}
		} // �Է��������ﷸ���9д����2

		// ���Ƿ񱻶����˧����
		for (int h = jx - 1; h >= 0; h--) {
			if (subuc[h][jy] == 0)
				continue;
			else if (subuc[h][jy] == 12)
				return true;
			else {
				break;
			}
		}

		// ���Ƿ񱻶���ı�����
		if (subuc[jx - 1][jy] == 14 || subuc[jx][jy - 1] == 14 || subuc[jx][jy + 1] == 14)
			return true;

		return false;
	}
	
	/**
	 * �жϵ�ǰ���Ƿ����ͽ�������ִ���ϲ�һ���źţ�true or false)
	 * @param datasub����ֱ�������,yourTurn�Ƿ��Լ��Ļغ�
	 */
	public static boolean jiangTip(int[][] datasub,boolean yourTurn) {
		if((yourTurn && isShuai(datasub))
				|| (!yourTurn && isJiang(datasub))){
			JOptionPane.showMessageDialog(null, "����");
			return false;
		}
		return true;
	}
	
	/**
	 * �ж�˧�Ƿ��ھŹ���
	 * @param data���������
	 */
	private boolean Shuai_exists(int[][] data) {
		for(int i=7;i<=9;i++)
			for(int j=3;j<=5;j++)
				if(data[i][j]==5)
					return true;
		return false;
	}
	
	/**
	 * �жϽ��Ƿ��ھŹ���
	 * @param data���������
	 */
	private boolean Jiang_exists(int[][] data) {
		for(int i=0;i<=2;i++)
			for(int j=3;j<=5;j++)
				if(data[i][j]==12)
					return true;
		return false;
	}
}
