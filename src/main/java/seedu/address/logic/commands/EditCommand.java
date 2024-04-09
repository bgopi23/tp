package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.messages.EditCommandMessages.MESSAGE_DUPLICATE_PERSON;
import static seedu.address.logic.messages.EditCommandMessages.MESSAGE_EDIT_PERSON_SUCCESS;
import static seedu.address.logic.messages.EditCommandMessages.MESSAGE_USAGE;
import static seedu.address.model.Model.PREDICATE_SHOW_ALL_PERSONS;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.NavigableMap;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;
import java.util.TreeMap;

import seedu.address.commons.core.index.Index;
import seedu.address.commons.util.CollectionUtil;
import seedu.address.commons.util.ToStringBuilder;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.logic.messages.Messages;
import seedu.address.model.Model;
import seedu.address.model.exercise.ExerciseSet;
import seedu.address.model.person.Address;
import seedu.address.model.person.Email;
import seedu.address.model.person.Height;
import seedu.address.model.person.Name;
import seedu.address.model.person.Note;
import seedu.address.model.person.Person;
import seedu.address.model.person.Phone;
import seedu.address.model.person.messages.PhoneMessages;
import seedu.address.model.person.messages.WeightMessages;
import seedu.address.model.person.weight.Weight;
import seedu.address.model.person.weight.WeightEntry;
import seedu.address.model.tag.Tag;

/**
 * Edits the details of an existing person in the address book.
 */
public class EditCommand extends Command {
    private final Index index;
    private final EditPersonDescriptor editPersonDescriptor;

    /**
     * @param index of the person in the filtered person list to edit
     * @param editPersonDescriptor details to edit the person with
     */
    public EditCommand(Index index, EditPersonDescriptor editPersonDescriptor) {
        requireNonNull(index);
        requireNonNull(editPersonDescriptor);

        this.index = index;
        this.editPersonDescriptor = new EditPersonDescriptor(editPersonDescriptor);
    }

    /**
     * Creates and returns a {@code Person} with the details of {@code personToEdit}
     * edited with {@code editPersonDescriptor}.
     */
    private static Person createEditedPerson(Person personToEdit, EditPersonDescriptor editPersonDescriptor)
            throws CommandException {
        assert personToEdit != null;

        Name updatedName = editPersonDescriptor.getName().orElse(personToEdit.getName());

        Phone updatedPhone = editPersonDescriptor.getPhone().orElse(personToEdit.getPhone());

        Email updatedEmail = editPersonDescriptor.getEmail().orElse(personToEdit.getEmail());

        Address updatedAddress = editPersonDescriptor.getAddress().orElse(personToEdit.getAddress());

        // Get the weight map of the person we are editing.
        NavigableMap<LocalDateTime, Weight> toEditWeightMap =
                new TreeMap<>(personToEdit.getWeights());

        // We only modify the weight map if user has specified the weight prefix.
        if (editPersonDescriptor.getWeight().isPresent()) {
            Weight updatedWeight = editPersonDescriptor.getWeight().get();

            // If there are no more weight values to be removed
            if (updatedWeight.getValue() == 0f && toEditWeightMap.isEmpty()) {
                throw new CommandException(WeightMessages.MESSAGE_EMPTY_WEIGHT_MAP);
            }

            toEditWeightMap.pollLastEntry();

            if (updatedWeight.getValue() != 0f) {
                toEditWeightMap.put(WeightEntry.getTimeOfExecution(), updatedWeight);
            }
        }

        Height updatedHeight = editPersonDescriptor.getHeight().orElse(personToEdit.getHeight());

        Note updatedNote = editPersonDescriptor.getNote().orElse(personToEdit.getNote());

        Set<Tag> updatedTags = editPersonDescriptor.getTags().orElse(personToEdit.getTags());

        // Modification of exercises cannot be performed as part of the edit command
        ExerciseSet exerciseSet = personToEdit.getExerciseSet();

        return new Person(updatedName, updatedPhone, updatedEmail, updatedAddress,
                toEditWeightMap, updatedHeight, updatedNote, updatedTags, exerciseSet);
    }

