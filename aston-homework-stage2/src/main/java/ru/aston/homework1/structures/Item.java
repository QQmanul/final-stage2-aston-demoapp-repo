package ru.aston.homework1.structures;

public class Item<V> {
    private V value;
    private Item<V> nextItem;
    private Item<V> prevItem;

    public Item(V value) {
        this.value = value;
    }

    public V getValue() {
        return value;
    }

    public void setValue(V value) {
        this.value = value;
    }

    public Item<V> getPrevItem() {
        return prevItem;
    }

    public void setPrevItem(Item<V> prevItem) {
        this.prevItem = prevItem;
    }

    public Item<V> getNextItem() {
        return nextItem;
    }

    public void setNextItem(Item<V> nextItem) {
        this.nextItem = nextItem;
    }
}
