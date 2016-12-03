package text_engine.unit.boundaries;

import org.junit.Test;

import text_engine.boundaries.Door;
import text_engine.boundaries.Room;
import text_engine.unit.BaseTest;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertTrue;

/**
 * Unit tests for {@link Door}s.
 */
public class DoorTest extends BaseTest {
    private final Door door1Duplicate = new Door(false, room1, room2);

    @Test
    public void testFits() {
        assertTrue(door1Room1Room2Unlocked.fits(keyDoor1));
        assertFalse(door2Room2Room3Locked.fits(keyDoor1));

        assertTrue(door2Room2Room3Locked.fits(keyDoor2));
        assertFalse(door1Room1Room2Unlocked.fits(keyDoor2));
    }

    @Test
    public void testUnlock() {
        // Try to unlock already unlocked door with multiple keys. Nothing should change.
        assertFalse(door1Room1Room2Unlocked.isLocked());
        assertFalse(door1Room1Room2Unlocked.unlock(keyDoor1));
        assertFalse(door1Room1Room2Unlocked.unlock(keyDoor2));
        assertFalse(door1Room1Room2Unlocked.isLocked());

        // Try to unlock locked door with bad key...
        assertTrue(door2Room2Room3Locked.isLocked());
        assertTrue(door2Room2Room3Locked.unlock(keyDoor1));
        assertTrue(door2Room2Room3Locked.isLocked());

        // then correct key.
        assertFalse(door2Room2Room3Locked.unlock(keyDoor2));
        assertFalse(door2Room2Room3Locked.isLocked());
    }

    @Test(expected = IllegalStateException.class)
    public void testGetOtherRoomLocked() {
        // Try to get room3, but fail because of locked door.
        door2Room2Room3Locked.getOtherRoom(room2);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testGetOtherRoomUnattachedRoom() {
        // Try to get other room when door is not attached to given room.
        door2Room2Room3Locked.getOtherRoom(room1);
    }

    @Test
    public void testGetOtherRoom() {
        door2Room2Room3Locked.unlock(keyDoor2);
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
}
