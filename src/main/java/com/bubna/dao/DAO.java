package com.bubna.dao;

public interface DAO<V> {
    void addInput(String key, Object val);
    V get();
    void update();
}
