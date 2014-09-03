package br.com.wal.delivery.exception;

/**
 * Created by marcelotozzi on 02/09/14.
 */
public class InvalidQueryRouteException extends Exception {
    public InvalidQueryRouteException() {
        super();
    }

    public InvalidQueryRouteException(String message) {
        super(message);
    }

    public InvalidQueryRouteException(String message, Throwable cause) {
        super(message, cause);
    }

    public InvalidQueryRouteException(Throwable cause) {
        super(cause);
    }
}
