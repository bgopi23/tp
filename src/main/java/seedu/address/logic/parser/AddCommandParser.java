package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_PARAMETER_FORMAT;
import static seedu.address.logic.Messages.MESSAGE_NAME_PARAMETER_MISSING;
import static seedu.address.logic.Messages.MESSAGE_NO_PARAMETERS;
import static seedu.address.logic.Messages.MESSAGE_PHONE_PARAMETER_MISSING;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ADDRESS;
import static seedu.address.logic.parser.CliSyntax.PREFIX_EMAIL;
import static seedu.address.logic.parser.CliSyntax.PREFIX_HEIGHT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NOTE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PHONE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TAG;
import static seedu.address.logic.parser.CliSyntax.PREFIX_WEIGHT;

import java.time.LocalDateTime;
import java.util.NavigableMap;
import java.util.Set;
import java.util.TreeMap;

import seedu.address.logic.commands.AddCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.person.Address;
import seedu.address.model.person.Email;
import seedu.address.model.person.Name;
import seedu.address.model.person.Note;
import seedu.address.model.person.Person;
import seedu.address.model.person.Phone;
import seedu.address.model.person.WeightTemp;
import seedu.address.model.person.height.Height;
import seedu.address.model.person.height.HeightEntry;
import seedu.address.model.tag.Tag;

/**
 * Parses input arguments and creates a new AddCommand object
 */
public class AddCommandParser implements Parser<AddCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the AddCommand
     * and returns an AddCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public AddCommand parse(String args) throws ParseException {
        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(args, PREFIX_NAME, PREFIX_PHONE, PREFIX_EMAIL, PREFIX_ADDRESS,
                        PREFIX_HEIGHT, PREFIX_WEIGHT, PREFIX_NOTE, PREFIX_TAG);

        // (add)
        if (args.isEmpty()) {
            throw new ParseException(String.format(MESSAGE_NO_PARAMETERS, AddCommand.MESSAGE_USAGE));
        }

        // (add John)
        if (!argMultimap.containsAll(PREFIX_NAME, PREFIX_PHONE)
                || !argMultimap.getPreamble().isEmpty()) {
            throw new ParseException(String.format(MESSAGE_INVALID_PARAMETER_FORMAT, AddCommand.MESSAGE_USAGE));
        }

        // (add p/99898888)
        if (!argMultimap.contains(PREFIX_NAME)) {
            throw new ParseException(String.format(MESSAGE_NAME_PARAMETER_MISSING, AddCommand.MESSAGE_USAGE));
        }

        // (add n/John)
        if (!argMultimap.contains(PREFIX_PHONE)) {
            throw new ParseException(String.format(MESSAGE_PHONE_PARAMETER_MISSING, AddCommand.MESSAGE_USAGE));
        }

        // (add n/John p/98988898...)
        argMultimap.verifyNoDuplicatePrefixesFor(PREFIX_NAME, PREFIX_PHONE, PREFIX_EMAIL, PREFIX_HEIGHT,
                PREFIX_WEIGHT, PREFIX_NOTE, PREFIX_ADDRESS);

        Name name = ParserUtil.parseName(argMultimap.getStringValue(PREFIX_NAME));
        Phone phone = ParserUtil.parsePhone(argMultimap.getStringValue(PREFIX_PHONE));
        Email email = ParserUtil.parseEmail(argMultimap.getValue(PREFIX_EMAIL));
        Address address = ParserUtil.parseAddress(argMultimap.getValue(PREFIX_ADDRESS));
        NavigableMap<LocalDateTime, Height> heightMap = new TreeMap<>();
        if (ParserUtil.parseHeight(argMultimap.getValue(PREFIX_HEIGHT)).getValue() != 0f) {
            heightMap.put(HeightEntry.getTimeOfExecution(),
                    ParserUtil.parseHeight(argMultimap.getValue(PREFIX_HEIGHT)));
        }
        WeightTemp weightTemp = ParserUtil.parseWeightTemp(argMultimap.getValue(PREFIX_WEIGHT));
        Note note = ParserUtil.parseNote(argMultimap.getValue(PREFIX_NOTE));
        Set<Tag> tagList = ParserUtil.parseTags(argMultimap.getAllValues(PREFIX_TAG));

        Person person = new Person(name, phone, email, address, heightMap, weightTemp, note, tagList);

        return new AddCommand(person);
    }
}
