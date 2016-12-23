package text_engine.unit.io.savegames;

import org.junit.Test;

import text_engine.characters.GameCharacter;
import text_engine.io.savegames.SaveGameSerial;
import text_engine.unit.BaseTest;

import static org.junit.Assert.assertEquals;

public class SaveGameSerialTest extends BaseTest {

    @Test
    public void saveGameState() throws Exception {
        SaveGameSerial saveGame = new SaveGameSerial("TestSave1", "TestSaveDir");
        saveGame.saveGameState(gameCharacter);
        GameCharacter loadedCharacter = saveGame.loadGameState();
        assertEquals(gameCharacter.getLocation().getName(), loadedCharacter.getLocation().getName());
        assertEquals(gameCharacter.getLocation().getDoors()[1]
                             .getOtherRoom(gameCharacter.getLocation())
                             .getName(),
                     loadedCharacter.getLocation().getDoors()[1]
                             .getOtherRoom(loadedCharacter.getLocation())
                             .getName());
    }
}
