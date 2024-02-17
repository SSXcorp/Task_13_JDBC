package Task13.Dao;


import java.util.List;

public interface ShoppingCartDao<T> {
    public void delete(Long userId, Long productId);

    public void deleteAll(Long id);

    public List<T> getAll();

    public List<T> getAllProducts(Long userId);

    public void save(T t);
}
