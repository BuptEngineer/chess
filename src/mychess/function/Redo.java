package mychess.function;

import java.util.ArrayList;
import java.util.List;

//���幦��
public class Redo {//Ӧ�ñ���data��ά�б�
	private List<int[][]> move_down;
	public Redo() {
		// TODO Auto-generated constructor stub
		move_down=new ArrayList<int[][]>();
	}
	
	public void add_one_step(int[][] data) {
		move_down.add(data);
	}
	
	public void remove_last_step() {
		move_down.remove(move_down.size()-1);
	}
	
	public int[][] get_last_step() {
		return move_down.get(move_down.size()-1);
	}
	
	public int get_all_step() {
		return move_down.size();
	}
	
	public int[][] get_one_step(int index) {
		return move_down.get(index);
	}

	public List<int[][]> getMove_down() {
		return move_down;
	}
	
	public void clear_all_step() {
		move_down.clear();
	}
}
