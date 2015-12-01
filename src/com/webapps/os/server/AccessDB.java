package com.webapps.os.server;

import java.io.IOException;
import java.io.Reader;
import java.util.List;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;

import com.webapps.os.shared.OrdProd;
import com.webapps.os.shared.Order;
import com.webapps.os.shared.Product;
import com.webapps.os.shared.User;

public class AccessDB {
	static boolean init;

	private static SqlSessionFactory sqlSessionFactory;

	public static void ensureInitDB() {
		synchronized (AccessDB.class) {

			if (init) {
				return;
			}
			init = true;
		}
		try {
			Reader resourceReader = Resources.getResourceAsReader("com/webapps/os/client/config.xml");
			sqlSessionFactory = new SqlSessionFactoryBuilder().build(resourceReader);
			sqlSessionFactory.getConfiguration().addMapper(OnlineStoreMapper.class);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static void addProduct(Product product) {
		ensureInitDB();
		SqlSession session = sqlSessionFactory.openSession();
		try {
			OnlineStoreMapper mapper = session.getMapper(OnlineStoreMapper.class);
			mapper.addProduct(product);
		} finally {
			session.commit();
			session.close();
		}
	}

	public static void addUser(User user) {
		ensureInitDB();
		SqlSession session = sqlSessionFactory.openSession();
		try {
			OnlineStoreMapper mapper = session.getMapper(OnlineStoreMapper.class);
			mapper.addUser(user);
		} finally {
			session.commit();
			session.close();
		}
	}

	public static User getUser(int i) {
		ensureInitDB();
		SqlSession session = sqlSessionFactory.openSession();
		try {
			OnlineStoreMapper mapper = session.getMapper(OnlineStoreMapper.class);
			System.out.println("method called");
			return mapper.getUser(i);
		} finally {
			session.close();
		}
	}

	public static List<User> getAllUsers() {

		ensureInitDB();
		SqlSession session = sqlSessionFactory.openSession();
		try {
			OnlineStoreMapper mapper = session.getMapper(OnlineStoreMapper.class);
			return mapper.getAllUsers();
		} finally {
			session.close();
		}
	}

	public static List<Product> getAllProducts() {
		ensureInitDB();
		SqlSession session = sqlSessionFactory.openSession();
		try {
			OnlineStoreMapper mapper = session.getMapper(OnlineStoreMapper.class);
			return mapper.getAllProducts();
		} finally {
			session.close();
		}

	}

	public static List<String> getOrderProductsNames(int order_id) {
		ensureInitDB();
		SqlSession session = sqlSessionFactory.openSession();
		try {
			OnlineStoreMapper mapper = session.getMapper(OnlineStoreMapper.class);
			return mapper.getOrderProductsNames(order_id);
		} finally {
			session.close();
		}

	}

	public static List<Order> getUserOrders(int user_id) {
		ensureInitDB();
		SqlSession session = sqlSessionFactory.openSession();
		try {
			OnlineStoreMapper mapper = session.getMapper(OnlineStoreMapper.class);
			return mapper.getUserOrders(user_id);
		} finally {
			session.close();
		}
	}

	public static List<Order> getUnpaidUserOrders(int user_id) {
		ensureInitDB();
		SqlSession session = sqlSessionFactory.openSession();
		try {
			OnlineStoreMapper mapper = session.getMapper(OnlineStoreMapper.class);
			return mapper.getUnpaidUserOrders(user_id);
		} finally {
			session.close();
		}
	}

	public static void deleteProduct(int product_id) {
		ensureInitDB();
		SqlSession session = sqlSessionFactory.openSession();
		try {
			OnlineStoreMapper mapper = session.getMapper(OnlineStoreMapper.class);
			mapper.deleteProduct(product_id);
		} finally {
			session.commit();
			session.close();
		}

	}

	public static void makeAdmin(int user_id) {
		ensureInitDB();
		SqlSession session = sqlSessionFactory.openSession();
		try {
			OnlineStoreMapper mapper = session.getMapper(OnlineStoreMapper.class);
			mapper.makeAdmin(user_id);
		} finally {
			session.commit();
			session.close();
		}

	}

	public static void addToBlackList(int user_id) {
		ensureInitDB();
		SqlSession session = sqlSessionFactory.openSession();
		try {
			OnlineStoreMapper mapper = session.getMapper(OnlineStoreMapper.class);
			mapper.addToBlackList(user_id);
		} finally {
			session.commit();
			session.close();
		}

	}

	public static void addNewOrder(int user_id, List<Product> cart) {
		ensureInitDB();
		SqlSession session = sqlSessionFactory.openSession();
		try {
			OnlineStoreMapper mapper = session.getMapper(OnlineStoreMapper.class);
			int ordId = mapper.createOrder(user_id);
			for (Product product : cart) {
				mapper.manageOrdProd(new OrdProd(product.getId(), ordId));
			}
		} finally {
			session.commit();
			session.close();
		}
	}

	public static void payOrder(int id) {
		ensureInitDB();
		SqlSession session = sqlSessionFactory.openSession();
		try {
			OnlineStoreMapper mapper = session.getMapper(OnlineStoreMapper.class);
			mapper.payOrder(id);
		} finally {
			session.commit();
			session.close();
		}
	}
}
