package seedu.address.logic.parser;

import static seedu.address.logic.commands.CommandTestUtil.NAME_DESC_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_NOTE_NOT_EMPTY;
import static seedu.address.logic.messages.Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;
import static seedu.address.testutil.TypicalIndexes.INDEX_SECOND_PERSON;

import org.junit.jupiter.api.Test;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.NoteCommand;
import seedu.address.logic.messages.Messages;
import seedu.address.logic.messages.NoteCommandMessages;
import seedu.address.model.person.Note;

public class NoteCommandParserTest {

    private NoteCommandParser parser = new NoteCommandParser();

    @Test
    public void parse_missingParts_failure() {
        // no index specified (note John)
        assertParseFailure(parser, VALID_NOTE_NOT_EMPTY,
                String.format(Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX,
                        NoteCommandMessages.MESSAGE_USAGE));

        // no index and no field specified (note)
        assertParseFailure(parser, "", String.format(Messages.MESSAGE_NO_INDEX,
                NoteCommandMessages.MESSAGE_USAGE));
    }

    @Test
    public void parse_invalidIndex_failure() {
        // negative index (note -5 n/John)
        assertParseFailure(parser, "-5" + NAME_DESC_AMY,
                String.format(MESSAGE_INVALID_PERSON_DISPLAYED_INDEX, NoteCommandMessages.MESSAGE_USAGE));

        // zero index (note 0 n/John)
        assertParseFailure(parser, "0" + NAME_DESC_AMY,
                String.format(MESSAGE_INVALID_PERSON_DISPLAYED_INDEX, NoteCommandMessages.MESSAGE_USAGE));

        // invalid index (note -5)
        assertParseFailure(parser, "-5",
                String.format(Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX,
                        NoteCommandMessages.MESSAGE_USAGE));
    }

    @Test
    public void parse_allFieldsSpecified_success() {
        Index targetIndex = INDEX_SECOND_PERSON;
        String userInput = targetIndex.getOneBased() + " " + VALID_NOTE_NOT_EMPTY;

        NoteCommand expectedCommand = new NoteCommand(targetIndex, new Note(VALID_NOTE_NOT_EMPTY));

        assertParseSuccess(parser, userInput, expectedCommand);
    }

    @Test
    public void parse_missingNote_success() {
        Index targetIndex = INDEX_SECOND_PERSON;
        String userInput = String.format("%s", targetIndex.getOneBased());

        NoteCommand expectedCommand = new NoteCommand(targetIndex, new Note(""));

        assertParseSuccess(parser, userInput, expectedCommand);
    }
}
