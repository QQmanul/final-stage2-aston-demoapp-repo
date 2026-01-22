package ru.aston.homework1.structures;

public interface MyList<T> {

    void add(T value);

    void addAll(T[] values);

    boolean remove(T value);

    boolean contains(T value);

    Item<T> get(T value);

    int getListCount();

    void addItem(Item<T> item);

    Item<T> getFirstItem();
}
