package seedu.address.logic.parser;

import static java.util.Objects.requireNonNull;

import java.util.Collection;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import javafx.util.Pair;
import seedu.address.commons.core.index.Index;
import seedu.address.commons.util.StringUtil;
import seedu.address.logic.commands.FindCommand;
import seedu.address.logic.messages.FindCommandMessages;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.exercise.Exercise;
import seedu.address.model.person.Address;
import seedu.address.model.person.Email;
import seedu.address.model.person.Height;
import seedu.address.model.person.Name;
import seedu.address.model.person.Note;
import seedu.address.model.person.Phone;
import seedu.address.model.person.weight.Weight;
import seedu.address.model.tag.Tag;

/**
 * Contains utility methods used for parsing strings in the various *Parser
 * classes.
 */
public class ParserUtil {

    public static final String MESSAGE_INVALID_INDEX = "Index is not a non-zero unsigned integer.";

    /**
     * Parses {@code oneBasedIndex} into an {@code Index} and returns it. Leading
     * and trailing whitespaces will be trimmed.
     *
     * @param oneBasedIndex The one-based index string to be parsed
     * @return The parsed Index object
     * @throws ParseException If the specified index is invalid (not non-zero unsigned integer)
     */
    public static Index parseIndex(String oneBasedIndex) throws ParseException {
        String trimmedIndex = oneBasedIndex.trim();
        if (!StringUtil.isNonZeroUnsignedInteger(trimmedIndex)) {
            throw new ParseException(MESSAGE_INVALID_INDEX);
        }
        return Index.fromOneBased(Integer.parseInt(trimmedIndex));
    }

    /**
     * Parses a {@code String name} into a {@code Name}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @param name The name string to be parsed
     * @return The parsed Name object
     * @throws ParseException If the given {@code name} is invalid
     */
    public static Name parseName(String name) throws ParseException {
        requireNonNull(name);
        String trimmedName = name.trim();
        if (!Name.isValidName(trimmedName)) {
            throw new ParseException(Name.MESSAGE_CONSTRAINTS);
        }
        return new Name(trimmedName);
    }

    /**
     * Parses a {@code String phone} into a {@code Phone}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @param phone The phone string to be parsed
     * @return The parsed Phone object
     * @throws ParseException If the given {@code phone} is invalid
     */
    public static Phone parsePhone(String phone) throws ParseException {
        requireNonNull(phone);
        String trimmedPhone = phone.replaceAll("\\s", "");
        if (!Phone.isValidPhone(trimmedPhone)) {
            throw new ParseException(Phone.MESSAGE_CONSTRAINTS);
        }
        return new Phone(trimmedPhone);
    }

    /**
     * Parses a {@code Optional<String> address} into an {@code Address}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @param address The optional address string to be parsed
     * @return The parsed Address object
     * @throws ParseException If the given {@code address} is invalid
     */
    public static Address parseAddress(Optional<String> address) throws ParseException {
        requireNonNull(address);

        String trimmedAddress = "";
        if (address.isPresent() && !address.get().isEmpty()) {
            trimmedAddress = address.get().trim();
            if (!Address.isValidAddress(trimmedAddress)) {
                throw new ParseException(Address.MESSAGE_CONSTRAINTS);
            }
        }

        return new Address(trimmedAddress);
    }

    /**
     * Parses a {@code Optional<String> email} into an {@code Email}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @param email The optional email string to be parsed
     * @return The parsed Email object
     * @throws ParseException If the given {@code email} is invalid
     */
    public static Email parseEmail(Optional<String> email) throws ParseException {
        requireNonNull(email);

        String trimmedEmail = "";
        if (email.isPresent() && !email.get().isEmpty()) {
            trimmedEmail = email.get().trim();
            if (!Email.isValidEmail(trimmedEmail)) {
                throw new ParseException(Email.MESSAGE_CONSTRAINTS);
            }
        }

        return new Email(trimmedEmail);
    }

    /**
     * Parses a {@code Optional<String> note} into a {@code Note}.
     * Leading and trailing whitespaces will be trimmed.
     * If the {@code Optional} is empty, return a {@code Note} with an empty string.
     *
     * @param note The optional note string to be parsed
     * @return The parsed Note object
     */
    public static Note parseNote(Optional<String> note) {
        requireNonNull(note);
        return note.isEmpty() ? new Note("") : new Note(note.get().trim());
    }

