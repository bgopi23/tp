package seedu.address.model.person.messages;

public class HeightMessages {

    public static final String MESSAGE_CONSTRAINTS = "Height value can only be a positive number.";
    public static final String VALIDATION_REGEX = "^(?:[0-9]+(?:\\.[0-9]*)?|\\.[0-9]+)?$";
    public static final String MESSAGE_RANGE = "Range should be more than or equals to zero."
            + " Should have been handled in Parser class";
    public static final String MESSAGE_NO_HEIGHT = "Height: N/A";
}
