package text_engine.effects;

import text_engine.characters.GameCharacter;
import text_engine.characters.PlayerCharacter;

public class PlayerCharacterEffect extends BaseEffect<PlayerCharacter> {

    public PlayerCharacterEffect(String name, String description, String report,
                                 Effect<PlayerCharacter> consumer) {
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
