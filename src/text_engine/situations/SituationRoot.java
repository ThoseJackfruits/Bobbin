package text_engine.situations;

import text_engine.characters.GameCharacter;
import text_engine.effects.BaseEffect;
import text_engine.effects.GameCharacterEffect;

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
