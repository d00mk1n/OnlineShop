package com.webapps.os.server;

import java.util.List;

import com.google.gwt.user.server.rpc.RemoteServiceServlet;
import com.webapps.os.client.UserDBService;
import com.webapps.os.shared.User;

@SuppressWarnings("serial")

public class UserDBServiceImpl extends RemoteServiceServlet implements UserDBService {
	
	@Override
	public boolean registerAttempt(User applicant) {
		AccessDB.addUser(applicant);
		return true;
	}

	@Override
	public List<User> getAllUsers() {
		return AccessDB.getAllUsers();
	}


	@Override
	public boolean makeAdmin(int userId) {
		AccessDB.makeAdmin(userId);
		return true;
	}

	@Override
	public boolean addToBlackList(int userId) {
		AccessDB.addToBlackList(userId);
		return true;
	}
}
