package text_engine.situations;

import text_engine.effects.Effect;
import text_engine.items.GameEntity;

public class SituationLeaf {
    private final SituationNode parent;
    private final Effect<GameEntity> effect;
    private final NextSituation next;

    /**
     * The next stop on the Situation train for {@link this} node.
     */
    public enum NextSituation {
        PARENT, ROOT, EXIT
    }

    /**
     * Create a new {@link SituationLeaf}.
     *
     * @param parent the parent of {@link this}
     * @param effect the effect that {@link this} should have after completion
     * @param next the next place to move to
     */
    public SituationLeaf(SituationNode parent, Effect<GameEntity> effect, NextSituation next) {
        this.parent = parent;
        this.effect = effect;
        this.next = next;
    }

    protected SituationNode root() {
        return parent.root();
    }

    void select(GameEntity gameEntity) {
        effect.accept(gameEntity);
        // will replace this with `interact` with relevant exceptions & console
        // interaction
    }
}