    /**
     * Parses a {@code Optional<String> weight} into a {@code Weight}.
     * If the {@code Optional} is empty, return a {@code Weight} with an uninitialized value of 0f.
     *
     * @param weight The optional weight string to be parsed
     * @return The parsed Weight object
     * @throws ParseException If the given {@code weight} is invalid
     */
    public static Weight parseWeight(Optional<String> weight) throws ParseException {
        requireNonNull(weight);

        if (!weight.isEmpty()) {
            String trimmedWeight = weight.get().trim();
            if (!Weight.isValidWeight(trimmedWeight)) {
                throw new ParseException(Weight.MESSAGE_CONSTRAINTS);
            }
            return trimmedWeight.isEmpty() ? new Weight(0f)
                : new Weight(Float.valueOf(trimmedWeight));
        }
        return new Weight(0f);
    }

    /**
     * Parses a {@code Optional<String> height} into a {@code Height}.
     * If the {@code Optional} is empty, return a {@code Height} with an uninitialized value of 0f.
     *
     * @param height The optional height string to be parsed
     * @return The parsed Height object
     * @throws ParseException If the given {@code height} is invalid
     */
    public static Height parseHeight(Optional<String> height) throws ParseException {
        requireNonNull(height);

        if (!height.isEmpty()) {
            String trimmedHeight = height.get().trim();
            if (!Height.isValidHeight(trimmedHeight)) {
                throw new ParseException(Height.MESSAGE_CONSTRAINTS);
            }
            return trimmedHeight.isEmpty() ? new Height(0f) : new Height(Float.valueOf(trimmedHeight));
        }
        return new Height(0f);
    }

    /**
     * Parses a {@code String tag} into a {@code Tag}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @param tag The tag string to be parsed
     * @return The parsed Tag object
     * @throws ParseException If the given {@code tag} is invalid
     */
    public static Tag parseTag(String tag) throws ParseException {
        requireNonNull(tag);
        String trimmedTag = tag.trim();
        if (!Tag.isValidTagName(trimmedTag)) {
            throw new ParseException(Tag.MESSAGE_CONSTRAINTS);
        }
        return new Tag(trimmedTag);
    }

    /**
     * Parses {@code Collection<String> tags} into a {@code Set<Tag>}.
     *
     * @param tags The collection of tag strings to be parsed
     * @return The set of parsed Tag objects
     * @throws ParseException If any of the given {@code tags} is invalid
     */
    public static Set<Tag> parseTags(Collection<String> tags) throws ParseException {
        requireNonNull(tags);
        final Set<Tag> tagSet = new HashSet<>();
        for (String tagName : tags) {
            tagSet.add(parseTag(tagName));
        }
        return tagSet;
    }

    /**
     * Parses a {@code Optional<String> exerciseNameOpt} into a {@code String}.
     *
     * @param exerciseNameOpt The optional exercise name string to be parsed
     * @return The parsed exercise name string
     */
    public static String parseExerciseName(Optional<String> exerciseNameOpt) throws ParseException {
        requireNonNull(exerciseNameOpt);

        String trimmedExerciseName = exerciseNameOpt.orElse("").trim();
        if (!Exercise.isValidName(trimmedExerciseName)) {
            throw new ParseException(Exercise.NAME_CONSTRAINT);
        }

        return trimmedExerciseName.toLowerCase();
    }

    /**
     * Parses a {@code Optional<String> exerciseSetsOpt} into a {@code Integer}.
     * If the {@code Optional} is empty, return a {@code Integer} with the default value.
     *
     * @param exerciseSetsOpt The optional exercise sets string to be parsed
     * @return The parsed exercise sets value
     * @throws ParseException If the given {@code exerciseSetsOpt} is invalid
     */
    public static Integer parseExerciseSets(Optional<String> exerciseSetsOpt) throws ParseException {
        requireNonNull(exerciseSetsOpt);

        if (exerciseSetsOpt.isEmpty()) {
            return Exercise.DEFAULT_SETS;
        }

        String exerciseSetsTrimmed = exerciseSetsOpt.get().trim();
        if (!StringUtil.isInteger(exerciseSetsTrimmed)) {
            throw new ParseException(Exercise.SETS_CONSTRAINT);
        }

        Integer exerciseSets = Integer.valueOf(exerciseSetsTrimmed);
        if (!Exercise.isValidReps(exerciseSets)) {
            throw new ParseException(Exercise.SETS_CONSTRAINT);
        }

        return exerciseSets;
    }

