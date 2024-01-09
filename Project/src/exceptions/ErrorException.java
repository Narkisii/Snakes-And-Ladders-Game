/**
 * 
 */
package exceptions;

/**
 * @author liorf
 *
 */
public class ErrorException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 4385096748564085719L;

	/**
	 * 
	 */
	public ErrorException() {
		// TODO Auto-generated constructor stub
	}

	/**
	 * @param message
	 */
	public ErrorException(String message) {
		super(message);
		// TODO Auto-generated constructor stub
	}

	/**
	 * @param cause
	 */
	public ErrorException(Throwable cause) {
		super(cause);
		// TODO Auto-generated constructor stub
	}

	/**
	 * @param message
	 * @param cause
	 */
	public ErrorException(String message, Throwable cause) {
		super(message, cause);
		// TODO Auto-generated constructor stub
	}

	/**
	 * @param message
	 * @param cause
	 * @param enableSuppression
	 * @param writableStackTrace
	 */
	public ErrorException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
		// TODO Auto-generated constructor stub
	}

}
