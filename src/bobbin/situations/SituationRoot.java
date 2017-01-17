package bobbin.situations;

import bobbin.characters.GameCharacter;
import bobbin.effects.BaseEffect;
import bobbin.interaction.ExitToException;
import bobbin.interaction.actions.Action;

public class SituationRoot extends SituationNode {

    class ExitToSituationRootException extends ExitToException {
    }

    public SituationRoot(BaseEffect<GameCharacter> effect, Action action) {
        super("", "", effect, action);
    }

    public SituationRoot() {
        this(null, null);
    }
}
