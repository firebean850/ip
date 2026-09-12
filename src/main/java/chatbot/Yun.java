package chatbot;

/**
 * Represents the Yun chatbot and coordinates task management, storage, parsing, and user messages.
 */
public class Yun {
    private static final String BYE_COMMAND = "bye";
    private static final String LIST_COMMAND = "list";
    private static final String FIND_COMMAND = "find";
    private static final String MARK_COMMAND = "mark";
    private static final String UNMARK_COMMAND = "unmark";
    private static final String TODO_COMMAND = "todo";
    private static final String DEADLINE_COMMAND = "deadline";
    private static final String EVENT_COMMAND = "event";
    private static final String DELETE_COMMAND = "delete";
    private static final String ON_COMMAND = "on";

    private Storage storage;
    private Ui ui;
    private TaskList taskList;

    /**
     * Creates a Yun chatbot and loads its saved task list.
     */
    public Yun() {
        storage = new Storage("taskList.txt");
        ui = new Ui();
        taskList = storage.load();
    }

    /**
     * Returns the chatbot's welcome message.
     *
     * @return Welcome message displayed when the application starts.
     */
    public String getWelcomeMessage() {
        return ui.showWelcomeMessage();
    }

    /**
     * Saves the current task list and returns the exit message.
     */
    private String handleByeCommand() {
        storage.save(taskList);
        return ui.showExitMessage();
    }

    /**
     * Returns a formatted view of all tasks.
     */
    private String handleListCommand() {
        return ui.showTasks(taskList);
    }

    /**
     * Searches for tasks matching the keyword in the user input.
     */
    private String handleFindCommand(String input) {
        String keyword = Parser.getFindKeyword(input);
        return ui.showMatchingTasks(taskList, keyword);
    }

    /**
     * Marks the task identified by the user input as complete.
     */
    private String handleMarkCommand(String input) {
        int taskNumber = Parser.getMarkOrUnmarkTaskNumber(input);
        if (taskNumber > taskList.size() || taskNumber <= 0) {
            throw new InvalidInputException("Sorry, the task number you entered was invalid. Please try again.");
        }
        taskList.get(taskNumber - 1).markComplete();
        storage.save(taskList);
        return ui.showMarked(taskList.get(taskNumber - 1));
    }

    /**
     * Marks the task identified by the user input as incomplete.
     */
    private String handleUnmarkCommand(String input) {
        int taskNo = Parser.getMarkOrUnmarkTaskNumber(input);
        if (taskNo > taskList.size() || taskNo <= 0) {
            throw new InvalidInputException("Sorry, the task number you entered was invalid.");
        }
        taskList.get(taskNo - 1).markIncomplete();
        storage.save(taskList);
        return ui.showUnmarked(taskList.get(taskNo - 1));
    }

    /**
     * Creates and saves a todo task from the user input.
     */
    private String handleTodoCommand(String input) {
        String taskDesc = Parser.getTodoDescription(input);
        taskList.add(new Todo(taskDesc));
        storage.save(taskList);
        return ui.showAdded(taskList.get(taskList.size() - 1), taskList.size());
    }

    /**
     * Creates and saves a deadline task from the user input.
     */
    private String handleDeadlineCommand(String input) {
        String deadlineDesc = Parser.getDeadlineDescription(input);
        String deadline = Parser.getDeadline(input);
        taskList.add(new Deadline(deadlineDesc, deadline));
        storage.save(taskList);
        return ui.showAdded(taskList.get(taskList.size() - 1), taskList.size());
    }

    /**
     * Creates and saves an event task from the user input.
     */
    private String handleEventCommand(String input) {
        String eventDesc = Parser.getEventDescription(input);
        String eventStart = Parser.getEventStart(input);
        String eventEnd = Parser.getEventEnd(input);
        taskList.add(new Event(eventDesc, eventStart, eventEnd));
        storage.save(taskList);
        return ui.showAdded(taskList.get(taskList.size() - 1), taskList.size());
    }

    /**
     * Deletes the task identified by the user input.
     */
    private String handleDeleteCommand(String input) {
        int taskId = Parser.getDeleteTaskNumber(input);
        if (taskId <= 0 || taskId > taskList.size()) {
            throw new InvalidInputException("The task number must be more than 0 and cannot be more than "
                    + "the number of tasks in the list. Please try again.");
        }
        String deleteMessage = ui.showDeleted(taskList.get(taskId - 1), taskList.size() - 1);
        taskList.remove(taskId - 1);
        storage.save(taskList);
        return deleteMessage;
    }

    /**
     * Returns tasks occurring on the date supplied by the user.
     */
    private String handleOnCommand(String input) {
        String dateString = Parser.getOnCommandDateText(input);
        return ui.listTasksOnDate(taskList, dateString);
    }

    /**
     * Throws an exception for an unrecognised command.
     */
    private String handleInvalidInput() {
        throw new InvalidInputException("Yo! Invalid input bro, please try again!");
    }

    /**
     * Converts an exception into a user-facing error message.
     */
    private String handleException(Exception e) {
        return ui.showError(e);
    }

    /**
     * Dispatches a user input string to the matching command handler.
     */
    private String handle(String input) {
        String command = Parser.getCommand(input);
        switch (command) {
            case BYE_COMMAND, "Bye":
                return handleByeCommand();
            case LIST_COMMAND:
                return handleListCommand();
            case FIND_COMMAND:
                return handleFindCommand(input);
            case MARK_COMMAND:
                return handleMarkCommand(input);
            case UNMARK_COMMAND:
                return handleUnmarkCommand(input);
            case TODO_COMMAND:
                return handleTodoCommand(input);
            case DEADLINE_COMMAND:
                return handleDeadlineCommand(input);
            case EVENT_COMMAND:
                return handleEventCommand(input);
            case DELETE_COMMAND:
                return handleDeleteCommand(input);
            case ON_COMMAND:
                return handleOnCommand(input);
            default:
                return handleInvalidInput();
        }
    }

    /**
     * Returns the chatbot's response to a user command and updates the task list when necessary.
     *
     * @param input User command to process.
     * @return Response message for the supplied command.
     */
    public String getResponse(String input) {
        try {
            return handle(input);
        } catch (InvalidInputException e) {
            return handleException(e);
        }
    }
}
