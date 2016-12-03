package text_engine.unit.effects;

import org.junit.Test;

import text_engine.characters.GameCharacter;
import text_engine.effects.GameCharacterEffect;
import text_engine.unit.BaseTest;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

public class TestGameCharacterEffect extends BaseTest {

    @Test
    public void testClearInventoryEffect() {
        assertNotEquals(0, gameCharacter.getInventory().size());
        GameCharacterEffect.CLEAR_INVENTORY.accept(gameCharacter);
        assertEquals(0, gameCharacter.getInventory().size());
    }

    @Test
    public void testNullEffect() throws CloneNotSupportedException {
        GameCharacter before = (GameCharacter) gameCharacter.clone();
        GameCharacterEffect.NULL.accept(gameCharacter);
        assertEquals(before, gameCharacter);
        assertEquals(before.getDescription(), gameCharacter.getDescription());
        assertEquals(before.getInventory(), gameCharacter.getInventory());
        assertEquals(before.getLocation(), gameCharacter.getLocation());
        assertEquals(before.getName(), gameCharacter.getName());
        assertEquals(before.hashCode(), gameCharacter.hashCode());
        assertEquals(before.isCombinable(), gameCharacter.isCombinable());
        assertEquals(before.isConsumable(), gameCharacter.isConsumable());
    }
}
