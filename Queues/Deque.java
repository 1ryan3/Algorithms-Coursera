import edu.princeton.cs.algs4.StdOut;

import java.util.Iterator;
import java.util.NoSuchElementException;

public class Deque<Item> implements Iterable<Item> {

    private Item[] dq;
    private int size;
    private int head;
    private int tail;


    // construct an empty deque
    public Deque() {
        dq = (Item[]) new Object[2];
        head = 0;
        tail = 1;
        size = 0;
    }

    // is the deque empty?
    public boolean isEmpty() {
        return size == 0;
    }

    // return the number of items on the deque
    public int size() {
        return size;
    }

    // add the item to the front
    public void addFirst(Item item) {

        if (item == null) {
            throw new IllegalArgumentException("Unitialized item!");
        }
        if (size == dq.length) resize(2 * dq.length);

        if (head == 0) {
            dq[head] = item;
            head = dq.length - 1;
        }
        else {
            dq[head--] = item;
        }

        size++;
    }

    // add the item to the back
    public void addLast(Item item) {
        if (item == null) {
            throw new IllegalArgumentException("Unitialized item!");
        }
        if (size == dq.length) resize(2 * dq.length);
        if (tail == dq.length - 1) {
            dq[tail] = item;
            tail = 0;
        }
        else {
            dq[tail++] = item;
        }
        size++;

    }

    // remove and return the item from the front
    public Item removeFirst() {
        Item temp;
        if (size == 0) throw new NoSuchElementException();
        if (size > 0 && size == dq.length / 4) resize(dq.length / 2);
        if (head == dq.length - 1) {
            temp = dq[0];
            dq[0] = null;
            head = 0;
        }
        else {
            temp = dq[++head];
            dq[head] = null;
        }
        size--;
        return temp;
    }

    // remove and return the item from the back
    public Item removeLast() {
        Item temp;
        if (size == 0) throw new NoSuchElementException();

        if (size > 0 && size == dq.length / 4) resize(dq.length / 2);
        if (tail == 0) {
            temp = dq[dq.length - 1];
            dq[dq.length - 1] = null;
            tail = dq.length - 1;
        }
        else {
            int last = tail;
            temp = dq[--tail];
            dq[last] = null;
        }
        size--;
        return temp;
    }

    // return an iterator over items in order from front to back
    public Iterator<Item> iterator() {
        return new DequeIterator();
    }

    private class DequeIterator implements Iterator<Item> {

        private int current = head;


        public boolean hasNext() {

            if (current == dq.length - 1) {
                return dq[0] != null;
            }
            else {
                return dq[current + 1] != null;
            }
        }

        public void remove() {
            throw new UnsupportedOperationException();
        }

        public Item next() {
            Item item;
            if (!hasNext()) {
                throw new NoSuchElementException();
            }
            else if (current == dq.length - 1) {
                current = 0;
                item = dq[current];
            }
            else {
                item = dq[++current];
            }
            return item;
        }
    }

    private void resize(int newSize) {
        assert newSize >= size;

        Item[] newDq = (Item[]) new Object[newSize];
        for (int x = 0; x < size; x++) {
            newDq[(size / 2) + x] = dq[(head + x + 1) % dq.length];
        }
        head = (size / 2) - 1;
        tail = (size / 2) + size;
        dq = newDq;
    }

    // unit testing (required
    public static void main(String[] args) {
        Deque<Integer> test = new Deque<Integer>();

        for (int i = 0; i < 300; i++) {
            test.addFirst(i);
            if (i % 5 == 0)
                test.removeLast();
        }


        StdOut.println("Array contents... ");
        for (int i : test)
            StdOut.println(i);


    }
}
