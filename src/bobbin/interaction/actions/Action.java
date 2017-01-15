package bobbin.interaction.actions;

import java.util.function.Function;

import bobbin.characters.PlayerCharacter;
import bobbin.interaction.Interactive;
import bobbin.items.BaseGameEntity;
import bobbin.items.GameEntity;

/**
 * Provides a way to get the freshest source of a given {@link Interactive} off of a {@link
 * PlayerCharacter}.
 */
public interface Action extends Function<PlayerCharacter, BaseGameEntity>, GameEntity {

    /**
     * Fetch the {@link Interactive} object from this {@link PlayerCharacter}.
     *
     * Should be called when presenting an {@link Action} to the player, in order to get the relevant
     * {@link Interactive} item to interact with off of the {@link PlayerCharacter}.
     *
     * @param playerCharacter the {@link PlayerCharacter} taking the action.
     * @return the relevant {@link Interactive} item to interact with
     */
    @Override
    BaseGameEntity apply(PlayerCharacter playerCharacter);
}
