package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ADDRESS;
import static seedu.address.logic.parser.CliSyntax.PREFIX_EMAIL;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NOTE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PHONE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TAG;

import seedu.address.commons.util.ToStringBuilder;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.person.Person;
import seedu.address.model.person.Phone;

/**
 * Adds a person to the address book.
 */
public class AddCommand extends Command {
    public static final String COMMAND_WORD = "add";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Adds a client to FitBook. \n"
            + "Parameters: "
            + PREFIX_NAME + "NAME "
            + PREFIX_PHONE + "PHONE "
            + "[" + PREFIX_EMAIL + "EMAIL] "
            + "[" + PREFIX_ADDRESS + "ADDRESS] "
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
    public static final String MESSAGE_WARN = "\n\nWARNING: %s";

    public static final String MESSAGE_DUPLICATE_PERSON = "This person already exists in the address book";
    private final Person toAdd;

    /**
     * Creates an AddCommand to add the specified {@code Person}
     */
    public AddCommand(Person person) {
        requireNonNull(person);
        toAdd = person;
    }

    /**
     * Gets the warning message for the command's execution.
     *
     * @return The warning message, or an empty string if none.
     */
    private String getMessageWarn() {
        boolean isPhoneOfExpectedFormat = toAdd.getPhone().isExpectedFormat();

        if (!isPhoneOfExpectedFormat) {
            return String.format(MESSAGE_WARN, Phone.MESSAGE_EXPECTED);
        }

        return "";
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);

        if (model.hasPerson(toAdd)) {
            throw new CommandException(MESSAGE_DUPLICATE_PERSON);
        }

        model.addPerson(toAdd);

        String messageSuccess = String.format(MESSAGE_SUCCESS, toAdd.getFormattedMessage());
        String messageWarn = this.getMessageWarn();

        String messageResult = String.format("%s%s", messageSuccess, messageWarn);

        return new CommandResult(messageResult);
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof AddCommand)) {
            return false;
        }

        AddCommand otherAddCommand = (AddCommand) other;
        return toAdd.equals(otherAddCommand.toAdd);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("toAdd", toAdd)
                .toString();
    }
}
