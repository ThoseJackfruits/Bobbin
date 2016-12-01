package text_engine.effects;

import text_engine.items.GameEntity;

import java.util.function.Consumer;

/**
 * Represents something that changes the state of the game.
 */
public abstract class Effect<T> extends GameEntity implements Consumer<T> {
    private final String report;
    private final Consumer<T> consumer;

    public Effect(String name, String description, String report, Consumer<T> consumer)
    {
        super(name, description);
        this.report = report;

        this.consumer = consumer;
    }

    @Override
    public void accept(T t) {
        consumer.accept(t);
        System.out.println(report);
    }
}
