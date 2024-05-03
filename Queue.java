import java.util.Arrays;

/** Generic Queue using Circular Array with sentinel. Implements QueueInterface */
public class Queue<T> implements QueueInterface<T> {
	// first element in array index
	private int head;
	// index to place next added value
	private int tail;
	// number of elements in queue
	private int count = 0;
	// max number of nodes on queue at single time
	private int max_count = 0;
	// set queue cap for easy resize if necessary
	private int CAPACITY = 20;
	/** data structure */
	private T[] q;
	
	
	/** Default Constructor */
	public Queue() {
		// generic array with size plus one for sentinel
		q = (T[]) new Object[count+1];
		// first is index 0;
		head = 0;
		// circular, last element one away from 0.
		tail = - 1;
		// size starts 0
		count = 0; 
	}	
	// ____________ Implementation Methods ________________ //
	
	@Override
	/** Remove and return first element from queue */
	public T pop() {
		// check if empty
		if(isEmpty()) { return null;}
		// store first element for return 
		T temp = q[head];
		// set head null removes head
		q[head] = null;
		// get next element to store in first position: drop old first
		head = (head + 1) % q.length;
		// decrement count of queue elements
		count--;
		// return removed item
		return temp;
	}	
	
	@Override
	/** Add value to end queue */
	public void push(T object) {
		// check if full		
		if(count == q.length - 1) {
			// if full resize 
			arrayResize();
		}
		// add new value at last index
		tail = (tail + 1) % q.length;
		q[tail] = object;
		// increment size queue
		count++;
		// check if max_count is exceeded
		if(count > max_count){
			max_count = count;
		}
	} 
 
 	@Override
	/** Return first element in queue */
	public T peek() {
		// check if empty
		if(isEmpty()) { return null; }
		// return first item
		return q[head];	
	}
	
	@Override
	/** Return true if queue is empty */
	public boolean isEmpty() {
		return count == 0;
	}	
	
// _______________ Helper Methods _____________________	//
	
	/** Return single line string representation of elements in Queue */
	@Override
	public String toString() {
		if(!isEmpty()) {
			return "Queue contents: " + q;
		} else {
			return "Queue empty";
		}
	}

	/** resize() doubles the current size of the queue. */
	private void arrayResize() {
		// declare a queue of double the size
		T[] resizedQueue = (T[]) new Object[CAPACITY * 2];
		// assign all queue elements to new queue
		for(int i = 0; i < q.length; i++) {
			// shift elements 
			resizedQueue[i] = q[(head + i) % q.length];	
		}		
		// make the queue bigger queue
		this.q = resizedQueue;
		this.head = 0;
		this.tail = q.length - 1;
	}	
}


