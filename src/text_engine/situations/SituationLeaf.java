package text_engine.situations;

import text_engine.characters.GameCharacter;
import text_engine.effects.Effect;

public class SituationLeaf {
    private final SituationNode parent;
    private final Effect<GameCharacter> effect;
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
    public SituationLeaf(SituationNode parent, Effect<GameCharacter> effect, NextSituation next) {
        this.parent = parent;
        this.effect = effect;
        this.next = next;
    }

    protected SituationNode root() {
        return parent.root();
    }

}
