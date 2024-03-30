package seedu.address.logic.commands;

import seedu.address.model.Model;

import static seedu.address.logic.messages.ExitCommandMessages.MESSAGE_EXIT_ACKNOWLEDGEMENT;

/**
 * Terminates the program.
 */
public class ExitCommand extends Command {

    @Override
    public CommandResult execute(Model model) {
        return new CommandResult(MESSAGE_EXIT_ACKNOWLEDGEMENT, false, true);
    }

}
