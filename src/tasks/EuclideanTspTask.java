package tasks;

import java.io.Serializable;
import java.util.Arrays;

import api.Task;

/**
 * This class solves a Traveling Salesman Problem (TSP), where the cities are points in the 2D Euclidean plane.
 */
public final class EuclideanTspTask implements Task<int[]>, Serializable{

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;
	
	/** The cities in 2D Euclidean plance that are part of the TSP. */
	private double[][] cities;
	
	/**
	 * Instantiates a new Euclidean TSP task.
	 *
	 * @param cities the cities in 2D Euclidean plance that are part of the TSP; it codes the x and y coordinates of city[i]: cities[i][0] is the x-coordinate of city[i] and cities[i][1] is the y-coordinate of city[i]
	 */
	public EuclideanTspTask(double[][] cities){
		this.cities = cities;		
	}
	
	/**
	 * Executes the Euclidean TSP Task.
	 * The method of finding the minimal distance tour is not efficient; the program will iterate over all permutations of the cities, and return a permutation of least cost. 
	 * @return tour that lists the order of the cities of a minimal distance tour. 	
	 */
	@Override
	public int[] execute() {
		// tour lists the order of the cities of a minimal distance tour.
		int[] tour = null;
		// Variable to hold the minimum distance between all the cities.
		double minDistance = Double.MAX_VALUE;
		// Array to hold the nth permutation.
		int[] permutation = new int[cities.length];
		int n = 1;
		// currentDistance holds the distance traveled for the given permutation of the cities
		double currentDistance = 0;
		
		while(true){			
			permutation = getPermutation(permutation, n++);
			if(permutation == null){ // All the permutations have been computed. No more left.
				break;
			}
			for(int j = 0; j < permutation.length; j++){
				currentDistance += calculateDistance(cities[permutation[j]], cities[permutation[(j + 1) % permutation.length]]);				
			}
			if(minDistance > currentDistance){
				minDistance = currentDistance;
				tour = Arrays.copyOf(permutation, permutation.length);				
			}
			currentDistance = 0;
		}
		
		return tour;
	}
	
	/**
	 * Generate the nth permutations in lexicographic order.
	 *
	 * @param permutation the (n-1)th permutation array; when n = 1, it is just 0 to permutation length - 1
	 * @param n the nth permutation to be computed
	 * @return the nth permutation
	 */
	private int[] getPermutation(int[] permutation, int n) {
		if(n == 1){ // the first permutation is just 0 to permutation length - 1 (0 to no. of cities - 1)
			for (int i = 0; i < permutation.length; i++)
		    	permutation[i] = i;
			return permutation;
		}
		int k, l;
        // Find the largest index k such that a[k] < a[k + 1]. If no such index exists, the permutation is the last permutation.
        for (k = permutation.length - 2; k >=0 && permutation[k] >= permutation[k+1]; k--);
        if(k == -1){
        	return null;
        }
        // Find the largest index l such that a[k] < a[l]. Since k + 1 is such an index, l is well defined and satisfies k < l.
        for (l = permutation.length - 1; permutation[k] >= permutation[l]; l--);
        // Swap a[k] with a[l].
        swap(permutation, k, l);
        // Reverse the sequence from a[k + 1] up to and including the final element a[n].
        for (int j = 1; k + j < permutation.length - j; j++){
        	swap(permutation, k + j, permutation.length - j);
        }
        return permutation;
	}
	
	/**
	 * Swap the elements of the array in place.
	 *
	 * @param arr the array
	 * @param i the ith position
	 * @param j the jth position
	 */
	private void swap(int[] arr, int i, int j){
		int temp = arr[i];
		arr[i] = arr[j];
		arr[j] = temp;
	}
	
	/**
	 * Prints the array.
	 *
	 * @param arr the array
	 */
	private void printArray(int[] arr){
		for(int i : arr){
			System.out.print(i + ", ");
		}
		System.out.println("");
	}
	
	/**
	 * Calculate the Euclidean distance.
	 *
	 * @param pointA the starting point
	 * @param pointB the ending point
	 * @return distance the distance between the points
	 */
	private double calculateDistance(double[] pointA, double[] pointB){		
		double temp1 = Math.pow((pointA[0] - pointB[0]), 2);
		double temp2 = Math.pow((pointA[1] - pointB[1]), 2);
		double distance = Math.sqrt(temp1 + temp2);
		return distance;
	}

	/**
	 * The main method.
	 *
	 * @param args the arguments
	 */
	public static void main(String[] args) {
		double[][] cities = {
				{6, 3},
				{2, 2},
				{5, 8},
				{1, 5},
				{1, 6},
				{2, 7},
				{2, 8},
				{6, 5},
				{1, 3},
				{6, 6}
				}; 
		EuclideanTspTask task = new EuclideanTspTask(cities);
		long startTime = System.currentTimeMillis();
		int[] tour = task.execute();
		System.out.println("Time: " + (System.currentTimeMillis() - startTime));
		System.out.println("Tour");
		task.printArray(tour);		
	}
}
