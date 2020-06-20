import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.StdRandom;

import java.util.Iterator;
import java.util.NoSuchElementException;

public class RandomizedQueue<Item> implements Iterable<Item> {
    private Item[] rq;
    private int size;

    // construct an empty randomized queue
    public RandomizedQueue() {
        rq = (Item[]) new Object[2];
        size = 0;
    }

    // is the randomized queue empty?
    public boolean isEmpty() {
        return size == 0;
    }

    // return the number of items on the randomized queue
    public int size() {
        return size;
    }

    // add the item
    public void enqueue(Item item) {
        if (item == null) throw new IllegalArgumentException();
        if (size == rq.length) resize(rq.length * 2);
        rq[size++] = item;
    }

    // remove and return a random item
    public Item dequeue() {
        if (size == 0) throw new NoSuchElementException();
        int index = StdRandom.uniform(size);
        Item out = rq[index];
        rq[index] = rq[--size]; // replace returned item with last item in queue
        rq[size] = null; // null the last item
        if (size > 0 && size == rq.length / 4) resize(rq.length / 2);
        return out;
    }

    // return a random item (but do not remove it)
    public Item sample() {
        if (size == 0) throw new NoSuchElementException();
        int index = StdRandom.uniform(size);
        Item out = rq[index];
        return out;
    }

    // return an independent iterator over items in random order
    public Iterator<Item> iterator() {
        return new RandomIterator();
    }

    private class RandomIterator implements Iterator<Item> {

        private final Item[] randomQ;
        private int n;

        public RandomIterator() {
            randomQ = (Item[]) new Object[size];
            for (int i = 0; i < size; i++)
                randomQ[i] = rq[i];
            StdRandom.shuffle(randomQ);
            n = 0;
        }

        public boolean hasNext() {
            return n != size;
        }

        public void remove() {
            throw new UnsupportedOperationException();
        }

        public Item next() {
            if (!hasNext()) throw new NoSuchElementException();
            return randomQ[n++];
        }
    }

    private void resize(int newSize) {
        Item[] newRq = (Item[]) new Object[newSize];
        for (int x = 0; x < size; x++) {
            newRq[x] = rq[x];
        }
        rq = newRq;
    }

    // unit testing (required)
    public static void main(String[] args) {
        RandomizedQueue<Integer> test = new RandomizedQueue<>();

        for (int i = 0; i < 10; i++) {
            test.enqueue(i);
        }
        for (int x : test)
            StdOut.println(x);
    }

}
