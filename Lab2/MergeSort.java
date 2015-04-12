import java.util.Arrays.*;

public class MergeSort implements Runnable
{	
	// Constructor.
	public MergeSort()
	{

	} // MergeSort

	//
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
			Thread[] mergeSortThreads = new Thread[2];
			// First half. Spawn a new thread.
            MergeSort newSort1 = new MergeSort();
            mergeSortThreads[0] = new Thread(newSort1);
            mergeSortThreads[0].start(); 
			result1 = newSort1.inPlaceSort(inArray, start, (start + end) / 2, threadNo / 2);

			// Create a new Runnable object 'VectorAdder' for the specified index range and start it.
            VectorAdder newAdder = new VectorAdder(firstVector, secondVector, resultsVector, leftIndex, rightIndex);
            adderThreads[i] = new Thread(newAdder);
            adderThreads[i].start();

			// Second half. Spawn a new thread.
            MergeSort newSort2 = new MergeSort();
            mergeSortThreads[1] = new Thread(newSort2);
            mergeSortThreads[1].start(); 
			result2 = newSort2.inPlaceSort(inArray, (start + end) / 2 + 1, end, threadNo / 2);

			// Join threads.
	        for (int i = 0; i < 2; i++)
	            try
	            {
	                mergeSortThreads[i].join();
	            } // try
	            catch (InterruptedException e)
	            {
	                e.printStackTrace();
	            } // catch

	        // Concatenate results.
		} // if
		else
		{

		} // else

		return result;

	} // inPlaceSort

	//
	public static void main(String[] args)
	{
		
	} // main

} // class MergeSort