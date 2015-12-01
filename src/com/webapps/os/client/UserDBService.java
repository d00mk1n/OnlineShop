package com.webapps.os.client;

import java.util.List;

import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;
import com.webapps.os.shared.User;
@RemoteServiceRelativePath("userDB")
public interface UserDBService extends RemoteService {

	List<User> getAllUsers();
	
	boolean registerAttempt(User applicant);

	boolean makeAdmin(int userId);

	boolean addToBlackList(int userId);

}
