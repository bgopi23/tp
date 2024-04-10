package seedu.address.logic.parser;

import static seedu.address.logic.commands.CommandTestUtil.INVALID_WEIGHT_NEGATIVE;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_WEIGHT_OVER_LIMIT;
import static seedu.address.logic.commands.CommandTestUtil.VALID_WEIGHT_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_WEIGHT_BOB;
import static seedu.address.logic.messages.WeightCommandMessages.MESSAGE_INVALID_INDEX_WEIGHT;
import static seedu.address.logic.messages.WeightCommandMessages.MESSAGE_INVALID_PARAMETER_WEIGHT;
import static seedu.address.logic.messages.WeightCommandMessages.MESSAGE_NO_PARAMETER_WEIGHT;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.model.person.weight.Weight.MESSAGE_CONSTRAINTS;

import org.junit.jupiter.api.Test;

public class WeightCommandParserTest {

    private WeightCommandParser parser = new WeightCommandParser();

    @Test
    public void parse_missingParts_failure() {
        assertParseFailure(this.parser, VALID_WEIGHT_BOB.toString(),
                MESSAGE_INVALID_PARAMETER_WEIGHT);

        // no index and no field specified (note)
        assertParseFailure(this.parser, "", MESSAGE_NO_PARAMETER_WEIGHT);
    }

    @Test
    public void parse_invalidIndex_failure() {
        assertParseFailure(this.parser, "-5" + VALID_WEIGHT_AMY,
                MESSAGE_INVALID_INDEX_WEIGHT);

        assertParseFailure(this.parser, "0" + VALID_WEIGHT_BOB,
                MESSAGE_INVALID_INDEX_WEIGHT);

        assertParseFailure(this.parser, "-5",
                MESSAGE_INVALID_INDEX_WEIGHT);
    }

    @Test
    public void parse_invalidWeight_failure() {
        assertParseFailure(this.parser, "1" + INVALID_WEIGHT_OVER_LIMIT, MESSAGE_CONSTRAINTS);

        assertParseFailure(this.parser, "1" + INVALID_WEIGHT_NEGATIVE, MESSAGE_CONSTRAINTS);
    }
}
