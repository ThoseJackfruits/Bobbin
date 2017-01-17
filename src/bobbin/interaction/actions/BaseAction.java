package bobbin.interaction.actions;

import bobbin.characters.PlayerCharacter;
import bobbin.interaction.Interactive;
import bobbin.items.BaseGameEntity;
import bobbin.items.GameEntity;
import com.sun.istack.internal.NotNull;

import java.util.function.Function;

public class BaseAction extends BaseGameEntity implements Action {

    private Function<PlayerCharacter, BaseGameEntity> action;

    /**
     * Constructs a new item {@link BaseGameEntity}.
     *
     * @param name        The name of the object
     * @param description The description of the object
     * @param action      to apply to the {@link PlayerCharacter}
     */
    public BaseAction(@NotNull String name, @NotNull String description,
                      Function<PlayerCharacter, BaseGameEntity> action) {
        super(name, description);
        this.action = action;
    }

    /**
     * Constructs a new {@link BaseAction} with no name, description, or report.
     *
     * @param action to apply to the {@link PlayerCharacter}
     */
    public BaseAction(Action action) {
        this("", "", action);
    }

    /**
     * Alternative to {@link #getName()} which falls back on
     * {@link #apply(PlayerCharacter)#getName(PlayerCharacter)}
     * if {@link this} does not have an explicit name.
     *
     * @param playerCharacter the {@link PlayerCharacter} taking the action
     * @return description of this {@link GameEntity}
     */
    public String getName(PlayerCharacter playerCharacter) {
        String name = getName();
        if (name.isEmpty()) {
            name = apply(playerCharacter).getName();
        }
        return name;
    }

    /**
     * Alternative to {@link #getDescription()} which falls back on the {@link BaseGameEntity} that
     * {@link #apply(PlayerCharacter)} gets off of the {@link PlayerCharacter}
     *
     * @param playerCharacter the {@link PlayerCharacter} taking the action
     * @return description of this {@link GameEntity}
     */
    public String getDescription(PlayerCharacter playerCharacter) {
        String description = super.getDescription();
        if (description.isEmpty()) {
            description = apply(playerCharacter).getDescription();
        }
        return description;
    }

    /**
     * Fetch the {@link Interactive} object from this {@link PlayerCharacter}.
     *
     * Should be called when presenting an {@link Action} to the player, in order to get the relevant
     * {@link Interactive} item to interact with off of the {@link PlayerCharacter}.
     *
     * @param playerCharacter the {@link PlayerCharacter} taking the action
     * @return the relevant {@link Interactive} item to interact with
     */
    @Override
    public BaseGameEntity apply(PlayerCharacter playerCharacter) {
        return action.apply(playerCharacter);
    }

}
