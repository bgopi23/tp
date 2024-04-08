package seedu.address.logic.parser;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_EXERCISE_ALL;
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

    private ArgumentMultimap getArgMultiMap(String args) {
        return ArgumentTokenizer.tokenize(args, PREFIX_EXERCISE_NAME, PREFIX_FITDELETE_DELETE_ALL);
    }

    private void verifyClientIndexExists(ArgumentMultimap argumentMultimap) throws ParseException {
        if (argumentMultimap.isPreambleEmpty()) {
            throw new ParseException(FitDeleteCommandMessages.MESSAGE_NO_INDEX_FITDELETE);
        }
    }

    private void verifyClientIndexSingleSegment(ArgumentMultimap argumentMultimap) throws ParseException {
        if (argumentMultimap.getPreambleSegmentNumber() != 1) {
            throw new ParseException(
                String.format(Messages.MESSAGE_INVALID_COMMAND_FORMAT, FitDeleteCommandMessages.MESSAGE_USAGE));
        }
    }

    private Index parseIndex(ArgumentMultimap argumentMultimap) throws ParseException {
        Index index;
        try {
            index = ParserUtil.parseIndex(argumentMultimap.getPreamble());
        } catch (ParseException pe) {
            throw new ParseException(FitDeleteCommandMessages.MESSAGE_INVALID_INDEX_FITDELETE, pe);
        }

        return index;
    }

    private void verifyNoDuplicatePrefixes(ArgumentMultimap argumentMultimap) throws ParseException {
        argumentMultimap.verifyNoDuplicatePrefixesFor(PREFIX_EXERCISE_NAME, PREFIX_EXERCISE_ALL);
    }

    private void verifyNoConflictingPrefixes(boolean containsPrefixExerciseName,
                                             boolean containsPrefixExerciseDeleteAll) throws ParseException {
        if (containsPrefixExerciseName && containsPrefixExerciseDeleteAll) {
            throw new ParseException(FitDeleteCommandMessages.MESSAGE_CONCURRENT_PREFIX);
        }
    }

    private void verifyNoMissingPrefixes(boolean containsPrefixExerciseName, boolean containsPrefixExerciseDeleteAll)
            throws ParseException {
        if (!containsPrefixExerciseName && !containsPrefixExerciseDeleteAll) {
            throw new ParseException(FitDeleteCommandMessages.MESSAGE_EXERCISE_NAME_PARAMETER_AND_ALL_PREFIX_MISSING);
        }
    }

    private void verifyNoArgumentValueForPrefixes(ArgumentMultimap argumentMultimap) throws ParseException {
        if (argumentMultimap.hasArgumentValueForPrefixes(PREFIX_FITDELETE_DELETE_ALL)) {
            throw new ParseException(
                String.format(Messages.MESSAGE_INVALID_COMMAND_FORMAT, FitDeleteCommandMessages.MESSAGE_USAGE));
        }
    }

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

        ArgumentMultimap argumentMultimap = getArgMultiMap(args);

        verifyClientIndexExists(argumentMultimap);
        verifyClientIndexSingleSegment(argumentMultimap);

        Index index = parseIndex(argumentMultimap);

        verifyNoArgumentValueForPrefixes(argumentMultimap);
        verifyNoDuplicatePrefixes(argumentMultimap);

        // Get existence of relevant prefixes
        boolean containsPrefixExerciseName = argumentMultimap.contains(PREFIX_EXERCISE_NAME);
        boolean containsPrefixExerciseDeleteAll = argumentMultimap.contains(PREFIX_FITDELETE_DELETE_ALL);

        verifyNoConflictingPrefixes(containsPrefixExerciseName, containsPrefixExerciseDeleteAll);
        verifyNoMissingPrefixes(containsPrefixExerciseName, containsPrefixExerciseDeleteAll);

        Optional<String> exerciseNameOpt = Optional.ofNullable(
            containsPrefixExerciseDeleteAll ? null
                : ParserUtil.parseExerciseName(argumentMultimap.getValue(PREFIX_EXERCISE_NAME)));

        return new FitDeleteCommand(index, exerciseNameOpt, containsPrefixExerciseDeleteAll);
    }
}
