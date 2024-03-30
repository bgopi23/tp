package seedu.address.logic.messages;

import static seedu.address.logic.parser.CliSyntax.PREFIX_EXERCISE_NAME;

/**
 * Messages used by FitDeleteCommand and associated classes.
 */
public class FitDeleteCommandMessages extends Messages {
    public static final String COMMAND_WORD = "fitdelete";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Deletes an exercise from the client identified "
        + "by their corresponding index.\n"
        + "Parameters: INDEX (must be a positive integer) "
        + PREFIX_EXERCISE_NAME + "EXERCISE_NAME\n"
        + "Example: " + COMMAND_WORD + " 1 "
        + PREFIX_EXERCISE_NAME + "squats ";

    public static final String MESSAGE_EXERCISE_NAME_PARAMETER_MISSING = "Exercise name parameter missing! \n%1$s";
    public static final String MESSAGE_DELETE_EXERCISE_SUCCESS = "Successfully removed exercise '%s' for client";
    public static final String MESSAGE_EXERCISE_NAME_DOES_NOT_EXIST =
        "Exercise name '%s' does not exist for the client";

    public static final String MESSAGE_EXERCISE_NAME_PARAMETER_MISSING_FITDELETE =
        String.format(MESSAGE_EXERCISE_NAME_PARAMETER_MISSING,
            MESSAGE_USAGE);
    public static final String MESSAGE_NO_INDEX_FITDELETE = String.format(MESSAGE_NO_INDEX, MESSAGE_USAGE);
    public static final String MESSAGE_INVALID_INDEX_FITDELETE = String.format(MESSAGE_INVALID_PERSON_DISPLAYED_INDEX,
        MESSAGE_USAGE);
}
