/**
 * Name: Ce Dong
 * Project: Week-2
 * Class: RandomizedQueue.java;
 */
import java.util.NoSuchElementException;
import java.util.Iterator;
import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.StdIn;
public class RandomizedQueue<Item> implements Iterable<Item> {
   private Item[] array;
   private int size;
   public RandomizedQueue() {
       size = 0;
       array = (Item[]) new Object[2];
   }                // construct an empty randomized queue
   public boolean isEmpty() {
       return (size == 0);
   }                 // is the randomized queue empty?
   public int size() {
       return size;
   }                        // return the number of items on the randomized queue
   private void resize(int capacity) {
       Item[] newArray = (Item[]) new Object[capacity];
       for (int i =0; i < size; i++) {
           newArray[i] = array[i];
       }
       array = newArray;
   }
   public void enqueue(Item item) {
       if (item == null) {
           throw new java.lang.IllegalArgumentException("The item is null!");
       }
       if (array.length == size) {
           resize(size *2);
       }
       array[size++] = item;
   }           // add the item
   public Item dequeue() {
       if (isEmpty()) {
           throw new NoSuchElementException("The queue is already empty!");
       }
       int index = (int) (StdRandom.uniform(0, size));
       Item itemToReturn = array[index];
       if (index != size -1) {
           array[index] = array[size -1];
       }
       array[size -1] = null;
       size--;
       if (size >0 && array.length /4 == size) {
           resize(array.length /2);
       }
       return itemToReturn;
   }                    // remove and return a random item
   public Item sample() {
       if (isEmpty()) {
           throw new NoSuchElementException("The queue is already empty!");
       }
       int index = (int) (StdRandom.uniform(0, size));
       Item itemToReturn = array[index];
       return itemToReturn;
   }                     // return a random item (but do not remove it)
   public Iterator<Item> iterator() {
       return new RQIterator();
   }         // return an independent iterator over items in random order
   private class RQIterator implements Iterator<Item> {
       private int index = 0;
       private Item[] arrayTemp;
       public RQIterator() {
           arrayTemp = (Item[]) new Object[size];
           for (int i =0; i < size; i++) {
               arrayTemp[i] = array[i];
           }
           StdRandom.shuffle(arrayTemp);
       }
       public boolean hasNext() {
           return index < size;
       }
       public Item next() {
           if (!hasNext()) {
               throw new NoSuchElementException("The queue is already empty!");
           } 
           Item itemToReturn = arrayTemp[index++];
           return itemToReturn;
       }
   }
   public static void main(String[] args) {
              RandomizedQueue<String> q = new RandomizedQueue<String>();
       StdOut.println("Type yes to start: ");
       String yes = StdIn.readString();
       while (yes.equals("yes")) {
           StdOut.println("you choice: 1.enqueue; 2.dequeue; 3.is empty? 4.size; 5.sample: ");
           int ch = StdIn.readInt();       
           switch (ch) {
  case 1://add first
      StdOut.println("Type a string to add first: ");
         String item = StdIn.readString();
         q.enqueue(item);
   break;
  case 2:
   q.dequeue();
   break;
  case 3:
   StdOut.println(q.isEmpty());
   break;
  case 4:
   StdOut.println(q.size());
   break;
  case 5:
   StdOut.println(q.sample());
//   StdOut.println(q.iterator());
   break;
   }
           StdOut.println("Type yes to start: ");
           yes = StdIn.readString();
       }
       StdOut.println("(" + q.size() + " left on queue)");
   
   }   // unit testing (optional)
}
