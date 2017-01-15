package bobbin.situations;

import bobbin.characters.GameCharacter;
import bobbin.effects.BaseEffect;
import bobbin.effects.GameCharacterEffect;

public class SituationRoot extends SituationNode {

    public SituationRoot(BaseEffect<GameCharacter> effect) {
        super(null, effect, NextSituation.EXIT);
    }

    public SituationRoot() {
        this(GameCharacterEffect.NULL);
    }

    @Override
    public SituationNode root() {
        return this;
    }
}
