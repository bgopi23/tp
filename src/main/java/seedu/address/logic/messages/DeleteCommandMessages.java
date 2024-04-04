package seedu.address.logic.messages;

/**
 * Messages used by DeleteCommand and associated classes.
 */
public class DeleteCommandMessages extends Messages {

    public static final String COMMAND_WORD = "delete";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Deletes the client identified by their corresponding list index.\n"
            + "Parameters: INDEX (must be a positive integer)\n"
            + "Example: " + COMMAND_WORD + " 1";

    public static final String MESSAGE_DELETE_PERSON_SUCCESS =
            "Successfully deleted client!" + MESSAGE_RESULT_DIVIDER;
}
