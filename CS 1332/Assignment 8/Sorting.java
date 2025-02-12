import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;
import java.util.PriorityQueue;

/**
 * Your implementation of various sorting algorithms.
 *
 * @author Shuohang Zeng
 * @version 1.0
 * @userid szeng70
 * @GTID 903798569
 *
 * Collaborators: LIST ALL COLLABORATORS YOU WORKED WITH HERE
 *
 * Resources: LIST ALL NON-COURSE RESOURCES YOU CONSULTED HERE
 */
public class Sorting {

    /**
     * Implement insertion sort.
     *
     * It should be:
     * in-place
     * stable
     * adaptive
     *
     * Have a worst case running time of:
     * O(n^2)
     *
     * And a best case running time of:
     * O(n)
     *
     * @param <T>        data type to sort
     * @param arr        the array that must be sorted after the method runs
     * @param comparator the Comparator used to compare the data in arr
     * @throws java.lang.IllegalArgumentException if the array or comparator is
     *                                            null
     */
    public static <T> void insertionSort(T[] arr, Comparator<T> comparator) {
        if (arr == null || comparator == null) {
            throw new IllegalArgumentException("Array or comparator is null");
        }
        for (int i = 1; i < arr.length; i++) {
            T tmp = arr[i];
            int j = i - 1;
            while (j >= 0 && comparator.compare(arr[j], tmp) > 0) {
                arr[j + 1] = arr[j--];
            }
            arr[j + 1] = tmp;
        }
    }

    /**
     * Implement cocktail sort.
     *
     * It should be:
     * in-place
     * stable
     * adaptive
     *
     * Have a worst case running time of:
     * O(n^2)
     *
     * And a best case running time of:
     * O(n)
     *
     * NOTE: See pdf for last swapped optimization for cocktail sort. You
     * MUST implement cocktail sort with this optimization
     *
     * @param <T>        data type to sort
     * @param arr        the array that must be sorted after the method runs
     * @param comparator the Comparator used to compare the data in arr
     * @throws java.lang.IllegalArgumentException if the array or comparator is
     *                                            null
     */
    public static <T> void cocktailSort(T[] arr, Comparator<T> comparator) {
        if (arr == null || comparator == null) {
            throw new IllegalArgumentException("Array or comparator is null");
        }
        boolean finished = false;
        int start = 0;
        int finish = arr.length - 1;
        int tmpStart = arr.length - 1;
        int tmpFinish = 0;
        while (!finished) {
            for (int i = start; i < finish; i++) {
                if (comparator.compare(arr[i], arr[i + 1]) > 0) {
                    T tmp = arr[i];
                    arr[i] = arr[i + 1];
                    arr[i + 1] = tmp;
                    tmpFinish = i;
                }
            }
            if (tmpFinish == finish) {
                finished = true;
            } else {
                finish = tmpFinish;
                for (int i = finish; i > start; i--) {
                    if (comparator.compare(arr[i - 1], arr[i]) > 0) {
                        T tmp = arr[i];
                        arr[i] = arr[i - 1];
                        arr[i - 1] = tmp;
                        tmpStart = i;
                    }
                }
            }
            if (tmpStart == start) {
                finished = true;
            }
            start = tmpStart;
            if ((start == arr.length - 1 && finish == 0)) {
                finished = true;
            }
        }
    }

    /**
     * Implement merge sort.
     *
     * It should be:
     * out-of-place
     * stable
     * not adaptive
     *
     * Have a worst case running time of:
     * O(n log n)
     *
     * And a best case running time of:
     * O(n log n)
     *
     * You can create more arrays to run merge sort, but at the end, everything
     * should be merged back into the original T[] which was passed in.
     *
     * When splitting the array, if there is an odd number of elements, put the
     * extra data on the right side.
     *
     * Hint: If two data are equal when merging, think about which subarray
     * you should pull from first
     *
     * @param <T>        data type to sort
     * @param arr        the array to be sorted
     * @param comparator the Comparator used to compare the data in arr
     * @throws java.lang.IllegalArgumentException if the array or comparator is
     *                                            null
     */
    public static <T> void mergeSort(T[] arr, Comparator<T> comparator) {
        if (arr == null || comparator == null) {
            throw new IllegalArgumentException("Array or comparator is null");
        }
        if (arr.length > 1) {
            T[] left = (T[]) new Object[arr.length / 2];
            T[] right = (T[]) new Object[arr.length - left.length];
            for (int i = 0; i < left.length; i++) {
                left[i] = arr[i];
            }
            for (int i = 0; i < right.length; i++) {
                right[i] = arr[i + left.length];
            }
            mergeSort(left, comparator);
            mergeSort(right, comparator);
            mergeSortHelper(arr, comparator, left, right);
        }
    }

