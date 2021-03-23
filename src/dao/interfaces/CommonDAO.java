package dao.interfaces;

import java.util.List;

public interface CommonDAO<T, K> {
  public T findById(K id);

  public void save(T object);

  public void delete(T object);

  public void update(T object);

  public List<T> finadAll();
}