    /**
     * Gets the warning message for the command's execution.
     *
     * @return The warning message, or an empty string if none.
     */
    private String getMessageWarn(Person editedPerson) {
        boolean isPhoneOfExpectedFormat = editedPerson.getPhone().isExpectedFormat();

        if (!isPhoneOfExpectedFormat) {
            return String.format(Messages.MESSAGE_WARN, PhoneMessages.MESSAGE_EXPECTED);
        }

        return "";
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        List<Person> lastShownList = model.getFilteredPersonList();

        if (this.index.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(String.format(Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX, MESSAGE_USAGE));
        }

        Person personToEdit = lastShownList.get(this.index.getZeroBased());

        Person editedPerson = createEditedPerson(personToEdit, this.editPersonDescriptor);

        if (!personToEdit.isSamePerson(editedPerson) && model.hasPerson(editedPerson)) {
            throw new CommandException(MESSAGE_DUPLICATE_PERSON);
        }

        model.setPerson(personToEdit, editedPerson);
        model.updateFilteredPersonList(PREDICATE_SHOW_ALL_PERSONS);

        String messageSuccess = String.format(MESSAGE_EDIT_PERSON_SUCCESS, editedPerson.getFormattedMessage());
        String messageWarn = this.getMessageWarn(editedPerson);

        String messageResult = String.format("%s%s", messageSuccess, messageWarn);

        return new CommandResult(messageResult);
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof EditCommand)) {
            return false;
        }

        EditCommand otherEditCommand = (EditCommand) other;
        return this.index.equals(otherEditCommand.index)
                && this.editPersonDescriptor.equals(otherEditCommand.editPersonDescriptor);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("index", this.index)
                .add("editPersonDescriptor", this.editPersonDescriptor)
                .toString();
    }

    /**
     * Stores the details to edit the person with. Each non-empty field value will
     * replace the corresponding field value of the person.
     */
    public static class EditPersonDescriptor {
        private Name name;
        private Phone phone;
        private Email email;
        private Address address;
        private Weight weight;
        private Height height;
        private Note note;
        private Set<Tag> tags;

        public EditPersonDescriptor() {
        }

        /**
         * Copy constructor.
         * A defensive copy of {@code tags} is used internally.
         */
        public EditPersonDescriptor(EditPersonDescriptor toCopy) {
            setName(toCopy.name);
            setPhone(toCopy.phone);
            setEmail(toCopy.email);
            setAddress(toCopy.address);
            setWeight(toCopy.weight);
            setHeight(toCopy.height);
            setNote(toCopy.note);
            setTags(toCopy.tags);
        }

        /**
         * Returns true if at least one field is edited.
         */
        public boolean isAnyFieldEdited() {
            return CollectionUtil.isAnyNonNull(this.name, this.phone, this.email,
                    this.address, this.weight, this.height, this.note, this.tags);
        }

        public Optional<Name> getName() {
            return Optional.ofNullable(this.name);
        }

        public void setName(Name name) {
            this.name = name;
        }

        public Optional<Phone> getPhone() {
            return Optional.ofNullable(this.phone);
        }

        public void setPhone(Phone phone) {
            this.phone = phone;
        }

        public Optional<Email> getEmail() {
            return Optional.ofNullable(this.email);
        }

        public void setEmail(Email email) {
            this.email = email;
        }

        public Optional<Address> getAddress() {
            return Optional.ofNullable(this.address);
        }

        public void setAddress(Address address) {
            this.address = address;
        }

        public Optional<Note> getNote() {
            return Optional.ofNullable(this.note);
        }

        public void setNote(Note note) {
            this.note = note;
        }

        public Optional<Weight> getWeight() {
            return Optional.ofNullable(this.weight);
        }

        public void setWeight(Weight weight) {
            this.weight = weight;
        }

        public Optional<Height> getHeight() {
            return Optional.ofNullable(this.height);
        }

        public void setHeight(Height height) {
            this.height = height;
        }

        /**
         * Returns an unmodifiable tag set, which throws
         * {@code UnsupportedOperationException}
         * if modification is attempted.
         * Returns {@code Optional#empty()} if {@code tags} is null.
         */
        public Optional<Set<Tag>> getTags() {
            return (this.tags != null) ? Optional.of(Collections.unmodifiableSet(this.tags)) : Optional.empty();
        }

        /**
         * Sets {@code tags} to this object's {@code tags}.
         * A defensive copy of {@code tags} is used internally.
         */
        public void setTags(Set<Tag> tags) {
            this.tags = (tags != null) ? new HashSet<>(tags) : null;
        }

        @Override
        public boolean equals(Object other) {
            if (other == this) {
                return true;
            }

            // instanceof handles nulls
            if (!(other instanceof EditPersonDescriptor)) {
                return false;
            }

            EditPersonDescriptor otherEditPersonDescriptor = (EditPersonDescriptor) other;
            return Objects.equals(this.name, otherEditPersonDescriptor.name)
                    && Objects.equals(this.phone, otherEditPersonDescriptor.phone)
                    && Objects.equals(this.email, otherEditPersonDescriptor.email)
                    && Objects.equals(this.address, otherEditPersonDescriptor.address)
                    && Objects.equals(this.weight, otherEditPersonDescriptor.weight)
                    && Objects.equals(this.height, otherEditPersonDescriptor.height)
                    && Objects.equals(this.note, otherEditPersonDescriptor.note)
                    && Objects.equals(this.tags, otherEditPersonDescriptor.tags);
        }

        @Override
        public String toString() {
            return new ToStringBuilder(this)
                    .add("name", this.name)
                    .add("phone", this.phone)
                    .add("email", this.email)
                    .add("address", this.address)
                    .add("weight", this.weight)
                    .add("height", this.height)
                    .add("note", this.note)
                    .add("tags", this.tags)
                    .toString();
        }
    }
}
