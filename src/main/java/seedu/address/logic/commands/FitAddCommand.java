package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.logic.messages.FitAddCommandMessages;
import seedu.address.model.Model;
import seedu.address.model.exercise.Exercise;
import seedu.address.model.exercise.ExerciseSet;
import seedu.address.model.person.Person;

/**
 * Adds a new exercise or overwrites an existing exercise of a person in the address book.
 */
public class FitAddCommand extends Command {
    private final Index index;
    private final Exercise exercise;

    /**
     * Constructs a new FitAddCommand instance.
     *
     * @param index    The index of the person in the filtered person list to add the exercise to
     * @param exercise The exercise to be added to the person
     */
    public FitAddCommand(Index index, Exercise exercise) {
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
            throw new CommandException(FitAddCommandMessages.MESSAGE_INVALID_INDEX_FITADD);
        }

        Person personToEdit = lastShownList.get(index.getZeroBased());

        Exercise exerciseToAdd = exercise;
        Set<Exercise> updatedExercises = new HashSet<>(personToEdit.getExerciseSet().getValue());

        for (Exercise e : updatedExercises) {
            if (e.getName().equals(exercise.getName())) {
                String name = exercise.getName();
                Integer sets = exercise.getSets() != Exercise.DEFAULT_SETS ? exercise.getSets() : e.getSets();
                Integer reps = exercise.getReps() != Exercise.DEFAULT_REPS ? exercise.getReps() : e.getReps();
                Integer rest = exercise.getRest() != Exercise.DEFAULT_REST ? exercise.getRest() : e.getRest();

                exerciseToAdd = new Exercise(name, sets, reps, rest);
                break;
            }
        }

        updatedExercises.remove(exerciseToAdd);
        updatedExercises.add(exerciseToAdd);

        ExerciseSet updatedExerciseSet = new ExerciseSet(updatedExercises);

        Person editedPerson = new Person(personToEdit.getName(), personToEdit.getPhone(), personToEdit.getEmail(),
            personToEdit.getAddress(), personToEdit.getWeights(), personToEdit.getHeight(),
            personToEdit.getNote(), personToEdit.getTags(), updatedExerciseSet);

        model.setPerson(personToEdit, editedPerson);
        model.updateFilteredPersonList(Model.PREDICATE_SHOW_ALL_PERSONS);

        return new CommandResult(
            String.format(FitAddCommandMessages.MESSAGE_ADD_EXERCISE_SUCCESS, exerciseToAdd.getName()));
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof FitAddCommand)) {
            return false;
        }

        FitAddCommand otherFitAddCommand = (FitAddCommand) other;
        return index.equals(otherFitAddCommand.index)
            && exercise.equals(otherFitAddCommand.exercise);
    }
}
