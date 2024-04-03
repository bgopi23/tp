package seedu.address.logic.parser;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_EXERCISE_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_FITDELETE_DELETE_ALL;

import java.util.Optional;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.FitDeleteCommand;
import seedu.address.logic.messages.FitDeleteCommandMessages;
import seedu.address.logic.messages.Messages;
import seedu.address.logic.parser.exceptions.ParseException;

/**
 * Parses input arguments and creates a new FitDeleteCommand object.
 */
public class FitDeleteCommandParser implements Parser<FitDeleteCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the FitDeleteCommand
     * and returns a FitDeleteCommand object for execution.
     *
     * @param args The string of arguments to be parsed
     * @return The FitDeleteCommand object for execution
     * @throws ParseException If the user input does not conform to the expected format
     */
    public FitDeleteCommand parse(String args) throws ParseException {
        requireNonNull(args);

        ArgumentMultimap argMultimap =
            ArgumentTokenizer.tokenize(args, PREFIX_EXERCISE_NAME, PREFIX_FITDELETE_DELETE_ALL);

        // Ensure that client index is present
        if (argMultimap.isPreambleEmpty()) {
            throw new ParseException(FitDeleteCommandMessages.MESSAGE_NO_INDEX_FITDELETE);
        }

        if (argMultimap.getPreambleSegmentNumber() != 1) {
            throw new ParseException(
                String.format(Messages.MESSAGE_INVALID_COMMAND_FORMAT, FitDeleteCommandMessages.MESSAGE_USAGE));
        }

        // Parse index of client to delete exercise from
        Index index;
        try {
            index = ParserUtil.parseIndex(argMultimap.getPreamble());
        } catch (ParseException pe) {
            throw new ParseException(FitDeleteCommandMessages.MESSAGE_INVALID_INDEX_FITDELETE, pe);
        }

        // Get existence of relevant prefixes
        boolean containsPrefixExerciseName = argMultimap.contains(PREFIX_EXERCISE_NAME);
        boolean containsPrefixExerciseDeleteAll = argMultimap.contains(PREFIX_FITDELETE_DELETE_ALL);

        // Checks for concurrent prefixes
        if (containsPrefixExerciseName && containsPrefixExerciseDeleteAll) {
            throw new ParseException(FitDeleteCommandMessages.MESSAGE_CONCURRENT_PREFIX);
        }

        // Checks if all prefixes missing
        if (!containsPrefixExerciseName && !containsPrefixExerciseDeleteAll) {
            throw new ParseException(FitDeleteCommandMessages.MESSAGE_EXERCISE_NAME_PARAMETER_AND_ALL_PREFIX_MISSING);
        }

        // Ensure no duplicate prefixes
        argMultimap.verifyNoDuplicatePrefixesFor(PREFIX_EXERCISE_NAME);

        Optional<String> exerciseNameOpt = Optional.ofNullable(
            containsPrefixExerciseDeleteAll ? null
                : ParserUtil.parseExerciseName(argMultimap.getValue(PREFIX_EXERCISE_NAME)));

        return new FitDeleteCommand(index, exerciseNameOpt, containsPrefixExerciseDeleteAll);
    }
}
