package seedu.address.logic.parser;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_EXERCISE_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_EXERCISE_REPS;
import static seedu.address.logic.parser.CliSyntax.PREFIX_EXERCISE_REST;
import static seedu.address.logic.parser.CliSyntax.PREFIX_EXERCISE_SETS;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.FitAddCommand;
import seedu.address.logic.messages.FitAddCommandMessages;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.exercise.Exercise;

/**
 * Parses input arguments and creates a new FitAddCommand object
 */
public class FitAddCommandParser implements Parser<FitAddCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the FitAddCommand
     * and returns a FitAddCommand object for execution.
     *
     * @throws ParseException if the user input does not conform the expected format
     */
    public FitAddCommand parse(String args) throws ParseException {
        requireNonNull(args);

        ArgumentMultimap argMultimap =
            ArgumentTokenizer.tokenize(args, PREFIX_EXERCISE_NAME, PREFIX_EXERCISE_SETS,
                PREFIX_EXERCISE_REPS, PREFIX_EXERCISE_REST);

        // Ensure that client index is present
        if (argMultimap.isPreambleEmpty()) {
            throw new ParseException(FitAddCommandMessages.MESSAGE_NO_INDEX_FITADD);
        }

        // Parse index of client to add exercise to
        Index index;
        try {
            index = ParserUtil.parseIndex(argMultimap.getPreamble());
        } catch (ParseException pe) {
            throw new ParseException(FitAddCommandMessages.MESSAGE_INVALID_INDEX_FITADD, pe);
        }

        // Ensure that required prefixes are present
        if (!argMultimap.containsAll(PREFIX_EXERCISE_NAME)) {
            throw new ParseException(FitAddCommandMessages.MESSAGE_EXERCISE_NAME_PARAMETER_MISSING_FITADD);
        }

        // Ensure no duplicate prefixes
        argMultimap.verifyNoDuplicatePrefixesFor(PREFIX_EXERCISE_NAME, PREFIX_EXERCISE_SETS, PREFIX_EXERCISE_REPS,
            PREFIX_EXERCISE_REST);

        String exerciseName = ParserUtil.parseExerciseName(argMultimap.getValue(PREFIX_EXERCISE_NAME));
        Integer exerciseSets = ParserUtil.parseExerciseSets(argMultimap.getValue(PREFIX_EXERCISE_SETS));
        Integer exerciseReps = ParserUtil.parseExerciseReps(argMultimap.getValue(PREFIX_EXERCISE_REPS));
        Integer exerciseRest = ParserUtil.parseExerciseRest(argMultimap.getValue(PREFIX_EXERCISE_REST));

        Exercise exercise = ParserUtil.parseExercise(exerciseName, exerciseSets, exerciseReps, exerciseRest);

        return new FitAddCommand(index, exercise);
    }

}
