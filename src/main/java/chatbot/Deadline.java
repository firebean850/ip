package chatbot;

/**
 * A Deadline is a Task with a specified deadline.
 */
public class Deadline extends Task{
    private String deadline;

    /**
     * Creates a Deadline object.
     * Parses input string to obtain and update Object with the correct deadline.
     * 
     * @param input Input string.
     */
    public Deadline(String input) {
        super(input.split("/by")[0].trim());
        String[] splittedInput = input.split("/by");
        String deadline = splittedInput[1].trim();
        this.deadline = deadline;
    }

    /**
     * Creates a Deadline object with the given deadline and task description.
     * 
     * @param taskDesc Task description.
     * @param deadline Deadline given.
     */

    public Deadline(String taskDesc, String deadline) {
        super(taskDesc);
        this.deadline = deadline;
    }

    public String getDeadline() {
        return this.deadline;
    }

    @Override 
    public String toString() {
        return "[D] " + this.getCompletionStatus() + " " + this.getTask() + 
            " (by: " + deadline + ")";
    }
    
}
