package text_engine.unit.io.savegames;

import org.junit.Test;

import text_engine.boundaries.Door;
import text_engine.boundaries.Room;
import text_engine.characters.GameCharacter;
import text_engine.io.savegames.SaveGameSerial;

import static org.junit.Assert.assertEquals;

public class SaveGameSerialTest {

    @Test
    public void saveGameTest() throws Exception {
        GameCharacter character1 = new GameCharacter("Mighty Whitey", "", new Room("Test Room"));
        Room nextRoom = new Room("Test Room 2");
        Door door = new Door(false, character1.getLocation(), nextRoom);

        SaveGameSerial saveGame = new SaveGameSerial("TestSave1", "TestSaveDir");
        saveGame.saveGameState(character1);
        GameCharacter character2 = saveGame.loadGameState();
        assertEquals(character1.getLocation().getName(), character2.getLocation().getName());
        assertEquals(
                character1.getLocation().getExits()[0].getOtherRoom(character1.getLocation()).getName(),
                character2.getLocation().getExits()[0].getOtherRoom(character2.getLocation()).getName());
    }
}
