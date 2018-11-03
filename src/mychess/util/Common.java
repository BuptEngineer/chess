package mychess.util;

/**
 * 这是工具类中公共部分
 */
public class Common {
	/**
	 * 备份当前棋局数组，可以使用clone代替
	 * @param data是当前棋局
	 */
	public static int[][] Backup(int[][] data) {
		int[][] sub=new int[data.length][data[0].length];
		for(int i=0;i<data.length;i++){
			for(int j=0;j<data[0].length;j++){
				sub[i][j]=data[i][j];//深度复制
			}
		}
		return sub;
	}
	
	/**
	 * 将字符串转换为数组
	 * @param temp是给的字符串
	 */
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
	
	/**
	 * 将数组转换为字符串
	 * @param data是棋局数组
	 */
	public static String Array_to_String(int[][] data) {
		String temp="";
		for(int i=0;i<data.length;i++){
			for(int j=0;j<data[i].length;j++){
				temp+=data[i][j]+" ";
			}
			temp+="\n";
		}
		return temp;
	}
	
	/**
	 * 这是对于两个给的数组，发现其中不同的点，将不同点转化为两对坐标组成数组
	 * @param
	 */
	public static int[] FindDiff(int[][] data1,int[][] data2,boolean isRed) {//可优化
		int[] aixs=new int[5];
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
		return aixs;
	}
}