    /**
     * helper method for mergeSort
     *
     * @param <T>           data type to sort
     * @param arr           the array to be sorted
     * @param comparator    comparator used to compare values
     * @param leftArr       left array
     * @param rightArr      right Array
     */
    private static <T> void mergeSortHelper(T[] arr, Comparator<T> comparator, T[] leftArr, T[] rightArr) {
        int left = 0;
        int right = 0;
        for (int i = 0; i < arr.length; i++) {
            if (right >= rightArr.length || (left < leftArr.length
                    && comparator.compare(leftArr[left], rightArr[right]) <= 0)) {
                arr[i] = leftArr[left];
                left++;
            } else {
                arr[i] = rightArr[right];
                right++;
            }
        }
    }

    /**
     * Implement quick sort.
     *
     * Use the provided random object to select your pivots. For example if you
     * need a pivot between a (inclusive) and b (exclusive) where b > a, use
     * the following code:
     *
     * int pivotIndex = rand.nextInt(b - a) + a;
     *
     * If your recursion uses an inclusive b instead of an exclusive one,
     * the formula changes by adding 1 to the nextInt() call:
     *
     * int pivotIndex = rand.nextInt(b - a + 1) + a;
     *
     * It should be:
     * in-place
     * unstable
     * not adaptive
     *
     * Have a worst case running time of:
     * O(n^2)
     *
     * And a best case running time of:
     * O(n log n)
     *
     * Make sure you code the algorithm as you have been taught it in class.
     * There are several versions of this algorithm and you may not receive
     * credit if you do not use the one we have taught you!
     *
     * @param <T>        data type to sort
     * @param arr        the array that must be sorted after the method runs
     * @param comparator the Comparator used to compare the data in arr
     * @param rand       the Random object used to select pivots
     * @throws java.lang.IllegalArgumentException if the array or comparator or
     *                                            rand is null
     */
    public static <T> void quickSort(T[] arr, Comparator<T> comparator,
                                     Random rand) {
        if (arr == null || comparator == null || rand == null) {
            throw new IllegalArgumentException("Array or comparator or random generator is null");
        }
        quickSortHelper(arr, 0, arr.length - 1, rand, comparator);
    }

    /**
     * Helper method for quickSort
     *
     * @param <T>           data type to sort
     * @param arr           the array to be sorted
     * @param first         starting index
     * @param last          ending index
     * @param rand          the Random object used to select pivots
     * @param comparator    the Comparator used to compare the data in arr
     */
    private static <T> void quickSortHelper(T[] arr, int first, int last,
                                     Random rand, Comparator<T> comparator) {
        if (last > first) {
            int partitionIndex = partition(arr, first, last,
                    rand.nextInt(last - first) + first, comparator);
            quickSortHelper(arr, first, partitionIndex - 1, rand, comparator);
            quickSortHelper(arr, partitionIndex + 1, last, rand, comparator);
        }
    }

    /**
     * Partitions array for quick sort
     *
     * @param <T>               data type to sort
     * @param arr               the array to be sorted
     * @param first             starting index
     * @param last              ending index
     * @param partitionIndex    pivot index
     * @param comparator        the Comparator used to compare the data in arr
     * @return new pivot index
     */
    private static <T> int partition(T[] arr, int first, int last, int partitionIndex, Comparator<T> comparator) {
        T pivot = arr[partitionIndex];
        T tmp = arr[partitionIndex];
        arr[partitionIndex] = arr[last];
        arr[last] = tmp;
        int index = first;
        for (int i = first; i < last; i++) {
            if (comparator.compare(arr[i], pivot) <= 0) {
                tmp = arr[i];
                arr[i] = arr[index];
                arr[index] = tmp;
                index++;
            }
        }
        tmp = arr[last];
        arr[last] = arr[index];
        arr[index] = tmp;
        return index;
    }


