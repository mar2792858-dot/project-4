package com.example.iterable;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Tests for Bag behavior, including add(), remove(), contains(), edge cases, and iteration effects.
 */
public class BagTest {

    /**
     * Verifies that adding one item increases size, makes the bag non-empty, and stores the item.
     */
    @Test
    void addSingleItemIncreasesSizeAndMakesBagNonEmpty() {
        Bag<String> bag = new Bag<>();

        bag.add("apple");

        assertEquals(1, bag.size());
        assertFalse(bag.isEmpty());
        assertTrue(bag.contains("apple"));
    }

    /**
     * Verifies that adding several items tracks all additions and each item can be found.
     */
    @Test
    void addMultipleItemsTracksAllAdds() {
        Bag<Integer> bag = new Bag<>();

        bag.add(10);
        bag.add(20);
        bag.add(30);

        assertEquals(3, bag.size());
        assertTrue(bag.contains(10));
        assertTrue(bag.contains(20));
        assertTrue(bag.contains(30));
    }

    /**
     * Verifies that null values are allowed, counted, and reported by contains.
     */
    @Test
    void addNullItemIsSupportedAndCounted() {
        Bag<String> bag = new Bag<>();

        bag.add(null);

        assertEquals(1, bag.size());
        assertTrue(bag.contains(null));
        assertFalse(bag.isEmpty());
    }

    /**
     * Verifies that duplicate values are kept as separate entries in the bag.
     */
    @Test
    void addDuplicateItemsKeepsAllOccurrences() {
        Bag<String> bag = new Bag<>();

        bag.add("book");
        bag.add("book");

        assertEquals(2, bag.size());
        assertTrue(bag.contains("book"));
    }

    /**
     * Verifies that iteration returns added items in insertion order for this ArrayList-backed bag.
     */
    @Test
    void addItemsAreVisibleThroughIteratorInInsertionOrder() {
        Bag<String> bag = new Bag<>();

        bag.add("A");
        bag.add("B");
        bag.add("C");

        List<String> iteratedItems = new ArrayList<>();
        for (String item : bag) {
            iteratedItems.add(item);
        }

        assertEquals(List.of("A", "B", "C"), iteratedItems);
    }

    /**
     * Verifies that removing an existing item returns true, decreases size, and removes that item.
     */
    @Test
    void removeExistingItemReturnsTrueAndUpdatesBagState() {
        Bag<String> bag = new Bag<>();
        bag.add("apple");
        bag.add("banana");

        boolean removed = bag.remove("apple");

        assertTrue(removed);
        assertEquals(1, bag.size());
        assertFalse(bag.contains("apple"));
        assertTrue(bag.contains("banana"));
    }

    /**
     * Verifies that removing an item not in the bag returns false and leaves the bag unchanged.
     */
    @Test
    void removeMissingItemReturnsFalseAndLeavesBagUnchanged() {
        Bag<String> bag = new Bag<>();
        bag.add("apple");
        bag.add("banana");

        boolean removed = bag.remove("pear");

        assertFalse(removed);
        assertEquals(2, bag.size());
        assertTrue(bag.contains("apple"));
        assertTrue(bag.contains("banana"));
    }

    /**
     * Verifies that removing from an empty bag returns false and keeps the bag empty.
     */
    @Test
    void removeFromEmptyBagReturnsFalseAndKeepsBagEmpty() {
        Bag<Integer> bag = new Bag<>();

        boolean removed = bag.remove(10);

        assertFalse(removed);
        assertEquals(0, bag.size());
        assertTrue(bag.isEmpty());
    }

    /**
     * Verifies that a stored null value can be removed and is no longer reported as present.
     */
    @Test
    void removeNullItemWhenPresentReturnsTrueAndRemovesNull() {
        Bag<String> bag = new Bag<>();
        bag.add(null);
        bag.add("apple");

        boolean removed = bag.remove(null);

        assertTrue(removed);
        assertEquals(1, bag.size());
        assertFalse(bag.contains(null));
        assertTrue(bag.contains("apple"));
    }

    /**
     * Verifies that removing a duplicate value removes only one occurrence and keeps the other.
     */
    @Test
    void removeDuplicateItemRemovesOnlyOneOccurrence() {
        Bag<String> bag = new Bag<>();
        bag.add("book");
        bag.add("book");

        boolean removed = bag.remove("book");

        assertTrue(removed);
        assertEquals(1, bag.size());
        assertTrue(bag.contains("book"));
    }

    /**
     * Verifies that iteration reflects removals and preserves the order of the remaining items.
     */
    @Test
    void removeUpdatesItemsVisibleThroughIterator() {
        Bag<String> bag = new Bag<>();
        bag.add("A");
        bag.add("B");
        bag.add("C");

        bag.remove("B");

        List<String> iteratedItems = new ArrayList<>();
        for (String item : bag) {
            iteratedItems.add(item);
        }

        assertEquals(List.of("A", "C"), iteratedItems);
    }

    /**
     * Verifies that contains returns false when called on an empty bag.
     */
    @Test
    void containsReturnsFalseForEmptyBag() {
        Bag<String> bag = new Bag<>();

        assertFalse(bag.contains("apple"));
    }

    /**
     * Verifies that contains returns true for a stored item and false for a value that was never added.
     */
    @Test
    void containsDistinguishesPresentAndAbsentItems() {
        Bag<String> bag = new Bag<>();
        bag.add("apple");
        bag.add("banana");

        assertTrue(bag.contains("banana"));
        assertFalse(bag.contains("pear"));
    }

    /**
     * Verifies that contains handles null correctly by reporting true when null is present and false when it is not.
     */
    @Test
    void containsHandlesNullWhenPresentAndMissing() {
        Bag<String> bag = new Bag<>();
        bag.add(null);
        bag.add("apple");

        assertTrue(bag.contains(null));

        bag.remove(null);

        assertFalse(bag.contains(null));
    }
}