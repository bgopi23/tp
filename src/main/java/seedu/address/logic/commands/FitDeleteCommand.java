package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.logic.messages.FitDeleteCommandMessages;
import seedu.address.model.Model;
import seedu.address.model.exercise.Exercise;
import seedu.address.model.exercise.ExerciseSet;
import seedu.address.model.person.Person;

/**
 * Adds a new exercise or overwrites an existing exercise of a person in the address book.
 */
public class FitDeleteCommand extends Command {
    private final Index index;
    private final Exercise exercise;

    /**
     * Constructs a new FitDeleteCommand instance.
     *
     * @param index    of the person in the filtered person list to add exercise.
     * @param exercise exercise to be added to the person.
     */
    public FitDeleteCommand(Index index, Exercise exercise) {
        requireNonNull(index);
        requireNonNull(exercise);

        this.index = index;
        this.exercise = exercise;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        List<Person> lastShownList = model.getFilteredPersonList();

        if (index.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(FitDeleteCommandMessages.MESSAGE_INVALID_INDEX_FITDELETE);
        }

        Person personToEdit = lastShownList.get(index.getZeroBased());

        Set<Exercise> updatedExercises = new HashSet<>(personToEdit.getExerciseSet().getValue());

        if (!updatedExercises.contains(exercise)) {
            throw new CommandException(
                String.format(FitDeleteCommandMessages.MESSAGE_EXERCISE_NAME_DOES_NOT_EXIST, exercise.getName()));
        }
        updatedExercises.remove(exercise);

        ExerciseSet updatedExerciseSet = new ExerciseSet(updatedExercises);

        Person editedPerson = new Person(personToEdit.getName(), personToEdit.getPhone(), personToEdit.getEmail(),
            personToEdit.getAddress(), personToEdit.getWeights(), personToEdit.getHeight(),
            personToEdit.getNote(), personToEdit.getTags(), updatedExerciseSet);

        model.setPerson(personToEdit, editedPerson);
        model.updateFilteredPersonList(Model.PREDICATE_SHOW_ALL_PERSONS);

        return new CommandResult(
            String.format(FitDeleteCommandMessages.MESSAGE_DELETE_EXERCISE_SUCCESS, exercise.getName()));
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof FitDeleteCommand)) {
            return false;
        }

        FitDeleteCommand otherFitDeleteCommand = (FitDeleteCommand) other;
        return index.equals(otherFitDeleteCommand.index)
            && exercise.equals(otherFitDeleteCommand.exercise);
    }
}
