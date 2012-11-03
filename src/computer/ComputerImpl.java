package computer;

import java.rmi.RMISecurityManager;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;

import api.Computer;
import api.Task;

/**
 * This class enables different tasks to be submitted by the remote clients using its remote reference
 * These tasks are run using the task's implementation of the execute method and the results are returned to the remote client.
 *
 * @author gautham
 */
public final class ComputerImpl extends UnicastRemoteObject implements Computer{
	
	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

	/**
	 * Instantiates a new implementation object for the Computer Interface
	 *
	 * @throws RemoteException the remote exception
	 */
	public ComputerImpl() throws RemoteException{		
	}

	/**
	 * Different tasks can be submitted to this method
	 * These tasks are run using the task's implementation of the execute method and the results are returned to the remote client.
	 *
	 * @param <T> the generic type
	 * @param t the Task object
	 * @return Object the return value of the Task object's execute method
	 * @throws RemoteException the remote exception
	 */
	@Override
	public <T> T execute(Task<T> t) throws RemoteException {
		return t.execute();
	}
	
	/**
	 * The main method.
	 *
	 * @param args the arguments
	 * @throws Exception the exception
	 */
	public static void main(String[] args) throws Exception {
		// Construct & set a security manager to allow downloading of classes from a remote codebase
		System.setSecurityManager(new RMISecurityManager());
		// instantiate a server object
		Computer computer = new ComputerImpl(); // can throw RemoteException
		// construct an rmiregistry within this JVM using the default port
		Registry registry = LocateRegistry.createRegistry(1099);
		// bind server in rmiregistry. 
		registry.rebind(Computer.SERVICE_NAME, computer);
		System.out.println("Computer is ready to execute tasks.");
	}
	
	
}
