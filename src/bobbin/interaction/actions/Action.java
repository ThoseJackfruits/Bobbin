package bobbin.interaction.actions;

import bobbin.characters.PlayerCharacter;
import bobbin.interaction.ExitToException;
import bobbin.interaction.Interactive;
import bobbin.items.BaseGameEntity;
import bobbin.items.GameEntity;

import java.io.BufferedReader;
import java.io.PrintWriter;
import java.util.function.Function;

/**
 * Provides a way to get the freshest source of a given {@link Interactive} off of a {@link
 * PlayerCharacter}. It is meant to modify the runtime tree of the program, but not modify any
 * properties on {@link GameEntity}s. For modifying properties, use {@link bobbin.effects.Effect}
 */
public interface Action extends Function<PlayerCharacter, BaseGameEntity> {

    /**
     * Fetch the {@link Interactive} object from this {@link PlayerCharacter}.
     * <p>
     * Should be called when presenting an {@link Action} to the player, in order to get the relevant
     * {@link Interactive} item to interact with off of the {@link PlayerCharacter}.
     *
     * @param playerCharacter the {@link PlayerCharacter} taking the action.
     * @return the relevant {@link Interactive} item to interact with
     */
    @Override
    BaseGameEntity apply(PlayerCharacter playerCharacter);

    Action NULL = playerCharacter -> new BaseGameEntity("", "") {
        @Override
        public int respondToInteraction(
                PlayerCharacter actor, BaseGameEntity from,
                BufferedReader reader,
                PrintWriter writer) throws ExitToException {
            return 1;
        }
    };
}
