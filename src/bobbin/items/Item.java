package bobbin.items;

import java.io.BufferedReader;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.Stack;

import bobbin.characters.GameCharacter;
import bobbin.characters.PlayerCharacter;
import bobbin.constants.Actions;
import bobbin.effects.BaseEffect;
import bobbin.effects.BaseEffector;
import bobbin.interaction.ConsolePrompt;
import bobbin.interaction.ExitToException;
import bobbin.interaction.Printers;
import bobbin.interaction.actions.Action;
import bobbin.interaction.actions.ActionList;
import bobbin.items.combinations.Combination;
import bobbin.items.combinations.Combinations;

/**
 * A non-character {@link BaseGameEntity}. Has the ability to be combinable or consumable.
 */
public class Item extends BaseGameEntity {

    private final Combinations combinations;
    private final BaseEffector effector;

    public Item(String name, String description, List<BaseEffect<? extends GameEntity>> effects) {
        super(name, description);
        this.combinations = new Combinations();
        this.effector = new BaseEffector(effects);
    }

    public Item(String name, String description) {
        this(name, description, new Stack<>());
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
        toCombine[toCombine.length - 1] = this; // Add this item to the combination to check for
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
     * @return whether this {@link Item} can be consumed for an {@link BaseEffect}.
     */
    @Override
    public boolean isConsumable() {
        return effector.hasEffects();
    }

    /**
     * Adds the given list of {@link Item}s as a new {@link Combination} to the {@link Combinations}
     * of both {@link this} and all of the other given {@link Item}s.
     *
     * Returns {@link this} so it can be used in a pseudo-builder style.
     *
     * @param items  {@link Item}s to be added to the new {@link Combination}
     * @param result result of the {@link Combination}
     * @return {@link this}
     */
    public Item addCombination(Item result, Item... items) {
        Combination combination = new Combination(items);
        combination.add(this);

        for (Item otherItem : items) {
            if (!otherItem.equals(this)) {
                otherItem.addCombination(combination, result);
            }
        }

        return addCombination(combination, result);
    }

    /**
     * Adds the given combination to {@link this} {@link Item}'s {@link Combinations}.
     *
     * @param combination {@link Combination} to add
     * @param result      result of the {@link Combination}
     * @return {@link this}
     */
    private Item addCombination(Combination combination, Item result) {
        combinations.put(combination, result);
        return this;
    }

    public Item addEffect(BaseEffect<? extends GameEntity> effect) {
        effector.addEffect(effect);
        return this;
    }

    @Override
    public BaseEffector consume() {
        return effector;
    }

    /**
     * Combines this item with the given one. EFFECTS: Changes the given inventory to remove this item
     * and the given items, replacing them with the new result of the combination.
     *
     * @param owner      The {@link GameCharacter} whose inventory contains all the necessary items.
     * @param otherItems The items to be combined with this item.
     * @return The new combined items as one
     * @throws IllegalArgumentException if the inventory does not contain both this item and the given
     *                                  item, no items were provided, or no combination was found for
     *                                  the given items.
     * @throws NullPointerException     if either given Object is null
     */
    public GameEntity combine(GameCharacter owner, Item... otherItems)
            throws IllegalArgumentException {
        Objects.requireNonNull(owner);
        Objects.requireNonNull(otherItems);
        Inventory inventory = owner.getInventory();

        if (otherItems.length == 0) {
            throw new IllegalArgumentException("No items were provided.");
        }

        List<Item> allItems = new ArrayList<>(otherItems.length + 1);
        allItems.addAll(Arrays.asList(otherItems));
        allItems.add(this);

        Item result = combinations.get(allItems);

        if (result == null) {
            throw new IllegalArgumentException(String.format("%s cannot be combined.", allItems));
        }

        if (!(inventory.containsAll(allItems))) {
            throw new IllegalArgumentException(
                    "Both this item and all of the given items must be in the given inventory.");
        }

        inventory.removeAll(allItems);
        inventory.add(result);
        return result;
    }

    @Override
    public Object clone() throws CloneNotSupportedException {
        return super.clone();
    }

    @Override
    protected ActionList actions(GameCharacter actor, BaseGameEntity from) {
        ActionList actions = super.actions(actor, from);

        if (isConsumable()) {
            actions.add(Actions.CONSUME(this));
        }

        actions.add(Actions.PICK_UP);

        return actions;
    }

    @Override
    public int respondToInteraction(PlayerCharacter actor, BaseGameEntity from,
                                    BufferedReader reader, PrintWriter writer)
            throws ExitToException {
        Printers.println(writer);
        Printers.print(writer, this);
        Printers.println(writer);

        Action action = ConsolePrompt.getChoice(reader, writer, actions(actor, from), null);

        if (action.equals(Actions.PICK_UP)) {
            actor.getInventory().add(this);
            try {
                actor.getLocation().takeItem(this);
            } catch (IllegalArgumentException e) {
                Printers.printMessage(writer, "Error.gameplay.item.alreadyInInventory",
                                      this.getName());
            }
        }

        return action.apply(actor).interact(actor, this, reader, writer);
    }
}
