package seedu.address.logic.messages;

import static seedu.address.logic.parser.CliSyntax.*;
import static seedu.address.logic.parser.CliSyntax.PREFIX_HEIGHT;

/**
 * Messages used by AddCommand and associated classes.
 */
public class AddCommandMessages extends Messages {

    public static final String COMMAND_WORD = "add";
    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Adds a client to FitBook. \n"
            + "Parameters: "
            + PREFIX_NAME + "NAME "
            + PREFIX_PHONE + "PHONE "
            + "[" + PREFIX_EMAIL + "EMAIL] "
            + "[" + PREFIX_ADDRESS + "ADDRESS] "
            + "[" + PREFIX_WEIGHT + "WEIGHT] "
            + "[" + PREFIX_HEIGHT + "HEIGHT] "
            + "[" + PREFIX_NOTE + "NOTE] "
            + "[" + PREFIX_TAG + "TAG]...\n"
            + "Example: " + COMMAND_WORD + " "
            + PREFIX_NAME + "John "
            + PREFIX_PHONE + "98765432 "
            + PREFIX_EMAIL + "john@gmail.com "
            + PREFIX_ADDRESS + "Clementi Ave 2 "
            + PREFIX_NOTE + "BFF "
            + PREFIX_TAG + "friend ";
    public static final String MESSAGE_SUCCESS = "Client successfully added!\n--------------------------\n%1$s";
    public static final String MESSAGE_NO_PARAMETERS_ADD = String.format(MESSAGE_NO_PARAMETERS, MESSAGE_USAGE);
    public static final String MESSAGE_NAME_PARAMETER_MISSING_ADD = String.format(MESSAGE_NAME_PARAMETER_MISSING,
            MESSAGE_USAGE);
    public static final String MESSAGE_PHONE_PARAMETER_MISSING_ADD = String.format(MESSAGE_PHONE_PARAMETER_MISSING,
            MESSAGE_USAGE);
    public static final String MESSAGE_INVALID_PARAMETER_FORMAT_ADD = String.format(MESSAGE_INVALID_PARAMETER_FORMAT,
            MESSAGE_USAGE);
    public static final String MESSAGE_DUPLICATE_PERSON = "This person already exists in the address book";

}
