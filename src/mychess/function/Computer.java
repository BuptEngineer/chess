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
 * ���������ģ��
 * 2019��1��8�� ����9:41:10
 */
public class Computer {
	private boolean isBlack;//ִ���Ƿ�ڷ�
	
	@SuppressWarnings("unused")
	private int choices;//ѡ����,�Ժ����
	
	private int[][][] values;//��ֵ����
	
	private int defaultDepth;//Ĭ���������
	
	private PriorityQueue<Node> priorityQueue;//��ѡ��
	public Computer() {
		// TODO Auto-generated constructor stub
		isBlack=ReadProperties.YOURTURN;
		defaultDepth=Integer.parseInt(ReadProperties.DEPTH);
		values=ExpertSystem.value();
	}
	
	public int[] work(int[][] data) {
		priorityQueue=new PriorityQueue<>(Comparator.comparing(Node::getBeta).reversed());//��װalpha���Ӵ�С����
		Node root=new Node();
		root.setAlpha(Integer.MIN_VALUE);
		root.setBeta(Integer.MAX_VALUE);
		root.setCurrent_depth(1);
		root.setData(Common.Backup(data));
		AlphaBetaAlg(root);
		return priorityQueue.peek().getAxis();
	}
	
	public int Evaluate(int[][] data) {//��������,��Լ������˵��
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
	
	//�����㷨
	public int AlphaBetaAlg(Node node) {
		if(node.getCurrent_depth()==defaultDepth){
			//��ʼ����
			return Evaluate(node.getData());//base
		}

		List<List<int[]>> moves=HasFinished.all_move(Common.Backup(node.getData()), node.getCurrent_depth()%2==1);//�������Ǻڷ����弯��
		
		for(int i=0;i<moves.size();i++){
			//����Ƕ�Ӧ�������ӵ������߷�
			for(int j=0;j<moves.get(i).size();j++){
				//�ڲ��Ӧĳ�����ӵľ����߷�
				Node n=new Node();
				n.setCurrent_depth(node.getCurrent_depth()+1);
				n.setAlpha(node.getAlpha());
				n.setBeta(node.getBeta());
				int[][] sub=Common.Backup(node.getData());//����
				int[] change=moves.get(i).get(j);//�ĸ�����,��0��ʼ
				n.setAxis(change);
				sub[change[2]][change[3]]=sub[change[0]][change[1]];
				sub[change[0]][change[1]]=0;
				n.setData(sub);
				int val=AlphaBetaAlg(n);//��һ�㷵�ظ��ϲ�ľ���ֵ
				if(node.getCurrent_depth()%2==1){
					//�����ʼ�ǵ�һ�㣬��ô��������alpha���ż������beta��С
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
					//��֦���
					if(node.getCurrent_depth()%2==1) return node.getAlpha();
					else return node.getBeta();
				}
			}
		}

		//���շ���
		if(node.getCurrent_depth()%2==1){
			return node.getAlpha();
		}
		else{
			return node.getBeta();
		}
	}
	
	class Node{
		//��������ʽ���洢������
		private int current_depth;//��ǰ�Ĳ���
		
		private int[][] data;//ÿ���Ӧ������
		
		private int[] axis;//�ƶ�����
		
		private int Alpha;
		
		private int Beta;
		
		private List<Node> childern;//���ӽڵ㼯��
		
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
