package bobbin.unit.constants;

import bobbin.constants.Items;
import bobbin.items.Item;
import bobbin.unit.BaseUnitTest;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

class ItemsTest extends BaseUnitTest {
    @SuppressWarnings("ConstantConditions")
    private final Item blueberryCopy = Items.getCopyOf(Items.BLUEBERRY).get();

    @Test
    void getCopyOf() {
        assertNotNull(blueberryCopy);
        assertTrue(
                blueberryCopy != Items.BLUEBERRY);  // Memory addresses should be different,
        assertEquals(blueberryCopy, Items.BLUEBERRY);  // but properties should be the same.
    }
}
