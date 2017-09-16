package bobbin.unit.items;

import org.junit.Assert;
import org.junit.Test;

import bobbin.items.Key;
import bobbin.unit.BaseUnitTest;

/**
 * Unit tests for {@link Key}s.
 */
public class KeyTest extends BaseUnitTest {
    private final Key door1Key1 = door1Room1Room2Unlocked.makeKey("D1 Key", "Door 1 Key");
    private final Key door1Key2 = door1Room1Room2Unlocked.makeKey("D1 Key", "Door 1 Key");
    private final Key door2Key = door2Room2Room3Locked.makeKey("D2 Key", "Door 2 Key");
    private final Key randomKey = new Key("Random Key", "A key without an associated door.");

    @Test
    public void equals() {
        Assert.assertFalse(randomKey.equals(door2Key));
        Assert.assertFalse(door1Key1.equals(door2Key));
        Assert.assertTrue(door1Key1.equals(door1Key2));
    }

    @Test
    public void fits() {
        Assert.assertFalse(randomKey.fits(door1Room1Room2Unlocked));
        Assert.assertFalse(randomKey.fits(door2Room2Room3Locked));

        Assert.assertTrue(door1Key1.fits(door1Room1Room2Unlocked));
        Assert.assertTrue(door1Key2.fits(door1Room1Room2Unlocked));

        Assert.assertFalse(door2Key.fits(door1Room1Room2Unlocked));
        Assert.assertTrue(door2Key.fits(door2Room2Room3Locked));
    }

    @Test
    public void unlock() {
        // Try to unlock locked door with bad key...
        Assert.assertTrue(door2Room2Room3Locked.isLocked());
        Assert.assertTrue(keyToDoor1.unlock(door2Room2Room3Locked));
        Assert.assertTrue(door2Room2Room3Locked.isLocked());

        // then correct key.
        Assert.assertFalse(keyToDoor2.unlock(door2Room2Room3Locked));
        Assert.assertFalse(door2Room2Room3Locked.isLocked());
    }
}
