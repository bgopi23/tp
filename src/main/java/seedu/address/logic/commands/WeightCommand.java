package seedu.address.logic.commands;

import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;
import static seedu.address.logic.messages.WeightCommandMessages.MESSAGE_ADD_WEIGHT_SUCCESS;
import static seedu.address.logic.messages.WeightCommandMessages.MESSAGE_DELETE_WEIGHT_SUCCESS;
import static seedu.address.logic.messages.WeightCommandMessages.MESSAGE_INVALID_INDEX_WEIGHT;
import static seedu.address.model.Model.PREDICATE_SHOW_ALL_PERSONS;
import static seedu.address.model.person.weight.WeightMap.MESSAGE_EMPTY_WEIGHT_MAP;

import java.time.LocalDateTime;
import java.util.List;
import java.util.NavigableMap;
import java.util.TreeMap;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.person.Person;
import seedu.address.model.person.weight.Weight;
import seedu.address.model.person.weight.WeightEntry;

/**
 * Changes the weight of an existing person in the address book.
 */
public class WeightCommand extends Command {

    private final Index index;
    private final WeightEntry weightEntry;
    private LocalDateTime timeOfExecution = null;

    /**
     * @param index of the person in the filtered person list to edit the weight
     * @param weight of the person to be updated to
     */
    public WeightCommand(Index index, WeightEntry weight) {
        requireAllNonNull(index, weight);

        this.index = index;
        this.weightEntry = weight;
    }

    /**
     * @param index of the person in the filtered person list to edit the weight
     * @param weight of the person to be updated to
     * @param timeOfExecution of the weight
     */
    public WeightCommand(Index index, WeightEntry weight, LocalDateTime timeOfExecution) {
        requireAllNonNull(index, weight, timeOfExecution);
        this.index = index;
        this.weightEntry = weight;
        this.timeOfExecution = timeOfExecution;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        List<Person> lastShownList = model.getFilteredPersonList();
        int listIndex = this.index.getZeroBased();

        if (listIndex >= lastShownList.size()) {
            throw new CommandException(MESSAGE_INVALID_INDEX_WEIGHT);
        }

        Person personToEdit = lastShownList.get(listIndex);

        NavigableMap<LocalDateTime, Weight> toEditWeightMap = new TreeMap<>(personToEdit.getWeights());
        if (this.weightEntry.getValue().getValue().isZero()) {
            if (toEditWeightMap.isEmpty()) {
                throw new CommandException(MESSAGE_EMPTY_WEIGHT_MAP);
            }
            toEditWeightMap.pollLastEntry();
        } else {
            // If user created this instance without specifying time of execution.
            if (this.timeOfExecution == null) {
                toEditWeightMap.put(WeightEntry.getTimeOfExecution(), this.weightEntry.getValue().getValue());
            } else {
                toEditWeightMap.put(this.timeOfExecution, this.weightEntry.getValue().getValue());
            }
        }

        Person editedPerson = new Person(
                personToEdit.getName(), personToEdit.getPhone(), personToEdit.getEmail(),
                personToEdit.getAddress(), toEditWeightMap, personToEdit.getHeight(),
                personToEdit.getNote(), personToEdit.getTags(), personToEdit.getExerciseSet());

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
        String message = !(this.weightEntry.getValue().getValue().isZero())
                ? MESSAGE_ADD_WEIGHT_SUCCESS
                : MESSAGE_DELETE_WEIGHT_SUCCESS;
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
                && this.weightEntry.equals(e.weightEntry);
    }
}
