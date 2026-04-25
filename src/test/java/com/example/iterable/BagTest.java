package com.example.iterable;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.ConcurrentModificationException;
import java.util.Iterator;
import java.util.List;
import java.util.NoSuchElementException;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Tests for Bag behavior, including add(), remove(), contains(), size(), isEmpty(), edge cases, and iteration effects.
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

    /**
     * Verifies that a newly created bag reports a size of zero.
     */
    @Test
    void sizeReturnsZeroForNewBag() {
        Bag<String> bag = new Bag<>();

        assertEquals(0, bag.size());
    }

    /**
     * Verifies that size updates correctly across a sequence of adds and removes.
     */
    @Test
    void sizeTracksMixedAddAndRemoveOperations() {
        Bag<String> bag = new Bag<>();

        bag.add("apple");
        bag.add("banana");
        bag.add("pear");
        assertEquals(3, bag.size());

        bag.remove("banana");
        assertEquals(2, bag.size());

        bag.remove("apple");
        assertEquals(1, bag.size());

        bag.remove("pear");
        assertEquals(0, bag.size());
    }

    /**
     * Verifies that size counts every stored entry, including null values and duplicates.
     */
    @Test
    void sizeCountsNullAndDuplicateEntries() {
        Bag<String> bag = new Bag<>();

        bag.add(null);
        bag.add("book");
        bag.add("book");

        assertEquals(3, bag.size());
    }

    /**
     * Verifies that isEmpty returns true for a newly created bag.
     */
    @Test
    void isEmptyReturnsTrueForNewBag() {
        Bag<String> bag = new Bag<>();

        assertTrue(bag.isEmpty());
    }

    /**
     * Verifies that isEmpty returns false after at least one item is added.
     */
    @Test
    void isEmptyReturnsFalseAfterAddingItem() {
        Bag<String> bag = new Bag<>();
        bag.add("apple");

        assertFalse(bag.isEmpty());
    }

    /**
     * Verifies that isEmpty returns true again after removing the last remaining item.
     */
    @Test
    void isEmptyReturnsTrueAfterRemovingLastItem() {
        Bag<String> bag = new Bag<>();
        bag.add("apple");

        bag.remove("apple");

        assertTrue(bag.isEmpty());
    }

    /**
     * Verifies that an iterator from an empty bag reports no next element.
     */
    @Test
    void iteratorHasNextReturnsFalseForEmptyBag() {
        Bag<String> bag = new Bag<>();
        Iterator<String> iterator = bag.iterator();

        assertFalse(iterator.hasNext());
    }

    /**
     * Verifies that calling next on an empty iterator throws NoSuchElementException.
     */
    @Test
    void iteratorNextThrowsWhenBagIsEmpty() {
        Bag<String> bag = new Bag<>();
        Iterator<String> iterator = bag.iterator();

        assertThrows(NoSuchElementException.class, iterator::next);
    }

    /**
     * Verifies that hasNext transitions from true to false as elements are consumed.
     */
    @Test
    void iteratorHasNextTracksRemainingElements() {
        Bag<String> bag = new Bag<>();
        bag.add("A");
        bag.add("B");

        Iterator<String> iterator = bag.iterator();

        assertTrue(iterator.hasNext());
        iterator.next();
        assertTrue(iterator.hasNext());
        iterator.next();
        assertFalse(iterator.hasNext());
    }

    /**
     * Verifies that next returns elements in insertion order and throws when exhausted.
     */
    @Test
    void iteratorNextReturnsElementsInOrderAndThrowsWhenExhausted() {
        Bag<String> bag = new Bag<>();
        bag.add("A");
        bag.add("B");
        bag.add("C");

        Iterator<String> iterator = bag.iterator();

        assertEquals("A", iterator.next());
        assertEquals("B", iterator.next());
        assertEquals("C", iterator.next());
        assertThrows(NoSuchElementException.class, iterator::next);
    }

    /**
     * Verifies ArrayList-style remove semantics by removing only the first matching duplicate and preserving order.
     */
    @Test
    void removeDuplicateRemovesFirstMatchAndPreservesOrder() {
        Bag<String> bag = new Bag<>();
        bag.add("A");
        bag.add("X");
        bag.add("B");
        bag.add("X");
        bag.add("C");

        boolean removed = bag.remove("X");

        List<String> iteratedItems = new ArrayList<>();
        for (String item : bag) {
            iteratedItems.add(item);
        }

        assertTrue(removed);
        assertEquals(4, bag.size());
        assertEquals(List.of("A", "B", "X", "C"), iteratedItems);
    }

    /**
     * Verifies ArrayList iterator fail-fast behavior after a structural modification.
     */
    @Test
    void iteratorThrowsConcurrentModificationExceptionAfterStructuralChange() {
        Bag<String> bag = new Bag<>();
        bag.add("A");
        bag.add("B");

        Iterator<String> iterator = bag.iterator();
        bag.add("C");

        assertThrows(ConcurrentModificationException.class, iterator::next);
    }
}