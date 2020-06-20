import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdOut;

public class Permutation {
    public static void main(String[] args) {
        int k = Integer.parseInt(args[0]);
        RandomizedQueue<String> randQ = new RandomizedQueue<>();
        while (!StdIn.isEmpty())
            randQ.enqueue(StdIn.readString());
        for (int i = k; i > 0; i--)
            StdOut.println(randQ.dequeue());
    }
}
