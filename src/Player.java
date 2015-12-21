import java.util.ArrayList;
import java.util.Objects;

/**
 * Created by Jack on 7/6/2015.
 *
 * Represents the player
 */
public class Player {

    private final ArrayList<Item> inventory;
    private Room location;

    /**
     * Constructs a Player object
     *
     * @param inventory The list of items in the Player's possession
     * @param location The room the player is in
     */
    Player(ArrayList<Item> inventory, Room location) {
        Objects.requireNonNull(inventory);
        Objects.requireNonNull(location);

        this.inventory = inventory;
        this.location = location;
    }

    /**
     * Constructs a Player object with an empty inventory
     *
     * @param location The room the player is in
     */
    Player(Room location) {
        Objects.requireNonNull(location);

        this.inventory = new ArrayList<Item>();
        this.location = location;
    }

    /**
     * @return This Player's inventory
     */
    public ArrayList<Item> getInventory() {
        return this.inventory;
    }

    /**
     * @return This Player's location
     */
    public Room getLocation() {
        return this.location;
    }

}
