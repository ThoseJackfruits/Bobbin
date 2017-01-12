package text_engine.io.gamedata;

import com.sun.istack.internal.NotNull;

import java.io.IOException;

import text_engine.characters.PlayerCharacter;

public class SaveGameSerial extends PersistedGameDataSerial<PlayerCharacter> {

    public SaveGameSerial(@NotNull String filename) throws IOException, InterruptedException {
        super(filename, "tngin");
    }

    public SaveGameSerial(@NotNull String filename, @NotNull String extension)
            throws IOException, InterruptedException {
        super(filename, extension);
    }
}
