package org.ramki.order;

/**
 * Exception class used when invalid update is encountered 
 *
 */
public class OrderUpdateException extends RuntimeException {
	private static final long serialVersionUID = 1L;

	public OrderUpdateException(String message) {
        super(message);
    }
}
