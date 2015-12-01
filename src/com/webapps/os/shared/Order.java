package com.webapps.os.shared;

import java.io.Serializable;

@SuppressWarnings("serial")
public class Order implements Serializable {
	
	private int id;
	private int userId;
	private boolean isPaid;
	
	public Order (Integer id, Boolean isPaid, Integer userId) {
		this.id = id;
		this.userId = userId;
		this.isPaid = isPaid;
		
	}
	
	public Order() {
		
	}
	
	
	
	public int getUser_id() {
		return userId;
	}
	public void setUser_id(int userId) {
		this.userId = userId;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public boolean isPaid() {
		return isPaid;
	}
	public void setPaid(boolean isPaid) {
		this.isPaid = isPaid;
	}

}
