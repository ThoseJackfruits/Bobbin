package text_engine.unit.characters;

import org.junit.Test;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;

import text_engine.boundaries.Door;
import text_engine.boundaries.Room;
import text_engine.characters.GameCharacter;
import text_engine.items.Item;

import static org.junit.Assert.assertEquals;

public class GameCharacterTest {
    private final Room room1 = new Room("Room 1");
    private final Room room2 = new Room("Room 2");
    private final Door door1 = new Door(false, room1, room2);
    private final GameCharacter character1 = new GameCharacter("Slartibartfast", "Award-winning Fjord Designer", room1);

    @Test
    public void testMoveTo() {
        character1.moveTo(room2);
        assertEquals(room2, character1.getLocation());
        character1.moveTo(room1);
        assertEquals(room1, character1.getLocation());
    }

    @Test
    public void testMoveThroughDoor() {
        character1.moveThroughDoor(door1);
        assertEquals(room2, character1.getLocation());
        character1.moveThroughDoor(door1);
        assertEquals(room1, character1.getLocation());
    }

    @Test
    public void testInventory() {
        final Item blueberry = new Item("Blueberry", "A berry.");
        Item sausage = new Item("Sausage", "A salty tube of preserved meat.");
        GameCharacter character2 = new GameCharacter("Other Character", "", room1, blueberry, sausage);
        List<Item> inventory = character2.getInventory();
        assertEquals(new HashSet<>(Arrays.asList(blueberry, sausage)), new HashSet<>(inventory));
    }
}
