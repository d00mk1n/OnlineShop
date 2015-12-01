package com.webapps.os.client;

import java.util.List;

import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;
import com.webapps.os.shared.Product;
@RemoteServiceRelativePath("productDB")
public interface ProductDBService extends RemoteService {

	List<Product> getAllProducts();

	boolean addProduct(Product product);

	boolean deleteProduct(int productId);

}
