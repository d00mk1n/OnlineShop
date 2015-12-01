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
import com.webapps.os.shared.Product;

final class ShowProductListHandler implements ClickHandler {
	private final static ProductDBServiceAsync productDBService = GWT.create(ProductDBService.class);

	@Override
	public void onClick(ClickEvent event) {
		productDBService.getAllProducts(new AsyncCallback<List<Product>>() {

			@Override
			public void onSuccess(List<Product> result) {
				OnlineShop.logger.log(Level.INFO, "Successfully updated orders table");
				OnlineShop.centralVPanel.clear();
				for (Product product : result) {
					drawProduct(product);
				}

				Button delProductButton = new Button("Delete a product");
				Button addProductButton = new Button("Add new product");
				delProductButton.addClickHandler(new ClickHandler() {

					@Override
					public void onClick(ClickEvent event) {
						final DialogBox deleteProductBox = new DialogBox();
						VerticalPanel deleteProductBoxPanel = new VerticalPanel();
						final TextBox prodToDeleteField = new TextBox();
						Button confDelProdButton = new Button("Delete product with this ID");
						Button closeDelProdButton = new Button("Close");
						closeDelProdButton.addClickHandler(new ClickHandler() {

							@Override
							public void onClick(ClickEvent event) {
								deleteProductBox.hide();

							}
						});
						confDelProdButton.addClickHandler(new ClickHandler() {

							@Override
							public void onClick(ClickEvent event) {
								productDBService.deleteProduct(Integer.decode(prodToDeleteField.getText()),
										new AsyncCallback<Boolean>() {

									@Override
									public void onFailure(Throwable caught) {
										OnlineShop.logger.log(Level.SEVERE, "Failed to connect to the Database");
										OnlineShop.indexHandler.drawRPCErrorBox();
									}

									@Override
									public void onSuccess(Boolean result) {
										OnlineShop.logger.log(Level.INFO, "Successfully deleted a product");
										deleteProductBox.hide();
										DialogBox delSuccBox = new DialogBox();
										delSuccBox.setAnimationEnabled(true);
										delSuccBox.setAutoHideEnabled(true);
										delSuccBox.setText("Success");
										delSuccBox.add(new Label("Product succesfuly deleted!"));
										delSuccBox.center();

									}
								});

							}
						});
						deleteProductBoxPanel.add(new MyLabel("Enter product id"));
						deleteProductBoxPanel.add(prodToDeleteField);
						deleteProductBoxPanel.add(confDelProdButton);
						deleteProductBoxPanel.add(closeDelProdButton);
						deleteProductBox.add(deleteProductBoxPanel);
						deleteProductBox.center();

					}

				});
				addProductButton.addClickHandler(new ClickHandler() {

					@Override
					public void onClick(ClickEvent event) {
						final DialogBox newProductBox = new DialogBox();
						VerticalPanel npp = new VerticalPanel();
						Button sendNewProductButton = new Button("Add Product");
						Button closeNewProductButton = new Button("Close");
						final TextBox productNameField = new TextBox();
						final TextBox productPriceField = new TextBox();
						closeNewProductButton.addClickHandler(new ClickHandler() {

							@Override
							public void onClick(ClickEvent event) {
								newProductBox.hide();

							}
						});
						sendNewProductButton.addClickHandler(new ClickHandler() {

							@Override
							public void onClick(ClickEvent event) {
								Product newProduct = new Product(productNameField.getText(),
										Integer.decode(productPriceField.getText()));
								productDBService.addProduct(newProduct, new AsyncCallback<Boolean>() {

									@Override
									public void onFailure(Throwable caught) {
										OnlineShop.logger.log(Level.SEVERE, "Failed to connect to the Database");
										OnlineShop.indexHandler.drawRPCErrorBox();
									}

									@Override
									public void onSuccess(Boolean result) {
										OnlineShop.logger.log(Level.INFO, "Successfully added a product");
										newProductBox.hide();
										DialogBox prodAddBox = new DialogBox();
										prodAddBox.setAnimationEnabled(true);
										prodAddBox.setAutoHideEnabled(true);
										prodAddBox.setText("Success");
										prodAddBox.add(new Label("Product successfully added!"));
										prodAddBox.center();
									}
								});
							}
						});
						npp.add(new MyLabel("Product Name"));
						npp.add(productNameField);
						npp.add(new MyLabel("Product Price"));
						npp.add(productPriceField);
						npp.add(sendNewProductButton);
						npp.add(closeNewProductButton);
						newProductBox.add(npp);
						newProductBox.center();
					}
				});
				OnlineShop.centralVPanel.add(addProductButton);
				OnlineShop.centralVPanel.add(delProductButton);
			}

			private void drawProduct(Product product) {
				HorizontalPanel horpan = new HorizontalPanel();
				horpan.setSpacing(5);
				horpan.add(new MyLabel(Integer.toString(product.getId())));
				horpan.add(new MyLabel(product.getName()));
				horpan.add(new MyLabel(String.valueOf(product.getPrice())));
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