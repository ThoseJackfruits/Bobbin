package bobbin.unit.items;

import org.junit.Test;

import sun.reflect.generics.reflectiveObjects.NotImplementedException;

import bobbin.items.BaseGameEntity;
import bobbin.unit.BaseUnitTest;

import static org.junit.Assert.assertFalse;

public class BaseGameEntityTest extends BaseUnitTest {
    private final BaseGameEntity bge = new BaseGameEntity("bge", "Generic game entity");

    @Test
    public void isCompatible() {
        assertFalse(bge.isCompatible());
    }

    @Test(expected = NotImplementedException.class)
    public void consume() {
        bge.consume();
    }

}
