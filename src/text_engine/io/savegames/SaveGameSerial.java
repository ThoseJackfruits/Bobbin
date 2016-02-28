package text_engine.io.savegames;

import com.sun.istack.internal.NotNull;

import java.io.File;
import java.io.IOException;

import javax.swing.filechooser.FileSystemView;

import text_engine.characters.Player;

public class SaveGameSerial implements SaveGame<Player> {

  /**
   * Construct a new savegame in the given subdirectory of the main directory.
   *
   * @param dirName subdirectory of main directory in which to save gamestates.
   */
  public SaveGameSerial(@NotNull String dirName) throws IOException {
    File parent = FileSystemView.getFileSystemView().getDefaultDirectory();
    saveDir = new File(parent, dirName);

    if (!parent.exists()) { // documents directory could not be found
      throw new IOException(String.format("Could not open parent directory: %s", parent.toString()));
    }

    if (saveDir.exists()) {
      if (saveDir.isDirectory()) {
        if (!saveDir.canWrite() || !saveDir.canRead()) { // permissions issue
          throw new IOException("Could not either read from or write to save directory");
        }
      } else { // non-directory file exists at that location
        throw new IOException(String.format("File already exists with the name \"%s\"", dirName));
      }
    } else if (!parent.canWrite()) { // could not write to parent directory in order
      throw new IOException("Could not create save directory");
    }

    if (!saveDir.mkdir()) { // create save directory
      throw new IOException("Could not create save directory (mkdir failure)");
    }
  }

  private final File saveDir;

  /**
   * Checks save file location, creating the file if it doesn't exist already.
   *
   * @param needWriteAccess whether the function checking needs write access on the save file.
   */
  private void setup(boolean needWriteAccess, String file) throws IOException {
    // TODO figure out if this is necessary
  }

  @Override // TODO
  public Player loadGameState() throws IOException {
    return null;
  }

  @Override // TODO
  public void saveGameState(Player toSave) throws IOException {

  }
}
