package seedu.address.testutil;

import static seedu.address.logic.commands.CommandTestUtil.VALID_ADDRESS_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_ADDRESS_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_EMAIL_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_EMAIL_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_NAME_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_NAME_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_PHONE_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_PHONE_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TAG_FRIEND;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TAG_HUSBAND;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import seedu.address.model.AddressBook;
import seedu.address.model.exercise.Exercise;
import seedu.address.model.person.Person;

/**
 * A utility class containing a list of {@code Person} objects to be used in
 * tests.
 */
public class TypicalPersons {

    public static final Person ALICE = new PersonBuilder().withName("Alice Pauline")
        .withAddress("123, Jurong West Ave 6, #08-111").withEmail("alice@example.com")
        .withPhone("94351253")
        .withTags("friends")
        .withNote("Best friend")
        .withWeights("2024-01-20T10:15:33=85f")
        .build();
    public static final Person BENSON = new PersonBuilder().withName("Benson Meier")
        .withAddress("311, Clementi Ave 2, #02-25")
        .withEmail("johnd@example.com").withPhone("98765432")
        .withTags("owesMoney", "friends")
        .withWeights("2023-02-22T13:11:32=83f")
        .withExercises(new Exercise("squats", 3, 5, 1))
        .build();
    public static final Person CARL = new PersonBuilder().withName("Carl Kurz").withPhone("95352563")
        .withEmail("heinz@example.com").withAddress("wall street").build();
    public static final Person DANIEL = new PersonBuilder().withName("Daniel Meier").withPhone("87652533")
        .withEmail("cornelia@example.com").withAddress("10th street").withTags("friends")
        .withNote("Best man").build();
    public static final Person ELLE = new PersonBuilder().withName("Elle Meyer").withPhone("94822240")
        .withEmail("werner@example.com").withAddress("michegan ave").build();
    public static final Person FIONA = new PersonBuilder().withName("Fiona Kunz").withPhone("94824279")
        .withEmail("lydia@example.com").withAddress("little tokyo").withNote("lover").build();
    public static final Person GEORGE = new PersonBuilder().withName("George Best").withPhone("94824428")
        .withEmail("anna@example.com").withAddress("4th street").build();

    // Manually added
    public static final Person HOON = new PersonBuilder().withName("Hoon Meier").withPhone("84824240")
        .withEmail("stefan@example.com").withAddress("little india").build();
    public static final Person IDA = new PersonBuilder().withName("Ida Mueller").withPhone("84821310")
        .withEmail("hans@example.com").withAddress("chicago ave").build();

    // Manually added - Person's details found in {@code CommandTestUtil}
    public static final Person AMY = new PersonBuilder().withName(VALID_NAME_AMY).withPhone(VALID_PHONE_AMY)
        .withEmail(VALID_EMAIL_AMY).withAddress(VALID_ADDRESS_AMY).withTags(VALID_TAG_FRIEND).build();
    public static final Person BOB = new PersonBuilder().withName(VALID_NAME_BOB).withPhone(VALID_PHONE_BOB)
        .withEmail(VALID_EMAIL_BOB).withAddress(VALID_ADDRESS_BOB)
        .withTags(VALID_TAG_HUSBAND, VALID_TAG_FRIEND)
        .build();

    public static final Person ALICE_WITHOUT_EMAIL = new PersonBuilder()
        .withName("Alice Pauline")
        .withPhone("94351253")
        .withEmail("")
        .withAddress("")
        .withNote("Best friend")
        .withWeights(new String[0])
        .build();

    public static final Person BENSON_WITHOUT_EMAIL = new PersonBuilder()
        .withName("Benson Meier")
        .withPhone("98765432")
        .withEmail("")
        .withAddress("")
        .withWeights(new String[0])
        .withTags("Friend")
        .build();

    public static final Person CARL_WITHOUT_EMAIL = new PersonBuilder()
        .withName("Carl Kurz")
        .withPhone("95352563")
        .withEmail("")
        .withAddress("wall street")
        .withWeights(new String[0])
        .build();

    private TypicalPersons() {
    } // prevents instantiation

    /**
     * Returns an {@code AddressBook} with all the typical persons.
     */
    public static AddressBook getTypicalAddressBook() {
        AddressBook ab = new AddressBook();
        for (Person person : getTypicalPersons()) {
            ab.addPerson(person);
        }
        return ab;
    }

    /**
     * Gets a typical List of Person objects
     *
     * @return a list of people without any special attribute restrictions
     */
    public static List<Person> getTypicalPersons() {
        return new ArrayList<>(Arrays.asList(ALICE, BENSON, CARL, DANIEL, ELLE, FIONA, GEORGE));
    }

    /**
     * Gets a typical List of Person objects the without email attribute
     *
     * @returns an AddressBook where every person does not have any email
     */
    public static AddressBook getTypicalAddressBookWithoutEmail() {
        AddressBook ab = new AddressBook();
        for (Person person : getTypicalPersonsWithoutEmail()) {
            ab.addPerson(person);
        }
        return ab;
    }

    /**
     * Gets a typical List of Person objects some of which don't have an email attribute
     *
     * @return a list of people some of whom don't have an email address
     */
    public static AddressBook getTypicalAddressBookSomeWithoutEmail() {
        AddressBook ab = new AddressBook();
        for (Person person : getSomeTypicalPersonsWithoutEmail()) {
            ab.addPerson(person);
        }
        return ab;
    }

    /**
     * Gets a AddressBook with a single typical person object
     *
     * @returns an AddressBook object
     */
    public static AddressBook getTypicalAddressBookWithSinglePerson() {
        AddressBook ab = new AddressBook();
        for (Person person : getSingleTypicalPerson()) {
            ab.addPerson(person);
        }
        return ab;
    }

    /**
     * Gets an AddressBook object that has no one
     *
     * @returns an AddressBook with no people inside
     */
    public static AddressBook getTypicalAddressBookWithNothing() {
        AddressBook ab = new AddressBook();
        for (Person person : getEmptyTypicalPerson()) {
            ab.addPerson(person);
        }
        return ab;
    }

    /**
     * Gets a list of people without any email
     *
     * @returns a list of Person Objects that do not have any email attribute associated with them
     */
    public static List<Person> getTypicalPersonsWithoutEmail() {
        return new ArrayList<>(Arrays.asList(ALICE_WITHOUT_EMAIL, BENSON_WITHOUT_EMAIL));
    }

    /**
     * Gets a list of people, some of whom do not have an email
     *
     * @returns a list of people where only some of them do not have an email
     */
    public static List<Person> getSomeTypicalPersonsWithoutEmail() {
        return new ArrayList<>(Arrays.asList(ALICE_WITHOUT_EMAIL, BENSON_WITHOUT_EMAIL, CARL_WITHOUT_EMAIL, DANIEL,
                ELLE));
    }

    /**
     * Gets a list of single typical person
     *
     * @returns a list containing a single typical person object
     */
    public static List<Person> getSingleTypicalPerson() {
        return new ArrayList<>(Arrays.asList(ALICE_WITHOUT_EMAIL));
    }

    /**
     * Gets a list without any people inside
     *
     * @returns a list containing no person objects
     */
    public static List<Person> getEmptyTypicalPerson() {
        return new ArrayList<>(Arrays.asList());
    }
}
