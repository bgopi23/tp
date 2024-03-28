package seedu.address.logic.commands;

import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.logic.commands.CommandTestUtil.showPersonAtIndex;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_PERSON;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBookWithNothing;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBookWithSinglePerson;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import seedu.address.logic.Messages;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;

/**
 * Contains integration tests (interaction with the Model) and unit tests for ListCommand.
 */
public class ListCommandTest {

    private Model model;
    private Model emptyModel;
    private Model expectedEmptyModel;
    private Model expectedModel;
    private Model singlePersonModel;
    private Model expectedSinglePersonModel;

    @BeforeEach
    public void setUp() {
        emptyModel = new ModelManager(getTypicalAddressBookWithNothing(), new UserPrefs());
        singlePersonModel = new ModelManager(getTypicalAddressBookWithSinglePerson(), new UserPrefs());
        model = new ModelManager(getTypicalAddressBook(), new UserPrefs());
        expectedSinglePersonModel = new ModelManager(singlePersonModel.getAddressBook(), new UserPrefs());
        expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs());
        expectedEmptyModel = new ModelManager(emptyModel.getAddressBook(), new UserPrefs());
    }

    @Test
    public void execute_listIsNotFiltered_showsSameList() {
        int listSize = expectedModel.getFilteredPersonList().size();
        String expectedMessage = String.format(Messages.MESSAGE_ALL_CLIENTS_LISTED, listSize);
        assertCommandSuccess(new ListCommand(), model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_emptyList_showsNothing() {
        String expectedMessage = String.format(Messages.MESSAGE_NO_CLIENTS_TO_LIST);
        assertCommandSuccess(new ListCommand(), emptyModel, expectedMessage, expectedEmptyModel);
    }

    @Test
    public void execute_singlePersonList_showsEverything() {
        showPersonAtIndex(model, INDEX_FIRST_PERSON);
        String expectedMessage = String.format(Messages.MESSAGE_ONE_CLIENT_LISTED);
        assertCommandSuccess(new ListCommand(), singlePersonModel, expectedMessage, expectedSinglePersonModel);
    }

    @Test
    public void execute_listIsFiltered_showsEverything() {
        showPersonAtIndex(model, INDEX_FIRST_PERSON);
        int listSize = expectedModel.getFilteredPersonList().size();
        String expectedMessage = String.format(Messages.MESSAGE_ALL_CLIENTS_LISTED, listSize);
        assertCommandSuccess(new ListCommand(), model, expectedMessage, expectedModel);
    }
}
