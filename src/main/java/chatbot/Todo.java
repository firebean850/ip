package chatbot;

/**
 * A Todo is a Task, and is a default type of task.
 */
public class Todo extends Task {

    /**
     * Initialises a new Todo with the given input as the task description.
     * @param input
     */
    public Todo(String input) {
        super(input);
    }

    @Override
    public String toString() {
        return "[T] " + this.getCompletionStatus() + " " + this.getTask();
    }
}
