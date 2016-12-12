package text_engine.situations;

import text_engine.characters.GameCharacter;
import text_engine.effects.Effect;
import text_engine.items.GameEntity;

import java.util.List;

public class SituationNode extends SituationLeaf {
    List<SituationNode> situationNodes;
    List<SituationLeaf> situationLeaves;

    public SituationNode(SituationNode parent, Effect<GameCharacter> effect, NextSituation next) {
        super(parent, effect, next);
    }

    public SituationNode(Effect<GameCharacter> effect, NextSituation next)
    {
        this(null, effect, next);
    }

    public SituationNode addChildLeafToExit(Effect<GameCharacter> effect) {
        addChildLeaf(effect, NextSituation.EXIT);
        return this;
    }

    public SituationNode addChildLeafToParent(Effect<GameCharacter> effect) {
        addChildLeaf(effect, NextSituation.PARENT);
        return this;
    }

    public SituationNode addChildLeafToRoot(Effect<GameCharacter> effect) {
        addChildLeaf(effect, NextSituation.ROOT);
        return this;
    }

    public SituationNode addChildLeaf(Effect<GameCharacter> effect, NextSituation next) {
        situationLeaves.add(new SituationLeaf(this, effect, next));
        return this;
    }

    public SituationNode addChildNodeToExit(Effect<GameCharacter> effect) {
        addChildNode(effect, NextSituation.EXIT);
        return this;
    }

    public SituationNode addChildNodeToParent(Effect<GameCharacter> effect) {
        addChildNode(effect, NextSituation.PARENT);
        return this;
    }

    public SituationNode addChildNodeToRoot(Effect<GameCharacter> effect) {
        addChildNode(effect, NextSituation.ROOT);
        return this;
    }

    public SituationNode addChildNode(Effect<GameCharacter> effect, NextSituation next) {
        situationNodes.add(new SituationNode(this, effect, next));
        return this;
    }

    public SituationNode addChildNode(SituationNode node)
    {
        this.addChildNode(node.getEffect(), node.getNext());
        return this;
    }
}
