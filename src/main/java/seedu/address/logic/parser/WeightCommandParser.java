package seedu.address.logic.parser;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_WEIGHT;

import seedu.address.commons.core.index.Index;
import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.logic.commands.WeightCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.person.Weight;

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

        Index index;
        try {
            index = ParserUtil.parseIndex(argMultimap.getPreamble());
        } catch (IllegalValueException ive) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, WeightCommand.MESSAGE_USAGE), ive);
        }

        argMultimap.verifyNoDuplicatePrefixesFor(PREFIX_WEIGHT);

        if (argMultimap.getValue(PREFIX_WEIGHT).isPresent()) {
            return new WeightCommand(index, new Weight(Float.valueOf(argMultimap.getValue(PREFIX_WEIGHT).get())));
        }

        return new WeightCommand(index, new Weight(0f));
    }
}
