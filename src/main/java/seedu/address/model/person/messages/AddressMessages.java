package seedu.address.model.person.messages;

public class AddressMessages {

    public static final String MESSAGE_CONSTRAINTS = "Addresses can take any values, and it should not be blank";
    /*
     * The first character of the address must not be a whitespace,
     * otherwise " " (a blank string) becomes a valid input.
     */
    public static final String VALIDATION_REGEX = "[^\\s].*";
}
