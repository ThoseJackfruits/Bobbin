package text_engine.characters;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

import text_engine.boundaries.Door;
import text_engine.boundaries.Room;
import text_engine.items.Item;

/**
 * Represents an in-game character, including both the player character and NPCs.
 */
public class GameCharacter extends Item implements Serializable {

    protected final List<Item> inventory;
    private Room location;

    public GameCharacter() {
        this.inventory = new ArrayList<>();
    }

    /**
     * Constructs a {@link GameCharacter} in the given location, with the given inventory.
     *
     * @param inventory The list of items in the {@link GameCharacter}'s inventory
     * @param location  The room the {@link GameCharacter} is in
     */
    public GameCharacter(String name, String description, Room location, Item... inventory) {
        super(name, description);
        Objects.requireNonNull(inventory);
        Objects.requireNonNull(location);

        this.inventory = Arrays.asList(inventory);
        this.location = location;
    }

    /**
     * Constructs a {@link GameCharacter} with an empty inventory.
     *
     * @param location The room the {@link GameCharacter} is in
     */
    public GameCharacter(String name, String description, Room location) {
        super(name, description);
        Objects.requireNonNull(location);

        this.inventory = new ArrayList<>();
        this.location = location;
    }

    /**
     * @return This {@link GameCharacter}'s inventory.
     */
    public List<Item> getInventory() {
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
        Room room = door.getOtherRoom(this.getLocation());
        moveTo(room);
    }
}
