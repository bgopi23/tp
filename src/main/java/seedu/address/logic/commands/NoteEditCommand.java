package seedu.address.logic.commands;

import java.util.List;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.logic.messages.Messages;
import seedu.address.logic.messages.NoteCommandMessages;
import seedu.address.model.Model;
import seedu.address.model.person.Note;
import seedu.address.model.person.Person;
import seedu.address.ui.MainWindow;

/**
 * An extension of the Note command which allows the user to modify an existing note.
 */
public class NoteEditCommand extends NoteCommand {

    /**
     * @param index of the person in the filtered person list to edit the note
     */
    public NoteEditCommand(Index index) {
        super(index, new Note(""));
    }

    /**
     * Populates the command box with the proper command syntax to edit the note for the person at the specified index.
     */
    @Override
    public CommandResult execute(Model model) throws CommandException {
        assert (model != null);

        MainWindow mainWindow = MainWindow.getInstance();

        List<Person> lastShownList = model.getFilteredPersonList();

        int zeroBasedIndex = super.getIndex().getZeroBased();
        int oneBasedIndex = super.getIndex().getOneBased();

        if (zeroBasedIndex >= lastShownList.size()) {
            throw new CommandException(String.format(Messages.MESSAGE_INVALID_INDEX,
                    NoteCommandMessages.MESSAGE_USAGE));
        }

        Person personToEdit = lastShownList.get(zeroBasedIndex);
        String noteToEdit = personToEdit.getNote().toString();

        mainWindow.setCommandBoxText(NoteCommandMessages.COMMAND_WORD + " " + oneBasedIndex + " " + noteToEdit);
        mainWindow.moveCommandBoxCursorToEnd();
        return new CommandResult(NoteCommandMessages.MESSAGE_EDIT_FEEDBACK_TO_USER + oneBasedIndex);
    }
}
