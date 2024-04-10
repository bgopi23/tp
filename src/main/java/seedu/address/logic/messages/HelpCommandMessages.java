package seedu.address.logic.messages;

/**
 * Messages used by HelpCommand and associated classes.
 */
public class HelpCommandMessages extends Messages {

    public static final String COMMAND_WORD = "help";
    public static final String SHOWING_HELP_MESSAGE = "Launched help window.";
    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Shows program usage instructions.\n"
            + "Example: " + COMMAND_WORD;
    public static final String INVALID_COMMAND_FORMAT_HELP = String.format(MESSAGE_INVALID_COMMAND_FORMAT,
            MESSAGE_USAGE);

}
