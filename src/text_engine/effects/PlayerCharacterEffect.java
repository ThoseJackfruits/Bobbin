package text_engine.effects;

import java.util.function.Consumer;

import text_engine.characters.GameCharacter;
import text_engine.characters.PlayerCharacter;

public class PlayerCharacterEffect extends Effect<PlayerCharacter> {

    public PlayerCharacterEffect(String name, String description, String report,
                                 Consumer<PlayerCharacter> consumer) {
        super(name, description, report, consumer);
    }

    /**
     * Performs this operation on the given argument.
     *
     * @param gameCharacter the input argument
     * @throws IllegalArgumentException gameCharacter is not a {@link PlayerCharacter}.
     */
    @Override
    public void accept(GameCharacter gameCharacter) {
        if (!(gameCharacter instanceof PlayerCharacter)) {
            throw new IllegalArgumentException(
                    String.format("Cannot apply PlayerCharacterEffect to %s.",
                                  gameCharacter.getClass()));
        }
        this.consumer.accept((PlayerCharacter) gameCharacter);
    }
}
