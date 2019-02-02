package mychess.util;

/**
 * ���ǹ������й�������
 */
public class Common {
	public static int MODE;//��Ϸģʽ,1��ʾ����ģʽ,2��ʾ����ģʽ,3��ʾ�˻�ģʽ,4��ʾ����ģʽ
	
	public static boolean isRed;//�����Ƿ�Ϊ�췽
	/**
	 * ���ݵ�ǰ������飬����ʹ��clone����
	 * @param data�ǵ�ǰ���
	 */
	public static int[][] Backup(int[][] data) {
		int[][] sub=new int[data.length][data[0].length];
		for(int i=0;i<data.length;i++){
			for(int j=0;j<data[0].length;j++){
				sub[i][j]=data[i][j];//��ȸ���
			}
		}
		return sub;
	}

	/**
	 * ���ַ���ת��Ϊ����
	 * @param temp�Ǹ����ַ���
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
	 * ������ת��Ϊ�ַ���
	 * @param data���������
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
