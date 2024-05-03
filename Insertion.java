import java.util.Comparator;

/** Insertion sort - an in-place sorting algorithm */
public class Insertion<T> implements Sorter<T> {

    /** Establishes ordering of type T */
    private Comparator<T> orderBy;

    /** Counter of compare operations */
    long count = 0;

    /** Constructor for Insertion Sort to set comparator
    * @param order Comparator to establish ordering of array elements.
    */
    public Insertion(Comparator<T> order) {
        orderBy = order;
    }
    
    @Override
    public void sort(T[] array) {
    	// call sort on whole array
    	sort(array, 0, array.length - 1);
    }
    
    /** overloaded for Tim Sort*/
    public void sort(T[] array, int p, int r) {
    	insertion(array, p, r);
    }
    
    /** Overloaded sort for specified start and end points using Insertion Sort.
    * @param array Array to be sorted.
    * @param p start index
    * @param r end index
    */
    public void insertion(T[] array, int p, int r) {
		// create key
		T key = null;
		// create while index
		int i = p;
		// outter loop to control key index
		for(int j = 1; j <= r; j++) {
			// increment count for each outter loop comparison
			count++;
			// set key
			key = array[j];
			// get index of insertion point, 1 before key..
			i = j - 1;
			while(i >= 0 && (orderBy.compare(key, array[i]) < 0)) {
				// increment count for each inner loop comparison
				count++;
				// if less key is less than, move it back.
				array[i + 1] = array[i];
				i = i - 1;	
			}
			// store it and onto next
			array[i + 1] = key;
		}
    } 

    @Override
    public void setComparator(Comparator<T> order) {
        orderBy = order;
    }
    @Override
    public long getCount() {
        return count;
    }
} // end class Insertion
