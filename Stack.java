/** Linked List Stack that implements user-defined QueueInterface */
public class Stack<T> implements QueueInterface<T> {
	// store number of elements currently on stack
	private int max_count = 0;
	private int count = 0;
	private Node<T> head;

	/** default constructor */
	public Stack() {}
	
	// ____________ Implementation Methods ________________ //
	
	/** isEmpty returns true if list is empty */
	public boolean isEmpty() {
		return head == null;
	}
	/** Push item onto top of Stack*/
	public void push(T value) {
		// store value in node with buffer
		Node<T> curr = new Node(value, head);
		// adjust buffer
		curr.next = head;
		// head becomes new node
		head = curr;
		// increment count
		count++;
		
		// check for updates to max_count
		if(count > max_count) {
			max_count = count;
		}
	}
	
	/** Pop item off top of stack */
	public T pop() {
		// empty check
		if(isEmpty()) { return null;}
		// create temp to return popped 
		Node<T> temp = head;
		// chop off the head
		head = head.next;
		// decrement the count
		count--;
		// return popped
		return temp.value;
	} 
	
	/** Return item at top of stack */ 
	public T peek() {
		if(isEmpty()) {return null;}
		return head.value;
	}
	
// _______________ Helper Methods _____________________	//
	
	/** toString()	Convert list to print formatted string of elements
	 * Returns print formatted string of list elements.
	 */
  	@Override
  	public String toString() {
  		// declare return string
    	String rs = "Stack contents: ";
       	// iterator
        Node curr = head;
        // index location of curr in list							
        int index = 0;		
        // while not at end of list					 		
        while(curr != null) {	
        	// add 1 based index add username	
        	rs += curr.value + ", ";	
			// move to next element
			curr = curr.next;        
			//increment index for accurate numbering of list
        } 
        // return string of elements in list
        return rs;
    }
	
	/** Return the greatest number elements in Stack at once */
	public int max_count() { 
		return max_count; 
	}
	
	/** definition for class Node */
	public class Node<T> {
		T value = null;
		Node next = null;
		/** default Node constructor */
		public Node(T val, Node curr) {
			value = val;
			next = curr;
		}
	}
}