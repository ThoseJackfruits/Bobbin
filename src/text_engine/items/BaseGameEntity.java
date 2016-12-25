package text_engine.items;

import com.sun.istack.internal.NotNull;

import sun.reflect.generics.reflectiveObjects.NotImplementedException;

import java.io.BufferedReader;
import java.io.PrintWriter;
import java.util.Objects;

import text_engine.characters.GameCharacter;
import text_engine.interaction.ExitToException;
import text_engine.interaction.Interactive;
import text_engine.interaction.Printers;

/**
 * Represents a generic in-game object, including characters, interactive items, etc.
 */
public class BaseGameEntity extends Interactive implements GameEntity {

    private String name;
    private String description;

    @Override
    public String getName() {
        return name;
    }

    @Override
    public void setName(@NotNull String name) {
        this.name = Objects.requireNonNull(name);
    }

    @Override
    public String getDescription() {
        return description;
    }

    @Override
    public void setDescription(@NotNull String description) {
        this.description = Objects.requireNonNull(description);
    }

    /**
     * Constructs a new item {@link BaseGameEntity}.
     *
     * @param name        The name of the object
     * @param description The description of the object
     */
    public BaseGameEntity(@NotNull String name, @NotNull String description) {
        Objects.requireNonNull(name);
        Objects.requireNonNull(description);

        setName(name);
        setDescription(description);
    }

    /**
     * Initialise a new {@link BaseGameEntity} with no name or description.
     */
    public BaseGameEntity() {
        this.name = "";
        this.description = "";
    }

    @Override
    public boolean isCompatible(Item... otherItems) {
        return false;
    }

    @Override
    public boolean isCombinable() {
        return false;
    }

    @Override
    public boolean isConsumable() {
        return false;
    }

    @Override
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
        BaseGameEntity gameEntity = (BaseGameEntity) o;
        return getName().equals(gameEntity.getName());
    }

    @Override
    public int hashCode() {
        return name.hashCode();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected int respondToInteraction(
            GameEntity from, BufferedReader reader, PrintWriter writer, String prompt)
            throws ExitToException {
        Printers.print(writer, this);
        return Interactive.PARENT;
    }
}
