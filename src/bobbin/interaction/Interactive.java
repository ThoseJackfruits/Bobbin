package bobbin.interaction;

import java.io.BufferedReader;
import java.io.PrintWriter;

import bobbin.characters.PlayerCharacter;
import bobbin.items.BaseGameEntity;
import bobbin.items.GameEntity;

public interface Interactive {

    /**
     * Respond to interaction from another {@link GameEntity}.
     *
     * @param from   source of the interaction.
     * @param reader to read response from
     * @param writer to print prompt to
     * @return the number of levels to go up from the current stage.
     */
    int respondToInteraction(PlayerCharacter actor, BaseGameEntity from,
                             BufferedReader reader, PrintWriter writer) throws ExitToException;

    public enum Visibility {
        FRESH(1000), SEEN(100), VISITED(10), HIDDEN(0);

        final int level;

        Visibility(int level) {
            this.level = level;
        }
    }
}
