package bobbin.unit.items;

import bobbin.items.BaseGameEntity;
import bobbin.unit.BaseUnitTest;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

class BaseGameEntityTest extends BaseUnitTest {
    private final BaseGameEntity bge = new BaseGameEntity("bge", "Generic game entity");

    @Test
    void testIsCompatible() {
        assertFalse(bge.isCompatible());
    }

    @Test
    void testConsume() {
        assertFalse(bge.consume().hasEffects());
    }

    @Test()
    void testEquals() {
        assertNotEquals(bge, new Object());
    }
}
