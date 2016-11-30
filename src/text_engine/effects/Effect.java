package text_engine.effects;

import java.util.function.Consumer;

/**
 * Represents something that changes the state of the game.
 */
public abstract class Effect<T> implements Consumer {
    @Override
    public abstract void accept(Object t);

    public abstract String getDescription();

    public abstract String getReport();
}
