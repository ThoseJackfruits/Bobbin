package text_engine.effects;

import com.sun.istack.internal.NotNull;

import sun.plugin.dom.exception.InvalidStateException;

import java.util.List;
import java.util.Objects;
import java.util.Stack;
import java.util.Vector;

import text_engine.characters.GameCharacter;
import text_engine.items.BaseGameEntity;
import text_engine.items.GameEntity;

/**
 * All {@link BaseGameEntity}'s which apply some kind of {@link Effect} should implement {@link
 * Effector} and delegate to a {@link BaseEffector}.
 */
public class BaseEffector extends BaseGameEntity implements Effector {

    private final Stack<Effect<? extends GameEntity>> effects;

    public BaseEffector(Stack<Effect<? extends GameEntity>> effects) {
        this.effects = effects;
    }

    public BaseEffector(List<Effect<? extends GameEntity>> effects) {
        this.effects = new Stack<>();
        this.effects.addAll(effects);
    }

    /**
     * Apply {@link this} object's {@link Effect}s to the given {@link GameCharacter}.
     *
     * @param gameCharacter the {@link GameCharacter} to apply {@link Effect}s to.
     */
    @Override
    public void apply(@NotNull GameCharacter gameCharacter) {
        Objects.requireNonNull(gameCharacter);
        if (!(hasEffects())) {
            throw new InvalidStateException(String.format("%s has no effects.", this.getName()));
        }

        while (!effects.isEmpty()) {
            effects.pop().accept(gameCharacter);
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean hasEffects() {
        return !effects.isEmpty();
    }

    /**
     * Return a {@link Vector} of effects contained by this {@link Effector}.
     */
    @Override
    public Vector<Effect<? extends GameEntity>> getEffects() {
        @SuppressWarnings("unchecked")
        Vector<Effect<? extends GameEntity>>
                result = (Vector<Effect<? extends GameEntity>>) effects.clone();
        return result;
    }
}
