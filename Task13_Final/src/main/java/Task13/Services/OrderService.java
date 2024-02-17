package Task13.Services;


import Task13.Dao.Impl.OrdersDaoImpl;
import Task13.Dao.Impl.ShoppingCartDaoImpl;
import Task13.Dao.Impl.UserDaoImpl;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.math.BigDecimal;

public class OrderService {
    private static final Logger logger = LogManager.getLogger(OrderService.class);

    public static void formOrder(Long userId) {
        logger.info("Forming order for user with user_id: " + userId);
        UserDaoImpl userDaoImpl = new UserDaoImpl();
        ShoppingCartDaoImpl shoppingCartDaoImpl = new ShoppingCartDaoImpl();
        OrdersDaoImpl ordersDaoImpl = new OrdersDaoImpl();

        // Check if the user exists
        if (!userDaoImpl.doesUserExist(userId)) {
            throw new IllegalArgumentException("User with such user_id not found");
        }

        // Check if the user has products in the shopping cart
        if (!shoppingCartDaoImpl.doesUserHaveProductsInCart(userId)) {
            throw new IllegalStateException("User with user_id " + userId + " does not have any products in the shopping cart");
        }

        String productsList = shoppingCartDaoImpl.getStringOfProductsInShoppingCart(userId);
        BigDecimal totalOrder = BigDecimal.valueOf(shoppingCartDaoImpl.calculateTotalSum(userId));

        ordersDaoImpl.saveOrderFromShoppingCart(productsList, totalOrder, userId);
        shoppingCartDaoImpl.deleteAll(userId);
    }

}
