package com.webapps.os.server;

import java.util.List;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import com.webapps.os.shared.OrdProd;
import com.webapps.os.shared.Order;
import com.webapps.os.shared.Product;
import com.webapps.os.shared.User;

public interface OnlineStoreMapper {
	
	@Select("select * from users where id = #{id}")
	User getUser(int id);
	
	@Insert("insert into users (login, password) values (#{login}, #{password})")
	boolean addUser(User user);
	
	@Select("select * from orders where id = #{id}")
	Order getOrder(int id);
	
	@Select("select * from products where id = #{id}")
	Product getProduct(int id);
	
	@Insert("insert into products (name, price) values (#{name}, #{price})")
	boolean addProduct(Product product);
	
	@Select ("select * from users")
	List<User> getAllUsers ();
	
	@Select ("select * from products")
	List<Product> getAllProducts ();
	
	@Select ("select * from orders")
	List<Order> getAllOrders ();
	
	@Select ("select * from orders where user_id = #{id}")
	List<Order> getUserOrders (int id);
	
	@Select ("select * from orders where user_id = #{id} and paid = false")
	List<Order> getUnpaidUserOrders (int id);
	
	@Select ("select name from products, orders, ordprod where orders.id = #{id} and products.id = ordprod.prod_id and orders.id = ordprod.ord_id") 
	List<String> getOrderProductsNames(int id);

	@Delete("delete from products where id = #{id}")
	void deleteProduct(int id);

	@Update("update users set admin=true where id = #{id}")
	void makeAdmin(int id);

	@Update("update users set blacklist=true where id = #{id}")
	void addToBlackList(int id);

	@Insert("insert into orders (id, paid, user_id) values (0, 0, #{user_id})")
	@Options(useGeneratedKeys=true, keyProperty = "id")
	int createOrder(int user_id);

	@Insert("insert into ordprod (prod_id, ord_id) values (#{prod_id}, #{ord_id})")
	void manageOrdProd(OrdProd ordprod);

	@Update("update orders set paid=true where id = #{id}")
	void payOrder(int id);


	
	

}
