package text_engine.unit.io.savegames;

import org.junit.Test;

import text_engine.characters.PlayerCharacter;
import text_engine.io.gamedata.SaveGameSerial;
import text_engine.unit.BaseUnitTest;

import static org.junit.Assert.assertEquals;

public class SaveGameSerialTest extends BaseUnitTest {

    @Test
    public void saveGameState() throws Exception {
        SaveGameSerial saveGame = new SaveGameSerial("TestSave1");
        saveGame.saveData(playerCharacter);

        PlayerCharacter loadedCharacter = saveGame.loadData();
        assertEquals(playerCharacter.getLocation().getName(), loadedCharacter.getLocation().getName());

        // Try some door/room traversal
        assertEquals(playerCharacter.getLocation().getDoors()[0]
                             .getOtherRoom(playerCharacter.getLocation())
                             .getName(),
                     loadedCharacter.getLocation().getDoors()[0]
                             .getOtherRoom(loadedCharacter.getLocation())
                             .getName());
    }
}
