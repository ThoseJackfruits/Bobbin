package bobbin.unit.items;

import bobbin.effects.GameCharacterEffect;
import bobbin.items.Item;
import bobbin.unit.BaseUnitTest;
import org.junit.Assert;
import org.junit.Test;

public class ItemTest extends BaseUnitTest {

    private final Item combinationResult = new Item("combinationResult", "Result Item");
    private final Item item1 = new Item("item1", "Item 1");
    private final Item item2 = new Item("item2", "Item 2");
    private final Item item3 = new Item("item3", "Item 3");
    private final Item item4 = new Item("item4", "Item 4")
            .addEffect(GameCharacterEffect.NULL);

    private static void assertAreCombinable(boolean expect, Item... items) {
        for (Item item : items) {
            Assert.assertEquals(expect, item.isCombinable());
        }
    }

    private static void assertAreCompatible(boolean expect, Item... items) {
        for (Item item : items) {
            Assert.assertEquals(expect, item.isCompatible(items)); // Filters out duplicate of item
        }
    }

    @Test
    public void addCombination() {
        assertAreCombinable(false, item1, item2, item3, item4);
        assertAreCompatible(false, item1, item2, item3);

        item3.addCombination(combinationResult, item1, item2);

        assertAreCompatible(true, item1, item2, item3);
        assertAreCombinable(true, item1, item2, item3);
        Assert.assertFalse(item4.isCombinable());
    }

    private void setupForCombine() {
        item3.addCombination(combinationResult, item1, item2);

        gameCharacter.getInventory().add(item1);
        gameCharacter.getInventory().add(item2);
        gameCharacter.getInventory().add(item3);
        gameCharacter.getInventory().add(item4);
    }

    @Test
    public void combine() {
        setupForCombine();
        Assert.assertEquals(combinationResult, item3.combine(gameCharacter, item1, item2));
    }

    @Test
    public void combine_includeSelf() {
        // Including the base object (item3) in the arguments (same behaviour as above).
        setupForCombine();
        Assert.assertEquals(combinationResult, item3.combine(gameCharacter, item1, item2, item3));
    }

    @Test(expected = IllegalArgumentException.class)
    public void combine_noArgs() {
        setupForCombine();
        Assert.assertEquals(combinationResult, item3.combine(gameCharacter));
    }

    @Test(expected = IllegalArgumentException.class)
    public void combine_missingItemsInInventory() {
        setupForCombine();
        Assert.assertEquals(combinationResult, item3.combine(playerCharacter, item1, item2));
    }

    @Test(expected = IllegalArgumentException.class)
    public void combine_missingItemsInCall() {
        setupForCombine();
        Assert.assertEquals(combinationResult, item3.combine(gameCharacter, item1));
    }

    @Test
    public void consumable() {
        Assert.assertFalse(item3.isConsumable());
        Assert.assertTrue(item4.isConsumable());
    }
}
