package seedu.address.logic.messages;

import seedu.address.logic.parser.CliSyntax;

/**
 * Messages used by ListCommand and associated classes.
 */
public class HeightCommandMessages extends Messages {

    public static final String COMMAND_WORD = "height";
    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Edits the height (in centimeters) of the person identified "
            + "by the index number used in the last person listing.\n"
            + "Existing height will be overwritten by the input.\n"
            + "Parameters: INDEX (must be a positive float) "
            + CliSyntax.PREFIX_HEIGHT + "HEIGHT\n"
            + "Example: " + COMMAND_WORD + " 1 "
            + CliSyntax.PREFIX_HEIGHT + "172.5";
    public static final String MESSAGE_ADD_HEIGHT_SUCCESS =
            "Successfully added height to client!\n---------------------------------\n%1$s";
    public static final String MESSAGE_DELETE_HEIGHT_SUCCESS =
            "Successfully removed height from client!\n--------------------------------------\n%1$s";
}
