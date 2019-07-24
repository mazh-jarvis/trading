package ca.jrvs.apps.trading.dao;

public interface CrudRepository<T, I> {
    T save(T entity);
    T findById(I id);
    boolean existsById(I id);
    void deleteById(I id);
}
