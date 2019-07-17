package ca.jrvs.apps.trading.dao;

import ca.jrvs.apps.trading.Trader;

public interface CrudRepository<T, T1> {
    Trader save(Trader entity);
    Trader findById(Integer id);
    boolean existsById(Integer id);
    void deleteById(Integer id);
}
