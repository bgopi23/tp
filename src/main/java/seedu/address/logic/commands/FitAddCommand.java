package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.logic.messages.FitAddCommandMessages;
import seedu.address.model.Model;
import seedu.address.model.exercise.Exercise;
import seedu.address.model.exercise.ExerciseSet;
import seedu.address.model.exercise.ExerciseToAdd;
import seedu.address.model.person.Person;

/**
 * Adds a new exercise or overwrites an existing exercise of a person in FitBook.
 */
public class FitAddCommand extends Command {
    public static final Set<ExerciseToAdd> DEFAULT_ARM_EXERCISES = new HashSet<>(Arrays.asList(
        new ExerciseToAdd("bicep curls", Optional.of(3), Optional.of(10), Optional.of(60)),
        new ExerciseToAdd("tricep dips", Optional.of(3), Optional.of(12), Optional.of(60)),
        new ExerciseToAdd("push-ups", Optional.of(3), Optional.of(15), Optional.of(90))
    ));

    public static final Set<ExerciseToAdd> DEFAULT_LEG_EXERCISES = new HashSet<>(Arrays.asList(
        new ExerciseToAdd("squats", Optional.of(4), Optional.of(15), Optional.of(90)),
        new ExerciseToAdd("lunges", Optional.of(3), Optional.of(12), Optional.of(60)),
        new ExerciseToAdd("calf raises", Optional.of(3), Optional.of(20), Optional.of(60))
    ));

    public static final Set<ExerciseToAdd> DEFAULT_CHEST_EXERCISES = new HashSet<>(Arrays.asList(
        new ExerciseToAdd("bench press", Optional.of(4), Optional.of(8), Optional.of(120)),
        new ExerciseToAdd("push-ups", Optional.of(3), Optional.of(15), Optional.of(90)),
        new ExerciseToAdd("chest fly", Optional.of(3), Optional.of(10), Optional.of(90))
    ));

    public static final Set<ExerciseToAdd> DEFAULT_BACK_EXERCISES = new HashSet<>(Arrays.asList(
        new ExerciseToAdd("pull-ups", Optional.of(3), Optional.of(8), Optional.of(120)),
        new ExerciseToAdd("bent-over rows", Optional.of(3), Optional.of(10), Optional.of(90)),
        new ExerciseToAdd("lat pull-downs", Optional.of(3), Optional.of(12), Optional.of(60))
    ));

    public static final Set<ExerciseToAdd> DEFAULT_SHOULDER_EXERCISES = new HashSet<>(Arrays.asList(
        new ExerciseToAdd("shoulder press", Optional.of(3), Optional.of(10), Optional.of(90)),
        new ExerciseToAdd("lateral raises", Optional.of(3), Optional.of(12), Optional.of(60)),
        new ExerciseToAdd("front raises", Optional.of(3), Optional.of(10), Optional.of(60))
    ));

    public static final Set<ExerciseToAdd> DEFAULT_ABS_EXERCISES = new HashSet<>(Arrays.asList(
        new ExerciseToAdd("crunches", Optional.of(3), Optional.of(20), Optional.of(60)),
        new ExerciseToAdd("plank", Optional.of(3), Optional.of(60), Optional.of(90)),
        new ExerciseToAdd("russian twists", Optional.of(3), Optional.of(15), Optional.of(60))
    ));
    private final Index index;
    private final Set<ExerciseToAdd> exercisesToAdd;

    /**
     * Constructs a new FitAddCommand instance.
     *
     * @param index          The index of the person in the filtered person list to add the exercise to
     * @param exercisesToAdd The set of exercises to be added to the person
     */
    public FitAddCommand(Index index, Set<ExerciseToAdd> exercisesToAdd) {
        requireNonNull(index);
        requireNonNull(exercisesToAdd);

        this.index = index;
        this.exercisesToAdd = exercisesToAdd;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        List<Person> lastShownList = model.getFilteredPersonList();

        if (this.index.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(FitAddCommandMessages.MESSAGE_INVALID_INDEX_FITADD);
        }

        Person personToEdit = lastShownList.get(this.index.getZeroBased());

        Set<Exercise> updatedExercises = new HashSet<>(personToEdit.getExerciseSet().getValue());

        for (ExerciseToAdd exerciseToAdd : this.exercisesToAdd) {
            Exercise newExercise = getExercise(exerciseToAdd, updatedExercises);

            updatedExercises.remove(newExercise);
            updatedExercises.add(newExercise);
        }

        ExerciseSet updatedExerciseSet = new ExerciseSet(updatedExercises);

        Person editedPerson = new Person(personToEdit.getName(), personToEdit.getPhone(), personToEdit.getEmail(),
            personToEdit.getAddress(), personToEdit.getWeights(), personToEdit.getHeight(),
            personToEdit.getNote(), personToEdit.getTags(), updatedExerciseSet);

        model.setPerson(personToEdit, editedPerson);

        return new CommandResult(FitAddCommandMessages.MESSAGE_ADD_EXERCISE_SUCCESS);
    }

    private static Exercise getUpdatedExercise(ExerciseToAdd exerciseToAdd, Exercise existingExercise,
                                               Exercise newExercise) {
        if (existingExercise.equals(newExercise)) {
            String newName = exerciseToAdd.getName();
            Integer newSets = exerciseToAdd.getSets().orElse(existingExercise.getSets());
            Integer newReps = exerciseToAdd.getReps().orElse(existingExercise.getReps());
            Integer newBreakBetweenSets =
                exerciseToAdd.getBreakBetweenSets().orElse(existingExercise.getBreakBetweenSets());

            return new Exercise(newName, newSets, newReps, newBreakBetweenSets);
        }

        return newExercise;
    }

    private static Exercise getExercise(ExerciseToAdd exerciseToAdd, Set<Exercise> updatedExercises) {
        String name = exerciseToAdd.getName();
        Integer sets = exerciseToAdd.getSets().orElse(Exercise.DEFAULT_SETS);
        Integer reps = exerciseToAdd.getReps().orElse(Exercise.DEFAULT_REPS);
        Integer breakBetweenSets = exerciseToAdd.getBreakBetweenSets().orElse(Exercise.DEFAULT_BREAK);

        Exercise newExercise =
            new Exercise(name, sets, reps, breakBetweenSets);

        if (updatedExercises.contains(newExercise)) {
            for (Exercise existingExercise : updatedExercises) {
                newExercise = getUpdatedExercise(exerciseToAdd, existingExercise, newExercise);
            }
        }
        return newExercise;
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
        return this.index.equals(otherFitAddCommand.index)
            && this.exercisesToAdd.equals(otherFitAddCommand.exercisesToAdd);
    }
}
