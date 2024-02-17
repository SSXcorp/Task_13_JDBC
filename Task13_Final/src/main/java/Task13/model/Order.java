package Task13.model;

import java.math.BigDecimal;

public class Order {

    private Long orderId;
    private Long userId;
    private String productList;
    private BigDecimal totalAmount;

    public Order() {

    }

    public Order(Long orderId, Long userId, String productList, BigDecimal totalAmount) {
        this.orderId = orderId;
        this.userId = userId;
        this.productList = productList;
        this.totalAmount = totalAmount;
    }

    public Long getOrderId() {
        return orderId;
    }

    public Long getUserId() {
        return userId;
    }

    public String getProductList() {
        return productList;
    }

    public BigDecimal getTotalAmount() {
        return totalAmount;
    }

    public void setOrderId(Long orderId) {
        this.orderId = orderId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public void setProductList(String productList) {
        this.productList = productList;
    }

    public void setTotalAmount(BigDecimal totalAmount) {
        this.totalAmount = totalAmount;
    }
}
