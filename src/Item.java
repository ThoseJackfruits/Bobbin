import java.util.ArrayList;
import java.util.HashMap;
import java.util.Objects;

/**
 * Created by Jack on 7/6/2015.
 *
 * Represents an item that the player can examine and pick up
 */
public class Item {

    private final String name;
    private final String description;
    private HashMap<String, Item> combos;

    /**
     * Constructs an Item object
     *
     * @param name The name of the object
     * @param description The description of the object
     * @param combos The possible combinations with other items
     */
    Item(String name, String description, HashMap<String, Item> combos) {
        Objects.requireNonNull(name);
        Objects.requireNonNull(description);
        Objects.requireNonNull(combos);

        this.name = name;
        this.description = description;
        this.combos = combos;
    }

    @Override
    public String toString() {
        return this.description;
    }

    /**
     * Can one combine this item with the given one?
     *
     * @param other The other item
     * @return Whether this item can be combined with the given item
     */
    public boolean compatible(Item other) {
        return combos.containsKey(other.name);
    }

    /**
     * Combines this item with the given one.
     * EFFECTS: Changes the given inventory to remove this item and the given item, replacing the two with
     * the new result of the combination
     *
     * @param other The item to be combined
     * @param inventory The player's inventory
     * @return The new combined items as one
     * @throws IllegalArgumentException if the inventory does not contain both this item and the given item
     * @throws NullPointerException if either given Object is null
     */
    public Item combine(Item other, ArrayList<Item> inventory) {
        Objects.requireNonNull(other);
        Objects.requireNonNull(inventory);
        if (!(inventory.contains(other) && inventory.contains(this))) {
            throw new IllegalArgumentException("Both this item and the given item must be in the given inventory.");
        }

        if (compatible(other)) {
            inventory.remove(other);
            inventory.remove(this);
            inventory.add(combos.get(other.name));
            return combos.get(other.name);
        }  else {
            return null;
        }
    }

}
