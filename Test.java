import java.util.*;

/** 
Used to test an implementation of a sorting algorithm.
Specify which algorithm to test using command-line options.
For example:

java Test insert
java Test merge counting
*/
public class Test {

	// _______________________________________________________________
	//            INITIALIZE VARIABLES USED IN TESTING
	// _______________________________________________________________

	/** Display information when reporting results */
    private static String title = "";
    
    /** The algorithm to test */
	private static Sorter<AlphaNumeric> algo = null;
	
	/** The ordering used by the algorithm to sort the array */
	private static Comparator<AlphaNumeric> orderBy = AlphaNumeric.orderNumeric;
	
	/** Test sorting by both numeric and alpha as appropriate */
	private static boolean testAlpha = true;

	// _______________________________________________________________
	//            MAIN
	// _______________________________________________________________
    public static void main(String[] args) {
    
    	// confirm user specified algorithm to test - otherwise bail.
    	if (args.length==0) {
    		System.out.println("Specify algorithm(s) (e.g. >java Test insertion)");
    		return;
    	}
		    	
    	// determine algorithms user wants to test and instantiate that algo
    	for (int i=0; i<args.length; i++) {
    		switch (args[i].toLowerCase()) {
    			case "insert":
    			case "insertion":
    				title = "TESTING INSERTION";
    				algo = new Insertion<AlphaNumeric>(orderBy);
    				break;
    			case "counting":
    			case "count":
    				title = "TESTING COUNTING";
    				algo = new Counting<AlphaNumeric>(AlphaNumeric.numberGetter);
    				testAlpha = false;
    				break;
    			case "merge":
    				title = "TESTING MERGE";
    				algo = new Merge<AlphaNumeric>(orderBy);
    				break;
    			case "radix":
    				title = "TESTING RADIX";
    				algo = new Radix<AlphaNumeric>(AlphaNumeric.numberGetter);
    				testAlpha = false;
    				break;
    			case "tim":
					title = "TESTING TIM";
					algo = new Tim<AlphaNumeric>(orderBy);
					break;	
    			//case "quick":
    				//title = "TESTING QUICK"
    				//algo = new Quick<AlphaNumeric>(orderBy);
    				//break;
    			default:
    				// User specified something not recognized
    				System.out.println("No test for algo "+args[i]);
    				return;
    		} // end switch
    		test_algorithm();
    	} // end for
    	
    	
    } // end main
    
	// _______________________________________________________________
	//            TEST THE ALGORITHM
	// _______________________________________________________________
    public static void test_algorithm() {

		// Store the array for sorting
        AlphaNumeric[] array;
        
        System.out.println("\n___________________ "+title+" (number on REVERSED)");
        
        // Make array with numbers decreasing and alpha increasing
        array = ArrayMaker.makeArray(ArrayMaker.ArrayOrder.REVERSED,10);
        System.out.println("\nOriginal Array:");
        print(array);
        
        // call the sorter that was established through switch above
        algo.sort(array);

		System.out.println("\nSorted Array:");
		print(array);
        System.out.println("\nOperations count "+ algo.getCount());

		// if we are supposed to test ordering using alpha, do that ...
		if (testAlpha) {
			System.out.println("\n___________________ "+title+" (alpha on REVERSED)");
		
			// Use array just sorted (in reverse order relative to new comparator)
			System.out.println("\nOriginal Array:");
			print(array);
		
			// Now use comparator for the alpha component
			algo.setComparator(AlphaNumeric.orderAlpha);
			algo.sort(array);
		
		
			System.out.println("\nSorted Array:");
			print(array);		
			System.out.println("\nOperations count "+ algo.getCount());
		}
        testAlpha = false;
        // Store the array for sorting
        AlphaNumeric[] array1;
        
        System.out.println("\n___________________ "+title+" (number on SORTED)");
        
        
         // Make array with numbers decreasing and alpha increasing
        array1 = ArrayMaker.makeArray(ArrayMaker.ArrayOrder.SORTED,10);
        System.out.println("\nOriginal Array 1:");
        print(array1);
        
        // call the sorter that was established through switch above
        algo.sort(array1);

		System.out.println("\nSorted Array 1:");
		print(array1);
        System.out.println("\nOperations count "+ algo.getCount());

		// if we are supposed to test ordering using alpha, do that ...
		if (testAlpha) {
			System.out.println("\n___________________ "+title+" (alpha on SORTED)");
		
			// Use array just sorted (in reverse order relative to new comparator)
			System.out.println("\nOriginal Array 1:");
			print(array1);
		
			// Now use comparator for the alpha component
			algo.setComparator(AlphaNumeric.orderAlpha);
			algo.sort(array1);
		
		
			System.out.println("\nSorted Array 1:");
			print(array1);		
			System.out.println("\nOperations count "+ algo.getCount());
		}
		
		
		//TEST ON RANDOM 10
		testAlpha = false;

		// Store the array for sorting
        AlphaNumeric[] array2;
        
        System.out.println("\n___________________ "+title+" (number on RANDOM)");
		
		 // Make array with numbers decreasing and alpha increasing
        array2 = ArrayMaker.makeArray(ArrayMaker.ArrayOrder.RANDOM,10);
        System.out.println("\nOriginal Array 2:");
        print(array2);
        
        // call the sorter that was established through switch above
        algo.sort(array2);

		System.out.println("\nSorted Array 2:");
		print(array2);
        System.out.println("\nOperations count "+ algo.getCount());

		// if we are supposed to test ordering using alpha, do that ...
		if (testAlpha) {
			System.out.println("\n___________________ "+title+" (alpha on RANDOM)");
		
			// Use array just sorted (in reverse order relative to new comparator)
			System.out.println("\nOriginal Array 2:");
			print(array2);
		
			// Now use comparator for the alpha component
			algo.setComparator(AlphaNumeric.orderAlpha);
			algo.sort(array2);
		
		
			System.out.println("\nSorted Array 2:");
			print(array2);		
			System.out.println("\nOperations count "+ algo.getCount());
		}
        
        
        
    } // end test 

	// _____________________________________________-
	// _____________  PRINT AN ARRAY 
    public static void print(Object[] array) {
        int col = 0;
        for (Object el : array) {
        	col++;
            System.out.print(el+" ");
            if(col == 10) {
            	System.out.print("\n");
            	col = 0;
            }
            
        }
        System.out.println();
    }
}
