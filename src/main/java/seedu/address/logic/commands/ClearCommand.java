package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.messages.ClearCommandMessages.MESSAGE_CONFIRM;
import static seedu.address.logic.messages.ClearCommandMessages.MESSAGE_SUCCESS;

import seedu.address.model.AddressBook;
import seedu.address.model.Model;

/**
 * Clears the address book.
 */
public class ClearCommand extends Command {

    private final boolean confirmed;

    public ClearCommand(boolean confirmed) {
        this.confirmed = confirmed;
    }

    @Override
    public CommandResult execute(Model model) {
        requireNonNull(model);

        if (!confirmed) {
            return new CommandResult(MESSAGE_CONFIRM);
        } else {
            model.setAddressBook(new AddressBook());
            return new CommandResult(MESSAGE_SUCCESS);
        }
    }
}
