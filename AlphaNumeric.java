import java.util.Comparator;
import java.util.function.Function;
import java.util.Random;

/** Basic class for testing sorting. */
public class AlphaNumeric  {

    /** The alpha part */
    private String alpha = null;

    /** The numeric part */
    private Integer number = null;

    /** Random number generator. Seeded for repeatable. */
    //private static Random random = new Random(10);

    // but maybe I do not want to seed it!!
    private static Random random = new Random();

    /** Length of alpha */
    private static int alength = 5;

    /** Default constructor creating random number and alpha */
    public AlphaNumeric() {

        alpha = "";
        for (int i=0; i<alength; i++) {
            alpha = alpha + (char)(random.nextInt(26)+'a');
        }
        // all numbers in range 0 to 1,000,000 (exclusive)
        number = random.nextInt(1000000);
    }

    /** Constructor with specified values of alpha and numeric */
    public AlphaNumeric(String a, Integer n) {
        alpha = a;
        number = n;
    }

    @Override
    public String toString() {
        return "["+alpha()+", "+number()+"] ";
    }

    // GETTERS
    public String alpha() {
        return alpha;
    }

    public Integer number() {
        return number;
    }

    /** Helper function to create an ordering of strings
    * @param a Next string based on this one
    * @param reverse True creates next in reverse, else next "increasing"
    * @return Next string in order ("aaaaaa, aaaaab" OR "zzzzzz","zzzzzy")
    */
    public static String nextAlpha(String a, boolean reverse) {

        // Reverse the string and put into an array to "add 1" from least to most significant char
        // "abcdef" will become {'f','e','d','c','b','a'}
        int length = a.length();
        int[] chars = new int[length];
        for (int i = 0; i < length; i++) {
            chars[i] = a.charAt(length - 1 - i);
        }
        // Need to add 1 to the least significant, and carry if necessary (meaning adding 1 to 'z')
        if (!reverse) {
            for (int i = 0; i < length; i++) {
                if ((char) (chars[i] + 1) > 'z') {
                    chars[i] = 'a';
                } else {
                    chars[i] = chars[i] + 1;
                    break;
                }
            }
        } else {
            // Need to subtract 1 to the least significant, and borrow if necessary (meaning subtract 1 from 'a')
            for (int i = 0; i < length; i++) {
                if ((char)(chars[i]-1) < 'a') {
                    chars[i] = 'z';
                } else {
                    chars[i] = chars[i] - 1;
                    break;
                }
            }
        }
        // Put the string back together
        String next = "";
        for (int i = 0; i < length; i++) {
            next = (char)chars[i] + next;
        }
        return next;
    }

    /** Comparator used to order AlphaNumerics based on the Alpha field */
    public static Comparator<AlphaNumeric> orderAlpha = new Comparator<AlphaNumeric>() {
        // This will come in handy later when recording results of experiments
        @Override
        public String toString() {
            return "Key Alpha";
        }
        public int compare(AlphaNumeric an1, AlphaNumeric an2) {
            return an1.alpha().compareTo(an2.alpha());
        }
    };

    /** Comparator used to order AlphaNumerics based on the Number field */
    public static Comparator<AlphaNumeric> orderNumeric = new Comparator<AlphaNumeric>() {
        // This will come in handy later when recording results of experiments
        @Override
        public String toString() {
            return "Key Number";
        }
        public int compare(AlphaNumeric an1, AlphaNumeric an2) {
            return an1.number().compareTo(an2.number());
        }
    };

    /** Getter for the field being sorted. Alpha not yet used. */
    public static Function<AlphaNumeric, String> alphaGetter = (object) -> object.alpha();

    /** Getter for the field being sorted. Number getter needed in radix and counting sort. */
    public static Function<AlphaNumeric, Integer> numberGetter = (object) -> object.number();
}
