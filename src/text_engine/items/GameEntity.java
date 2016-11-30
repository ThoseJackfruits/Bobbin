package text_engine.items;

import java.util.Objects;

/**
 * Represents a generic in-game object, including characters, interactive items, etc.
 */
public class GameEntity {

    private String name;
    private String description;

    /**
     * @return name of this {@link GameEntity}
     */
    public String getName() {
        return name;
    }

    /**
     * Set the name of this {@link GameEntity}
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * @return description of this {@link GameEntity}
     */
    public String getDescription() {
        return description;
    }

    /**
     * Set the description of this {@link GameEntity}
     */
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * Constructs a new item {@link GameEntity}.
     *
     * @param name        The name of the object
     * @param description The description of the object
     */
    public GameEntity(String name, String description) {
        Objects.requireNonNull(name);
        Objects.requireNonNull(description);

        this.name = name;
        this.description = description;
    }

    public GameEntity() {
        this.name = "";
        this.description = "";
    }

    /**
     * Standard {@link GameEntity}s cannot be combined with anything.
     *
     * @param other The other item
     * @return {@code false}
     */
    public boolean compatible(Item other) {
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
        GameEntity gameEntity = (GameEntity) o;
        return name.equals(gameEntity.name);
    }

    @Override
    public int hashCode() {
        return name.hashCode();
    }
}
