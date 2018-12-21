package bobbin.unit.constants;

import bobbin.constants.Items;
import bobbin.items.Item;
import bobbin.unit.BaseUnitTest;
import org.junit.Assert;
import org.junit.Test;

public class ItemsTest extends BaseUnitTest {
    @SuppressWarnings("ConstantConditions")
    private final Item blueberryCopy = Items.getCopyOf(Items.BLUEBERRY).get();

    @Test
    public void getCopyOf() {
        Assert.assertNotNull(blueberryCopy);
        Assert.assertTrue(
                blueberryCopy != Items.BLUEBERRY);  // Memory addresses should be different,
        Assert.assertEquals(blueberryCopy, Items.BLUEBERRY);  // but properties should be the same.
    }
}
