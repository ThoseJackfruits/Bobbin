package bobbin.effects;

import bobbin.characters.GameCharacter;

/**
 * Represents an effect on the player. Affects anything about the player's status.
 */
public class GameCharacterEffect extends BaseEffect<GameCharacter> {

    private GameCharacterEffect(
            String name, String description, String report,
            Effect<GameCharacter> consumer) {
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
            (gameCharacter) -> {});

    public static final GameCharacterEffect CLEAR_INVENTORY =
            new GameCharacterEffect(
                    "Clear Inventory",
                    "Clears your inventory. Yikes!",
                    "Your inventory has been cleared.",
                    (gameCharacter) -> gameCharacter.getInventory().clear());

    public static GameCharacterEffect STEAL_INVENTORY(GameCharacter thief) {
        return new GameCharacterEffect(
                "Steal Inventory",
                "Everything you were carrying, stolen!",
                "Your inventory has been stolen!",
                (gameCharacter) -> {
                    thief.getInventory().addAll(gameCharacter.getInventory());
                    gameCharacter.getInventory().clear();
                });
    }
}
