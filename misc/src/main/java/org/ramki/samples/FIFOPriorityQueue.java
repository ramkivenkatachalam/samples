package org.ramki.samples;

import java.util.Arrays;
import java.util.Collection;
import java.util.Iterator;
import java.util.PriorityQueue;
import java.util.Queue;

/**
 * FIFOPriorityQueue is a Priority Queue, where elements having same priority are removed in FIFO
 * order. We implement this as a proxy for a PriortyQueue object "queue". When a object is inserted 
 * into the FIFOPriortyQueue, we wrap the object with a FIFO element that has an insertSeq. FIFO element
 * uses the insertSeq to break ties when the underlying objects are equal. 
 * @param <T> - any class that implements Comparable.
 * 
 * @author ramki
 */

public class FIFOPriorityQueue<T extends Comparable<T>> 
							implements Queue<T> {
	private final Queue<FIFOElement<T>> queue = new PriorityQueue<FIFOElement<T>>();
	private long insertSeq = 0;
	
	/**
	 * We use wrap every object inserted into the queue with a FIFOElement. FIFOElement's compareTo
	 * method uses the insertSeq to break ties when the underlying objects are equal.
	 */
	private static class FIFOElement<T extends Comparable<T>> 
							implements Comparable<FIFOElement<T>> {
		final private long insertSeq;
		final private T obj;

		/**
		 * @param insertSeq - a monotonically increasing sequence number that tracks how many inserts
		 * have been called on the queue.
		 * @param obj - object being inserted into queue
		 */
		public FIFOElement(long insertSeq, T obj) {
			super();
			this.insertSeq = insertSeq;
			this.obj = obj;
		}

		public T getObj() {
			return obj;
		}

		/**
		 * use only underlying obj (ignore insertSeq)
		 */
		@Override
		public int hashCode() {
			final int prime = 31;
			int result = 1;
			result = prime * result + ((obj == null) ? 0 : obj.hashCode());
			return result;
		}

		/**
		 * use only underlying obj for comparison (ignore insertSeq)
		 */
		@Override
		public boolean equals(Object obj) {
			if (this == obj)
				return true;
			if (obj == null)
				return false;
			if (getClass() != obj.getClass())
				return false;
			FIFOElement other = (FIFOElement) obj;
			if (this.obj == null) {
				if (other.obj != null)
					return false;
			} else if (!this.obj.equals(other.obj))
				return false;
			return true;
		}

		public int compareTo(FIFOElement<T> o) {
			int r = this.obj.compareTo(o.obj);
			if (r != 0)
				return r;
			else
				return new Long(this.insertSeq).compareTo(o.insertSeq);
		}

	}

	/**
	 *  Implement the Queue interface.
	 *  Just call the underlying Queue method with wrapping/unwrapping with FIFOElement as needed.
	 */
	public boolean offer(T e) {
		FIFOElement<T> f = new FIFOElement<T>(insertSeq++, e);
		return queue.offer(f);
	}

	public boolean addAll(Collection<? extends T> c) {
		for (T t: c) {
			this.add(t);
		}
		return true;
	}

	public boolean add(T e) {
		FIFOElement<T> f = new FIFOElement<T>(insertSeq++, e);
		return queue.add(f);
	}

	public void clear() {
		queue.clear();
	}

	public T element() {
		FIFOElement<T> f = queue.element();
		return (f == null)? null: f.getObj();
	}

	public T remove() {
		FIFOElement<T> f = queue.remove();
		return (f == null)? null: f.getObj();
	}

	public boolean isEmpty() {
		return queue.isEmpty();
	}

	public int size() {
		return queue.size();
	}

	public T peek() {
		FIFOElement<T> f = queue.peek();
		return (f == null)? null: f.getObj();
	}

	public T poll() {
		FIFOElement<T> f = queue.poll();
		return (f == null)? null: f.getObj();
	}

	public boolean contains(Object o) {
		// TODO: implement this
		throw new RuntimeException("Not yet implemented");
	}

	public boolean containsAll(Collection<?> c) {
		// TODO: implement this
		throw new RuntimeException("Not yet implemented");
	}

	public Iterator<T> iterator() {
		// TODO: implement this
		throw new RuntimeException("Not yet implemented");
	}

	public boolean remove(Object o) {
		// TODO: implement this
		throw new RuntimeException("Not yet implemented");
	}

	public boolean removeAll(Collection<?> c) {
		// TODO: implement this
		throw new RuntimeException("Not yet implemented");
	}

	public boolean retainAll(Collection<?> c) {
		// TODO: implement this
		throw new RuntimeException("Not yet implemented");
	}

	public Object[] toArray() {
		// TODO: implement this
		throw new RuntimeException("Not yet implemented");
	}

	public <T> T[] toArray(T[] a) {
		// TODO: implement this
		throw new RuntimeException("Not yet implemented");
	}



	public static void main(String[] args) {
		FIFOPriorityQueue<Integer> fifoQueue = new FIFOPriorityQueue<Integer>();
		fifoQueue.add(3);
		fifoQueue.add(1);
		fifoQueue.add(2);
		fifoQueue.addAll(Arrays.asList(10, 9, 1));
		while (!fifoQueue.isEmpty()) {
			Integer i = fifoQueue.remove();
			System.out.println("Removed: " + i);
		}		
	}

}
