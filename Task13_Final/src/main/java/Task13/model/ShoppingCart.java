package Task13.model;

import java.util.Objects;

public class ShoppingCart {

    private Long cartId;
    private Long userId;
    private Long productId;

    public ShoppingCart() {

    }

    public ShoppingCart(Long cartId, Long userId, Long productId) {
        this.cartId = cartId;
        this.userId = userId;
        this.productId = productId;
    }

    public Long getCartId() {
        return cartId;
    }

    public Long getUserId() {
        return userId;
    }

    public Long getProductId() {
        return productId;
    }

    public void setCartId(Long cartId) {
        this.cartId = cartId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public void setProductId(Long productId) {
        this.productId = productId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ShoppingCart that = (ShoppingCart) o;
        return userId == that.userId &&
                cartId == that.cartId &&
                productId == that.productId;
    }

    @Override
    public int hashCode() {
        return Objects.hash(userId, cartId, productId);
    }
}
