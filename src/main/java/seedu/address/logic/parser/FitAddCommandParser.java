package seedu.address.logic.parser;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.messages.FitAddCommandMessages.MESSAGE_ADD_EXERCISE_NAME_CONFLICTING_PREFIXES;
import static seedu.address.logic.messages.FitAddCommandMessages.MESSAGE_ADD_EXERCISE_VALUES_CONFLICTING_PREFIXES;
import static seedu.address.logic.messages.FitAddCommandMessages.MESSAGE_EXERCISE_NAME_PARAMETER_AND_DEFAULT_PREFIXES_MISSING;
import static seedu.address.logic.messages.FitAddCommandMessages.MESSAGE_INVALID_COMMAND_FORMAT_FITADD;
import static seedu.address.logic.messages.FitAddCommandMessages.MESSAGE_INVALID_INDEX_FITADD;
import static seedu.address.logic.messages.FitAddCommandMessages.MESSAGE_NO_INDEX_FITADD;
import static seedu.address.logic.parser.CliSyntax.ALL_EXERCISE_PREFIXES;
import static seedu.address.logic.parser.CliSyntax.DEFAULT_EXERCISE_PREFIXES;
import static seedu.address.logic.parser.CliSyntax.EXERCISE_VALUE_PREFIXES;
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
import java.util.Optional;
import java.util.Set;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.FitAddCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.exercise.ExerciseToAdd;

/**
 * Parses input arguments and creates a new FitAddCommand object
 */
public class FitAddCommandParser implements Parser<FitAddCommand> {

    /**
     * Parses the input arguments to create an {@link ArgumentMultimap}
     * which maps each prefix to its respective arguments.
     *
     * @param args The string of arguments to be tokenized and mapped.
     * @return An {@link ArgumentMultimap} object representing the mapping of prefixes to their arguments.
     */
    private ArgumentMultimap getArgMultiMap(String args) {
        return ArgumentTokenizer.tokenize(args, ALL_EXERCISE_PREFIXES);
    }

    /**
     * Verifies that a client index is specified in the input arguments.
     * This method checks if the preamble of the argument multimap is empty,
     * indicating that no client index was provided.
     *
     * @param argumentMultimap The {@link ArgumentMultimap} containing the parsed input arguments.
     * @throws ParseException If no client index is provided in the input arguments.
     */
    private void verifyClientIndexExists(ArgumentMultimap argumentMultimap) throws ParseException {
        if (argumentMultimap.isPreambleEmpty()) {
            throw new ParseException(MESSAGE_NO_INDEX_FITADD);
        }
    }

    /**
     * Ensures that the client index is specified as a single segment in the input arguments.
     * This method checks if the preamble contains more than just the client index,
     * which would indicate an invalid command format.
     *
     * @param argumentMultimap The {@link ArgumentMultimap} containing the parsed input arguments.
     * @throws ParseException If the client index is not the only segment in the preamble.
     */
    private void verifyClientIndexSingleSegment(ArgumentMultimap argumentMultimap) throws ParseException {
        if (!argumentMultimap.hasOnlyOnePreambleSegment()) {
            throw new ParseException(MESSAGE_INVALID_COMMAND_FORMAT_FITADD);
        }
    }

    /**
     * Parses the client index from the preamble of the argument multimap.
     * This method attempts to parse the client index and throws a {@link ParseException} if the index is invalid.
     *
     * @param argumentMultimap The {@link ArgumentMultimap} containing the parsed input arguments.
     * @return An {@link Index} representing the parsed client index.
     * @throws ParseException If the client index is invalid or cannot be parsed.
     */
    private Index parseIndex(ArgumentMultimap argumentMultimap) throws ParseException {
        // Parse index of client to add exercise to
        Index index;
        try {
            index = ParserUtil.parseIndex(argumentMultimap.getPreamble());
        } catch (ParseException pe) {
            throw new ParseException(MESSAGE_INVALID_INDEX_FITADD, pe);
        }

        return index;
    }

    /**
     * Verifies that there are no duplicate prefixes in the input arguments.
     * This method checks if any of the exercise prefixes are duplicated, which is not allowed.
     *
     * @param argumentMultimap The {@link ArgumentMultimap} containing the parsed input arguments.
     * @throws ParseException If duplicate prefixes are found.
     */
    private void verifyNoDuplicatePrefixes(ArgumentMultimap argumentMultimap) throws ParseException {
        argumentMultimap.verifyNoDuplicatePrefixesFor(ALL_EXERCISE_PREFIXES);
    }

