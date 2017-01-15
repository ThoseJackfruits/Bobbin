package bobbin.effects;

import java.util.function.Consumer;

import bobbin.characters.GameCharacter;
import bobbin.items.BaseGameEntity;
import bobbin.items.GameEntity;

/**
 * Represents something that changes the state of the game.
 *
 * Will always take in a {@link GameCharacter}, and each implementation of {@link BaseEffect} needs to get
 * the relevant subclass of {@link GameEntity} from the {@link GameCharacter}.
 */
public abstract class BaseEffect<T extends GameEntity> extends BaseGameEntity
        implements Consumer<GameCharacter> {

    protected final String report;
    protected final Effect<T> consumer;

    public BaseEffect(String name, String description, String report, Effect<T> consumer) {
        super(name, description);
        this.report = report;

        this.consumer = consumer;
    }
}
