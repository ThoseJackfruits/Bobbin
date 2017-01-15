package bobbin.characters;

import bobbin.boundaries.Room;
import bobbin.items.Item;
import bobbin.situations.SituationRoot;

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

}
