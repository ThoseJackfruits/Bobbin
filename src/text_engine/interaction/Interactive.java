package text_engine.interaction;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;

import text_engine.characters.GameCharacter;
import text_engine.constants.Prompts;
import text_engine.items.GameEntity;

public abstract class Interactive {

    protected static final int THIS = 0;
    protected static final int PARENT = 1;
    protected static final int GRANDPARENT = 2;

    /**
     * Interact with the given object, implying that that object takes over the console and all
     * interaction with the player.
     *
     * The default implementation will work for both single-action pop-in-and-out {@link
     * #respondToInteraction(GameEntity, BufferedReader, PrintWriter, String)} implementations, which
     * would require that they return a {@code 1} or greater. A {@code 0} return value from that
     * method would imply that this should continue to call it until a non-zero return value is found
     *
     * @param from   {@link GameEntity} interacting with this object
     * @param reader to read response from
     * @param writer to print prompt to
     * @param prompt to show the player
     * @return the number of levels to go up from the current stage.
     * @throws IllegalStateException the height returned by {@link #respondToInteraction(GameEntity,
     *                               BufferedReader, PrintWriter, String)} is negative
     * @throws ExitToException       {@link #respondToInteraction(GameEntity, BufferedReader,
     *                               PrintWriter, String)} throws an {@link ExitToException}
     */
    public int interact(GameEntity from, BufferedReader reader, PrintWriter writer)
            throws ExitToException {

        int height;
        do {
            height = respondToInteraction(from, reader, writer, Prompts.PC_SELECT_ACTION);
        } while (height == 0);

        if (height < 0) {
            throw new IllegalStateException(String.format("negative height (%d) found", height));
        }

        return height - 1;
    }

    /**
     * Respond to interaction from another {@link GameEntity}.
     *
     * @param from   source of the interaction.
     * @param reader to read response from
     * @param writer to print prompt to
     * @return the number of levels to go up from the current stage.
     */
    protected abstract int respondToInteraction(
            GameEntity from, BufferedReader reader, PrintWriter writer, String prompt)
            throws ExitToException;


    // This is a holdover until we have a proper exit game confirmation dialogue.
    @Deprecated
    protected static Interactive exitGame(GameCharacter gc) {
        System.exit(0);
        return null;  // Solely to allow exitGame() to fit in cleanly with other action lambdas.
    }
}
