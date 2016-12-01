package text_engine.effects;

import text_engine.characters.PlayerCharacter;

import java.util.function.Consumer;

import static sun.audio.AudioPlayer.player;

/**
 * Represents an effect on the player. Affects anything about the player's status.
 */
public class PlayerEffect extends Effect<PlayerCharacter> {

    private PlayerEffect(String name, String description, String report, Consumer<PlayerCharacter> consumer) {
        super(name, description, report, consumer);
    }

    public static final PlayerEffect CLEAR_INVENTORY =
            new PlayerEffect(
                    "Wipes your inventory. Yikes!",
                    "Your inventory has been wiped",
                    "fuk of",
                    (player) -> player.getInventory().clear());
}
