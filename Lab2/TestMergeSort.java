import java.util.Random;

public class TestMergeSort
{
    // Specify the maximum possible value for a randomly generated int.
    private static int maxValue = 1000000;

	public static void main(String[] args)
	{
		int threadNumber = 0;
        int vectorLength = 0;
        if (args.length != 2)
        {
            System.out.println("You have not specified the correct command line parameters.\n"
                + "These should be the number of threads to be used and the vector length.");
            System.exit(-1);
        } // if
        else
        {
            threadNumber = Integer.parseInt(args[0]);
            vectorLength = Integer.parseInt(args[1]);
        } // else

        int[] testArray = new int[vectorLength];
        // Random generator = new Random();

        // Randomise vector values.
        // for (int i = 0; i < vectorLength; i++)
        // {
        //     testArray[i] = generator.nextInt(maxValue);
        // } // for

        // Instead of randomising the array values, create the "worst case" scenario
        // for merge-sorting.
        int p = 1, q = (vectorLength / 2);
	    for (int i = 0; i < vectorLength; i++)
	    {
	    	if (i % 2 == 0)
	    	{
	        	testArray[i] = p;
	        	p++;
	        } // if
	        else
	        {
	        	testArray[i] = q;
	        	q--;
	        } // else
	    } // for

        // printVector(testArray, "Initial Test Array");

		// Get the starting time of the additions in nanoseconds.
        long startTime = System.nanoTime();
        // Do the sorting.
		MergeSort newMergeSorting = new MergeSort();
        int[] sortedTestArray = newMergeSorting.inPlaceSort(testArray, 0, testArray.length - 1, threadNumber);
        // Compute the duration of the addition.
        long duration = System.nanoTime() - startTime;
		// Transform into seconds.
        double durationInSecond = duration / 1000000000.0;
        // Calculate performance.
        double performance = 1.0 / durationInSecond;

		// To print results of addition, uncomment following lines.
        // printVector(testArray, "Initial Test Array");
        // printVector(sortedTestArray, "Sorted Test Array");

        // Print duration and performance.
        System.out.println("Duration in seconds:\t" + durationInSecond);
        System.out.println("Performance:\t\t" + performance);
	} // main

	// Helper method for printing out vector contents.
    public static void printVector(int[] vector, String vectorName)
    {
        System.out.print(vectorName + " vector:\t");
        for (int i = 0; i < vector.length; i++)
            System.out.print(vector[i] + "\t");
        System.out.println();
    } // printVector

} // class TestMergeSort