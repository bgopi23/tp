package seedu.address.logic.messages;

import static seedu.address.logic.parser.CliSyntax.PREFIX_ADDRESS;
import static seedu.address.logic.parser.CliSyntax.PREFIX_EMAIL;
import static seedu.address.logic.parser.CliSyntax.PREFIX_HEIGHT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NOTE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PHONE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TAG;
import static seedu.address.logic.parser.CliSyntax.PREFIX_WEIGHT;

/**
 * Messages used by FindCommand and associated classes.
 */
public class FindCommandMessages extends Messages {

    public static final String COMMAND_WORD = "find";
    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Lists all clients whose specified attribute contains the specified keyword.\n"
            + "Parameters: "
            + "[" + PREFIX_NAME + "NAME] "
            + "[" + PREFIX_PHONE + "PHONE] "
            + "[" + PREFIX_EMAIL + "EMAIL] "
            + "[" + PREFIX_ADDRESS + "ADDRESS] "
            + "[" + PREFIX_WEIGHT + "WEIGHT] "
            + "[" + PREFIX_HEIGHT + "HEIGHT] "
            + "[" + PREFIX_NOTE + "NOTE] "
            + "[" + PREFIX_TAG + "TAG] "
            + "\nExample: " + COMMAND_WORD + " "
            + PREFIX_EMAIL + "lewis@hotmail.com";
    public static final String MESSAGE_INVALID_COMMAND_FORMAT_FIND = String.format(MESSAGE_INVALID_COMMAND_FORMAT,
            MESSAGE_USAGE);
    public static final String MESSAGE_PERSONS_FOUND_OVERVIEW = "%1$d clients found!";
    public static final String MESSAGE_NO_CLIENTS_FOUND = "No clients found!";
    public static final String MESSAGE_ONE_CLIENT_FOUND = "1 client found!";
    public static final String MESSAGE_USAGE_RANGE = COMMAND_WORD
            + ": Finds using a specified range that is comma-delimited."
            + " FROM parameter must be less than or equal to TO parameter.\n"
            + "Parameters: FROM, TO (both of them must be a positive number greater than or equals to 0).\n"
            + "Example: " + COMMAND_WORD + " w/70, 80";
}
