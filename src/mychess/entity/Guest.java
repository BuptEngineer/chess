package mychess.entity;

import java.awt.Image;
import java.util.ArrayList;
import java.util.List;

/**
 * �ο�ʵ��
 * 2019��1��8�� ����11:19:08
 */
public class Guest extends User{
	private String Id;//��mac��ַhash����
	
	private Image image;//�ο�ͷ��
	
	private int score;//����
	
	private String signature; //����ǩ��
	
	private List<User> friends;//����
	
	public Guest() {
		// TODO Auto-generated constructor stub
		friends=new ArrayList<User>();
	}

	public String getId() {
		return Id;
	}

	public void setId(String id) {
		Id = id;
	}

	public Image getImage() {
		return image;
	}

	public void setImage(Image image) {
		this.image = image;
	}

	public int getScore() {
		return score;
	}

	public void setScore(int score) {
		this.score = score;
	}

	public String getSignature() {
		return signature;
	}

	public void setSignature(String signature) {
		this.signature = signature;
	}

	public List<User> getFriends() {
		return friends;
	}

	public void setFriends(List<User> friends) {
		this.friends = friends;
	}
}
