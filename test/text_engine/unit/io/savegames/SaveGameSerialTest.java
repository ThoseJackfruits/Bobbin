package text_engine.unit.io.savegames;

import org.junit.Test;

import text_engine.boundaries.Room;
import text_engine.characters.Player;
import text_engine.io.savegames.SaveGameSerial;

import static org.junit.Assert.assertEquals;

public class SaveGameSerialTest {

    @Test
    public void saveGameTest() throws Exception {
        Player player1 = new Player(new Room("Test Room"));
        Room nextRoom = new Room("Test Room 2");
        player1.getLocation().addExit(false, nextRoom);

        SaveGameSerial saveGame = new SaveGameSerial("TestSave1", "TestSaveDir");
        saveGame.saveGameState(player1);
        Player player2 = saveGame.loadGameState();
        assertEquals(player1.getLocation().getName(), player2.getLocation().getName());
        assertEquals(
                player1.getLocation().getExits()[0].getOtherRoom(player1.getLocation()).getName(),
                player2.getLocation().getExits()[0].getOtherRoom(player2.getLocation()).getName());
    }
}
