package seedu.address.logic.parser;

import static seedu.address.logic.messages.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.messages.Messages.MESSAGE_UNKNOWN_COMMAND;

import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import seedu.address.commons.core.LogsCenter;
import seedu.address.logic.commands.Command;
import seedu.address.logic.commands.ExitCommand;
import seedu.address.logic.commands.HelpCommand;
import seedu.address.logic.commands.ListCommand;
import seedu.address.logic.messages.AddCommandMessages;
import seedu.address.logic.messages.ClearCommandMessages;
import seedu.address.logic.messages.DeleteCommandMessages;
import seedu.address.logic.messages.EditCommandMessages;
import seedu.address.logic.messages.ExitCommandMessages;
import seedu.address.logic.messages.FindCommandMessages;
import seedu.address.logic.messages.FitAddCommandMessages;
import seedu.address.logic.messages.FitDeleteCommandMessages;
import seedu.address.logic.messages.HelpCommandMessages;
import seedu.address.logic.messages.NoteCommandMessages;
import seedu.address.logic.messages.WeightCommandMessages;
import seedu.address.logic.parser.exceptions.ParseException;

/**
 * Parses user input.
 */
public class AddressBookParser {

    /**
     * Used for initial separation of command word and args.
     */
    private static final Pattern BASIC_COMMAND_FORMAT = Pattern.compile("(?<commandWord>\\S+)(?<arguments>.*)");
    private static final Logger logger = LogsCenter.getLogger(AddressBookParser.class);

    /**
     * Parses user input into command for execution.
     *
     * @param userInput full user input string
     * @return the command based on the user input
     * @throws ParseException if the user input does not conform the expected format
     */
    public Command parseCommand(String userInput) throws ParseException {
        final Matcher matcher = BASIC_COMMAND_FORMAT.matcher(userInput.trim());
        if (!matcher.matches()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, HelpCommandMessages.MESSAGE_USAGE));
        }

        final String commandWord = matcher.group("commandWord");
        final String arguments = matcher.group("arguments");

        // Note to developers: Change the log level in config.json to enable lower level (i.e., FINE, FINER and lower)
        // log messages such as the one below.
        // Lower level log messages are used sparingly to minimize noise in the code.
        logger.fine("Command word: " + commandWord + "; Arguments: " + arguments);
        switch (commandWord) {
        case NoteCommandMessages.COMMAND_WORD:
            return new NoteCommandParser().parse(arguments);

        case AddCommandMessages.COMMAND_WORD:
            return new AddCommandParser().parse(arguments);

        case EditCommandMessages.COMMAND_WORD:
            return new EditCommandParser().parse(arguments);

        case DeleteCommandMessages.COMMAND_WORD:
            return new DeleteCommandParser().parse(arguments);

        case ClearCommandMessages.COMMAND_WORD:
            return new ClearCommandParser().parse(arguments);

        case FindCommandMessages.COMMAND_WORD:
            return new FindCommandParser().parse(arguments);

        case WeightCommandMessages.COMMAND_WORD:
            return new WeightCommandParser().parse(arguments);

        case ListCommand.COMMAND_WORD:
            return new ListCommand();

        case ExitCommandMessages.COMMAND_WORD:
            return new ExitCommand();

        case HelpCommandMessages.COMMAND_WORD:
            return new HelpCommand();

        case FitAddCommandMessages.COMMAND_WORD:
            return new FitAddCommandParser().parse(arguments);

        case FitDeleteCommandMessages.COMMAND_WORD:
            return new FitDeleteCommandParser().parse(arguments);

        default:
            logger.finer("This user input caused a ParseException: " + userInput);
            throw new ParseException(MESSAGE_UNKNOWN_COMMAND);
        }
    }

}
