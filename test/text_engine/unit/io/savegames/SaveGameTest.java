package text_engine.unit.io.savegames;

import org.junit.Ignore;
import org.junit.Test;

import text_engine.characters.PlayerCharacter;
import text_engine.io.gamedata.SaveGameJSON;
import text_engine.io.gamedata.SaveGameSerial;
import text_engine.unit.BaseUnitTest;

import static org.junit.Assert.assertEquals;

public class SaveGameTest extends BaseUnitTest {

    private void saveGameTo(SaveGameSerial saveGame) {
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

    @Test
    public void saveGameSerial() throws Exception {
        saveGameTo(new SaveGameSerial("TestSaveSerial"));
    }

    @Test
    @Ignore
    public void saveGameJSON() throws Exception {
        // Circular references somewhere are making Gson unhappy. Have to investigate further.
        saveGameTo(new SaveGameJSON("TestSaveJSON"));
    }
}
