package mychess.util;

import java.util.ArrayList;
import java.util.List;

import mychess.entity.Message;

/**
 * 悔棋功能
 * 2019年1月6日 下午7:52:08
 */
public class Withdraw {
	//收集数据报
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
