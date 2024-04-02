package seedu.address.logic.messages;

import static seedu.address.logic.parser.CliSyntax.PREFIX_EXERCISE_BREAK_BETWEEN_SETS;
import static seedu.address.logic.parser.CliSyntax.PREFIX_EXERCISE_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_EXERCISE_REPS;
import static seedu.address.logic.parser.CliSyntax.PREFIX_EXERCISE_SETS;

/**
 * Messages used by FitAddCommand and associated classes.
 */
public class FitAddCommandMessages extends Messages {
    public static final String COMMAND_WORD = "fitadd";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Adds an exercise to the client identified "
        + "by their corresponding index.\n"
        + "Parameters: INDEX (must be a positive integer) "
        + PREFIX_EXERCISE_NAME + "EXERCISE_NAME "
        + "[" + PREFIX_EXERCISE_SETS + "SETS] "
        + "[" + PREFIX_EXERCISE_REPS + "REPETITIONS] "
        + "[" + PREFIX_EXERCISE_BREAK_BETWEEN_SETS + "REST_TIME]\n"
        + "Example: " + COMMAND_WORD + " 1 "
        + PREFIX_EXERCISE_NAME + "squats "
        + PREFIX_EXERCISE_SETS + "3 "
        + PREFIX_EXERCISE_REPS + "10 "
        + PREFIX_EXERCISE_BREAK_BETWEEN_SETS + "1";

    public static final String MESSAGE_NO_INDEX_FITADD = String.format(MESSAGE_NO_INDEX, MESSAGE_USAGE);
    public static final String MESSAGE_INVALID_INDEX_FITADD = String.format(MESSAGE_INVALID_PERSON_DISPLAYED_INDEX,
        MESSAGE_USAGE);
    public static final String MESSAGE_EXERCISE_NAME_PARAMETER_AND_DEFAULT_PREFIXES_MISSING =
        String.format("Either exercise name parameter or default exercise prefix(es) must be supplied\n%1$s",
            MESSAGE_USAGE);
    public static final String MESSAGE_ADD_EXERCISE_CONFLICTING_PREFIXES =
        String.format("Exercise name parameter cannot be supplied together with default exercise prefix(es)\n%1$s",
            MESSAGE_USAGE);
    public static final String MESSAGE_ADD_EXERCISE_SUCCESS = "Successfully added exercise(s) for client";
}
