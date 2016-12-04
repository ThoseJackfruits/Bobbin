package text_engine.effects;

import com.sun.istack.internal.NotNull;

import java.util.Collection;
import java.util.Vector;

import text_engine.characters.GameCharacter;
import text_engine.items.GameEntity;

public interface Effector {

    /**
     * Apply {@link this} {@link Effector}'s {@link Effect}s to the given {@link GameCharacter}.
     *
     * @param gameCharacter the {@link GameCharacter} to apply {@link Effect}s to.
     * @throws IllegalStateException no effects to apply.
     */
    void apply(@NotNull GameCharacter gameCharacter) throws IllegalStateException;

    /**
     * Also implies whether {@link #apply(GameCharacter)} will perform any actions or throw an
     * {@link IllegalStateException}.
     *
     * @return whether {@link this} has any effects
     */
    boolean hasEffects();

    /**
     * Return the {@link Collection} of effects contained by this {@link Effector}.
     */
    Vector<Effect<? extends GameEntity>> getEffects();
}