    /**
     * Parses a {@code Optional<String> exerciseRepsOpt} into a {@code Integer}.
     * If the {@code Optional} is empty, return a {@code Integer} with the default value.
     *
     * @param exerciseRepsOpt The optional exercise reps string to be parsed
     * @return The parsed exercise reps value
     * @throws ParseException If the given {@code exerciseRepsOpt} is invalid
     */
    public static Integer parseExerciseReps(Optional<String> exerciseRepsOpt) throws ParseException {
        requireNonNull(exerciseRepsOpt);

        if (exerciseRepsOpt.isEmpty()) {
            return Exercise.DEFAULT_REPS;
        }

        String exerciseRepsTrimmed = exerciseRepsOpt.orElse("").trim();
        if (!StringUtil.isInteger(exerciseRepsTrimmed)) {
            throw new ParseException(Exercise.REPS_CONSTRAINT);
        }

        Integer exerciseReps = Integer.valueOf(exerciseRepsTrimmed);
        if (!Exercise.isValidReps(exerciseReps)) {
            throw new ParseException(Exercise.REPS_CONSTRAINT);
        }


        return exerciseReps;
    }

    /**
     * Parses a {@code Optional<String> exerciseBreakBetweenSetsOpt} into a {@code Integer}.
     * If the {@code Optional} is empty, return a {@code Integer} with the default value.
     *
     * @param exerciseBreakBetweenSetsOpt The optional exercise rest string to be parsed
     * @return The parsed exercise rest value
     * @throws ParseException If the given {@code exerciseBreakBetweenSetsOpt} is invalid
     */
    public static Integer parseExerciseBreakBetweenSets(Optional<String> exerciseBreakBetweenSetsOpt)
            throws ParseException {
        requireNonNull(exerciseBreakBetweenSetsOpt);

        if (exerciseBreakBetweenSetsOpt.isEmpty()) {
            return Exercise.DEFAULT_BREAK;
        }

        String exerciseRestTrimmed = exerciseBreakBetweenSetsOpt.orElse("").trim();
        if (!StringUtil.isInteger(exerciseRestTrimmed)) {
            throw new ParseException(Exercise.BREAK_CONSTRAINT);
        }

        Integer exerciseBreakBetweenSets = Integer.valueOf(exerciseRestTrimmed);
        if (!Exercise.isValidBreakBetweenSets(exerciseBreakBetweenSets)) {
            throw new ParseException(Exercise.BREAK_CONSTRAINT);
        }

        return exerciseBreakBetweenSets;
    }

    /**
     * Parses the supplied values into a {@code Exercise}.
     *
     * @param name The name of the exercise
     * @param sets The number of sets for the exercise
     * @param reps The number of reps for the exercise
     * @param rest The rest duration for the exercise
     * @return The parsed Exercise object
     * @throws ParseException If any of the supplied values are invalid
     */
    public static Exercise parseExercise(String name, Integer sets, Integer reps, Integer rest) throws ParseException {
        requireNonNull(name);
        requireNonNull(sets);
        requireNonNull(reps);
        requireNonNull(rest);

        if (!Exercise.isValidName(name)) {
            throw new ParseException(Exercise.NAME_CONSTRAINT);
        }

        if (!Exercise.isValidSets(sets)) {
            throw new ParseException(Exercise.SETS_CONSTRAINT);
        }

        if (!Exercise.isValidSets(reps)) {
            throw new ParseException(Exercise.REPS_CONSTRAINT);
        }

        if (!Exercise.isValidBreakBetweenSets(rest)) {
            throw new ParseException(Exercise.BREAK_CONSTRAINT);
        }

        return new Exercise(name, sets, reps, rest);
    }

    /**
     * Parses a {@code String searchString} into a string.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @param searchString The string to search
     * @return The string ready to be used for searching
     */
    public static String parseSearchString(String searchString) {
        requireNonNull(searchString);
        return searchString.trim();
    }

    /**
     * Parses a {@code Optional<String> searchRange} into a Pair of Floats.
     *
     * @param searchRange The optional range string to be parsed
     * @return The Pair instance ready to be used for searching
     * @throws ParseException If the given {@code searchRange} is invalid
     */
    public static Pair<Float, Float> parseSearchRange(Optional<String> searchRange) throws ParseException {
        requireNonNull(searchRange);

        if (searchRange.isPresent() && !searchRange.get().isEmpty()) {
            String trimmedRange = searchRange.get().trim();

            if (!FindCommand.isValidRange(trimmedRange)) {
                throw new ParseException(FindCommandMessages.MESSAGE_USAGE_RANGE);
            }

            String[] range = searchRange.get().split(",\\s*");
            Float fromRange = Float.valueOf(range[0]);
            Float toRange = Float.valueOf(range[1]);

            if (fromRange > toRange) {
                throw new ParseException(FindCommandMessages.MESSAGE_USAGE_RANGE);
            }

            return new Pair<>(fromRange, toRange);
        }

        return new Pair<>(0f, Float.MAX_VALUE);
    }
}
