package text_engine.unit.boundaries;

import org.junit.Test;

import text_engine.boundaries.Door;
import text_engine.boundaries.Room;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class RoomTest {

    Room r1 = new Room("r1");
    Room r2 = new Room("r2");
    Room r3 = new Room("r3");
    Room r4 = new Room("r4");
    Room r5 = new Room("r5");
    Door d1 = new Door(true, r1, r3);
    Door d2 = new Door(false, r1, r2);
    Door d3 = new Door(true, r2, r3);

    @Test
    public void testAddDuplicateDoor() {
        Door d4 = new Door(true, r2, r3);
        assertEquals(2, r2.getExits().length);
        assertEquals(2, r3.getExits().length);
    }

    @Test
    public void testCanMoveTo() {
        Door d4 = new Door(false, r3, r4);
        assertTrue(r1.canMoveTo(r2));
        assertTrue(r2.canMoveTo(r1));
        assertFalse(r1.canMoveTo(r3));
        assertFalse(r3.canMoveTo(r1));
        assertFalse(r1.canMoveTo(r4));
        assertFalse(r4.canMoveTo(r1));
        assertFalse(r5.canMoveTo(r2));
        assertFalse(r2.canMoveTo(r5));
    }
}
