package text_engine.items;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

/**
 * Represents an item that the player can examine and pick up.
 */
public class Item {

    private final String name;
    private final String description;
    private Map<String, Item> combinations;

    /**
     * Constructs a new item {@link Item}.
     *
     * @param name        The name of the object
     * @param description The description of the object
     */
    public Item(String name, String description) {
        Objects.requireNonNull(name);
        Objects.requireNonNull(description);

        this.name = name;
        this.description = description;
        this.combinations = new HashMap<>();
    }

    /**
     * Constructs a new item {@link Item}.
     *
     * @param name         The name of the object
     * @param description  The description of the object
     * @param combinations The possible combinations with other items
     */
    public Item(String name, String description, Map<String, Item> combinations) {
        Objects.requireNonNull(name);
        Objects.requireNonNull(description);
        Objects.requireNonNull(combinations);

        this.name = name;
        this.description = description;
        this.combinations = combinations;
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
        return combinations.containsKey(other.name);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Item item = (Item) o;
        return name.equals(item.name);
    }

    @Override
    public int hashCode() {
        return name.hashCode();
    }

    /**
     * Combines this item with the given one. EFFECTS: Changes the given inventory to remove this item
     * and the given item, replacing the two with the new result of the combination
     *
     * @param other     The item to be combined
     * @param inventory The player's inventory
     * @return The new combined items as one
     * @throws IllegalArgumentException if the inventory does not contain both this item and the given
     *                                  item
     * @throws NullPointerException     if either given Object is null
     */
    public Item combine(Item other, ArrayList<Item> inventory) {
        Objects.requireNonNull(other);
        Objects.requireNonNull(inventory);
        if (!(inventory.contains(other) && inventory.contains(this))) {
            throw new IllegalArgumentException(
                    "Both this item and the given item must be in the given inventory.");
        }

        if (compatible(other)) {
            inventory.remove(other);
            inventory.remove(this);
            inventory.add(combinations.get(other.name));
            return combinations.get(other.name);
        } else {
            return null;
        }
    }

}
