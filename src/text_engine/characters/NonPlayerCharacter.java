package text_engine.characters;

import text_engine.boundaries.Room;
import text_engine.items.Item;

/**
 * Represents an NPC (Non GameCharacter GameCharacter)
 */
public class NonPlayerCharacter extends GameCharacter {

    /**
     * Constructs a {@link NonPlayerCharacter}
     *
     * @param inventory The list of items in the NPC's possession
     * @param location  The room the NPC is in
     */
    NonPlayerCharacter(String name, String description, Room location, Item... inventory) {
        super(name, description, location, inventory);
    }


}
