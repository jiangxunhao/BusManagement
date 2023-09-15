package stacs.bus.busManagement.exception;

public class InvalidRouteNameException extends Exception {
    /**
     * Throw error when Route did not exist
     *
     * @param text - error
     */
    public InvalidRouteNameException(String text) {
        super(text);
    }
}
