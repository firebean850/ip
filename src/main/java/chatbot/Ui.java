package chatbot;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Scanner;

/**
 * The Ui class handles user inputs and outputs any necessary messages.
 */
public class Ui {
    private Scanner scanner = new Scanner(System.in);
    private String line = "=".repeat(70) + "\n";
    private String banner = "\\ \\ / /| | | || \\ | |\n"
                            + " \\ V / | | | ||  \\| |\n"
                            + "  | |  | |_| || |\\  |\n"
                            + "  |_|   \\___/ |_| \\_|\n";

    /**
     * Prints the welcome message with banner.
     */
    public void showWelcomeMessage() {
        System.out.println(line + banner + "\nHello! I'm Yun.\nWhat can I do for you?\n" + line);
    }

    /**
     * Prints out a line.
     */
    public void printLine() {
        System.out.println(line);
    }

    /**
     * Prints out the exit message.
     */
    public void showExitMessage() {
        System.out.println("Bye. Hope to see you again soon!\n\n" + line);
    }

    /**
     * Reads the next input line by the user and returns it.
     * @return Input string by user.
     */
    public String readInputWithCommand() {
        return scanner.nextLine();
    }

    /**
     * Prints the error message and displays it to user.
     * @param e Exception encountered.
     */
    public void showError(Exception e) {
        System.out.println(e.getMessage() + "\n\n" + line);
    }

    /**
     * Lists all tasks in a given taskList.
     * @param taskList A given list of Tasks.
     */
    public void showTasks(TaskList taskList) {
        System.out.println("Here are the tasks in your list:\n");
        for (int i = 0; i < taskList.size(); i++) {
            System.out.println((i + 1) + "." + taskList.get(i).toString());
        }
        System.out.println("\n" + line);
    }

    /**
     * Lists tasks whose descriptions contain the given keyword, ignoring case.
     * @param taskList Tasks to search.
     * @param keyword Text to search for.
     */
    public void showMatchingTasks(TaskList taskList, String keyword) {
        System.out.println("Here are the matching tasks in your list:\n");
        int count = 1;
        String searchTerm = keyword.toLowerCase();
        for (Task task : taskList) {
            if (task.getTask().toLowerCase().contains(searchTerm)) {
                System.out.println(count + "." + task);
                count++;
            }
        }
        System.out.println("\n" + line);
    }

    /**
     * Prints out a message showing the task is marked successfully as completed.
     * @param task Task to be marked.
     */
    public void showMarked(Task task) {
        System.out.println("Nice! I've marked this task as done:\n" + task.toString()
            + "\n\n" + line);
    }

    /**
     * Prints out a message showing the task is unmarked successfully (i.e. not completed).
     * @param task Task to be unmarked
     */
    public void showUnmarked(Task task) {
        System.out.println("OK, I've marked this task as not done yet:\n"
            + task.toString() + "\n\n" + line);
    }

    /**
     * Indicate that a task has been successfully added to the list.
     * @param task The task to be added.
     * @param size Current number of tasks in list.
     */
    public void showAdded(Task task, int size) {
        System.out.println("Got it. I've added this task:\n"
            + task.toString()
            + "\nNow you have " + size + " task(s) in the list." + "\n\n" + line);
    }

    /**
     * Indicate that a task has been successfully deleted.
     * @param task Task to be deleted.
     * @param newSize New number of tasks in list.
     */
    public void showDeleted(Task task, int newSize) {
        System.out.println("Noted. I've removed this task:\n"
            + task.toString()
            + "\nNow you have " + newSize + " task(s) in the list." + "\n\n" + line);
    }

    /**
     * Lists all events and deadlines that occur on a specific date.
     * For events, only lists events that occur on start/end date.
     * @param taskList A given list of tasks.
     * @param dateText Specified date by user.
     */
    public void listTasksOnDate(TaskList taskList, String dateText) {
        try {
            LocalDate date = LocalDate.parse(dateText, DateTimeFormatter.ofPattern("yyyy-MM-dd"));
            DateTimeFormatter displayFormatter = DateTimeFormatter.ofPattern("dd MMM yyyy");
            System.out.println("Here are the list of events and deadlines occurring on "
                + date.format(displayFormatter) + ":");
            int count = 1;
            for (Task task : taskList) {
                if (task instanceof Deadline) {
                    Deadline deadline = (Deadline) task;
                    if (deadline.getDeadline().toLocalDate().equals(date)) {
                        System.out.println(count + "." + task);
                        count++;
                    }
                } else if (task instanceof Event) {
                    Event event = (Event) task;
                    if (event.getStart().toLocalDate().equals(date)
                        ||
                        event.getEnd().toLocalDate().equals(date)) {
                        System.out.println(count + "." + task);
                        count++;
                    }
                }

            }
            System.out.println("\n" + line);
        } catch (DateTimeParseException e) {
            throw new InvalidInputException("Please input a date in the format YYYY-MM-DD");
        }
    }

    /**
     * Closes the scanner.
     */
    public void close() {
        scanner.close();
    }
}
