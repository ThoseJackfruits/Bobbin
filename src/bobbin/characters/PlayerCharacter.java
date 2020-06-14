package bobbin.characters;

import bobbin.boundaries.Room;
import bobbin.constants.Actions;
import bobbin.interaction.ExitToException;
import bobbin.interaction.actions.ActionList;
import bobbin.items.BaseGameEntity;
import bobbin.items.Item;

import java.io.BufferedReader;
import java.io.PrintWriter;

public class PlayerCharacter extends GameCharacter {

    public static class ExitToPlayerCharacterException extends ExitToException {

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

    @Override
    protected ActionList actions(GameCharacter actor, BaseGameEntity from) {
        ActionList actions = new ActionList();

        actions.add(Actions.MAIN_MENU);
        actions.add(Actions.LOOK_AROUND);
        actions.add(Actions.OPEN_INVENTORY);

        return actions;
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
    public int
    respondToInteraction(
            PlayerCharacter actor, BaseGameEntity from,
            BufferedReader reader, PrintWriter writer) throws ExitToException {
        return super.respondToInteraction(actor, from, reader, writer);
    }
}
