package text_engine.effects;

import java.util.function.Consumer;

import text_engine.characters.GameCharacter;

/**
 * Represents an effect on the player. Affects anything about the player's status.
 */
public class GameCharacterEffect extends Effect<GameCharacter> {

    private GameCharacterEffect(String name, String description, String report,
                                Consumer<GameCharacter> consumer) {
        super(name, description, report, consumer);
    }

    @Override
    public void accept(GameCharacter gameCharacter) {
        this.consumer.accept(gameCharacter);
    }

    public static final GameCharacterEffect NULL = new GameCharacterEffect(
            "No effect.",
            "",
            "",
            (gameCharacter) -> { });

    public static final GameCharacterEffect CLEAR_INVENTORY =
            new GameCharacterEffect(
                    "Clear Inventory",
                    "Clears your inventory. Yikes!",
                    "Your inventory has been cleared.",
                    (gameCharacter) -> gameCharacter.getInventory().clear());
}
