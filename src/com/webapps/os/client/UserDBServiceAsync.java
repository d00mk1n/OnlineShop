package com.webapps.os.client;

import java.util.List;

import com.google.gwt.user.client.rpc.AsyncCallback;
//import com.webapps.os.shared.RegistrationRequest;
import com.webapps.os.shared.User;

public interface UserDBServiceAsync {

	void getAllUsers(AsyncCallback<List<User>> callback);
	
	void registerAttempt(User applicant, AsyncCallback<Boolean> asyncCallback);
	
	void makeAdmin(int userId, AsyncCallback<Boolean> asyncCallback);
	
	void addToBlackList(int id, AsyncCallback<Boolean> asyncCallback);

}
