package text_engine.unit.constants;

import org.junit.Test;

import text_engine.constants.Items;
import text_engine.items.Item;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class ItemsTest {

    @Test
    public void testGetCopyOf() {
        Item blueberryCopy = Items.getCopyOf(Items.BLUEBERRY);
        assertTrue(blueberryCopy != Items.BLUEBERRY);  // Memory addresses should be different,
        assertEquals(blueberryCopy, Items.BLUEBERRY);  // properties should be the same.
    }
}
