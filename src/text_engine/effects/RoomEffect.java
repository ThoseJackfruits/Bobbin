package text_engine.effects;

import java.util.function.Consumer;

import text_engine.boundaries.Room;
import text_engine.characters.GameCharacter;

public class RoomEffect extends Effect<Room> {

    public RoomEffect(String name, String description, String report,
                                 Consumer<Room> consumer) {
        super(name, description, report, consumer);
    }

    /**
     * Performs this operation on the given argument.
     *
     * @param gameCharacter the input argument
     * @throws IllegalArgumentException gameCharacter is not a {@link Room}.
     */
    @Override
    public void accept(GameCharacter gameCharacter) {
        this.consumer.accept(gameCharacter.getLocation());
    }
}
