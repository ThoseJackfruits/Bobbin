package text_engine.interaction.actions;

import com.sun.istack.internal.NotNull;

import text_engine.characters.PlayerCharacter;
import text_engine.interaction.Interactive;
import text_engine.items.BaseGameEntity;

public class BaseAction extends BaseGameEntity implements Action {

    private Action action;

    /**
     * Constructs a new item {@link BaseGameEntity}.
     *
     * @param name        The name of the object
     * @param description The description of the object
     */
    public BaseAction(@NotNull String name, @NotNull String description,
                      Action action) {
        super(name, description);
        this.action = action;
    }

    public BaseAction(Action action) {
        this.action = action;
    }

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
    public Interactive apply(PlayerCharacter playerCharacter) {
        return action.apply(playerCharacter);
    }
}
