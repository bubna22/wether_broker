package com.bubna.utils;

public interface Handler<K, V> {
    void setNext(Handler<K, V> next);
    void handle(K o);
    V getResult();
}
