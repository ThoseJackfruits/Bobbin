package text_engine.characters;

import java.util.ArrayList;
import java.util.Objects;

import text_engine.boundaries.Door;
import text_engine.boundaries.Room;
import text_engine.items.Item;

/**
 * Created by Jack on 7/6/2015.
 *
 * Represents the player
 */
public class Player {

    private final ArrayList<Item> inventory;
    private Room location;

    /**
     * Constructs a text_engine.characters.Player object
     *
     * @param inventory The list of items in the text_engine.characters.Player's possession
     * @param location The room the player is in
     */
    Player(ArrayList<Item> inventory, Room location) {
        Objects.requireNonNull(inventory);
        Objects.requireNonNull(location);

        this.inventory = inventory;
        this.location = location;
    }

    /**
     * Constructs a text_engine.characters.Player object with an empty inventory
     *
     * @param location The room the player is in
     */
    Player(Room location) {
        Objects.requireNonNull(location);

        this.inventory = new ArrayList<Item>();
        this.location = location;
    }

    /**
     * @return This text_engine.characters.Player's inventory
     */
    public ArrayList<Item> getInventory() {
        return this.inventory;
    }

    /**
     * @return This text_engine.characters.Player's location
     */
    public Room getLocation() {
        return this.location;
    }

    /**
     * Change the player's location. Will only work if there is a {@link Door} to the next {@link Room}
     * and if that {@link Door} is not locked.
     *
     * @param destination The {@link Room} to move to
     */
    public void move(Room destination) {

        if (this.location.canMoveTo(destination)) this.location = destination;

    }

}
