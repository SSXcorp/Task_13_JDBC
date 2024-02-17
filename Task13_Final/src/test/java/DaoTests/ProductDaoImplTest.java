package DaoTests;

import Task13.model.Product;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import Task13.Dao.Impl.*;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

public class ProductDaoImplTest {

    private ProductsDaoImpl productsDaoImpl;


    @BeforeEach
    public void setUp() {
        productsDaoImpl = new ProductsDaoImpl();
    }

    @Test
    public void getProductPositiveTest() {
        // Retrieve product from the database
        Optional<Product> productOptional = productsDaoImpl.get(2L);
        Product product = productOptional.orElse(null);

        // Perform assertions
        assertNotNull(product);
        assertEquals(2, product.getProductId());
        assertEquals("Grilled Steak", product.getProductName());
        assertEquals(BigDecimal.valueOf(200.00), product.getPrice());
    }

    @Test
    public void getAllProductsPositiveTest() {
        List<Product> products = productsDaoImpl.getAll();

        assertNotNull(products);
        assertFalse(products.isEmpty());

        for (Product product : products) {
            System.out.println("Product ID: " + product.getProductId());
            System.out.println("Name: " + product.getProductName());
            System.out.println("Price: " + product.getPrice());
            System.out.println("-------------------------");
        }
    }

    @Test
    public void updateProductPositiveTest() {
        // Create updated product
        Product updatedProduct = new Product();
        updatedProduct.setProductId(10L);
        updatedProduct.setProductName("Bubble gum");
        updatedProduct.setPrice(BigDecimal.valueOf(3.99));

        // Update product in the database
        productsDaoImpl.update(updatedProduct);

        // Retrieve updated product from the database
        Optional<Product> productOptional = productsDaoImpl.get(10L);
        Product retrievedProduct = productOptional.orElse(null);

        // Perform assertions
        assertNotNull(retrievedProduct);
        assertEquals(updatedProduct.getProductId(), retrievedProduct.getProductId());
        assertEquals(updatedProduct.getProductName(), retrievedProduct.getProductName());
        assertEquals(updatedProduct.getPrice(), retrievedProduct.getPrice());
    }

    @Test
    public void saveProductPositiveTest() {
        // Create new product to save
        Product newProduct = new Product();
        newProduct.setProductName("Gold fish");
        newProduct.setPrice(BigDecimal.valueOf(599.99));

        // Save product to the database
        productsDaoImpl.save(newProduct);

        // Retrieve saved product from the database
        Optional<Product> savedProductOptional = productsDaoImpl.get(newProduct.getProductId());
        Product savedProduct = savedProductOptional.orElse(null);


        // Perform assertions
        assertNotNull(savedProduct);
        assertEquals(newProduct.getProductName(), savedProduct.getProductName());
        assertEquals(newProduct.getPrice(), savedProduct.getPrice());
    }

    @Test
    public void deleteProductPositiveTest() {
        productsDaoImpl.delete(14L);

        Optional<Product> deletedProductOptional = productsDaoImpl.get(14L);

        assertFalse(deletedProductOptional.isPresent(), "Product should be deleted");
    }

}
