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
import seedu.address.model.person.Person;
import seedu.address.model.person.WeightTemp;
import seedu.address.testutil.PersonBuilder;

class WeightTempCommandTest {

    private static final Float WEIGHT_STUB = 92.5f;
    private static final Float UNINITIALIZED_WEIGHT = 0f;

    private Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());
    private Model modelWithoutEmail = new ModelManager(getTypicalAddressBookWithoutEmail(), new UserPrefs());
    @Test
    public void execute_addWeightTempUnfilteredList_success() {
        Person firstPerson = model.getFilteredPersonList().get(INDEX_FIRST_PERSON.getZeroBased());
        Person editedPerson = new PersonBuilder(firstPerson).withWeightTemp(WEIGHT_STUB).build();

        WeightTempCommand weightTempCommand = new WeightTempCommand(INDEX_FIRST_PERSON,
                new WeightTemp(editedPerson.getWeightTemp().getValue()));

        String expectedMessage = String.format(WeightTempCommand.MESSAGE_ADD_WEIGHT_SUCCESS,
                editedPerson.getFormattedMessage());

        Model expectedModel = new ModelManager(new AddressBook(model.getAddressBook()), new UserPrefs());
        expectedModel.setPerson(firstPerson, editedPerson);

        assertCommandSuccess(weightTempCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_deleteWeightTempUnfilteredList_success() {
        Person firstPerson = model.getFilteredPersonList().get(INDEX_FIRST_PERSON.getZeroBased());
        Person editedPerson = new PersonBuilder(firstPerson).withWeightTemp(UNINITIALIZED_WEIGHT).build();

        WeightTempCommand weightTempCommand = new WeightTempCommand(INDEX_FIRST_PERSON,
                new WeightTemp(editedPerson.getWeightTemp().getValue()));

        String expectedMessage = String.format(WeightTempCommand.MESSAGE_DELETE_WEIGHT_SUCCESS,
               editedPerson.getFormattedMessage());

        Model expectedModel = new ModelManager(new AddressBook(model.getAddressBook()), new UserPrefs());
        expectedModel.setPerson(firstPerson, editedPerson);

        assertCommandSuccess(weightTempCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_addWeightTempUnfilteredListWithoutEmail_success() {
        Person firstPerson = modelWithoutEmail.getFilteredPersonList().get(INDEX_FIRST_PERSON.getZeroBased());
        Person editedPerson = new PersonBuilder(firstPerson).withWeightTemp(WEIGHT_STUB).build();

        WeightTempCommand weightTempCommand = new WeightTempCommand(INDEX_FIRST_PERSON,
                new WeightTemp(editedPerson.getWeightTemp().getValue()));

        String expectedMessage = String.format(WeightTempCommand.MESSAGE_ADD_WEIGHT_SUCCESS,
                editedPerson.getFormattedMessage());

        Model expectedModel = new ModelManager(new AddressBook(modelWithoutEmail.getAddressBook()), new UserPrefs());
        expectedModel.setPerson(firstPerson, editedPerson);

        assertCommandSuccess(weightTempCommand, modelWithoutEmail, expectedMessage, expectedModel);
    }

    @Test
    public void execute_invalidIndex_failure() {
        Index invalidIndex = Index.fromOneBased(model.getFilteredPersonList().size() + 1);
        WeightTempCommand weightTempCommand = new WeightTempCommand(invalidIndex, new WeightTemp(WEIGHT_STUB));
        assertCommandFailure(weightTempCommand, model, Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
    }

    @Test
    public void equals() {
        WeightTempCommand firstWeightTemp = new WeightTempCommand(INDEX_FIRST_PERSON, new WeightTemp(WEIGHT_STUB));
        WeightTempCommand secondWeightTemp = new WeightTempCommand(INDEX_SECOND_PERSON, new WeightTemp(WEIGHT_STUB));
        WeightTempCommand firstWeightTempClone = new WeightTempCommand(INDEX_FIRST_PERSON, new WeightTemp(WEIGHT_STUB));

        // same weightTemp => return true
        assertEquals(firstWeightTemp, firstWeightTemp);

        // same weightTemp details => return true
        assertEquals(firstWeightTemp, firstWeightTempClone);

        // same weightTemp but different person => return false
        assertNotEquals(firstWeightTemp, secondWeightTemp);
    }
}
