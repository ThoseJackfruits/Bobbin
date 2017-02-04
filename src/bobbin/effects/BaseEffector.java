package bobbin.effects;

import com.sun.istack.internal.NotNull;

import sun.plugin.dom.exception.InvalidStateException;

import java.io.BufferedReader;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import bobbin.characters.GameCharacter;
import bobbin.characters.PlayerCharacter;
import bobbin.interaction.ExitToException;
import bobbin.items.BaseGameEntity;
import bobbin.items.GameEntity;

/**
 * All {@link BaseGameEntity}'s which apply some kind of {@link BaseEffect} should implement {@link
 * Effector} and delegate to a {@link BaseEffector}.
 */
public class BaseEffector extends BaseGameEntity implements Effector {

    private final List<BaseEffect<? extends GameEntity>> effects;

    public BaseEffector(List<BaseEffect<? extends GameEntity>> effects) {
        this.effects = new ArrayList<>(effects);
    }

    /**
     * Apply {@link this} object's {@link BaseEffect}s to the given {@link GameCharacter}.
     *
     * @param gameCharacter the {@link GameCharacter} to apply {@link BaseEffect}s to.
     */
    @Override
    public void apply(@NotNull GameCharacter gameCharacter) {
        Objects.requireNonNull(gameCharacter);
        if (!(hasEffects())) {
            throw new InvalidStateException(String.format("%s has no effects.", this.getName()));
        }

        while (!effects.isEmpty()) {
            effects.remove(0).accept(gameCharacter);
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
     * Add the given effect to the effector.
     *
     * @param effect to add
     * @return {@link this}
     */
    @Override
    public Effector addEffect(BaseEffect<? extends GameEntity> effect) {
        effects.add(effect);
        return this;
    }

    /**
     * Return a {@link List} of effects contained by this {@link Effector}.
     */
    @Override
    public List<BaseEffect<? extends GameEntity>> getEffects() {
        return new ArrayList<>(effects);
    }

    @Override
    public int respondToInteraction(PlayerCharacter actor, BaseGameEntity from, BufferedReader reader,
                                    PrintWriter writer) throws ExitToException {

        return GoTo.GRANDPARENT;
    }
}
