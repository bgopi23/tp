package seedu.address.logic.parser;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.messages.WeightCommandMessages.MESSAGE_INVALID_PARAMETER_WEIGHT;
import static seedu.address.logic.messages.WeightCommandMessages.MESSAGE_NO_PARAMETER_WEIGHT;

import java.util.AbstractMap;
import java.util.Optional;

import seedu.address.commons.core.index.Index;
import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.logic.commands.WeightCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.person.weight.Weight;
import seedu.address.model.person.weight.WeightEntry;

/**
 * Parses input arguments and creates a new {@code WeightCommand} object
 */
public class WeightCommandParser implements Parser<WeightCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the {@code WeightCommand}
     * and returns a {@code WeightCommand} object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public WeightCommand parse(String args) throws ParseException {
        requireNonNull(args);

        if (args.trim().isEmpty()) {
            throw new ParseException(MESSAGE_NO_PARAMETER_WEIGHT);
        }

        String[] argsArray = args.trim().split(" ");

        Index index;
        try {
            index = ParserUtil.parseIndex(argsArray[0]);
        } catch (IllegalValueException ive) {
            throw new ParseException(MESSAGE_INVALID_PARAMETER_WEIGHT, ive);
        }

        // If there is no value specified for the weight, user is deleting the last added weight value.
        if (argsArray.length <= 1) {
            return new WeightCommand(index, new WeightEntry(new AbstractMap.SimpleEntry<>(
                    WeightEntry.getTimeOfExecution(), new Weight(0f))));
        }

        // Else, user is adding a new weight value.
        return new WeightCommand(index, new WeightEntry(new AbstractMap.SimpleEntry<>(
                WeightEntry.getTimeOfExecution(),
                ParserUtil.parseWeight(Optional.of(argsArray[1])))));
    }
}
