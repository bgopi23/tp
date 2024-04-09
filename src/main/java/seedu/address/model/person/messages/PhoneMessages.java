package seedu.address.model.person.messages;

/**
 * Messages used by Phone and associated classes.
 */
public class PhoneMessages {

    public static final String MESSAGE_CONSTRAINTS =
            "Phone numbers should only contain digits and have a minimum length of 1.";
    public static final String MESSAGE_EXPECTED =
            "The phone number supplied does not appear to be from Singapore,\nfor other types of numbers, "
                    + "please ensure that you have keyed it in correctly.";
    public static final String EXPECTED_FORMAT_REGEX = "^[689]\\d{7}$";
    public static final String REQUIRED_REGEX = "^\\d+$";
}