    /**
     * Implement LSD (least significant digit) radix sort.
     *
     * Make sure you code the algorithm as you have been taught it in class.
     * There are several versions of this algorithm and you may not get full
     * credit if you do not implement the one we have taught you!
     *
     * Remember you CANNOT convert the ints to strings at any point in your
     * code! Doing so may result in a 0 for the implementation.
     *
     * It should be:
     * out-of-place
     * stable
     * not adaptive
     *
     * Have a worst case running time of:
     * O(kn)
     *
     * And a best case running time of:
     * O(kn)
     *
     * You are allowed to make an initial O(n) passthrough of the array to
     * determine the number of iterations you need. The number of iterations
     * can be determined using the number with the largest magnitude.
     *
     * At no point should you find yourself needing a way to exponentiate a
     * number; any such method would be non-O(1). Think about how how you can
     * get each power of BASE naturally and efficiently as the algorithm
     * progresses through each digit.
     *
     * Refer to the PDF for more information on LSD Radix Sort.
     *
     * You may use ArrayList or LinkedList if you wish, but it may only be
     * used inside radix sort and any radix sort helpers. Do NOT use these
     * classes with other sorts. However, be sure the List implementation you
     * choose allows for stability while being as efficient as possible.
     *
     * Do NOT use anything from the Math class except Math.abs().
     *
     * @param arr the array to be sorted
     * @throws java.lang.IllegalArgumentException if the array is null
     */
    public static void lsdRadixSort(int[] arr) {
        if (arr == null) {
            throw new IllegalArgumentException("Array is null");
        }
        LinkedList<Integer>[] buckets = new LinkedList[19];
        for (int i = 0; i < 19; i++) {
            buckets[i] = new LinkedList<>();
        }
        int mod = 10;
        int div = 1;
        boolean cont = true;
        while (cont) {
            cont = false;
            for (int num : arr) {
                int bucket = num / div;
                if (bucket / 10 != 0) {
                    cont = true;
                }
                if (buckets[bucket % mod + 9] == null) {
                    buckets[bucket % mod + 9] = new LinkedList<>();
                }
                buckets[bucket % mod + 9].add(num);
            }
            int arrayIndex = 0;
            for (LinkedList<Integer> bucket : buckets) {
                if (bucket != null) {
                    for (int num : bucket) {
                        arr[arrayIndex] = num;
                        arrayIndex++;
                    }
                    bucket.clear();
                }
            }
            div *= 10;
        }
    }

    /**
     * Implement heap sort.
     *
     * It should be:
     * out-of-place
     * unstable
     * not adaptive
     *
     * Have a worst case running time of:
     * O(n log n)
     *
     * And a best case running time of:
     * O(n log n)
     *
     * Use java.util.PriorityQueue as the heap. Note that in this
     * PriorityQueue implementation, elements are removed from smallest
     * element to largest element.
     *
     * Initialize the PriorityQueue using its build heap constructor (look at
     * the different constructors of java.util.PriorityQueue).
     *
     * Return an int array with a capacity equal to the size of the list. The
     * returned array should have the elements in the list in sorted order.
     *
     * @param data the data to sort
     * @return the array with length equal to the size of the input list that
     * holds the elements from the list is sorted order
     * @throws java.lang.IllegalArgumentException if the data is null
     */
    public static int[] heapSort(List<Integer> data) {
        if (data == null) {
            throw new IllegalArgumentException("data is null");
        }
        PriorityQueue<Integer> heap = new PriorityQueue<>();
        for (int i : data) {
            heap.add(i);
        }
        int[] intArray = new int[data.size()];
        int i = 0;
        while (!heap.isEmpty()) {
            intArray[i] = heap.poll();
            i++;
        }
        return intArray;
    }
}
