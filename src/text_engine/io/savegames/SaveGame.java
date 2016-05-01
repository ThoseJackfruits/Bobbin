package text_engine.io.savegames;

import java.util.Objects;

/**
 * Interface to be used by any class writing/reading savegames from a file or database.
 */
abstract class SaveGame<T> {

  public SaveGame(String name) {
    Objects.requireNonNull(name);
    this.name = name;
  }

  public String getName() {
    return name;
  }

  private String name;

  /**
   * Loads the saved game state of type {@link T}
   * @return loaded game state
   */
  abstract T loadGameState();

  abstract void saveGameState(T toSave);
}
