package chatbot;

/**
 * An Event is a Task that lasts from a given start date/time 
 * to a given end date/time.
 */
public class Event extends Task {
    private String start;
    private String end;

    /**
     * Creates an Event object.
     * Parses the input string to obtain and update the object with
     * the correct task description, start time and end time.
     * 
     * @param input Input string.
     */
    public Event(String input) {
        super(input.split("/from|/to")[0].trim());
        String[] splittedInput = input.split("/from|/to");
        String start = splittedInput[1].trim();
        String end = splittedInput[2].trim();
        this.start = start;
        this.end = end;
    }

    /**
     * Creates an Event object.
     * Takes in given task description, start and end time and updates the object.
     * 
     * @param task Task Description.
     * @param start Start time of event.
     * @param start End time of event.
     */
    public Event(String task, String start, String end) {
        super(task);
        this.start = start;
        this.end = end;
    }

    public String getStart() {
        return this.start;
    }

    public String getEnd() {
        return this.end;
    }

    @Override 
    public String toString() {
        return "[E] " + this.getCompletionStatus() + " " + this.getTask() + 
            " (from: " + start + " to: " + end + ")";
    }
    
}
