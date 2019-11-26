package com.pliskin;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

public class HashTable<K, V> {

    private List[] keyBuckets;
    private List[] valueBuckets;

    private int capacity;
    private int load;
    private double loadFactor;

    public HashTable() {
        this(16);
    }

    public HashTable(int capacity) {
        this(capacity, 0.75);
    }

    public HashTable(int capacity, double loadFactor) {
        this.capacity = capacity;
        this.loadFactor = loadFactor;
        keyBuckets = new LinkedList[capacity];
        valueBuckets = new LinkedList[capacity];
    }

    public final void put(K key, V value) {
        if (key == null) {
            throw new IllegalArgumentException("key value is null");
        }
        if (load >= capacity * loadFactor) {
            resize();
        }
        int index = getIndexFor(key);
        if (keyBuckets[index] == null) {
            keyBuckets[index] = new LinkedList();
            valueBuckets[index] = new LinkedList();
            load++;
        }
        int indexInBucket = findIndexInBucket(key, keyBuckets[index]);
        if (indexInBucket != -1) {
            valueBuckets[index].remove(indexInBucket);
            keyBuckets[index].remove(indexInBucket);
        }
        keyBuckets[index].add(key);
        valueBuckets[index].add(value);
    }

    public final V get(K key) {
        if (key == null) {
            throw new IllegalArgumentException("key value is null");
        }
        int index = getIndexFor(key);
        if (keyBuckets[index] == null) {
            return null;
        }
        int indexInBucket = findIndexInBucket(key, keyBuckets[index]);
        if (indexInBucket != -1) {
            return (V) valueBuckets[index].get(indexInBucket);
        }
        return null;
    }

    public final void remove(K key) {
        if (key == null) {
            throw new IllegalArgumentException("key value is null");
        }
        int index = getIndexFor(key);
        if (keyBuckets[index] == null) {
            return;
        }
        int indexInBucket = findIndexInBucket(key, keyBuckets[index]);
        if (indexInBucket != -1) {
            valueBuckets[index].remove(indexInBucket);
            keyBuckets[index].remove(indexInBucket);
        }
    }

    int hash(K key) {
        return key.hashCode();
    }

    boolean keysEquals(K key1, K key2) {
        return key1.equals(key2);
    }

    private int getIndexFor(K key) {
        return Math.abs(hash(key) % capacity);
    }

    private int findIndexInBucket(K key, List bucket) {
        int indexInBucket = -1;
        boolean isKeyAlreadyExists = false;
        for (K k : (Iterable<K>) bucket) {
            indexInBucket++;
            if (keysEquals(k, key)) {
                isKeyAlreadyExists = true;
                break;
            }
        }
        return isKeyAlreadyExists ? indexInBucket : -1;
    }

    private void resize() {
        if (!(capacity * 2 > capacity && capacity * 2 < Integer.MAX_VALUE)) {
            return;
        }

        List[] oldKeyBuckets = keyBuckets;
        List[] oldValueBuckets = valueBuckets;

        int oldCapacity = capacity;
        capacity *= 2;
        keyBuckets = new List[capacity];
        valueBuckets = new List[capacity];

        int newLoad = 0;
        for (int i = 0; i < oldCapacity; i++) {
            if (oldKeyBuckets[i] == null) {
                continue;
            }
            Iterator oldKeyBucketIt = oldKeyBuckets[i].iterator();
            Iterator oldValueBucketIt = oldValueBuckets[i].iterator();

            while (oldKeyBucketIt.hasNext()) {
                K key = (K) oldKeyBucketIt.next();
                V value = (V) oldValueBucketIt.next();
                int index = getIndexFor(key);
                if (keyBuckets[index] == null) {
                    keyBuckets[index] = new LinkedList();
                    valueBuckets[index] = new LinkedList();
                    newLoad++;
                }
                keyBuckets[index].add(key);
                valueBuckets[index].add(value);
            }
        }
        load = newLoad;
    }
}