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
    public void testGetCopyOf() {
        assertNotNull(blueberryCopy);
        assertTrue(blueberryCopy != Items.BLUEBERRY);  // Memory addresses should be different,
        assertEquals(blueberryCopy, Items.BLUEBERRY);  // but properties should be the same.
    }

    @Test
    public void testGetCopyOfConsume() {
        assertNotNull(blueberryCopy);
        assertTrue(gameCharacter.getInventory().contains(blueberryCopy));
        blueberryCopy.consume(gameCharacter);
        assertFalse(gameCharacter.getInventory().contains(blueberryCopy));
    }
}
