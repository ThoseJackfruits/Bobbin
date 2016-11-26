package text_engine.unit.boundaries;

import org.junit.Test;

import text_engine.boundaries.Door;
import text_engine.boundaries.Room;

import static org.junit.Assert.assertEquals;

public class RoomTest {

    Room r1 = new Room("r1");
    Room r2 = new Room("r2");
    Room r3 = new Room("r3");
    Door d1 = new Door(true, r1, r3);
    Door d2 = new Door(true, r1, r2);
    Door d3 = new Door(true, r2, r3);

    @Test
    public void testAddDuplicateDoor() {
        Door d4 = new Door(true, r2, r3);
        assertEquals(2, r2.getExits().length);
        assertEquals(2, r3.getExits().length);
    }
}
