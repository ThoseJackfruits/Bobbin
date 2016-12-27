package text_engine.unit.items;

import org.junit.Test;

import sun.reflect.generics.reflectiveObjects.NotImplementedException;

import text_engine.items.BaseGameEntity;
import text_engine.unit.BaseUnitTest;

import static org.junit.Assert.assertFalse;

public class BaseGameEntityTest extends BaseUnitTest {
    private final BaseGameEntity bge = new BaseGameEntity("bge", "Generic game entity");

    @Test
    public void isCompatible() {
        assertFalse(bge.isCompatible());
    }

    @Test(expected = NotImplementedException.class)
    public void consume() {
        bge.consume(playerCharacter);
    }

}
