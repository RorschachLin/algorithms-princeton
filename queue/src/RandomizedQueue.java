import edu.princeton.cs.algs4.StdRandom;

import java.util.Iterator;
import java.util.NoSuchElementException;


public class RandomizedQueue<Item> implements Iterable<Item> {
    private Object[] itemData;
    private final int max_capacity = Integer.MAX_VALUE - 8;
    private final int initial_capacity = 16;
    private int tail = 0;

    // construct an empty randomized queue
    public RandomizedQueue() {
        this.itemData = new Object[initial_capacity];
    }

    // resize the deque array
    private void resize() {
        int length = itemData.length;
        if (length == tail) {
            int newCapacity = length << 1;
            if (newCapacity > max_capacity) return;
            newItemData(newCapacity);
        }
        if (tail == (length >> 2)) {
            int newCapacity = length >> 1;
            if (newCapacity < initial_capacity) return;
            newItemData(newCapacity);
        }
    }

    private void newItemData(int newCapacity) {
        Object[] copy = new Object[newCapacity];
        System.arraycopy(itemData, 0, copy, 0, Math.min(itemData.length, newCapacity));
        itemData = copy;
    }


    // is the randomized queue empty?
    public boolean isEmpty() {
        return tail == 0;
    }

    // return the number of items on the randomized queue
    public int size() {
        return tail;
    }

    // add the item
    public void enqueue(Item item) {
        if (item == null) throw new IllegalArgumentException();
        resize();
        itemData[tail] = item;
        tail++;
    }

    // remove and return a random item
    public Item dequeue() {
        if (isEmpty()) throw new NoSuchElementException();
        resize();
        int randomIndex = randomNum();
        final Item resultItem = (Item) itemData[randomIndex];
        itemData[randomIndex] = itemData[tail - 1];
        itemData[tail - 1] = null;
        tail--;
        return resultItem;
    }

    // return a random item (but do not remove it)
    public Item sample() {
        if (isEmpty()) throw new NoSuchElementException();
        return (Item) itemData[randomNum()];
    }

    private int randomNum() {
        return (int) StdRandom.uniform(0, tail);
    }

    // return an independent iterator over items in random order
    public Iterator<Item> iterator() {
        return new Itr();
    }

    private class Itr implements Iterator<Item> {
        int cursor;
        Item[] itrItems;

        Itr() {
            this.cursor = 0;
            itrItems = (Item[]) new Object[tail];
            System.arraycopy(itemData, 0, itrItems, 0, itrItems.length);
            StdRandom.shuffle(itrItems);
        }

        public boolean hasNext() {
            return cursor != tail;
        }

        public Item next() {
            if (!hasNext()) throw new NoSuchElementException();
            Item next = (Item) itemData[cursor];
            cursor++;
            return next;
        }

        // remove the last returned element
        public void remove() {
            throw new UnsupportedOperationException();
        }

    }

    // unit testing (required)
    public static void main(String[] args) {
        RandomizedQueue<Integer> rq = new RandomizedQueue<>();
        for (int i = 0; i < 20; i++) {
            //System.out.println("time " + i);
            rq.enqueue(i);
            //System.out.println("tail" + rq.tail);
        }
        Iterator itr = rq.iterator();
        System.out.println(rq.size());
        int counter = 0;
        while (itr.hasNext()) {
            System.out.println(counter++);
            System.out.println("result " + itr.next());
        }
    }

}
