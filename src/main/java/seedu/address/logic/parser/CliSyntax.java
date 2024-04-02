package seedu.address.logic.parser;

/**
 * Contains Command Line Interface (CLI) syntax definitions common to multiple commands
 */
public class CliSyntax {

    /* Prefix definitions */
    public static final Prefix PREFIX_NAME = new Prefix("n/");
    public static final Prefix PREFIX_PHONE = new Prefix("p/");
    public static final Prefix PREFIX_EMAIL = new Prefix("e/");
    public static final Prefix PREFIX_ADDRESS = new Prefix("a/");
    public static final Prefix PREFIX_WEIGHT = new Prefix("w/");
    public static final Prefix PREFIX_HEIGHT = new Prefix("h/");
    public static final Prefix PREFIX_TAG = new Prefix("t/");
    public static final Prefix PREFIX_NOTE = new Prefix("nt/");
    public static final Prefix PREFIX_EXERCISE_NAME = new Prefix("n/");
    public static final Prefix PREFIX_EXERCISE_SETS = new Prefix("s/");
    public static final Prefix PREFIX_EXERCISE_REPS = new Prefix("r/");
    public static final Prefix PREFIX_EXERCISE_BREAK_BETWEEN_SETS = new Prefix("b/");
    public static final Prefix PREFIX_FITDELETE_DELETE_ALL = new Prefix("/all");

    public static final Prefix[] ALL_PREFIXES = {
        PREFIX_NAME,
        PREFIX_PHONE,
        PREFIX_EMAIL,
        PREFIX_ADDRESS,
        PREFIX_HEIGHT,
        PREFIX_WEIGHT,
        PREFIX_NOTE,
        PREFIX_TAG
    };
    public static final Prefix[] ALL_PREFIXES_EXCEPT_TAG = {
        PREFIX_NAME,
        PREFIX_PHONE,
        PREFIX_EMAIL,
        PREFIX_ADDRESS,
        PREFIX_HEIGHT,
        PREFIX_WEIGHT,
        PREFIX_NOTE,
    };
    public static final Prefix[] PREFIX_NAME_AND_PHONE = {
        PREFIX_NAME,
        PREFIX_PHONE,
    };
    public static final Prefix[] PREFIXES_NAME_PHONE_EMAIL_ADDRESS = {
        PREFIX_NAME,
        PREFIX_PHONE,
        PREFIX_EMAIL,
        PREFIX_ADDRESS
    };
    public static final Prefix PREFIX_CLEAR_CONFIRM = new Prefix("/confirm");
}
