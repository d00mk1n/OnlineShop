package com.webapps.os.client;

import java.util.List;
import java.util.logging.Level;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.DialogBox;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.webapps.os.shared.Order;
import com.webapps.os.shared.User;

class LoginHandler implements ClickHandler {

	private final static UserDBServiceAsync userDBService = GWT.create(UserDBService.class);
	private final static OrdersDBServiceAsync ordersDBService = GWT.create(OrdersDBService.class);

	public void onClick(ClickEvent event) {
		final String userLogin = OnlineShop.indexHandler.getLoginField().getText();
		final String userPass = OnlineShop.indexHandler.getPasswordField().getText();
		userDBService.getAllUsers(new AsyncCallback<List<User>>() {

			@Override
			public void onSuccess(List<User> users) {
				OnlineShop.logger.log(Level.INFO, "Successfully got the list of all users");
				for (User user : users) {

					if (user.getLogin() == userLogin && userPass == user.getPassword()) {
						OnlineShop.loggedUser = user;
						if (!user.isAdmin()) {
							displayUserUI(user);
						} else {
							displayAdminUI(user);
						}
						return;
					}
				}
				showLoginErrorBox();
			}

			@Override
			public void onFailure(Throwable caught) {
				OnlineShop.indexHandler.drawRPCErrorBox();
				OnlineShop.logger.log(Level.SEVERE, "Failed to connect to the Database");
			}
		});
	}

	private void showLoginErrorBox() {
		DialogBox loginErrorBox = new DialogBox();
		loginErrorBox.setAutoHideEnabled(true);
		loginErrorBox.setAnimationEnabled(true);
		loginErrorBox.setText("Error");
		loginErrorBox.add(new Label("No such login/password combination found"));
		loginErrorBox.center();
	}

	private static void displayAdminUI(User loggedUser) {
		OnlineShop.centralVPanel.clear();
		OnlineShop.rightVPanel.clear();
		OnlineShop.leftVPanel.clear();

		Button showProductListButton = new Button("Show the list of products");
		showProductListButton.addClickHandler(OnlineShop.showProductListHandler);
		Button showUserListButton = new Button("Show the list of Users");
		showUserListButton.addClickHandler(OnlineShop.showUserListHandler);
		Button backToMainButton = new Button("Back to main page");
		backToMainButton.addClickHandler(OnlineShop.indexHandler);
		OnlineShop.leftVPanel.add(backToMainButton);
		OnlineShop.rightVPanel.add(new MyLabel("Hello, " + loggedUser.getLogin()));
		OnlineShop.leftVPanel.add(showUserListButton);
		OnlineShop.leftVPanel.add(showProductListButton);

	}

	static void displayUserUI(User loggedUser) {
		OnlineShop.centralVPanel.clear();
		OnlineShop.rightVPanel.clear();
		OnlineShop.leftVPanel.clear();
		OnlineShop.rightVPanel.add(new MyLabel("Welcome, " + loggedUser.getLogin()));
		Button backToMainButton = new Button("Back to main page");
		backToMainButton.addClickHandler(OnlineShop.indexHandler);
		OnlineShop.leftVPanel.add(backToMainButton);
		ordersDBService.getUserOrders(loggedUser.getId(), new AsyncCallback<List<Order>>() {

			@Override
			public void onSuccess(List<Order> orders) {
				OnlineShop.logger.log(Level.INFO, "Successfully got the orders of a user");
				OnlineShop.centralVPanel.clear();
				for (Order order : orders) {
					drawOrder(order);
				}
			}

			@Override
			public void onFailure(Throwable caught) {
				OnlineShop.logger.log(Level.SEVERE, "Failed to connect to the Database");
				OnlineShop.indexHandler.drawRPCErrorBox();
			}
		});
	}

	private static void drawOrder(final Order order) {
		HorizontalPanel horpan = new HorizontalPanel();
		horpan.setSpacing(5);
		horpan.add(new MyLabel(String.valueOf(order.getId())));
		final VerticalPanel orderProducts = new VerticalPanel();
		ordersDBService.getOrderProductsNames(order.getId(), new AsyncCallback<List<String>>() {

			@Override
			public void onSuccess(List<String> productNames) {
				OnlineShop.logger.log(Level.INFO, "Successfully got the list of products in an order");
				for (String productName : productNames) {
					orderProducts.add(new MyLabel(productName));
				}
			}

			@Override
			public void onFailure(Throwable caught) {
				OnlineShop.indexHandler.drawRPCErrorBox();
				OnlineShop.logger.log(Level.SEVERE, "Failed to connect to the Database");
			}
		});

		horpan.add(orderProducts);
		OnlineShop.centralVPanel.add(horpan);
		if (!order.isPaid()) {
			Button payButton = new Button("Pay");
			horpan.add(payButton);
			payButton.addClickHandler(new ClickHandler() {

				@Override
				public void onClick(ClickEvent event) {
					ordersDBService.payOrder(order.getId(), new AsyncCallback<Boolean>() {

						@Override
						public void onFailure(Throwable caught) {
							OnlineShop.logger.log(Level.SEVERE, "Failed to connect to the Database");
							OnlineShop.indexHandler.drawRPCErrorBox();

						}

						@Override
						public void onSuccess(Boolean result) {
							OnlineShop.logger.log(Level.INFO, "Successfully updated orders table");
							LoginHandler.displayUserUI(OnlineShop.loggedUser);
							DialogBox orderPaidBox = new DialogBox();
							orderPaidBox.setAnimationEnabled(true);
							orderPaidBox.setAutoHideEnabled(true);
							orderPaidBox.setText("Success");
							orderPaidBox.add(new Label("Product successfully added!"));
							orderPaidBox.center();
						}
					});
				}
			});
		} else {
			horpan.add(new MyLabel("Order is paid"));
		}
	}
}