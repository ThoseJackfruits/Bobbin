package text_engine.io.gamedata;

import com.sun.istack.internal.NotNull;

import java.io.IOException;

import text_engine.characters.PlayerCharacter;

public class SaveGameSerial extends PersistedGameDataSerial<PlayerCharacter> {

    /**
     * Construct a new savegame in the given subdirectory of the main directory.
     *
     * @param filename filename of the file
     * @throws IOException          if the file cannot be written to, for a variety of reasons
     * @throws InterruptedException if the base folder could not be fetched from the OS
     */
    public SaveGameSerial(@NotNull String filename) throws IOException, InterruptedException {
        super(filename);
    }
}
