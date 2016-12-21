package text_engine.effects;

import text_engine.boundaries.Room;
import text_engine.characters.GameCharacter;

public class RoomEffect extends BaseEffect<Room> {

    public RoomEffect(String name, String description, String report,
                                 Effect<Room> consumer) {
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
