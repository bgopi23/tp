package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.messages.DeleteCommandMessages.MESSAGE_DELETE_PERSON_SUCCESS;
import static seedu.address.logic.messages.DeleteCommandMessages.MESSAGE_INVALID_INDEX_DELETE;

import java.util.List;

import seedu.address.commons.core.index.Index;
import seedu.address.commons.util.ToStringBuilder;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.person.Person;

/**
 * Deletes a client identified using it's displayed index from the address book.
 */
public class DeleteCommand extends Command {

    private final Index targetIndex;

    public DeleteCommand(Index targetIndex) {
        this.targetIndex = targetIndex;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        List<Person> lastShownList = model.getFilteredPersonList();
        int listIndex = this.targetIndex.getZeroBased();

        if (listIndex >= lastShownList.size()) {
            throw new CommandException(MESSAGE_INVALID_INDEX_DELETE);
        }

        Person personToDelete = lastShownList.get(listIndex);
        model.deletePerson(personToDelete);
        return new CommandResult(String.format(MESSAGE_DELETE_PERSON_SUCCESS,
                personToDelete.getFormattedMessage()));
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof DeleteCommand)) {
            return false;
        }

        DeleteCommand otherDeleteCommand = (DeleteCommand) other;
        return this.targetIndex.equals(otherDeleteCommand.targetIndex);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("targetIndex", this.targetIndex)
                .toString();
    }
}
