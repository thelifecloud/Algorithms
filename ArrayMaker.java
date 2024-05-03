import java.util.Random;
import java.io.*;

/** Creates AlphaNumeric arrays for testing. */
public class ArrayMaker {

	/** Different characteristics of the arrays to be sorted */
	public static enum ArrayOrder {
		REVERSED, 	// numbers decreasing with alpha increasing
		SORTED, 	// numbers increasing with alpha decreasing
		RANDOM		// all unique (specify percent rearranged )
	};

	/** Length of alpha string */
	private static int alength = 6;

	private static int percent = 100;
	private static String filename = "data.txt";
	private static int size = 100000;

	// default ordering if not user-specified
	private static ArrayOrder order = ArrayOrder.RANDOM;

	// _______________________________________________________________
	//            MAIN
	// _______________________________________________________________
	public static void main(String[] args) {

		// confirm user provided information regarding data to create
		if (args.length==0) {
			System.out.println("Need to specify requirements");
			return;
		}

		parse_arguments(args);

		// Split filename into name and extension
		String fname = (filename.split("\\."))[0];
		String ext = (filename.split("\\."))[1];

		// Need to create 3 versions if creating random arrays
		int versions = 1;
		if (order.equals(ArrayOrder.RANDOM)) {
			versions = 3;
			fname = fname+"_rand"+percent;
		}

		// Store the filenames
		String[] filenames = new String[versions];

		// Create array
		AlphaNumeric[] array = null;


		for (int i=0; i<versions; i++) {
			if (order.equals(ArrayOrder.RANDOM)) {
				array = makeArray(order,size,percent);
			} else {
				array = makeArray(order,size);
			}

			filename = "data/"+fname+"_v"+i+"."+ext;
			try (BufferedWriter writer =
			new BufferedWriter(new FileWriter(filename))) {

				// Write information to the file
				writer.write(array.length+" "+order+" "+percent+"\n");

				for (int j=0; j<array.length; j++) {
					writer.write(array[j].alpha()+" "+array[j].number()+" ");
				}
				writer.close();
			} catch (IOException e) {
				e.printStackTrace();
				return;
			}
		}

	} // end main()


	// _______________________________________________________________
	//            PARSE COMMAND-LINE ARGUMENTS
	// _______________________________________________________________
	public static void parse_arguments(String[] args) {
		// Parse options from command line
		/*
		-o ordering (reverse, sorted, random. default is random)
		-p percent randomized (default 100%)
		-v number of files (versions). default 1
		-f file to write data to (default data_<date>.txt)
		-s size of array (default 100000)
		*/

		int orderOption = -1;

		// parse the command line options
		for (int i=0; i<args.length; i++) {
			switch(args[i]) {
				case("-o"):
					orderOption = i+1;
					break;
				case("-p"):
					percent = Integer.valueOf(args[i+1]);
					break;
				case("-f"):
					filename = args[i+1];
					break;
				case("-s"):
					size = Integer.valueOf(args[i+1]);
					break;
				// no default because it might be the value after the -x flag
			}
		}

		// determine the ordering of the values (if user used -o option)
		if (orderOption!=-1) {
			switch(args[orderOption].toLowerCase()) {
				case("reverse"):
				case("rev"):
				case("reversed"):
					order = ArrayOrder.REVERSED;
					break;
				case("sorted"):
				case("sort"):
					order = ArrayOrder.SORTED;
					break;
				case("random"):
				case("rand"):
					order = ArrayOrder.RANDOM;
					break;
				default:
					// they entered something but it is not recognized
					System.out.println("Do not recognize data type.");
					return;
			}
		}
	} // end parse_arguements

	// _______________________________________________________________
	//           MAKING OF ARRAYS - 3 WAYS
	// _______________________________________________________________
	/** Create an array with specified ordering and size
	* @param order One of the enumerated characteristics
	* @param size Of the array
	* @return new AlphaNumeric array of specified size and ordering
	*/
	public static AlphaNumeric[] makeArray(ArrayOrder order, int size, int percent) {

		// Call appropriate helper function based on ordering
		// it should not really be anything but RANDOM, anyway ...
		if (order == ArrayOrder.REVERSED) {
			return makeReversed(size);
		} else if (order == ArrayOrder.SORTED) {
			return makeSorted(size);
		} else if (order == ArrayOrder.RANDOM) {
			return makeRandom(size,percent);
		}

		// Should not get here, because only valid enums would be accepted, but just in case
		return null;

	} // end makeArray()

	/** Create an array with specified ordering and size
	* @param order One of the enumerated characteristics
	* @param size Of the array
	* @return new AlphaNumeric array of specified size and ordering
	*/
	public static AlphaNumeric[] makeArray(ArrayOrder order, int size) {

		// Call appropriate helper function based on ordering
		if (order == ArrayOrder.REVERSED) {
			return makeReversed(size);
		} else if (order == ArrayOrder.SORTED) {
			return makeSorted(size);
		} else if (order == ArrayOrder.RANDOM) {
			return makeRandom(size,100);
		}

		// Should not get here, because only valid enums would be accepted, but just in case
		return null;

	} // end makeArray()

	/** Reversed array with numbers in decreasing order and alphas in increasing order */
	private static AlphaNumeric[] makeReversed(int size) {

		AlphaNumeric[] array = new AlphaNumeric[size];

		// All alphas start as "aaa...". The numbers start at "size"
		String starter = "";
		for (int i=0; i<alength;i++) {
			starter += "a";
		}
		array[0] = new AlphaNumeric(starter,size);
		for (int i=1; i<size; i++) {
			// Use the helper function to determine next string (ex: aaaaaa, aaaaab, ...)
			// The value at [i] is the "next" after [i-1]
			array[i] = new AlphaNumeric(
			AlphaNumeric.nextAlpha(array[i-1].alpha(),false),
			array[i-1].number()-1);
		}
		return array;
	} // end makeReversed()

	/** Sorted array with numbers in increasing order and alphas in decreasing order */
	private static AlphaNumeric[] makeSorted(int size) {

		AlphaNumeric[] array = new AlphaNumeric[size];

		String starter = "";
		for (int i=0; i<alength;i++) {
			starter += "z";
		}
		// All Alphas start as "zzz...". The numbers start at 0
		array[0] = new AlphaNumeric(starter, 0);
		for (int i = 1; i < size; i++) {
			// Use the helper function to determine next string (ex: zzzzzz, zzzzzy, zzzzzx, ...)
			// The value at [i] is the "next" after [i-1]
			array[i] = new AlphaNumeric(
			AlphaNumeric.nextAlpha(array[i - 1].alpha(), true),
			array[i - 1].number() + 1);
		}
		return array;
	} // end makeSorted()

	/** Random array of unique elements with percent randomly rearranged */
	private static AlphaNumeric[] makeRandom(int size, int percent) {

		Random random = new Random();

		// Start with sorted array (by number), then randomize
		AlphaNumeric[] array = makeSorted(size);

		// randomly rearrange percent values
		for (int i=0; i<percent/2; i++) {
			// Choose a random locations to swap
			int index1 = random.nextInt(size);
			int index2 = random.nextInt(size);
			AlphaNumeric temp = array[index1];
			array[index1] = array[index2];
			array[index2] = temp;
		}
		return array;
	} // end makeRandom()
}
