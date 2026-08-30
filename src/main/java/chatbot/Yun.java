package chatbot;

import java.io.IOException;
import java.io.FileWriter;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.stream.Stream;
import java.util.Scanner;

/**
 * Yun is an interactive chatbot.
 */
public class Yun {
    private static final DateTimeFormatter FILE_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HHmm");


    /**
     * Syncs the file from harddisk into the taskList.
     * @param taskList The taskList array to be modified.
     */
    public static void syncFileToList(ArrayList<Task> taskList) {
        if (Files.exists(Paths.get("taskList.txt"))) {
            try (Stream<String> allLines = Files.lines(Paths.get("taskList.txt"))) {
                String[] lines = allLines.toArray(String[]::new);
                for (int i = 0; i < lines.length; i++) {
                    String[] parts = lines[i].split("\\|");
                    if (parts[0].equals("T")){
                        taskList.add(new Todo(parts[2]));
                    } else if (parts[0].equals("D")) {
                        taskList.add(new Deadline(parts[2], parts[3]));
                    } else {
                        taskList.add(new Event(parts[2], parts[3], parts[4]));
                    }
                    if (parts[1].equals( "[X]")) {
                            taskList.get(i).markComplete();
                    }
                }

            } catch (IOException e){
                System.out.println("The chatbot has encountered an error. Please try again later.");
            };
        }
    }

    /**
     * Syncs from the tasklist array into a file. Creates a file if there is no taskList.txt file yet.
     * @param taskList The created tasklist.
     */
    public static void syncListToFile(ArrayList<Task> taskList){
        try (FileWriter writer = new FileWriter("taskList.txt", false)){
            for (int i = 0; i < taskList.size(); i++) {
                Task curr = taskList.get(i);
                if (curr instanceof Todo) {
                    writer.write("T|" + curr.getCompletionStatus() + "|" + curr.getTask());
                } else if (curr instanceof Event) {
                    Event currEvent = (Event) curr;
                    writer.write("E|" + currEvent.getCompletionStatus() + "|" + currEvent.getTask()
                        + "|" + currEvent.getStart().format(FILE_FORMATTER) + "|" 
                        + currEvent.getEnd().format(FILE_FORMATTER));
                } else {
                    Deadline currDeadline = (Deadline) curr;
                    writer.write("D|" + currDeadline.getCompletionStatus() + "|" + currDeadline.getTask()
                        + "|" + currDeadline.getDeadline().format(FILE_FORMATTER));
                }
                writer.write(System.lineSeparator());
            }
        } catch (IOException e) {
            System.out.println("Failed to save tasks.");
        }
    }

