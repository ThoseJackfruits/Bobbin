package bobbin.items;

import bobbin.characters.PlayerCharacter;
import bobbin.effects.BaseEffector;
import bobbin.interaction.ConsolePrompt;
import bobbin.interaction.ExitToException;
import bobbin.interaction.Interactive;
import bobbin.interaction.Printers;

import javax.validation.constraints.NotNull;
import java.io.BufferedReader;
import java.io.PrintWriter;
import java.util.Collections;
import java.util.Objects;

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
    public BaseEffector consume() {
        return new BaseEffector(Collections.emptyList());
    }

    @Override
    public String toString() {
        return this.getDescription().isEmpty()
                ? this.getName()
                : this.getName() + ": " + this.getDescription();
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

    @Override
    public int respondToInteraction(
            PlayerCharacter actor, BaseGameEntity from,
            BufferedReader reader, PrintWriter writer)
            throws ExitToException {
        Printers.println(writer);
        Printers.print(writer, this);
        Printers.println(writer);

        return ConsolePrompt
                .getChoice(reader, writer, actions(actor, from), null)
                .apply(actor)
                .interact(actor, this, reader, writer);
    }
}
