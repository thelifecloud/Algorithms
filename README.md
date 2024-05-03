## Sorting Analysis Project

<hr>

1. Your first task is to transfer your implementations of Counting and Merge Sort into this framework. That means that you need to spend time trying to understand how the code works. 

2. Each Sorter contains a counter to track the number of comparisons and/or iterations. You should increment the counter in your implemented algorithms.

3. Implement Insertion Sort in this framework, including the counter.

Be sure to test your code using the `Test.java` class as described below.


<hr>

**In this comparative algorithm project**, you will be implementing sorting algorithms then comparing their runtime efficiency. Each algorithm will be used to sort a collection of arrays of increasing size. The arrays will be either in increasing order, decreasing order, or a random order to begin with. The efficiency of the algorithm will be assessed based on the actual time it takes to run the algorithm and counting the total number of iterations/comparisons executed. The final deliverable is a report that presents and discusses the results.

First, you will implement the algorithms _Insertion_, _Counting_, and _Merge_ Sort within this provided framework. Each sorting algorithm is a method in a class that implements **the interface `Sorter`**. This structure is in place to assist in the experimental phase of the project. A _Sorter_ uses generics and technically could be used to sort any type of object. However, for testing purposes, all algorithms will sort objects of type `AlphaNumeric`.

As part of your implementation of an algorithm, count the number of comparisons or iterations that are executed. There is a `count` variable in each _Sorter_ that should be reset to 0 at the start of the algorithm and incremented for each iteration. 


### Understanding the Code

`AlphaNumeric` is a very simple class that has 2 member variables -- one of type String (alpha) and one of type Integer (numeric). This is the primary class that will be used in the experiments. The code creates Java basic arrays of type `AlphaNumeric` in various configurations to be sorted by the different algorithms.

The `AlphaNumeric.orderAlpha` and `AlphaNumeric.orderNumeric` are of type `Comparator<AlphaNumeric>` which implements the `compare(T,T)` method. In _Insertion Sort_ and _Merge Sort_, this Comparator is assigned to the variable _orderBy_. It is used in this way to test if `array[i] > array[i+1]`:

```Java
if (orderBy.compare(array[i], array[i+1]) > 0) {
```

`Counting` and `Radix` sort are more complicated. Counting sort needs to extract the Integer component of the AlphaNumeric to build the histogram and order based on those Integers. In Radix, you have to extract the specific digit from the Integer component of the AlphaNumeric.

The `Sorter` interface has a `sort()`, as well as the `getCount` method. All sorting algorithms implement this interface.

`class Insertion`, `class Merge`, `class Counting`, `class Radix` contain the implementations of the sorting algorithm. The constructor for each of these classes has a parameter for the Comparator. After instantiating a Sorter, you can pass it an array to be sorted.

<hr>

### Testing Your Algorithms 

After implementing an algorithm, you want to test that it is working. You can call the `Test` method using command-line arguments to specify which algorithms to test. Here are some examples:

```
java Test insertion 
java Test counting merge 
```

<hr>

### Making Arrays

`ArrayMaker` can make arrays that are in random, ascending, or descending order relative to the provided comparator (i.e. `orderNumeric` or `orderAlpha`). Furthermore, for random arrays, you can specify how much of the array is randomized. Each random array starts as a sorted array and then elements are randomly selected and swapped. The number of swaps depends on the percent (%) specified for randomization (the default is 100%).

`arrayMaker` can be called directory, however for experiments, the intent is to use command-line arguments to build arrays that get stored in files and placed in the `data` folder. 

| flag | desc | options | default | example |
|------|------|---------|---------|---------|
| -o   | array ordering | reverse, sorted, random | reverse | java ArrayMaker -o random |
| -p   | percent randomized | 1-100 | 100 | java ArrayMaker -o random -p 50 |
| -f   | filename for saving array (data/ added to the front) | \<file\>.txt | data.txt | java ArrayMaker -f datafile.txt |
| -s   | size of array | integer | 100000 | java ArrayMaker -s 1000 |

Options can appear in any order: `java ArrayMaker -f data.txt -p 50 -o random`

The array that is created is stored in a file and placed in the /data directory. If random is selected for ordering, 3 versions are created so that you can take an average. Information about the data is stored in the first line of the file.

