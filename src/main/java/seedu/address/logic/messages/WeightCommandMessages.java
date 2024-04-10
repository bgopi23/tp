package seedu.address.logic.messages;

import seedu.address.logic.parser.CliSyntax;

/**
 * Messages used by ListCommand and associated classes.
 */
public class WeightCommandMessages extends Messages {

    public static final String COMMAND_WORD = "weight";
    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Adds a weight (in kg) to the client specified by the index number used in the client list.\n"
            + "If no weight or '0' is specified, the latest weight value will be removed from the client.\n"
            + "Parameters: INDEX [WEIGHT] (must be a number between 0 and 5000)\n"
            + "Example: " + COMMAND_WORD + " 1 "
            + "70.0";
    public static final String WEIGHT_VALUE_HEADER = "Weight: ";
    public static final String WEIGHT_DATE_HEADER = "Date Recorded: ";
    public static final String MESSAGE_ADD_WEIGHT_SUCCESS =
            "Successfully added weight to client!"
            + MESSAGE_RESULT_DIVIDER;
    public static final String MESSAGE_DELETE_WEIGHT_SUCCESS =
            "Successfully removed weight from client!"
            + MESSAGE_RESULT_DIVIDER;
    public static final String MESSAGE_INVALID_PARAMETER_WEIGHT = String.format(MESSAGE_INVALID_INDEX, MESSAGE_USAGE);
    public static final String MESSAGE_NO_PARAMETER_WEIGHT = String.format(MESSAGE_NO_PARAMETERS, MESSAGE_USAGE);
    public static final String MESSAGE_INVALID_INDEX_WEIGHT = String.format(MESSAGE_INVALID_INDEX, MESSAGE_USAGE);
}