    public static void listTasksOnDate(ArrayList<Task> taskList, String dateText) {
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
                    if (event.getStart().toLocalDate().equals(date) || 
                        event.getEnd().toLocalDate().equals(date)) {
                        System.out.println(count + "." + task);
                        count++;
                    }
                }

            }
        } catch (DateTimeParseException e) {
            throw new InvalidInputException("Please input a date in the format YYYY-MM-DD");
        }
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        String line = "=".repeat(70) + "\n";
        ArrayList<Task> taskList = new ArrayList<>();
        syncFileToList(taskList);
        String banner = "\\ \\ / /| | | || \\ | |\n"
              + " \\ V / | | | ||  \\| |\n"
              + "  | |  | |_| || |\\  |\n"
              + "  |_|   \\___/ |_| \\_|\n";

        String intro = line + banner + "\nHello! I'm Yun.\nWhat can I do for you?\n" + line;
        System.out.println(intro);
        String exitMessage = "Bye. Hope to see you again soon!\n";
        boolean running = true;
        while (running) {
            try {
                String input = scanner.nextLine();
                String[] parsedInput = input.split(" ", 2);
                String command = parsedInput[0];
                System.out.println(line);
                switch (command) {
                    case "bye":
                    case "Bye":
                        System.out.println(exitMessage + "\n" + line);
                        syncListToFile(taskList);
                        running = false;
                        break;
                    case "list":
                        System.out.println("Here are the tasks in your list:\n");
                        for (int i = 0; i < taskList.size(); i++) {
                            System.out.println((i+1) + "." + taskList.get(i).toString());
                        }
                        System.out.println("\n" + line);
                        break;
                    case "mark":
                        int taskNumber;
                        if (parsedInput.length < 2 || parsedInput[1].trim().isEmpty()) 
                            throw new InvalidInputException("Please key in a number after the mark command.");
                        try {
                            taskNumber = Integer.parseInt(parsedInput[1]);
                        } catch (NumberFormatException e) {
                            throw new InvalidInputException("Please key in a number after the mark command.");
                        }
                        if (taskNumber > taskList.size() || taskNumber <= 0) {
                            throw new InvalidInputException("Sorry, the task number you entered was invalid. Please try again.");
                        }
                        taskList.get(taskNumber-1).markComplete();
                        System.out.println("Nice! I've marked this task as done:\n" + taskList.get(taskNumber-1).toString() + 
                            "\n\n" + line);
                        syncListToFile(taskList);
                        break;
                    case "unmark":
                        int taskNo;
                        if (parsedInput.length < 2 || parsedInput[1].trim().isEmpty()) 
                            throw new InvalidInputException("Please key in a number after the unmark command.");
                        try {
                            taskNo = Integer.parseInt(parsedInput[1]);
                        } catch (NumberFormatException e) {
                            throw new InvalidInputException("Please key in a number after the unmark command.");
                        }
                        if (taskNo > taskList.size() || taskNo <= 0) {
                            throw new InvalidInputException("Sorry, the task number you entered was invalid.");
                        }
                        taskList.get(taskNo-1).markIncomplete();
                        System.out.println("OK, I've marked this task as not done yet:\n" + 
                            taskList.get(taskNo-1).toString() + "\n\n" + line);
                        syncListToFile(taskList);
                        break;
                    case "todo":
                        if (parsedInput.length < 2 || parsedInput[1].trim().isEmpty()) {
                            throw new InvalidInputException("Heyo! The description of a todo cannot be empty. Please try again.");
                        }
                        taskList.add(new Todo(input.split("todo ")[1].trim()));
                        System.out.println("Got it. I've added this task:\n" + 
                            taskList.get(taskList.size()-1).toString() + 
                            "\nNow you have " + taskList.size() + " task(s) in the list." + "\n\n" + line);
                        syncListToFile(taskList);
                        break;
                    case "deadline":
                        if (!input.contains("/by")) {
                            throw new InvalidInputException("Sorry, please provide a due date for the deadline command in this format:\n" + 
                                "deadline <task desc> /by <due date> without angular brackets.");
                        }
                        taskList.add(new Deadline(input.split("deadline ")[1].trim()));
                        System.out.println("Got it. I've added this task:\n" + 
                            taskList.get(taskList.size()-1).toString() + 
                            "\nNow you have " + taskList.size() + " task(s) in the list." + "\n\n" + line);
                        syncListToFile(taskList);
                        break;
                    case "event":
                        if (!input.contains("/from") || !input.contains("/to") ) {
                            throw new InvalidInputException("Sorry, please provide a start and end date/time for the event command in this format:\n" + 
                                "event <task desc> /from <start> /to <end> without angular brackets.");
                        }
                        taskList.add(new Event(input.split("event ")[1].trim()));
                        System.out.println("Got it. I've added this task:\n" + 
                            taskList.get(taskList.size()-1).toString() + 
                            "\nNow you have " + taskList.size() + " task(s) in the list." + "\n\n" + line);
                        syncListToFile(taskList);
                        break;
                    case "delete": 
                        int taskId;
                        if (parsedInput.length < 2 || parsedInput[1].trim().isEmpty()) {
                            throw new InvalidInputException("Heyo! The description of a delete cannot be empty, and/or the task number must be more than 0." + 
                                "Please try again, including the task number of the task you want to delete after the delete command");
                        } else try {
                            taskId = Integer.parseInt(parsedInput[1].trim());
                            if (taskId <= 0 || taskId > taskList.size()) {
                                throw new InvalidInputException("The task number must be more than 0 and cannot be more than the number of tasks in the list. Please try again.");
                            }
                        } catch (NumberFormatException e) {
                            throw new InvalidInputException("HEY! Please key in a number after the delete command.");
                        }   
                        System.out.println("Noted. I've removed this task:\n" + 
                            taskList.get(taskId-1).toString() + 
                            "\nNow you have " + (taskList.size()-1) + " task(s) in the list." + "\n\n" + line);
                        taskList.remove(taskId-1);
                        syncListToFile(taskList);
                        break;
                    case "on":
                        if (parsedInput.length < 2) {
                            throw new InvalidInputException("Please provide a date in the format YYYY-MM-DD");
                        }
                        listTasksOnDate(taskList, parsedInput[1].trim());
                        System.out.println("\n" + line);
                        break;

                    default:
                        throw new InvalidInputException("Yo! Invalid input bro, please try again!");
                }
            } catch (InvalidInputException e) {
                System.out.println(e.getMessage() + "\n\n" + line);
            }
        }
        scanner.close();
    }
}
