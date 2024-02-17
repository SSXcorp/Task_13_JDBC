package DaoTests;

import Task13.Dao.Impl.*;
import Task13.model.ShoppingCart;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class ShoppingCartDaoImplTest {

    private ShoppingCartDaoImpl shoppingCartDaoImpl;

    @BeforeEach
    public void setUp() {
        shoppingCartDaoImpl = new ShoppingCartDaoImpl();
    }

    @Test
    public void saveShoppingCartTest() {
        ShoppingCart shoppingCart = new ShoppingCart();
        shoppingCart.setCartId(27L);
        shoppingCart.setUserId(2L);
        shoppingCart.setProductId(6L);

        shoppingCartDaoImpl.save(shoppingCart);

        List<ShoppingCart> userShoppingCart = shoppingCartDaoImpl.getAllProducts(2L);
        assertTrue(userShoppingCart.contains(shoppingCart));
    }

    @Test
    public void deleteProductFromShoppingCartTest() {
        ShoppingCart shoppingCart = new ShoppingCart();
        shoppingCart.setUserId(2L);
        shoppingCart.setProductId(6L);

        shoppingCartDaoImpl.delete(2L, 6L);

        List<ShoppingCart> userShoppingCart = shoppingCartDaoImpl.getAllProducts(2L);
        assertFalse(userShoppingCart.contains(shoppingCart));
    }

    @Test
    public void deleteAllProductsFromShoppingCartTest() {
        ShoppingCart shoppingCart1 = new ShoppingCart();
        shoppingCart1.setUserId(1L);
        shoppingCart1.setProductId(2L);

        ShoppingCart shoppingCart2 = new ShoppingCart();
        shoppingCart2.setUserId(1L);
        shoppingCart2.setProductId(4L);

        shoppingCartDaoImpl.save(shoppingCart1);
        shoppingCartDaoImpl.save(shoppingCart2);

        shoppingCartDaoImpl.deleteAll(1L);

        List<ShoppingCart> userShoppingCart = shoppingCartDaoImpl.getAllProducts(1L);
        assertTrue(userShoppingCart.isEmpty());
    }

    @Test
    public void getAllProductsFromUserShoppingCartTest() {
        List<ShoppingCart> items = shoppingCartDaoImpl.getAllProducts(1L);

        // Perform assertions
        assertNotNull(items);
        assertFalse(items.isEmpty());

        for (ShoppingCart item : items) {
            System.out.println("Cart ID: " + item.getCartId());
            System.out.println("User ID: " + item.getUserId());
            System.out.println("Product ID: " + item.getProductId());
            System.out.println("-------------------------");
        }

    }

    @Test
    public void getStringOfProductsInShoppingCartTest() {
        String expectedProducts = "Coffee";

        String listOfProducts = shoppingCartDaoImpl.getStringOfProductsInShoppingCart(1L);

        assertEquals(expectedProducts, listOfProducts);
    }

    @Test
    public void calculateTotalSumTest() {
        double expectedTotalSum = 17.00;

        double totalSum = shoppingCartDaoImpl.calculateTotalSum(4L);

        assertEquals(expectedTotalSum, totalSum);
    }

    @Test
    public void doesUserHaveProductsInCartTest() {
        assertTrue(shoppingCartDaoImpl.doesUserHaveProductsInCart(24L));
        assertFalse(shoppingCartDaoImpl.doesUserHaveProductsInCart(4L));
    }
}

