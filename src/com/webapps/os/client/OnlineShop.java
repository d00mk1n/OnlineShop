package com.webapps.os.client;

import java.util.LinkedList;
import java.util.List;
import java.util.logging.Logger;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.webapps.os.shared.Product;
import com.webapps.os.shared.User;

public class OnlineShop implements EntryPoint {

	static Logger logger = Logger.getLogger("logger");

	static List<Product> Cart = new LinkedList<Product>();

	static User loggedUser;

	static VerticalPanel rightVPanel;
	static VerticalPanel leftVPanel;
	static VerticalPanel centralVPanel;

	static IndexHandler indexHandler;
	static LoginHandler loginHandler;
	static ShowProductListHandler showProductListHandler;
	static ShowUserListHandler showUserListHandler;

	public void onModuleLoad() {

		rightVPanel = new VerticalPanel();
		leftVPanel = new VerticalPanel();
		centralVPanel = new VerticalPanel();
		rightVPanel.setSpacing(5);
		leftVPanel.setSpacing(5);
		centralVPanel.setSpacing(5);

		RootPanel.get("LeftContainer").add(leftVPanel.asWidget());
		RootPanel.get("RightContainer").add(rightVPanel.asWidget());
		RootPanel.get("CenterContainer").add(centralVPanel.asWidget());

		showUserListHandler = new ShowUserListHandler();
		showProductListHandler = new ShowProductListHandler();
		loginHandler = new LoginHandler();
		indexHandler = new IndexHandler();
		indexHandler.displayMainUI();

	}
}
