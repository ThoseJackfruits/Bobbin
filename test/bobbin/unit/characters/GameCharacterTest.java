package bobbin.unit.characters;

import bobbin.unit.BaseUnitTest;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class GameCharacterTest extends BaseUnitTest {

    @Test
    void testMoveTo() {
        gameCharacter.moveTo(room2);
        assertEquals(room2, gameCharacter.getLocation());
        gameCharacter.moveTo(room1);
        assertEquals(room1, gameCharacter.getLocation());
    }

    @Test
    void testMoveThroughDoor() {
        gameCharacter.moveThroughDoor(door1Room1Room2Unlocked);
        assertEquals(room1, gameCharacter.getLocation());
        gameCharacter.moveThroughDoor(door1Room1Room2Unlocked);
        assertEquals(room2, gameCharacter.getLocation());
    }
}
