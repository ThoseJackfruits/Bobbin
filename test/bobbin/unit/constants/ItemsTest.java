package bobbin.unit.constants;

import org.junit.Assert;
import org.junit.Test;

import bobbin.constants.Items;
import bobbin.items.Item;
import bobbin.unit.BaseUnitTest;

public class ItemsTest extends BaseUnitTest {
    private final Item blueberryCopy = Items.getCopyOf(Items.BLUEBERRY);

    @Test
    public void getCopyOf() {
        Assert.assertNotNull(blueberryCopy);
        Assert.assertTrue(blueberryCopy != Items.BLUEBERRY);  // Memory addresses should be different,
        Assert.assertEquals(blueberryCopy, Items.BLUEBERRY);  // but properties should be the same.
    }
}
