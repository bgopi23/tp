package seedu.address.logic.messages;

/**
 * Messages used by ListCommand and associated classes.
 */
public class WeightCommandMessages extends Messages {

    public static final String COMMAND_WORD = "weight";
    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Adds a weight (in kg) to a client specified by the index number used in the last person listing.\n"
            + "If no weight or '0' is specified, the latest weight value will be removed from the client.\n"
            + "Parameters: INDEX [WEIGHT] (must be a positive float)\n"
            + "Example: " + COMMAND_WORD + " 1 "
            + "70.0";
    public static final String MESSAGE_ADD_WEIGHT_SUCCESS =
            "Successfully added weight to client!" + MESSAGE_RESULT_DIVIDER;
    public static final String MESSAGE_DELETE_WEIGHT_SUCCESS =
            "Successfully removed weight from client!" + MESSAGE_RESULT_DIVIDER;
    public static final String MESSAGE_INVALID_PARAMETER_WEIGHT = String.format(MESSAGE_INVALID_PERSON_DISPLAYED_INDEX,
            MESSAGE_USAGE);

    public static final String MESSAGE_NO_PARAMETER_WEIGHT = String.format(MESSAGE_NO_PARAMETERS, MESSAGE_USAGE);

    public static final String MESSAGE_INVALID_INDEX_WEIGHT = String.format(MESSAGE_INVALID_PERSON_DISPLAYED_INDEX,
            MESSAGE_USAGE);

    public static final String WEIGHT_VALUE_HEADER = "Weight: ";
    public static final String WEIGHT_DATE_HEADER = "Date Recorded: ";

    // Represents an empty weight field in the UI that needs extra clarity (instead of not showing the field at all)
    public static final String EMPTY_FIELD_WEIGHT_VALUE = WEIGHT_VALUE_HEADER + Messages.EMPTY_FIELD;
    public static final String EMPTY_FIELD_WEIGHT_DATE = WEIGHT_DATE_HEADER + Messages.EMPTY_FIELD;
}
