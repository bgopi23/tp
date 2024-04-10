package seedu.address.model.person.messages;

/**
 * Messages used by Weight and associated classes.
 */
public class WeightMessages {

    public static final String MESSAGE_RANGE = "Range should be more than or equals to zero."
            + " Should have been handled in Parser class";
    public static final String MESSAGE_EMPTY_WEIGHT_MAP = "There are no more weight values to be removed. "
            + "This client has no more weight values associated with them.";
    public static final String MESSAGE_CONSTRAINTS =
            "Weight value can only be a number between 0 and 5000 (inclusive).";
    public static final String MESSAGE_CONSTRAINTS_DATE = "Date value is invalid. Should follow the format "
            + "YYYY-MM-DDTHH:mm:ss, e.g. 2024-03-27T10:15:30";
    public static final String MESSAGE_JSON_KEY_NOT_FOUND = "Key value not found in JSON file.";
    public static final String MESSAGE_JSON_EMPTY_WEIGHT = "Weight value specified in JSON cannot be an empty string.";
    public static final String VALIDATION_REGEX = "^(?:[0-9]+(?:\\.[0-9]*)?|\\.[0-9]+)?$";
}
