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
import seedu.address.model.person.Weight;
import seedu.address.model.person.Person;
import seedu.address.testutil.PersonBuilder;

class WeightCommandTest {

    private static final Float WEIGHT_STUB = 92.5f;
    private static final Float UNINITIALIZED_WEIGHT = 0f;

    private Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());
    private Model modelWithoutEmail = new ModelManager(getTypicalAddressBookWithoutEmail(), new UserPrefs());
    @Test
    public void execute_addWeightUnfilteredList_success() {
        Person firstPerson = model.getFilteredPersonList().get(INDEX_FIRST_PERSON.getZeroBased());
        Person editedPerson = new PersonBuilder(firstPerson).withWeight(WEIGHT_STUB).build();

        WeightCommand weightCommand = new WeightCommand(INDEX_FIRST_PERSON,
                new Weight(editedPerson.getWeight().getValue()));

        String expectedMessage = String.format(WeightCommand.MESSAGE_ADD_WEIGHT_SUCCESS,
                editedPerson.getFormattedMessage());

        Model expectedModel = new ModelManager(new AddressBook(model.getAddressBook()), new UserPrefs());
        expectedModel.setPerson(firstPerson, editedPerson);

        assertCommandSuccess(weightCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_deleteWeightUnfilteredList_success() {
        Person firstPerson = model.getFilteredPersonList().get(INDEX_FIRST_PERSON.getZeroBased());
        Person editedPerson = new PersonBuilder(firstPerson).withWeight(UNINITIALIZED_WEIGHT).build();

        WeightCommand weightCommand = new WeightCommand(INDEX_FIRST_PERSON,
                new Weight(editedPerson.getWeight().getValue()));

        String expectedMessage = String.format(WeightCommand.MESSAGE_DELETE_WEIGHT_SUCCESS,
               editedPerson.getFormattedMessage());

        Model expectedModel = new ModelManager(new AddressBook(model.getAddressBook()), new UserPrefs());
        expectedModel.setPerson(firstPerson, editedPerson);

        assertCommandSuccess(weightCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_addWeightUnfilteredListWithoutEmail_success() {
        Person firstPerson = modelWithoutEmail.getFilteredPersonList().get(INDEX_FIRST_PERSON.getZeroBased());
        Person editedPerson = new PersonBuilder(firstPerson).withWeight(WEIGHT_STUB).build();

        WeightCommand weightCommand = new WeightCommand(INDEX_FIRST_PERSON,
                new Weight(editedPerson.getWeight().getValue()));

        String expectedMessage = String.format(WeightCommand.MESSAGE_ADD_WEIGHT_SUCCESS,
                editedPerson.getFormattedMessage());

        Model expectedModel = new ModelManager(new AddressBook(modelWithoutEmail.getAddressBook()), new UserPrefs());
        expectedModel.setPerson(firstPerson, editedPerson);

        assertCommandSuccess(weightCommand, modelWithoutEmail, expectedMessage, expectedModel);
    }

    @Test
    public void execute_invalidIndex_failure() {
        Index invalidIndex = Index.fromOneBased(model.getFilteredPersonList().size() + 1);
        WeightCommand weightCommand = new WeightCommand(invalidIndex, new Weight(WEIGHT_STUB));
        assertCommandFailure(weightCommand, model, Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
    }

    @Test
    public void equals() {
        WeightCommand firstWeight = new WeightCommand(INDEX_FIRST_PERSON, new Weight(WEIGHT_STUB));
        WeightCommand secondWeight = new WeightCommand(INDEX_SECOND_PERSON, new Weight(WEIGHT_STUB));
        WeightCommand firstWeightClone = new WeightCommand(INDEX_FIRST_PERSON, new Weight(WEIGHT_STUB));

        // same weight => return true
        assertEquals(firstWeight, firstWeight);

        // same weight details => return true
        assertEquals(firstWeight, firstWeightClone);

        // same weight but different person => return false
        assertNotEquals(firstWeight, secondWeight);
    }
}
