package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.logic.messages.FindCommandMessages.MESSAGE_NO_CLIENTS_FOUND;
import static seedu.address.logic.messages.FindCommandMessages.MESSAGE_ONE_CLIENT_FOUND;
import static seedu.address.logic.messages.FindCommandMessages.MESSAGE_PERSONS_FOUND_OVERVIEW;
import static seedu.address.testutil.TypicalPersons.ALICE;
import static seedu.address.testutil.TypicalPersons.BENSON;
import static seedu.address.testutil.TypicalPersons.CARL;
import static seedu.address.testutil.TypicalPersons.DANIEL;
import static seedu.address.testutil.TypicalPersons.ELLE;
import static seedu.address.testutil.TypicalPersons.FIONA;
import static seedu.address.testutil.TypicalPersons.GEORGE;
import static seedu.address.testutil.TypicalPersons.HOON;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.person.predicates.AddressContainsSubstringPredicate;
import seedu.address.model.person.predicates.CombinedPredicates;
import seedu.address.model.person.predicates.EmailContainsSubstringPredicate;
import seedu.address.model.person.predicates.NameContainsSubstringPredicate;
import seedu.address.model.person.predicates.NoteContainsSubstringPredicate;
import seedu.address.model.person.predicates.PhoneContainsSubstringPredicate;
import seedu.address.model.person.predicates.TagSetContainsAllTagsPredicate;
import seedu.address.model.tag.Tag;

/**
 * Contains integration tests (interaction with the Model) for
 * {@code FindCommand}.
 */
