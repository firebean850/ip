package chatbot;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

/** Tests user-facing message formatting and task searches. */
class UiTest {
    private final Ui ui = new Ui();

    @Test
    void messages_includeExpectedWelcomeHelpAndExitContent() {
        assertTrue(ui.showWelcomeMessage().contains("Hello! I'm Yun."));
        assertTrue(ui.showHelp().contains("todo, event, deadline"));
        assertEquals("Buh bai. Cya again soon!\n\n", ui.showExitMessage());
    }

    @Test
    void showTasksAndFind_formatAllAndMatchingTasks() {
        TaskList tasks = new TaskList();
        tasks.add(new Todo("Read Book"));
        tasks.add(new Deadline("submit report", "2026-09-01 1800"));

        assertTrue(ui.showTasks(tasks).contains("1.[T] [ ] Read Book"));
        String matches = ui.showMatchingTasks(tasks, "book");
        assertTrue(matches.contains("1.[T] [ ] Read Book"));
        assertTrue(!matches.contains("submit report"));
    }

    @Test
    void listTasksOnDate_includesDeadlinesAndEventEndpointsOnly() {
        TaskList tasks = new TaskList();
        tasks.add(new Todo("ordinary task"));
        tasks.add(new Deadline("deadline", "2026-09-01 1800"));
        tasks.add(new Event("cross-day event", "2026-08-31 2300", "2026-09-01 0100"));

        String result = ui.listTasksOnDate(tasks, "2026-09-01");
        assertTrue(result.contains("deadline"));
        assertTrue(result.contains("cross-day event"));
        assertTrue(!result.contains("ordinary task"));
    }

    @Test
    void listTasksOnDate_rejectsInvalidDate() {
        assertThrows(InvalidInputException.class, () -> ui.listTasksOnDate(new TaskList(), "01-09-2026"));
    }
}
