package bobbin.unit.io.savegames;

import org.junit.Test;

import bobbin.characters.PlayerCharacter;
import bobbin.io.gamedata.SaveGameSerial;
import bobbin.unit.BaseUnitTest;

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
}
