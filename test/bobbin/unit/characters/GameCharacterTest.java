package bobbin.unit.characters;

import org.junit.Assert;
import org.junit.Test;

import bobbin.unit.BaseUnitTest;

public class GameCharacterTest extends BaseUnitTest {

    @Test
    public void testMoveTo() {
        gameCharacter.moveTo(room2);
        Assert.assertEquals(room2, gameCharacter.getLocation());
        gameCharacter.moveTo(room1);
        Assert.assertEquals(room1, gameCharacter.getLocation());
    }

    @Test
    public void testMoveThroughDoor() {
        gameCharacter.moveThroughDoor(door1Room1Room2Unlocked);
        Assert.assertEquals(room1, gameCharacter.getLocation());
        gameCharacter.moveThroughDoor(door1Room1Room2Unlocked);
        Assert.assertEquals(room2, gameCharacter.getLocation());
    }
}
