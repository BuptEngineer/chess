package mychess.entity;

public enum Code {
	//��Ϣ״̬����
	Normal(200,"����"),Redo(201,"����"),Aggree(500,"ͬ�����"),Refuse(501,"��ͬ�����"),
	Free(100,"����"),Over(104,"����"),Run(103,"����"),Prepare(102,"׼��");
	
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
