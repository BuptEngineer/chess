package mychess.function;

import java.util.ArrayList;
import java.util.List;

//悔棋功能
public class Redo {//应该保持data二维列表
	private List<int[]> move_down;
	public Redo() {
		// TODO Auto-generated constructor stub
		move_down=new ArrayList<int[]>();
	}
	
	public void add_one_step(int[] aixs) {
		move_down.add(aixs);
	}
	
	public void remove_one_step() {
		move_down.remove(move_down.size()-1);
	}
	
	public int[] get_last_step() {
		return move_down.get(move_down.size()-1);
	}
	
	public int get_all_step() {
		return move_down.size();
	}
}
