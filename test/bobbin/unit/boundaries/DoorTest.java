package bobbin.unit.boundaries;

import bobbin.boundaries.Door;
import bobbin.boundaries.Room;
import bobbin.unit.BaseUnitTest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * Unit tests for {@link Door}s.
 */
class DoorTest extends BaseUnitTest {
    private final Door door1Duplicate = new Door(false, room1, room2);

    @Test
    void testFits() {
        assertTrue(door1Room1Room2Unlocked.fits(keyToDoor1));
        assertFalse(door2Room2Room3Locked.fits(keyToDoor1));

        assertTrue(door2Room2Room3Locked.fits(keyToDoor2));
        assertFalse(door1Room1Room2Unlocked.fits(keyToDoor2));
    }

    @Test
    void testUnlock() {
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
    void testLock() {
        assertFalse(door1Room1Room2Unlocked.isLocked());
        assertTrue(door1Room1Room2Unlocked.lock(keyToDoor1));
        assertTrue(door1Room1Room2Unlocked.isLocked());
    }

    @Test
    @DisplayName("Try to get room3, but fail because of locked door")
    void testGetOtherRoom_locked() {
        assertThrows(
                IllegalStateException.class,
                () -> door2Room2Room3Locked.getOtherRoom(room2));
    }

    @Test
    @DisplayName("Try to get other room when door is not attached to given room")
    void testGetOtherRoom_unattachedRoom() {
        assertThrows(
                IllegalArgumentException.class,
                () -> door2Room2Room3Locked.getOtherRoom(room1));
    }

    @Test
    void testGetOtherRoom() {
        door2Room2Room3Locked.unlock(keyToDoor2);
        // Get opposite room from Room 2 through the [Room 2 <-> Room 3] door.
        final Room otherRoomFromRoom2 = door2Room2Room3Locked.getOtherRoom(room2);
        assertEquals(room3, otherRoomFromRoom2);

        final Room otherRoomFromRoom3 = door2Room2Room3Locked.getOtherRoom(otherRoomFromRoom2);
        assertEquals(room2, otherRoomFromRoom3);
    }

    @Test
    void testEquals() {
        assertEquals(door1Room1Room2Unlocked, door1Duplicate);
        assertNotEquals(door1Room1Room2Unlocked, door2Room2Room3Locked);
    }

    @Test
    void testHashCode() {
        assertEquals(door1Room1Room2Unlocked.hashCode(), door1Duplicate.hashCode());
        assertNotEquals(door1Room1Room2Unlocked.hashCode(), door2Room2Room3Locked.hashCode());
    }

    @Test
    void testGetRoomNum() {
        assertEquals(room1, door1Room1Room2Unlocked.getRoom1());
        assertEquals(room2, door1Room1Room2Unlocked.getRoom2());
    }

    @Test
    void testToString() {
        final String result = door2Room2Room3Locked.toString();
        assertTrue(result.contains(room2.getName()));
        assertTrue(result.contains(room3.getName()));
        assertTrue(result.contains("locked"));
    }
}
