package mychess.function;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.List;
import javax.swing.JFileChooser;
import javax.swing.filechooser.FileFilter;

public class Replay {
	public static void WriteFile(List<int[][]> list,String filename) {
		try(ObjectOutputStream oos=new ObjectOutputStream(new FileOutputStream(filename))) {
			oos.writeObject(list);
			oos.close();
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
	}
	
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
		return retList;
	}
	
	public static String Open_Get_Filepath() {
		JFileChooser jf=new JFileChooser(new File("file"));
		jf.setFileFilter(new ChooseFile());
		File file=null;
		if(jf.showOpenDialog(null)==JFileChooser.APPROVE_OPTION){
			file=jf.getSelectedFile();
		}
		return file==null ? null : file.getAbsolutePath();
	}
	
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
