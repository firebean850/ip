package chatbot;

import java.util.ArrayList;
import java.util.Iterator;

/**
 * The TaskList class is a wrapper class that manages the list of Tasks 
 */
public class TaskList implements Iterable<Task> {
    private final ArrayList<Task> tasks = new ArrayList<>();

    /**
     * Adds a task to the list.
     * @param task
     */
    public void add(Task task) {
        tasks.add(task);
    }

    /** 
     * Removes specified task from list.
     * @param index Task index of task to be removed.
     */
    public void remove(int index) {
        tasks.remove(index);
    }

    /**
     * Retrieve task from list at specified index.
     * @param index Index of task to be retrieved.
     * @return Task to be retrieved.
     */
    public Task get(int index) {
        return tasks.get(index);
    }

    /**
     * Returns number of tasks in list.
     * @return Number of tasks in list.
     */
    public int size() {
        return tasks.size();
    }

    @Override
    public Iterator<Task> iterator() {
        return tasks.iterator();
    }
    
}
