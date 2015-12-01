package com.webapps.os.shared;

public class OrdProd {
	
	private int id;
	private int prodId;
	private int ordId;
	
	public OrdProd(){
		
	}
	
	public OrdProd(int prodId, int ordId) {
		this.prodId = prodId;
		this.ordId = ordId;
		
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getProd_id() {
		return prodId;
	}
	public void setProd_id(int prodId) {
		this.prodId = prodId;
	}
	public int getUser_id() {
		return ordId;
	}
	public void setUser_id(int ordId) {
		this.ordId = ordId;
	}

}
