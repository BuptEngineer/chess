package mychess.entity;

import java.io.Serializable;

/**
 * 定义统一报文格式，用于客户端和服务器端交互
 * 2019年1月3日 下午9:55:29
 */
public class Message implements Serializable,Cloneable{
	private static final long serialVersionUID = 1L;

	public String sourceIp;//消息触发的源ip地址
	
	public String targetIp;//发往给对方的ip地址
	
	public Code code;//消息状态代号，包括系统消息代号，用户消息代号以及游戏过程代号等
	
	public boolean isValid=true;//该报文是否授权有效
	
	public byte role;//当前角色
	
	public boolean yourTurn;//是否是你的回合

	public String getSourceIp() {
		return sourceIp;
	}

	public void setSourceIp(String sourceIp) {
		this.sourceIp = sourceIp;
	}

	public String getTargetIp() {
		return targetIp;
	}

	public void setTargetIp(String targetIp) {
		this.targetIp = targetIp;
	}

	public boolean isValid() {
		return isValid;
	}

	public void setValid(boolean isValid) {
		this.isValid = isValid;
	}

	public Code getCode() {
		return code;
	}

	public void setCode(Code code) {
		this.code = code;
	}

	public byte getRole() {
		return role;
	}

	public void setRole(byte role) {
		this.role = role;
	}

	public boolean isYourTurn() {
		return yourTurn;
	}

	public void setYourTurn(boolean yourTurn) {
		this.yourTurn = yourTurn;
	}
	
	public Message clone() throws CloneNotSupportedException {
		// TODO Auto-generated method stub
		return (Message) super.clone();
	}
}
