package chatbot;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

/**
 * The Ui class handles user inputs and outputs any necessary messages.
 */
public class Ui {
    private String banner = "\\ \\ / /| | | || \\ | |\n"
                            + " \\ V / | | | ||  \\| |\n"
                            + "  | |  | |_| || |\\  |\n"
                            + "  |_|   \\___/ |_| \\_|\n";

    /**
     * Prints the welcome message with banner.
     */
    public String showWelcomeMessage() {
        return banner + "\nHello! I'm Yun.\nWhat can I do for you?\n";
    }

    /**
     * Prints out the exit message.
     */
    public String showExitMessage() {
        return "Bye. Hope to see you again soon!\n\n";
    }

    /**
     * Prints the error message and displays it to user.
     * @param e Exception encountered.
     */
    public String showError(Exception e) {
        return e.getMessage();
    }

    /**
     * Lists all tasks in a given taskList.
     * @param taskList A given list of Tasks.
     */
    public String showTasks(TaskList taskList) {
        String result = "Here are the tasks in your list:\n";
        for (int i = 0; i < taskList.size(); i++) {
            result += (Integer.toString(i + 1) + "." + taskList.get(i).toString() + "\n");
        }
        return result + "\n";
    }

    /**
     * Lists tasks whose descriptions contain the given keyword, ignoring case.
     * @param taskList Tasks to search.
     * @param keyword Text to search for.
     */
    public String showMatchingTasks(TaskList taskList, String keyword) {
        String result = "Here are the matching tasks in your list:\n";
        int count = 1;
        String searchTerm = keyword.toLowerCase();
        for (Task task : taskList) {
            if (task.getTask().toLowerCase().contains(searchTerm)) {
                result += (Integer.toString(count) + "." + task + "\n");
                count++;
            }
        }
        return result + "\n";
    }

    /**
     * Prints out a message showing the task is marked successfully as completed.
     * @param task Task to be marked.
     */
    public String showMarked(Task task) {
        return "Nice! I've marked this task as done:\n" + task.toString()
            + "\n\n";
    }

    /**
     * Prints out a message showing the task is unmarked successfully (i.e. not completed).
     * @param task Task to be unmarked
     */
    public String showUnmarked(Task task) {
        return "OK, I've marked this task as not done yet:\n"
            + task.toString() + "\n\n";
    }

    /**
     * Indicate that a task has been successfully added to the list.
     * @param task The task to be added.
     * @param size Current number of tasks in list.
     */
    public String showAdded(Task task, int size) {
        return "Got it. I've added this task:\n"
            + task.toString()
            + "\nNow you have " + size + " task(s) in the list." + "\n\n";
    }

    /**
     * Indicate that a task has been successfully deleted.
     * @param task Task to be deleted.
     * @param newSize New number of tasks in list.
     */
    public String showDeleted(Task task, int newSize) {
        return "Noted. I've removed this task:\n"
            + task.toString()
            + "\nNow you have " + newSize + " task(s) in the list." + "\n\n";
    }

    /**
     * Lists all events and deadlines that occur on a specific date.
     * For events, only lists events that occur on start/end date.
     * @param taskList A given list of tasks.
     * @param dateText Specified date by user.
     */
    public String listTasksOnDate(TaskList taskList, String dateText) {
        try {
            LocalDate date = LocalDate.parse(dateText, DateTimeFormatter.ofPattern("yyyy-MM-dd"));
            DateTimeFormatter displayFormatter = DateTimeFormatter.ofPattern("dd MMM yyyy");
            String result = "Here are the list of events and deadlines occurring on "
                + date.format(displayFormatter) + ":\n";
            int count = 1;
            for (Task task : taskList) {
                if (task instanceof Deadline) {
                    Deadline deadline = (Deadline) task;
                    if (deadline.getDeadline().toLocalDate().equals(date)) {
                        result += count + "." + task + "\n";
                        count++;
                    }
                } else if (task instanceof Event) {
                    Event event = (Event) task;
                    if (event.getStart().toLocalDate().equals(date)
                        ||
                        event.getEnd().toLocalDate().equals(date)) {
                        result += count + "." + task + "\n";
                        count++;
                    }
                }

            }
            return result + "\n";
        } catch (DateTimeParseException e) {
            throw new InvalidInputException("Please input a date in the format YYYY-MM-DD");
        }
    }
}
