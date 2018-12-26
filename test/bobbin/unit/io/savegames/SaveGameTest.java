package bobbin.unit.io.savegames;

import bobbin.characters.PlayerCharacter;
import bobbin.io.gamedata.SaveGameSerial;
import bobbin.unit.BaseUnitTest;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class SaveGameTest extends BaseUnitTest {

    @Test
    void testSaveGameSerial() throws Exception {
        SaveGameSerial saveGame = new SaveGameSerial("TestSaveSerial");
        saveGame.saveData(playerCharacter);

        PlayerCharacter loadedCharacter = saveGame.loadData();
        assertEquals(
                playerCharacter.getLocation().getName(),
                loadedCharacter.getLocation().getName());

        // Try some door/room traversal
        assertEquals(
                playerCharacter.getLocation().getDoors()[ 0 ]
                        .getOtherRoom(playerCharacter.getLocation())
                        .getName(),
                loadedCharacter.getLocation().getDoors()[ 0 ]
                        .getOtherRoom(loadedCharacter.getLocation())
                        .getName());
    }
}
