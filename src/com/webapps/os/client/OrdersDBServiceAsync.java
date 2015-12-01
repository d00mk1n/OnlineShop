package com.webapps.os.client;

import java.util.List;

import com.google.gwt.user.client.rpc.AsyncCallback;
import com.webapps.os.shared.Order;
import com.webapps.os.shared.Product;

public interface OrdersDBServiceAsync {
	
	void getUserOrders(int userId, AsyncCallback<List<Order>> callback);
	
	void getUnpaidUserOrders(int userId, AsyncCallback<List<Order>> callback);
	
	void getOrderProductsNames(int orderId, AsyncCallback<List<String>> callback);
	
	void addNewOrder(int userId, List<Product> cart, AsyncCallback<Boolean> asyncCallback);
	
	void payOrder(int id, AsyncCallback<Boolean> asyncCallback);

}
