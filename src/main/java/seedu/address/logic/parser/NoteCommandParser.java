package seedu.address.logic.parser;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT_NOTE;
import static seedu.address.logic.Messages.MESSAGE_INVALID_INDEX_NOTE;
import static seedu.address.logic.Messages.MESSAGE_NO_INDEX_NOTE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NOTE;

import seedu.address.commons.core.index.Index;
import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.logic.Messages;
import seedu.address.logic.commands.NoteCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.person.Note;

/**
 * Parses input arguments and creates a new {@code NoteCommand} object
 */
public class NoteCommandParser implements Parser<NoteCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the {@code NoteCommand}
     * and returns a {@code NoteCommand} object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public NoteCommand parse(String args) throws ParseException {
        requireNonNull(args);
        ArgumentMultimap argMultimap = ArgumentTokenizer.tokenize(args, PREFIX_NOTE);

        // (note)
        if (args.isEmpty() || argMultimap.isPreambleEmpty()) {
            throw new ParseException(MESSAGE_NO_INDEX_NOTE);
        }

        // (note 1 2)
        if (!argMultimap.isPreambleAlone()) {
            throw new ParseException(MESSAGE_INVALID_COMMAND_FORMAT_NOTE);
        }

        Index index;
        try {
            index = ParserUtil.parseIndex(argMultimap.getPreamble());
        } catch (IllegalValueException ive) {
            throw new ParseException(MESSAGE_INVALID_INDEX_NOTE, ive);
        }

        argMultimap.verifyNoDuplicatePrefixesFor(PREFIX_NOTE);

        Note note = new Note(argMultimap.getValueOrEmpty(PREFIX_NOTE));

        return new NoteCommand(index, note);
    }
}
