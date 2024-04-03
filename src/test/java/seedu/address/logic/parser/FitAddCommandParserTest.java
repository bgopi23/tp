package seedu.address.logic.parser;

import static seedu.address.logic.commands.CommandTestUtil.INVALID_EXERCISE_BREAK_DESC;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_EXERCISE_NAME_DESC;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_EXERCISE_REPS_DESC;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_EXERCISE_SETS_DESC;
import static seedu.address.logic.commands.CommandTestUtil.PREAMBLE_WHITESPACE;
import static seedu.address.logic.commands.CommandTestUtil.VALID_EXERCISE_BREAK;
import static seedu.address.logic.commands.CommandTestUtil.VALID_EXERCISE_BREAK_DESC;
import static seedu.address.logic.commands.CommandTestUtil.VALID_EXERCISE_NAME;
import static seedu.address.logic.commands.CommandTestUtil.VALID_EXERCISE_NAME_DESC;
import static seedu.address.logic.commands.CommandTestUtil.VALID_EXERCISE_REPS;
import static seedu.address.logic.commands.CommandTestUtil.VALID_EXERCISE_REPS_DESC;
import static seedu.address.logic.commands.CommandTestUtil.VALID_EXERCISE_SETS;
import static seedu.address.logic.commands.CommandTestUtil.VALID_EXERCISE_SETS_DESC;
import static seedu.address.logic.parser.CliSyntax.PREFIX_EXERCISE_ABS;
import static seedu.address.logic.parser.CliSyntax.PREFIX_EXERCISE_ALL;
import static seedu.address.logic.parser.CliSyntax.PREFIX_EXERCISE_ARM;
import static seedu.address.logic.parser.CliSyntax.PREFIX_EXERCISE_BACK;
import static seedu.address.logic.parser.CliSyntax.PREFIX_EXERCISE_CHEST;
import static seedu.address.logic.parser.CliSyntax.PREFIX_EXERCISE_LEG;
import static seedu.address.logic.parser.CliSyntax.PREFIX_EXERCISE_SHOULDER;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_PERSON;

import java.util.HashSet;
import java.util.Set;

import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.FitAddCommand;
import seedu.address.logic.messages.FitAddCommandMessages;
import seedu.address.logic.messages.Messages;
import seedu.address.model.exercise.Exercise;

public class FitAddCommandParserTest {

    private FitAddCommandParser parser = new FitAddCommandParser();

    @Test
    public void parse_missingParts_failure() {
        // no index specified
        assertParseFailure(parser, VALID_EXERCISE_NAME_DESC,
            FitAddCommandMessages.MESSAGE_NO_INDEX_FITADD);

        // no field specified
        assertParseFailure(parser, "1",
            FitAddCommandMessages.MESSAGE_EXERCISE_NAME_PARAMETER_AND_DEFAULT_PREFIXES_MISSING);

        // no index and no field specified
        assertParseFailure(parser, "", FitAddCommandMessages.MESSAGE_NO_INDEX_FITADD);
    }

    @Test
    public void parse_invalidPreamble_failure() {
        // negative index
        assertParseFailure(parser, "-5" + VALID_EXERCISE_NAME_DESC,
            FitAddCommandMessages.MESSAGE_INVALID_INDEX_FITADD);

        // zero index
        assertParseFailure(parser, "0" + VALID_EXERCISE_NAME_DESC,
            FitAddCommandMessages.MESSAGE_INVALID_INDEX_FITADD);

        // invalid arguments being parsed as preamble
        assertParseFailure(parser, "1 some random string",
            String.format(Messages.MESSAGE_INVALID_COMMAND_FORMAT, FitAddCommandMessages.MESSAGE_USAGE));
    }

