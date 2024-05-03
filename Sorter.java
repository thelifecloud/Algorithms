import java.util.*;

public interface Sorter<T> {

    /** Sorts the given array. It will be sorted upon return. */
    public void sort(T[] array);

    /** Setter for the basis of ordering. */
    public void setComparator(Comparator<T> orderBy);

    /** The number of iterations or comparisons made during sorting */
    public long getCount();

}
