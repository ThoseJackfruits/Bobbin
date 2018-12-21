package bobbin.interaction.actions;

import bobbin.characters.PlayerCharacter;
import bobbin.interaction.Interactive;
import bobbin.items.BaseGameEntity;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Problems this is solving:
 * - Easily accessible, consistent list of actions to be taken
 * - Keeps {@link Interactive}s fresh, as they are fetched only when the player is prompted
 * - More extensible than other options. Can keep Actions as a group of constants
 */
public class ActionList extends ArrayList<BaseAction> {

    /**
     * Apply all of the {@link Action}s in the {@link ActionList}.
     *
     * @return {@link List} of {@link Interactive} objects returned by {@link Action}s.
     */
    public List<Interactive> build(PlayerCharacter playerCharacter) {
        return stream().map((action -> action.apply(playerCharacter))).collect(Collectors.toList());
    }

    /**
     * Adds actions for all of the given {@link BaseGameEntity}s, wrapping them in lambdas.
     *
     * @param gameEntities to be added
     * @param <T>          the actual type of the {@link BaseGameEntity} subclass
     * @return whether anything was added
     */
    public <T extends BaseGameEntity> boolean addAll(List<T> gameEntities) {
        ensureCapacity(size() + gameEntities.size());

        for (BaseGameEntity gameEntity : gameEntities) {
            add(new BaseAction(gameEntity.getName(), gameEntity.getDescription(),
                               playerCharacter -> gameEntity));
        }

        return gameEntities.size() != 0;
    }
}