    @Test
    public void parse_invalidValue_failure() {
        assertParseFailure(parser, "1" + INVALID_EXERCISE_NAME_DESC, Exercise.NAME_CONSTRAINT); // invalid exercise name
        assertParseFailure(parser, "1" + VALID_EXERCISE_NAME_DESC + INVALID_EXERCISE_SETS_DESC,
            Exercise.SETS_CONSTRAINT); // invalid exercise sets
        assertParseFailure(parser, "1" + VALID_EXERCISE_NAME_DESC + INVALID_EXERCISE_REPS_DESC,
            Exercise.REPS_CONSTRAINT); // invalid exercise reps
        assertParseFailure(parser, "1" + VALID_EXERCISE_NAME_DESC + INVALID_EXERCISE_BREAK_DESC,
            Exercise.BREAK_CONSTRAINT); // invalid exercise break
    }

    @Test
    public void parse_exerciseSpecified_success() {
        Exercise exercise = new Exercise(VALID_EXERCISE_NAME.toLowerCase(), Integer.parseInt(VALID_EXERCISE_SETS),
            Integer.parseInt(VALID_EXERCISE_REPS), Integer.parseInt(VALID_EXERCISE_BREAK));
        Set<Exercise> exerciseSet = new HashSet<>();
        exerciseSet.add(exercise);

        // preamble with leading whitespace
        assertParseSuccess(parser, PREAMBLE_WHITESPACE + "1" + VALID_EXERCISE_NAME_DESC
                + VALID_EXERCISE_SETS_DESC + VALID_EXERCISE_REPS_DESC + VALID_EXERCISE_BREAK_DESC,
            new FitAddCommand(INDEX_FIRST_PERSON, exerciseSet));
    }

    @Test
    public void parse_defaultExercisePrefixes_success() {
        // arm exercises
        assertParseSuccess(parser, "1" + " " + PREFIX_EXERCISE_ARM,
            new FitAddCommand(INDEX_FIRST_PERSON, FitAddCommand.DEFAULT_ARM_EXERCISES));

        // leg exercises
        assertParseSuccess(parser, "1" + " " + PREFIX_EXERCISE_LEG,
            new FitAddCommand(INDEX_FIRST_PERSON, FitAddCommand.DEFAULT_LEG_EXERCISES));

        // chest exercises
        assertParseSuccess(parser, "1" + " " + PREFIX_EXERCISE_CHEST,
            new FitAddCommand(INDEX_FIRST_PERSON, FitAddCommand.DEFAULT_CHEST_EXERCISES));

        // back exercises
        assertParseSuccess(parser, "1" + " " + PREFIX_EXERCISE_BACK,
            new FitAddCommand(INDEX_FIRST_PERSON, FitAddCommand.DEFAULT_BACK_EXERCISES));

        // shoulder exercises
        assertParseSuccess(parser, "1" + " " + PREFIX_EXERCISE_SHOULDER,
            new FitAddCommand(INDEX_FIRST_PERSON, FitAddCommand.DEFAULT_SHOULDER_EXERCISES));

        // abs exercises
        assertParseSuccess(parser, "1" + " " + PREFIX_EXERCISE_ABS,
            new FitAddCommand(INDEX_FIRST_PERSON, FitAddCommand.DEFAULT_ABS_EXERCISES));

        // all exercises
        Set<Exercise> allExercises = new HashSet<>();
        allExercises.addAll(FitAddCommand.DEFAULT_ARM_EXERCISES);
        allExercises.addAll(FitAddCommand.DEFAULT_LEG_EXERCISES);
        allExercises.addAll(FitAddCommand.DEFAULT_CHEST_EXERCISES);
        allExercises.addAll(FitAddCommand.DEFAULT_BACK_EXERCISES);
        allExercises.addAll(FitAddCommand.DEFAULT_SHOULDER_EXERCISES);
        allExercises.addAll(FitAddCommand.DEFAULT_ABS_EXERCISES);
        assertParseSuccess(parser, "1" + " " + PREFIX_EXERCISE_ALL,
            new FitAddCommand(INDEX_FIRST_PERSON, allExercises));
    }
}
