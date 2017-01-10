package text_engine.effects;

import com.sun.istack.internal.NotNull;

import java.util.Collection;
import java.util.List;

import text_engine.characters.GameCharacter;
import text_engine.items.GameEntity;

public interface Effector extends GameEntity {

    /**
     * Apply {@link this} {@link Effector}'s {@link BaseEffect}s to the given {@link GameCharacter}.
     *
     * @param gameCharacter the {@link GameCharacter} to apply {@link BaseEffect}s to.
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
     * Add the given effect to the effector.
     *
     * @param effect to add
     * @return {@link this}
     */
    Effector addEffect(BaseEffect<? extends GameEntity> effect);

    /**
     * Return the {@link Collection} of effects contained by this {@link Effector}.
     */
    List<BaseEffect<? extends GameEntity>> getEffects();
}
