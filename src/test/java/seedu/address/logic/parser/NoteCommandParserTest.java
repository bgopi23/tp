package seedu.address.logic.parser;

import static seedu.address.logic.commands.CommandTestUtil.EDIT_TAG_LEADING_WHITESPACE;
import static seedu.address.logic.commands.CommandTestUtil.EDIT_TAG_RANDOM_CASE;
import static seedu.address.logic.commands.CommandTestUtil.EDIT_TAG_TRAILING_WHITESPACE;
import static seedu.address.logic.commands.CommandTestUtil.EDIT_TAG_WITH_MORE_WORDS;
import static seedu.address.logic.commands.CommandTestUtil.NAME_DESC_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_NOTE_NOT_EMPTY;
import static seedu.address.logic.messages.Messages.MESSAGE_INVALID_INDEX;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_PERSON;
import static seedu.address.testutil.TypicalIndexes.INDEX_SECOND_PERSON;

import org.junit.jupiter.api.Test;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.NoteCommand;
import seedu.address.logic.commands.NoteEditCommand;
import seedu.address.logic.messages.Messages;
import seedu.address.logic.messages.NoteCommandMessages;
import seedu.address.model.person.Note;

public class NoteCommandParserTest {

    private NoteCommandParser parser = new NoteCommandParser();

    @Test
    public void parse_missingParts_failure() {
        // no index specified (note John)
        assertParseFailure(this.parser, VALID_NOTE_NOT_EMPTY,
                String.format(Messages.MESSAGE_INVALID_INDEX,
                        NoteCommandMessages.MESSAGE_USAGE));

        // no index and no field specified (note)
        assertParseFailure(this.parser, "", String.format(Messages.MESSAGE_NO_INDEX,
                NoteCommandMessages.MESSAGE_USAGE));
    }

    @Test
    public void parse_invalidIndex_failure() {
        // negative index (note -5 n/John)
        assertParseFailure(this.parser, "-5" + NAME_DESC_AMY,
                String.format(MESSAGE_INVALID_INDEX, NoteCommandMessages.MESSAGE_USAGE));

        // zero index (note 0 n/John)
        assertParseFailure(this.parser, "0" + NAME_DESC_AMY,
                String.format(MESSAGE_INVALID_INDEX, NoteCommandMessages.MESSAGE_USAGE));

        // invalid index (note -5)
        assertParseFailure(this.parser, "-5",
                String.format(Messages.MESSAGE_INVALID_INDEX,
                        NoteCommandMessages.MESSAGE_USAGE));
    }

    @Test
    public void parse_allFieldsSpecified_success() {
        Index targetIndex = INDEX_SECOND_PERSON;
        String userInput = targetIndex.getOneBased() + " " + VALID_NOTE_NOT_EMPTY;

        NoteCommand expectedCommand = new NoteCommand(targetIndex, new Note(VALID_NOTE_NOT_EMPTY));

        assertParseSuccess(this.parser, userInput, expectedCommand);
    }

    @Test
    public void parse_missingNote_success() {
        Index targetIndex = INDEX_SECOND_PERSON;
        String userInput = String.format("%s", targetIndex.getOneBased());

        NoteCommand expectedCommand = new NoteCommand(targetIndex, new Note(""));

        assertParseSuccess(this.parser, userInput, expectedCommand);
    }

    @Test
    public void parse_noteEditRandomCase_success() {
        Index targetIndex = INDEX_FIRST_PERSON;
        String userInput = targetIndex.getOneBased() + " " + EDIT_TAG_RANDOM_CASE;
        NoteCommand expectedCommand = new NoteEditCommand(targetIndex);
        assertParseSuccess(this.parser, userInput, expectedCommand);
    }

    // (note 1 /edit moreText)
    @Test
    public void parse_noteEditWithOtherText_success() {
        Index targetIndex = INDEX_FIRST_PERSON;
        String userInput = targetIndex.getOneBased() + " " + EDIT_TAG_WITH_MORE_WORDS;
        NoteCommand expectedCommand = new NoteCommand(targetIndex, new Note(EDIT_TAG_WITH_MORE_WORDS));
        assertParseSuccess(this.parser, userInput, expectedCommand);
    }

    @Test
    public void parse_noteEditLeadingWhitespace_success() {
        Index targetIndex = INDEX_FIRST_PERSON;
        String userInput = targetIndex.getOneBased() + " " + EDIT_TAG_LEADING_WHITESPACE;
        NoteCommand expectedCommand = new NoteEditCommand(targetIndex);
        assertParseSuccess(this.parser, userInput, expectedCommand);
    }

    @Test
    public void parse_noteEditTrailingWhitespace_success() {
        Index targetIndex = INDEX_FIRST_PERSON;
        String userInput = targetIndex.getOneBased() + " " + EDIT_TAG_TRAILING_WHITESPACE;
        NoteCommand expectedCommand = new NoteEditCommand(targetIndex);
        assertParseSuccess(this.parser, userInput, expectedCommand);
    }
}
