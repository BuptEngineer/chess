package mychess.util;

/**
 * 这是工具类中公共部分
 */
public class Common {
	public static int MODE;//游戏模式,1表示单机模式,2表示联网模式,3表示人机模式,4表示机机模式
	
	public static boolean isRed;//单机是否为红方
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

	public static int getMODE() {
		return MODE;
	}

	public static void setMODE(int mODE) {
		MODE = mODE;
	}
}