package bobbin.situations;

import bobbin.characters.GameCharacter;
import bobbin.effects.BaseEffect;
import bobbin.effects.GameCharacterEffect;
import bobbin.interaction.ExitToException;
import bobbin.interaction.actions.Action;

import javax.validation.constraints.NotNull;

/**
 * The root of a Situation tree, which can include things like dialogue or action sequences.
 */
public class SituationRoot extends SituationNode {

    class ExitToSituationRootException extends ExitToException {
    }

    public SituationRoot(BaseEffect<GameCharacter> effect, Action action) {
        super("", "", effect, action);
    }

    public SituationRoot() {
        this(GameCharacterEffect.NULL, Action.NULL);
    }

    @Override
    public SituationRoot addChildNode(
            @NotNull String prompt, @NotNull String response,
            BaseEffect<GameCharacter> effect, Action next) {
        super.addChildNode(prompt, response, effect, next);
        return this;
    }

    @Override
    public SituationRoot addChildNode(SituationNode node) {
        super.addChildNode(node);
        return this;
    }

    @Override
    public SituationRoot addChildNodes(SituationNode... nodes) {
        super.addChildNodes(nodes);
        return this;
    }
}
