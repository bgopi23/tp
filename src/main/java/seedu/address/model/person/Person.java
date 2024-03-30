package seedu.address.model.person;

import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Map;
import java.util.NavigableMap;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;
import java.util.logging.Logger;

import com.google.zxing.WriterException;

import seedu.address.commons.core.LogsCenter;
import seedu.address.commons.util.ToStringBuilder;
import seedu.address.logic.QrCodeGenerator;
import seedu.address.model.exercise.Exercise;
import seedu.address.model.exercise.ExerciseSet;
import seedu.address.model.person.exceptions.AttributeNotFoundException;
import seedu.address.model.person.weight.Weight;
import seedu.address.model.person.weight.WeightMap;
import seedu.address.model.tag.Tag;
import seedu.address.model.tag.TagSet;

/**
 * Represents a Person in the address book.
 * Guarantees: details are present and not null, field values are validated,
 * immutable.
 */
public class Person {
    private static final Logger logger = LogsCenter.getLogger(Person.class);
    // Identity fields
    private final Name name;
    private final Phone phone;
    private final Email email;
    // Data fields
    private final Address address;
    private final WeightMap weights;
    private final Height height;
    private final Note note;
    private final TagSet tags;
    private final ExerciseSet exercises;

    /**
     * Every field must be present and not null.
     */
    public Person(Name name, Phone phone, Email email, Address address, NavigableMap<LocalDateTime, Weight> weights,
                  Height height, Note note, Set<Tag> tags, ExerciseSet exercises) {
        requireAllNonNull(name, phone, email, address, weights, height, note, tags);
        this.name = name;
        this.phone = phone;
        this.email = email;
        this.address = address;
        this.weights = new WeightMap(weights);
        this.height = height;
        this.note = note;
        Set<Tag> tagSet = new HashSet<>();
        tagSet.addAll(tags);
        this.tags = new TagSet(tagSet);
        this.exercises = exercises;
    }

    /**
     * Get the value of the specified attribute.
     *
     * @param attribute Attribute to retrieve
     * @return Value of the specified attribute
     */
    public Attribute<? extends Object> getAttribute(PersonAttribute attribute) {
        switch (attribute) {
        case NAME:
            return this.name;
        case PHONE:
            return this.phone;
        case EMAIL:
            return this.email;
        case ADDRESS:
            return this.address;
        case NOTE:
            return this.note;
        case WEIGHT:
            return this.weights;
        case HEIGHT:
            return this.height;
        case TAGS:
            return this.tags;
        case EXERCISES:
            return this.exercises;

        default:
            throw new AttributeNotFoundException();
        }
    }

    public Name getName() {
        return name;
    }

    public Phone getPhone() {
        return phone;
    }

    public Email getEmail() {
        return email;
    }

    public Address getAddress() {
        return address;
    }

    public Optional<Map.Entry<LocalDateTime, Weight>> getLatestWeight() {
        return Optional.of(this.weights.getValue().lastEntry());
    }

    /**
     * Returns an immutable navigable map, which throws
     * {@code UnsupportedOperationException}
     * if modification is attempted.
     */
    public NavigableMap<LocalDateTime, Weight> getWeights() {
        return this.weights.getValue();
    }

    public Height getHeight() {
        return this.height;
    }

    public Note getNote() {
        return this.note;
    }


    /**
     * Returns an immutable tag set, which throws
     * {@code UnsupportedOperationException}
     * if modification is attempted.
     */
    public Set<Tag> getTags() {
        return this.tags.getValue();
    }

    /**
     * Returns an immutable exercise set, which throws
     * {@code UnsupportedOperationException}
     * if modification is attempted.
     */
    public Set<Exercise> getExercises() {
        return this.exercises.getValue();
    }

    /**
     * Retrieves the QR code path.
     *
     * @return the path of the generated QR code
     */
    public Path getQrCodePath() {
        return QrCodeGenerator.getQrCodePath(this);
    }

    /**
     * Generates a QR code for the person.
     */
    public void generateQrCode() {
        try {
            QrCodeGenerator.generateQrCode(this);
            logger.info("Generated QR code for " + this);
        } catch (WriterException | IOException e) {
            logger.warning("Unable to generate QR code for " + this);
        }
    }

    /**
     * Returns true if both persons have the same name.
     * This defines a weaker notion of equality between two persons.
     */
    public boolean isSamePerson(Person otherPerson) {
        if (otherPerson == this) {
            return true;
        }

        return otherPerson != null
                && otherPerson.getAttribute(PersonAttribute.NAME).equals(getAttribute(PersonAttribute.NAME))
                && otherPerson.getAttribute(PersonAttribute.PHONE).equals(getAttribute(PersonAttribute.PHONE));
    }

    /**
     * Returns true if both persons have the same identity and data fields.
     * This defines a stronger notion of equality between two persons.
     */
    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof Person)) {
            return false;
        }

        Person otherPerson = (Person) other;
        return name.equals(otherPerson.name)
                && phone.equals(otherPerson.phone)
                && email.equals(otherPerson.email)
                && address.equals(otherPerson.address)
                && tags.equals(otherPerson.tags)
                && note.equals(otherPerson.note)
                && exercises.equals(otherPerson.exercises);
    }

    @Override
    public int hashCode() {
        // use this method for custom fields hashing instead of implementing your own
        return Objects.hash(name, phone, email, address, tags, exercises);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("name", name)
                .add("phone", phone)
                .add("email", email)
                .add("address", address)
                .add("note", note)
                .add("tags", tags)
                .add("exercises", exercises)
                .toString();
    }

    /**
     * Deletes the QR code for the person.
     *
     * @return true if a QR code was deleted, false otherwise
     */
    public boolean deleteQrCode() {
        try {
            boolean result = Files.deleteIfExists(this.getQrCodePath());
            if (result) {
                logger.info("Deleted QR code for " + this);
            } else {
                logger.info("Unable to delete QR code for " + this + " as it does not exist");
            }
            return result;
        } catch (IOException e) {
            logger.warning("Unable to delete QR code for " + this);
            return false;
        }
    }

    /**
     * Generates a formatted message for the Person.
     * Only fields with values are included.
     */
    public String getFormattedMessage() {
        StringBuilder sb = new StringBuilder();

        sb.append("Name: ").append(name);
        sb.append(" | Phone: ").append(phone);

        if (!email.getValue().isEmpty()) {
            sb.append(" | Email: ").append(email);
        }

        if (!address.getValue().isEmpty()) {
            sb.append("\nAddress: ").append(address);
        }

        if (!note.getValue().isEmpty()) {
            sb.append(" | Note: ").append(note);
        }

        if (!weights.getValue().isEmpty()) {
            sb.append(" | Latest Weight: ").append(this.getLatestWeight().get().getValue().toString());
        }

        if (!(height.getValue() == 0f)) {
            sb.append(" | Height: ").append(height);
        }

        if (!this.getTags().isEmpty()) {
            sb.append(" | Tags: ").append(tags);
        }

        return sb.toString();
    }

    /**
     * Attributes of a Person
     */
    public enum PersonAttribute {
        NAME,
        PHONE,
        EMAIL,
        ADDRESS,
        WEIGHT,
        HEIGHT,
        NOTE,
        TAGS,
        EXERCISES
    }
}
