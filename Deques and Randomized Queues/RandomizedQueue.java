import java.util.Iterator;
import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.StdRandom;

import java.lang.IllegalArgumentException;
import java.lang.UnsupportedOperationException;
import java.util.NoSuchElementException;

public class RandomizedQueue<Item> implements Iterable<Item>{
    private Item[] array;
    private int n;

    // construct an empty randomized queue
    public RandomizedQueue(){
        array = (Item[]) new Object[2];
        n = 0;
    }

    // is the randomized queue empty?
    public boolean isEmpty(){
        return n == 0;
    }

    // return the number of items on the randomized queue
    public int size()  {
        return n;
    }

    //implement resizing method: double when full, halve when 1/2
    private void resizing(int capacity){
        assert capacity >= n;
        Item[] temp = (Item[]) new Object[capacity];
        for (int i = 0; i < n; i ++){
            temp[i] = array[i];
        }
        array = temp;
    }

    // add the item
    public void enqueue(Item item){
        if (item == null){
            throw new IllegalArgumentException("Do not enqueue with null");
        }
        if (n == array.length) resizing(array.length * 2);
        array[n++] = item;
    }

    // remove and return a random item
    public Item dequeue(){
        if (isEmpty()){
            throw new NoSuchElementException("Randomized Queue is empty. No more element");
        }

        int r = StdRandom.uniform(n);
        Item item = array[r];
        for (int i = r; i < n-1; i ++){
            array[i] = array[i+1];
        }
        array[n-1] = null;
        n--;

        if (n > 0 && n == array.length/4) resizing(array.length / 2);
        return item;
    }

    @Override
    public String toString(){
        StringBuilder out = new StringBuilder();
        for (Item item:array){
            out.append(item + " ");

        }
        return out.toString();
    }

    // return a random item (but do not remove it)
    public Item sample(){
        if(isEmpty()){
            throw new NoSuchElementException("Randomized Queue is empty. No more element");
        }
        int rand = StdRandom.uniform(n);
        return array[rand];
    }

    // return an independent iterator over items in random order
    public Iterator<Item> iterator() {return new RQueueIterator();}

    private class RQueueIterator implements Iterator<Item>{
        public boolean hasNext(){
            return n != 0;
        }

        public void remove() { throw new UnsupportedOperationException("remove is not supported");}

        public Item next(){
            if (!hasNext()){
                throw new NoSuchElementException("Randomized Queue is empty. No more element");
            }
            return dequeue();
        }
    }

    // unit testing (optional)
    public static void main(String[] args){
        RandomizedQueue<Integer> rq = new RandomizedQueue<Integer>();

        StdOut.println("is empty: " + rq.isEmpty());
        StdOut.println("size of rq: " + rq.size());

        rq.enqueue(10);
        rq.enqueue(3);

        StdOut.println("is empty: " + rq.isEmpty());
        StdOut.println("size of rq: " + rq.size());
        rq.enqueue(7);
        rq.enqueue(5);
        StdOut.println(rq.toString());

        int s = rq.sample();
        StdOut.println("sample: " + s);

        int out1 = rq.dequeue();
        StdOut.println("1st out element: " + out1);
        int out2 = rq.dequeue();
        StdOut.println("2nd out element: " + out2);
        StdOut.println(rq.toString());
    }

}
