package text_engine.unit.boundaries;

import org.junit.Test;

import text_engine.boundaries.Door;
import text_engine.boundaries.Room;
import text_engine.items.Key;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

/**
 * Unit tests for {@link Door}s.
 */
public class DoorTest {

    private final Room r1 = new Room("r1");
    private final Room r2 = new Room("r2");
    private final Room r3 = new Room("r3");
    private final Door d1 = new Door(true, r1, r2);
    private final Door d2 = new Door(false, r1, r2);
    private final Key d1Key = d1.makeKey("D1 Key", "Door 1 Key");
    private final Key d2Key = d2.makeKey("D2 Key", "Door 2 Key");

    @Test
    public void testFits() {
        assertTrue(d1.fits(d1Key));
        assertFalse(d2.fits(d1Key));

        assertTrue(d2.fits(d2Key));
        assertFalse(d1.fits(d2Key));
    }

    @Test
    public void testUnlock() {
        assertTrue(d1.isLocked());
        assertTrue(d1.unlock(d2Key));
        assertTrue(d1.isLocked());

        assertFalse(d1.unlock(d1Key));
        assertFalse(d1.isLocked());
    }

    @Test(expected = IllegalStateException.class)
    public void testGetOtherRoomLocked() {
        d1.getOtherRoom(r1);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testGetOtherRoomUnattachedRoom() {
        d2.getOtherRoom(r3);
    }

    @Test
    public void testGetOtherRoom() {
        d1.unlock(d1Key);
        final Room room1OtherRoom = d1.getOtherRoom(r1);
        assertEquals(r2, room1OtherRoom);

        final Room room2OtherRoom = d2.getOtherRoom(r2);
        assertEquals(r1, room2OtherRoom);
    }
}
