package mychess.util;

public class Common {
	public static int[][] Backup(int[][] data) {
		int[][] sub=new int[data.length][data[0].length];
		for(int i=0;i<data.length;i++){
			for(int j=0;j<data[0].length;j++){
				sub[i][j]=data[i][j];//深度复制
			}
		}
		return sub;
	}
	
	public static int[][] String_to_Array(String temp) {
		String[] lines=temp.split("\n");
		int[][] datasub=new int[10][9];
		for(int i=0;i<lines.length;i++){
			String[] inner=lines[i].trim().split(" ");
			for(int j=0;j<inner.length;j++){
				datasub[i][j]=Integer.parseInt(inner[j]);
			}
		}
		return datasub;
	}
	
	public static int[] FindDiff(int[][] data1,int[][] data2,boolean isRed) {//可优化
		int[] aixs=new int[4];
		for(int i=0;i<data1.length;i++)
			for(int j=0;j<data1[i].length;j++)
				if(data1[i][j]!=data2[i][j])
					if(data2[i][j]==0){
						aixs[0]=i+1;
						aixs[1]=j+1;
					}else{
						aixs[2]=i+1;
						aixs[3]=j+1;
					}
//		if(isRed){
//			//映射
//			aixs[0]=11-aixs[0];
//			aixs[2]=11-aixs[2];
//		}
		return aixs;
	}
}
