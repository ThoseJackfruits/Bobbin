package bobbin.unit;

import bobbin.boundaries.Door;
import bobbin.boundaries.Room;
import bobbin.characters.GameCharacter;
import bobbin.characters.PlayerCharacter;
import bobbin.constants.Items;
import bobbin.items.Key;

/**
 * Base class for all tests. Gives the basic environment that most tests will require.
 */
public class BaseUnitTest {

    protected final Room room1 = new Room("Room 1", "Starting room, attached to Room 2.",
                                          Items.getCopiesOf(Items.BLUEBERRY, Items.FLOUR));
    protected final Room room2 = new Room("Room 2", "Attached to Room 1.");
    protected final Room room3 = new Room("Room 3", "Attached to Room 2.");

    protected final Door door1Room1Room2Unlocked = new Door(false, room1, room2);
    protected final Door door2Room2Room3Locked = new Door(true, room2, room3);

    protected final Key keyToDoor1 = door1Room1Room2Unlocked.makeKey("D1 Key", "Door 1 Key");
    protected final Key keyToDoor2 = door2Room2Room3Locked.makeKey("D2 Key", "Door 2 Key");

    protected final PlayerCharacter playerCharacter
            = new PlayerCharacter("Player Character",
                                  "The main Player Character, initially in Room 1.",
                                  room1,
                                  Items.getCopiesOf(Items.BLUEBERRY, Items.FLOUR,
                                                    Items.WATER, Items.BED));

    protected final GameCharacter gameCharacter
            = new GameCharacter("Generic Game Character",
                                "A generic GameCharacter, initially in Room 2.",
                                room2,
                                Items.getCopyOf(Items.WATER));

}
