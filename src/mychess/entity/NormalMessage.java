package mychess.entity;

import java.io.Serializable;

/**
 * 一般消息
 * 2019年1月3日 下午10:10:34
 */
public class NormalMessage extends Message implements Serializable{
	private static final long serialVersionUID = 1L;
	public String attach;//附加消息

	public String getAttach() {
		return attach;
	}

	public void setAttach(String attach) {
		this.attach = attach;
	}	
}
