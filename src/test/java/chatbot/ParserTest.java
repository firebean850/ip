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
}
