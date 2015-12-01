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
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.webapps.os.shared.Order;
import com.webapps.os.shared.User;

final class ShowUserListHandler implements ClickHandler {
	private final static UserDBServiceAsync userDBService = GWT.create(UserDBService.class);
	private final static OrdersDBServiceAsync ordersDBService = GWT.create(OrdersDBService.class);

	@Override
	public void onClick(ClickEvent event) {
		userDBService.getAllUsers(new AsyncCallback<List<User>>() {

			@Override
			public void onSuccess(List<User> users) {
				OnlineShop.logger.log(Level.INFO, "Successfully got the list of users");
				OnlineShop.centralVPanel.clear();
				HorizontalPanel horpan = new HorizontalPanel();
				horpan.setSpacing(5);
				horpan.add(new MyLabel("User Login"));
				horpan.add(new MyLabel("User ID"));
				horpan.add(new MyLabel("Total orders"));
				horpan.add(new MyLabel("Unpaid Orders"));
				OnlineShop.centralVPanel.add(horpan);
				for (User user : users) {
					drawUser(user);
				}
				Button makeAdminBoxButton = new Button("Grant a user admin rights");
				Button addToBLBoxButton = new Button("Add user to blacklist");
				addToBLBoxButton.addClickHandler(new ClickHandler() {

					@Override
					public void onClick(ClickEvent event) {
						final DialogBox addToBLBox = new DialogBox();
						VerticalPanel addToBLBoxPanel = new VerticalPanel();
						final TextBox toBLId = new TextBox();
						Button closeAddToBLBox = new Button("Close");
						closeAddToBLBox.addClickHandler(new ClickHandler() {

							@Override
							public void onClick(ClickEvent event) {
								addToBLBox.hide();

							}
						});
						Button addToBL = new Button("Add to Blacklist");
						addToBL.addClickHandler(new ClickHandler() {

							@Override
							public void onClick(ClickEvent event) {

								userDBService.addToBlackList(Integer.decode(toBLId.getText()),
										new AsyncCallback<Boolean>() {

									@Override
									public void onFailure(Throwable caught) {
										OnlineShop.logger.log(Level.SEVERE, "Failed to connect to the Database");
										OnlineShop.indexHandler.drawRPCErrorBox();
									}

									@Override
									public void onSuccess(Boolean result) {
										OnlineShop.logger.log(Level.INFO, "Successfully updated users table");
										addToBLBox.hide();
										DialogBox madeAdmin = new DialogBox();
										madeAdmin.setAnimationEnabled(true);
										madeAdmin.setAutoHideEnabled(true);
										madeAdmin.setText("Success");
										madeAdmin.add(new Label("Added to Blacklist"));
										madeAdmin.center();

									}
								});

							}
						});
						addToBLBoxPanel.add(new Label("Enter the ID of the user to add to BlackList"));
						addToBLBoxPanel.add(toBLId);
						addToBLBoxPanel.add(addToBL);
						addToBLBoxPanel.add(closeAddToBLBox);
						addToBLBox.add(addToBLBoxPanel);
						addToBLBox.center();
					}

				});
				makeAdminBoxButton.addClickHandler(new ClickHandler() {

					@Override
					public void onClick(ClickEvent event) {
						final DialogBox makeAdminBox = new DialogBox();
						VerticalPanel makeAdminBoxPanel = new VerticalPanel();
						final TextBox newAdminID = new TextBox();
						Button closeMakeAdminBox = new Button("Close");
						closeMakeAdminBox.addClickHandler(new ClickHandler() {

							@Override
							public void onClick(ClickEvent event) {
								makeAdminBox.hide();

							}
						});
						Button makeAdmin = new Button("Make Admin");
						makeAdmin.addClickHandler(new ClickHandler() {

							@Override
							public void onClick(ClickEvent event) {

								userDBService.makeAdmin(Integer.decode(newAdminID.getText()),
										new AsyncCallback<Boolean>() {

									@Override
									public void onFailure(Throwable caught) {
										OnlineShop.logger.log(Level.SEVERE, "Failed to connect to the Database");
										OnlineShop.indexHandler.drawRPCErrorBox();
									}

									@Override
									public void onSuccess(Boolean result) {
										OnlineShop.logger.log(Level.INFO, "Successfully updated users table");
										makeAdminBox.hide();
										DialogBox madeAdmin = new DialogBox();
										madeAdmin.setAnimationEnabled(true);
										madeAdmin.setAutoHideEnabled(true);
										madeAdmin.setText("Success");
										madeAdmin.add(new Label("Admin rights granted"));
										madeAdmin.center();

									}
								});

							}
						});
						makeAdminBoxPanel.add(new Label("Enter the ID of the new admin"));
						makeAdminBoxPanel.add(newAdminID);
						makeAdminBoxPanel.add(makeAdmin);
						makeAdminBoxPanel.add(closeMakeAdminBox);
						makeAdminBox.add(makeAdminBoxPanel);
						makeAdminBox.center();
					}
				});
				OnlineShop.centralVPanel.add(makeAdminBoxButton);
				OnlineShop.centralVPanel.add(addToBLBoxButton);
			}

			private void drawUser(User user) {
				final HorizontalPanel horpan = new HorizontalPanel();
				horpan.setSpacing(5);
				horpan.add(new MyLabel(user.getLogin()));
				horpan.add(new MyLabel(String.valueOf(user.getId())));
				ordersDBService.getUserOrders(user.getId(), new AsyncCallback<List<Order>>() {
					@Override
					public void onFailure(Throwable caught) {
						OnlineShop.logger.log(Level.SEVERE, "Failed to connect to the Database");
						OnlineShop.indexHandler.drawRPCErrorBox();
					}

					@Override
					public void onSuccess(List<Order> result) {
						OnlineShop.logger.log(Level.INFO, "Successfully got the list of a user's orders");
						horpan.add(new MyLabel(String.valueOf(result.size())));
					}
				});
				ordersDBService.getUnpaidUserOrders(user.getId(), new AsyncCallback<List<Order>>() {
					@Override
					public void onFailure(Throwable caught) {
						OnlineShop.logger.log(Level.SEVERE, "Failed to connect to the Database");
						OnlineShop.indexHandler.drawRPCErrorBox();
					}

					@Override
					public void onSuccess(List<Order> result) {
						OnlineShop.logger.log(Level.INFO, "Successfully got the list of a user's unpaid orders");
						horpan.add(new MyLabel(String.valueOf(result.size())));
					}
				});

				OnlineShop.centralVPanel.add(horpan);
			}

			@Override
			public void onFailure(Throwable caught) {
				OnlineShop.logger.log(Level.SEVERE, "Failed to connect to the Database");
				OnlineShop.indexHandler.drawRPCErrorBox();
			}
		});
	}
}