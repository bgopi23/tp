package seedu.address.model.person;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;
import java.util.TreeMap;

import org.junit.jupiter.api.Test;

import javafx.util.Pair;
import seedu.address.model.person.weight.Weight;
import seedu.address.model.person.weight.WeightEntry;
import seedu.address.model.person.weight.WeightMap;
import seedu.address.model.tag.Tag;
import seedu.address.model.tag.TagSet;

public class WeightMapTest {
    private static LocalDateTime timeStub = WeightEntry.getTimeOfExecution();
    private static Weight weightStub = new Weight(182f);

    @Test
    public void isMatch() {
        TreeMap<LocalDateTime, Weight> treeMap = new TreeMap<>();
        treeMap.put(timeStub, weightStub);

        WeightMap weightMap = new WeightMap(treeMap);

        // Exact range -> returns true
        assertTrue(weightMap.isMatch(new Pair<>(182f, 182f)));

        // Falls within range -> returns true
        assertTrue(weightMap.isMatch(new Pair<>(180f, 185f)));

        // Falls outside of range -> returns false
        assertFalse(weightMap.isMatch(new Pair<>(160f, 169f)));

        // Incorrect generic types -> returns false
        assertFalse(weightMap.isMatch(new Pair<Object, Object>("foo", "bar")));
        assertFalse(weightMap.isMatch(new Pair<Float, Object>(182f, "bar")));
        assertFalse(weightMap.isMatch(new Pair<Object, Float>("foo", 182f)));

        // Null pair -> returns false
        assertFalse(weightMap.isMatch(new Pair<Float, Float>(null, null)));

        // Either value is null -> returns false
        assertFalse(weightMap.isMatch(new Pair<Float, Float>(180f, null)));
        assertFalse(weightMap.isMatch(new Pair<Float, Float>(null, 180f)));
    }

    @Test
    public void equals() {
        Tag friendTag = new Tag("friend");
        Tag loverTag = new Tag("lover");
        Tag invalidTag = new Tag("invalid");

        Set<Tag> testSet = new HashSet<Tag>(Arrays.asList(friendTag, loverTag));

        TagSet tagSet = new TagSet(testSet);

        // Same tagset
        assertTrue(tagSet.equals(tagSet));

        // Different tagset, same values
        assertTrue(tagSet.equals(new TagSet(testSet)));

        // Empty tagset
        assertFalse(tagSet.equals(new TagSet(new HashSet<>())));

        // Partial tagset
        assertFalse(tagSet.equals(new TagSet(new HashSet<Tag>(Arrays.asList(loverTag)))));

        // Additional tag in tagset
        assertFalse(tagSet.equals(new TagSet(new HashSet<>(Arrays.asList(friendTag, loverTag, invalidTag)))));
    }
}