public class FindCommandTest {
    private Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());
    private Model expectedModel = new ModelManager(getTypicalAddressBook(), new UserPrefs());

    @BeforeEach
    public void resetModels() {
        model = new ModelManager(getTypicalAddressBook(), new UserPrefs());
        expectedModel = new ModelManager(getTypicalAddressBook(), new UserPrefs());
    }

    @Test
    public void equals() {
        NameContainsSubstringPredicate firstPredicate = new NameContainsSubstringPredicate("Alex");
        NameContainsSubstringPredicate secondPredicate = new NameContainsSubstringPredicate("Chris");

        NameContainsSubstringPredicate firstPredicateClone = new NameContainsSubstringPredicate("Alex");

        FindCommand findFirstCommand = new FindCommand(new CombinedPredicates(firstPredicate));
        FindCommand findSecondCommand = new FindCommand(new CombinedPredicates(secondPredicate));

        // same object -> returns true
        assertTrue(findFirstCommand.equals(findFirstCommand));

        // same values -> returns true
        FindCommand findFirstCommandCopy = new FindCommand(new CombinedPredicates(firstPredicateClone));
        assertTrue(findFirstCommand.equals(findFirstCommandCopy));

        // different types -> returns false
        assertFalse(findFirstCommand.equals(1));

        // null -> returns false
        assertFalse(findFirstCommand.equals(null));

        // different person -> returns false
        assertFalse(findFirstCommand.equals(findSecondCommand));
    }

    @Test
    public void execute_searchEmpty_allPersonsFound() {
        String expectedMessage = String.format(MESSAGE_PERSONS_FOUND_OVERVIEW, 7);

        NameContainsSubstringPredicate namePredicate = new NameContainsSubstringPredicate("");
        PhoneContainsSubstringPredicate phonePredicate = new PhoneContainsSubstringPredicate("");
        EmailContainsSubstringPredicate emailPredicate = new EmailContainsSubstringPredicate("");
        AddressContainsSubstringPredicate addressPredicate = new AddressContainsSubstringPredicate("");
        TagSetContainsAllTagsPredicate tagsPredicate = new TagSetContainsAllTagsPredicate(new HashSet<>());
        NoteContainsSubstringPredicate notePredicate = new NoteContainsSubstringPredicate("");

        // Empty name
        FindCommand command = new FindCommand(new CombinedPredicates(namePredicate));
        assertCommandSuccess(command, model, expectedMessage, expectedModel);
        assertEquals(getTypicalAddressBook().getPersonList(), model.getFilteredPersonList());

        // Empty phone
        command = new FindCommand(new CombinedPredicates(phonePredicate));
        assertCommandSuccess(command, model, expectedMessage, expectedModel);
        assertEquals(getTypicalAddressBook().getPersonList(), model.getFilteredPersonList());

        // Empty email
        command = new FindCommand(new CombinedPredicates(emailPredicate));
        assertCommandSuccess(command, model, expectedMessage, expectedModel);
        assertEquals(getTypicalAddressBook().getPersonList(), model.getFilteredPersonList());

        // Empty address
        command = new FindCommand(new CombinedPredicates(addressPredicate));
        assertCommandSuccess(command, model, expectedMessage, expectedModel);
        assertEquals(getTypicalAddressBook().getPersonList(), model.getFilteredPersonList());

        // Empty tags
        command = new FindCommand(new CombinedPredicates(tagsPredicate));
        assertCommandSuccess(command, model, expectedMessage, expectedModel);
        assertEquals(getTypicalAddressBook().getPersonList(), model.getFilteredPersonList());

        // Empty note
        command = new FindCommand(new CombinedPredicates(notePredicate));
        assertCommandSuccess(command, model, expectedMessage, expectedModel);
        assertEquals(getTypicalAddressBook().getPersonList(), model.getFilteredPersonList());

        // Empty multiple
        command = new FindCommand(new CombinedPredicates(namePredicate, phonePredicate));
        assertCommandSuccess(command, model, expectedMessage, expectedModel);
        assertEquals(getTypicalAddressBook().getPersonList(), model.getFilteredPersonList());
    }

    @Test
    public void execute_searchName_noPersonsFound() {
        String expectedMessage = String.format(MESSAGE_NO_CLIENTS_FOUND);
        NameContainsSubstringPredicate predicate = new NameContainsSubstringPredicate("alonica");
        FindCommand command = new FindCommand(new CombinedPredicates(predicate));
        expectedModel.updateFilteredPersonList(predicate);
        assertCommandSuccess(command, model, expectedMessage, expectedModel);
        assertEquals(Collections.emptyList(), model.getFilteredPersonList());
    }

    @Test
    public void execute_searchName_personsFound() {
        // Single person found
        String expectedMessage = String.format(MESSAGE_ONE_CLIENT_FOUND);
        NameContainsSubstringPredicate predicate = new NameContainsSubstringPredicate("alice");
        FindCommand command = new FindCommand(new CombinedPredicates(predicate));
        expectedModel.updateFilteredPersonList(predicate);
        assertCommandSuccess(command, model, expectedMessage, expectedModel);
        assertEquals(Arrays.asList(ALICE), model.getFilteredPersonList());

        // Multiple persons found
        expectedMessage = String.format(MESSAGE_PERSONS_FOUND_OVERVIEW, 2);
        predicate = new NameContainsSubstringPredicate("meier");
        command = new FindCommand(new CombinedPredicates(predicate));
        expectedModel.updateFilteredPersonList(predicate);
        assertCommandSuccess(command, model, expectedMessage, expectedModel);
        assertEquals(Arrays.asList(BENSON, DANIEL), model.getFilteredPersonList());
    }

    @Test
    public void execute_searchPhone_noPersonsFound() {
        String expectedMessage = String.format(MESSAGE_NO_CLIENTS_FOUND);
        PhoneContainsSubstringPredicate predicate = new PhoneContainsSubstringPredicate("11111111");
        FindCommand command = new FindCommand(new CombinedPredicates(predicate));
        expectedModel.updateFilteredPersonList(predicate);
        assertCommandSuccess(command, model, expectedMessage, expectedModel);
        assertEquals(Collections.emptyList(), model.getFilteredPersonList());
    }

    @Test
    public void execute_searchPhone_personsFound() {
        // Single person found
        String expectedMessage = String.format(MESSAGE_ONE_CLIENT_FOUND);
        PhoneContainsSubstringPredicate predicate = new PhoneContainsSubstringPredicate("9435");
        FindCommand command = new FindCommand(new CombinedPredicates(predicate));
        expectedModel.updateFilteredPersonList(predicate);
        assertCommandSuccess(command, model, expectedMessage, expectedModel);
        assertEquals(Arrays.asList(ALICE), model.getFilteredPersonList());

        // Multiple persons found
        expectedMessage = String.format(MESSAGE_PERSONS_FOUND_OVERVIEW, 3);
        predicate = new PhoneContainsSubstringPredicate("9482");
        command = new FindCommand(new CombinedPredicates(predicate));
        expectedModel.updateFilteredPersonList(predicate);
        assertCommandSuccess(command, model, expectedMessage, expectedModel);
        assertEquals(Arrays.asList(ELLE, FIONA, GEORGE), model.getFilteredPersonList());
    }

    @Test
    public void execute_searchEmail_noPersonsFound() {
        String expectedMessage = String.format(MESSAGE_NO_CLIENTS_FOUND);
        EmailContainsSubstringPredicate predicate = new EmailContainsSubstringPredicate("alonica");
        FindCommand command = new FindCommand(new CombinedPredicates(predicate));
        expectedModel.updateFilteredPersonList(predicate);
        assertCommandSuccess(command, model, expectedMessage, expectedModel);
        assertEquals(Collections.emptyList(), model.getFilteredPersonList());
    }

    @Test
    public void execute_searchEmail_personsFound() {
        // Single person found
        String expectedMessage = String.format(MESSAGE_ONE_CLIENT_FOUND);
        EmailContainsSubstringPredicate predicate = new EmailContainsSubstringPredicate("johnd");
        FindCommand command = new FindCommand(new CombinedPredicates(predicate));
        expectedModel.updateFilteredPersonList(predicate);
        assertCommandSuccess(command, model, expectedMessage, expectedModel);
        assertEquals(Arrays.asList(BENSON), model.getFilteredPersonList());

        // Multiple persons found
        expectedMessage = String.format(MESSAGE_PERSONS_FOUND_OVERVIEW, 7);
        predicate = new EmailContainsSubstringPredicate("example.com");
        command = new FindCommand(new CombinedPredicates(predicate));
        expectedModel.updateFilteredPersonList(predicate);
        assertCommandSuccess(command, model, expectedMessage, expectedModel);
        assertEquals(Arrays.asList(ALICE, BENSON, CARL, DANIEL, ELLE, FIONA, GEORGE), model.getFilteredPersonList());
    }

    @Test
    public void execute_searchAddress_noPersonsFound() {
        String expectedMessage = String.format(MESSAGE_NO_CLIENTS_FOUND);
        AddressContainsSubstringPredicate predicate = new AddressContainsSubstringPredicate("Republic City");
        FindCommand command = new FindCommand(new CombinedPredicates(predicate));
        expectedModel.updateFilteredPersonList(predicate);
        assertCommandSuccess(command, model, expectedMessage, expectedModel);
        assertEquals(Collections.emptyList(), model.getFilteredPersonList());
    }

    @Test
    public void execute_searchAddress_personsFound() {
        // Single person found
        String expectedMessage = String.format(MESSAGE_ONE_CLIENT_FOUND);
        AddressContainsSubstringPredicate predicate = new AddressContainsSubstringPredicate("tokyo");
        FindCommand command = new FindCommand(new CombinedPredicates(predicate));
        expectedModel.updateFilteredPersonList(predicate);
        assertCommandSuccess(command, model, expectedMessage, expectedModel);
        assertEquals(Arrays.asList(FIONA), model.getFilteredPersonList());

        // Multiple persons found
        expectedMessage = String.format(MESSAGE_PERSONS_FOUND_OVERVIEW, 3);
        predicate = new AddressContainsSubstringPredicate("street");
        command = new FindCommand(new CombinedPredicates(predicate));
        expectedModel.updateFilteredPersonList(predicate);
        assertCommandSuccess(command, model, expectedMessage, expectedModel);
        assertEquals(Arrays.asList(CARL, DANIEL, GEORGE), model.getFilteredPersonList());
    }

    @Test
    public void execute_searchTags_noPersonsFound() {
        String expectedMessage = String.format(MESSAGE_NO_CLIENTS_FOUND);

        TagSetContainsAllTagsPredicate predicate = new TagSetContainsAllTagsPredicate(
                new HashSet<>(Arrays.asList(new Tag("Enemies"))));

        FindCommand command = new FindCommand(new CombinedPredicates(predicate));
        expectedModel.updateFilteredPersonList(predicate);
        assertCommandSuccess(command, model, expectedMessage, expectedModel);
        assertEquals(Collections.emptyList(), model.getFilteredPersonList());
    }

    @Test
    public void execute_searchSingleTag_personsFound() {
        // Single person found
        String expectedMessage = String.format(MESSAGE_ONE_CLIENT_FOUND);

        TagSetContainsAllTagsPredicate predicate = new TagSetContainsAllTagsPredicate(
                new HashSet<>(Arrays.asList(new Tag("owesmoney"))));

        FindCommand command = new FindCommand(new CombinedPredicates(predicate));
        expectedModel.updateFilteredPersonList(predicate);
        assertCommandSuccess(command, model, expectedMessage, expectedModel);
        assertEquals(Arrays.asList(BENSON), model.getFilteredPersonList());

        // Multiple persons found
        expectedMessage = String.format(MESSAGE_PERSONS_FOUND_OVERVIEW, 3);

        predicate = new TagSetContainsAllTagsPredicate(new HashSet<>(Arrays.asList(new Tag("friends"))));

        command = new FindCommand(new CombinedPredicates(predicate));
        expectedModel.updateFilteredPersonList(predicate);
        assertCommandSuccess(command, model, expectedMessage, expectedModel);
        assertEquals(Arrays.asList(ALICE, BENSON, DANIEL), model.getFilteredPersonList());
    }

    @Test
    public void execute_searchMultipleTags_personsFound() {
        // Single person found
        String expectedMessage = String.format(MESSAGE_ONE_CLIENT_FOUND);

        TagSetContainsAllTagsPredicate predicate = new TagSetContainsAllTagsPredicate(
                new HashSet<>(Arrays.asList(new Tag("owesmoney"), new Tag("friends"))));

        FindCommand command = new FindCommand(new CombinedPredicates(predicate));
        expectedModel.updateFilteredPersonList(predicate);
        assertCommandSuccess(command, model, expectedMessage, expectedModel);
        assertEquals(Arrays.asList(BENSON), model.getFilteredPersonList());
    }

    @Test
    public void execute_searchPartialTag_noPersonsFound() {
        // No persons found
        String expectedMessage = String.format(MESSAGE_NO_CLIENTS_FOUND);

        TagSetContainsAllTagsPredicate predicate = new TagSetContainsAllTagsPredicate(
                new HashSet<>(Arrays.asList(new Tag("fri"))));

        FindCommand command = new FindCommand(new CombinedPredicates(predicate));
        expectedModel.updateFilteredPersonList(predicate);
        assertCommandSuccess(command, model, expectedMessage, expectedModel);
        assertEquals(Arrays.asList(), model.getFilteredPersonList());
    }

    @Test
    public void execute_searchCaseInsensitiveTag_personsFound() {
        // No persons found
        String expectedMessage = String.format(MESSAGE_PERSONS_FOUND_OVERVIEW, 3);

        TagSetContainsAllTagsPredicate predicate = new TagSetContainsAllTagsPredicate(
                new HashSet<>(Arrays.asList(new Tag("FrIenDs"))));

        FindCommand command = new FindCommand(new CombinedPredicates(predicate));
        expectedModel.updateFilteredPersonList(predicate);
        assertCommandSuccess(command, model, expectedMessage, expectedModel);
        assertEquals(Arrays.asList(ALICE, BENSON, DANIEL), model.getFilteredPersonList());
    }

    @Test
    public void execute_searchNote_noPersonsFound() {
        String expectedMessage = String.format(MESSAGE_NO_CLIENTS_FOUND);
        NoteContainsSubstringPredicate predicate = new NoteContainsSubstringPredicate("enemy");
        FindCommand command = new FindCommand(new CombinedPredicates(predicate));
        expectedModel.updateFilteredPersonList(predicate);
        assertCommandSuccess(command, model, expectedMessage, expectedModel);
        assertEquals(Collections.emptyList(), model.getFilteredPersonList());
    }

    @Test
    public void execute_searchNote_personsFound() {
        // Single person found
        String expectedMessage = String.format(MESSAGE_ONE_CLIENT_FOUND);
        NoteContainsSubstringPredicate predicate = new NoteContainsSubstringPredicate("love");
        FindCommand command = new FindCommand(new CombinedPredicates(predicate));
        expectedModel.updateFilteredPersonList(predicate);
        assertCommandSuccess(command, model, expectedMessage, expectedModel);
        assertEquals(Arrays.asList(FIONA), model.getFilteredPersonList());

        // Multiple persons found
        expectedMessage = String.format(MESSAGE_PERSONS_FOUND_OVERVIEW, 2);
        predicate = new NoteContainsSubstringPredicate("best");
        command = new FindCommand(new CombinedPredicates(predicate));
        expectedModel.updateFilteredPersonList(predicate);
        assertCommandSuccess(command, model, expectedMessage, expectedModel);
        assertEquals(Arrays.asList(ALICE, DANIEL), model.getFilteredPersonList());
    }

    @Test
    public void execute_searchMultiple_noPersonsFound() {
        String expectedMessage = String.format(MESSAGE_NO_CLIENTS_FOUND);

        // All mismatch
        NoteContainsSubstringPredicate notePredicate = new NoteContainsSubstringPredicate("enemy");
        NameContainsSubstringPredicate namePredicate = new NameContainsSubstringPredicate("alonica");

        CombinedPredicates combinedPredicates = new CombinedPredicates(notePredicate, namePredicate);

        FindCommand command = new FindCommand(new CombinedPredicates(notePredicate, namePredicate));
        expectedModel.updateFilteredPersonList(combinedPredicates);
        assertCommandSuccess(command, model, expectedMessage, expectedModel);
        assertEquals(Collections.emptyList(), model.getFilteredPersonList());

        // Some match, some mismatch
        notePredicate = new NoteContainsSubstringPredicate("best");
        namePredicate = new NameContainsSubstringPredicate("alonica");

        combinedPredicates = new CombinedPredicates(notePredicate, namePredicate);

        command = new FindCommand(new CombinedPredicates(notePredicate, namePredicate));
        expectedModel.updateFilteredPersonList(combinedPredicates);
        assertCommandSuccess(command, model, expectedMessage, expectedModel);
        assertEquals(Collections.emptyList(), model.getFilteredPersonList());
    }

    @Test
    public void execute_addPersonSearch_personFound() throws CommandException {
        String expectedMessage = String.format(MESSAGE_ONE_CLIENT_FOUND);

        AddCommand addHoonCommand = new AddCommand(HOON);
        addHoonCommand.execute(model);
        addHoonCommand.execute(expectedModel);

        NameContainsSubstringPredicate namePredicate = new NameContainsSubstringPredicate("Hoon Meier");

        CombinedPredicates combinedPredicates = new CombinedPredicates(namePredicate);

        FindCommand command = new FindCommand(new CombinedPredicates(namePredicate));
        expectedModel.updateFilteredPersonList(combinedPredicates);
        assertCommandSuccess(command, model, expectedMessage, expectedModel);
        assertEquals(Arrays.asList(HOON),
                model.getFilteredPersonList());
    }

    @Test
    public void execute_searchMultiple_personsFound() {
        // Single person found
        String expectedMessage = String.format(MESSAGE_ONE_CLIENT_FOUND);

        NoteContainsSubstringPredicate notePredicate = new NoteContainsSubstringPredicate("best");
        NameContainsSubstringPredicate namePredicate = new NameContainsSubstringPredicate("alice");

        CombinedPredicates combinedPredicates = new CombinedPredicates(notePredicate, namePredicate);

        FindCommand command = new FindCommand(new CombinedPredicates(notePredicate, namePredicate));
        expectedModel.updateFilteredPersonList(combinedPredicates);
        assertCommandSuccess(command, model, expectedMessage, expectedModel);
        assertEquals(Arrays.asList(ALICE), model.getFilteredPersonList());

        // Multiple persons found
        expectedMessage = String.format(MESSAGE_PERSONS_FOUND_OVERVIEW, 2);

        TagSetContainsAllTagsPredicate tagsPredicate = new TagSetContainsAllTagsPredicate(
                new HashSet<>(Arrays.asList(new Tag("friends"))));
        PhoneContainsSubstringPredicate phonePredicate = new PhoneContainsSubstringPredicate("8765");

        combinedPredicates = new CombinedPredicates(tagsPredicate, phonePredicate);

        command = new FindCommand(new CombinedPredicates(tagsPredicate, phonePredicate));
        expectedModel.updateFilteredPersonList(combinedPredicates);
        assertCommandSuccess(command, model, expectedMessage, expectedModel);
        assertEquals(Arrays.asList(BENSON, DANIEL), model.getFilteredPersonList());
    }

    @Test
    public void toStringMethod() {
        NameContainsSubstringPredicate predicate = new NameContainsSubstringPredicate("Fiona");
        CombinedPredicates combinedPredicates = new CombinedPredicates(predicate);
        FindCommand findCommand = new FindCommand(combinedPredicates);
        String expected = FindCommand.class.getCanonicalName() + "{predicates=" + combinedPredicates + "}";
        assertEquals(expected, findCommand.toString());
    }
}
