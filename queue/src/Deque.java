import java.util.Iterator;
import java.util.NoSuchElementException;

public class Deque<Item> implements Iterable<Item> {

    private Node<Item> first;
    private Node<Item> last;

    private int dequeSize;


//    // resize the deque array
//    private void resize() {
//        if (itemData.length == dequeSize) {
//            int newSize = dequeSize << 1;
//            if (newSize > max_capacity) return;
//            newItemData(newSize);
//        }
//        if ((itemData.length >> 2) == dequeSize) {
//            int newSize = dequeSize >> 1;
//            if (newSize < initial_capacity) return;
//            newItemData(newSize);
//        }
//    }
//
//    private void newItemData(int newCapacity) {
//        Object[] copy = new Object[newCapacity];
//        System.arraycopy(itemData, 0, copy, 0, newCapacity);
//        itemData = copy;
//    }


    private class Node<Item> {
        Item item;
        Node<Item> next;
        Node<Item> previous;

        // constructor
        Node(Item item, Node<Item> previous, Node<Item> next) {
            this.item = item;
            this.previous = previous;
            this.next = next;
        }
    }

    // construct an empty deque
    public Deque() {
    }

    // is the deque empty?
    public boolean isEmpty() {
        return dequeSize == 0;
    }

    // return the number of items on the deque
    public int size() {
        return dequeSize;
    }

    // add the item to the front
    public void addFirst(Item item) {
        if (item == null) throw new IllegalArgumentException();
        Node<Item> f = first;
        Node<Item> newNode = new Node<>(item, null, f);
        first = newNode;
        if (f == null) {
            last = newNode;
        } else {
            f.previous = newNode;
        }
        dequeSize++;
    }

    // add the item to the back
    public void addLast(Item item) {
        if (item == null) throw new IllegalArgumentException();
        Node<Item> lastItem = last;
        Node<Item> newNode = new Node<>(item, lastItem, null);
        last = newNode;
        if (lastItem == null) {
            first = newNode;
        } else {
            lastItem.next = newNode;
        }
        dequeSize++;
    }

    // remove and return the item from the front
    public Item removeFirst() {
        if (first == null) throw new NoSuchElementException();
        final Item resultItem = first.item;
        Node<Item> next = first.next;
        first.item = null;
        first.next = null;
        first = next;
        if (next == null) {
            last = null;
        } else {
            next.previous = null;
        }
        dequeSize--;
        return resultItem;
    }

    // remove and return the item from the back
    public Item removeLast() {
        if (last == null) throw new NoSuchElementException();
        final Item resultItem = last.item;
        Node<Item> prev = last.previous;
        last.item = null;
        last.next = null;
        last = prev;
        if (prev == null) {
            first = null;
        } else {
            prev.next = null;
        }
        dequeSize--;
        return resultItem;
    }

    // return an iterator over items in order from front to back
    public Iterator<Item> iterator() {
        return new Itr();
    }

    private class Itr implements Iterator<Item> {
        private Node<Item> lastReturned;
        private Node<Item> next;

        Itr() {
            next = first;
        }

        public boolean hasNext() {
            return next != null;
        }

        public Item next() {
            //java.util.NoSuchElementException
            if (!hasNext()) throw new NoSuchElementException();
            lastReturned = next;
            next = next.next;
            return lastReturned.item;
        }

        // remove the last returned element
        public void remove() {
            throw new UnsupportedOperationException();
        }

    }

    // unit testing (required)
    public static void main(String[] args) {
        Deque<String> deque = new Deque<>();
        deque.addLast("ssss");
        deque.addLast("aeiou");
        System.out.println(deque.dequeSize);
        Iterator itr = deque.iterator();
        while (itr.hasNext()) {
            System.out.println(itr.next());
        }

    }

}
