package ru.aston.homework1.structures;

public class MyHashSet<E> implements MySet<E> {
    private static final int DEFAULT_BUCKETS_COUNT = 8;
    private static final double scale = 2;

    private int bucketsCount = DEFAULT_BUCKETS_COUNT;
    private int elementCount = 0;

    private MyList<E>[] buckets;

    public MyHashSet() {
        buckets = new MyLinkedList[DEFAULT_BUCKETS_COUNT];
        for (int i = 0; i < buckets.length; i++) {
            buckets[i] = new MyLinkedList<>();
        }
    }

    @Override
    public int size() {
        return elementCount;
    }

    @Override
    public void remove(E value) {
        int bucketIdx = Math.abs(value.hashCode()) % bucketsCount;
        MyList<E> bucket = buckets[bucketIdx];

        if (bucket.remove(value))
            elementCount--;
    }

    @Override
    public boolean contains(E value) {
        int bucketIdx = Math.abs(value.hashCode()) % bucketsCount;
        MyList<E> bucket = buckets[bucketIdx];

        return bucket.contains(value);
    }

    @Override
    public void add(E value) {
        if (!contains(value)) {
            if (elementCount == bucketsCount) {
                resizeBuckets();
            }

            int bucketIdx = Math.abs(value.hashCode()) % bucketsCount;
            buckets[bucketIdx].add(value);
            elementCount++;
        }
    }

    private void resizeBuckets() {
        int newBucketsCount = (int) (bucketsCount * scale);

        MyList<E>[] newBuckets = new MyLinkedList[newBucketsCount];
        for (int i = 0; i < newBucketsCount; i++) {
            newBuckets[i] = new MyLinkedList<>();
        }

        for (MyList<E> bucket : buckets) {
            Item<E> item = bucket.getFirstItem();

            while (item != null) {
                Item<E> next = item.getNextItem();

                item.setPrevItem(null);
                item.setNextItem(null);

                int bucketIdx = Math.abs(item.getValue().hashCode()) % newBucketsCount;
                newBuckets[bucketIdx].addItem(item);

                item = next;
            }
        }

        bucketsCount = newBucketsCount;
        buckets = newBuckets;
    }
}