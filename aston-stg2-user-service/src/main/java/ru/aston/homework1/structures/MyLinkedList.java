package ru.aston.homework1.structures;

public class MyLinkedList<T> implements MyList<T> {
    private Item<T> firstItem;
    private Item<T> lastItem;
    private int listCount = 0;

    public MyLinkedList() {
    }

    public MyLinkedList(T value) {
        addItem(new Item<T>(value));
    }

    public void addItem(Item<T> item) {
        if (listCount == 0) {
            setFirstItem(item);
        } else {
            item.setNextItem(firstItem);
            firstItem.setPrevItem(item);
            firstItem = item;
        }
        listCount++;
    }

    public void addItemBack(Item<T> item) {
        if (listCount == 0) {
            setFirstItem(item);
        } else {
            item.setPrevItem(lastItem);
            lastItem.setNextItem(item);
            lastItem = item;
        }
        listCount++;
    }

    @Override
    public void add(T value) {
        addItem(new Item<T>(value));
    }

    @Override
    public void addAll(T[] values) {
        if (values == null) {
            return;
        }

        for (T value : values) {
            add(value);
        }
    }

    public void addBack(T value) {
        addItemBack(new Item<T>(value));
    }

    @Override
    public Item<T> get(T value) {
        Item<T> currentItem = firstItem;

        while (currentItem != null) {
            if (currentItem.getValue().equals(value)){
                return currentItem;
            }

            currentItem = currentItem.getNextItem();
        }

        return null;
    }

    @Override
    public boolean contains(T value) {
        return get(value) != null;
    }

    @Override
    public boolean remove(T value) {
        Item<T> itemFound = get(value);
        if (itemFound == null) return false;

        Item<T> previous = itemFound.getPrevItem();
        Item<T> next = itemFound.getNextItem();

        if (previous == null){
            firstItem = next;
        }
        else {
            previous.setNextItem(next);
        }

        if (next == null) {
            lastItem = previous;
        }
        else {
            next.setPrevItem(previous);
        }

        listCount--;
        return true;
    }

    public Item<T> getFirstItem() {
        return firstItem;
    }

    private void setFirstItem(Item<T> item) {
        firstItem = lastItem = item;
    }

    @Override
    public int getListCount() {
        return listCount;
    }

    public Item<T> getLastItem() {
        return lastItem;
    }
}