<hr>

### Running an Experiment 

The `Experiment` class allows you to record count and timing information for an array of increasing size using a specific algorithm. The idea is to create data so that you can graph the function that represents the runtime as n increased (towards infinity). There are command-line arguments that dictate the experiment. The first argument is always the algorithm being used for sorting.

| flag | desc | options | default |
|------|------|---------|---------|
| -o   | sorting order | alpha, number | number |
| -d   | datafile to sort | \<file\>.txt (relative path) | none | 
| -f   | filename for saving data (appends to file) | \<file\>.csv | data.csv | 
| -s   | maximum size array | any value up to number of elements in datafile | number of elements in the datafile |

Here are several examples of how to run an experiment:

```
java insert -o alpha -d data/random_rand100_v0.txt -f results.csv -s 1000
java merge -o numeric -d data/sorted.txt -f results.csv
java counting -d data/reversed.txt -f results.csv -s 10000000
```


<hr>

### Instantiation of a Sorter

**Instantiation and use of Insertion Sort** looks like this:

```Java
Insertion<AlphaNumeric> insertionAlpha = new Insertion<>(AlphaNumeric.orderAlpha);
insertionAlpha.sort(array);
```

or 

```Java
Insertion<AlphaNumeric> insertionNumeric = new Insertion<>(AlphaNumeric.orderNumeric);
insertionNumeric.sort(array);
```

This is already managed for you in both Test.java and Experiment.java. However, it will be useful for you to follow how this works so that you can debug your implementation of the algorithms.

> In Insertion Sort, increment the counter (i.e. `++count`) inside the outer loop and the inner loop. This is to count the comparison in the stopping condition of the inner for loop, as well as all comparisons in the inner loop. 
<hr>

**Instantiation and use of Merge Sort** looks like this:

```
Merge<AlphaNumeric> mergeAlpha = new Merge<>(AlphaNumeric.orderAlpha);
mergeAlpha.sort(array);
```

or 

```Java
Merge<AlphaNumeric> mergeNumeric = new Merge<>(AlphaNumeric.orderNumeric);
mergeNumeric.sort(array);
```

>In Merge sort, increment the count in the inner and outer loop of the nested for loop at the end of Merge. Also increment the count for the loops that are copying the arrays.



**Instantiation and use of the Counting Sorter** is:

```Java
Counting<AlphaNumeric> countingNumber = new Counting<>(AlphaNumeric.numberGetter);
countingNumber.sort(array);
```

The `numberGetter` is of type `Function<AlphaNumeric,Integer>`. It looks like this:

```
public static Function<AlphaNumeric, Integer> numberGetter = (object) -> object.number();
```

When it is applied to an AlphaNumeric object, it will call the getter `number()`. It looks like this ...

```
Integer valueOf = keyGetter.apply(element);
```

In the above example, if `element` is an AlphaNumeric object with the values "aab" and 734 and the `keyGetter` is `AlphaNumeric.numberGetter`, then once the keyGetter is applied to the element, _valueOf_ = 734 after this line is executed.

The _Radix_ algorithm will also use this `Function<>` to extract the Integer portion of an AlphaNumeric object.

>In Counting Sort, increment the counter in each of the for loops. 

> Notice that the call to sort the array only passes the array, however Counting Sort does not sort in-place. It places the sorted elements into another array. This is managed from inside the Sorter. 

**Instantiation and use of the Radix Sorter** is:

```Java
Radix<AlphaNumeric> radixNumber = new Radix<>(AlphaNumeric.numberGetter);
radixNumber.sort(array);
```

<hr>

### Resources

You may and should copy the algorithms from the textbook! Just remember to convert to 0-based indexing. You may look for examples of the Function class and the Comparator class to understand how they fit into this project. You may look at any documentation of Java to help with syntax.

Please do not be shy about asking me questions. This is a lot of code and I am happy to walk you through any parts that you do not understand. I can typically debug code pretty fast - tooting my own horn :-) -- but really, I'm fast, just ask when you are stuck!


This is an independent project. You are to implement your own code. You may not copy code off the web. You may not email your code to a classmate. You may not let other's see your code so that they can copy it.


