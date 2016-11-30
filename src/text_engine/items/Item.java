package text_engine.items;

import sun.plugin.dom.exception.InvalidStateException;

import java.util.ArrayList;
import java.util.Objects;
import java.util.Stack;

import text_engine.items.combinations.Combinations;

/**
 * A non-character {@link GameEntity}. Has the ability to be combinable or consumable.
 */
public class Item extends GameEntity {

    private final Combinations combinations;
    private final Stack<Object> effects;  // TODO: change this once Effects are added

    public Item(String name, String description) {
        this(name, description, new Combinations(), new Stack<>());
    }

    public Item(String name, String description, Combinations combinations, Stack<Object> effects) {
        super(name, description);
        this.combinations = combinations;
        this.effects = effects;
    }

    /**
     * Can one combine this item with the given one?
     *
     * @param other The other item
     * @return Whether this item can be combined with the given item
     */
    @Override
    public boolean compatible(Item other) {
        return combinations.keySet().stream().anyMatch(c -> c.contains(other));
    }

    /**
     * @return whether this {@link Item} can be combined with another {@link Item}.
     */
    public boolean isCombinable() {
        return !combinations.isEmpty();
    }

    /**
     * @return whether this {@link Item} can be consumed for an (todo Effect).
     */
    public boolean isConsumable() {
        return !effects.isEmpty();
    }

    public void consume() {
        if (!isConsumable()) {
            throw new InvalidStateException(String.format("%s is not consumable.", this.getName()));
        }
        while (!effects.isEmpty()) {
            effects.pop();//.apply() // TODO: change this once Effects are added
        }
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
    public GameEntity combine(Item other, ArrayList<GameEntity> inventory) {
        Objects.requireNonNull(other);
        Objects.requireNonNull(inventory);
        if (!(inventory.contains(other) && inventory.contains(this))) {
            throw new IllegalArgumentException(
                    "Both this item and the given item must be in the given inventory.");
        }

        if (compatible(other)) {
            inventory.remove(other);
            inventory.remove(this);
            GameEntity result = combinations.get(this, other);
            inventory.add(result);
            return result;
        }
        else {
            return null;
        }
    }
}
