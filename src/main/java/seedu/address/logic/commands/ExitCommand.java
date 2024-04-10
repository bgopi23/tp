package seedu.address.logic.commands;

import static seedu.address.logic.messages.ExitCommandMessages.MESSAGE_EXIT_ACKNOWLEDGEMENT;

import seedu.address.model.Model;

/**
 * Terminates the program.
 */
public class ExitCommand extends Command {

    @Override
    public CommandResult execute(Model model) {
        assert(model != null);
        return new CommandResult(MESSAGE_EXIT_ACKNOWLEDGEMENT, false, true);
    }

}
