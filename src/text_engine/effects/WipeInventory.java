package text_engine.effects;

import text_engine.characters.PlayerCharacter;

/**
 * Represents an effect on the player. Affects anything about the player's status.
 */
public class WipeInventory extends Effect<PlayerCharacter> {

    @Override
    public void accept(Object o) {
        PlayerCharacter p = (PlayerCharacter) o;
        p.wipeInventory();
    }

    @Override
    public String getDescription() {
        return "Wipes your inventory. Yikes!";
    }

    @Override
    public String getReport() {
        return "Your inventory has been wiped";
    }

}
