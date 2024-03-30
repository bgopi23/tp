package seedu.address.logic.parser;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_EXERCISE_NAME;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.FitDeleteCommand;
import seedu.address.logic.messages.FitDeleteCommandMessages;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.exercise.Exercise;

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
            ArgumentTokenizer.tokenize(args, PREFIX_EXERCISE_NAME);

        // Ensure that client index is present
        if (argMultimap.isPreambleEmpty()) {
            throw new ParseException(FitDeleteCommandMessages.MESSAGE_NO_INDEX_FITDELETE);
        }

        // Parse index of client to delete exercise from
        Index index;
        try {
            index = ParserUtil.parseIndex(argMultimap.getPreamble());
        } catch (ParseException pe) {
            throw new ParseException(FitDeleteCommandMessages.MESSAGE_INVALID_INDEX_FITDELETE, pe);
        }

        // Ensure that required prefixes are present
        if (!argMultimap.containsAll(PREFIX_EXERCISE_NAME)) {
            throw new ParseException(FitDeleteCommandMessages.MESSAGE_EXERCISE_NAME_PARAMETER_MISSING_FITDELETE);
        }

        // Ensure no duplicate prefixes
        argMultimap.verifyNoDuplicatePrefixesFor(PREFIX_EXERCISE_NAME);

        String exerciseName = ParserUtil.parseExerciseName(argMultimap.getValue(PREFIX_EXERCISE_NAME));

        Exercise exercise =
            ParserUtil.parseExercise(exerciseName, Exercise.DEFAULT_SETS, Exercise.DEFAULT_REPS, Exercise.DEFAULT_REST);

        return new FitDeleteCommand(index, exercise);
    }

}