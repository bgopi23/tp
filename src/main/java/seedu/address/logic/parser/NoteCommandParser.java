package seedu.address.logic.parser;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.messages.NoteCommandMessages.MESSAGE_INVALID_INDEX_NOTE;
import static seedu.address.logic.messages.NoteCommandMessages.MESSAGE_NO_INDEX_NOTE;

import java.util.Arrays;

import seedu.address.commons.core.index.Index;
import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.logic.messages.Messages;
import seedu.address.logic.commands.NoteCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.person.Note;

/**
 * Parses input arguments and creates a new {@code NoteCommand} object
 */
public class NoteCommandParser implements Parser<NoteCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the
     * {@code NoteCommand}
     * and returns a {@code NoteCommand} object for execution.
     *
     * @throws ParseException if the user input does not conform the expected format
     */
    public NoteCommand parse(String args) throws ParseException {
        requireNonNull(args);

        // (note)
        if (args.trim().isEmpty()) {
            throw new ParseException(MESSAGE_NO_INDEX_NOTE);
        }

        String[] argsArray = args.trim().split(" ");

        Index index;
        try {
            index = ParserUtil.parseIndex(argsArray[0]);
        } catch (IllegalValueException ive) {
            throw new ParseException(MESSAGE_INVALID_INDEX_NOTE, ive);
        }

        Note note;
        if (argsArray.length <= 1) {
            note = new Note("");
        } else {
            note = new Note(String.join(" ", Arrays.copyOfRange(argsArray, 1, argsArray.length)).trim());
        }

        return new NoteCommand(index, note);
    }
}
