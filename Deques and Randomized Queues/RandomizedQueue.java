import java.util.Iterator;
import edu.princeton.cs.algs4.StdOut;
import java.lang.*;
import java.util.NoSuchElementException;

public class RandomizedQueue<Item> {

    //create randomized queue using resized array
    private class ResizingArray{
        private Item[] array;
        private int N = 1;
    }

    private ResizingArray RdQueue;

    // construct an empty randomized queue
    public RandomizedQueue(){
        RdQueue = new ResizingArray();
    }

    // is the randomized queue empty?
    public boolean isEmpty(){
        return RdQueue.N == 0;
    }

    // return the number of items on the randomized queue
    public int size()  {
        return RdQueue.N;
    }


    public void enqueue(Item item)           // add the item
    public Item dequeue()                    // remove and return a random item
    public Item sample()                     // return a random item (but do not remove it)
    public Iterator<Item> iterator()         // return an independent iterator over items in random order
    public static void main(String[] args)   // unit testing (optional)
}
