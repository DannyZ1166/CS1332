import java.util.ArrayList;
import java.util.NoSuchElementException;

/**
 * Your implementation of a MinHeap.
 *
 * @author Shuohang Zeng
 * @version 1.0
 * @userid szeng70
 * @GTID 903798569
 * <p>
 * Collaborators: LIST ALL COLLABORATORS YOU WORKED WITH HERE
 * <p>
 * Resources: LIST ALL NON-COURSE RESOURCES YOU CONSULTED HERE
 */
public class MinHeap<T extends Comparable<? super T>> {

    /**
     * The initial capacity of the MinHeap when created with the default
     * constructor.
     * <p>
     * DO NOT MODIFY THIS VARIABLE!
     */
    public static final int INITIAL_CAPACITY = 13;

    // Do not add new instance variables or modify existing ones.
    private T[] backingArray;
    private int size;

    /**
     * Constructs a new MinHeap.
     * <p>
     * The backing array should have an initial capacity of INITIAL_CAPACITY.
     * To initialize the backing array, create a Comparable array and then cast
     * it to a T array.
     */
    public MinHeap() {
        backingArray = (T[]) new Comparable[INITIAL_CAPACITY];
        size = 0;
    }

    /**
     * Creates a properly ordered heap from a set of initial values.
     * <p>
     * You must use the BuildHeap algorithm that was taught in lecture! Simply
     * adding the data one by one using the add method will not get any credit.
     * As a reminder, this is the algorithm that involves building the heap
     * from the bottom up by repeated use of downHeap operations.
     * <p>
     * Before doing the algorithm, first copy over the data from the
     * ArrayList to the backingArray (leaving index 0 of the backingArray
     * empty). The data in the backingArray should be in the same order as it
     * appears in the passed in ArrayList before you start the BuildHeap
     * algorithm.
     * <p>
     * The backingArray should have capacity 2n + 1 where n is the
     * size of the passed in ArrayList (not INITIAL_CAPACITY). Index 0 should
     * remain empty, indices 1 to n should contain the data in proper order, and
     * the rest of the indices should be empty.
     *
     * @param data a list of data to initialize the heap with
     * @throws java.lang.IllegalArgumentException if data or any element in data
     *                                            is null
     */
    public MinHeap(ArrayList<T> data) {
        if (data == null) {
            throw new IllegalArgumentException("Data can't be null");
        }
        backingArray = (T[]) new Comparable[data.size() * 2 + 1];
        size = data.size();
        for (int i = 0; i < size; i++) {
            if (data.get(i) == null) {
                throw new IllegalArgumentException("Data can't be null");
            }
            backingArray[i + 1] = data.get(i);
        }
        for (int i = size / 2; i >= 1; i--) {
            minHeapHelper(i);
        }
    }

    /**
     * Turns tree to min heap
     *
     * @param i the index of a value
     */
    private void minHeapHelper(int i) {
        while (2 * i <= size) {
            int j = 2 * i;
            if (j < size && backingArray[j].compareTo(backingArray[j + 1]) > 0) {
                j++;
            }
            if (!(backingArray[i].compareTo(backingArray[j]) > 0)) {
                break;
            }
            T tmp = backingArray[i];
            backingArray[i] = backingArray[j];
            backingArray[j] = tmp;
            i = j;
        }
    }

    /**
     * Adds an item to the heap. If the backing array is full (except for
     * index 0) and you're trying to add a new item, then double its capacity.
     * The order property of the heap must be maintained after adding. You can
     * assume that no duplicate data will be passed in.
     *
     * @param data the data to add
     * @throws java.lang.IllegalArgumentException if data is null
     */
    public void add(T data) {
        if (data == null) {
            throw new IllegalArgumentException("Data can't be null");
        }
        if (size == backingArray.length - 1) {
            T[] temp = (T[]) new Comparable[backingArray.length * 2];
            for (int i = 1; i < size + 1; i++) {
                temp[i] = backingArray[i];
            }
            backingArray = temp;
        }
        size++;
        backingArray[size] = data;

        percolateUp(size);
    }

    /**
     * helper method to percolate up
     *
     * @param i the index of a value
     */
    private void percolateUp(int i) {
        int j;
        if (i % 2 == 0) {
            j = i / 2;
        } else if (i == 1) {
            j = 1;
        } else {
            j = (i - 1) / 2;
        }
        if ((backingArray[i].compareTo(backingArray[j]) < 0) && i != 1) {
            T temp = backingArray[j];
            backingArray[j] = backingArray[i];
            backingArray[i] = temp;
            percolateUp(j);
        }
    }

