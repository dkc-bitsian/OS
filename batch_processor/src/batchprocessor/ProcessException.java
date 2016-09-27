package batchprocessor;
/** CS 5338.001 Operating Systems Concepts
 * Krishna Chaitanya Reddy Dodda
*/

/**
 * An exception that should be used to signal problems with the
 * execution of the batch file. 
 */
@SuppressWarnings("serial")
public class ProcessException extends Exception
{
	public ProcessException(String message) {
		super(message);
	}

	public ProcessException(String message, Throwable throwable) {
		super(message, throwable);
	}
}
