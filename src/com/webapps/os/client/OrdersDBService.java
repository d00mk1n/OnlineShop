package com.webapps.os.client;

import java.util.List;

import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;
import com.webapps.os.shared.Order;
import com.webapps.os.shared.Product;
@RemoteServiceRelativePath("ordersDB")
public interface OrdersDBService extends RemoteService {

	List<Order> getUserOrders(int userId);
	
	List<Order> getUnpaidUserOrders(int userId);
	
	List<String> getOrderProductsNames (int orderId);

	boolean addNewOrder(int userId, List<Product> cart);

	boolean payOrder(int id);

}
