package text_engine.io.savegames;

import org.junit.Test;

import text_engine.boundaries.Room;
import text_engine.characters.Player;

import static org.junit.Assert.assertEquals;

public class SaveGameSerialTest {

  @Test
  public void saveGameTest() throws Exception {
    Player p = new Player(new Room("Test Room"));
    SaveGameSerial saveGame = new SaveGameSerial("TestSave1", "TestSaveDir");
    saveGame.saveGameState(p);
    Player loadedPlayer = saveGame.loadGameState();
    assertEquals(p.getLocation().getName(), loadedPlayer.getLocation().getName());
  }
}
