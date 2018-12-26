package bobbin.unit.items;

import bobbin.items.Key;
import bobbin.unit.BaseUnitTest;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertTrue;

class InventoryTest extends BaseUnitTest {

    @Test
    void testHasKeyThatMatches() {
        playerCharacter.getInventory().add(keyToDoor2);
        assertTrue(playerCharacter.getInventory().hasKeyThatMatches(
                key -> !door2Room2Room3Locked.unlock((Key) key)));
    }
}
