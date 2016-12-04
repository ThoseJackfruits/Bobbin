package text_engine.effects;

import java.util.function.Consumer;

import text_engine.characters.GameCharacter;
import text_engine.items.BaseGameEntity;
import text_engine.items.GameEntity;

/**
 * Represents something that changes the state of the game.
 */
public abstract class Effect<T extends GameEntity> extends BaseGameEntity implements Consumer<GameCharacter> {

    protected final String report;
    protected final Consumer<T> consumer;

    public Effect(String name, String description, String report, Consumer<T> consumer) {
        super(name, description);
        this.report = report;

        this.consumer = consumer;
    }
}
