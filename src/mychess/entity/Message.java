package mychess.entity;

import java.io.Serializable;

/**
 * ����ͳһ���ĸ�ʽ�����ڿͻ��˺ͷ������˽���
 * 2019��1��3�� ����9:55:29
 */
public class Message implements Serializable,Cloneable{
	private static final long serialVersionUID = 1L;

	public String sourceIp;//��Ϣ������Դip��ַ
	
	public String targetIp;//�������Է���ip��ַ
	
	public Code code;//��Ϣ״̬���ţ�����ϵͳ��Ϣ���ţ��û���Ϣ�����Լ���Ϸ���̴��ŵ�
	
	public boolean isValid=true;//�ñ����Ƿ���Ȩ��Ч
	
	public byte role;//��ǰ��ɫ
	
	public boolean yourTurn;//�Ƿ�����Ļغ�

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
