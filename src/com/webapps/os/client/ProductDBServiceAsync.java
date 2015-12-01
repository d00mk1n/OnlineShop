package com.webapps.os.client;

import java.util.List;

import com.google.gwt.user.client.rpc.AsyncCallback;
import com.webapps.os.shared.Product;

public interface ProductDBServiceAsync {

	void getAllProducts(AsyncCallback<List<Product>> callback);
	
	void addProduct(Product product, AsyncCallback<Boolean> asyncCallback);
	
	void deleteProduct(int productId, AsyncCallback<Boolean> asyncCallback);
	
}
