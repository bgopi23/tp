package seedu.address.model.util;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.NavigableMap;
import java.util.Set;
import java.util.TreeMap;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import seedu.address.model.AddressBook;
import seedu.address.model.ReadOnlyAddressBook;
import seedu.address.model.person.Address;
import seedu.address.model.person.Email;
import seedu.address.model.person.Name;
import seedu.address.model.person.Note;
import seedu.address.model.person.Person;
import seedu.address.model.person.Phone;
import seedu.address.model.person.Weight;
import seedu.address.model.person.height.Height;
import seedu.address.model.tag.Tag;

/**
 * Contains utility methods for populating {@code AddressBook} with sample data.
 */
public class SampleDataUtil {
    public static Person[] getSamplePersons() {
        return new Person[] {
            new Person(new Name("Alex Yeoh"), new Phone("87438807"), new Email("alexyeoh@example.com"),
                new Address("Blk 30 Geylang Street 29, #06-40"), getHeightMap("2024-03-27T10:15:30=180f"),
                    new Weight(92.5f), new Note("Likes to swim"), getTagSet("friends")),
            new Person(new Name("Bernice Yu"), new Phone("99272758"), new Email("berniceyu@example.com"),
                new Address("Blk 30 Lorong 3 Sgn Gardens, #07-18"), getHeightMap("2023-04-21T10:12:12=178f"),
                    new Weight(50f), new Note("Likes to swim"), getTagSet("colleagues", "friends")),
            new Person(new Name("Charlotte Oliveiro"), new Phone("93210283"), new Email("charlotte@example.com"),
                new Address("Blk 11 Ang Mo Kio Street 74, #11-04"), getHeightMap("2023-06-20T10:15:30=176f"),
                    new Weight(67.5f), new Note("Likes to swim"), getTagSet("neighbours")),
            new Person(new Name("David Li"), new Phone("91031282"), new Email("lidavid@example.com"),
                new Address("Blk 436 Serangoon Gardens Street 26, #16-43"),
                    getHeightMap("2024-01-20T10:15:33=169f"), new Weight(102.5f), new Note("Likes to swim"),
                    getTagSet("family")),
            new Person(new Name("Irfan Ibrahim"), new Phone("92492021"), new Email("irfan@example.com"),
                new Address("Blk 47 Tampines Street 20, #17-35"), getHeightMap("2023-04-21T10:11:30=173.5f"),
                    new Weight(80.5f), new Note("Likes to swim"), getTagSet("classmates")),
            new Person(new Name("Roy Balakrishnan"), new Phone("92624417"), new Email("royb@example.com"),
                new Address("Blk 45 Aljunied Street 85, #11-31"), getHeightMap("2023-09-20T10:19:31=171f"),
                    new Weight(70.5f), new Note("Likes to snitch"), getTagSet("colleagues"))
        };
    }

    public static ReadOnlyAddressBook getSampleAddressBook() {
        AddressBook sampleAb = new AddressBook();
        for (Person samplePerson : getSamplePersons()) {
            sampleAb.addPerson(samplePerson);
        }
        return sampleAb;
    }

    /**
     * Returns a tag set containing the list of strings given.
     */
    public static Set<Tag> getTagSet(String... strings) {
        return Arrays.stream(strings)
                .map(Tag::new)
                .collect(Collectors.toSet());
    }

    /**
     * Returns a NavigableMap containing the list of LocalDateTime and Height values given as strings.
     */
    public static NavigableMap<LocalDateTime, Height> getHeightMap(String... strings) {
        return Stream.of(strings)
                .map(str -> str.split("="))
                .collect(Collectors.toMap(
                        arr -> LocalDateTime.parse(arr[0].strip()),
                        arr -> new Height(Float.parseFloat(arr[1].strip())), (
                                existing, replacement) -> existing,
                        TreeMap::new
                ));
    }
}
