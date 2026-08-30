package chatbot;

/**
 * An InvalidInputException is a RuntimeException that is thrown when there are invalid inputs.
 */
public class InvalidInputException extends RuntimeException {
    
    /**
     * Creates an InvalidInputException with given message.
     * 
     * @param message Message to be printed.
     */
    public InvalidInputException(String message) {
        super(message);
    }
}
