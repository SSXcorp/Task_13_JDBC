package Task13.Dao.Impl;

import Task13.ConnectionFactory;
import Task13.Dao.OrderDao;
import Task13.model.Order;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.math.BigDecimal;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class OrdersDaoImpl implements OrderDao<Order> {

    private static final Logger logger = LogManager.getLogger(OrdersDaoImpl.class);
    private final Connection connection;

    public OrdersDaoImpl() {
        this.connection = ConnectionFactory.getConnection();
    }

    @Override
    public List<Order> getAll() {
        logger.info("Getting all orders");
        List<Order> orders = new ArrayList<>();
        try (PreparedStatement statement = connection.prepareStatement("SELECT * FROM orders");
             ResultSet resultSet = statement.executeQuery()) {

            while (resultSet.next()) {
                Order order = new Order();
                order.setOrderId(Long.valueOf(resultSet.getInt("order_id")));
                order.setUserId(Long.valueOf(resultSet.getInt("user_id")));
                order.setProductList(resultSet.getString("product_list"));
                order.setTotalAmount(BigDecimal.valueOf(resultSet.getDouble("total_amount")));
                orders.add(order);
            }
        } catch (SQLException e) {
            logger.error("Error getting all orders!");
            throw new RuntimeException("Error getting all orders", e);
        }
        return orders;
    }

    @Override
    public List<Order> get(Long userId) {
        logger.info("Getting orders with user_id: " + userId);
        List<Order> orders = new ArrayList<>();
        try (PreparedStatement statement = connection.prepareStatement("SELECT * FROM orders WHERE user_id = ?")) {
            statement.setLong(1, userId);
            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {
                Order order = new Order();
                order.setOrderId(Long.valueOf(resultSet.getInt("order_id")));
                order.setUserId(Long.valueOf(resultSet.getInt("user_id")));
                order.setProductList(resultSet.getString("product_list"));
                order.setTotalAmount(BigDecimal.valueOf(resultSet.getDouble("total_amount")));
                orders.add(order);
            }
        }catch(SQLException e){
            logger.error("Error getting orders with user_id: " + userId);
            throw new RuntimeException("Error getting all orders of single user", e);
        }

        return orders;
    }

    @Override
    public void save(Order order) {
        logger.info("Saving order");
        try (PreparedStatement insertStatement = connection.prepareStatement("INSERT INTO Orders (user_id, product_list, total_amount) VALUES (?, ?, ?)", Statement.RETURN_GENERATED_KEYS)) {
            insertStatement.setLong(1, order.getUserId());
            insertStatement.setString(2, order.getProductList());
            insertStatement.setBigDecimal(3, order.getTotalAmount());

            int affectedRows = insertStatement.executeUpdate();

            if (affectedRows == 0) {
                throw new SQLException("Creating order failed, no rows affected.");
            }

        } catch (SQLException e) {
            logger.error("Error when saving order");
            throw new RuntimeException("Error creating shopping cart unit", e);
        }
    }

    public void saveOrderFromShoppingCart(String productList, BigDecimal totalAmount, Long userId) {
        logger.info("Saving order");
        try (PreparedStatement insertStatement = connection.prepareStatement("INSERT INTO Orders (user_id, product_list, total_amount) VALUES (?, ?, ?)", Statement.RETURN_GENERATED_KEYS)) {
            insertStatement.setLong(1, userId);
            insertStatement.setString(2, productList);
            insertStatement.setBigDecimal(3, totalAmount);

            int affectedRows = insertStatement.executeUpdate();

            if (affectedRows == 0) {
                throw new SQLException("Creating order failed, no rows affected.");
            }

        } catch (SQLException e) {
            logger.error("Error when saving order from shopping_cart");
            throw new RuntimeException("Error when saving order from shopping_cart", e);
        }
    }



}
