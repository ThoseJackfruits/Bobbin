package bobbin.unit.situations;

import bobbin.characters.NonPlayerCharacter;
import bobbin.effects.GameCharacterEffect;
import bobbin.interaction.actions.Action;
import bobbin.situations.SituationNode;
import bobbin.situations.SituationRoot;
import bobbin.unit.BaseUnitTest;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

final class SituationRootTest extends BaseUnitTest {
    private final NonPlayerCharacter npc
            = new NonPlayerCharacter(
            "Generic NPC",
            "A generic NPC, initially in Room 2",
            room2);

    private final SituationRoot situationRoot = new SituationRoot(
            GameCharacterEffect.NULL,
            Action.NULL
    );

    private final SituationNode situationNode = new SituationNode(
            "Test prompt",
            "Test response",
            GameCharacterEffect.NULL,
            Action.NULL
    );

    @Test
    void addChildNodes_selfReturn() {
        SituationRoot same = situationRoot.addChildNodes();
        assertEquals(situationRoot, same);
    }

    @Test
    void addChildNodes() {
        assertTrue(situationRoot.isLeaf());
        situationRoot.addChildNodes(situationNode);
        assertFalse(situationRoot.isLeaf());
    }

    @Test
    void addChildNodes_emptyInput() {
        assertTrue(situationRoot.isLeaf());
        situationRoot.addChildNodes();
        assertTrue(situationRoot.isLeaf());
    }

    @Test
    void addChildNodes_Empty() {
        assertTrue(situationRoot.isLeaf());
        situationRoot.addChildNodes();
        assertTrue(situationRoot.isLeaf());
    }

    @Test
    void addChildNode_selfReturn() {
        SituationRoot same = situationRoot.addChildNode(
                "",
                "",
                GameCharacterEffect.NULL,
                Action.NULL
        );
        assertEquals(situationRoot, same);
    }

    @Test
    void addChildNode2_selfReturn() {
        SituationRoot same = situationRoot.addChildNode(situationNode);
        assertEquals(situationRoot, same);
    }
}
