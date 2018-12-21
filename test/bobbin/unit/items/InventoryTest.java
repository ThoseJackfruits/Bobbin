package bobbin.unit.items;

import bobbin.items.Key;
import bobbin.unit.BaseUnitTest;
import org.junit.Assert;
import org.junit.Test;

public class InventoryTest extends BaseUnitTest {

    @Test
    public void testHasKeyThatMatches() throws Exception {
        playerCharacter.getInventory().add(keyToDoor2);
        Assert.assertTrue(playerCharacter.getInventory().hasKeyThatMatches(
                key -> !door2Room2Room3Locked.unlock((Key) key)));
    }
}
