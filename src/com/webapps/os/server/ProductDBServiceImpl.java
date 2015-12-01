package com.webapps.os.server;

import java.util.List;

import com.google.gwt.user.server.rpc.RemoteServiceServlet;
import com.webapps.os.client.ProductDBService;
import com.webapps.os.shared.Product;

@SuppressWarnings("serial")

public class ProductDBServiceImpl extends RemoteServiceServlet implements ProductDBService {
	

	@Override
	public List<Product> getAllProducts() {
		return AccessDB.getAllProducts();
	}

	@Override
	public boolean addProduct(Product product) {
		AccessDB.addProduct(product);
		return true;
	}

	@Override
	public boolean deleteProduct(int productId) {
		AccessDB.deleteProduct(productId);
		return true;
	}
}
