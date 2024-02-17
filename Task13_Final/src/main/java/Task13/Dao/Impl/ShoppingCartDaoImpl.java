package Task13.Dao.Impl;

import Task13.ConnectionFactory;
import Task13.Dao.ShoppingCartDao;
import Task13.model.ShoppingCart;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ShoppingCartDaoImpl implements ShoppingCartDao<ShoppingCart> {

    private static final Logger logger = LogManager.getLogger(ShoppingCartDaoImpl.class);

    private final Connection connection;

    public ShoppingCartDaoImpl() {
        this.connection = ConnectionFactory.getConnection();
    }

    @Override
    public void delete(Long userId, Long productId) { //delete single product
        logger.info("Deleting product for user with user_id: " + userId);
        try (PreparedStatement statement = connection.prepareStatement("DELETE FROM shopping_cart WHERE user_id = ? AND product_id = ?")) {
            statement.setLong(1, userId);
            statement.setLong(2, productId);

            int affectedRows = statement.executeUpdate();

            if (affectedRows == 0) {
                throw new SQLException("Deleting user failed, no rows affected.");
            }
        } catch (SQLException e) {
            logger.error("Error deleting product from shopping_cart");
            throw new RuntimeException("Error deleting user", e);
        }
    }

@Override
    public void deleteAll(Long id) {
    logger.info("Deleting All products for user: " + id);
        try (PreparedStatement statement = connection.prepareStatement("DELETE FROM shopping_cart WHERE user_id = ?")) {
            statement.setLong(1, id);

            int affectedRows = statement.executeUpdate();

            if (affectedRows == 0) {
                throw new SQLException("Deleting user failed, no rows affected.");
            }
        } catch (SQLException e) {
            logger.error("Error deleting ALL products from user's shopping_cart");
            throw new RuntimeException("Error deleting user", e);
        }
    }

    @Override
    public List<ShoppingCart> getAll() { //to get all shopping cart table
        logger.info("Getting ALL shopping cart records");
        List<ShoppingCart> userDetails = new ArrayList<>();
        try (PreparedStatement statement = connection.prepareStatement("SELECT * FROM shopping_cart");
             ResultSet resultSet = statement.executeQuery()) {

            while (resultSet.next()) {
                ShoppingCart shoppingCartUnit = new ShoppingCart();
                shoppingCartUnit.setUserId(resultSet.getLong("user_id"));
                shoppingCartUnit.setCartId(resultSet.getLong("cart_id"));
                shoppingCartUnit.setProductId(resultSet.getLong("product_id"));
                userDetails.add(shoppingCartUnit);
            }
        } catch (SQLException e) {
            logger.error("Error getting ALL shopping cart records");
            throw new RuntimeException("Error getting all shopping cart details", e);
        }
        return userDetails;
    }

    @Override
    public List<ShoppingCart> getAllProducts(Long userId) { //to get all products of one user
        logger.info("Getting ALL shopping cart records for user: " + userId);
        List<ShoppingCart> userShoppingCart = new ArrayList<>();
        try (PreparedStatement statement = connection.prepareStatement("SELECT * FROM shopping_cart WHERE user_id = ?")) {
            statement.setLong(1, userId);
            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {
                ShoppingCart shoppingCartUnit = new ShoppingCart();
                shoppingCartUnit.setUserId(resultSet.getLong("user_id"));
                shoppingCartUnit.setCartId(resultSet.getLong("cart_id"));
                shoppingCartUnit.setProductId(resultSet.getLong("product_id"));
                userShoppingCart.add(shoppingCartUnit);
            }
        } catch (SQLException e) {
            logger.error("Error getting all products from the user's shopping cart");
            throw new RuntimeException("Error getting all products from the user's shopping cart", e);
        }
        return userShoppingCart;
    }

    @Override
    public void save(ShoppingCart shoppingCart) { // to add product to user's shopping cart
        logger.info("Adding product to user's shopping cart ");
        try (PreparedStatement insertStatement = connection.prepareStatement("INSERT INTO Shopping_Cart (user_id, product_id) VALUES (?, ?)", Statement.RETURN_GENERATED_KEYS)) {
            insertStatement.setLong(1, shoppingCart.getUserId());
            insertStatement.setLong(2, shoppingCart.getProductId());

            int affectedRows = insertStatement.executeUpdate();

            if (affectedRows == 0) {
                throw new SQLException("Creating shopping cart unit failed, no rows affected.");
            }

            try (ResultSet generatedKeys = insertStatement.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    shoppingCart.setCartId(generatedKeys.getLong(1));
                } else {
                    throw new SQLException("Creating shopping cart unit failed, no ID obtained.");
                }
            }
        } catch (SQLException e) {
            logger.error("Error when adding product to user's shopping cart");
            throw new RuntimeException("Error creating shopping cart unit", e);
        }
    }

    public String getStringOfProductsInShoppingCart(Long userId) { //to get list of user's products as product_name String
        logger.info("Getting list of user's products as product_name String");
        StringBuilder listOfProducts = new StringBuilder();
        try (PreparedStatement statement = connection.prepareStatement("SELECT Products.product_name FROM Shopping_Cart JOIN Products ON Shopping_Cart.product_id = Products.product_id WHERE Shopping_Cart.user_id = ?")) {
            statement.setLong(1, userId);
            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {
                listOfProducts.append(resultSet.getString("product_name")).append(", ");
            }
        listOfProducts = new StringBuilder(listOfProducts.substring(0, listOfProducts.length() - 2));
        } catch(SQLException e){
            logger.error("Error when getting list of user's products as product_name String");
            throw new RuntimeException("Error when getting list of user's products as product_name String", e);
        }

        return listOfProducts.toString();
    }

    public Double calculateTotalSum(Long userId) { // to calculate total sum of all products in user's shopping cart
        logger.info("Calculating total sum of order");
        double sum = 0.00;
        try (PreparedStatement statement = connection.prepareStatement("SELECT Products.price FROM Shopping_Cart JOIN Products ON Shopping_Cart.product_id = Products.product_id WHERE Shopping_Cart.user_id = ?")) {
            statement.setLong(1, userId);
            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {
                sum = sum + resultSet.getInt("price");
            }
        }catch(SQLException e){
            logger.error("Error when calculating total sum of order");
            throw new RuntimeException("Error when calculating total sum of order", e);
        }

        return sum;
    }

    public boolean doesUserHaveProductsInCart(Long userId) { // to check if user has products in user's shopping cart
        logger.info("Checking if user has products in user's shopping cart");
        boolean hasProducts;
        try (PreparedStatement statement = connection.prepareStatement("SELECT 1 FROM Shopping_Cart WHERE user_id = ?")) {
            statement.setLong(1, userId);
            try (ResultSet resultSet = statement.executeQuery()) {
                hasProducts = resultSet.next();
            }
        } catch (SQLException e) {
            logger.error("Error when checking if user has products in user's shopping cart");
            throw new RuntimeException("Error checking if user has products in cart", e);
        }
        return hasProducts;
    }



}
