package chatbot;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

/**
 * An Event is a Task that lasts from a given start date/time
 * to a given end date/time.
 */
public class Event extends Task {
    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HHmm");
    private LocalDateTime start;
    private LocalDateTime end;

    /**
     * Creates an Event object.
     * Takes in given task description, start and end time and updates the object.
     *
     * @param task Task Description.
     * @param startString Start time of event.
     * @param endString End time of event.
     */
    public Event(String task, String startString, String endString) {
        super(task);
        try {
            this.start = LocalDateTime.parse(startString, FORMATTER);
            this.end = LocalDateTime.parse(endString, FORMATTER);
        } catch (DateTimeParseException e) {
            throw new InvalidInputException("Invalid datetime format. Please input the datetimes in this format:\n"
                + "YYYY-MM-DD HHMM");
        }
    }

    public LocalDateTime getStart() {
        return this.start;
    }

    public LocalDateTime getEnd() {
        return this.end;
    }

    @Override
    public String toString() {
        DateTimeFormatter displayFormatter = DateTimeFormatter.ofPattern("dd MMM yyyy h:mm a");
        return "[E] " + this.getCompletionStatus() + " " + this.getTask()
            +
            " (from: " + start.format(displayFormatter) + " to: " + end.format(displayFormatter) + ")";
    }

}
