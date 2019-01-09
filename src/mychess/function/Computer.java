package mychess.function;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.PriorityQueue;

import mychess.script.ReadProperties;
import mychess.util.Common;
import mychess.util.ExpertSystem;
import mychess.util.HasFinished;

/**
 * 计算机处理模块
 * 2019年1月8日 下午9:41:10
 */
public class Computer {
	private boolean isBlack;//执行是否黑方
	
	@SuppressWarnings("unused")
	private int choices;//选择数,以后待用
	
	private int[][][] values;//价值函数
	
	private int defaultDepth;//默认搜索深度
	
	private PriorityQueue<Node> priorityQueue;//候选集
	public Computer() {
		// TODO Auto-generated constructor stub
		isBlack=ReadProperties.YOURTURN;
		defaultDepth=Integer.parseInt(ReadProperties.DEPTH);
		values=ExpertSystem.value();
	}
	
	public int[] work(int[][] data) {
		priorityQueue=new PriorityQueue<>(Comparator.comparing(Node::getBeta).reversed());//安装alpha来从大到小排序
		Node root=new Node();
		root.setAlpha(Integer.MIN_VALUE);
		root.setBeta(Integer.MAX_VALUE);
		root.setCurrent_depth(1);
		root.setData(Common.Backup(data));
		AlphaBetaAlg(root);
		return priorityQueue.peek().getAxis();
	}
	
	public int Evaluate(int[][] data) {//评估函数,相对计算机来说的
		int res=0;
		for(int i=0;i<10;i++){
			for(int j=0;j<9;j++){
				if(data[i][j]==0) continue;
				if((isBlack && data[i][j]>=8) || (!isBlack && data[i][j]<=7))
					res+=values[data[i][j]][i][j];
				else
					res-=values[data[i][j]][i][j];
			}
		}
		return res;
	}
	
	//核心算法
	public int AlphaBetaAlg(Node node) {
		if(node.getCurrent_depth()==defaultDepth){
			//开始计算
			return Evaluate(node.getData());//base
		}

		List<List<int[]>> moves=HasFinished.all_move(Common.Backup(node.getData()), node.getCurrent_depth()%2==1);//基数层是黑方走棋集合
		
		for(int i=0;i<moves.size();i++){
			//外层是对应具体棋子的所有走法
			for(int j=0;j<moves.get(i).size();j++){
				//内层对应某个棋子的具体走法
				Node n=new Node();
				n.setCurrent_depth(node.getCurrent_depth()+1);
				n.setAlpha(node.getAlpha());
				n.setBeta(node.getBeta());
				int[][] sub=Common.Backup(node.getData());//备份
				int[] change=moves.get(i).get(j);//四个坐标,从0开始
				n.setAxis(change);
				sub[change[2]][change[3]]=sub[change[0]][change[1]];
				sub[change[0]][change[1]]=0;
				n.setData(sub);
				int val=AlphaBetaAlg(n);//下一层返回给上层的决策值
				if(node.getCurrent_depth()%2==1){
					//假设初始是第一层，那么奇数层求alpha最大，偶数层求beta最小
					if(node.getAlpha()<val)
						node.setAlpha(val);
					if(node.getCurrent_depth()==1){
						priorityQueue.add(n);
					}
				}else{
					if(node.getBeta()>val)
						node.setBeta(val);
				}
				if(node.getAlpha()>node.getBeta()){
					//减枝情况
					if(node.getCurrent_depth()%2==1) return node.getAlpha();
					else return node.getBeta();
				}
			}
		}

		//最终返回
		if(node.getCurrent_depth()%2==1){
			return node.getAlpha();
		}
		else{
			return node.getBeta();
		}
	}
	
	class Node{
		//以树的形式来存储博弈树
		private int current_depth;//当前的层数
		
		private int[][] data;//每层对应的数据
		
		private int[] axis;//移动坐标
		
		private int Alpha;
		
		private int Beta;
		
		private List<Node> childern;//孩子节点集合
		
		public Node() {
			// TODO Auto-generated constructor stub
			childern=new ArrayList<Computer.Node>();
		}

		public int getCurrent_depth() {
			return current_depth;
		}

		public void setCurrent_depth(int current_depth) {
			this.current_depth = current_depth;
		}

		public int[][] getData() {
			return data;
		}

		public void setData(int[][] data) {
			this.data = data;
		}

		public int getAlpha() {
			return Alpha;
		}

		public void setAlpha(int alpha) {
			Alpha = alpha;
		}

		public int getBeta() {
			return Beta;
		}

		public void setBeta(int beta) {
			Beta = beta;
		}

		public List<Node> getChildern() {
			return childern;
		}

		public void setChildern(List<Node> childern) {
			this.childern = childern;
		}

		public int[] getAxis() {
			return axis;
		}

		public void setAxis(int[] axis) {
			this.axis = axis;
		}
		
	}
}