    /**
     * Ensures that there are no argument values for default exercise prefixes.
     * This method checks if any of the default exercise prefixes are accompanied by argument values, which is invalid.
     *
     * @param argumentMultimap The {@link ArgumentMultimap} containing the parsed input arguments.
     * @throws ParseException If argument values are found for default exercise prefixes.
     */
    private void verifyNoArgumentValueForPrefixes(ArgumentMultimap argumentMultimap) throws ParseException {
        if (argumentMultimap.hasArgumentValueForPrefixes(DEFAULT_EXERCISE_PREFIXES)) {
            throw new ParseException(MESSAGE_INVALID_COMMAND_FORMAT_FITADD);
        }
    }

    /**
     * Checks for the presence of required prefixes in the input arguments.
     * This method ensures that either an exercise name prefix is present or default exercise prefixes are provided.
     *
     * @param hasExerciseNamePrefix A boolean indicating whether the exercise name prefix is present.
     * @param hasDefaultExercisePrefixes A boolean indicating whether any default exercise prefixes are present.
     * @throws ParseException If neither an exercise name prefix nor default exercise prefixes are provided.
     */
    private void verifyNoMissingPrefixes(boolean hasExerciseNamePrefix, boolean hasDefaultExercisePrefixes)
            throws ParseException {
        if (!hasExerciseNamePrefix && !hasDefaultExercisePrefixes) {
            throw new ParseException(MESSAGE_EXERCISE_NAME_PARAMETER_AND_DEFAULT_PREFIXES_MISSING);
        }
    }

    /**
     * Ensures that there are no conflicting prefixes in the input arguments.
     * This method checks for conflicts between exercise name and default exercise prefixes,
     * as well as between default exercise and exercise value prefixes.
     *
     * @param hasExerciseNamePrefix A boolean indicating whether the exercise name prefix is present.
     * @param hasExerciseValuesPrefix A boolean indicating whether any exercise value prefixes are present.
     * @param hasDefaultExercisePrefixes A boolean indicating whether any default exercise prefixes are present.
     * @throws ParseException If there are conflicting prefixes in the input arguments.
     */
    private void verifyNoConflictingPrefixes(boolean hasExerciseNamePrefix, boolean hasExerciseValuesPrefix,
                                             boolean hasDefaultExercisePrefixes)
            throws ParseException {
        if (hasExerciseNamePrefix && hasDefaultExercisePrefixes) {
            throw new ParseException(MESSAGE_ADD_EXERCISE_NAME_CONFLICTING_PREFIXES);
        }

        if (hasDefaultExercisePrefixes && hasExerciseValuesPrefix) {
            throw new ParseException(MESSAGE_ADD_EXERCISE_VALUES_CONFLICTING_PREFIXES);
        }
    }

    /**
     * Parses the given {@link ArgumentMultimap} to create an {@link ExerciseToAdd} object containing exercise details.
     * This method specifically handles the scenario where individual exercise details are provided by the user.
     *
     * @param argumentMultimap The {@link ArgumentMultimap} containing the exercise details.
     * @return An {@link ExerciseToAdd} object containing the parsed exercise name, sets, reps, and break between sets.
     * @throws ParseException If the parsing of exercise parameters fails.
     */
    private ExerciseToAdd parseExerciseToAdd(ArgumentMultimap argumentMultimap) throws ParseException {
        // If individual exercise details are provided, add that exercise
        String exerciseName = ParserUtil.parseExerciseName(argumentMultimap.getValue(PREFIX_EXERCISE_NAME));
        Optional<Integer> exerciseSets = ParserUtil.parseExerciseSets(argumentMultimap.getValue(PREFIX_EXERCISE_SETS));
        Optional<Integer> exerciseReps = ParserUtil.parseExerciseReps(argumentMultimap.getValue(PREFIX_EXERCISE_REPS));
        Optional<Integer> exerciseBreakBetweenSets =
            ParserUtil.parseExerciseBreakBetweenSets(argumentMultimap.getValue(PREFIX_EXERCISE_BREAK_BETWEEN_SETS));

        return new ExerciseToAdd(exerciseName, exerciseSets, exerciseReps, exerciseBreakBetweenSets);
    }

