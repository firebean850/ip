package chatbot;

/**
 * Represents a task with a description and completion status.
 */
public class Task {
    private static final String COMPLETE_INDICATOR = "[X]";
    private static final String INCOMPLETE_INDICATOR = "[ ]";

    private final String description;
    private boolean completed;

    /**
     * Initialises a new Task object with the given task description
     * @param description Task description.
     */
    public Task(String description) {
        if (description == null || description.isBlank()) {
            throw new InvalidInputException("Task description cannot be empty.");
        }
        this.description = description.trim();
    }

    /**
     * Marks the task as complete.
     */
    public void markComplete() {
        completed = true;
    }

    /**
     * Marks the task as incomplete.
     */
    public void markIncomplete() {
        completed = false;
    }

    /**
     * Retrieve the task completion status.
     * @return A matching string to indicate if the task is completed or not.
     */
    public String getCompletionStatus() {
        if (completed) {
            return COMPLETE_INDICATOR;
        }
        return INCOMPLETE_INDICATOR;
    }

    /**
     * Returns the task description.
     * @return Task description.
     */
    public String getDescription() {
        return this.description;
    }

    /**
     * Returns the task's completion marker and description.
     *
     * @return Formatted task description.
     */
    @Override
    public String toString() {
        return this.getCompletionStatus() + " " + this.description;
    }

}
