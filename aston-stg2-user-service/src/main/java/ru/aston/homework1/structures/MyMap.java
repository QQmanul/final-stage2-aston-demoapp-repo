package ru.aston.homework1.structures;

public interface MyMap<K, V> {

    void put(K key, V value);

    V get(K key);

    boolean remove(K key);

    int size();
}