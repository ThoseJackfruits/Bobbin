package text_engine.characters;

import text_engine.boundaries.Room;
import text_engine.items.Item;

/**
 * Created by JackDavis on 2/12/16.
 *
 * Represents an NPC (Non Player Character)
 */
public class NonPlayerCharacter extends Player {

  /**
   * Constructs a text_engine.characters.NonPlayerCharacter object
   *
   * @param inventory The list of items in the text_engine.characters.NonPlayerCharacter's possession
   * @param location  The room the NPC is in
   */
  NonPlayerCharacter(Room location, Item... inventory) {
    super(location, inventory);
  }


}
