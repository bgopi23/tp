package seedu.address.model.person.messages;

/**
 * Messages used by Weight and associated classes.
 */
public class WeightMessages {

    public static final String MESSAGE_CONSTRAINTS =
            "Weight value can only be a number between 0 and 5000 (inclusive).";
    public static final String VALIDATION_REGEX = "^(?:[0-9]+(?:\\.[0-9]*)?|\\.[0-9]+)?$";
    public static final String MESSAGE_RANGE = "Range should be more than or equals to zero."
            + " Should have been handled in Parser class";
    public static final String MESSAGE_EMPTY_WEIGHT_MAP = "There are no more weight values to be removed. "
            + "This client has no more weight values associated with them.";
}
