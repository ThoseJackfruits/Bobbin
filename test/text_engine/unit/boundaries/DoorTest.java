package text_engine.unit.boundaries;

import org.junit.Test;

import text_engine.boundaries.Door;
import text_engine.boundaries.Room;
import text_engine.items.Key;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

/**
 * Unit tests for {@link Door}s.
 */
public class DoorTest {

    Room r1 = new Room("r1");
    Room r2 = new Room("r2");
    Door d1 = new Door(true, r1, r2);
    Door d2 = new Door(true, r1, r2);

    @Test
    public void testKeyFitting() {
        int keyCount = 10;
        Key[] d1Keys = new Key[keyCount];
        Key[] d2Keys = new Key[keyCount];

        for (int i = 0; i < keyCount; i++) {
            d1Keys[i] = d1.makeKey(String.format("Key #%d", i), "Key 1");
            d2Keys[i] = d2.makeKey(String.format("Key #%d", i), "Key 2");
        }

        for (Key k : d1Keys) {
            assertTrue(d1.fits(k));
            assertFalse(d2.fits(k));
        }

        for (Key k : d2Keys) {
            assertTrue(d2.fits(k));
            assertFalse(d1.fits(k));
        }
    }
}
