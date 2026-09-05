package chatbot;


/**
 * Yun is an interactive chatbot.
 */
public class Yun {

    public static void main(String[] args) {
        Storage storage = new Storage("taskList.txt");
        Ui ui = new Ui();
        TaskList taskList = storage.load();
        ui.showWelcomeMessage();
        boolean running = true;
        while (running) {
            try {
                String input = ui.readInputWithCommand();
                String command = Parser.getCommand(input);
                ui.printLine();
                switch (command) {
                    case "bye": //Fallthrough
                    case "Bye":
                        ui.showExitMessage();
                        storage.save(taskList);
                        running = false;
                        break;
                    case "list":
                        ui.showTasks(taskList);
                        break;
                    case "find":
                        String keyword = Parser.getFindKeyword(input);
                        ui.showMatchingTasks(taskList, keyword);
                        break;
                    case "mark":
                        int taskNumber = Parser.getMarkOrUnmarkTaskNumber(input);
                        if (taskNumber > taskList.size() || taskNumber <= 0) {
                            throw new InvalidInputException(
                                "Sorry, the task number you entered was invalid. Please try again.");
                        }
                        taskList.get(taskNumber - 1).markComplete();
                        ui.showMarked(taskList.get(taskNumber - 1));
                        storage.save(taskList);
                        break;
                    case "unmark":
                        int taskNo = Parser.getMarkOrUnmarkTaskNumber(input);
                        if (taskNo > taskList.size() || taskNo <= 0) {
                            throw new InvalidInputException("Sorry, the task number you entered was invalid.");
                        }
                        taskList.get(taskNo - 1).markIncomplete();
                        ui.showUnmarked(taskList.get(taskNo - 1));
                        storage.save(taskList);
                        break;
                    case "todo":
                        String taskDesc = Parser.getTodoDescription(input);
                        taskList.add(new Todo(taskDesc));
                        ui.showAdded(taskList.get(taskList.size() - 1), taskList.size());
                        storage.save(taskList);
                        break;
                    case "deadline":
                        String deadlineDesc = Parser.getDeadlineDescription(input);
                        String deadline = Parser.getDeadline(input);
                        taskList.add(new Deadline(deadlineDesc, deadline));
                        ui.showAdded(taskList.get(taskList.size() - 1), taskList.size());
                        storage.save(taskList);
                        break;
                    case "event":
                        String eventDesc = Parser.getEventDescription(input);
                        String eventStart = Parser.getEventStart(input);
                        String eventEnd = Parser.getEventEnd(input);
                        taskList.add(new Event(eventDesc, eventStart, eventEnd));
                        ui.showAdded(taskList.get(taskList.size() - 1), taskList.size());
                        storage.save(taskList);
                        break;
                    case "delete":
                        int taskId = Parser.getDeleteTaskNumber(input);
                        if (taskId <= 0 || taskId > taskList.size()) {
                            throw new InvalidInputException(
                                "The task number must be more than 0 and cannot be more than the number of tasks "
                                    + "in the list. Please try again.");
                        }
                        ui.showDeleted(taskList.get(taskId - 1), taskList.size() - 1);
                        taskList.remove(taskId - 1);
                        storage.save(taskList);
                        break;
                    case "on":
                        String dateString = Parser.getOnCommandDateText(input);
                        ui.listTasksOnDate(taskList, dateString);
                        break;
                    default:
                        throw new InvalidInputException("Yo! Invalid input bro, please try again!");
                }
            } catch (InvalidInputException e) {
                ui.showError(e);
            }
        }
        ui.close();
    }
}
