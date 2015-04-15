import java.util.Arrays.*;
import java.lang.Thread;

public class MergeSort implements Runnable
{	
	// Constructor. Does nothing.
	public MergeSort()
	{
	} // MergeSort

	// Does nothing.
	public void run()
	{
	} // run

	// 
	public int[] inPlaceSort(int[] inArray, int start, int end, int threadNo)
	{
		// Final resulting sorted array (or piece of array).
		int[] result;

		// Check how many threads are left to be used.
		if (threadNo > 1)
		{
			// First half. Spawn a new thread.
            MergeSort newSort = new MergeSort();
            Thread newThread = new Thread(newSort);
            newThread.start(); 
			int[] firstHalf = newSort.inPlaceSort(inArray, start, (start + end) / 2, threadNo / 2);

			// Second half. Sort in the same thread
			int[] secondHalf = this.inPlaceSort(inArray, (start + end) / 2 + 1, end, threadNo / 2);

			// Join threads.
            try
            {
                newThread.join();
            } // try
            catch (InterruptedException e)
            {
                e.printStackTrace();
            } // catch

	        // Concatenate results.
            result = merge(firstHalf, secondHalf);

		} // if
		else
		{
			// Use java built-in function for sorting the remaining elements.
			result = new int[end - start + 1];
			System.arraycopy(inArray, start, result, 0, (end - start + 1));;
			java.util.Arrays.sort(result);
			// java.util.Arrays.sort(inArray, start, (end + 1));
		} // else

		return result;

	} // inPlaceSort

	// A function that merges two sorted arrays into one (also sorted).
	public int[] merge(int[] left, int[] right)
	{
		// The resulting array.
		int[] result = new int[left.length + right.length];

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
				result[k] = left[i];
				// Increment the index of this array (left) so that next time
				// the next element gets compared to the one in the other
				// array.
				i++;
			} // if
			else
			{
				// Place element in result array.
				result[k] = right[j];
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
			result[k] = left[i];
			i++;
			k++;
		} // while

		while (j < right.length)
		{
			result[k] = right[j];
			j++;
			k++;
		} // while

		return result;
	} // merge

} // class MergeSort