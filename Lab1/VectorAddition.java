import java.util.Random;
import java.lang.Thread;


public class VectorAddition
{
    // Specify the maximum possible value for a randomly generated int.
    private static int maxValue = 1000000;

    // Main method.
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

        // The actual thread number needed.
        // (If 'vectorLength' is less than the specified 'threadNumber',
        // we only need to create 'vectorLength' many threads.)
        if (vectorLength < threadNumber)
            threadNumber = vectorLength;

        System.out.println("==== Program initialised with values - Threads: "
            + threadNumber + " VectorLength: " + vectorLength + " ====\n");

        // Declare vectors.
        int[] firstVector = new int[vectorLength];
        int[] secondVector = new int[vectorLength];
        int[] resultsVector = new int[vectorLength];

        Random generator = new Random();

        // Randomise vector values.
        for (int i = 0; i < vectorLength; i++)
        {
            firstVector[i] = generator.nextInt(maxValue);
            secondVector[i] = generator.nextInt(maxValue);
        } // for

        // Declare the threads that will be used.
        Thread[] adderThreads = new Thread[threadNumber];

        // Calculate how many additions will be performed per thread.
        int additionsPerThread = vectorLength / threadNumber;
        // Calculate what's the 'overhead', i.e. how many extra additions
        // are left over after each thread has been assigned the same number
        // of additions.
        int overhead = vectorLength % threadNumber;

        int leftIndex = 0;
        int rightIndex = leftIndex;

        // Get the starting time of the additions in nanoseconds.
        long startTime = System.nanoTime();

        for (int i = 0; i < threadNumber; i++)
        {
            // The right index will be (additionsPerThread - 1) more
            // than the left index. The '-1' is there because if we want
            // 1 addition for example, that means leftIndex should be the same
            // as righIndex (representing the 1 index to be added in the vectors).
            rightIndex = leftIndex + (additionsPerThread - 1);

            // Now check if we have any overhead left, if yes, we split this evenly
            // between the threads so just increment rightIndex and decrement
            // the overhead variable.
            if (overhead > 0)
            {
                rightIndex++;
                overhead--;
            } // if

            // Create a new Runnable object 'VectorAdder' for the specified index range and start it.
            VectorAdder newAdder = new VectorAdder(firstVector, secondVector, resultsVector, leftIndex, rightIndex);
            adderThreads[i] = new Thread(newAdder);
            adderThreads[i].start();

            // Update leftIndex to be the next value after rightIndex.
            leftIndex = rightIndex + 1;
        } // for

        // Wait for the threads to finish.
        for (int i = 0; i < threadNumber; i++)
            try
            {
                adderThreads[i].join();
            } // try
            catch (InterruptedException e)
            {
                e.printStackTrace();
            } // catch


        // Compute the duration of the addition.
        long duration = System.nanoTime() - startTime;
        // Transform into seconds.
        double durationInSecond = duration / 1000000000.0;
        // Calculate performance.
        double performance = 1.0 / durationInSecond;

        // To print results of addition, uncomment following lines.
        // printVector(firstVector, "First");
        // printVector(secondVector, "Second");
        // printVector(resultsVector, "Result");

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

} // class VectorAddition