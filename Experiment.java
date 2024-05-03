import java.io.*;
import java.util.*;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;

public class Experiment {

	// _______________________________________________________________
	//            INITIALIZE VARIABLES USED IN EXPERIMENT
	// _______________________________________________________________

	/** Number of data points to collect.
	* Each data point is an increase of "n" up to maximum size.
	*/
	public static int datapoints = 25;

	/** Array to be sorted. It will be filled from data file.*/
	private static AlphaNumeric[] array;

	/** File that contains the AlphaNumeric data to sort*/
	private static String datafile = null;

	/** File for storing the experiment results. */
	private static String filename = "data.csv";

	/** The maximum size of the array to sort.
	* Assuming that the data file holds at least that many elements.
	*/
	private static int size = -1;

	/** Establishes ordering for sorting */
	private static Comparator<AlphaNumeric> orderBy = AlphaNumeric.orderNumeric;

	/** Algorithm to use for sorting the datafile */
	private static Sorter<AlphaNumeric> algo;

	/** String algorithm which will be written into the results file.*/
	private static String algorithmString = "";

	/** Ordering of the data file -- random, sorted, reversed.
	* Information obtained from data file then written to results file.
	*/
	private static String ordering = "number";

	/** Relevant when random data file. Data is first in sorted order,
	* then "percent" randomized. Default is 100% randomization.
	*/
	private static String percent = "100";

	// _______________________________________________________________
	//            MAIN
	// _______________________________________________________________
	public static void main(String[] args) {

		// User has to specify some information, otherwise bail.
		if (args.length<=1) {
			System.out.println("Need to specify conditions of experiment");
			return;
		}
		// Example of user-specified information
		// java Experiment algo -d datafile -o ordering -f output.csv

		// determine what the user entered (sets all the static vars)
		parse_arguments(args);

		// If user did not provide a data file, nothing to do here. Bail.
		if (datafile==null) {
			System.out.println("Data file not defined.");
			return;
		}

		// Determine which algorithm will be used
		switch (args[0].toLowerCase()) {
			case "insert":
			case "insertion":
				algorithmString = "insertion";
				algo = new Insertion<AlphaNumeric>(orderBy);
				break;
			case "counting":
			case "count":
				algorithmString = "counting";
				algo = new Counting<AlphaNumeric>(AlphaNumeric.numberGetter);
				break;
			case "merge":
				algorithmString = "merge";
				algo = new Merge<AlphaNumeric>(orderBy);
				break;
			case "radix":
				algorithmString = "radix";
				algo = new Radix<AlphaNumeric>(AlphaNumeric.numberGetter);
				break;
			case "tim":
				algorithmString = "tim";
				algo = new Tim<AlphaNumeric>(orderBy);
				break;			
			//case "quick": // not yet implemented
				//title = "Sorting with RANDOMIZED QUICK"
				//algo = new Quick<AlphaNumeric>(orderBy);
				//break;
			default:
				System.out.println("Algorithm "+args[0]+" not valid.");
				return;
		} // end switch

		// Make the primary array from the data file
		createArrayFromFile();

		// Let us get some data ...
		run_experiment();

	} // end main()
	
	// _______________________________________________________________
	//            CREATE ARRAY FROM FILE
	// _______________________________________________________________
	public static void createArrayFromFile() {

		// read datafile and prepare the array for sorting.
		try (Scanner scanner = new Scanner(new File(datafile))) {

			// read the first line to get the size for the array to sort.
			String[] words = scanner.nextLine().split(" ");
			if (size==-1) {
				size = Integer.valueOf(words[0]);
				ordering = words[1];
				percent = words[2];
			}

			// Create an array and read in the data
			array = new AlphaNumeric[size];

			for (int i = 0; i<size; i++) {
				AlphaNumeric an = new AlphaNumeric(scanner.next(),Integer.valueOf(scanner.next()));
				array[i] = an;
			}
			scanner.close();
		} catch (IOException e) {
			e.printStackTrace();
			return;
		}
	} // end createArrayFromFile()

	// _______________________________________________________________
	//            RUN EXPERIMENT
	// _______________________________________________________________

	public static void run_experiment() {
		// Incrementally increase array size, sorting each time.
		// determine increments
		int increment = size/datapoints;
		long[] counts = new long[datapoints];

		AlphaNumeric[] subarray;
		for (int i=0; i<datapoints; i++) {
			// Create a new array of appropriate size
			subarray = new AlphaNumeric[(i+1)*increment];
			for (int j=0; j<((i+1)*increment); j++) {
				subarray[j] = array[j];
			}
			algo.sort(subarray);
			counts[i] = algo.getCount();
		}

		// Create a datestamp to mark the data
		Timestamp ts = new Timestamp((new Date()).getTime());
		String datestamp = (new SimpleDateFormat("MM-dd HH:mm")).format(ts);

		// Append the results to the results file
		try (BufferedWriter writer =
		new BufferedWriter(new FileWriter(filename,true))) {

			for (int i=0; i<counts.length; i++) {
				writer.write(datestamp+",");
				writer.write(((i+1)*increment)+",");
				writer.write(counts[i]+",");
				writer.write(algorithmString+",");
				writer.write(datafile+",");
				writer.write(ordering+",");
				writer.write(percent+"\n");
			}
			writer.close();
		} catch (IOException e) {
			e.printStackTrace();
			return;
		}
	} // end run_experiment

	// _______________________________________________________________
	//            PARSE COMMAND-LINE ARGUMENTS
	// _______________________________________________________________
	public static void parse_arguments(String[] args) {
		// Parse options from command line
		/*
		-o ordering (alpha or numeric)
		-d datafile to read from
		-f file to write data to (default results_<date>.csv)
		-s size of array (default number of elements in the array)
		*/

		// parse the command line options
		ordering = "numeric";
		for (int i=0; i<args.length; i++) {
			switch(args[i]) {
				case("-o"):
					ordering = args[i+1];
					break;
				case("-d"):
					datafile = args[i+1];
					break;
				case("-f"):
					filename = args[i+1];
					break;
				case("-s"):
					size = Integer.valueOf(args[i+1]);
					break;
				// no default because it might be the value after the -x flag
			}
		} // end for

		// determine the ordering of the values (if user used -o option)
		switch(ordering.toLowerCase()) {
			case("alpha"):
				orderBy = AlphaNumeric.orderAlpha;
				break;
			case("numeric"):
			case("number"):
			case("num"):
				orderBy = AlphaNumeric.orderNumeric;
				break;
			default:
				// they entered something but it is not recognized
				System.out.println("Do not recognize ordering type.");
			return;
		}
	} // end parse_arguements
}