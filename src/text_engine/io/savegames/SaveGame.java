package text_engine.io.savegames;

import java.io.IOException;

/**
 * Interface to be used by any class writing/reading savegames from a file or database.
 */
public interface SaveGame<T> {

  T loadGameState() throws IOException;

  void saveGameState(T toSave) throws IOException;
}
