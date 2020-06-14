package bobbin.unit.items;

import bobbin.items.Key;
import bobbin.unit.BaseUnitTest;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit tests for {@link Key}s.
 */
class KeyTest extends BaseUnitTest {
    private final Key door1Key1 = door1Room1Room2Unlocked.makeKey("D1 Key", "Door 1 Key");
    private final Key door1Key2 = door1Room1Room2Unlocked.makeKey("D1 Key", "Door 1 Key");
    private final Key door2Key = door2Room2Room3Locked.makeKey("D2 Key", "Door 2 Key");
    private final Key randomKey = new Key("Random Key", "A key without an associated door.");

    @Test
    void equals() {
        assertNotEquals(randomKey, door2Key);
        assertNotEquals(door1Key1, door2Key);
        assertEquals(door1Key1, door1Key2);
    }

    @Test
    void fits() {
        assertFalse(randomKey.fits(door1Room1Room2Unlocked));
        assertFalse(randomKey.fits(door2Room2Room3Locked));

        assertTrue(door1Key1.fits(door1Room1Room2Unlocked));
        assertTrue(door1Key2.fits(door1Room1Room2Unlocked));

        assertFalse(door2Key.fits(door1Room1Room2Unlocked));
        assertTrue(door2Key.fits(door2Room2Room3Locked));
    }

    @Test
    void unlock() {
        // Try to unlock locked door with bad key...
        assertTrue(door2Room2Room3Locked.isLocked());
        assertTrue(keyToDoor1.unlock(door2Room2Room3Locked));
        assertTrue(door2Room2Room3Locked.isLocked());

        // then correct key.
        assertFalse(keyToDoor2.unlock(door2Room2Room3Locked));
        assertFalse(door2Room2Room3Locked.isLocked());
    }
}
