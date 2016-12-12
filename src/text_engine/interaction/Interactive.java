package text_engine.interaction;

import java.io.InputStream;
import java.io.PrintStream;

import text_engine.items.GameEntity;

public abstract class Interactive {

    /**
     * Interact with the given object, implying that that object takes over the console and all
     * interaction with the player.
     *
     * All subclasses which don't require a single-action-then-return-to-parent workflow should
     * override this. For example, subclasses which require that a prompt is repeatedly shown to the
     * player, the following pattern would be common:
     *
     * <pre>
     * do {
     *     try {
     *         int height = respondToInteraction(from, in, out);
     *     }
     *     catch (RelevantExceptionToThisClass) {
     *         continue;
     *     }
     * } while (height == 0);
     *
     * return height - 1;
     * </pre>
     *
     * The minimum {@code 1} return value means that, by default, we will always be doing an action
     * and then moving back up to the parent. This will prevent the player getting stuck in traps and
     * will make it easier to implement the simpler use-cases without repetition.
     *
     * @param from {@link GameEntity} interacting with this object
     * @param in   {@link InputStream} to use for reading input from the user
     * @param out  {@link PrintStream} for writing output to the user
     * @return the number of levels to go up from the current stage.
     * @throws ExitToException {@link #respondToInteraction(GameEntity, InputStream, PrintStream)}
     *                       throws an {@link ExitToException}
     */
    public int interact(GameEntity from, InputStream in, PrintStream out) throws ExitToException {
        return Math.max(0, respondToInteraction(from, in, out) - 1);
    }

    /**
     * Respond to interaction from another {@link GameEntity}.
     *
     * @param from source of the interaction.
     * @param in   {@link InputStream} to use for reading input from the user
     * @param out  {@link PrintStream} for writing output to the user
     * @return the number of levels to go up from the current stage.
     */
    protected abstract int respondToInteraction(GameEntity from, InputStream in, PrintStream out)
            throws ExitToException;
}