    /**
     * Removes and returns the min item of the heap. As usual for array-backed
     * structures, be sure to null out spots as you remove. Do not decrease the
     * capacity of the backing array.
     * The order property of the heap must be maintained after removing.
     *
     * @return the data that was removed
     * @throws NoSuchElementException if the heap is empty
     */
    public T remove() {
        if (size == 0) {
            throw new NoSuchElementException("Heap is empty");
        } else {
            T tempRecord = backingArray[1];
            backingArray[1] = backingArray[size];
            backingArray[size] = null;
            size--;
            percolateDown(1);
            return tempRecord; //  return value at index 0
        }
    }

    /**
     * helper method to percolate down
     *
     * @param i the index of a value
     */
    private void percolateDown(int i) {
        if (i * 2 > size) {
            return;
        } else {
            if (i * 2 < size) { //if there are two child of this index
                if ((backingArray[i].compareTo(backingArray[2 * i]) > 0)
                        && (backingArray[i].compareTo(backingArray[(2 * i) + 1]) > 0)) {
                    //if both child are smaller than the parent
                    if (backingArray[2 * i].compareTo(backingArray[(2 * i) + 1]) < 0) {
                        T tempRecord = backingArray[2 * i];
                        backingArray[2 * i] = backingArray[i];
                        backingArray[i] = tempRecord;
                        percolateDown(2 * i); //recursively calls method again for child index
                    } else { //compare the two child and swaps the parent with the smaller child
                        T tempRecord = backingArray[(2 * i) + 1];
                        backingArray[(2 * i) + 1] = backingArray[i];
                        backingArray[i] = tempRecord;
                        percolateDown((2 * i) + 1); //recursively calls method again for child index
                    }
                } else if (backingArray[i].compareTo(backingArray[2 * i]) > 0) {
                    // if only the left child is smaller, swap the left with the parent
                    T tempRecord = backingArray[2 * i];
                    backingArray[2 * i] = backingArray[i];
                    backingArray[i] = tempRecord;
                    percolateDown(2 * i); //recursively calls method again for child index
                } else if (backingArray[i].compareTo(backingArray[(2 * i) + 1]) > 0) {
                    // if only the right child is smaller, swap the right with the parent
                    T tempRecord = backingArray[(2 * i) + 1];
                    backingArray[(2 * i) + 1] = backingArray[i];
                    backingArray[i] = tempRecord;
                    percolateDown((2 * i) + 1); //recursively calls method again for child index
                }
            } else if (i * 2 == size) { //if there is only one child test if the child is smaller
                if (backingArray[i].compareTo(backingArray[2 * i]) > 0) { //swap if it is smaller
                    T tempRecord = backingArray[2 * i];
                    backingArray[2 * i] = backingArray[i];
                    backingArray[i] = tempRecord;
                    percolateDown(2 * i); //recursively calls method again for child index
                }
            }
        }
    }

    /**
     * Returns the minimum element in the heap.
     *
     * @return the minimum element
     * @throws java.util.NoSuchElementException if the heap is empty
     */
    public T getMin() {
        if (size == 0) {
            throw new NoSuchElementException("Heap is empty");
        } else {
            return backingArray[1];
        }
    }

    /**
     * Returns whether or not the heap is empty.
     *
     * @return true if empty, false otherwise
     */
    public boolean isEmpty() {
        return size == 0;
    }

    /**
     * Clears the heap.
     * <p>
     * Resets the backing array to a new array of the initial capacity and
     * resets the size.
     */
    public void clear() {
        backingArray = (T[]) new Comparable[INITIAL_CAPACITY];
        size = 0;
    }

    /**
     * Returns the backing array of the heap.
     * <p>
     * For grading purposes only. You shouldn't need to use this method since
     * you have direct access to the variable.
     *
     * @return the backing array of the list
     */
    public T[] getBackingArray() {
        // DO NOT MODIFY THIS METHOD!
        return backingArray;
    }

    /**
     * Returns the size of the heap.
     * <p>
     * For grading purposes only. You shouldn't need to use this method since
     * you have direct access to the variable.
     *
     * @return the size of the list
     */
    public int size() {
        // DO NOT MODIFY THIS METHOD!
        return size;
    }
}
