package ru.aston.homework1.structures;

public class MyHashMap<K, V> implements MyMap<K, V> {

    private static final int DEFAULT_BUCKETS_COUNT = 16;
    private static final double LOAD_FACTOR = 0.75;
    private static final double SCALE_FACTOR = 1.5;

    private int bucketsCount = DEFAULT_BUCKETS_COUNT;
    private int elementCount = 0;

    private MyList<Entry<K, V>>[] buckets;

    public MyHashMap() {
        buckets = new MyLinkedList[DEFAULT_BUCKETS_COUNT];
        for (int i = 0; i < buckets.length; i++) {
            buckets[i] = new MyLinkedList<>();
        }
    }

    @Override
    public void put(K key, V value) {
        if (elementCount >= bucketsCount * LOAD_FACTOR) {
            resize();
        }

        int bucketIndex = getBucketIndex(key, bucketsCount);
        MyList<Entry<K, V>> bucket = buckets[bucketIndex];

        Entry<K, V> searchEntry = new Entry<>(key, null);
        Item<Entry<K, V>> existingItem = bucket.get(searchEntry);

        if (existingItem != null) {
            existingItem.getValue().setValue(value);
        } else {
            bucket.add(new Entry<>(key, value));
            elementCount++;
        }
    }

    @Override
    public V get(K key) {
        int bucketIndex = getBucketIndex(key, bucketsCount);
        MyList<Entry<K, V>> bucket = buckets[bucketIndex];

        Entry<K, V> searchEntry = new Entry<>(key, null);
        Item<Entry<K, V>> item = bucket.get(searchEntry);

        if (item != null) {
            return item.getValue().getValue();
        }
        return null;
    }

    @Override
    public boolean remove(K key) {
        int bucketIndex = getBucketIndex(key, bucketsCount);
        MyList<Entry<K, V>> bucket = buckets[bucketIndex];

        boolean removed = bucket.remove(new Entry<>(key, null));
        if (removed) {
            elementCount--;
        }
        return removed;
    }

    @Override
    public int size() {
        return elementCount;
    }

    private int getBucketIndex(K key, int capacity) {
        if (key == null) return 0;
        return Math.abs(key.hashCode()) % capacity;
    }

    private void resize() {
        int newBucketsCount = (int) (bucketsCount * SCALE_FACTOR);

        MyList<Entry<K, V>>[] newBuckets = new MyLinkedList[newBucketsCount];
        for (int i = 0; i < newBucketsCount; i++) {
            newBuckets[i] = new MyLinkedList<>();
        }

        for (MyList<Entry<K, V>> bucket : buckets) {
            Item<Entry<K, V>> currentItem = bucket.getFirstItem();

            while (currentItem != null) {
                Item<Entry<K, V>> next = currentItem.getNextItem();

                currentItem.setPrevItem(null);
                currentItem.setNextItem(null);

                int newIndex = getBucketIndex(currentItem.getValue().getKey(), newBucketsCount);
                newBuckets[newIndex].addItem(currentItem);

                currentItem = next;
            }
        }

        buckets = newBuckets;
        bucketsCount = newBucketsCount;
    }
}