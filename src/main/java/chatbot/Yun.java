package chatbot;


/**
 * Represents the Yun chatbot and coordinates task management, storage, parsing, and user messages.
 */
public class Yun {
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
     * Returns the chatbot's response to a user command and updates the task list when necessary.
     *
     * @param input User command to process.
     * @return Response message for the supplied command.
     */
    public String getResponse(String input) {
        try {
            String command = Parser.getCommand(input);
            switch (command) {
                case "bye", "Bye":
                    storage.save(taskList);
                    return ui.showExitMessage();
                case "list":
                    return ui.showTasks(taskList);
                case "find":
                    String keyword = Parser.getFindKeyword(input);
                    return ui.showMatchingTasks(taskList, keyword);
                case "mark":
                    int taskNumber = Parser.getMarkOrUnmarkTaskNumber(input);
                    if (taskNumber > taskList.size() || taskNumber <= 0) {
                        throw new InvalidInputException(
                            "Sorry, the task number you entered was invalid. Please try again.");
                    }
                    taskList.get(taskNumber - 1).markComplete();
                    storage.save(taskList);
                    return ui.showMarked(taskList.get(taskNumber - 1));
                case "unmark":
                    int taskNo = Parser.getMarkOrUnmarkTaskNumber(input);
                    if (taskNo > taskList.size() || taskNo <= 0) {
                        throw new InvalidInputException("Sorry, the task number you entered was invalid.");
                    }
                    taskList.get(taskNo - 1).markIncomplete();
                    storage.save(taskList);
                    return ui.showUnmarked(taskList.get(taskNo - 1));
                case "todo":
                    String taskDesc = Parser.getTodoDescription(input);
                    taskList.add(new Todo(taskDesc));
                    storage.save(taskList);
                    return ui.showAdded(taskList.get(taskList.size() - 1), taskList.size());
                case "deadline":
                    String deadlineDesc = Parser.getDeadlineDescription(input);
                    String deadline = Parser.getDeadline(input);
                    taskList.add(new Deadline(deadlineDesc, deadline));
                    storage.save(taskList);
                    return ui.showAdded(taskList.get(taskList.size() - 1), taskList.size());
                case "event":
                    String eventDesc = Parser.getEventDescription(input);
                    String eventStart = Parser.getEventStart(input);
                    String eventEnd = Parser.getEventEnd(input);
                    taskList.add(new Event(eventDesc, eventStart, eventEnd));
                    storage.save(taskList);
                    return ui.showAdded(taskList.get(taskList.size() - 1), taskList.size());
                case "delete":
                    int taskId = Parser.getDeleteTaskNumber(input);
                    if (taskId <= 0 || taskId > taskList.size()) {
                        throw new InvalidInputException(
                            "The task number must be more than 0 and cannot be more than the number of tasks "
                                + "in the list. Please try again.");
                    }
                    String deleteMessage = ui.showDeleted(taskList.get(taskId - 1), taskList.size() - 1);
                    taskList.remove(taskId - 1);
                    storage.save(taskList);
                    return deleteMessage;
                case "on":
                    String dateString = Parser.getOnCommandDateText(input);
                    return ui.listTasksOnDate(taskList, dateString);
                default:
                    throw new InvalidInputException("Yo! Invalid input bro, please try again!");
            }
        } catch (InvalidInputException e) {
            return ui.showError(e);
        }

    }
}
