import java.util.Iterator;
import edu.princeton.cs.algs4.StdOut;
import java.lang.*;
import java.util.NoSuchElementException;

//Implements Deque using a Linkedlist structure
//public class Deque<Item> implements Iterable<Item>{
public class Deque<Item>{

    private class Node{
        Item item;
        Node prev;
        Node next;

        Node(Item item){
            this.item = item;
        }
    }

    private Node head;
    private Node tail;
    private int size;

    // construct an empty deque with dummy head and tail
    public Deque() {
        head = new Node(null);
        tail = new Node(null);
        head.next = tail;
        tail.prev = head;

    }

    // is the deque empty?
    public boolean isEmpty(){
        return size == 0;
    }

    // return the number of items on the deque
    public int size() {
        return size;
    }

    // add the item to the front
    public void addFirst(Item item){
        if (item == null) {
            throw new IllegalArgumentException("Please supply an item that is not null");
        }

        Node newNode = new Node(item);
        newNode.next = head.next;
        newNode.prev = head;
        head.next.prev = newNode;
        head.next = newNode;
        size ++;
    }

    // add the item to the end
    public void addLast(Item item){
        if (item == null){
            throw new IllegalArgumentException("Please supply an item that is not null");
        }

        Node newNode = new Node(item);
        newNode.next = tail;
        newNode.prev = tail.prev;
        tail.prev.next = newNode;
        tail.prev = newNode;
        size ++;
    }

    @Override
    public String toString(){
        String s = "[ ";
        while (head.next != tail){
            head = head.next;
            s += head.item;
            s += " ";
        }
        s += "]";
        return s;
    }


    // remove and return the item from the front
    public Item removeFirst(){
        if (size == 0){
            throw new NoSuchElementException("Deque is now empty");
        }

        Node first = head.next;
        head.next = first.next;
        head.next.prev = head;
        size --;

        return first.item;
    }

    // remove and return the item from the end
    public Item removeLast(){
        if (size == 0){
            throw new NoSuchElementException("Deque is now empty");
        }

        Node last = tail.prev;
        tail.prev = last.prev;
        tail.prev.next = tail;
        size --;

        return last.item;
    }

    // return an iterator over items in order from front to end
    public Iterator<Item> iterator(){ return new DequeIterator();}

    private class DequeIterator implements Iterator<Item>{
        private Node curr = head;

        public boolean hasNext(){
            return curr.next != tail;
        }

        public void remove() {
            throw new UnsupportedOperationException("remove not supported here");
        }

        public Item next(){
            if (!hasNext()){
                throw new NoSuchElementException("No more element in the deque");
            }
            curr = curr.next;
            return curr.item;
        }
    }

    // unit testing (optional)
    public static void main(String[] args){
        Deque<String> de = new Deque<String>();

        StdOut.println("Is deque empty: " + de.isEmpty());
        StdOut.println("Deque has size: " + de.size());

        de.addFirst("first");
        StdOut.println("After adding 1st item, deque has size: " + de.size());
        de.addFirst("second");
        StdOut.println("After adding 2nd item, deque has size: " + de.size());
        de.addLast("third");
        StdOut.println("After adding 3rd item, deque has size: " + de.size());

        StdOut.println("Before remove head: " + de.toString());
        StdOut.println("Is deque empty: " + de.isEmpty());

        String out = de.removeFirst();
        StdOut.println("After remove first: " + de.toString());

    }
}

