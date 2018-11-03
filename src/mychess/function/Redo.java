package mychess.function;

import java.util.ArrayList;
import java.util.List;

/**
 * 这是象棋的悔棋功能类
 */
public class Redo {
	/**
	 * 这是每步走棋对应当前棋局的保存<br>
	 * 保存为一个列表，列表中的每个元素的都是棋局的数组形式
	 */
	private List<int[][]> move_down;
	/**
	 * 对棋局列表进行初始化
	 * @param
	 */
	public Redo() {
		// TODO Auto-generated constructor stub
		move_down=new ArrayList<int[][]>();
	}
	
	/**
	 * 向已有的棋局中加上当前棋局
	 * @param data是当前棋局的数组表示
	 */
	public void add_one_step(int[][] data) {
		move_down.add(data);
	}
	
	/**
	 * 向已有的棋局中去掉最后一个棋局，常用于悔棋中
	 * @param
	 */
	public void remove_last_step() {
		move_down.remove(move_down.size()-1);
	}
	
	/**
	 * 从所有棋局中获取最后一个棋局
	 * @param
	 */
	public int[][] get_last_step() {
		return move_down.get(move_down.size()-1);
	}
	
	/**
	 * 获取所有棋局的个数，可以用来判断走的步数，辅之以判和
	 * @param
	 */
	public int get_all_step() {
		return move_down.size();
	}
	
	/**
	 * 获取特定步数的棋局，可以在复盘中定位棋局
	 * @param index从0开始，表示对应于第index+1步的棋局
	 */
	public int[][] get_one_step(int index) {
		return move_down.get(index);
	}

	/**
	 * 获取所有棋局
	 * @param
	 */
	public List<int[][]> getMove_down() {
		return move_down;
	}
	
	/**
	 * 清空所有的棋局
	 * @param
	 */
	public void clear_all_step() {
		move_down.clear();
	}
}
