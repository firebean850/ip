import java.util.Scanner;


public class Yun {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        String line = "=".repeat(60) + "\n";
        String[] tasklist = new String[100];
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
            String command = scanner.nextLine();
            System.out.println(line);
            switch (command) {
                case "bye":
                case "Bye":
                    System.out.println(exitMessage + "\n" + line);
                    running = false;
                    break;
                case "list":
                    for (int i = 0; i < count; i++) {
                        System.out.printf("%d. %s%n", i+1, tasklist[i]);
                    }
                    System.out.println("\n" + line);
                    break;
                default:
                    tasklist[count++] = command;
                    System.out.println("added: " + command + "\n\n" + line);

            }
        }
        scanner.close();
    }
}
