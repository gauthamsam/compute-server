package api;

import java.rmi.Remote;
import java.rmi.RemoteException;

/**
 * This is the remote interface through which different tasks can be submitted and executed.
 * These tasks are run using the task's implementation of the execute method and the results are returned to the remote client.
 * Any object that implements this interface can be a remote object.
 */
public interface Computer extends Remote{
	
	/** The name under which the RMI registry binds the remote reference. */
	public static final String SERVICE_NAME = "Computer";
	
	/**
	 * A remote method to which different tasks can be submitted by the remote clients.
	 * These tasks are run using the task's implementation of the execute method and the results are returned to the remote client.
	 *
	 * @param <T> the generic type
	 * @param t the Task object
	 * @return Object the return value of the Task object's execute method
	 * @throws RemoteException the remote exception
	 */
	<T> T execute(Task<T> t) throws RemoteException;
}
