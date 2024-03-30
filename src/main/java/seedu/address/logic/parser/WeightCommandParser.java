package seedu.address.logic.parser;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.messages.Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX;
import static seedu.address.logic.parser.CliSyntax.PREFIX_WEIGHT;

import java.util.AbstractMap;

import seedu.address.commons.core.index.Index;
import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.logic.commands.WeightCommand;
import seedu.address.logic.messages.Messages;
import seedu.address.logic.messages.WeightCommandMessages;
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

        ArgumentMultimap argMultimap = ArgumentTokenizer.tokenize(args, PREFIX_WEIGHT);

        if (args.isEmpty() || argMultimap.getPreamble().isEmpty()) {
            throw new ParseException(
                    String.format(Messages.MESSAGE_NO_INDEX, WeightCommandMessages.MESSAGE_USAGE));
        }

        Index index;
        try {
            index = ParserUtil.parseIndex(argMultimap.getPreamble());
        } catch (IllegalValueException ive) {
            throw new ParseException(String.format(MESSAGE_INVALID_PERSON_DISPLAYED_INDEX,
                    WeightCommandMessages.MESSAGE_USAGE), ive);
        }

        argMultimap.verifyNoDuplicatePrefixesFor(PREFIX_WEIGHT);

        // If there is no value specified for the weight, user is deleting the last added weight value.
        if (argMultimap.getValue(PREFIX_WEIGHT).get().isEmpty()) {
            return new WeightCommand(index, new WeightEntry(new AbstractMap.SimpleEntry<>(
                    WeightEntry.getTimeOfExecution(), new Weight(0f))));
        }

        return new WeightCommand(index, new WeightEntry(new AbstractMap.SimpleEntry<>(
                WeightEntry.getTimeOfExecution(),
                new Weight(Float.valueOf(argMultimap.getValue(PREFIX_WEIGHT).get())))));
    }
}
