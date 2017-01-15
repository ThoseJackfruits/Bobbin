package bobbin.effects;

import bobbin.characters.GameCharacter;
import bobbin.characters.PlayerCharacter;

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
