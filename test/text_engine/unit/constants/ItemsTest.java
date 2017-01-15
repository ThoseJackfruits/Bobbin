package text_engine.unit.constants;

import org.junit.Test;

import text_engine.constants.Items;
import text_engine.items.Item;
import text_engine.unit.BaseUnitTest;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

public class ItemsTest extends BaseUnitTest {
    private final Item blueberryCopy = Items.getCopyOf(Items.BLUEBERRY);

    @Test
    public void getCopyOf() {
        assertNotNull(blueberryCopy);
        assertTrue(blueberryCopy != Items.BLUEBERRY);  // Memory addresses should be different,
        assertEquals(blueberryCopy, Items.BLUEBERRY);  // but properties should be the same.
    }
}
