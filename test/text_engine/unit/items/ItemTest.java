package text_engine.unit.items;

import org.junit.Test;

import text_engine.items.Item;
import text_engine.unit.BaseTest;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;

public class ItemTest extends BaseTest {

    private final Item combinationResult = new Item("combinationResult", "Result Item");
    private final Item item1 = new Item("item1", "Item 1");
    private final Item item2 = new Item("item2", "Item 2");
    private final Item item3 = new Item("item3", "Item 3");
    private final Item item4 = new Item("item4", "Item 4");

    private static void assertAreCombinable(boolean expect, Item... items) {
        for (Item item : items) {
            assertEquals(expect, item.isCombinable());
        }
    }

    private static void assertAreCompatible(boolean expect, Item... items) {
        for (Item item : items) {
            assertEquals(expect, item.isCompatible(items)); // Filters out duplicate of item
        }
    }

    @Test
    public void testAddCombination() {
        assertAreCombinable(false, item1, item2, item3, item4);
        assertAreCompatible(false, item1, item2, item3);

        item3.addCombination(combinationResult, item1, item2);

        assertAreCompatible(true, item1, item2, item3);
        assertAreCombinable(true, item1, item2, item3);
        assertFalse(item4.isCombinable());
    }

    private void setupForCombine() {
        item3.addCombination(combinationResult, item1, item2);

        gameCharacter.getInventory().add(item1);
        gameCharacter.getInventory().add(item2);
        gameCharacter.getInventory().add(item3);
        gameCharacter.getInventory().add(item4);
    }

    @Test
    public void testCombine() {
        setupForCombine();
        assertEquals(combinationResult, item3.combine(gameCharacter, item1, item2));
    }

    @Test
    public void testCombine_includeSelf() {
        // Including the base object (item3) in the arguments (same behaviour as above).
        setupForCombine();
        assertEquals(combinationResult, item3.combine(gameCharacter, item1, item2, item3));
    }


    @Test(expected = IllegalArgumentException.class)
    public void testCombine_noArgs() {
        setupForCombine();
        assertEquals(combinationResult, item3.combine(gameCharacter));
    }

    @Test(expected = IllegalArgumentException.class)
    public void testCombine_notInInv() {
        setupForCombine();
        assertEquals(combinationResult, item3.combine(playerCharacter, item1, item2));
    }

    @Test(expected = IllegalArgumentException.class)
    public void testCombine_cantCombine() {
        setupForCombine();
        assertEquals(combinationResult, item3.combine(gameCharacter, item1));
    }
}
