package mychess.entity;

import java.io.Serializable;

/**
 * һ����Ϣ
 * 2019��1��3�� ����10:10:34
 */
public class NormalMessage extends Message implements Serializable{
	private static final long serialVersionUID = 1L;
	public String attach;//������Ϣ

	public String getAttach() {
		return attach;
	}

	public void setAttach(String attach) {
		this.attach = attach;
	}	
}
