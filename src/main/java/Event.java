public class Event extends Task {
    private String start;
    private String end;
    public Event(String input) {
        super(input.split("/from|/to")[0].trim());
        String[] splittedInput = input.split("/from|/to");
        String start = splittedInput[1].trim();
        String end = splittedInput[2].trim();
        this.start = start;
        this.end = end;
    }

    @Override 
    public String toString() {
        return "[E] " + this.getCompletionStatus() + " " + this.getTask() + 
            " (from: " + start + " to: " + end + ")";
    }
    
}
