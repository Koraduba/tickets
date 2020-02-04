package epam.pratsaunik.tickets.exception;

public class ServiceLevelException extends Exception{
    public ServiceLevelException() {
    }

    public ServiceLevelException(String message) {
        super(message);
    }

    public ServiceLevelException(String message, Throwable cause) {
        super(message, cause);
    }

    public ServiceLevelException(Throwable cause) {
        super(cause);
    }
}
