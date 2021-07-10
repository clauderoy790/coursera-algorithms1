import java.util.Iterator;
//import edu.princeton.cs.algs4.StdOut;

import edu.princeton.cs.algs4.StdOut;

public class Deque<Item> implements Iterable<Item> {
	private Node first, last;
	private int size;

	// construct an empty deque
	public Deque() {
		first = null;
		last = null;
		size = 0;
	}

	// is the deque empty?
	public boolean isEmpty() {
		return size() == 0;
	}

	// return the number of items on the deque
	public int size() {
		return size;
	}

	// add the item to the front
	public void addFirst(Item item) {
		if (item == null) {
			throw new IllegalArgumentException("Cannot add null item");
		}

		Node node = new Node(item);
		if (first == null) {
			first = last = node;
		} else {
			node.next = first;
			first.previous = node;
			first = node;
		}
		size++;

	}

	// add the item to the back
	public void addLast(Item item) {
		if (item == null) {
			throw new IllegalArgumentException("Cannot add null item");
		}

		Node node = new Node(item);
		if (last == null) {
			first = last = node;
		} else {
			node.previous = last;
			last.next = node;
			last = node;
		}
		size++;

	}

	// remove and return the item from the front
	public Item removeFirst() {
		if (first == null) {
			throw new java.util.NoSuchElementException();
		}
		Item temp = first.item;
		if (first == last) {
			last = null;
			first = null;
		} else {
			first = first.next;
			first.previous = null;
		}
		size--;
		return temp;
	}

	// remove and return the item from the back
	public Item removeLast() {
		if (last == null) {
			throw new java.util.NoSuchElementException();
		}
		Item temp = last.item;
		if (first == last) {
			last = null;
			first = null;
		} else {
			last = last.previous;
			last.next = null;
		}
		size--;
		return temp;
	}

	// return an iterator over items in order from front to back
	public Iterator<Item> iterator() {
		return new DequeIterator();
	}

	private class DequeIterator implements Iterator<Item> {
		private Node current = null;
		
		public DequeIterator() {
			current = first;
		}

		public boolean hasNext() {
			return current != null;
		}

		public void remove() {
			throw new UnsupportedOperationException();
		}

		public Item next() {
			if (!hasNext())
				throw new java.util.NoSuchElementException();
			
			Item item = current.item;
			current = current.next;
			return item;
		}
	}

	private class Node {
		Item item;
		Node next, previous;

		Node(Item i) {
			next = null;
			previous = null;
			item = i;
		}
	}

	// unit testing (required)
	public static void main(String[] args) {
		Deque<String> queue = new Deque<>();
	
		StdOut.println("new queue is not null: "+(queue != null));
		StdOut.println("is empty is true: "+queue.isEmpty());
		StdOut.println("size is 0: "+queue.size());
		
		queue.addFirst("salut");
		StdOut.println("first is eqaul to salut "+(queue.first.item == "salut"));
		queue.addLast("coucou");
		StdOut.println("last is eqaul to coucou "+(queue.last.item == "coucou"));
		StdOut.println("first != last "+(queue.last.item != queue.first.item));
		queue.addFirst("bonjour");
		StdOut.println("first == bonjour "+(queue.first.item == "bonjour"));
		StdOut.println("first.next == salut "+(queue.first.next.item == "salut"));
		queue.removeFirst();
		StdOut.println("remove first ");
		StdOut.println("first == salut "+(queue.first.item == "salut"));
		queue.removeLast();
		StdOut.println("remove last");
		StdOut.println("last == salut "+(queue.last.item == "salut"));
	}
}