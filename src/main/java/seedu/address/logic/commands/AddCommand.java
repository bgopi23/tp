package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.messages.AddCommandMessages.MESSAGE_DUPLICATE_PERSON;
import static seedu.address.logic.messages.AddCommandMessages.MESSAGE_SUCCESS;
import static seedu.address.logic.messages.Messages.MESSAGE_PHONE_WARN;

import seedu.address.commons.util.ToStringBuilder;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.person.Person;

/**
 * Adds a person to the address book.
 */
public class AddCommand extends Command {
    private final Person toAdd;

    /**
     * Creates an AddCommand to add the specified {@code Person}
     */
    public AddCommand(Person person) {
        requireNonNull(person);
        this.toAdd = person;
    }

    /**
     * Gets the warning message for the command's execution.
     *
     * @return The warning message, or an empty string if none.
     */
    private String getMessageWarn() {
        boolean isPhoneOfExpectedFormat = this.toAdd.getPhone().isExpectedFormat();

        if (!isPhoneOfExpectedFormat) {
            return MESSAGE_PHONE_WARN;
        }

        return "";
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);

        if (model.hasPerson(this.toAdd)) {
            throw new CommandException(MESSAGE_DUPLICATE_PERSON);
        }

        model.addPerson(this.toAdd);

        String messageSuccess = String.format(MESSAGE_SUCCESS, this.toAdd.getFormattedMessage());
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
        return this.toAdd.equals(otherAddCommand.toAdd);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("toAdd", this.toAdd)
                .toString();
    }
}
