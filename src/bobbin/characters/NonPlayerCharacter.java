package bobbin.characters;

import bobbin.boundaries.Room;
import bobbin.interaction.ExitToException;
import bobbin.interaction.console.Console;
import bobbin.items.BaseGameEntity;
import bobbin.items.Item;
import bobbin.situations.SituationRoot;

/**
 * Represents an NPC (Non GameCharacter GameCharacter)
 */
public class NonPlayerCharacter extends GameCharacter {

    public SituationRoot getConversation() {
        return conversation;
    }

    private final SituationRoot conversation;

    /**
     * Constructs a {@link NonPlayerCharacter}
     *
     * @param inventory The list of items in the NPC's possession
     * @param location  The room the NPC is in
     */
    public NonPlayerCharacter(
            String name, String description, Room location,
            SituationRoot conversation, Item... inventory) {
        super(name, description, location, inventory);
        location.addNPC(this);
        this.conversation = conversation;
    }

    public NonPlayerCharacter(String name, String description, Room location, Item... inventory) {
        this(name, description, location, new SituationRoot(), inventory);
    }

    @Override
    public int respondToInteraction(
            PlayerCharacter actor, BaseGameEntity from, Console console) throws ExitToException {
        return conversation.interact(actor, from, console);
    }
}
