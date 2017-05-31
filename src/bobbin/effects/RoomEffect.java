package bobbin.effects;

import java.util.Objects;

import bobbin.boundaries.Room;
import bobbin.characters.GameCharacter;

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
        this.consumer.accept(Objects.requireNonNull(gameCharacter).getLocation());
    }
}
