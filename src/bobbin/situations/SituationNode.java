package bobbin.situations;

import bobbin.characters.GameCharacter;
import bobbin.characters.PlayerCharacter;
import bobbin.effects.BaseEffect;
import bobbin.interaction.ExitToException;
import bobbin.interaction.actions.Action;
import bobbin.interaction.actions.ActionList;
import bobbin.interaction.console.Console;
import bobbin.items.BaseGameEntity;

import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class SituationNode extends BaseGameEntity {
    private final List<SituationNode> childNodes = new ArrayList<>();
    private final BaseEffect<GameCharacter> effect;
    private final Action next;

    public SituationNode(
            @NotNull String prompt, @NotNull String response,
            BaseEffect<GameCharacter> effect, Action next) {
        super(prompt, response);
        this.next = Objects.requireNonNull(next);
        this.effect = Objects.requireNonNull(effect);
    }

    public SituationNode addChildNode(
            @NotNull String prompt, @NotNull String response,
            BaseEffect<GameCharacter> effect, Action next) {
        childNodes.add(new SituationNode(prompt, response, effect, next));
        return this;
    }

    public SituationNode addChildNode(SituationNode node) {
        this.addChildNode(node.getName(), node.getDescription(), node.getEffect(), node.getNext());
        return this;
    }

    public SituationNode addChildNodes(SituationNode... nodes) {
        for (SituationNode n : nodes) {
            addChildNode(n);
        }
        return this;
    }

    public boolean isLeaf() {
        return childNodes.isEmpty();
    }

    protected BaseEffect<GameCharacter> getEffect() {
        return effect;
    }

    /**
     * The {@link Action} that will immediately follow {@link this} {@link Action}.
     *
     * @return the {@link Action} to follow
     */
    protected Action getNext() {
        return next;
    }

    @Override
    protected ActionList actions(GameCharacter actor, BaseGameEntity from) {
        ActionList actions = super.actions(actor, from);
        assert actions.addAll(childNodes);
        return actions;
    }

    @Override
    public int respondToInteraction(
            PlayerCharacter actor, BaseGameEntity from, Console console) throws ExitToException {
        getEffect().accept(actor);

        if (getNext() != Action.NULL) {
            return getNext().apply(actor).interact(actor, this, console);
        }

        if (childNodes.size() == 0) {
            return GoTo.PARENT;
        }

        return super.respondToInteraction(actor, from, console);
    }
}
