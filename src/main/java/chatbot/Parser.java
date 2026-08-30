package chatbot;

/**
 * The parser class parses input strings from users to retrieve user instructions.
 */
public class Parser {
    
    /**
     * Parses input string to retrieve command.
     * @param input Input string from user.
     * @return Command by user.
     */
    public static String getCommand(String input) {
        String[] parsedInput = input.split(" ", 2);
        String command = parsedInput[0];
        return command;
    }

    /**
     * Retrieves task number to be marked/unmarked from input string (1-based).
     * @param input Input string from user.
     * @return Task number of task to be marked/unmarked.
     */
    public static int getMarkOrUnmarkTaskNumber(String input) {
        String[] parsedInput = input.split(" ", 2);
        try {
            if (parsedInput.length < 2) {
                throw new NumberFormatException();
            }
            return Integer.parseInt(parsedInput[1].trim());
        } catch (NumberFormatException e) {
            throw new InvalidInputException("Please key in a number after the mark/unmark command.");
        }
    }

    /**
     * Retrieves task number to be deleted from user's input string (1-based).
     * @param input Input string from user.
     * @return Task number of task to be deleted.
     */
    public static int getDeleteTaskNumber(String input) {
        int taskId;
        String[] parsedInput = input.split(" ", 2);
        if (parsedInput.length < 2 || parsedInput[1].trim().isEmpty()) {
            throw new InvalidInputException("Heyo! The description of a delete cannot be empty, and/or the task number " 
                + "must be more than 0. Please try again, including the task number of the task you want to delete " 
                + "after the delete command");
        } else try {
            taskId = Integer.parseInt(parsedInput[1].trim());
            return taskId;
        } catch (NumberFormatException e) {
            throw new InvalidInputException("HEY! Please key in a number after the delete command.");
        }   
    }

    /**
     * Retrieve the description of a todo instruction from user input.
     * @param input User input.
     * @return Description of todo.
     */
    public static String getTodoDescription(String input) {
        String[] parsedInput = input.split(" ", 2);
        if (parsedInput.length < 2 || parsedInput[1].trim().isEmpty()) {
            throw new InvalidInputException("Heyo! The description of a todo cannot be empty. Please try again.");
        }
        return input.split("todo ")[1].trim();
    }

    /**
     * Retrieve the description of a deadline instruction from user input.
     * @param input User input.
     * @return Description of deadline.
     */
    public static String getDeadlineDescription(String input) {
        if (!input.contains("/by")) {
            throw new InvalidInputException("Sorry, please provide a due date for the deadline command in this format:\n" + 
                "deadline <task desc> /by <due date> without angular brackets.");
        }
        return input.split("deadline ")[1].trim().split("/by")[0].trim();
    }
    /**
     * Retrieves the deadline of a deadline instruction from user input.
     * @param input User input
     * @return Deadline as a string.
     */
    public static String getDeadline(String input) {
        return input.split("deadline ")[1].trim().split("/by")[1].trim();
    }

    /**
     * Retrieve the description of an event instruction from user input.
     * @param input Input string of user.
     * @return Description of event.
     */
    public static String getEventDescription(String input) {
        if (!input.contains("/from") || !input.contains("/to") ) {
            throw new InvalidInputException("Sorry, please provide a start and end date/time for the event command in this format:\n" + 
                "event <task desc> /from <start> /to <end> without angular brackets.");
        }
        return input.split("event ")[1].trim().split("/from|/to")[0].trim();
    }

    /**
     * Retrieve event start time of event instruction from user input
     * @param input Input string of user.
     * @return Event start time as a String.
     */
    public static String getEventStart(String input) {
        return input.split("event ")[1].trim().split("/from|/to")[1].trim();
    }
    
    /**
     * Retrieve event end time of event instruction from user input
     * @param input Input string of user.
     * @return Event end time as a String.
     */
    public static String getEventEnd(String input) {
        return input.split("event ")[1].trim().split("/from|/to")[2].trim();
    }

    /**
     * Gets the date field of an "on" command instruction from user input
     * @param input Input string of user.
     * @return Date field of an "on" command instruction
     */
    public static String getOnCommandDateText(String input) {
        String[] parsedInput = input.split(" ", 2);
        if (parsedInput.length < 2) {
            throw new InvalidInputException("Please provide a date in the format YYYY-MM-DD");
        }
        return parsedInput[1].trim();
    }

    /**
     * Retrieves the keyword from a find command.
     * @param input Input string from user.
     * @return Keyword to search for.
     */
    public static String getFindKeyword(String input) {
        String[] parsedInput = input.split(" ", 2);
        if (parsedInput.length < 2 || parsedInput[1].trim().isEmpty()) {
            throw new InvalidInputException("Please provide a keyword after the find command.");
        }
        return parsedInput[1].trim();
    }

}
