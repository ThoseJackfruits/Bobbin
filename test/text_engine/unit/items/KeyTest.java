package text_engine.unit.items;

import org.junit.Test;

import text_engine.items.Key;
import text_engine.unit.BaseTest;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

/**
 * Unit tests for {@link Key}s.
 */
public class KeyTest extends BaseTest {
    private final Key door1Key1 = door1Room1Room2Unlocked.makeKey("D1 Key", "Door 1 Key");
    private final Key door1Key2 = door1Room1Room2Unlocked.makeKey("D1 Key", "Door 1 Key");
    private final Key door2Key = door2Room2Room3Locked.makeKey("D2 Key", "Door 2 Key");

    @Test
    public void testEquals() {
        assertFalse(door1Key1.equals(door2Key));
        assertTrue(door1Key1.equals(door1Key2));
    }
}
