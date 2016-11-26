package text_engine.characters;

import text_engine.boundaries.Room;
import text_engine.items.Item;

/**
 * Represents an NPC (Non Player Character)
 */
public class NonPlayerCharacter extends Player {

  /**
   * Constructs a text_engine.characterster object
   *
   * @param inventory The list of items in the text_engine.characterster's possession
   * @param location  The room the NPC is in
   */
  NonPlayerCharacter(Room location, Item... inventory) {
    super(location, inventory);
  }


}
