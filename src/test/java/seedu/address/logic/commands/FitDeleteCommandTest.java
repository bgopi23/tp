package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.logic.commands.CommandTestUtil.showPersonAtIndex;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_PERSON;
import static seedu.address.testutil.TypicalIndexes.INDEX_SECOND_PERSON;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import org.junit.jupiter.api.Test;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.messages.FitDeleteCommandMessages;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.exercise.Exercise;
import seedu.address.model.exercise.ExerciseSet;
import seedu.address.model.person.Person;

public class FitDeleteCommandTest {

    private Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());

    @Test
    public void execute_validIndexAndExerciseName_success() {
        Person personToEdit = this.model.getFilteredPersonList().get(INDEX_FIRST_PERSON.getZeroBased());
        String exerciseName = "Test Exercise";
        Exercise exerciseToDelete = new Exercise(exerciseName, Exercise.DEFAULT_SETS, Exercise.DEFAULT_REPS,
            Exercise.DEFAULT_BREAK);
        Set<Exercise> updatedExercises = new HashSet<>(personToEdit.getExerciseSet().getValue());
        updatedExercises.add(exerciseToDelete);
        Person editedPerson = new Person(personToEdit.getName(), personToEdit.getPhone(), personToEdit.getEmail(),
            personToEdit.getAddress(), personToEdit.getWeights(), personToEdit.getHeight(),
            personToEdit.getNote(), personToEdit.getTags(), new ExerciseSet(updatedExercises));
        this.model.setPerson(personToEdit, editedPerson);

        FitDeleteCommand fitDeleteCommand = new FitDeleteCommand(INDEX_FIRST_PERSON, Optional.of(exerciseName), false);

        String expectedMessage = String.format(FitDeleteCommandMessages.MESSAGE_DELETE_EXERCISE_SUCCESS, exerciseName);

        ModelManager expectedModel = new ModelManager(this.model.getAddressBook(), new UserPrefs());
        Set<Exercise> expectedExercises = new HashSet<>(editedPerson.getExerciseSet().getValue());
        expectedExercises.remove(exerciseToDelete);
        Person updatedPerson = new Person(personToEdit.getName(), personToEdit.getPhone(), personToEdit.getEmail(),
            personToEdit.getAddress(), personToEdit.getWeights(), personToEdit.getHeight(),
            personToEdit.getNote(), personToEdit.getTags(), new ExerciseSet(expectedExercises));
        expectedModel.setPerson(editedPerson, updatedPerson);

        assertCommandSuccess(fitDeleteCommand, this.model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_validIndexAndDeleteAll_success() {
        Person personToEdit = this.model.getFilteredPersonList().get(INDEX_FIRST_PERSON.getZeroBased());
        Set<Exercise> exercises = new HashSet<>(Arrays.asList(
            new Exercise("Exercise 1", 3, 10, 60),
            new Exercise("Exercise 2", 4, 12, 90)
        ));
        Person editedPerson = new Person(personToEdit.getName(), personToEdit.getPhone(), personToEdit.getEmail(),
            personToEdit.getAddress(), personToEdit.getWeights(), personToEdit.getHeight(),
            personToEdit.getNote(), personToEdit.getTags(), new ExerciseSet(exercises));
        this.model.setPerson(personToEdit, editedPerson);

        FitDeleteCommand fitDeleteCommand = new FitDeleteCommand(INDEX_FIRST_PERSON, Optional.empty(), true);

        String expectedMessage = FitDeleteCommandMessages.MESSAGE_DELETE_ALL_EXERCISES_SUCCESS;

        ModelManager expectedModel = new ModelManager(this.model.getAddressBook(), new UserPrefs());
        Person updatedPerson = new Person(personToEdit.getName(), personToEdit.getPhone(), personToEdit.getEmail(),
            personToEdit.getAddress(), personToEdit.getWeights(), personToEdit.getHeight(),
            personToEdit.getNote(), personToEdit.getTags(), new ExerciseSet(new HashSet<>()));
        expectedModel.setPerson(editedPerson, updatedPerson);

        assertCommandSuccess(fitDeleteCommand, this.model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_invalidIndexUnfilteredList_throwsCommandException() {
        Index outOfBoundIndex = Index.fromOneBased(this.model.getFilteredPersonList().size() + 1);
        FitDeleteCommand fitDeleteCommand = new FitDeleteCommand(outOfBoundIndex, Optional.empty(), false);

        assertCommandFailure(fitDeleteCommand, this.model, FitDeleteCommandMessages.MESSAGE_INVALID_INDEX_FITDELETE);
    }

    @Test
    public void execute_invalidIndexFilteredList_throwsCommandException() {
        showPersonAtIndex(this.model, INDEX_FIRST_PERSON);
        Index outOfBoundIndex = INDEX_SECOND_PERSON;
        // ensures that outOfBoundIndex is still in bounds of address book list
        assertTrue(outOfBoundIndex.getZeroBased() < this.model.getAddressBook().getPersonList().size());

        FitDeleteCommand fitDeleteCommand = new FitDeleteCommand(outOfBoundIndex, Optional.empty(), false);

        assertCommandFailure(fitDeleteCommand, this.model, FitDeleteCommandMessages.MESSAGE_INVALID_INDEX_FITDELETE);
    }

    @Test
    public void execute_invalidExerciseName_throwsCommandException() {
        String invalidExerciseName = "Invalid Exercise";
        FitDeleteCommand fitDeleteCommand =
            new FitDeleteCommand(INDEX_FIRST_PERSON, Optional.of(invalidExerciseName), false);

        assertCommandFailure(fitDeleteCommand, this.model,
            String.format(FitDeleteCommandMessages.MESSAGE_EXERCISE_NAME_DOES_NOT_EXIST,
                invalidExerciseName));
    }

    @Test
    public void execute_deleteAllWithNoExercises_throwsCommandException() {
        Person personToEdit = this.model.getFilteredPersonList().get(INDEX_FIRST_PERSON.getZeroBased());
        Person editedPerson = new Person(personToEdit.getName(), personToEdit.getPhone(), personToEdit.getEmail(),
            personToEdit.getAddress(), personToEdit.getWeights(), personToEdit.getHeight(),
            personToEdit.getNote(), personToEdit.getTags(), new ExerciseSet(new HashSet<>()));
        this.model.setPerson(personToEdit, editedPerson);

        FitDeleteCommand fitDeleteCommand = new FitDeleteCommand(INDEX_FIRST_PERSON, Optional.empty(), true);

        assertCommandFailure(fitDeleteCommand, this.model,
                FitDeleteCommandMessages.MESSAGE_DELETE_ALL_EXERCISES_FAILURE);
    }

    @Test
    public void equals() {
        FitDeleteCommand deleteFirstCommand =
            new FitDeleteCommand(INDEX_FIRST_PERSON, Optional.of("Exercise 1"), false);
        FitDeleteCommand deleteSecondCommand =
            new FitDeleteCommand(INDEX_SECOND_PERSON, Optional.of("Exercise 2"), false);
        FitDeleteCommand deleteAllCommand = new FitDeleteCommand(INDEX_FIRST_PERSON, Optional.empty(), true);

        // same object -> returns true
        assertEquals(deleteFirstCommand, deleteFirstCommand);

        // same values -> returns true
        FitDeleteCommand deleteFirstCommandCopy =
            new FitDeleteCommand(INDEX_FIRST_PERSON, Optional.of("Exercise 1"), false);
        assertEquals(deleteFirstCommand, deleteFirstCommandCopy);

        // different types -> returns false
        assertNotEquals(1, deleteFirstCommand);

        // null -> returns false
        assertNotEquals(null, deleteFirstCommand);

        // different index -> returns false
        assertNotEquals(deleteFirstCommand, deleteSecondCommand);

        // different exercise name -> returns false
        FitDeleteCommand deleteFirstCommandDifferentExercise =
            new FitDeleteCommand(INDEX_FIRST_PERSON, Optional.of("Different Exercise"), false);
        assertNotEquals(deleteFirstCommand, deleteFirstCommandDifferentExercise);

        // different delete all flag -> returns false
        assertNotEquals(deleteFirstCommand, deleteAllCommand);
    }
}
