package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.messages.ListCommandMessages.MESSAGE_ALL_CLIENTS_LISTED;
import static seedu.address.logic.messages.ListCommandMessages.MESSAGE_NO_CLIENTS_TO_LIST;
import static seedu.address.logic.messages.ListCommandMessages.MESSAGE_ONE_CLIENT_LISTED;
import static seedu.address.model.Model.PREDICATE_SHOW_ALL_PERSONS;

import seedu.address.model.Model;

/**
 * Lists all persons in the address book to the user.
 */
public class ListCommand extends Command {

    @Override
    public CommandResult execute(Model model) {
        requireNonNull(model);
        model.updateFilteredPersonList(PREDICATE_SHOW_ALL_PERSONS);
        int listSize = model.getFilteredPersonList().size();

        if (listSize == 0) {
            return new CommandResult(MESSAGE_NO_CLIENTS_TO_LIST);
        }

        if (listSize == 1) {
            return new CommandResult(MESSAGE_ONE_CLIENT_LISTED);
        }

        return new CommandResult(String.format(MESSAGE_ALL_CLIENTS_LISTED, listSize));
    }
}
