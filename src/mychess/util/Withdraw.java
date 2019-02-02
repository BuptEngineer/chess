package mychess.util;

import java.util.ArrayList;
import java.util.List;

import mychess.entity.Message;

/**
 * ���幦��
 * 2019��1��6�� ����7:52:08
 */
public class Withdraw {
	//�ռ����ݱ�
	private List<Message> messages;
	
	public Withdraw() {
		// TODO Auto-generated constructor stub
		messages=new ArrayList<Message>();
	}
	
	public void add(Message message) {
		messages.add(message);
	}
	
	public void remove() {
		messages.remove(messages.size()-1);
	}
	
	public Message getLast() {
		return messages.get(messages.size()-1);
	}
	
	public int allSteps() {
		return messages.size();
	}
}
