/**
 *  Name: Ce Dong
 *  Project: Deques and Randomized Queues
 *  Class: Deque.java
 */
import java.util.Iterator;
import java.util.NoSuchElementException;
import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.StdIn;

public class Deque<Item> implements Iterable<Item> {
   private class Node {
       Item content;
       Node next;
       Node prev;
   }
   private int size;
   private Node first;
   private Node last;
   public Deque() {
       first = null;
       last = null;
       size = 0;
   }                           // construct an empty deque
   public boolean isEmpty() {
       return (size == 0);
   }                 // is the deque empty?
   public int size() {
       return size;
   }                        // return the number of items on the deque
   public void addFirst(Item item) {
       if (item == null) throw new java.lang.IllegalArgumentException();
       Node addFirst = new Node();
       addFirst.content = item;
       addFirst.prev = null;
       if (isEmpty()) {
           first = addFirst;
           last = addFirst;
           addFirst.next = null;
       } else {
           addFirst.next = first;
           first.prev = addFirst;
           first = addFirst;
       }
       size++;
   }         // add the item to the front
   public void addLast(Item item) {
       if (item == null) throw new java.lang.IllegalArgumentException();
       Node addLast = new Node();
       addLast.content = item;
       addLast.next = null;
       if (isEmpty()) {
           addLast.prev = null;
           first = addLast;
           last = addLast;
       } else {
           addLast.prev = last;
           last.next = addLast;
           last = addLast;
       }
       size++;
   }           // add the item to the end
   public Item removeFirst() {
       if (first == null) {
           throw new NoSuchElementException("The deque is already empty!");
       }
       Item firstToReturn = first.content;
       size--;
       if (isEmpty()) {
           first = null;
           last = null;
       } else {
           first = first.next;
           first.prev = null;
       }
       return firstToReturn;
   }               // remove and return the item from the front
   public Item removeLast() {
       if (last == null) {
           throw new NoSuchElementException("The deque is already empty!");
       }
       Item lastToReturn = last.content;
       size--;
       if (isEmpty()) {
           first = null;
           last = null;
       } else {
           last = last.prev;
           last.next = null;
       }
       return lastToReturn;
   }                 // remove and return the item from the end
   public Iterator<Item> iterator() {
       return new ListIterator();
   }
   private class ListIterator implements Iterator<Item> {
       private Node current = first;
       public boolean hasNext() {
           return current != null;
       }
       public Item next() {
           if (!hasNext()) {
               throw new NoSuchElementException();
           }
           Item item = current.content;
           current = current.next;
           return item;
       }
   } // return an iterator over items in order from front to end
   public static void main(String[] args) {
       Deque<String> q = new Deque<String>();
       StdOut.println("first step!");
       System.out.println("Type yes to start: ");
       String s = StdIn.readString();
       while (s.equals("yes")) {
           StdOut.println("you choice: 1.add first; 2.add last; 3.is empty? 4.size; 5.remove first; 6. remove last");
           int ch = StdIn.readInt();
           
           switch (ch) {
  case 1://add first
      System.out.println("Type a string to add first: ");
         String item = StdIn.readString();
         q.addFirst(item);
         StdOut.println(q.iterator());
   break;
  case 2:
      System.out.println("Type a string to add last: ");
   String itema = StdIn.readString();
   q.addLast(itema);
         StdOut.println(q.iterator());
   break;
  case 3:
   StdOut.println(q.isEmpty());
         StdOut.println(q.iterator());
   break;
  case 4:
   StdOut.println(q.size());
   break;
  case 5:
   q.removeFirst();
         StdOut.println(q.iterator());
   break;
  case 6:
   q.removeLast();
         StdOut.println(q.iterator());
   break;
  }
           System.out.println("Type yes to go on: ");
           s = StdIn.readString();
       }
       StdOut.println("(" + q.size() + " left on queue)");
   }   // unit testing (optional)
}
