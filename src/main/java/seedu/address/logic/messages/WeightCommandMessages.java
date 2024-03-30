package seedu.address.logic.messages;

import seedu.address.logic.parser.CliSyntax;

/**
 * Messages used by ListCommand and associated classes.
 */
public class WeightCommandMessages extends Messages {

    public static final String COMMAND_WORD = "weight";
    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Edits the weight (in kilograms) of the person identified "
            + "by the index number used in the last person listing.\n"
            + "Existing weight will be overwritten by the input.\n"
            + "Parameters: INDEX (must be a positive float) "
            + CliSyntax.PREFIX_WEIGHT + "WEIGHT\n"
            + "Example: " + COMMAND_WORD + " 1 "
            + CliSyntax.PREFIX_WEIGHT + "72.5";
    public static final String MESSAGE_ADD_WEIGHT_SUCCESS =
            "Successfully added weight to client!\n---------------------------------\n%1$s";
    public static final String MESSAGE_DELETE_WEIGHT_SUCCESS =
            "Successfully removed weight from client!\n--------------------------------------\n%1$s";

    public static final String MESSAGE_NO_INDEX_WEIGHT = String.format(MESSAGE_NO_INDEX, MESSAGE_USAGE);

    // Represents an empty weight field in the UI that needs extra clarity (instead of not showing the field at all)
    public static final String EMPTY_FIELD = "Weight: " + Messages.EMPTY_FIELD;
}
