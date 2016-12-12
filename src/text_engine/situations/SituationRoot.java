package text_engine.situations;

import text_engine.characters.GameCharacter;
import text_engine.effects.Effect;
import text_engine.effects.GameCharacterEffect;
import text_engine.items.GameEntity;

public class SituationRoot extends SituationNode {

    public SituationRoot(Effect<GameCharacter> effect) {
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
