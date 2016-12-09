package text_engine.situations;

import text_engine.effects.Effect;
import text_engine.effects.GameEntityEffect;
import text_engine.items.GameEntity;

public class SituationRoot extends SituationNode {

    public SituationRoot(Effect<GameEntity> effect) {
        super(null, effect, NextSituation.EXIT);
    }

    public SituationRoot() {
        this(GameEntityEffect.NULL);
    }

    @Override
    public SituationNode root() {
        return this;
    }
}
