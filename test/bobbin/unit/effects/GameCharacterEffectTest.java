package bobbin.unit.effects;

import bobbin.characters.GameCharacter;
import bobbin.effects.GameCharacterEffect;
import bobbin.unit.BaseUnitTest;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

class GameCharacterEffectTest extends BaseUnitTest {

    // Static effect tests

    @Test
    void testEffect_CLEAR_INVENTORY() {
        assertNotEquals(0, gameCharacter.getInventory().size());
        GameCharacterEffect.CLEAR_INVENTORY.accept(gameCharacter);
        assertEquals(0, gameCharacter.getInventory().size());
    }

    @Test
    void testEffect_NULL() throws CloneNotSupportedException {
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
