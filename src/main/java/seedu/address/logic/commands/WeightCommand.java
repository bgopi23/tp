package seedu.address.logic.commands;

import seedu.address.model.Model;

/**
 * Changes the weight of an existing person in the address book.
 */
public class WeightCommand extends Command {

    public static final String COMMAND_WORD = "weight";

    @Override
    public CommandResult execute(Model model) {
        return new CommandResult("Hello from weight");
    }
}
