package text_engine.items;

import com.sun.istack.internal.NotNull;

import java.io.Serializable;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.Stack;

import text_engine.characters.GameCharacter;
import text_engine.effects.BaseEffector;
import text_engine.effects.Effect;
import text_engine.effects.Effector;
import text_engine.items.combinations.Combinations;

/**
 * A non-character {@link BaseGameEntity}. Has the ability to be combinable or consumable.
 */
public class Item extends BaseGameEntity implements Serializable {

    private final Combinations combinations;
    private final Effector effector;

    public Item(String name, String description, Combinations combinations,
                List<Effect<? extends GameEntity>> effects) {
        super(name, description);
        this.combinations = combinations;
        this.effector = new BaseEffector(effects);
    }

    public Item(String name, String description) {
        this(name, description, new Combinations(), new Stack<>());
    }

    /**
     * Can one combine this item with the given one?
     *
     * @param otherItems The other items to combine with.
     * @return Whether this item can be combined with the given items
     */
    @Override
    public boolean isCompatible(Item... otherItems) {
        Item[] toCombine = Arrays.copyOf(otherItems, otherItems.length + 1);
        return combinations.get(toCombine) != null;
    }

    /**
     * @return whether this {@link Item} can be combined.
     */
    @Override
    public boolean isCombinable() {
        return !combinations.isEmpty();
    }

    /**
     * @return whether this {@link Item} can be consumed for an {@link Effect}.
     */
    @Override
    public boolean isConsumable() {
        return effector.hasEffects();
    }

    @Override
    public void consume(@NotNull GameCharacter gameCharacter) {
        effector.apply(gameCharacter);

        gameCharacter.getInventory().remove(this);
    }

    /**
     * Combines this item with the given one. EFFECTS: Changes the given inventory to remove this item
     * and the given items, replacing them with the new result of the combination.
     *
     * @param otherItems The items to be combined with this item.
     * @param inventory  The player's inventory
     * @return The new combined items as one
     * @throws IllegalArgumentException if the inventory does not contain both this item and the given
     *                                  item, no items were provided, or no combination was found for
     *                                  the given items.
     * @throws NullPointerException     if either given Object is null
     */
    public GameEntity combine(List<GameEntity> inventory, Item... otherItems)
            throws IllegalArgumentException {
        Objects.requireNonNull(otherItems);
        Objects.requireNonNull(inventory);

        if (otherItems.length == 0) {
            throw new IllegalArgumentException("No items were provided.");
        }

        List<Item> allItems = Arrays.asList(otherItems);
        allItems.add(this);

        if (!(inventory.containsAll(allItems))) {
            throw new IllegalArgumentException(
                    "Both this item and all of the given items must be in the given inventory.");
        }

        GameEntity result = combinations.get(allItems);

        if (result == null) {
            throw new IllegalArgumentException(String.format("%s cannot be combined.", allItems));
        }

        inventory.removeAll(allItems);
        inventory.add(result);
        return result;
    }

    @Override
    public Object clone() throws CloneNotSupportedException {
        super.clone();
        return new Item(getName(), getDescription(), combinations, effector.getEffects());
    }
}
