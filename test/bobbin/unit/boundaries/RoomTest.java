package bobbin.unit.boundaries;

import bobbin.boundaries.Door;
import bobbin.boundaries.Room;
import bobbin.constants.Items;
import bobbin.items.Item;

import bobbin.unit.BaseUnitTest;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertThrows;

class RoomTest extends BaseUnitTest {

    private void fillRoomToLimit(Room room) {
        for (int i = 0; i < Room.CONTENT_LIMIT; i++) {
            room.addItem(new Item(String.format("item_%d", i), ""));
        }
    }

    @Test
    void testAddDuplicateDoor() {
        final Door door4 = new Door(false, room1, room2);
        assertEquals(1, room1.getDoors().length);
        assertEquals(2, room2.getDoors().length);

        final Door door5 = new Door(true, room1, room2);
        assertEquals(1, room1.getDoors().length);
        assertEquals(2, room2.getDoors().length);
    }

    @Test
    void testCanMoveTo() {
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
    void testGetRoomThroughDoor() {
        assertEquals(room2, room1.getRoomThroughDoor(door1Room1Room2Unlocked));
        assertEquals(room1, room2.getRoomThroughDoor(door1Room1Room2Unlocked));
    }

    @Test
    void testGetRoomThroughDoor_notInRoom() {
        assertThrows(
                IllegalArgumentException.class,
                () -> room1.getRoomThroughDoor(door2Room2Room3Locked));
    }

    @Test
    void testAddItem_duplicate() {
        assertFalse(room1.addItem(Items.BLUEBERRY));
    }

    @Test
    void testAddItem_underItemLimit() {
        final Room room = new Room("room", "A room full of items");
        fillRoomToLimit(room); // Should not throw an exception
    }

    @Test
    void testAddItem_overItemLimit() {
        final Room room = new Room("room", "A room full of items");
        fillRoomToLimit(room);

        final Item straw = new Item("straw", "The straw that broke the camel's back.");
        assertThrows(
                IllegalStateException.class,
                () -> room.addItem(straw));
    }
}
