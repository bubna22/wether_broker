package com.bubna.model;

public interface Model<V> {
    V get(V v);
    void update(V v);
}
