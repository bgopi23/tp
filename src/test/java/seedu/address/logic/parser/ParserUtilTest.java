package seedu.address.logic.parser;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.parser.ParserUtil.MESSAGE_INVALID_INDEX;
import static seedu.address.testutil.Assert.assertThrows;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_PERSON;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import org.junit.jupiter.api.Test;

import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.person.Address;
import seedu.address.model.person.Email;
import seedu.address.model.person.Name;
import seedu.address.model.person.Phone;
import seedu.address.model.tag.Tag;

public class ParserUtilTest {
    private static final String INVALID_NAME = "R@chel";
    private static final String INVALID_PHONE = "+651234";
    private static final String INVALID_ADDRESS = " ";
    private static final String INVALID_EMAIL = "example.com";
    private static final String INVALID_TAG = "#friend";

    private static final String VALID_NAME = "Rachel Walker";
    private static final String VALID_QUARTER_FIRST_PHONE = "88";
    private static final String VALID_QUARTER_SECOND_PHONE = "90";
    private static final String VALID_QUARTER_THIRD_PHONE = "38";

    private static final String VALID_QUARTER_FOURTH_PHONE = "27";

    private static final String VALID_HALF_FIRST_PHONE = "8890";
    private static final String VALID_HALF_SECOND_PHONE = "3827";

    private static final String VALID_PHONE = "88903827";
    private static final String VALID_ADDRESS = "123 Main Street #0505";
    private static final String VALID_EMAIL = "rachel@example.com";
    private static final String VALID_TAG_1 = "friend";
    private static final String VALID_TAG_2 = "neighbour";

    private static final String INVALID_EXERCISE_NAME = "";
    private static final String INVALID_EXERCISE_SETS = "0";
    private static final String INVALID_EXERCISE_REPS = "-10";
    private static final String INVALID_EXERCISE_BREAK = "-5";

    private static final String VALID_EXERCISE_NAME = "Push Ups";
    private static final String VALID_EXERCISE_SETS = "3";
    private static final String VALID_EXERCISE_REPS = "12";
    private static final String VALID_EXERCISE_BREAK = "60";

    private static final String WHITESPACE = " \t\r\n";

    @Test
    public void parseIndex_invalidInput_throwsParseException() {
        assertThrows(ParseException.class, () -> ParserUtil.parseIndex("10 a"));
    }

    @Test
    public void parseIndex_outOfRangeInput_throwsParseException() {
        assertThrows(ParseException.class, MESSAGE_INVALID_INDEX, ()
            -> ParserUtil.parseIndex(Long.toString(Integer.MAX_VALUE + 1)));
    }

    @Test
    public void parseIndex_validInput_success() throws Exception {
        // No whitespaces
        assertEquals(INDEX_FIRST_PERSON, ParserUtil.parseIndex("1"));

        // Leading and trailing whitespaces
        assertEquals(INDEX_FIRST_PERSON, ParserUtil.parseIndex("  1  "));
    }

