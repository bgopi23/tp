package seedu.address.storage;

import java.time.LocalDateTime;
import java.util.AbstractMap;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.person.weight.Weight;
import seedu.address.model.person.weight.WeightEntry;
import seedu.address.model.tag.Tag;

/**
 * Jackson-friendly version of {@link Weight} with {@code LocalDateTime} as key.
 */
class JsonAdaptedWeight {

    private final String weightDate;
    private final String weightValue;

    /**
     * Constructs a {@code JsonAdaptedWeight} with the given {@code weightDate} and {@code weightValue}.
     */
    @JsonCreator
    public JsonAdaptedWeight(@JsonProperty("weightDate") String weightDate,
                             @JsonProperty("weightValue") String weightValue) {
        this.weightDate = weightDate;
        this.weightValue = weightValue;
    }

    /**
     * Converts a given {@code Tag} into this class for Jackson use.
     */
    public JsonAdaptedWeight(WeightEntry source) {
        this.weightDate = source.getValue().getKey().toString();
        this.weightValue = source.getValue().getValue().toString();
    }

    /**
     * Converts this Jackson-friendly adapted date-weight key-value pair into the model's
     * {@code Entry<LocalDateTime, Weight>} object.
     *
     * @throws IllegalValueException if there were any data constraints violated in
     *                               the adapted tag.
     */
    public WeightEntry toModelType() throws IllegalValueException {
        if (!Weight.isValidWeight(this.weightValue)) {
            throw new IllegalValueException(Tag.MESSAGE_CONSTRAINTS);
        }
        return new WeightEntry(new AbstractMap.SimpleEntry<>(
                LocalDateTime.parse(this.weightDate), new Weight(Float.valueOf(this.weightValue))));
    }
}