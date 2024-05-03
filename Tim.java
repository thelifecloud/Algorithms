import java.util.Comparator;

/** TimSort - a hybrid sorting algorithm */
public class Tim<T> implements Sorter<T> {
	/** Establishes ordering of type T */
    private Comparator<T> orderBy;
	/** Establish sorters type T for algo calls */
	private Insertion<T> insertion; 	
	private Merge<T> merge;

    /** Counter of compare operations */
    long count = 0;
	
	/** Minimum run length */	
	private static int threshold = 32;
	
	
	/** Constructor for Tim Sort: set comparator and sorters.
    * @param order Comparator to establish ordering of array elements.
    */
    public Tim(Comparator<T> order) {
        orderBy = order;
        insertion = new Insertion<>(orderBy);		
		merge = new Merge<>(orderBy);
    }

	/** Sorts given array of length n using TimSort 
	 * @param array Array to be sorted
	 * @param n Length of array
	 */
	public void sort(T[] array) {
		// reset count on first call to sort
		count = 0;
		// call first sort on entire array.	
		sort(array, 0, array.length - 1);	
	}	
	
	/** Sort for standard merge */
	public void sort(T[] array, int p, int r) {
		// get length of specified subarray of passed array for calcRun
		int n = r - p + 1;

		// check for adequate sort size
		int runLength = calcRun(n);
		for(int i = p; i < r; i += runLength) {
			count++;
			// get end index of current run to keep in bounds
			if((i + runLength) < r) {
				r = i + runLength - 1; 
			} else {
				r = r;
			}
			// call insertion sort from i to end of run
			insertion.sort(array, i, r);
			count += insertion.getCount();
		}
		
		// each run is now sorted
		// now merge each run, doubling array size for each merge
		for(int newRunLength = runLength; newRunLength < n; newRunLength*=2) {			
			count++;
			// starting from the left get proper indices
			for(int k = p; k <= r; k += (newRunLength*2)) {
				count++;
				// start of left: k
				// end of left: q == min(k + newRunLength - 1, r)
				int q;
				// get q, index for end left & start right for merge call
				if(k + newRunLength < r) {

					q = k + newRunLength - 1;
				} else {
					q = r - 1;
				}
				count++;
				// get end : either double or end of subarray
				if((k + newRunLength*2) -1 < r) {
					r = k + newRunLength*2 - 1;
				} else {
					r = r;
				}
				count++;
				merge.sort(array, k, q, r);
				count += merge.getCount();
			}
		}
	}	

	/** 
	 *calcRun breaks array down by powers of two until run size threshold is met 
	 *@param n is the size of the current run
	*/
	public int calcRun(int n) {
		// r stores remainders of divisions of by 2 for add back to n
		int r = 0; 
		// track powers of 2 required to reach threshold for required number of runs
		int power = 0;
		// halve n until n <= threshold to get the size and total number of runs
		while(n > threshold) {
			// if split is not even increment r to account for remainder.
			if(n%2 == 1) {
				r += 1;
			}
			// bitwise division by two
			n >>= 1;
			// increase power
			power += 1;
			}
		return n + r;
	} // end calcRun 

	/** exponent function for powers of 2 */
	private int pow(int power) {
		// return, 2^0 = 1 
		int result = 1;
		// base 2 method
		int base = 2;
		// increment count for if comparison
		count++;
		if(power != 0) {
			for(int i = 0; i < power; i++) {
				// count comparisons of i to power
				count++;
				result = result*base;
			}
		}
		return result;
	}

	/** Sets comparator to Tim and sets comparators for merge / insertion */
    @Override
    public void setComparator(Comparator<T> order) {
    	orderBy = order;
    	merge.setComparator(order);
    	insertion.setComparator(order);
    }
    @Override
    public long getCount() {
        return count;
    }
} // end class Tim


