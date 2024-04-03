package seedu.address.logic.commands;

import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.logic.commands.CommandTestUtil.showPersonAtIndex;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_PERSON;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBookWithNothing;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBookWithSinglePerson;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import seedu.address.logic.messages.ListCommandMessages;
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
        this.emptyModel = new ModelManager(getTypicalAddressBookWithNothing(), new UserPrefs());
        this.singlePersonModel = new ModelManager(getTypicalAddressBookWithSinglePerson(), new UserPrefs());
        this.model = new ModelManager(getTypicalAddressBook(), new UserPrefs());
        this.expectedSinglePersonModel = new ModelManager(this.singlePersonModel.getAddressBook(), new UserPrefs());
        this.expectedModel = new ModelManager(this.model.getAddressBook(), new UserPrefs());
        this.expectedEmptyModel = new ModelManager(this.emptyModel.getAddressBook(), new UserPrefs());
    }

    @Test
    public void execute_listIsNotFiltered_showsSameList() {
        int listSize = this.expectedModel.getFilteredPersonList().size();
        String expectedMessage = String.format(ListCommandMessages.MESSAGE_ALL_CLIENTS_LISTED, listSize);
        assertCommandSuccess(new ListCommand(), this.model, expectedMessage, this.expectedModel);
    }

    @Test
    public void execute_emptyList_showsNothing() {
        String expectedMessage = String.format(ListCommandMessages.MESSAGE_NO_CLIENTS_TO_LIST);
        assertCommandSuccess(new ListCommand(), this.emptyModel, expectedMessage, this.expectedEmptyModel);
    }

    @Test
    public void execute_singlePersonList_showsEverything() {
        showPersonAtIndex(this.model, INDEX_FIRST_PERSON);
        String expectedMessage = String.format(ListCommandMessages.MESSAGE_ONE_CLIENT_LISTED);
        assertCommandSuccess(new ListCommand(), this.singlePersonModel,
                expectedMessage, this.expectedSinglePersonModel);
    }

    @Test
    public void execute_listIsFiltered_showsEverything() {
        showPersonAtIndex(this.model, INDEX_FIRST_PERSON);
        int listSize = this.expectedModel.getFilteredPersonList().size();
        String expectedMessage = String.format(ListCommandMessages.MESSAGE_ALL_CLIENTS_LISTED, listSize);
        assertCommandSuccess(new ListCommand(), this.model, expectedMessage, this.expectedModel);
    }
}
