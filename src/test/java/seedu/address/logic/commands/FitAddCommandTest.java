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
import java.util.List;
import java.util.Set;

import org.junit.jupiter.api.Test;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.messages.FitAddCommandMessages;
import seedu.address.model.AddressBook;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.exercise.Exercise;
import seedu.address.model.exercise.ExerciseSet;
import seedu.address.model.person.Person;

public class FitAddCommandTest {

    private Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());

    @Test
    public void execute_validIndexAndExercise_success() {
        Person personToEdit = this.model.getFilteredPersonList().get(INDEX_FIRST_PERSON.getZeroBased());
        Exercise exerciseToAdd = new Exercise("Test Exercise", 3, 10, 60);
        Set<Exercise> exercisesToAdd = new HashSet<>(List.of(exerciseToAdd));
        FitAddCommand fitAddCommand = new FitAddCommand(INDEX_FIRST_PERSON, exercisesToAdd);

        String expectedMessage = FitAddCommandMessages.MESSAGE_ADD_EXERCISE_SUCCESS;

        Model expectedModel = new ModelManager(new AddressBook(this.model.getAddressBook()), new UserPrefs());

        Person editedPerson = new Person(personToEdit.getName(), personToEdit.getPhone(), personToEdit.getEmail(),
            personToEdit.getAddress(), personToEdit.getWeights(), personToEdit.getHeight(),
            personToEdit.getNote(), personToEdit.getTags(),
            new ExerciseSet(new HashSet<>(List.of(exerciseToAdd))));

        expectedModel.setPerson(personToEdit, editedPerson);

        assertCommandSuccess(fitAddCommand, this.model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_invalidPersonIndexUnfilteredList_failure() {
        Index outOfBoundIndex = Index.fromOneBased(this.model.getFilteredPersonList().size() + 1);
        Set<Exercise> exercisesToAdd = new HashSet<>();
        FitAddCommand fitAddCommand = new FitAddCommand(outOfBoundIndex, exercisesToAdd);

        assertCommandFailure(fitAddCommand, this.model, FitAddCommandMessages.MESSAGE_INVALID_INDEX_FITADD);
    }

    @Test
    public void execute_invalidPersonIndexFilteredList_failure() {
        showPersonAtIndex(this.model, INDEX_FIRST_PERSON);
        Index outOfBoundIndex = INDEX_SECOND_PERSON;
        // ensures that outOfBoundIndex is still in bounds of address book list
        assertTrue(outOfBoundIndex.getZeroBased() < this.model.getAddressBook().getPersonList().size());

        Set<Exercise> exercisesToAdd = new HashSet<>();
        FitAddCommand fitAddCommand = new FitAddCommand(outOfBoundIndex, exercisesToAdd);

        assertCommandFailure(fitAddCommand, this.model, FitAddCommandMessages.MESSAGE_INVALID_INDEX_FITADD);
    }

    @Test
    public void equals() {
        Set<Exercise> exercisesToAdd1 = new HashSet<>(Arrays.asList(
            new Exercise("Exercise 1", 3, 10, 60)));
        Set<Exercise> exercisesToAdd2 = new HashSet<>(Arrays.asList(
            new Exercise("Exercise 2", 4, 12, 90)));
        FitAddCommand addExercise1Command = new FitAddCommand(INDEX_FIRST_PERSON, exercisesToAdd1);
        FitAddCommand addExercise2Command = new FitAddCommand(INDEX_SECOND_PERSON, exercisesToAdd2);

        // same object -> returns true
        assertEquals(addExercise1Command, addExercise1Command);

        // same values -> returns true
        FitAddCommand addExercise1CommandCopy = new FitAddCommand(INDEX_FIRST_PERSON, exercisesToAdd1);
        assertEquals(addExercise1Command, addExercise1CommandCopy);

        // different types -> returns false
        assertNotEquals(1, addExercise1Command);

        // null -> returns false
        assertNotEquals(null, addExercise1Command);

        // different index -> returns false
        assertNotEquals(addExercise1Command, addExercise2Command);

        // different exercises -> returns false
        FitAddCommand addDifferentExerciseCommand = new FitAddCommand(INDEX_FIRST_PERSON, exercisesToAdd2);
        assertNotEquals(addExercise1Command, addDifferentExerciseCommand);
    }

}
