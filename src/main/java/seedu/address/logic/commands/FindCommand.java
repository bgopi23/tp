package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ADDRESS;
import static seedu.address.logic.parser.CliSyntax.PREFIX_EMAIL;
import static seedu.address.logic.parser.CliSyntax.PREFIX_HEIGHT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NOTE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PHONE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TAG;
import static seedu.address.logic.parser.CliSyntax.PREFIX_WEIGHT;

import seedu.address.commons.util.ToStringBuilder;
import seedu.address.logic.Messages;
import seedu.address.model.Model;
import seedu.address.model.person.predicates.CombinedPredicates;

/**
 * Finds and lists all persons in address book whose name contains any of the
 * argument keywords.
 * Keyword matching is case insensitive.
 */
public class FindCommand extends Command {

    public static final String COMMAND_WORD = "find";

    public static final String VALIDATION_REGEX_RANGE = "^\\d+(\\.\\d+)?,\\s*\\d+(\\.\\d+)?$";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Lists all clients whose specified attribute contains the specified keyword.\n"
            + "Parameters: "
            + "[" + PREFIX_NAME + "NAME] "
            + "[" + PREFIX_PHONE + "PHONE] "
            + "[" + PREFIX_EMAIL + "EMAIL] "
            + "[" + PREFIX_ADDRESS + "ADDRESS] "
            + "[" + PREFIX_HEIGHT + "FROM, TO] "
            + "[" + PREFIX_WEIGHT + "FROM, TO] "
            + "[" + PREFIX_NOTE + "NOTE] "
            + "[" + PREFIX_TAG + "TAG] "
            + "\nExample: " + COMMAND_WORD + " "
            + PREFIX_EMAIL + "lewis@hotmail.com";


    public static final String MESSAGE_USAGE_RANGE = COMMAND_WORD
            + ": Finds using a specified range that is comma-delimited."
            + " FROM parameter must be less than or equal to TO parameter.\n"
            + " This range function only works for the HEIGHT " + PREFIX_HEIGHT
            + " and WEIGHT " + PREFIX_WEIGHT + " attributes.\n"
            + "Parameters: FROM, TO (both of them must be a positive number greater than or equals to 0).\n"
            + "Example: " + COMMAND_WORD + " " + PREFIX_WEIGHT + " 70, 80";

    private final CombinedPredicates predicates;

    public FindCommand(CombinedPredicates predicates) {
        this.predicates = predicates;
    }

    /**
     * Returns if a given string is a valid email.
     */
    public static boolean isValidRange(String test) {
        return test.matches(FindCommand.VALIDATION_REGEX_RANGE);
    }

    @Override
    public CommandResult execute(Model model) {
        requireNonNull(model);
        model.updateFilteredPersonList(predicates);
        return new CommandResult(
                String.format(Messages.MESSAGE_PERSONS_LISTED_OVERVIEW, model.getFilteredPersonList().size()));
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof FindCommand)) {
            return false;
        }

        FindCommand otherFindCommand = (FindCommand) other;
        return predicates.equals(otherFindCommand.predicates);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("predicates", predicates)
                .toString();
    }
}
