package text_engine.effects;

import text_engine.items.GameEntity;

import java.util.function.Consumer;

public class GameEntityEffect extends Effect<GameEntity> {
    public GameEntityEffect(String name, String description, String report, Consumer<GameEntity> consumer) {
        super(name, description, report, consumer);
    }

    @Override
    public void accept(GameEntity gameEntity) {
        consumer.accept(gameEntity);
    }

    public static final GameEntityEffect NULL = new GameEntityEffect(
            "No effect.",
            "",
            "",
            (gameEntity) -> { });
}
