package ru.aston.homework1.structures;

public interface MySet<T> {

    int size();

    boolean contains(T value);

    void remove(T value);

    void add(T value);
}

