package bobbin.unit.boundaries;

import org.junit.Test;

import bobbin.boundaries.Door;
import bobbin.boundaries.Room;
import bobbin.unit.BaseUnitTest;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertTrue;

/**
 * Unit tests for {@link Door}s.
 */
public class DoorTest extends BaseUnitTest {
    private final Door door1Duplicate = new Door(false, room1, room2);

    @Test
    public void testFits() {
        assertTrue(door1Room1Room2Unlocked.fits(keyToDoor1));
        assertFalse(door2Room2Room3Locked.fits(keyToDoor1));

        assertTrue(door2Room2Room3Locked.fits(keyToDoor2));
        assertFalse(door1Room1Room2Unlocked.fits(keyToDoor2));
    }

    @Test
    public void testUnlock() {
        // Try to unlock already unlocked door with multiple keys. Nothing should change.
        assertFalse(door1Room1Room2Unlocked.isLocked());
        assertFalse(door1Room1Room2Unlocked.unlock(keyToDoor1));
        assertFalse(door1Room1Room2Unlocked.unlock(keyToDoor2));
        assertFalse(door1Room1Room2Unlocked.isLocked());

        // Try to unlock locked door with bad key...
        assertTrue(door2Room2Room3Locked.isLocked());
        assertTrue(door2Room2Room3Locked.unlock(keyToDoor1));
        assertTrue(door2Room2Room3Locked.isLocked());

        // then correct key.
        assertFalse(door2Room2Room3Locked.unlock(keyToDoor2));
        assertFalse(door2Room2Room3Locked.isLocked());
    }

    @Test
    public void testLock() {
        assertFalse(door1Room1Room2Unlocked.isLocked());
        assertTrue(door1Room1Room2Unlocked.lock(keyToDoor1));
        assertTrue(door1Room1Room2Unlocked.isLocked());
    }

    @Test(expected = IllegalStateException.class)
    public void testGetOtherRoom_locked() {
        // Try to get room3, but fail because of locked door.
        door2Room2Room3Locked.getOtherRoom(room2);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testGetOtherRoom_unattachedRoom() {
        // Try to get other room when door is not attached to given room.
        door2Room2Room3Locked.getOtherRoom(room1);
    }

    @Test
    public void testGetOtherRoom() {
        door2Room2Room3Locked.unlock(keyToDoor2);
        // Get opposite room from Room 2 through the [Room 2 <-> Room 3] door.
        final Room otherRoomFromRoom2 = door2Room2Room3Locked.getOtherRoom(room2);
        assertEquals(room3, otherRoomFromRoom2);

        final Room otherRoomFromRoom3 = door2Room2Room3Locked.getOtherRoom(otherRoomFromRoom2);
        assertEquals(room2, otherRoomFromRoom3);
    }

    @Test
    public void testEquals() {
        assertEquals(door1Room1Room2Unlocked, door1Duplicate);
        assertNotEquals(door1Room1Room2Unlocked, door2Room2Room3Locked);
    }

    @Test
    public void testHashCode() {
        assertEquals(door1Room1Room2Unlocked.hashCode(), door1Duplicate.hashCode());
        assertNotEquals(door1Room1Room2Unlocked.hashCode(), door2Room2Room3Locked.hashCode());
    }

    @Test
    public void testGetRoomNum() {
        assertEquals(room1, door1Room1Room2Unlocked.getRoom1());
        assertEquals(room2, door1Room1Room2Unlocked.getRoom2());
    }

    @Test
    public void testToString() {
        final String result = door2Room2Room3Locked.toString();
        assertTrue(result.contains(room2.getName()));
        assertTrue(result.contains(room3.getName()));
        assertTrue(result.contains("locked"));
    }
}
