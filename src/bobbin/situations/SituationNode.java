package bobbin.situations;

import bobbin.characters.GameCharacter;
import bobbin.effects.BaseEffect;
import bobbin.interaction.actions.Action;
import bobbin.items.BaseGameEntity;
import com.sun.istack.internal.NotNull;

import java.util.ArrayList;
import java.util.List;

public class SituationNode extends BaseGameEntity {
    private final List<SituationNode> childNodes = new ArrayList<>();
    private final BaseEffect<GameCharacter> effect;
    private final Action next;

    public SituationNode(@NotNull String name, @NotNull String description,
                         BaseEffect<GameCharacter> effect, Action next) {
        super(name, description);
        this.next = next;
        this.effect = effect;
    }

    public SituationNode addChildNode(@NotNull String name, @NotNull String description,
                                      BaseEffect<GameCharacter> effect, Action next) {
        childNodes.add(new SituationNode(name, description, effect, next));
        return this;
    }

    public SituationNode addChildNode(SituationNode node) {
        this.addChildNode(node.getName(), node.getDescription(), node.getEffect(), node.getNext());
        return this;
    }

    public boolean isLeaf() {
        return childNodes.isEmpty();
    }

    protected BaseEffect<GameCharacter> getEffect() {
        return effect;
    }

    protected Action getNext() {
        return next;
    }


}
