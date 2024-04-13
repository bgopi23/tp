package seedu.address.logic.parser;

import static seedu.address.logic.messages.DeleteCommandMessages.MESSAGE_INVALID_INDEX_DELETE;
import static seedu.address.logic.messages.DeleteCommandMessages.MESSAGE_NO_INDEX_DELETE;
import static seedu.address.logic.parser.ParserUtil.parseIndex;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.DeleteCommand;
import seedu.address.logic.parser.exceptions.ParseException;

/**
 * Parses input arguments and creates a new DeleteCommand object
 */
public class DeleteCommandParser implements Parser<DeleteCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the DeleteCommand
     * and returns a DeleteCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public DeleteCommand parse(String args) throws ParseException {
        if (args.isEmpty()) {
            throw new ParseException(MESSAGE_NO_INDEX_DELETE);
        }

        try {
            Index index = parseIndex(args);
            return new DeleteCommand(index);
        } catch (ParseException pe) {
            throw new ParseException(MESSAGE_INVALID_INDEX_DELETE, pe);
        }

    }

}
