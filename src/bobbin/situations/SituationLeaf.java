package bobbin.situations;

import bobbin.characters.GameCharacter;
import bobbin.effects.BaseEffect;

public class SituationLeaf {
    private final SituationNode parent;
    private final BaseEffect<GameCharacter> effect;
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
    public SituationLeaf(SituationNode parent, BaseEffect<GameCharacter> effect, NextSituation next) {
        this.parent = parent;
        this.effect = effect;
        this.next = next;
    }

    protected SituationNode root() {
        return parent.root();
    }
    protected BaseEffect<GameCharacter> getEffect() { return effect; }
    protected NextSituation getNext() { return next; }

}
