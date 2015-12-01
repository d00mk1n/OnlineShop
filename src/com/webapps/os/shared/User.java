package com.webapps.os.shared;

import java.io.Serializable;

@SuppressWarnings("serial")
public class User implements Serializable {

	private int id;
	private String login;
	private String password;
	private boolean isAdmin;
	private boolean isBlackList;

	public User(Integer id, String login, String password, Boolean isAdmin, Boolean isBlackList) {
		this.id = id;
		this.login = login;
		this.password = password;
		this.isAdmin = isAdmin;
		this.isBlackList = isBlackList;

	}

	public User() {

	}

	public User(String login, String pass) {
		this.setLogin(login);
		this.setPassword(pass);
	}

	public String getLogin() {
		return login;
	}

	public void setLogin(String login) {
		this.login = login;
	}

	public String getPassword() {
		System.out.println("got pass");
		return password;
	}

	public void setPassword(String pass) {
		this.password = pass;
	}

	public boolean isAdmin() {
		System.out.println("got admin");
		return isAdmin;
	}

	public void setAdmin(boolean isAdmin) {
		this.isAdmin = isAdmin;
	}

	public boolean isBlackList() {
		return isBlackList;
	}

	public void setBlackList(boolean isBlackList) {
		this.isBlackList = isBlackList;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

}
