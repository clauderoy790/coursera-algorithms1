import java.util.Iterator;

import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.StdRandom;

public class RandomizedQueue<Item> implements Iterable<Item> {
	Item[] array;
	int size = 0;

    // construct an empty randomized queue
    public RandomizedQueue() {
    	array = null;
    	size = 0;
    }

    // is the randomized queue empty?
    public boolean isEmpty() {
    	return size() == 0;
    }

    // return the number of items on the randomized queue
    public int size() {
    	return size;
    }

    // add the item
    public void enqueue(Item item) {
    	if (item == null) {
			throw new IllegalArgumentException("Cannot add null item");
		}
    	
    	if (shouldExpandArray()) {
    		int minSize = Math.max(size(), 1);
    		resizeArray(minSize*2);
    	}
    	array[size++] = item;
    }

    // remove and return a random item
    public Item dequeue() {
    	if (isEmpty()) {
    		throw new java.util.NoSuchElementException();
    	}
    	int index = StdRandom.uniform(size);
    	Item item = array[index];
    	array[index] = null;
    	for (int i = index+1;i < size();++i) {
    		array[i-1] = array[i];
    	}
    	size--;
    	if (shouldShrinkArray()) resizeArray(size()/4);
    	
    	return item;
    }

    // return a random item (but do not remove it)
    public Item sample() {
    	if (isEmpty()) {
    		throw new java.util.NoSuchElementException();
    	}
    	System.out.println("size: "+size());
    	int nb = StdRandom.uniform(size());
    	System.out.println("nb: "+nb);
    	return array[StdRandom.uniform(size())];
    }

    
    boolean shouldExpandArray() {
    	return size() == 0 || size() == array.length;
    }
    
    boolean shouldShrinkArray() {
    	return size() <= array.length/4;
    }
    
    void resizeArray(int newSize) {
    	Item[] newArr = (Item[]) new Object[newSize];
    	for (int i = 0; i < size();++i) {
    		newArr[i] = array[i];
    	}
    	array = newArr;
    }
    
    // return an independent iterator over items in random order
    public Iterator<Item> iterator() {
    	return new RandomizedDequeIterator();
    }

	private class RandomizedDequeIterator implements Iterator<Item> {
		Node first;
		int size;
		
		public RandomizedDequeIterator() {
			size = array.length;
			StdRandom.setSeed(StdRandom.uniform(Long.MAX_VALUE));
			Node previous = null;
			for (Item item : array) {
				Node node = new Node(item);
				if (first == null) {
					first = node;
				}
				if (previous != null) {
					node.previous = previous;
					previous.next = node;
				}
				previous = node;
			}
		}
		public boolean hasNext() {
			return first != null;
		}

		public void remove() {
			throw new UnsupportedOperationException();
		}

		public Item next() {
			if (!hasNext())
				throw new java.util.NoSuchElementException();
			
			int ind = StdRandom.uniform(size);
			Node node = first;
			for (int i = 0; i < ind;++i) {
				node = first.next;
			}
			if (node.previous != null) {
				node.previous.next = node.next;
			}
			if (node.next != null) {
				node.next.previous = node.previous;
			}
			
			--size;
			return node.item;
		}
		
		private class Node {
			Item item;
			Node next;
			Node previous;

			Node(Item i) {
				next = null;
				item = i;
			}
		}
	}
    

    // unit testing (required)
    public static void main(String[] args) {
    	RandomizedQueue<String> queue = new RandomizedQueue<>();
    	
		StdOut.println("new queue is not null: "+(queue != null));
		StdOut.println("is empty is true: "+queue.isEmpty());
		StdOut.println("size is 0: "+queue.size());
		queue.enqueue("salut");
		StdOut.println("Should be salut: "+queue.sample());
		queue.enqueue("coucou");
		StdOut.println("size should be 2: "+(queue.size() == 2));
		queue.dequeue();
		StdOut.println("size should be 1 after dequeue: "+queue.size());
		StdOut.println("Sample should be something: "+queue.sample());
    }

}