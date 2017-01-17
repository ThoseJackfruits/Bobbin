package bobbin.characters;

import bobbin.boundaries.Room;
import bobbin.interaction.ExitToException;
import bobbin.items.BaseGameEntity;
import bobbin.items.Item;
import bobbin.situations.SituationRoot;

import java.io.BufferedReader;
import java.io.PrintWriter;

/**
 * Represents an NPC (Non GameCharacter GameCharacter)
 */
public class NonPlayerCharacter extends GameCharacter {

    private SituationRoot conversation;

    /**
     * Constructs a {@link NonPlayerCharacter}
     *
     * @param inventory The list of items in the NPC's possession
     * @param location  The room the NPC is in
     */
    NonPlayerCharacter(String name, String description, Room location, SituationRoot conversation, Item... inventory) {
        super(name, description, location, inventory);
        this.conversation = conversation;
    }

    NonPlayerCharacter(String name, String description, Room location, Item... inventory)
    {
        this(name, description, location, new SituationRoot(), inventory);
    }

    @Override
    protected int respondToInteraction(PlayerCharacter actor, BaseGameEntity from, BufferedReader reader, PrintWriter writer) throws ExitToException {
        return super.respondToInteraction(actor, from, reader, writer);
    }
}
