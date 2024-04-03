package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.logic.messages.WeightCommandMessages.MESSAGE_INVALID_INDEX_WEIGHT;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_PERSON;
import static seedu.address.testutil.TypicalIndexes.INDEX_SECOND_PERSON;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBookWithoutEmail;

import java.util.AbstractMap;

import org.junit.jupiter.api.Test;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.messages.WeightCommandMessages;
import seedu.address.model.AddressBook;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.person.Person;
import seedu.address.model.person.weight.Weight;
import seedu.address.model.person.weight.WeightEntry;
import seedu.address.testutil.PersonBuilder;

class WeightCommandTest {

    private static final String WEIGHT_STUB = "2024-01-20T10:15:33=169f";

    private Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());
    private Model modelWithoutEmail = new ModelManager(getTypicalAddressBookWithoutEmail(), new UserPrefs());
    @Test
    public void execute_addWeightUnfilteredList_success() {
        Person firstPerson = this.model.getFilteredPersonList().get(INDEX_FIRST_PERSON.getZeroBased());
        Person editedPerson = new PersonBuilder(firstPerson).withWeights(WEIGHT_STUB).build();

        WeightCommand weightCommand = new WeightCommand(INDEX_FIRST_PERSON,
                new WeightEntry(editedPerson.getLatestWeight().get()));

        String expectedMessage = String.format(WeightCommandMessages.MESSAGE_ADD_WEIGHT_SUCCESS,
                editedPerson.getFormattedMessage());

        Model expectedModel = new ModelManager(new AddressBook(this.model.getAddressBook()), new UserPrefs());
        expectedModel.setPerson(firstPerson, editedPerson);

        assertCommandSuccess(weightCommand, this.model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_deleteWeightUnfilteredList_success() {
        Person firstPerson = this.model.getFilteredPersonList().get(INDEX_FIRST_PERSON.getZeroBased());
        Person editedPerson = new PersonBuilder(firstPerson).withWeights().build();

        WeightCommand weightCommand = new WeightCommand(INDEX_FIRST_PERSON, new WeightEntry(
                new AbstractMap.SimpleEntry<>(WeightEntry.getTimeOfExecution(), new Weight(0f))));

        String expectedMessage = String.format(WeightCommandMessages.MESSAGE_DELETE_WEIGHT_SUCCESS,
               editedPerson.getFormattedMessage());

        Model expectedModel = new ModelManager(new AddressBook(this.model.getAddressBook()), new UserPrefs());
        expectedModel.setPerson(firstPerson, editedPerson);

        assertCommandSuccess(weightCommand, this.model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_addWeightUnfilteredListWithoutEmail_success() {
        Person firstPerson = this.modelWithoutEmail.getFilteredPersonList().get(INDEX_FIRST_PERSON.getZeroBased());
        Person editedPerson = new PersonBuilder(firstPerson).withWeights(WEIGHT_STUB).build();

        WeightCommand weightCommand = new WeightCommand(INDEX_FIRST_PERSON,
                new WeightEntry(editedPerson.getLatestWeight().get()));

        String expectedMessage = String.format(WeightCommandMessages.MESSAGE_ADD_WEIGHT_SUCCESS,
                editedPerson.getFormattedMessage());

        Model expectedModel = new ModelManager(new AddressBook(this.modelWithoutEmail.getAddressBook()), new UserPrefs());
        expectedModel.setPerson(firstPerson, editedPerson);

        assertCommandSuccess(weightCommand, this.modelWithoutEmail, expectedMessage, expectedModel);
    }

    @Test
    public void execute_invalidIndex_failure() {
        WeightEntry weightEntry = new WeightEntry(
                new AbstractMap.SimpleEntry<>(WeightEntry.getTimeOfExecution(), new Weight(180f)));

        Index invalidIndex = Index.fromOneBased(this.model.getFilteredPersonList().size() + 1);
        WeightCommand weightCommand = new WeightCommand(invalidIndex, weightEntry);
        assertCommandFailure(weightCommand, this.model, MESSAGE_INVALID_INDEX_WEIGHT);
    }

    @Test
    public void equals() {
        WeightEntry weightEntry = new WeightEntry(
                new AbstractMap.SimpleEntry<>(WeightEntry.getTimeOfExecution(), new Weight(180f)));

        WeightCommand firstWeight = new WeightCommand(INDEX_FIRST_PERSON, weightEntry);
        WeightCommand secondWeight = new WeightCommand(INDEX_SECOND_PERSON, weightEntry);
        WeightCommand firstWeightClone = new WeightCommand(INDEX_FIRST_PERSON, weightEntry);

        // same weight => return true
        assertEquals(firstWeight, firstWeight);

        // same weight details => return true
        assertEquals(firstWeight, firstWeightClone);

        // same weight but different person => return false
        assertNotEquals(firstWeight, secondWeight);
    }
}
