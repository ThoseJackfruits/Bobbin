package bobbin.unit.items;

import bobbin.items.BaseGameEntity;
import bobbin.unit.BaseUnitTest;
import org.junit.Assert;
import org.junit.Test;

public class BaseGameEntityTest extends BaseUnitTest {
    private final BaseGameEntity bge = new BaseGameEntity("bge", "Generic game entity");

    @Test
    public void testIsCompatible() {
        Assert.assertFalse(bge.isCompatible());
    }

    @Test
    public void testConsume() {
        Assert.assertFalse(bge.consume().hasEffects());
    }

    @Test()
    public void testEquals() {
        Assert.assertNotEquals(bge, new Object());
    }
}
