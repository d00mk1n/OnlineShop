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
import com.google.gwt.user.client.ui.PasswordTextBox;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.webapps.os.shared.Product;
import com.webapps.os.shared.User;

class IndexHandler implements ClickHandler {

	private final static OrdersDBServiceAsync ordersDBService = GWT.create(OrdersDBService.class);
	private final static ProductDBServiceAsync productDBService = GWT.create(ProductDBService.class);
	private final static UserDBServiceAsync userDBService = GWT.create(UserDBService.class);

	private DialogBox rpcErrorBox = new DialogBox();
	private Button toCartButton = new Button("To cart");
	private Button loginButton = new Button("Login");
	private Button logoutButton = new Button("Log out");
	private Button toProfileButton = new Button("To User Profile");
	private Button registerBoxButton = new Button("Register");
	private TextBox loginField = new TextBox();
	private PasswordTextBox passwordField = new PasswordTextBox();

	public TextBox getLoginField() {
		return loginField;
	}

	public PasswordTextBox getPasswordField() {
		return passwordField;
	}

	public DialogBox getRpcErrorBox() {
		return rpcErrorBox;
	}

	@Override
	public void onClick(ClickEvent event) {
		displayMainUI();
	}

	void displayMainUI() {

		OnlineShop.rightVPanel.clear();
		OnlineShop.centralVPanel.clear();
		OnlineShop.leftVPanel.clear();

		if (OnlineShop.loggedUser == null) {

			loginField.getElement().setId("loginField");
			loginField.setText("login");
			passwordField.setText("password");
			loginField.selectAll();

			OnlineShop.rightVPanel.add(loginField);
			OnlineShop.rightVPanel.add(passwordField);
			OnlineShop.rightVPanel.add(loginButton);

			loginButton.addClickHandler(OnlineShop.loginHandler);

			OnlineShop.rightVPanel.add(new Label("Not a member yet? Register now!"));
			OnlineShop.rightVPanel.add(registerBoxButton);
			registerBoxButton.addClickHandler(new ClickHandler() {

				@Override
				public void onClick(ClickEvent event) {
					drawRegistrationBox();
				}

				private void drawRegistrationBox() {
					final DialogBox registrationBox = new DialogBox();
					registrationBox.setText("Registration");
					registrationBox.setAnimationEnabled(true);
					final TextBox registrationLogin = new TextBox();
					final PasswordTextBox registrationPass1 = new PasswordTextBox();
					final PasswordTextBox registrationPass2 = new PasswordTextBox();

					final Button sendRegisterButton = new Button("Register");
					sendRegisterButton.addClickHandler(new ClickHandler() {

						@Override
						public void onClick(ClickEvent event) {
							if (registrationLogin.getText().length() > 3 && registrationPass1.getText().length() > 3) {
								if (registrationPass1.getText() == registrationPass2.getText()) {
									User applicant = new User(registrationLogin.getText(), registrationPass1.getText());
									userDBService.registerAttempt(applicant, new AsyncCallback<Boolean>() {
										@Override
										public void onFailure(Throwable caught) {
											OnlineShop.logger.log(Level.SEVERE, "Failed to connect to the Database");
											OnlineShop.indexHandler.drawRPCErrorBox();
										}

										@Override
										public void onSuccess(Boolean result) {
											OnlineShop.logger.log(Level.INFO, "Successfully added a new user");
											registrationLogin.setText("");
											registrationPass1.setText("");
											registrationPass2.setText("");
											registrationBox.hide();
											final DialogBox successBox = new DialogBox();
											successBox.setAnimationEnabled(true);
											successBox.setText("Success");
											VerticalPanel successVPanel = new VerticalPanel();
											final Button successButton = new Button("Close");
											successVPanel
													.add(new Label("You can now log in using you login and password"));
											successVPanel.add(successButton);
											successBox.add(successVPanel);
											successButton.addClickHandler(new ClickHandler() {
												@Override
												public void onClick(ClickEvent event) {
													successBox.hide();
												}
											});
											successBox.center();
										}
									});
								} else {
									showPasswordErrorBox();
								}
							} else {
								showInvalidInputErorrBox();
							}
						}

						private void showInvalidInputErorrBox() {
							final DialogBox invalidInputErrorBox = new DialogBox();
							VerticalPanel invalidInputErrorBoxVPanel = new VerticalPanel();
							invalidInputErrorBox.add(invalidInputErrorBoxVPanel);
							invalidInputErrorBox.setAnimationEnabled(true);
							invalidInputErrorBox.setText("Error");

							final Button closeInvalidInputErrorBoxButton = new Button("Close");
							closeInvalidInputErrorBoxButton.addClickHandler(new ClickHandler() {
								@Override
								public void onClick(ClickEvent event) {
									invalidInputErrorBox.hide();
								}
							});
							invalidInputErrorBoxVPanel
									.add(new Label("login and password should be at least 4 characters"));
							invalidInputErrorBoxVPanel.add(closeInvalidInputErrorBoxButton);
							invalidInputErrorBox.center();

						}

						private void showPasswordErrorBox() {
							final DialogBox passwordsErrorBox = new DialogBox();
							VerticalPanel passwordsErrorBoxVPanel = new VerticalPanel();
							passwordsErrorBox.add(passwordsErrorBoxVPanel);
							passwordsErrorBox.setAnimationEnabled(true);
							passwordsErrorBox.setText("Error");

							final Button closePasswordsErrorBoxButton = new Button("Close");
							closePasswordsErrorBoxButton.addClickHandler(new ClickHandler() {
								@Override
								public void onClick(ClickEvent event) {
									passwordsErrorBox.hide();
								}
							});
							passwordsErrorBoxVPanel.add(new Label("Passwords don't match"));
							passwordsErrorBoxVPanel.add(closePasswordsErrorBoxButton);
							passwordsErrorBox.center();

						}
					});
					final Button closeRegistrationBoxButton = new Button("Close");
					closeRegistrationBoxButton.addClickHandler(new ClickHandler() {
						@Override
						public void onClick(ClickEvent event) {
							registrationBox.hide();
						}
					});
					final VerticalPanel dialogVPanel = new VerticalPanel();
					dialogVPanel.add(new Label("Enter your Login and Password"));
					dialogVPanel.setHorizontalAlignment(VerticalPanel.ALIGN_RIGHT);
					HorizontalPanel horLogin = new HorizontalPanel();
					horLogin.add(new MyLabel("Login"));
					horLogin.add(registrationLogin);
					HorizontalPanel horPass1 = new HorizontalPanel();
					horPass1.add(new MyLabel("Password"));
					horPass1.add(registrationPass1);
					HorizontalPanel horPass2 = new HorizontalPanel();
					horPass2.add(new MyLabel("Repeat password"));
					horPass2.add(registrationPass2);
					dialogVPanel.add(horLogin);
					dialogVPanel.add(horPass1);
					dialogVPanel.add(horPass2);
					dialogVPanel.add(sendRegisterButton);
					dialogVPanel.add(closeRegistrationBoxButton);
					registrationBox.add(dialogVPanel);
					registrationBox.center();

				}
			});

		} else {
			OnlineShop.rightVPanel.add(new Label("Hello, " + OnlineShop.loggedUser.getLogin()));
			OnlineShop.rightVPanel.add(toProfileButton);
			OnlineShop.rightVPanel.add(toCartButton);
			OnlineShop.rightVPanel.add(logoutButton);

			logoutButton.addClickHandler(new ClickHandler() {
				@Override
				public void onClick(ClickEvent event) {
					OnlineShop.loggedUser = null;
					OnlineShop.indexHandler.displayMainUI();
				}
			});

			toProfileButton.addClickHandler(new ClickHandler() {
				@Override
				public void onClick(ClickEvent event) {
					LoginHandler.displayUserUI(OnlineShop.loggedUser);
				}
			});

			toCartButton.addClickHandler(new ClickHandler() {

				@Override
				public void onClick(ClickEvent event) {
					if (!OnlineShop.Cart.isEmpty()) {

						final DialogBox cartBox = new DialogBox();
						final VerticalPanel cartVPanel = new VerticalPanel();
						cartVPanel.setSpacing(10);
						Button finishOrderButton = new Button("Finish Order");
						finishOrderButton.addClickHandler(new ClickHandler() {

							@Override
							public void onClick(ClickEvent event) {

								if (!OnlineShop.loggedUser.isBlackList()) {
									ordersDBService.addNewOrder(OnlineShop.loggedUser.getId(), OnlineShop.Cart,
											new AsyncCallback<Boolean>() {

										@Override
										public void onSuccess(Boolean result) {
											OnlineShop.logger.log(Level.INFO, "Successfully added a new order");
											cartBox.hide();
											new IndexHandler().displayMainUI();
											OnlineShop.Cart.clear();
											DialogBox successOrderBox = new DialogBox();
											successOrderBox.setAutoHideEnabled(true);
											successOrderBox.setAnimationEnabled(true);
											successOrderBox.setText("Success");
											successOrderBox.add(new Label("Order added succesfully!"));
											successOrderBox.center();
										}

										@Override
										public void onFailure(Throwable caught) {
											OnlineShop.logger.log(Level.SEVERE, "Failed to connect to the Database");
											OnlineShop.indexHandler.drawRPCErrorBox();
										}
									});

								} else {
									cartBox.hide();
									new IndexHandler().displayMainUI();
									OnlineShop.Cart.clear();
									DialogBox errorOrderBox = new DialogBox();
									errorOrderBox.setAutoHideEnabled(true);
									errorOrderBox.setAnimationEnabled(true);
									errorOrderBox.setText("Error");
									errorOrderBox.add(new Label("Sorry, users from BlackList cannot create orders"));
									errorOrderBox.center();
								}
							}
						});
						Button closeCartButton = new Button("Close");
						closeCartButton.addClickHandler(new ClickHandler() {

							@Override
							public void onClick(ClickEvent event) {
								cartBox.hide();

							}
						});
						cartVPanel.add(new MyLabel("In cart right now"));
						final HorizontalPanel topRow = new HorizontalPanel();
						topRow.add(new MyLabel("Product"));
						topRow.add(new MyLabel("Price"));
						cartVPanel.add(topRow);
						for (Product product : OnlineShop.Cart) {
							HorizontalPanel horpan = new HorizontalPanel();
							horpan.add(new MyLabel(product.getName()));
							horpan.add(new MyLabel(String.valueOf(product.getPrice())));
							cartVPanel.add(horpan);
						}
						cartVPanel.add(finishOrderButton);
						cartVPanel.add(closeCartButton);
						cartBox.add(cartVPanel);
						cartBox.center();

					} else {
						DialogBox emptyCartErrorBox = new DialogBox();
						emptyCartErrorBox.setAutoHideEnabled(true);
						emptyCartErrorBox.setAnimationEnabled(true);
						emptyCartErrorBox.setText("Error");
						emptyCartErrorBox.add(new Label("Your cart is currently empty!"));
						emptyCartErrorBox.center();
					}
				}
			});
		}

		productDBService.getAllProducts(new AsyncCallback<List<Product>>() {

			@Override
			public void onSuccess(List<Product> products) {
				OnlineShop.logger.log(Level.INFO, "Successfully got the list of products");
				OnlineShop.centralVPanel.clear();
				HorizontalPanel horpan = new HorizontalPanel();
				horpan.add(new MyLabel("PRODUCT"));
				horpan.add(new MyLabel("PRICE"));
				OnlineShop.centralVPanel.add(horpan);
				for (Product product : products) {
					addProduct(product);
				}
			}

			@Override
			public void onFailure(Throwable caught) {
				OnlineShop.logger.log(Level.SEVERE, "Failed to connect to the Database");
				OnlineShop.indexHandler.drawRPCErrorBox();
			}
		});
	}

