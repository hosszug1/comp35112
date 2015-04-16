import java.util.Arrays.*;
import java.lang.Thread;

public class MergeSort implements Runnable
{	
	// Variable for resulting sorted array.
	// Public because used many times in the algorithm, making
	// access easier.
	public int[] result;
	// The array, start index, end index and total number of threads.
	private int[] array;
	private int start;
	private int end;
	private int numberOfThreads;
	// Keep track of threads.
	private static int threadCounter = 0;
	private final int threadID;

	// Main Constructor. Initialises thread ID and takes the variables
	// needed for sorting as parameter (so run() method can access them).
	public MergeSort(int[] array, int start, int end, int numberOfThreads)
	{
		// Initialise ID.
		this.threadID = threadCounter;
		threadCounter++;
		// System.out.println("Thread created with ID " + this.threadID);

		// Setup variables to be used in sorting.
		this.array = array;
		this.start = start;
		this.end = end;
		this.numberOfThreads = numberOfThreads;
	} // MergeSort

	// Secondary Constructor. Initialises thread ID but doesn't take extra parameters.
	public MergeSort()
	{
		// Initialise ID.
		this.threadID = threadCounter;
		threadCounter++;
		// System.out.println("Thread created with ID " + this.threadID);
	} // MergeSort

	// Calls the sorting function using the variables kept in this class.
	public void run()
	{
		// System.out.println("run started in thread " + this.threadID);
		this.inPlaceSort(this.array, this.start, this.end, this.numberOfThreads);
	} // run

	// 
	public void inPlaceSort(int[] inArray, int start, int end, int threadNo)
	{
		// Final resulting sorted array (or piece of array).
		// int[] result;

		// System.out.println("inPlaceSort started in thread " + this.threadID);

		// Check how many threads are left to be used.
		if (threadNo > 1)
		{
			// First half. Spawn a new thread.
			// System.out.println("Spawning a new thread here...");
            MergeSort newSort = new MergeSort(inArray, start, (start + end) / 2, threadNo / 2);
            Thread newThread = new Thread(newSort);
            // Start the thread, when run() is called, it will take the
            // parameters for sorting from the class created just above.
            newThread.start();

			// System.out.println("Continuing activity in thread " + this.threadID);

			// Second half. Sort in the same thread.
			this.inPlaceSort(inArray, (start + end) / 2 + 1, end, threadNo / 2);

			// Wait for first half to finish.
            try
            {
                newThread.join();
            } // try
            catch (InterruptedException e)
            {
                e.printStackTrace();
            } // catch

	        // Concatenate results.
            this.result = this.merge(newSort.result, this.result);
		} // if
		else
		{
			// Use java built-in function for sorting the remaining elements.
			this.result = new int[end - start + 1];
			System.arraycopy(inArray, start, this.result, 0, (end - start + 1));;
			java.util.Arrays.sort(this.result);
			// java.util.Arrays.sort(inArray, start, (end + 1));
		} // else

		// return result;

	} // inPlaceSort

	// A function that merges two sorted arrays into one (also sorted).
	public int[] merge(int[] left, int[] right)
	{
		// The resulting array.
		int[] mergedArray = new int[left.length + right.length];

		// Initialise indexes for the left, right and resulting arrays.
		int i = 0, j = 0, k = 0;

		// Loop through the elements of the two arrays and compare two at a time
		// (one from each array) to see which one is less than or equal and put
		// that in the resulting array (this is possible because we know that
		// the two arrays are already sorted in ascending order).
		while (i < left.length && j < right.length)
		{
			if (left[i] <= right[j])
			{
				// Place element in result array.
				mergedArray[k] = left[i];
				// Increment the index of this array (left) so that next time
				// the next element gets compared to the one in the other
				// array.
				i++;
			} // if
			else
			{
				// Place element in result array.
				mergedArray[k] = right[j];
				// Increment the index of this array (right) so that next time
				// the next element gets compared to the one in the other
				// array.
				j++;
			} // else

			// Increment the index for the resulting array.
			k++;
		} // while

		// Either arrays may have elements left in them.
		while (i < left.length)
		{
			mergedArray[k] = left[i];
			i++;
			k++;
		} // while

		while (j < right.length)
		{
			mergedArray[k] = right[j];
			j++;
			k++;
		} // while

		return mergedArray;
	} // merge

} // class MergeSort