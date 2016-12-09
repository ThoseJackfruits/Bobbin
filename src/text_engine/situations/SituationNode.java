package text_engine.situations;

import text_engine.effects.Effect;
import text_engine.items.GameEntity;

import java.util.List;

public class SituationNode extends SituationLeaf {
    List<SituationNode> situationNodes;
    List<SituationLeaf> situationLeaves;

    public SituationNode(SituationNode parent, Effect<GameEntity> effect, NextSituation next) {
        super(parent, effect, next);
    }

    public SituationNode addChildLeafToExit(Effect<GameEntity> effect) {
        addChildLeaf(effect, NextSituation.EXIT);
        return this;
    }

    public SituationNode addChildLeafToParent(Effect<GameEntity> effect) {
        addChildLeaf(effect, NextSituation.PARENT);
        return this;
    }

    public SituationNode addChildLeafToRoot(Effect<GameEntity> effect) {
        addChildLeaf(effect, NextSituation.ROOT);
        return this;
    }

    public SituationNode addChildLeaf(Effect<GameEntity> effect, NextSituation next) {
        situationLeaves.add(new SituationLeaf(this, effect, next));
        return this;
    }

    public SituationNode addChildNodeToExit(Effect<GameEntity> effect) {
        addChildNode(effect, NextSituation.EXIT);
        return this;
    }

    public SituationNode addChildNodeToParent(Effect<GameEntity> effect) {
        addChildNode(effect, NextSituation.PARENT);
        return this;
    }

    public SituationNode addChildNodeToRoot(Effect<GameEntity> effect) {
        addChildNode(effect, NextSituation.ROOT);
        return this;
    }

    public SituationNode addChildNode(Effect<GameEntity> effect, NextSituation next) {
        situationNodes.add(new SituationNode(this, effect, next));
        return this;
    }
}
