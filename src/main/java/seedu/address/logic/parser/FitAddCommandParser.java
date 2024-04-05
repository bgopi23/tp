package seedu.address.logic.parser;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.commands.FitAddCommand.DEFAULT_ABS_EXERCISES;
import static seedu.address.logic.commands.FitAddCommand.DEFAULT_ARM_EXERCISES;
import static seedu.address.logic.commands.FitAddCommand.DEFAULT_BACK_EXERCISES;
import static seedu.address.logic.commands.FitAddCommand.DEFAULT_CHEST_EXERCISES;
import static seedu.address.logic.commands.FitAddCommand.DEFAULT_LEG_EXERCISES;
import static seedu.address.logic.commands.FitAddCommand.DEFAULT_SHOULDER_EXERCISES;
import static seedu.address.logic.messages.FitAddCommandMessages.MESSAGE_ADD_EXERCISE_CONFLICTING_PREFIXES;
import static seedu.address.logic.messages.FitAddCommandMessages.MESSAGE_EXERCISE_NAME_PARAMETER_AND_DEFAULT_PREFIXES_MISSING;
import static seedu.address.logic.messages.FitAddCommandMessages.MESSAGE_INVALID_COMMAND_FORMAT_FITADD;
import static seedu.address.logic.messages.FitAddCommandMessages.MESSAGE_INVALID_INDEX_FITADD;
import static seedu.address.logic.messages.FitAddCommandMessages.MESSAGE_NO_INDEX_FITADD;
import static seedu.address.logic.parser.CliSyntax.ALL_EXERCISE_PREFIXES;
import static seedu.address.logic.parser.CliSyntax.DEFAULT_EXERCISE_PREFIXES;
import static seedu.address.logic.parser.CliSyntax.PREFIX_EXERCISE_ABS;
import static seedu.address.logic.parser.CliSyntax.PREFIX_EXERCISE_ALL;
import static seedu.address.logic.parser.CliSyntax.PREFIX_EXERCISE_ARM;
import static seedu.address.logic.parser.CliSyntax.PREFIX_EXERCISE_BACK;
import static seedu.address.logic.parser.CliSyntax.PREFIX_EXERCISE_BREAK_BETWEEN_SETS;
import static seedu.address.logic.parser.CliSyntax.PREFIX_EXERCISE_CHEST;
import static seedu.address.logic.parser.CliSyntax.PREFIX_EXERCISE_LEG;
import static seedu.address.logic.parser.CliSyntax.PREFIX_EXERCISE_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_EXERCISE_REPS;
import static seedu.address.logic.parser.CliSyntax.PREFIX_EXERCISE_SETS;
import static seedu.address.logic.parser.CliSyntax.PREFIX_EXERCISE_SHOULDER;

import java.util.HashSet;
import java.util.Set;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.FitAddCommand;
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
     * @param args The string of arguments to be parsed
     * @return The FitAddCommand object for execution
     * @throws ParseException If the user input does not conform to the expected format
     */
    public FitAddCommand parse(String args) throws ParseException {
        requireNonNull(args);

        ArgumentMultimap argMultimap =
            ArgumentTokenizer.tokenize(args, ALL_EXERCISE_PREFIXES);

        // Ensure that client index is present
        if (argMultimap.isPreambleEmpty()) {
            throw new ParseException(MESSAGE_NO_INDEX_FITADD);
        }

        if (!argMultimap.isPreambleAlone()) {
            throw new ParseException(MESSAGE_INVALID_COMMAND_FORMAT_FITADD);
        }

        // Parse index of client to add exercise to
        Index index;
        try {
            index = ParserUtil.parseIndex(argMultimap.getPreamble());
        } catch (ParseException pe) {
            throw new ParseException(MESSAGE_INVALID_INDEX_FITADD, pe);
        }

        argMultimap.verifyNoDuplicatePrefixesFor(ALL_EXERCISE_PREFIXES);

        // Check for existing prefixes
        boolean hasExerciseNamePrefix = argMultimap.contains(PREFIX_EXERCISE_NAME);
        boolean hasDefaultExercisePrefixes = argMultimap.containsAny(DEFAULT_EXERCISE_PREFIXES);

        if (!hasExerciseNamePrefix && !hasDefaultExercisePrefixes) {
            throw new ParseException(MESSAGE_EXERCISE_NAME_PARAMETER_AND_DEFAULT_PREFIXES_MISSING);
        }

        if (hasExerciseNamePrefix && hasDefaultExercisePrefixes) {
            throw new ParseException(MESSAGE_ADD_EXERCISE_CONFLICTING_PREFIXES);
        }

        Set<Exercise> exercisesToAdd = new HashSet<>();

        if (hasExerciseNamePrefix) {
            // If individual exercise details are provided, add that exercise
            String exerciseName = ParserUtil.parseExerciseName(argMultimap.getValue(PREFIX_EXERCISE_NAME));
            Integer exerciseSets = ParserUtil.parseExerciseSets(argMultimap.getValue(PREFIX_EXERCISE_SETS));
            Integer exerciseReps = ParserUtil.parseExerciseReps(argMultimap.getValue(PREFIX_EXERCISE_REPS));
            Integer exerciseBreakBetweenSets =
                ParserUtil.parseExerciseBreakBetweenSets(argMultimap.getValue(PREFIX_EXERCISE_BREAK_BETWEEN_SETS));

            Exercise exercise =
                ParserUtil.parseExercise(exerciseName, exerciseSets, exerciseReps, exerciseBreakBetweenSets);
            exercisesToAdd.add(exercise);
        } else {
            if (argMultimap.contains(PREFIX_EXERCISE_ALL)) {
                exercisesToAdd.addAll(DEFAULT_ARM_EXERCISES);
                exercisesToAdd.addAll(DEFAULT_LEG_EXERCISES);
                exercisesToAdd.addAll(DEFAULT_CHEST_EXERCISES);
                exercisesToAdd.addAll(DEFAULT_BACK_EXERCISES);
                exercisesToAdd.addAll(DEFAULT_SHOULDER_EXERCISES);
                exercisesToAdd.addAll(DEFAULT_ABS_EXERCISES);
            } else {
                if (argMultimap.contains(PREFIX_EXERCISE_ARM)) {
                    exercisesToAdd.addAll(DEFAULT_ARM_EXERCISES);
                }
                if (argMultimap.contains(PREFIX_EXERCISE_LEG)) {
                    exercisesToAdd.addAll(DEFAULT_LEG_EXERCISES);
                }
                if (argMultimap.contains(PREFIX_EXERCISE_CHEST)) {
                    exercisesToAdd.addAll(DEFAULT_CHEST_EXERCISES);
                }
                if (argMultimap.contains(PREFIX_EXERCISE_BACK)) {
                    exercisesToAdd.addAll(DEFAULT_BACK_EXERCISES);
                }
                if (argMultimap.contains(PREFIX_EXERCISE_SHOULDER)) {
                    exercisesToAdd.addAll(DEFAULT_SHOULDER_EXERCISES);
                }
                if (argMultimap.contains(PREFIX_EXERCISE_ABS)) {
                    exercisesToAdd.addAll(DEFAULT_ABS_EXERCISES);
                }
            }
        }

        return new FitAddCommand(index, exercisesToAdd);
    }

}
