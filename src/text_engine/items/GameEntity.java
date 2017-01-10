package text_engine.items;

import com.sun.istack.internal.NotNull;

import java.io.Serializable;

import text_engine.effects.BaseEffector;
import text_engine.effects.Effector;

public interface GameEntity extends Serializable, Cloneable {

    /**
     * @return name of this {@link GameEntity}
     */
    String getName();

    /**
     * Set the name of this {@link GameEntity}
     */
    void setName(@NotNull String name);

    /**
     * @return description of this {@link GameEntity}
     */
    String getDescription();

    /**
     * Set the description of this {@link GameEntity}
     */
    void setDescription(@NotNull String description);

    /**
     * Standard {@link GameEntity}s cannot be combined with anything.
     *
     * @param otherItems The other items to combine with.
     * @return {@code false}
     */
    boolean isCompatible(Item... otherItems);

    /**
     * Standard {@link GameEntity}s cannot be combined with anything.
     *
     * @return {@code false}
     */
    boolean isCombinable();

    /**
     * Standard {@link GameEntity}s cannot be consumed.
     *
     * @return {@code false}
     */
    boolean isConsumable();

    /**
     * Get the {@link Effector} from consuming {@link this}.
     *
     * @return {@link Effector} from consuming {@link this}.
     */
    BaseEffector consume();
}
