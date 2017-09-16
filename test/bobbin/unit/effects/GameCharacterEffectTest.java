package bobbin.unit.effects;

import org.junit.Assert;
import org.junit.Test;

import bobbin.characters.GameCharacter;
import bobbin.effects.GameCharacterEffect;
import bobbin.unit.BaseUnitTest;

public class GameCharacterEffectTest extends BaseUnitTest {

    // Static effect tests

    @Test
    public void testEffect_CLEAR_INVENTORY() {
        Assert.assertNotEquals(0, gameCharacter.getInventory().size());
        GameCharacterEffect.CLEAR_INVENTORY.accept(gameCharacter);
        Assert.assertEquals(0, gameCharacter.getInventory().size());
    }

    @Test
    public void testEffect_NULL() throws CloneNotSupportedException {
        GameCharacter before = (GameCharacter) gameCharacter.clone();
        GameCharacterEffect.NULL.accept(gameCharacter);
        Assert.assertEquals(before, gameCharacter);
        Assert.assertEquals(before.getDescription(), gameCharacter.getDescription());
        Assert.assertEquals(before.getInventory(), gameCharacter.getInventory());
        Assert.assertEquals(before.getLocation(), gameCharacter.getLocation());
        Assert.assertEquals(before.getName(), gameCharacter.getName());
        Assert.assertEquals(before.hashCode(), gameCharacter.hashCode());
        Assert.assertEquals(before.isCombinable(), gameCharacter.isCombinable());
        Assert.assertEquals(before.isConsumable(), gameCharacter.isConsumable());
    }
}
