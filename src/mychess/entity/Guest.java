package mychess.entity;

import java.awt.Image;
import java.util.ArrayList;
import java.util.List;

/**
 * 游客实体
 * 2019年1月8日 下午11:19:08
 */
public class Guest extends User{
	private String Id;//由mac地址hash生成
	
	private Image image;//游客头像
	
	private int score;//积分
	
	private String signature; //个人签名
	
	private List<User> friends;//好友
	
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
