import java.util.Comparator;
import java.util.function.Function;
import java.util.*;

/** Counting Sort assumes key of type Integer */
public class Counting<T> implements Sorter<T> {

    /** Extracts the key from an object in the array */
    Function<T,Integer> keyGetter;

    /** Max Value in the array to be sorted */
    Integer maxValue = null;

    /** Counter of loop iterations */
    long count = 0;

    /** Default empty constructor. */
    public Counting() {}
        
        /** Constructor for Counting
        * @param order Comparator to establish ordering of array elements.
        */
        public Counting(Function<T,Integer> getter) {
            keyGetter = getter;
        }
        
        /** Constructor for Counting with known max value
        * @param order Comparator to establish ordering of array elements.
        */
        public Counting(Function<T, Integer> getter, Integer maximum) {
            keyGetter = getter;
            this.maxValue = maximum;
        }
		
		/** setter for keyGetter */
        public void setKeyGetter(Function<T, Integer> getter) {
            keyGetter = getter;
        }

        /** Sorts specified array using Counting Sort. Inplace version of the sorter.
        * @param array Array to be sorted.
        */
        @Override
        public void sort(T[] array) {
			// Get max value for current array.
			maxValue = findMax(array);
		
         	// Create array C: working array (*histogram)	
	        @SuppressWarnings("unchecked")
			int[] C = new int[maxValue + 1];
			// set counts for each value <= maxValue as 0.
			for(int i = 0; i <= maxValue; i++) {
				// count each comparison of i and maxValue
				count++;
				C[i] = 0;
			}
			
			// Create array A: unsorted to hold array to be sorted.
            @SuppressWarnings("unchecked")
            T[] A = (T[]) new Object[array.length];
     
			// populate array unsorted with contents of passed array
			for (int i=0; i < array.length; i++) {
                // count each comparison of i and array.length
                count++;
                A[i] = array[i]; 
            }				
			
			// count occurence of each value in A[]
			for(int j = 1; j < array.length; j++){
				// count each comparison of j and array.length
				count++;
			 	C[keyGetter.apply(A[j])] = C[keyGetter.apply(A[j])] + 1;
			}
			// add prev to curr for sum of numbers less than value of current element.
			for(int i = 1; i <= maxValue; i++) {
				// count each comparison of i and maxValue
				count++;
				C[i] += C[i - 1];
			}
			// build the output array for copying to passed array[]
			for(int i = array.length-1; i >= 0; i--){
				// count each comparison of i and 0
				count++;
				// get index of sorted location of current element i
				A[C[keyGetter.apply(array[i])]] = array[i];
			}
			// copy sorted A to array
			for(int i = 0; i < array.length; i++) {
				// count each comparison of i and array.length
				count++;
				array[i] = A[i];
			}
        } // end sort(T[])
       
        private Integer findMax(T[] array) {
        	// if length is 0, then max is 0. C will have one index that counts 0.
            if(array.length == 0) {return 0;} 
            Integer max = keyGetter.apply(array[0]);
           	// compare value at current iteration to value of current greatest to get max.
            for (T element : array) {
        		count++;
                Integer valueOf = keyGetter.apply(element);
                if (valueOf > max) {
                	max = valueOf;
                }
            } return max;
       } // end findMax()

        @Override
        public long getCount() {
            return count;
        }

        @Override
        public void setComparator(Comparator<T> c) {
            // not relevant for counting sort
        }
    } // end class Countin
