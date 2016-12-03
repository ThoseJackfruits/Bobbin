package text_engine.items;

import com.sun.istack.internal.NotNull;

import sun.reflect.generics.reflectiveObjects.NotImplementedException;

import java.io.Serializable;
import java.util.Objects;

import text_engine.characters.GameCharacter;

/**
 * Represents a generic in-game object, including characters, interactive items, etc.
 */
public class GameEntity implements Serializable {

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
    public void setName(@NotNull String name) {
        this.name = Objects.requireNonNull(name);
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
    public void setDescription(@NotNull String description) {
        this.description = Objects.requireNonNull(description);
    }

    /**
     * Constructs a new item {@link GameEntity}.
     *
     * @param name        The name of the object
     * @param description The description of the object
     */
    public GameEntity(@NotNull String name, @NotNull String description) {
        Objects.requireNonNull(name);
        Objects.requireNonNull(description);

        setName(name);
        setDescription(description);
    }

    /**
     * Initialise a new {@link GameEntity} with no name or description.
     */
    public GameEntity() {
        this.name = "";
        this.description = "";
    }

    /**
     * Standard {@link GameEntity}s cannot be combined with anything.
     *
     * @param otherItems The other items to combine with.
     * @return {@code false}
     */
    public boolean isCompatible(Item... otherItems) {
        return false;
    }

    /**
     * Standard {@link GameEntity}s cannot be combined with anything.
     *
     * @return {@code false}
     */
    public boolean isCombinable() {
        return false;
    }

    /**
     * Standard {@link GameEntity}s cannot be consumed.
     *
     * @return {@code false}
     */
    public boolean isConsumable() {
        return false;
    }

    public void consume(GameCharacter gameCharacter) {
        throw new NotImplementedException();
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
