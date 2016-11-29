package text_engine.items;

import java.util.Objects;

import text_engine.items.combinable.CombinableItem;

/**
 * Represents a generic in-game object, including characters, interactive items, etc.
 */
public class Item {

    private String name;
    private String description;

    /**
     * @return name of this {@link Item}
     */
    public String getName() {
        return name;
    }

    /**
     * Set the name of this {@link Item}
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * @return description of this {@link Item}
     */
    public String getDescription() {
        return description;
    }

    /**
     * Set the description of this {@link Item}
     */
    public void setDescription(String description) {
        this.description = description;
    }

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

    public Item() {
        this.name = "";
        this.description = "";
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
