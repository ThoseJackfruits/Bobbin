package text_engine.effects;

import java.util.function.Consumer;

import text_engine.characters.GameCharacter;

/**
 * Represents an effect on the player. Affects anything about the player's status.
 */
public class CharacterEffect extends Effect<GameCharacter> {

    private CharacterEffect(String name, String description, String report,
                            Consumer<GameCharacter> consumer) {
        super(name, description, report, consumer);
    }

    @Override
    public void accept(GameCharacter gameCharacter) {
        this.consumer.accept(gameCharacter);
    }

    public static final CharacterEffect CLEAR_INVENTORY =
            new CharacterEffect(
                    "Wipes your inventory. Yikes!",
                    "Your inventory has been wiped",
                    "fuk of",
                    (player) -> player.getInventory().clear());
}
