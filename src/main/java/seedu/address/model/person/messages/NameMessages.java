package seedu.address.model.person.messages;

public class NameMessages {

    public static final String MESSAGE_CONSTRAINTS = "Names should only contain alphanumeric"
            + " characters and spaces, and it should not be blank";
    /*
     * The first character of the address must not be a whitespace,
     * otherwise " " (a blank string) becomes a valid input.
     */
    public static final String VALIDATION_REGEX = "[\\p{Alnum}][\\p{Alnum} ]*";
}
