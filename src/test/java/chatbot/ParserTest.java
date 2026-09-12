package chatbot;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

/**
 * Tests parsing of user commands.
 */
class ParserTest {

    /**
     * A find command should return the keyword after the command.
     */
    @Test
    void getFindKeyword_validCommand_returnsTrimmedKeyword() {
        assertEquals("book", Parser.getFindKeyword("find book"));
        assertEquals("project notes", Parser.getFindKeyword("find   project notes"));
    }

    /**
     * A find command without a keyword should be rejected.
     */
    @Test
    void getFindKeyword_missingKeyword_throwsInvalidInputException() {
        assertThrows(InvalidInputException.class, () -> Parser.getFindKeyword("find"));
        assertThrows(InvalidInputException.class, () -> Parser.getFindKeyword("find   "));
    }

    /**
     * Null and blank input should be rejected by the parser.
     */
    @Test
    void parser_nullOrBlankInput_throwsInvalidInputException() {
        assertThrows(InvalidInputException.class, () -> Parser.getCommand(null));
        assertThrows(InvalidInputException.class, () -> Parser.getCommand("   "));
    }

    /**
     * Commands should tolerate leading and repeated whitespace.
     */
    @Test
    void parser_extraWhitespace_extractsArgumentsCorrectly() {
        assertEquals("buy milk", Parser.getTodoDescription("  todo   buy milk  "));
        assertEquals("2026-09-25 0830", Parser.getDeadline("deadline task /by 2026-09-25 0830"));
        assertEquals("2026-09-25 0830", Parser.getEventStart(
            "  event meeting /from 2026-09-25 0830 /to 2026-09-25 0930"));
    }
}
