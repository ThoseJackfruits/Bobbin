package text_engine.items.combinable;

import java.util.ArrayList;
import java.util.Objects;

import text_engine.items.Item;

/**
 * {@inheritDoc}
 *
 * Can be combined with other items to form new items.
 */
public class CombinableItem extends Item {

    private Combinations combinations;

    CombinableItem(String name, String description,
                   Combinations combinations) {
        super(name, description);
        this.combinations = combinations;
    }

    /**
     * Can one combine this item with the given one?
     *
     * @param other The other item
     * @return Whether this item can be combined with the given item
     */
    @Override
    public boolean compatible(CombinableItem other) {
        return combinations.keySet().stream().anyMatch(c -> c.contains(other));
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
    public Item combine(CombinableItem other, ArrayList<Item> inventory) {
        Objects.requireNonNull(other);
        Objects.requireNonNull(inventory);
        if (!(inventory.contains(other) && inventory.contains(this))) {
            throw new IllegalArgumentException(
                    "Both this item and the given item must be in the given inventory.");
        }

        if (compatible(other)) {
            inventory.remove(other);
            inventory.remove(this);
            Item result = combinations.get(this, other);
            inventory.add(result);
            return result;
        }
        else {
            return null;
        }
    }
}