	private void addProduct(final Product product) {
		final HorizontalPanel horpan = new HorizontalPanel();
		horpan.setSpacing(5);
		horpan.add(new MyLabel(product.getName()));
		horpan.add(new MyLabel(String.valueOf(product.getPrice())));
		Button productToCartButton = new Button("Add to cart");
		horpan.add(productToCartButton);
		OnlineShop.centralVPanel.add(horpan.asWidget());
		productToCartButton.addClickHandler(new ClickHandler() {

			@Override
			public void onClick(ClickEvent event) {
				if (!(OnlineShop.loggedUser == null)) {
					OnlineShop.Cart.add(product);
					horpan.clear();
				} else {
					DialogBox noLoginBuyBox = new DialogBox();
					noLoginBuyBox.setAutoHideEnabled(true);
					noLoginBuyBox.setAnimationEnabled(true);
					noLoginBuyBox.setText("Error");
					noLoginBuyBox.add(new Label("You should log in first!"));
					noLoginBuyBox.center();
				}
			}
		});
	}

	void drawRPCErrorBox() {
		rpcErrorBox.setAnimationEnabled(true);
		rpcErrorBox.setText("Remote Procedure Call - Failure");
		VerticalPanel rpcErrorPanel = new VerticalPanel();
		rpcErrorPanel.add(new Label("An error occurred while "
				+ "attempting to contact the server. Please check your network connection and try again."));
		Button closeRPCErrorBoxButton = new Button("Close");
		rpcErrorPanel.add(closeRPCErrorBoxButton);
		rpcErrorBox.center();
		closeRPCErrorBoxButton.addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				rpcErrorBox.hide();
			}
		});
	}
}