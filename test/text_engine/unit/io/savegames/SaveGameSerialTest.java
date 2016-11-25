package text_engine.unit.io.savegames;

import org.junit.Test;

import text_engine.boundaries.Room;
import text_engine.characters.Player;
import text_engine.io.savegames.SaveGameSerial;

import static org.junit.Assert.assertEquals;

public class SaveGameSerialTest {

  @Test
  public void saveGameTest() throws Exception {
    Player p = new Player(new Room("Test Room"));
    Room nextRoom = new Room("Test Room 2");
    p.getLocation().addExit(false, nextRoom);

    SaveGameSerial saveGame = new SaveGameSerial("TestSave1", "TestSaveDir");
    saveGame.saveGameState(p);
    Player loadedPlayer = saveGame.loadGameState();
    assertEquals(p.getLocation().getName(), loadedPlayer.getLocation().getName());
    assertEquals(p.getLocation().getExits()[0].getOtherRoom(p.getLocation()).getName(),
                 loadedPlayer.getLocation().getExits()[0].getOtherRoom(p.getLocation()).getName());
  }
}
