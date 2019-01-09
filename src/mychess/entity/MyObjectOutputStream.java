package mychess.entity;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.OutputStream;

public class MyObjectOutputStream extends ObjectOutputStream{

	protected MyObjectOutputStream() throws IOException, SecurityException {
		super();
		// TODO Auto-generated constructor stub
	}
	
	 public MyObjectOutputStream(OutputStream out) throws IOException {
		 super(out);
	 }
	 
	 //重写ObjectOutputStream的方法,解决invalid AC问题
	 protected void writeStreamHeader() throws IOException {
		  return;
	 } 
}
