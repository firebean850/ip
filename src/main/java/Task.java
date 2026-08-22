public class Task {
    private String task;
    private boolean completed;

    public Task(String input) {
        this.task = input;
        this.completed = false;
    }
    public void markComplete() {
        if (!this.completed) this.completed = !completed;
    }
    public void markIncomplete() {
        if (this.completed) this.completed = !completed;
    }
    public String getCompletionStatus() {
        if (completed) return "[X]"; 
        return "[ ]";
    }
    public String getTask() {
        return this.task;
    }

    @Override
    public String toString() {
        return this.getCompletionStatus() + " " + this.task;
    }
    
}
