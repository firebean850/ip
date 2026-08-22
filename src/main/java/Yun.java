import java.util.Scanner;


public class Yun {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        String line = "=".repeat(60) + "\n";

        String banner = "\\ \\ / /| | | || \\ | |\n"
              + " \\ V / | | | ||  \\| |\n"
              + "  | |  | |_| || |\\  |\n"
              + "  |_|   \\___/ |_| \\_|\n";
        String intro = line + banner + "\nHello! I'm Yun.\nWhat can I do for you?\n" + line;
        System.out.println(intro);
        String exitMessage = "Bye. Hope to see you again soon!\n";
        while (true) {
            String command = scanner.nextLine();
            System.out.println(line);
            if (command.equals("bye") || command.equals("Bye")) {
                System.out.println(exitMessage + "\n" + line);
                break;
            } else {
                System.out.println(command + "\n\n" + line);
            }
        }
        scanner.close();
    }
}
