package com.bubna.model.dao;

public interface DAO<V> {
    V get(V entity);
    void update(V entity);
}
