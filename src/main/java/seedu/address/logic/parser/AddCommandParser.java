package seedu.address.logic.parser;

import static seedu.address.logic.messages.AddCommandMessages.MESSAGE_INVALID_PARAMETER_FORMAT_ADD;
import static seedu.address.logic.messages.AddCommandMessages.MESSAGE_NAME_PARAMETER_MISSING_ADD;
import static seedu.address.logic.messages.AddCommandMessages.MESSAGE_NO_PARAMETERS_ADD;
import static seedu.address.logic.messages.AddCommandMessages.MESSAGE_PHONE_PARAMETER_MISSING_ADD;
import static seedu.address.logic.parser.CliSyntax.ALL_PREFIXES;
import static seedu.address.logic.parser.CliSyntax.ALL_PREFIXES_EXCEPT_TAG;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ADDRESS;
import static seedu.address.logic.parser.CliSyntax.PREFIX_EMAIL;
import static seedu.address.logic.parser.CliSyntax.PREFIX_HEIGHT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NOTE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PHONE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TAG;
import static seedu.address.logic.parser.CliSyntax.PREFIX_WEIGHT;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.NavigableMap;
import java.util.Set;
import java.util.TreeMap;

import seedu.address.logic.commands.AddCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.exercise.ExerciseSet;
import seedu.address.model.person.Address;
import seedu.address.model.person.Email;
import seedu.address.model.person.Height;
import seedu.address.model.person.Name;
import seedu.address.model.person.Note;
import seedu.address.model.person.Person;
import seedu.address.model.person.Phone;
import seedu.address.model.person.weight.Weight;
import seedu.address.model.person.weight.WeightEntry;
import seedu.address.model.tag.Tag;

/**
 * Parses input arguments and creates a new AddCommand object
 */
public class AddCommandParser implements Parser<AddCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the AddCommand
     * and returns an AddCommand object for execution.
     *
     * @throws ParseException if the client's input does not conform the expected format
     */
    public AddCommand parse(String args) throws ParseException {
        ArgumentMultimap argMultimap = ArgumentTokenizer.tokenize(args, ALL_PREFIXES);

        // (add)
        if (args.isEmpty()) {
            throw new ParseException(MESSAGE_NO_PARAMETERS_ADD);
        }

        // (add John)
        if (!argMultimap.isPreambleEmpty()) {
            throw new ParseException(MESSAGE_INVALID_PARAMETER_FORMAT_ADD);
        }

        // (add p/99898888)
        if (!argMultimap.contains(PREFIX_NAME)) {
            throw new ParseException(MESSAGE_NAME_PARAMETER_MISSING_ADD);
        }

        // (add n/John)
        if (!argMultimap.contains(PREFIX_PHONE)) {
            throw new ParseException(MESSAGE_PHONE_PARAMETER_MISSING_ADD);
        }

        // (add n/John p/98988898...)
        argMultimap.verifyNoDuplicatePrefixesFor(ALL_PREFIXES_EXCEPT_TAG);

        Name name = ParserUtil.parseName(argMultimap.getStringValue(PREFIX_NAME));

        Phone phone = ParserUtil.parsePhone(argMultimap.getStringValue(PREFIX_PHONE));

        Email email = ParserUtil.parseEmail(argMultimap.getValue(PREFIX_EMAIL));

        Address address = ParserUtil.parseAddress(argMultimap.getValue(PREFIX_ADDRESS));

        NavigableMap<LocalDateTime, Weight> weightMap = new TreeMap<>();
        if (ParserUtil.parseWeight(argMultimap.getValue(PREFIX_WEIGHT)).getValue() != 0f) {
            weightMap.put(WeightEntry.getTimeOfExecution(),
                ParserUtil.parseWeight(argMultimap.getValue(PREFIX_WEIGHT)));
        }

        Height height = ParserUtil.parseHeight(argMultimap.getValue(PREFIX_HEIGHT));

        Note note = ParserUtil.parseNote(argMultimap.getValue(PREFIX_NOTE));

        Set<Tag> tagList = ParserUtil.parseTags(argMultimap.getAllValues(PREFIX_TAG));

        ExerciseSet exercises = new ExerciseSet(new HashSet<>());

        Person person = new Person(name, phone, email, address, weightMap, height, note, tagList, exercises);

        return new AddCommand(person);
    }
}
