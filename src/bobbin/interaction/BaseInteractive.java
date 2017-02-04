package bobbin.interaction;

import java.io.BufferedReader;
import java.io.PrintWriter;
import java.util.Objects;

import bobbin.boundaries.Room;
import bobbin.characters.GameCharacter;
import bobbin.characters.PlayerCharacter;
import bobbin.constants.Actions;
import bobbin.interaction.actions.ActionList;
import bobbin.items.BaseGameEntity;
import bobbin.items.GameEntity;

public abstract class BaseInteractive implements Interactive {

    /**
     * Get the set of actions to present to the player when they {@link #interact(PlayerCharacter,
     * BaseGameEntity, BufferedReader, PrintWriter)} with {@link this}. Depending on needs, the base
     * list should be fetched by subclasses of {@link BaseInteractive}, which should then add to that
     * list.
     *
     * @param actor {@link GameCharacter} interacting with {@link this}
     * @param from  {@link GameEntity} that the {@link GameCharacter} came from
     * @return the base set of player actions
     */
    protected ActionList actions(GameCharacter actor, BaseGameEntity from) {
        ActionList actions = new ActionList();
        actions.add(Actions.BACK);
        return actions;
    }

    /**
     * Should be caught at the highest level possible (e.g. the main game loop) which should, after
     * catching it, call {@link #then#interact(PlayerCharacter, BaseGameEntity,
     * BufferedReader, PrintWriter)}.
     *
     * This will allow for the stack to be reset whenever moving from one {@link BaseInteractive} to
     * another is not necessarily a parent-child jump. For example, when moving between {@link Room}s,
     * one {@link Room} wouldn't be considered the parent or the child of the other.
     *
     * This is to allow for continuous use of Java's stack as a method of "tree" traversal and state
     * management without the risk of exceeding the recursion limit.
     */
    public class ResetStackException extends ExitToException {

        public final PlayerCharacter actor;
        public final BaseInteractive then;

        public ResetStackException(PlayerCharacter actor, BaseInteractive then) {
            this.actor = actor;
            this.then = then;
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

    public Visibility getVisibility() {
        return visibility;
    }

    public void setSeen() {
        setVisibilityDownTo(Visibility.SEEN);
    }

    public void setHidden() {
        setVisibilityDownTo(Visibility.HIDDEN);
    }

    public void setVisited() {
        setVisibilityDownTo(Visibility.VISITED);
    }

    /**
     * Brings the visibility of {@link this} down to the given {@link Visibility}. Will not change the
     * {@link Visibility} if the current value is already lower than {@code visibility}.
     *
     * @param visibility the new {@link Visibility}
     */
    private void setVisibilityDownTo(Visibility visibility) {
        if (this.visibility.level > visibility.level) {
            this.visibility = visibility;
        }
    }

    public int interact(PlayerCharacter actor, BaseGameEntity from,
                        BufferedReader reader,
                        PrintWriter writer) throws ExitToException {
        Objects.requireNonNull(actor);
        Objects.requireNonNull(reader);
        Objects.requireNonNull(writer);

        setVisibilityDownTo(Visibility.VISITED);

        int height;
        do {
            height = respondToInteraction(actor, from, reader, writer);
        } while (height == GoTo.THIS);

        if (height < 0) {
            throw new IllegalStateException(String.format("negative height (%d) found", height));
        }

        return height - 1;
    }

    public void resetStackAndInteract(PlayerCharacter actor) throws ResetStackException {
        throw new ResetStackException(actor, this);
    }

    // This is a holdover until we have a proper exit game confirmation dialogue.
    @Deprecated
    public static BaseGameEntity exitGame(GameCharacter gc) {
        System.exit(0);
        return null;  // Solely to allow exitGame() to fit in cleanly with other action lambdas.
    }
}
