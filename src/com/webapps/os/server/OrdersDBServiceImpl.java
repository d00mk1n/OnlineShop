package com.webapps.os.server;

import java.util.List;

import com.google.gwt.user.server.rpc.RemoteServiceServlet;
import com.webapps.os.client.OrdersDBService;
import com.webapps.os.shared.Order;
import com.webapps.os.shared.Product;

@SuppressWarnings("serial")

public class OrdersDBServiceImpl extends RemoteServiceServlet implements OrdersDBService {

	@Override
	public List<Order> getUserOrders(int userId) {
		return AccessDB.getUserOrders(userId);
	}

	@Override
	public List<String> getOrderProductsNames(int orderId) {
		return AccessDB.getOrderProductsNames(orderId);
	}

	@Override
	public List<Order> getUnpaidUserOrders(int userId) {
		return AccessDB.getUnpaidUserOrders(userId);
	}

	@Override
	public boolean addNewOrder(int userId, List<Product> cart) {
		AccessDB.addNewOrder(userId, cart);
		return true;
	}

	@Override
	public boolean payOrder(int id) {
		AccessDB.payOrder(id);
		return true;
	}
}
