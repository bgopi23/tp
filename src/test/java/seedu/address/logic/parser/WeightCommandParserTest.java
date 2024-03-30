package seedu.address.logic.parser;

import static seedu.address.logic.commands.CommandTestUtil.VALID_WEIGHT_AMY;
import static seedu.address.logic.messages.Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;

import org.junit.jupiter.api.Test;

import seedu.address.logic.messages.Messages;
import seedu.address.logic.messages.WeightCommandMessages;

public class WeightCommandParserTest {

    private WeightCommandParser parser = new WeightCommandParser();

    @Test
    public void parse_missingParts_failure() {
        assertParseFailure(parser, "", String.format(Messages.MESSAGE_NO_INDEX,
                WeightCommandMessages.MESSAGE_USAGE));
    }

    @Test
    public void parse_invalidIndex_failure() {
        // negative index
        assertParseFailure(parser, "-5" + VALID_WEIGHT_AMY.toString(),
                String.format(MESSAGE_INVALID_PERSON_DISPLAYED_INDEX, WeightCommandMessages.MESSAGE_USAGE));

        // zero index
        assertParseFailure(parser, "0" + VALID_WEIGHT_AMY.toString(),
                String.format(MESSAGE_INVALID_PERSON_DISPLAYED_INDEX, WeightCommandMessages.MESSAGE_USAGE));

        // invalid index
        assertParseFailure(parser, "-5",
                String.format(Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX,
                        WeightCommandMessages.MESSAGE_USAGE));
    }
}
