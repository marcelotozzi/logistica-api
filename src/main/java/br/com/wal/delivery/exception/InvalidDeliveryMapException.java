package br.com.wal.delivery.exception;

/**
 * Created by marcelotozzi on 01/09/14.
 */
public class InvalidDeliveryMapException extends Exception {
    public InvalidDeliveryMapException(String message) {
        super(message);
    }

    public InvalidDeliveryMapException(String message, Throwable cause) {
        super(message, cause);
    }

    public InvalidDeliveryMapException(Throwable cause) {
        super(cause);
    }
}
