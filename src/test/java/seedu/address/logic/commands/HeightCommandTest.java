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
import seedu.address.model.person.Height;
import seedu.address.model.person.Person;
import seedu.address.testutil.PersonBuilder;

class HeightCommandTest {

    private static final Float WEIGHT_STUB = 92.5f;
    private static final Float UNINITIALIZED_WEIGHT = 0f;

    private Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());
    private Model modelWithoutEmail = new ModelManager(getTypicalAddressBookWithoutEmail(), new UserPrefs());
    @Test
    public void execute_addHeightUnfilteredList_success() {
        Person firstPerson = model.getFilteredPersonList().get(INDEX_FIRST_PERSON.getZeroBased());
        Person editedPerson = new PersonBuilder(firstPerson).withHeight(WEIGHT_STUB).build();

        HeightCommand heightCommand = new HeightCommand(INDEX_FIRST_PERSON,
                new Height(editedPerson.getHeight().getValue()));

        String expectedMessage = String.format(HeightCommand.MESSAGE_ADD_WEIGHT_SUCCESS,
                editedPerson.getFormattedMessage());

        Model expectedModel = new ModelManager(new AddressBook(model.getAddressBook()), new UserPrefs());
        expectedModel.setPerson(firstPerson, editedPerson);

        assertCommandSuccess(heightCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_deleteHeightUnfilteredList_success() {
        Person firstPerson = model.getFilteredPersonList().get(INDEX_FIRST_PERSON.getZeroBased());
        Person editedPerson = new PersonBuilder(firstPerson).withHeight(UNINITIALIZED_WEIGHT).build();

        HeightCommand heightCommand = new HeightCommand(INDEX_FIRST_PERSON,
                new Height(editedPerson.getHeight().getValue()));

        String expectedMessage = String.format(HeightCommand.MESSAGE_DELETE_WEIGHT_SUCCESS,
               editedPerson.getFormattedMessage());

        Model expectedModel = new ModelManager(new AddressBook(model.getAddressBook()), new UserPrefs());
        expectedModel.setPerson(firstPerson, editedPerson);

        assertCommandSuccess(heightCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_addHeightUnfilteredListWithoutEmail_success() {
        Person firstPerson = modelWithoutEmail.getFilteredPersonList().get(INDEX_FIRST_PERSON.getZeroBased());
        Person editedPerson = new PersonBuilder(firstPerson).withHeight(WEIGHT_STUB).build();

        HeightCommand heightCommand = new HeightCommand(INDEX_FIRST_PERSON,
                new Height(editedPerson.getHeight().getValue()));

        String expectedMessage = String.format(HeightCommand.MESSAGE_ADD_WEIGHT_SUCCESS,
                editedPerson.getFormattedMessage());

        Model expectedModel = new ModelManager(new AddressBook(modelWithoutEmail.getAddressBook()), new UserPrefs());
        expectedModel.setPerson(firstPerson, editedPerson);

        assertCommandSuccess(heightCommand, modelWithoutEmail, expectedMessage, expectedModel);
    }

    @Test
    public void execute_invalidIndex_failure() {
        Index invalidIndex = Index.fromOneBased(model.getFilteredPersonList().size() + 1);
        HeightCommand heightCommand = new HeightCommand(invalidIndex, new Height(WEIGHT_STUB));
        assertCommandFailure(heightCommand, model, Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
    }

    @Test
    public void equals() {
        HeightCommand firstHeight = new HeightCommand(INDEX_FIRST_PERSON, new Height(WEIGHT_STUB));
        HeightCommand secondHeight = new HeightCommand(INDEX_SECOND_PERSON, new Height(WEIGHT_STUB));
        HeightCommand firstHeightClone = new HeightCommand(INDEX_FIRST_PERSON, new Height(WEIGHT_STUB));

        // same height => return true
        assertEquals(firstHeight, firstHeight);

        // same height details => return true
        assertEquals(firstHeight, firstHeightClone);

        // same height but different person => return false
        assertNotEquals(firstHeight, secondHeight);
    }
}
