package Task13.Dao.Impl;

import Task13.ConnectionFactory;
import Task13.Dao.Dao;
import Task13.model.Product;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class ProductsDaoImpl implements Dao<Product> {

    private static final Logger logger = LogManager.getLogger(ProductsDaoImpl.class);

    private Connection connection;

    public ProductsDaoImpl() {
        this.connection = ConnectionFactory.getConnection();
    }

    @Override
    public Optional<Product> get(Long userId) {
        logger.info("Getting products for user with user_id: " + userId);
        try (PreparedStatement statement = connection.prepareStatement("SELECT * FROM products WHERE product_id = ?")) {
            statement.setLong(1, userId);

            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    Product product = new Product();
                    product.setProductId(resultSet.getLong("product_id"));
                    product.setProductName(resultSet.getString("product_name"));
                    product.setPrice(resultSet.getBigDecimal("price"));
                    return Optional.of(product);
                }
            }
        } catch (SQLException e) {
            logger.error("Error getting products");
            throw new RuntimeException("Error getting user", e);
        }
        return Optional.empty();
    }

    @Override
    public List<Product> getAll() {
        logger.info("Getting ALL products");
        List<Product> products = new ArrayList<>();
        try (PreparedStatement statement = connection.prepareStatement("SELECT * FROM products ORDER BY product_id");
             ResultSet resultSet = statement.executeQuery()) {

            while (resultSet.next()) {
                Product product = new Product();
                product.setProductId(resultSet.getLong("product_id"));
                product.setProductName(resultSet.getString("product_name"));
                product.setPrice(resultSet.getBigDecimal("price"));
                products.add(product);
            }
        } catch (SQLException e) {
            logger.error("Error getting ALL products");
            throw new RuntimeException("Error getting all products", e);
        }
        return products;
    }

    @Override
    public void save(Product product) {
        logger.info("Saving product");
        try (PreparedStatement insertStatement = connection.prepareStatement("INSERT INTO Products (product_name, price) VALUES (?, ?)", Statement.RETURN_GENERATED_KEYS)) {
            insertStatement.setString(1, product.getProductName());
            insertStatement.setBigDecimal(2, product.getPrice());

            int affectedRows = insertStatement.executeUpdate();

            if (affectedRows == 0) {
                throw new SQLException("Creating product failed, no rows affected.");
            }

            try (ResultSet generatedKeys = insertStatement.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    product.setProductId(generatedKeys.getLong(1));
                } else {
                    throw new SQLException("Creating product failed, no ID obtained.");
                }
            }
        } catch (SQLException e) {
            logger.error("Error saving product");
            throw new RuntimeException("Error creating product", e);
        }
    }


    @Override
    public void update(Product product) {
        logger.info("Updating product");
        try (PreparedStatement updateStatement = connection.prepareStatement("UPDATE products SET product_name = ?, price = ? WHERE product_id = ?")) {
            updateStatement.setString(1, product.getProductName());
            updateStatement.setBigDecimal(2, product.getPrice());
            updateStatement.setLong(3, product.getProductId());

            int affectedRows = updateStatement.executeUpdate();

            if (affectedRows == 0) {
                throw new SQLException("Updating product failed, no rows affected.");
            }
        } catch (SQLException e) {
            logger.error("Error updating product");
            throw new RuntimeException("Error updating product", e);
        }
    }



    @Override
    public void delete(Long id) {
        logger.info("Deleting product");
        try (PreparedStatement statement = connection.prepareStatement("DELETE FROM products WHERE product_id = ?")) {
            statement.setLong(1, id);

            int affectedRows = statement.executeUpdate();

            if (affectedRows == 0) {
                throw new SQLException("Deleting product failed, no rows affected.");
            }
        } catch (SQLException e) {
            logger.error("Error deleting product");
            throw new RuntimeException("Error deleting product", e);
        }
    }

}
