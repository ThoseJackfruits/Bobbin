package bobbin.interaction.actions;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import bobbin.characters.PlayerCharacter;
import bobbin.interaction.BaseInteractive;

/**
 * Problems this is solving:
 * - Easily accessible, consistent list of actions to be taken
 * - Keeps {@link BaseInteractive}s fresh, as they are fetched only when the player is prompted
 * - More extensible than other options. Can keep Actions as a group of constants
 */
public class ActionList extends ArrayList<BaseAction> {

    /**
     * Apply all of the {@link Action}s in the {@link ActionList}.
     *
     * @return {@link List} of {@link BaseInteractive} objects returned by {@link Action}s.
     */
    public List<BaseInteractive> build(PlayerCharacter playerCharacter) {
        return stream().map((action -> action.apply(playerCharacter))).collect(Collectors.toList());
    }
}