    @Test
    public void parseName_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> ParserUtil.parseName((String) null));
    }

    @Test
    public void parseName_invalidValue_throwsParseException() {
        assertThrows(ParseException.class, () -> ParserUtil.parseName(INVALID_NAME));
    }

    @Test
    public void parseName_validValueWithoutWhitespace_returnsName() throws Exception {
        Name expectedName = new Name(VALID_NAME);
        assertEquals(expectedName, ParserUtil.parseName(VALID_NAME));
    }

    @Test
    public void parseName_validValueWithWhitespace_returnsTrimmedName() throws Exception {
        String nameWithWhitespace = WHITESPACE + VALID_NAME + WHITESPACE;
        Name expectedName = new Name(VALID_NAME);
        assertEquals(expectedName, ParserUtil.parseName(nameWithWhitespace));
    }

    @Test
    public void parsePhone_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> ParserUtil.parsePhone((String) null));
    }

    @Test
    public void parsePhone_invalidValue_throwsParseException() {
        assertThrows(ParseException.class, () -> ParserUtil.parsePhone(INVALID_PHONE));
    }

    @Test
    public void parsePhone_validValueWithoutWhitespace_returnsPhone() throws Exception {
        Phone expectedPhone = new Phone(VALID_PHONE);
        assertEquals(expectedPhone, ParserUtil.parsePhone(VALID_PHONE));
    }

    @Test
    public void parsePhone_validValueWithMiddleWhitespace_returnsTrimmedPhone() throws Exception {
        String phoneWithWhitespace = VALID_HALF_FIRST_PHONE + WHITESPACE + VALID_HALF_SECOND_PHONE;
        Phone expectedPhone = new Phone(VALID_PHONE);
        assertEquals(expectedPhone, ParserUtil.parsePhone(phoneWithWhitespace));
    }

    @Test
    public void parsePhone_validValueWithMiddleWhitespaces_returnsTrimmedPhone() throws Exception {
        String phoneWithWhitespace = VALID_QUARTER_FIRST_PHONE + WHITESPACE + VALID_QUARTER_SECOND_PHONE + WHITESPACE
            + VALID_QUARTER_THIRD_PHONE + WHITESPACE + VALID_QUARTER_FOURTH_PHONE;
        Phone expectedPhone = new Phone(VALID_PHONE);
        assertEquals(expectedPhone, ParserUtil.parsePhone(phoneWithWhitespace));
    }

    @Test
    public void parsePhone_validValueWithTrailingWhitespace_returnsTrimmedPhone() throws Exception {
        String phoneWithWhitespace = WHITESPACE + VALID_PHONE + WHITESPACE;
        Phone expectedPhone = new Phone(VALID_PHONE);
        assertEquals(expectedPhone, ParserUtil.parsePhone(phoneWithWhitespace));
    }

    @Test
    public void parseAddress_invalidValue_throwsParseException() {
        assertThrows(ParseException.class, () -> ParserUtil.parseAddress(Optional.of(INVALID_ADDRESS)));
    }

    @Test
    public void parseAddress_validValueWithoutWhitespace_returnsAddress() throws Exception {
        Address expectedAddress = new Address(VALID_ADDRESS);
        assertEquals(expectedAddress, ParserUtil.parseAddress(Optional.of(VALID_ADDRESS)));
    }

    @Test
    public void parseAddress_validValueWithWhitespace_returnsTrimmedAddress() throws Exception {
        String addressWithWhitespace = WHITESPACE + VALID_ADDRESS + WHITESPACE;
        Address expectedAddress = new Address(VALID_ADDRESS);
        assertEquals(expectedAddress, ParserUtil.parseAddress(Optional.of(addressWithWhitespace)));
    }

    @Test
    public void parseEmail_invalidValue_throwsParseException() {
        assertThrows(ParseException.class, () -> ParserUtil.parseEmail(Optional.of(INVALID_EMAIL)));
    }

    @Test
    public void parseEmail_validValueWithoutWhitespace_returnsEmail() throws Exception {
        Email expectedEmail = new Email(VALID_EMAIL);
        assertEquals(expectedEmail, ParserUtil.parseEmail(Optional.of(VALID_EMAIL)));
    }

    @Test
    public void parseEmail_validValueWithWhitespace_returnsTrimmedEmail() throws Exception {
        String emailWithWhitespace = WHITESPACE + VALID_EMAIL + WHITESPACE;
        Email expectedEmail = new Email(VALID_EMAIL);
        assertEquals(expectedEmail, ParserUtil.parseEmail(Optional.of(emailWithWhitespace)));
    }

    @Test
    public void parseTag_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> ParserUtil.parseTag(null));
    }

    @Test
    public void parseTag_invalidValue_throwsParseException() {
        assertThrows(ParseException.class, () -> ParserUtil.parseTag(INVALID_TAG));
    }

    @Test
    public void parseTag_validValueWithoutWhitespace_returnsTag() throws Exception {
        Tag expectedTag = new Tag(VALID_TAG_1);
        assertEquals(expectedTag, ParserUtil.parseTag(VALID_TAG_1));
    }

    @Test
    public void parseTag_validValueWithWhitespace_returnsTrimmedTag() throws Exception {
        String tagWithWhitespace = WHITESPACE + VALID_TAG_1 + WHITESPACE;
        Tag expectedTag = new Tag(VALID_TAG_1);
        assertEquals(expectedTag, ParserUtil.parseTag(tagWithWhitespace));
    }

    @Test
    public void parseTags_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> ParserUtil.parseTags(null));
    }

    @Test
    public void parseTags_collectionWithInvalidTags_throwsParseException() {
        assertThrows(ParseException.class, () -> ParserUtil.parseTags(Arrays.asList(VALID_TAG_1, INVALID_TAG)));
    }

    @Test
    public void parseTags_emptyCollection_returnsEmptySet() throws Exception {
        assertTrue(ParserUtil.parseTags(Collections.emptyList()).isEmpty());
    }

    @Test
    public void parseTags_collectionWithValidTags_returnsTagSet() throws Exception {
        Set<Tag> actualTagSet = ParserUtil.parseTags(Arrays.asList(VALID_TAG_1, VALID_TAG_2));
        Set<Tag> expectedTagSet = new HashSet<Tag>(Arrays.asList(new Tag(VALID_TAG_1), new Tag(VALID_TAG_2)));

        assertEquals(expectedTagSet, actualTagSet);
    }

    @Test
    public void parseExerciseName_invalidValue_throwsParseException() {
        assertThrows(ParseException.class, () -> ParserUtil.parseExerciseName(Optional.of(INVALID_EXERCISE_NAME)));
    }

    @Test
    public void parseExerciseName_validValueWithoutWhitespace_returnsExerciseName() throws Exception {
        assertEquals(VALID_EXERCISE_NAME.toLowerCase(), ParserUtil.parseExerciseName(Optional.of(VALID_EXERCISE_NAME)));
    }

    @Test
    public void parseExerciseName_validValueWithWhitespace_returnsTrimmedExerciseName() throws Exception {
        String exerciseNameWithWhitespace = WHITESPACE + VALID_EXERCISE_NAME + WHITESPACE;
        assertEquals(VALID_EXERCISE_NAME.toLowerCase(),
            ParserUtil.parseExerciseName(Optional.of(exerciseNameWithWhitespace)));
    }

    @Test
    public void parseExerciseSets_invalidValue_throwsParseException() {
        assertThrows(ParseException.class, () -> ParserUtil.parseExerciseSets(Optional.of(INVALID_EXERCISE_SETS)));
    }

    @Test
    public void parseExerciseSets_validValueWithoutWhitespace_returnsExerciseSets() throws Exception {
        Integer expectedExerciseSets = Integer.valueOf(VALID_EXERCISE_SETS);
        assertEquals(Optional.of(expectedExerciseSets), ParserUtil.parseExerciseSets(Optional.of(VALID_EXERCISE_SETS)));
    }

    @Test
    public void parseExerciseSets_validValueWithWhitespace_returnsTrimmedExerciseSets() throws Exception {
        String exerciseSetsWithWhitespace = WHITESPACE + VALID_EXERCISE_SETS + WHITESPACE;
        Integer expectedExerciseSets = Integer.valueOf(VALID_EXERCISE_SETS);
        assertEquals(Optional.of(expectedExerciseSets),
            ParserUtil.parseExerciseSets(Optional.of(exerciseSetsWithWhitespace)));
    }

    @Test
    public void parseExerciseReps_invalidValue_throwsParseException() {
        assertThrows(ParseException.class, () -> ParserUtil.parseExerciseReps(Optional.of(INVALID_EXERCISE_REPS)));
    }

    @Test
    public void parseExerciseReps_validValueWithoutWhitespace_returnsExerciseReps() throws Exception {
        Integer expectedExerciseReps = Integer.valueOf(VALID_EXERCISE_REPS);
        assertEquals(Optional.of(expectedExerciseReps), ParserUtil.parseExerciseReps(Optional.of(VALID_EXERCISE_REPS)));
    }

    @Test
    public void parseExerciseReps_validValueWithWhitespace_returnsTrimmedExerciseReps() throws Exception {
        String exerciseRepsWithWhitespace = WHITESPACE + VALID_EXERCISE_REPS + WHITESPACE;
        Integer expectedExerciseReps = Integer.valueOf(VALID_EXERCISE_REPS);
        assertEquals(Optional.of(expectedExerciseReps),
            ParserUtil.parseExerciseReps(Optional.of(exerciseRepsWithWhitespace)));
    }

    @Test
    public void parseExerciseBreakBetweenSets_invalidValue_throwsParseException() {
        assertThrows(ParseException.class, () ->
            ParserUtil.parseExerciseBreakBetweenSets(Optional.of(INVALID_EXERCISE_BREAK)));
    }

    @Test
    public void parseExerciseBreakBetweenSets_validValueWithoutWhitespace_returnsExerciseBreakBetweenSets()
            throws Exception {
        Integer expectedExerciseBreakBetweenSets = Integer.valueOf(VALID_EXERCISE_BREAK);
        assertEquals(Optional.of(expectedExerciseBreakBetweenSets),
            ParserUtil.parseExerciseBreakBetweenSets(Optional.of(VALID_EXERCISE_BREAK)));
    }

    @Test
    public void parseExerciseBreakBetweenSets_validValueWithWhitespace_returnsTrimmedExerciseBreakBetweenSets()
            throws Exception {
        String exerciseBreakWithWhitespace = WHITESPACE + VALID_EXERCISE_BREAK + WHITESPACE;
        Integer expectedExerciseBreakBetweenSets = Integer.valueOf(VALID_EXERCISE_BREAK);
        assertEquals(Optional.of(expectedExerciseBreakBetweenSets),
            ParserUtil.parseExerciseBreakBetweenSets(Optional.of(exerciseBreakWithWhitespace)));
    }
}
