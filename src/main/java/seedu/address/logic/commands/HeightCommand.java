package seedu.address.logic.commands;

import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;
import static seedu.address.model.Model.PREDICATE_SHOW_ALL_PERSONS;

import java.util.List;
import java.util.TreeMap;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.Messages;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.person.Height;
import seedu.address.model.person.HeightMap;
import seedu.address.model.person.Person;

/**
 * Changes the height of an existing person in the address book.
 */
public class HeightCommand extends Command {

    public static final String COMMAND_WORD = "height";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Edits the height (in centimeters) of the person identified "
            + "by the index number used in the last person listing. "
            + "Existing height will be overwritten by the input.\n"
            + "Parameters: INDEX (must be a positive float) "
            + "w/ HEIGHT\n"
            + "Example: " + COMMAND_WORD + " 1 "
            + "w/ 182";

    public static final String MESSAGE_ADD_HEIGHT_SUCCESS =
            "Successfully added height to client!\n---------------------------------\n%1$s";

    public static final String MESSAGE_DELETE_HEIGHT_SUCCESS =
            "Successfully removed height from client!\n--------------------------------------\n%1$s";

    private final Index index;
    private final Height height;

    /**
     * @param index of the person in the filtered person list to edit the height
     * @param height of the person to be updated to
     */
    public HeightCommand(Index index, Height height) {
        requireAllNonNull(index, height);

        this.index = index;
        this.height = height;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        List<Person> lastShownList = model.getFilteredPersonList();

        if (index.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
        }

        Person personToEdit = lastShownList.get(index.getZeroBased());

        TreeMap toEditHeightMap = new TreeMap(personToEdit.getHeights());
        if (this.height.getValue() == 0f) {
            toEditHeightMap.pollLastEntry();
        } else {
            toEditHeightMap.put(HeightMap.getTimeOfExecution(), this.height);
        }

        Person editedPerson = new Person(
                personToEdit.getName(), personToEdit.getPhone(), personToEdit.getEmail(),
                personToEdit.getAddress(), toEditHeightMap, personToEdit.getWeight(),
                personToEdit.getNote(), personToEdit.getTags());

        model.setPerson(personToEdit, editedPerson);
        model.updateFilteredPersonList(PREDICATE_SHOW_ALL_PERSONS);

        return new CommandResult(generateSuccessMessage(editedPerson));
    }

    /**
     * Generates a command execution success message based on whether
     * the height is added to or removed from
     * {@code personToEdit}.
     */
    private String generateSuccessMessage(Person personToEdit) {
        String message = !(height.getValue() == 0f) ? MESSAGE_ADD_HEIGHT_SUCCESS : MESSAGE_DELETE_HEIGHT_SUCCESS;
        return String.format(message, personToEdit.getFormattedMessage());
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof HeightCommand)) {
            return false;
        }

        HeightCommand e = (HeightCommand) other;
        return this.index.equals(e.index)
                && this.height.equals(e.height);
    }
}
