package chatbot;

/**
 * A Task is a class with a given task description and tracks the completion status of the task.
 */
public class Task {
    private String task;
    private boolean completed;

    /**
     * Initialises a new Task object with the given task description
     * @param input Task description.
     */
    public Task(String input) {
        this.task = input;
        this.completed = false;
    }
    
    /**
     * Marks the task as complete.
     * 
     * @param none
     */
    public void markComplete() {
        if (!this.completed) this.completed = !completed;
    }

    /**
     * Marks the task as incomplete.
     * 
     * @param none
     */
    public void markIncomplete() {
        if (this.completed) this.completed = !completed;
    }

    /**
     * Retrieve the task completion status.
     * 
     * @param none
     * @return A matching string to indicate if the task is completed or not.
     */
    public String getCompletionStatus() {
        if (completed) return "[X]"; 
        return "[ ]";
    }

    /**
     * Returns the task description.
     * 
     * @param none
     * @return Task description.
     */
    public String getTask() {
        return this.task;
    }

    @Override
    public String toString() {
        return this.getCompletionStatus() + " " + this.task;
    }
    
}
