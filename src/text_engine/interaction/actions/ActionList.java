package text_engine.interaction.actions;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import text_engine.characters.PlayerCharacter;
import text_engine.interaction.Interactive;

/**
 * Problems this is solving:
 * - Easily accessible, consistent list of actions to be taken
 * - Keeps {@link Interactive}s fresh, as they are fetched only when the player is prompted
 * - More extensible than other options. Can keep Actions as a group of constants
 */
public class ActionList extends ArrayList<Action> {

    /**
     * Apply all of the {@link Action}s in the {@link ActionList}.
     *
     * @return {@link List} of {@link Interactive} objects returned by {@link Action}s.
     */
    public List<Interactive> build(PlayerCharacter playerCharacter) {
        return stream().map((action -> action.apply(playerCharacter))).collect(Collectors.toList());
    }
}
