package bobbin.situations;

import bobbin.characters.GameCharacter;
import bobbin.effects.BaseEffect;

import java.util.List;

public class SituationNode extends SituationLeaf {
    List<SituationNode> situationNodes;
    List<SituationLeaf> situationLeaves;

    public SituationNode(SituationNode parent, BaseEffect<GameCharacter> effect, NextSituation next) {
        super(parent, effect, next);
    }

    public SituationNode(BaseEffect<GameCharacter> effect, NextSituation next)
    {
        this(null, effect, next);
    }

    public SituationNode addChildLeafToExit(BaseEffect<GameCharacter> effect) {
        addChildLeaf(effect, NextSituation.EXIT);
        return this;
    }

    public SituationNode addChildLeafToParent(BaseEffect<GameCharacter> effect) {
        addChildLeaf(effect, NextSituation.PARENT);
        return this;
    }

    public SituationNode addChildLeafToRoot(BaseEffect<GameCharacter> effect) {
        addChildLeaf(effect, NextSituation.ROOT);
        return this;
    }

    public SituationNode addChildLeaf(BaseEffect<GameCharacter> effect, NextSituation next) {
        situationLeaves.add(new SituationLeaf(this, effect, next));
        return this;
    }

    public SituationNode addChildNodeToExit(BaseEffect<GameCharacter> effect) {
        addChildNode(effect, NextSituation.EXIT);
        return this;
    }

    public SituationNode addChildNodeToParent(BaseEffect<GameCharacter> effect) {
        addChildNode(effect, NextSituation.PARENT);
        return this;
    }

    public SituationNode addChildNodeToRoot(BaseEffect<GameCharacter> effect) {
        addChildNode(effect, NextSituation.ROOT);
        return this;
    }

    public SituationNode addChildNode(BaseEffect<GameCharacter> effect, NextSituation next) {
        situationNodes.add(new SituationNode(this, effect, next));
        return this;
    }

    public SituationNode addChildNode(SituationNode node)
    {
        this.addChildNode(node.getEffect(), node.getNext());
        return this;
    }
}
