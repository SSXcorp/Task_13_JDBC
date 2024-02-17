package Task13;

import Task13.model.Order;
import Task13.model.User;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.List;
import java.util.Optional;

import Task13.Dao.Impl.*;

public class Main {

    private static final Logger logger = LogManager.getLogger(Task13.Main.class);


    public static void main(String[] args) {

        UserDaoImpl userDaoImpl = new UserDaoImpl();

        logger.trace("We've just greeted the user!");
        logger.debug("We've just greeted the user!");
        logger.info("We've just greeted the user!");
        logger.warn("We've just greeted the user!");
        logger.error("We've just greeted the user!");
        logger.fatal("GG");

        //to get user with id = 1
		Optional<User> optionalUser = userDaoImpl.get(1L);
		User user = optionalUser.orElse(null); // transforms Optional into User object or returns null (or can be new User())
		String username = user.getUsername();
		System.out.println("User ID: " + user.getUserId());
		System.out.println("Username: " + user.getUsername());
		System.out.println("Usersurname: " + user.getUsersurname());
		System.out.println("Email: " + user.getEmail());

        //to add NEW user
//		User newUser = new User();
//		newUser.setEmail("shapowal111@gmail.com");
//		newUser.setUsername("Bohdan");
//		newUser.setUsersurname("Shapowal");
//		userDao.save(newUser);

        //to delete user by id
//		userDao.delete(25);

        //to display ALL users
//		List<User> users = userDao.getAll();
//		System.out.println("List of Users:");
//		for (User usera : users) {
//			System.out.println("User ID: " + usera.getUserId());
//			System.out.println("Username: " + usera.getUsername());
//			System.out.println("Usersurname: " + usera.getUsersurname());
//			System.out.println("Email: " + usera.getEmail());
//			System.out.println("-------------------------");
//		}

        //to update user using User object
//		User user2 = userDao.get(7).orElse(null);
//		user2.setEmail("user3@example.com");
//		userDao.update(user2);

        //===============================================================================================
        //UserDetails

        UserDetailsDaoImpl userDetailsDaoImpl = new UserDetailsDaoImpl();


        //get user by user_id
//		Optional<UserDetails> userDetails = userDetailsDao.get(2);
//		UserDetails user2Details = userDetails.orElse(null);
//		System.out.println("User ID: " + user2Details.getUserId());
//		System.out.println("Job: " + user2Details.getJob());
//		System.out.println("Address: " + user2Details.getAddress());
//		System.out.println("Salary: " + user2Details.getSalary());

        //save
//		UserDetails userDetails = new UserDetails();
//		userDetails.setUserId(9);
//		userDetails.setAddress("St. Deribasivska 13");
//		userDetails.setJob("PM");
//		userDetails.setSalary(120000);
//		userDetailsDao.save(userDetails);

        //update user_details
//		UserDetails userDetails = new UserDetails();
//		userDetails.setUserId(1);
//		userDetails.setAddress("Bul. Academy Voltza 12");
//		userDetails.setJob("Java Developer");
//		userDetails.setSalary(6200);
//		userDetailsDao.update(userDetails);

        //delete
//		userDetailsDao.delete(911);


        //get ALL
//		List<UserDetails> users = userDetailsDao.getAll();
//		System.out.println("List of Users:");
//		for (UserDetails usera : users) {
//			System.out.println("User ID: " + usera.getUserId());
//			System.out.println("Job: " + usera.getJob());
//			System.out.println("Address: " + usera.getAddress());
//			System.out.println("Salary: " + usera.getSalary());
//			System.out.println("-------------------------");
//			}


        //===============================================================================================
        //Products
        ProductsDaoImpl productsDaoImpl = new ProductsDaoImpl();


        //get single product
//		Optional<Products> productsOptional = pd.get(2);
//		Products product = productsOptional.orElse(null);
//		System.out.println("Product ID: " + product.getProductId());
//		System.out.println("Name: " + product.getProductName());
//		System.out.println("Price: " + product.getPrice());

        //get all products
//		List<Products> products = productsDao.getAll();
//		System.out.println("List of Users:");
//		for (Products product : products) {
//			System.out.println("Product ID: " + product.getProductId());
//			System.out.println("Name: " + product.getProductName());
//			System.out.println("Price: " + product.getPrice());
//			System.out.println("-------------------------");
//			}

        //update product
//		Products updatedProduct = new Products(11,"Beef", 220.00);
//		productsDao.update(updatedProduct);

        //delete product
//		productsDao.delete(11);

        //add product
//		Products newProduct = new Products();
//		newProduct.setProductName("Beef");
//		newProduct.setPrice(212.99);
//		productsDao.save(newProduct);

        //===============================================================================================
        ShoppingCartDaoImpl shoppingCartDaoImpl = new ShoppingCartDaoImpl();

//		List<ShoppingCart> items = shoppingCartDaoImpl.getAllProducts(4);
//
//		for (ShoppingCart item : items) {
//			System.out.println("Cart ID: " + item.getCartId());
//			System.out.println("User ID: " + item.getUserId());
//			System.out.println("Product ID: " + item.getProductId());
//			System.out.println("-------------------------");
//		}
//
//		System.out.println();

//		ShoppingCart shoppingCart = new ShoppingCart(0, 1,12);
//		shoppingCartDao.save(shoppingCart);

        //delete shopping cart unit
//		shoppingCartDao.delete(1,12);



        //===============================================================================================
        //Orders

        OrdersDaoImpl ordersDaoImpl = new OrdersDaoImpl();

        //get all orders of 1 user
//		List<Orders> orders = ordersDao.get(1);
//		System.out.println("List of Users:");
//		for (Orders order : orders) {
//			System.out.println("Order ID: " + order.getOrderId());
//			System.out.println("User ID: " + order.getUserId());
//			System.out.println("Products list: " + order.getProductList());
//			System.out.println("Total amount: " + order.getTotalAmount());
//			System.out.println("-------------------------");
//		}

        //get all orders
        List<Order> orders = ordersDaoImpl.getAll();
        System.out.println("List of Orders:");
        for (Order order : orders) {
            System.out.println("Order ID: " + order.getOrderId());
            System.out.println("User ID: " + order.getUserId());
            System.out.println("Products list: " + order.getProductList());
            System.out.println("Total amount: " + order.getTotalAmount());
            System.out.println("-------------------------");
        }

        //---------------======================------------------==============--------------===============
        //to form order

//		String s = shoppingCartDao.getStringOfProductsInShoppingCart(1);
//		System.out.println(s);
//
//		double d = shoppingCartDao.calculateTotalSum(1);
//		System.out.println(d);
//
//		ordersDao.saveOrderFromShoppingCart(s,d,1);

//		OrderService.formOrder(9);


    }
}