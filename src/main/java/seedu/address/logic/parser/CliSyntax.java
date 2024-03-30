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
    public static final Prefix PREFIX_TAG = new Prefix("t/");
    public static final Prefix PREFIX_NOTE = new Prefix("nt/");
    public static final Prefix[] ALL_PREFIXES = {
        PREFIX_NAME,
        PREFIX_PHONE,
        PREFIX_EMAIL,
        PREFIX_ADDRESS,
        PREFIX_NOTE,
        PREFIX_TAG
    };
    public static final Prefix[] ALL_PREFIXES_EXCEPT_TAG = {
        PREFIX_NAME,
        PREFIX_PHONE,
        PREFIX_EMAIL,
        PREFIX_ADDRESS,
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
