package client;

import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RMISecurityManager;
import java.rmi.RemoteException;

import tasks.EuclideanTspTask;
import tasks.MandelbrotSetTask;

import api.Computer;
import api.Task;

/**
 * This class represents an RMI client that can execute tasks on a remote computer.
 * The RMI client requests a reference to a named remote object. The reference (the remote object's stub instance) is what the client will use to make remote method calls to the remote object.
 * The client also encompasses the functionality of visualizing the results of the different tasks that are executed on a remote machine.
 */
public class Client {
	
	/** The remote server url. */
	private static String serverURL;
	
	/**
	 * The main method.
	 *
	 * @param args the arguments
	 * @throws Exception the exception
	 */
	public static void main(String[] args) throws Exception {
		// Construct & set a security manager to allow downloading of classes from a remote codebase
		System.setSecurityManager(new RMISecurityManager());
		String serverDomainName = args[0];
		serverURL = "//" + serverDomainName + "/" + Computer.SERVICE_NAME;
		
		// t1 is an instance of the MandelbrotSetTask
		Task<int[][]> t1 = new MandelbrotSetTask(new double[] { -2, -2 }, 4,
				256, 64);
		double[][] cities = { { 6, 3 }, { 2, 2 }, { 5, 8 }, { 1, 5 }, { 1, 6 },
				{ 2, 7 }, { 2, 8 }, { 6, 5 }, { 1, 3 }, { 6, 6 } };
		// t2 is an instance of the EuclideanTspTask
		Task<int[]> t2 = new EuclideanTspTask(cities);
		
		
		int[][] counts = (int[][]) runTask(t1);
		int[] tour = (int[]) runTask(t2);
		// Visualize the results using Java graphics
		Visualizer.visualize(counts, 256, tour, cities);

	}
	
	/**
	 * Runs the given task by calling the execute method on the remote server.
	 * The execution is repeated five times and the round trip time involved in the remote execution is calculated.
	 *
	 * @param task the task
	 * @return object
	 * @throws RemoteException the remote exception
	 * @throws MalformedURLException the malformed url exception
	 * @throws NotBoundException the not bound exception
	 */
	private static Object runTask(Task task) throws RemoteException, MalformedURLException, NotBoundException
	{
		// The RMI client requests a reference to a named remote object. The reference (the remote object's stub instance) is what the client will use to make remote method calls to the remote object.
		Computer computer = (Computer) Naming.lookup(serverURL);
		/* print task class name;
		* run task 5 times
		* collect/print the execution times
		* compute the average time		
		*/
		System.out.println("Task: " + task.getClass().getName());
		long startTime = 0;
		long endTime = 0;
		long totalElapsedTime = 0;
		long elapsedTime = 0;
		Object obj = null;
		System.out.println("Elapsed Time:");
		System.out.println("----------------------------------");
		for (int i = 0; i < 5; i++) {
			startTime = System.currentTimeMillis();
			obj = computer.execute(task);
			endTime = System.currentTimeMillis();
			elapsedTime = endTime - startTime;
			System.out.println("Trial " + (i + 1) + ": " + elapsedTime + " ms");
			totalElapsedTime += elapsedTime;
		}
		System.out.println("Avg. Elapsed Time: " + totalElapsedTime / 5 + " ms");
		System.out.println("----------------------------------");
		return obj;
	}
}
