package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.messages.FitDeleteCommandMessages.MESSAGE_EXERCISE_NAME_DOES_NOT_EXIST;
import static seedu.address.logic.messages.FitDeleteCommandMessages.MESSAGE_INVALID_INDEX_FITDELETE;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.logic.messages.FitDeleteCommandMessages;
import seedu.address.model.Model;
import seedu.address.model.exercise.Exercise;
import seedu.address.model.exercise.ExerciseSet;
import seedu.address.model.person.Person;

/**
 * Deletes an exercise from a person in FitBook.
 */
public class FitDeleteCommand extends Command {
    private final Index index;
    private final Optional<String> exerciseName;

    /**
     * Constructs a new FitDeleteCommand instance.
     *
     * @param index The index of the person in the filtered person list to delete the exercise from
     * @param exerciseName The optional exercise name to be deleted from the person
     */
    public FitDeleteCommand(Index index, Optional<String> exerciseName) {
        requireNonNull(index);
        requireNonNull(exerciseName);

        this.index = index;
        this.exerciseName = exerciseName;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        List<Person> lastShownList = model.getFilteredPersonList();
        int listIndex = this.index.getZeroBased();

        if (listIndex >= lastShownList.size()) {
            throw new CommandException(MESSAGE_INVALID_INDEX_FITDELETE);
        }

        Person personToEdit = lastShownList.get(listIndex);
        Person editedPerson = getEditedPerson(personToEdit);

        model.setPerson(personToEdit, editedPerson);

        String commandResult = FitDeleteCommandMessages.MESSAGE_DELETE_ALL_EXERCISES_SUCCESS;
        if (this.exerciseName.isPresent()) {
            commandResult =
                String.format(FitDeleteCommandMessages.MESSAGE_DELETE_EXERCISE_SUCCESS, this.exerciseName.get());
        }

        return new CommandResult(commandResult);
    }

    private Person getEditedPerson(Person personToEdit) throws CommandException {
        ExerciseSet updatedExerciseSet = new ExerciseSet(new HashSet<>());

        if (this.exerciseName.isPresent()) {
            Exercise exerciseToDelete =
                new Exercise(this.exerciseName.get(), Exercise.DEFAULT_SETS, Exercise.DEFAULT_REPS,
                    Exercise.DEFAULT_BREAK);
            Set<Exercise> updatedExercises = new HashSet<>(personToEdit.getExerciseSet().getValue());

            if (!updatedExercises.contains(exerciseToDelete)) {
                throw new CommandException(String.format(MESSAGE_EXERCISE_NAME_DOES_NOT_EXIST,
                    exerciseToDelete.getName()));
            }

            updatedExercises.remove(exerciseToDelete);
            updatedExerciseSet = new ExerciseSet(updatedExercises);
        } else {
            Set<Exercise> updatedExercises = new HashSet<>(personToEdit.getExerciseSet().getValue());

            if (updatedExercises.isEmpty()) {
                throw new CommandException(FitDeleteCommandMessages.MESSAGE_DELETE_ALL_EXERCISES_FAILURE);
            }
        }

        return new Person(personToEdit.getName(), personToEdit.getPhone(), personToEdit.getEmail(),
            personToEdit.getAddress(), personToEdit.getWeights(), personToEdit.getHeight(),
            personToEdit.getNote(), personToEdit.getTags(), updatedExerciseSet);
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
        return this.index.equals(otherFitDeleteCommand.index)
            && this.exerciseName.equals(otherFitDeleteCommand.exerciseName);
    }
}
