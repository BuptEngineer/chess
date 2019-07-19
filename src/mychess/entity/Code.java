package mychess.entity;

public enum Code {
	//消息状态代号
	Normal(200,"正常"),Redo(201,"撤销"),Aggree(500,"同意悔棋"),Refuse(501,"不同意悔棋"),
	Free(100,"空闲"),Over(104,"结束"),Run(103,"进行"),Prepare(102,"准备");
	
	private int id;
	private String des;
	
	private Code(int id,String des){
		this.id=id;
		this.des=des;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getDes() {
		return des;
	}

	public void setDes(String des) {
		this.des = des;
	}
}