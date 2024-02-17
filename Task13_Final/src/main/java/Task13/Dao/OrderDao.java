package Task13.Dao;

import java.util.List;

public interface OrderDao<T> {

    public void save(T t);

    public List<T> getAll();

    public List<T> get(Long userId);

}
