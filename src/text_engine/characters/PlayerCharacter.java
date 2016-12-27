package text_engine.characters;

import java.io.BufferedReader;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

import text_engine.boundaries.Room;
import text_engine.interaction.ConsoleActors;
import text_engine.interaction.ExitToException;
import text_engine.interaction.Interactive;
import text_engine.items.GameEntity;
import text_engine.items.Item;

public class PlayerCharacter extends GameCharacter {

    private static List<Function<PlayerCharacter, Interactive>> actions = new ArrayList<>();

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

        // Actions
        actions.add(GameCharacter::getLocation);
        actions.add(Interactive::exitGame);
    }

    /**
     * Constructs a {@link PlayerCharacter} with an empty inventory.
     *
     * @param location The room the player is in
     */
    public PlayerCharacter(String name, String description, Room location) {
        super(name, description, location);
    }


    @Override
    protected int respondToInteraction(GameEntity from, BufferedReader reader, PrintWriter writer,
                                       String prompt)
            throws ExitToException {
        Interactive choice = ConsoleActors.getChoice(reader, writer,
                                actions.stream().map(f -> f.apply(this))
                                       .collect(Collectors.toList()), prompt);
        return choice.interact(from, reader, writer);
    }
}
