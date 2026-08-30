package chatbot;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;


/**
 * A Deadline is a Task with a specified deadline.
 */
public class Deadline extends Task{
    private LocalDateTime deadline;
    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HHmm");

    /**
     * Creates a Deadline object with the given deadline and task description.
     * 
     * @param taskDesc Task description.
     * @param deadline Deadline given.
     */
    public Deadline(String taskDesc, String deadlineString) {
        super(taskDesc);
        try {
            this.deadline = LocalDateTime.parse(deadlineString, FORMATTER);
        } catch (DateTimeParseException e) {
            throw new InvalidInputException("Invalid datetime format. Please input the datetime in this format:\n"
                + "YYYY-MM-DD HHMM");
        }
    }

    public LocalDateTime getDeadline() {
        return this.deadline;
    }

    @Override 
    public String toString() {
        DateTimeFormatter displayFormatter = DateTimeFormatter.ofPattern("dd MMM yyyy h:mm a");
        return "[D] " + this.getCompletionStatus() + " " + this.getTask() + 
            " (by: " + deadline.format(displayFormatter) + ")";
    }
    
}
