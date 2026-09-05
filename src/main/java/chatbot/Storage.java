package chatbot;

import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.format.DateTimeFormatter;
import java.util.stream.Stream;

/**
 * The Storage class handles syncing of data to enable saves of task data
 * onto the hardDisk whenever there are changes to the taskList, and loads
 * existing taskData whenever a new instance of Yun is created.
 */
public class Storage {
    private static final DateTimeFormatter FILE_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HHmm");
    private final String filePath;

    /**
     * Creates a Storage object with a specified filePath of file used to store data of tasks.
     * @param filePath
     */
    public Storage(String filePath) {
        this.filePath = filePath;
    }

    /**
     * Syncs the file from harddisk into the taskList.
     */
    public TaskList load() {
        TaskList taskList = new TaskList();
        if (Files.exists(Paths.get(filePath))) {
            try (Stream<String> allLines = Files.lines(Paths.get(filePath))) {
                String[] lines = allLines.toArray(String[]::new);
                for (int i = 0; i < lines.length; i++) {
                    String[] parts = lines[i].split("\\|");
                    if (parts[0].equals("T")) {
                        taskList.add(new Todo(parts[2]));
                    } else if (parts[0].equals("D")) {
                        taskList.add(new Deadline(parts[2], parts[3]));
                    } else {
                        taskList.add(new Event(parts[2], parts[3], parts[4]));
                    }
                    if (parts[1].equals("[X]")) {
                        taskList.get(i).markComplete();
                    }
                }
            } catch (IOException e) {
                System.out.println("The chatbot has encountered an error. Please try again later.");
            }
        }
        return taskList;
    }

    /**
     * Syncs from the tasklist array into a file. Creates a file if there is file with given filepath yet.
     * @param taskList The created tasklist.
     */
    public void save(TaskList taskList) {
        try (FileWriter writer = new FileWriter(filePath, false)) {
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

}
