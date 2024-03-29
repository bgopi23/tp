package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_PERSON;
import static seedu.address.testutil.TypicalIndexes.INDEX_SECOND_PERSON;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBookWithoutEmail;

import org.junit.jupiter.api.Test;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.Messages;
import seedu.address.model.AddressBook;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.person.height.Height;
import seedu.address.model.person.Person;
import seedu.address.model.person.height.HeightEntry;
import seedu.address.testutil.PersonBuilder;

import java.util.AbstractMap;

class HeightCommandTest {

    private static final String HEIGHT_STUB = "2024-01-20T10:15:33=169f";
    private static final String UNINITIALIZED_HEIGHT = "2024-01-20T10:15:33=0f";

    private Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());
    private Model modelWithoutEmail = new ModelManager(getTypicalAddressBookWithoutEmail(), new UserPrefs());
    @Test
    public void execute_addHeightUnfilteredList_success() {
        Person firstPerson = model.getFilteredPersonList().get(INDEX_FIRST_PERSON.getZeroBased());
        Person editedPerson = new PersonBuilder(firstPerson).withHeights(HEIGHT_STUB).build();

        HeightCommand heightCommand = new HeightCommand(INDEX_FIRST_PERSON,
                new HeightEntry(editedPerson.getLatestHeight()));

        String expectedMessage = String.format(HeightCommand.MESSAGE_ADD_HEIGHT_SUCCESS,
                editedPerson.getFormattedMessage());

        Model expectedModel = new ModelManager(new AddressBook(model.getAddressBook()), new UserPrefs());
        expectedModel.setPerson(firstPerson, editedPerson);

        assertCommandSuccess(heightCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_deleteHeightUnfilteredList_success() {
        Person firstPerson = model.getFilteredPersonList().get(INDEX_FIRST_PERSON.getZeroBased());
        Person editedPerson = new PersonBuilder(firstPerson).withHeights(UNINITIALIZED_HEIGHT).build();

        HeightCommand heightCommand = new HeightCommand(INDEX_FIRST_PERSON,
                new HeightEntry(editedPerson.getLatestHeight()));

        String expectedMessage = String.format(HeightCommand.MESSAGE_DELETE_HEIGHT_SUCCESS,
               editedPerson.getFormattedMessage());

        Model expectedModel = new ModelManager(new AddressBook(model.getAddressBook()), new UserPrefs());
        expectedModel.setPerson(firstPerson, editedPerson);

        assertCommandSuccess(heightCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_addHeightUnfilteredListWithoutEmail_success() {
        Person firstPerson = modelWithoutEmail.getFilteredPersonList().get(INDEX_FIRST_PERSON.getZeroBased());
        Person editedPerson = new PersonBuilder(firstPerson).withHeights(HEIGHT_STUB).build();

        HeightCommand heightCommand = new HeightCommand(INDEX_FIRST_PERSON,
                new HeightEntry(editedPerson.getLatestHeight()));

        String expectedMessage = String.format(HeightCommand.MESSAGE_ADD_HEIGHT_SUCCESS,
                editedPerson.getFormattedMessage());

        Model expectedModel = new ModelManager(new AddressBook(modelWithoutEmail.getAddressBook()), new UserPrefs());
        expectedModel.setPerson(firstPerson, editedPerson);

        assertCommandSuccess(heightCommand, modelWithoutEmail, expectedMessage, expectedModel);
    }

    @Test
    public void execute_invalidIndex_failure() {
        Person firstPerson = modelWithoutEmail.getFilteredPersonList().get(INDEX_FIRST_PERSON.getZeroBased());

        Index invalidIndex = Index.fromOneBased(model.getFilteredPersonList().size() + 1);
        HeightCommand heightCommand = new HeightCommand(invalidIndex, new HeightEntry(firstPerson.getLatestHeight()));
        assertCommandFailure(heightCommand, model, Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
    }

    @Test
    public void equals() {
        HeightEntry heightEntry = new HeightEntry(
                new AbstractMap.SimpleEntry<>(HeightEntry.getTimeOfExecution(), new Height(180f)));

        HeightCommand firstHeight = new HeightCommand(INDEX_FIRST_PERSON, heightEntry);
        HeightCommand secondHeight = new HeightCommand(INDEX_SECOND_PERSON, heightEntry);
        HeightCommand firstHeightClone = new HeightCommand(INDEX_FIRST_PERSON, heightEntry);

        // same height => return true
        assertEquals(firstHeight, firstHeight);

        // same height details => return true
        assertEquals(firstHeight, firstHeightClone);

        // same height but different person => return false
        assertNotEquals(firstHeight, secondHeight);
    }
}
