import java.util.Scanner;


public class Yun {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        String line = "=".repeat(60) + "\n";
        Task[] taskList = new Task[100];
        int count = 0;

        String banner = "\\ \\ / /| | | || \\ | |\n"
              + " \\ V / | | | ||  \\| |\n"
              + "  | |  | |_| || |\\  |\n"
              + "  |_|   \\___/ |_| \\_|\n";

        String intro = line + banner + "\nHello! I'm Yun.\nWhat can I do for you?\n" + line;
        System.out.println(intro);
        String exitMessage = "Bye. Hope to see you again soon!\n";
        boolean running = true;
        while (running) {
            String input = scanner.nextLine();
            String[] parsedInput = input.split(" ", 2);
            String command = parsedInput[0];
            System.out.println(line);
            switch (command) {
                case "bye":
                case "Bye":
                    System.out.println(exitMessage + "\n" + line);
                    running = false;
                    break;
                case "list":
                    System.out.println("Here are the tasks in your list:\n");
                    for (int i = 0; i < count; i++) {
                        System.out.println((i+1) + "." + taskList[i].toString());
                    }
                    System.out.println("\n" + line);
                    break;
                case "mark":
                    int taskNumber = Integer.parseInt(parsedInput[1]);
                    if (taskNumber > count || taskNumber < 0) {
                        System.out.println("Sorry, the task number you entered was invalid. Please try again.\n" + line);
                        break;
                    }
                    taskList[taskNumber-1].markComplete();
                    System.out.println("Nice! I've marked this task as done:\n\n" + taskList[taskNumber-1].toString() + 
                        "\n" + line);
                    break;
                case "unmark":
                    int taskNo = Integer.parseInt(parsedInput[1]);
                    if (taskNo > count || taskNo < 0) {
                        System.out.println("Sorry, the task number you entered was invalid.");
                        break;
                    }
                    taskList[taskNo-1].markIncomplete();
                    System.out.println("OK, I've marked this task as not done yet:\n\n" + 
                        taskList[taskNo-1].toString() + "\n" + line);
                    break;
                case "todo":
                    taskList[count++] = new Todo(input.split("todo ")[1].trim());
                    System.out.println("Got it. I've added this task:\n" + 
                        taskList[count-1].toString() + 
                        "\nNow you have " + count + " tasks in the list." + "\n" + line);
                    break;
                case "deadline":
                    if (!input.contains("/by")) {
                        System.out.println("Sorry, please provide a due date for the deadline command in this format:\n" + 
                            "deadline <task desc> /by <due date> without angular brackets.");
                        break;
                    }
                    taskList[count++] = new Deadline(input.split("deadline ")[1].trim());
                    System.out.println("Got it. I've added this task:\n" + 
                        taskList[count-1].toString() + 
                        "\nNow you have " + count + " tasks in the list." + "\n" + line);
                    break;
                case "event":
                    if (!input.contains("/from") || !input.contains("/to") ) {
                        System.out.println("Sorry, please provide a start and end date/time for the event command in this format:\n" + 
                            "event <task desc> /from <start> /to <end> without angular brackets.");
                        break;
                    }
                    taskList[count++] = new Event(input.split("event ")[1].trim());
                    System.out.println("Got it. I've added this task:\n" + 
                        taskList[count-1].toString() + 
                        "\nNow you have " + count + " tasks in the list." + "\n" + line);
                    break;
                default:
                    taskList[count++] = new Task(input);
                    System.out.println("added: " + input + "\n\n" + line);

            }
        }
        scanner.close();
    }
}
