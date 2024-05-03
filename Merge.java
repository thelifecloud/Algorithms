import java.util.Comparator;

/** Merge Sort divide-and-conquer recursive algorithm */
public class Merge<T> implements Sorter<T> {
    /** Establishes ordering of type T */
    private Comparator<T> orderBy;
    
    /** Counter of compare operations */
    long count = 0;
    
    /** Constructor for Merge Sort to set comparator
    * @param order Comparator to establish ordering of array elements.
    */
    public Merge(Comparator<T> order) {
        orderBy = order;    
    }
    
    /** Sorts specified array using Merge Sort
    * @param array Array to be sorted.
    */
    @Override
    public void sort(T[] array) {
        //Reset count back to 0
		sort(array, 0, array.length - 1);	
    }
 	
 	/** Sort for Tim Sort merges */
	public void sort(T[] A, int p, int q, int r) {
		merge(A,p,q,r);
	}
	  
	public void sort(T[] A, int p, int r) {
		// count each comparison of p and r.
		count++;
		// check if splitting is complete
		if (p < r) {	
			// get middle
			int q = (p+r)/2;
			// recursive call to get L[]
			sort(A, p, q);
			// recursive call to get R[]
			sort(A, q + 1, r);
			// merge it
			merge(A,p,q,r);
		}
	}

	/** Merge sort algorithm */
	private void merge(T[] A, int p, int q, int r) {		
		// End marker for L[]
		int n1 = q - p + 1;
		// End marker for R[]
		int n2 = r - q;	
		// Create Left subarray
		@SuppressWarnings("unchecked")
		T[] L = (T[]) new Object[n1];	
		for(int i = 0; i < n1; i++) {
			count++;
			L[i] = A[p + i];
		}
		// Create Right subarray 
		@SuppressWarnings("unchecked")
		T[] R = (T[]) new Object[n2];		
		for(int j = 0; j < n2; j++) {
			count++;
			// The right half starts at [q+1] to [r]
			R[j] = A[q + 1 + j];
		}		

		// index markers for merge control in while loop.
		int i = 0;
		int j = 0;
		int k = p;
		// while not at end of either subarray
		while(i < n1 && j < n2) {
			// increment count for each comparison in while loop
			count++;
			if(orderBy.compare(L[i], R[j]) <= 0) {
				A[k++] = L[i++];
			} else {
				A[k++] = R[j++];
			}
		}
		// one of the subarrays is at end 
		// check L for remaining values and dump into A if present
		while(i < n1) {
			// increment count for i and n1 comparison
			count++;
			A[k++] = L[i++];
		}
		// check R for remaining values and dump into A if present
		while(j < n2) {
			// increment count for j and n2 comparison
			count++;
			A[k++] = R[j++];
		}
	} // end Merge function
    @Override
    public void setComparator(Comparator<T> order) {
		orderBy = order;
	}
    
    @Override
    public long getCount() {
        return count;
    }
} // end class Merge
