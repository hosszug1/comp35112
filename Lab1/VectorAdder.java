public class VectorAdder implements Runnable
{
	// Declare the variables for the vectors and indexes.
	private int[] firstV;
	private int[] secondV;
	private int[] resultsV;
	private int leftI;
	private int rightI;


	// Constructor. Takes the 3 vectors and 2 indexes as parameters.
	public VectorAdder(int[] firstV, int secondV[], int[] resultsV, int leftI, int rightI)
	{
		this.firstV = firstV;
		this.secondV = secondV;
		this.resultsV = resultsV;
		this.leftI = leftI;
		this.rightI = rightI;
		// System.out.println("New thread created with values:\n"
		// 	+ "leftIndex: " + leftI
		// 	+ "\nrightIndex: " + rightI);
		// System.out.println("Number of additions assigned to this thread: " + (rightI - leftI + 1));
		// System.out.println("====================================================");
	} // VectorAdder


	// The run method of the Runnable. Updates the values in the result vector
	// with the additions it performs.
	public void run()
	{
		// System.out.println("Thread running.");
		for (int i = leftI; i <= rightI; i++)
			resultsV[i] = firstV[i] + secondV[i];
	} // run

} // class VectorAdder