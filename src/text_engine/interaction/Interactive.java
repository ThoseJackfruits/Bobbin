package text_engine.interaction;

import java.io.BufferedReader;
import java.io.PrintWriter;
import java.util.Objects;

import text_engine.boundaries.Room;
import text_engine.characters.GameCharacter;
import text_engine.characters.PlayerCharacter;
import text_engine.constants.Actions;
import text_engine.interaction.actions.ActionList;
import text_engine.items.BaseGameEntity;
import text_engine.items.GameEntity;

public abstract class Interactive {

    /**
     * Get the set of actions to present to the player when they {@link #interact(PlayerCharacter,
     * BaseGameEntity, BufferedReader, PrintWriter)} with {@link this}. Depending on
     * needs, the base list should be fetched by subclasses of {@link Interactive}, which should then
     * add to that list.
     *
     * @param actor  {@link GameCharacter} interacting with {@link this}
     * @param from   {@link GameEntity} that the {@link GameCharacter} came from
     * @param reader to read response from
     * @param writer to print prompt to
     * @return the base set of player actions
     */
    protected ActionList actions(
            GameCharacter actor, BaseGameEntity from, BufferedReader reader,
            PrintWriter writer) {
        ActionList actions = new ActionList();
        actions.add(Actions.BACK(from));
        return actions;
    }

    /**
     * Should be caught at the highest level possible (e.g. the main game loop) which should, after
     * catching it, call {@link #then#interact(PlayerCharacter, BaseGameEntity,
     * BufferedReader, PrintWriter)}.
     *
     * This will allow for the stack to be reset whenever moving from one {@link Interactive} to
     * another is not necessarily a parent-child jump. For example, when moving between {@link Room}s,
     * one {@link Room} wouldn't be considered the parent or the child of the other.
     *
     * This is to allow for continuous use of Java's stack as a method of "tree" traversal and state
     * management without the risk of exceeding the recursion limit.
     */
    public class ResetStackException extends ExitToException {

        public final Interactive then;

        public ResetStackException(Interactive then) {
            this.then = then;
        }

    }

    public enum Visibility {
        FRESH(1000), SEEN(100), VISITED(10), HIDDEN(0);

        final int level;

        Visibility(int level) {
            this.level = level;
        }
    }

    /**
     * Class of shortcuts for return statements in {@link #interact(PlayerCharacter,
     * BaseGameEntity, BufferedReader, PrintWriter)}, {@link
     * #respondToInteraction(PlayerCharacter, BaseGameEntity, BufferedReader,
     * PrintWriter)}, and the like. Makes the semantics of the return statements in these methods more
     * reader-friendly.
     */
    protected class GoTo {

        public static final int THIS = 0;
        public static final int PARENT = 1;
        public static final int GRANDPARENT = 2;
    }

    private Visibility visibility = Visibility.FRESH;

    /**
     * Get the player-facing {@link Visibility} of {@link this}.
     *
     * @return player-facing {@link Visibility} of {@link this}.
     */

    public Visibility getVisibility() {
        return visibility;
    }

    /**
     * Confirm that the player has seen {@link this}.
     */
    public void setSeen() {
        setVisibilityDownTo(Visibility.SEEN);
    }

    /**
     * Confirm that the player has seen {@link this}.
     */
    public void setHidden() {
        setVisibilityDownTo(Visibility.HIDDEN);
    }

    /**
     * Confirm that the player has visited {@link this}.
     */
    public void setVisited() {
        setVisibilityDownTo(Visibility.VISITED);
    }

    /**
     * Brings the visibility of {@link this} down to the given {@link Visibility}. Will not change the
     * {@link Visibility} if the current value is already lower than {@code v}.
     *
     * @param v the new {@link Visibility}
     */
    private void setVisibilityDownTo(Visibility v) {
        if (visibility.level > v.level) {
            visibility = v;
        }
    }

    /**
     * Interact with the given object, implying that that object takes over the console and all
     * interaction with the player.
     *
     * The default implementation will work for both single-action pop-in-and-out {@link
     * #respondToInteraction(PlayerCharacter, BaseGameEntity, BufferedReader, PrintWriter)}
     * implementations, which would require that they return a {@code 1} or greater. A {@code 0}
     * return value from that method would imply that this should continue to call it until a non-zero
     * return value is found
     *
     * @param actor  {@link PlayerCharacter} interacting with {@link this}
     * @param from   {@link GameEntity} that the {@link GameCharacter} came from
     * @param reader to read response from
     * @param writer to print prompt to
     * @return the number of levels to go up from the current stage
     * @throws IllegalStateException the height returned by {@link #respondToInteraction(PlayerCharacter,
     *                               BaseGameEntity, BufferedReader, PrintWriter)} is negative
     * @throws ExitToException       {@link #respondToInteraction(PlayerCharacter, BaseGameEntity,
     *                               BufferedReader, PrintWriter)} throws an {@link ExitToException}
     */
    public int interact(PlayerCharacter actor, BaseGameEntity from,
                        BufferedReader reader,
                        PrintWriter writer) throws ExitToException {
        Objects.requireNonNull(actor);
        Objects.requireNonNull(reader);
        Objects.requireNonNull(writer);

        setVisibilityDownTo(Visibility.VISITED);

        int height;
        do {
            height = respondToInteraction(actor, from, reader, writer
                                         );
        } while (height == GoTo.THIS);

        if (height < 0) {
            throw new IllegalStateException(String.format("negative height (%d) found", height));
        }

        return height - 1;
    }

    public void resetStackAndInteract() throws ResetStackException {
        throw new ResetStackException(this);
    }

    /**
     * Respond to interaction from another {@link GameEntity}.
     *
     * @param from   source of the interaction.
     * @param reader to read response from
     * @param writer to print prompt to
     * @return the number of levels to go up from the current stage.
     */
    protected abstract int
    respondToInteraction(PlayerCharacter actor, BaseGameEntity from,
                         BufferedReader reader, PrintWriter writer) throws ExitToException;


    // This is a holdover until we have a proper exit game confirmation dialogue.
    @Deprecated
    public static BaseGameEntity exitGame(GameCharacter gc) {
        System.exit(0);
        return null;  // Solely to allow exitGame() to fit in cleanly with other action lambdas.
    }
}
