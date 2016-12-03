package text_engine.unit.boundaries;

import org.junit.Test;

import text_engine.boundaries.Door;
import text_engine.unit.BaseTest;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class RoomTest extends BaseTest {

    @Test
    public void testAddDuplicateDoor() {
        Door door4 = new Door(false, room1, room2);
        assertEquals(1, room1.getDoors().length);
        assertEquals(2, room2.getDoors().length);

        Door door5 = new Door(true, room1, room2);
        assertEquals(1, room1.getDoors().length);
        assertEquals(2, room2.getDoors().length);
    }

    @Test
    public void testCanMoveTo() {
        // Unlocked door
        assertTrue(room1.canMoveTo(room2));
        assertTrue(room2.canMoveTo(room1));

        // No door
        assertFalse(room1.canMoveTo(room3));
        assertFalse(room3.canMoveTo(room1));

        // Locked door
        assertFalse(room2.canMoveTo(room3));
        assertFalse(room3.canMoveTo(room2));
    }
}
