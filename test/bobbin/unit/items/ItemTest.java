package bobbin.unit.items;

import bobbin.effects.GameCharacterEffect;
import bobbin.items.Item;
import bobbin.unit.BaseUnitTest;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

class ItemTest extends BaseUnitTest {

    private final Item combinationResult = new Item("combinationResult", "Result Item");
    private final Item item1 = new Item("item1", "Item 1");
    private final Item item2 = new Item("item2", "Item 2");
    private final Item item3 = new Item("item3", "Item 3");
    private final Item item4 = new Item("item4", "Item 4")
            .addEffect(GameCharacterEffect.NULL);

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
    void addCombination() {
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
    void combine() {
        setupForCombine();
        assertEquals(combinationResult, item3.combine(gameCharacter, item1, item2));
    }

    @Test
    void combine_includeSelf() {
        // Including the base object (item3) in the arguments (same behaviour as above).
        setupForCombine();
        assertEquals(combinationResult, item3.combine(gameCharacter, item1, item2, item3));
    }

    @Test
    void combine_noArgs() {
        setupForCombine();
        assertThrows(
                IllegalArgumentException.class,
                () -> item3.combine(gameCharacter));
    }

    @Test
    void combine_missingItemsInInventory() {
        setupForCombine();
        assertThrows(
                IllegalArgumentException.class,
                () -> item3.combine(playerCharacter, item1, item2));
    }

    @Test
    void combine_missingItemsInCall() {
        setupForCombine();
        assertThrows(
                IllegalArgumentException.class,
                () -> item3.combine(gameCharacter, item1));
    }

    @Test
    void consumable() {
        assertFalse(item3.isConsumable());
        assertTrue(item4.isConsumable());
    }
}
