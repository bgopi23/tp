package seedu.address.logic.commands;

import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;
import static seedu.address.model.Model.PREDICATE_SHOW_ALL_PERSONS;

import java.util.List;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.Messages;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.person.Person;
import seedu.address.model.person.Weight;

/**
 * Changes the weight of an existing person in the address book.
 */
public class WeightCommand extends Command {

    public static final String COMMAND_WORD = "weight";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Edits the weight (in kilograms) of the person identified "
            + "by the index number used in the last person listing. "
            + "Existing weight will be overwritten by the input.\n"
            + "Parameters: INDEX (must be a positive float) "
            + "w/ WEIGHT\n"
            + "Example: " + COMMAND_WORD + " 1 "
            + "w/ 92.5";

    public static final String MESSAGE_ADD_WEIGHT_SUCCESS =
            "Successfully added weight to client!\n---------------------------------\n%1$s";

    public static final String MESSAGE_DELETE_WEIGHT_SUCCESS =
            "Successfully removed weight from client!\n--------------------------------------\n%1$s";

    private final Index index;
    private final Weight weight;

    /**
     * @param index of the person in the filtered person list to edit the weight
     * @param weight of the person to be updated to
     */
    public WeightCommand(Index index, Weight weight) {
        requireAllNonNull(index, weight);

        this.index = index;
        this.weight = weight;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        List<Person> lastShownList = model.getFilteredPersonList();

        if (index.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
        }

        Person personToEdit = lastShownList.get(index.getZeroBased());
        Person editedPerson = new Person(
                personToEdit.getName(), personToEdit.getPhone(), personToEdit.getEmail(),
                personToEdit.getAddress(), personToEdit.getLatestHeight(), this.weight,
                personToEdit.getNote(), personToEdit.getTags());

        model.setPerson(personToEdit, editedPerson);
        model.updateFilteredPersonList(PREDICATE_SHOW_ALL_PERSONS);

        return new CommandResult(generateSuccessMessage(editedPerson));
    }

    /**
     * Generates a command execution success message based on whether
     * the weight is added to or removed from
     * {@code personToEdit}.
     */
    private String generateSuccessMessage(Person personToEdit) {
        String message = !(weight.getValue() == 0f) ? MESSAGE_ADD_WEIGHT_SUCCESS : MESSAGE_DELETE_WEIGHT_SUCCESS;
        return String.format(message, personToEdit.getFormattedMessage());
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof WeightCommand)) {
            return false;
        }

        WeightCommand e = (WeightCommand) other;
        return this.index.equals(e.index)
                && this.weight.equals(e.weight);
    }
}
