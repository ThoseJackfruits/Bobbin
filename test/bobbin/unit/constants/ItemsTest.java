package bobbin.unit.constants;

import bobbin.constants.Items;
import bobbin.items.Item;
import bobbin.unit.BaseUnitTest;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ItemsTest extends BaseUnitTest {
    @SuppressWarnings("OptionalGetWithoutIsPresent")
    private final Item blueberryCopy = Items.getCopyOf(Items.BLUEBERRY).get();

    @Test
    void getCopyOf() {
        assertNotNull(blueberryCopy);
        assertNotSame(blueberryCopy, Items.BLUEBERRY);  // Memory addresses should be different,
        assertEquals(blueberryCopy, Items.BLUEBERRY);  // but properties should be the same.
    }
}
