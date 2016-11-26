package text_engine.items;

import java.util.Objects;

import text_engine.items.combinable.CombinableItem;

/**
 * Represents an item that the player can examine and pick up.
 */
public class Item {

    public final String name;
    public final String description;

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
    }

    /**
     * Standard {@link Item}s cannot be combined with anything.
     *
     * @param other The other item
     * @return {@code false}
     */
    public boolean compatible(CombinableItem other) {
        return false;
    }

    @Override
    public String toString() {
        return this.name.concat(": ").concat(this.description);
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
}
