public class Deadline extends Task{
    private String deadline;
    public Deadline(String input) {
        super(input.split("/by")[0].trim());
        String[] splittedInput = input.split("/by");
        String deadline = splittedInput[1].trim();
        this.deadline = deadline;
    }

    @Override 
    public String toString() {
        return "[D] " + this.getCompletionStatus() + " " + this.getTask() + 
            " (by: " + deadline + ")";
    }
    
}
