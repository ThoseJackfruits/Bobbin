package text_engine.io.savegames;

import java.io.IOException;

import text_engine.characters.Player;

public class SaveGameSerial implements SaveGame<Player> {

  @Override
  public Player loadGameState() throws IOException {
    return null;
  }

  @Override
  public void saveGameState(Player toSave) throws IOException {

  }
}
