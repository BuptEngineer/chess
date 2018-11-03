package mychess.function;

import java.util.ArrayList;
import java.util.List;

/**
 * ��������Ļ��幦����
 */
public class Redo {
	/**
	 * ����ÿ�������Ӧ��ǰ��ֵı���<br>
	 * ����Ϊһ���б��б��е�ÿ��Ԫ�صĶ�����ֵ�������ʽ
	 */
	private List<int[][]> move_down;
	/**
	 * ������б���г�ʼ��
	 * @param
	 */
	public Redo() {
		// TODO Auto-generated constructor stub
		move_down=new ArrayList<int[][]>();
	}
	
	/**
	 * �����е�����м��ϵ�ǰ���
	 * @param data�ǵ�ǰ��ֵ������ʾ
	 */
	public void add_one_step(int[][] data) {
		move_down.add(data);
	}
	
	/**
	 * �����е������ȥ�����һ����֣������ڻ�����
	 * @param
	 */
	public void remove_last_step() {
		move_down.remove(move_down.size()-1);
	}
	
	/**
	 * ����������л�ȡ���һ�����
	 * @param
	 */
	public int[][] get_last_step() {
		return move_down.get(move_down.size()-1);
	}
	
	/**
	 * ��ȡ������ֵĸ��������������ж��ߵĲ�������֮���к�
	 * @param
	 */
	public int get_all_step() {
		return move_down.size();
	}
	
	/**
	 * ��ȡ�ض���������֣������ڸ����ж�λ���
	 * @param index��0��ʼ����ʾ��Ӧ�ڵ�index+1�������
	 */
	public int[][] get_one_step(int index) {
		return move_down.get(index);
	}

	/**
	 * ��ȡ�������
	 * @param
	 */
	public List<int[][]> getMove_down() {
		return move_down;
	}
	
	/**
	 * ������е����
	 * @param
	 */
	public void clear_all_step() {
		move_down.clear();
	}
}