    /**
     * Retrieves the default exercises based on the specified exercise body part prefixes
     * within the {@link ArgumentMultimap}.
     * This method is used when the user specifies one or more body parts without providing individual exercise details.
     *
     * @param argumentMultimap The {@link ArgumentMultimap} containing the prefixes for body parts.
     * @return A set of {@link ExerciseToAdd} objects representing the default exercises for the specified body parts.
     */
    private Set<ExerciseToAdd> getDefaultExercises(ArgumentMultimap argumentMultimap) {
        Set<ExerciseToAdd> defaultExercises = new HashSet<>();

        if (argumentMultimap.contains(PREFIX_EXERCISE_ALL)) {
            defaultExercises.addAll(FitAddCommand.DEFAULT_ARM_EXERCISES);
            defaultExercises.addAll(FitAddCommand.DEFAULT_LEG_EXERCISES);
            defaultExercises.addAll(FitAddCommand.DEFAULT_CHEST_EXERCISES);
            defaultExercises.addAll(FitAddCommand.DEFAULT_BACK_EXERCISES);
            defaultExercises.addAll(FitAddCommand.DEFAULT_SHOULDER_EXERCISES);
            defaultExercises.addAll(FitAddCommand.DEFAULT_ABS_EXERCISES);
        } else {
            if (argumentMultimap.contains(PREFIX_EXERCISE_ARM)) {
                defaultExercises.addAll(FitAddCommand.DEFAULT_ARM_EXERCISES);
            }
            if (argumentMultimap.contains(PREFIX_EXERCISE_LEG)) {
                defaultExercises.addAll(FitAddCommand.DEFAULT_LEG_EXERCISES);
            }
            if (argumentMultimap.contains(PREFIX_EXERCISE_CHEST)) {
                defaultExercises.addAll(FitAddCommand.DEFAULT_CHEST_EXERCISES);
            }
            if (argumentMultimap.contains(PREFIX_EXERCISE_BACK)) {
                defaultExercises.addAll(FitAddCommand.DEFAULT_BACK_EXERCISES);
            }
            if (argumentMultimap.contains(PREFIX_EXERCISE_SHOULDER)) {
                defaultExercises.addAll(FitAddCommand.DEFAULT_SHOULDER_EXERCISES);
            }
            if (argumentMultimap.contains(PREFIX_EXERCISE_ABS)) {
                defaultExercises.addAll(FitAddCommand.DEFAULT_ABS_EXERCISES);
            }
        }

        return defaultExercises;
    }

    /**
     * Determines the set of exercises to add based on whether an exercise name prefix is present.
     * If an exercise name prefix is present, it parses the individual exercise details to create an
     * {@link ExerciseToAdd} object.
     * Otherwise, it retrieves the default exercises for the specified body parts.
     *
     * @param argumentMultimap The {@link ArgumentMultimap} containing the parsed arguments.
     * @param hasExerciseNamePrefix A boolean indicating whether the exercise name prefix is present.
     * @return A set of {@link ExerciseToAdd} objects representing the exercises to add.
     * @throws ParseException If the parsing of exercise parameters fails.
     */
    private Set<ExerciseToAdd> getExercisesToAdd(ArgumentMultimap argumentMultimap, boolean hasExerciseNamePrefix)
            throws ParseException {
        Set<ExerciseToAdd> exercisesToAdd = new HashSet<>();
        if (hasExerciseNamePrefix) {
            exercisesToAdd.add(parseExerciseToAdd(argumentMultimap));
        } else {
            exercisesToAdd.addAll(getDefaultExercises(argumentMultimap));
        }

        return exercisesToAdd;
    }

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

        ArgumentMultimap argumentMultimap = getArgMultiMap(args);

        verifyClientIndexExists(argumentMultimap);
        verifyClientIndexSingleSegment(argumentMultimap);

        Index index = parseIndex(argumentMultimap);

        verifyNoArgumentValueForPrefixes(argumentMultimap);
        verifyNoDuplicatePrefixes(argumentMultimap);

        // Get existence of relevant prefixes
        boolean hasExerciseNamePrefix = argumentMultimap.contains(PREFIX_EXERCISE_NAME);
        boolean hasExerciseValuesPrefix = argumentMultimap.containsAny(EXERCISE_VALUE_PREFIXES);
        boolean hasDefaultExercisePrefixes = argumentMultimap.containsAny(DEFAULT_EXERCISE_PREFIXES);

        verifyNoMissingPrefixes(hasExerciseNamePrefix, hasDefaultExercisePrefixes);
        verifyNoConflictingPrefixes(hasExerciseNamePrefix, hasExerciseValuesPrefix, hasDefaultExercisePrefixes);

        return new FitAddCommand(index, getExercisesToAdd(argumentMultimap, hasExerciseNamePrefix));
    }
}
