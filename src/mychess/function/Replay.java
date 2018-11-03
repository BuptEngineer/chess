package mychess.function;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.List;
import javax.swing.JFileChooser;
import javax.swing.filechooser.FileFilter;

/**
 * 这是象棋的复盘功能类，主要是依据保存的象棋文件,
 * 进行自动播放
 */
public class Replay {
	/**
	 * 将已有的棋局列表写到文件中进行保存
	 * @param list是已有的棋局列表，filename是保存的文件名
	 */
	public static void WriteFile(List<int[][]> list,String filename) {
		try(ObjectOutputStream oos=new ObjectOutputStream(new FileOutputStream(filename))) {
			oos.writeObject(list);
			oos.close();
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
	}
	
	/**
	 * 读取象棋文件，获取其中的棋局列表
	 * @param filename是要读取的文件名
	 */
	@SuppressWarnings("unchecked")
	public static List<int[][]> ReadFile(String filename) {
		List<int[][]> retList=null;
		try(ObjectInputStream ois=new ObjectInputStream(new FileInputStream(filename))) {
			retList=(List<int[][]>) ois.readObject();
			ois.close();
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return retList;//返回棋局列表
	}
	
	/**
	 * 辅助方法，在文件选择器中选择文件后，返回所选的文件绝对路径<br>
	 * 其是用于获取文件名的，以便读取该文件名的数据
	 * @param
	 */
	public static String Open_Get_Filepath() {
		JFileChooser jf=new JFileChooser(new File("file"));
		jf.setFileFilter(new ChooseFile());
		File file=null;
		if(jf.showOpenDialog(null)==JFileChooser.APPROVE_OPTION){
			file=jf.getSelectedFile();//获取选择的文件
		}
		return file==null ? null : file.getAbsolutePath();
	}
	
	/**
	 * 辅助类，用于文件筛选即只能选择以.chess结尾的文件
	 * 用于上面的方法中
	 */
    static class ChooseFile extends FileFilter{

		@Override
		public boolean accept(File f) {
			// TODO Auto-generated method stub
			return f.getName().endsWith(".chess");
		}

		@Override
		public String getDescription() {
			// TODO Auto-generated method stub
			return "*.chess";
		}
	}
}
