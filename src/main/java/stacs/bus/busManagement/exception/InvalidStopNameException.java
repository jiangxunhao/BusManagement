package stacs.bus.busManagement.exception;

public class InvalidStopNameException extends Exception {
    /**
     * Throw error when Stop did not exist
     *
     * @param text - error
     */
    public InvalidStopNameException(String text) {
        super(text);
    }
}
