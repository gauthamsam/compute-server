package api;

/**
 * This defines the interface between the Computer implementation and the work that it needs to do, providing the way to start the work.
 * The interface has a type parameter, T, which represents the result type of the task's computation. 
 * @param <T> the generic type
 */
public interface Task<T> {
	
	/**
	 * Executes a given task.
	 * This method returns the result of the implementing task's computation and thus its return type is T.
	 *
	 * @return Object of type T
	 */
	T execute();
}
