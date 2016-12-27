package text_engine.unit.boundaries;

import org.junit.Test;

import text_engine.boundaries.Door;
import text_engine.boundaries.Room;
import text_engine.constants.Items;
import text_engine.items.Item;
import text_engine.unit.BaseUnitTest;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class RoomTest extends BaseUnitTest {

    private static void fillRoomToLimit(Room room) {
        for (int i = 0; i < Room.CONTENT_LIMIT; i++) {
            room.addItem(new Item(String.format("item_%d", i), ""));
        }
    }

    @Test
    public void addDuplicateDoor() {
        final Door door4 = new Door(false, room1, room2);
        assertEquals(1, room1.getDoors().length);
        assertEquals(2, room2.getDoors().length);

        final Door door5 = new Door(true, room1, room2);
        assertEquals(1, room1.getDoors().length);
        assertEquals(2, room2.getDoors().length);
    }

    @Test
    public void canMoveTo() {
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

    @Test
    public void getRoomThroughDoor() {
        assertEquals(room2, room1.getRoomThroughDoor(door1Room1Room2Unlocked));
        assertEquals(room1, room2.getRoomThroughDoor(door1Room1Room2Unlocked));
    }

    @Test(expected = IllegalArgumentException.class)
    public void getRoomThroughDoor_notInRoom() {
        room1.getRoomThroughDoor(door2Room2Room3Locked);
    }

    @Test
    public void addItem_duplicate() {
        assertFalse(room1.addItem(Items.BLUEBERRY));
    }

    @Test
    public void addItem_underItemLimit() {
        final Room room = new Room("room", "A room full of items");
        fillRoomToLimit(room); // Should not throw an exception
    }

    @Test(expected = IllegalStateException.class)
    public void addItem_overItemLimit() {
        final Room room = new Room("room", "A room full of items");
        fillRoomToLimit(room);

        final Item straw = new Item("straw", "The straw that broke the camel's back.");
        room.addItem(straw);
    }
}
