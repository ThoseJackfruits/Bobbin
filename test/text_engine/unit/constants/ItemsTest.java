package text_engine.unit.constants;

import org.junit.Test;

import text_engine.constants.Items;
import text_engine.items.Item;
import text_engine.unit.BaseTest;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

public class ItemsTest extends BaseTest {
    private final Item blueberryCopy = Items.getCopyOf(Items.BLUEBERRY);

    @Test
    public void getCopyOf() {
        assertNotNull(blueberryCopy);
        assertTrue(blueberryCopy != Items.BLUEBERRY);  // Memory addresses should be different,
        assertEquals(blueberryCopy, Items.BLUEBERRY);  // but properties should be the same.
    }

    @Test
    public void consume_copy() {
        assertNotNull(blueberryCopy);
        assertTrue(playerCharacter.getInventory().contains(blueberryCopy));
        blueberryCopy.consume(playerCharacter);
        assertFalse(playerCharacter.getInventory().contains(blueberryCopy));
    }
}
