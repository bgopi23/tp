package seedu.address.storage;

import java.time.LocalDateTime;
import java.util.AbstractMap;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.person.height.Height;
import seedu.address.model.person.height.HeightEntry;
import seedu.address.model.tag.Tag;

/**
 * Jackson-friendly version of {@link Height} with {@code LocalDateTime} as key.
 */
class JsonAdaptedHeight {

    private final String heightDate;
    private final String heightValue;

    /**
     * Constructs a {@code JsonAdaptedHeight} with the given {@code heightDate} and {@code heightValue}.
     */
    @JsonCreator
    public JsonAdaptedHeight(@JsonProperty("heightDate") String heightDate,
                             @JsonProperty("heightValue") String heightValue) {
        this.heightDate = heightDate;
        this.heightValue = heightValue;
    }

    /**
     * Converts a given {@code Tag} into this class for Jackson use.
     */
    public JsonAdaptedHeight(HeightEntry source) {
        this.heightDate = source.getValue().getKey().toString();
        this.heightValue = source.getValue().getValue().toString();
    }

    /**
     * Converts this Jackson-friendly adapted date-height key-value pair into the model's
     * {@code Entry<LocalDateTime, Height>} object.
     *
     * @throws IllegalValueException if there were any data constraints violated in
     *                               the adapted tag.
     */
    public HeightEntry toModelType() throws IllegalValueException {
        if (!Height.isValidHeight(this.heightValue)) {
            throw new IllegalValueException(Tag.MESSAGE_CONSTRAINTS);
        }
        return new HeightEntry(new AbstractMap.SimpleEntry<>(
                LocalDateTime.parse(this.heightDate), new Height(Float.valueOf(this.heightValue))));
    }
}
