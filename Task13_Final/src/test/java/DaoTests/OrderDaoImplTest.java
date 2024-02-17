package DaoTests;

import Task13.Dao.Impl.OrdersDaoImpl;
import Task13.model.Order;
import org.junit.Test;
import org.junit.jupiter.api.BeforeEach;

import java.math.BigDecimal;
import java.util.List;

import static org.junit.Assert.*;

public class OrderDaoImplTest {

    private OrdersDaoImpl ordersDaoImpl;

    @BeforeEach
    public void setUp() {
        ordersDaoImpl = new OrdersDaoImpl();
    }

    @Test
    public void getAllOrdersPositiveTest() {
        List<Order> orders = ordersDaoImpl.getAll();

        assertNotNull(orders);
        assertFalse(orders.isEmpty());
    }

    @Test
    public void getOrdersByUserIdPositiveTest() {
        long userId = 1;
        List<Order> orders = ordersDaoImpl.get(userId);

        assertFalse(orders.isEmpty());
    }

    @Test
    public void saveOrderFromShoppingCartTest() {
        String productList = "King Shrimp, Iced Tea";
        BigDecimal totalAmount = BigDecimal.valueOf(150.00);
        Long userId = 1L;

        ordersDaoImpl.saveOrderFromShoppingCart(productList, totalAmount, userId);

        List<Order> orders = ordersDaoImpl.get(userId); // Fetch orders for the user and check if the newly added order exists
        boolean orderFound = orders.stream().anyMatch(order -> order.getProductList().equals(productList) && order.getTotalAmount() == totalAmount);

        assertTrue(orderFound);
    }
}
