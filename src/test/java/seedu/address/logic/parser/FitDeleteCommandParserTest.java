package seedu.address.logic.parser;

import static seedu.address.logic.commands.CommandTestUtil.INVALID_EXERCISE_NAME_DESC;
import static seedu.address.logic.commands.CommandTestUtil.PREAMBLE_WHITESPACE;
import static seedu.address.logic.commands.CommandTestUtil.VALID_EXERCISE_NAME;
import static seedu.address.logic.commands.CommandTestUtil.VALID_EXERCISE_NAME_DESC;
import static seedu.address.logic.parser.CliSyntax.PREFIX_FITDELETE_DELETE_ALL;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_PERSON;

import java.util.Optional;

import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.FitDeleteCommand;
import seedu.address.logic.messages.FitDeleteCommandMessages;
import seedu.address.logic.messages.Messages;
import seedu.address.model.exercise.Exercise;

public class FitDeleteCommandParserTest {

    private FitDeleteCommandParser parser = new FitDeleteCommandParser();

    @Test
    public void parse_validArgs_returnsFitDeleteCommand() {
        // add specific exercise
        assertParseSuccess(this.parser, PREAMBLE_WHITESPACE + "1" + VALID_EXERCISE_NAME_DESC,
            new FitDeleteCommand(INDEX_FIRST_PERSON, Optional.of(VALID_EXERCISE_NAME.toLowerCase())));

        // delete all exercises
        assertParseSuccess(this.parser, PREAMBLE_WHITESPACE + "1" + " " + PREFIX_FITDELETE_DELETE_ALL,
            new FitDeleteCommand(INDEX_FIRST_PERSON, Optional.empty()));
    }

    @Test
    public void parse_invalidArgs_throwsParseException() {
        // invalid index
        assertParseFailure(this.parser, "a" + VALID_EXERCISE_NAME_DESC,
            FitDeleteCommandMessages.MESSAGE_INVALID_INDEX_FITDELETE);

        // invalid exercise name
        assertParseFailure(this.parser, "1" + INVALID_EXERCISE_NAME_DESC,
            Exercise.NAME_CONSTRAINT);
    }

    @Test
    public void parse_missingParts_failure() {
        // no index specified
        assertParseFailure(this.parser, VALID_EXERCISE_NAME_DESC,
            FitDeleteCommandMessages.MESSAGE_NO_INDEX_FITDELETE);

        // no field specified
        assertParseFailure(this.parser, "1",
            FitDeleteCommandMessages.MESSAGE_EXERCISE_NAME_PARAMETER_AND_ALL_PREFIX_MISSING);

        // no index and no field specified
        assertParseFailure(this.parser, "",
            FitDeleteCommandMessages.MESSAGE_NO_INDEX_FITDELETE);
    }

    @Test
    public void parse_invalidPreamble_failure() {
        // negative index
        assertParseFailure(this.parser, "-5" + VALID_EXERCISE_NAME_DESC,
            FitDeleteCommandMessages.MESSAGE_INVALID_INDEX_FITDELETE);

        // zero index
        assertParseFailure(this.parser, "0" + VALID_EXERCISE_NAME_DESC,
            FitDeleteCommandMessages.MESSAGE_INVALID_INDEX_FITDELETE);

        // invalid arguments being parsed as preamble
        assertParseFailure(this.parser, "1 some random string",
            String.format(Messages.MESSAGE_INVALID_COMMAND_FORMAT, FitDeleteCommandMessages.MESSAGE_USAGE));
    }

    @Test
    public void parse_concurrentPrefixes_throwsParseException() {
        // both exercise name and delete all prefixes present
        assertParseFailure(this.parser, "1" + VALID_EXERCISE_NAME_DESC + " " + PREFIX_FITDELETE_DELETE_ALL,
            FitDeleteCommandMessages.MESSAGE_CONCURRENT_PREFIX);
    }

    @Test
    public void parse_prefixesMissing_throwsParseException() {
        // both exercise name and delete all prefixes missing
        assertParseFailure(this.parser, "1",
            FitDeleteCommandMessages.MESSAGE_EXERCISE_NAME_PARAMETER_AND_ALL_PREFIX_MISSING);
    }
}
