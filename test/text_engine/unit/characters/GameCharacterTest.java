package text_engine.unit.characters;

import org.junit.Test;

import text_engine.unit.BaseTest;

import static org.junit.Assert.assertEquals;

public class GameCharacterTest extends BaseTest {

    @Test
    public void testMoveTo() {
        gameCharacter.moveTo(room2);
        assertEquals(room2, gameCharacter.getLocation());
        gameCharacter.moveTo(room1);
        assertEquals(room1, gameCharacter.getLocation());
    }

    @Test
    public void testMoveThroughDoor() {
        gameCharacter.moveThroughDoor(door1Room1Room2Unlocked);
        assertEquals(room1, gameCharacter.getLocation());
        gameCharacter.moveThroughDoor(door1Room1Room2Unlocked);
        assertEquals(room2, gameCharacter.getLocation());
    }
}
