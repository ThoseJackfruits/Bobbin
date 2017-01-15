package bobbin.unit.constants;

import org.junit.Test;

import bobbin.constants.Items;
import bobbin.items.Item;
import bobbin.unit.BaseUnitTest;

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
