package text_engine.unit.items;

import org.junit.Test;

import text_engine.boundaries.Door;
import text_engine.boundaries.Room;
import text_engine.items.Key;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

/**
 * Unit tests for {@link Key}s.
 */
public class KeyTest {

    private final Room r1 = new Room("r1");
    private final Room r2 = new Room("r2");
    private final Door d1 = new Door(true, r1, r2);
    private final Door d2 = new Door(true, r1, r2);
    private final Key door1Key1 = d1.makeKey("D1 Key", "Door 1 Key");
    private final Key door1Key2 = d1.makeKey("D1 Key", "Door 1 Key");
    private final Key door2Key = d2.makeKey("D2 Key", "Door 2 Key");

    @Test
    public void testEquals() {
        assertFalse(door1Key1.equals(door2Key));
        assertTrue(door1Key1.equals(door1Key2));
    }
}
