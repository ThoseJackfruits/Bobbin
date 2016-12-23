package text_engine.characters;

import java.io.BufferedReader;
import java.io.PrintWriter;
import java.util.Objects;
import java.util.function.Function;

import text_engine.boundaries.Room;
import text_engine.interaction.ConsoleActors;
import text_engine.interaction.ExitToException;
import text_engine.interaction.Interactive;
import text_engine.items.GameEntity;
import text_engine.items.Item;

public class PlayerCharacter extends GameCharacter {

    public class ExitToPlayerCharacterException extends ExitToException {

    }

    /**
     * Constructs a {@link PlayerCharacter} in the given location, with the given inventory.
     *
     * @param location  The room the player is in
     * @param inventory The list of items in the {@link GameCharacter}'s inventory
     */
    public PlayerCharacter(String name, String description, Room location, Item... inventory) {
        super(name, description, location, inventory);
    }

    /**
     * Constructs a {@link PlayerCharacter} with an empty inventory.
     *
     * @param location The room the player is in
     */
    public PlayerCharacter(String name, String description, Room location) {
        super(name, description, location);
    }

    private enum Action {

        // This is a a trial run to see if this would be a good way to lay out
        // the options available to the player. It has its problems, but it's
        // also the cleanest thing I could come up with right now. There will
        // have to be refactoring to create Interactive objects for a few
        // different things in the game (the player's inventory being one), but
        // that was something that would have been necessary at some point, and
        // will be beneficial in keeping things clean.

        // Unlike previous things, like Items, there is an immediate necessity
        // to quickly and easily list the available actions for the player. I
        // don't see this being possible in anywhere near as clean of a way
        // without using an enum.

        // INVENTORY("Open inventory", GameCharacter::getInventory),
        ROOM("Look around", GameCharacter::getLocation),
        EXIT("Exit the game", Interactive::exitGame);

        private final String name;
        private final Function<GameCharacter, Interactive> nextStep;

        Action(String name, Function<GameCharacter, Interactive> nextStep) {
            this.name = Objects.requireNonNull(name);
            this.nextStep = Objects.requireNonNull(nextStep);
        }

        @Override
        public String toString() {
            return name;
        }
    }

    @Override
    protected int respondToInteraction(GameEntity from, BufferedReader reader, PrintWriter writer,
                                       String prompt)
            throws ExitToException {
        Action action = ConsoleActors.getChoice(
                reader, writer, Action.values(), prompt);
        return action.nextStep.apply(this).interact(from, reader, writer);
    }
}
