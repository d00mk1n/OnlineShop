package com.webapps.os.shared;

import java.io.Serializable;

@SuppressWarnings("serial")
public class Product implements Serializable {
	
	private int id;
	private String name;
	private int price;
	
	public Product (String name, Integer price) {
		this.name = name;
		this.price = price;
	}
	
	public Product() {
		
	}
	
	public Product (Integer id, String name, Integer price) {
		this.id = id;
		this.name = name;
		this.price = price;
	}
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public int getPrice() {
		return price;
	}
	public void setPrice(int price) {
		this.price = price;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

}
