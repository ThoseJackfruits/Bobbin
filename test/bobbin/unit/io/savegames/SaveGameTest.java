package bobbin.unit.io.savegames;

import org.junit.Assert;
import org.junit.Test;

import bobbin.characters.PlayerCharacter;
import bobbin.io.gamedata.SaveGameSerial;
import bobbin.unit.BaseUnitTest;

public class SaveGameTest extends BaseUnitTest {

    @Test
    public void testSaveGameSerial() throws Exception {
        SaveGameSerial saveGame = new SaveGameSerial("TestSaveSerial");
        saveGame.saveData(playerCharacter);

        PlayerCharacter loadedCharacter = saveGame.loadData();
        Assert.assertEquals(playerCharacter.getLocation().getName(), loadedCharacter.getLocation().getName());

        // Try some door/room traversal
        Assert.assertEquals(playerCharacter.getLocation().getDoors()[0]
                                    .getOtherRoom(playerCharacter.getLocation())
                                    .getName(),
                            loadedCharacter.getLocation().getDoors()[0]
                                    .getOtherRoom(loadedCharacter.getLocation())
                                    .getName());
    }
}
