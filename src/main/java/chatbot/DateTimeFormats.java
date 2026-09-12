package chatbot;

import java.time.format.DateTimeFormatter;

/**
 * Provides shared date and time formats for the chatbot.
 */
final class DateTimeFormats {
    static final DateTimeFormatter STORAGE =
        DateTimeFormatter.ofPattern("yyyy-MM-dd HHmm");

    static final DateTimeFormatter DISPLAY_DATE_TIME =
        DateTimeFormatter.ofPattern("dd MMM yyyy h:mm a");

    static final DateTimeFormatter DATE =
        DateTimeFormatter.ofPattern("yyyy-MM-dd");

    static final DateTimeFormatter DISPLAY_DATE =
        DateTimeFormatter.ofPattern("dd MMM yyyy");

    static final String INPUT_FORMAT = "yyyy-MM-dd HHmm";

    private DateTimeFormats() {
        // Prevent instantiation.
    }

}
