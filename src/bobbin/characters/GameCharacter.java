package bobbin.characters;

import java.util.Objects;

import bobbin.boundaries.Door;
import bobbin.boundaries.Room;
import bobbin.interaction.ExitToException;
import bobbin.items.BaseGameEntity;
import bobbin.items.Inventory;
import bobbin.items.Item;
import bobbin.items.Key;

/**
 * Represents an in-game character, including both the player character and NPCs.
 */
public class GameCharacter extends BaseGameEntity implements Cloneable {

    public class ExitToGameCharacterException extends ExitToException {

    }

    private final Inventory inventory;
    private Room location;

    /**
     * Constructs a {@link GameCharacter} in the given location, with the given inventory.
     *
     * @param items    The list of items in the {@link GameCharacter}'s inventory
     * @param location The room the {@link GameCharacter} is in
     */
    public GameCharacter(String name, String description, Room location, Item... items) {
        super(name, description);
        Objects.requireNonNull(items);
        Objects.requireNonNull(location);

        this.inventory = new Inventory(items);
        this.location = location;
    }

    /**
     * Constructs a {@link GameCharacter} with an empty inventory.
     *
     * @param location The room the {@link GameCharacter} is in
     */
    public GameCharacter(String name, String description, Room location) {
        this(name, description, location, new Item[0]);
    }

    /**
     * @return This {@link GameCharacter}'s inventory.
     */
    public Inventory getInventory() {
        return this.inventory;
    }

    /**
     * @return This {@link GameCharacter}'s inventory.
     */
    public Room getLocation() {
        return this.location;
    }

    /**
     * Attempt to change the {@link GameCharacter}'s location.
     *
     * @param destination The {@link Room} to move to
     */
    public void moveTo(Room destination) {
        if (this.location.canMoveTo(destination)) {
            this.location = destination;
        }
    }

    /**
     * Try to move through the given door.
     *
     * @param door the door to move through
     * @throws IllegalStateException    door is locked
     * @throws IllegalArgumentException given room is not connected to this door
     */
    public void moveThroughDoor(Door door) {
        Room room;
        try {
            room = door.getOtherRoom(this.getLocation());
        }
        catch (IllegalStateException exc) {  // door is locked
            if (getInventory().hasKeyThatMatches((key) -> !door.unlock((Key) key))) {
                room = door.getOtherRoom(this.getLocation());
            }
            else {
                throw exc;
            }
        }
        moveTo(room);
    }

    @Override
    public Object clone() throws CloneNotSupportedException {
        super.clone();
        return new GameCharacter(getName(), getDescription(), getLocation(),
                                 getInventory().toArray());
    }
}
