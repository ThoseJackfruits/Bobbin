package bobbin.io.gamedata;

import com.sun.istack.internal.NotNull;

import java.io.IOException;

import bobbin.characters.PlayerCharacter;
import bobbin.constants.Globals;
import bobbin.io.settings.Settings;

public class SaveGameSerial extends PersistedGameDataSerial<PlayerCharacter> {

    public SaveGameSerial(@NotNull String filename) throws IOException, InterruptedException {
        super(filename);
    }

    public SaveGameSerial(@NotNull String filename, @NotNull String extension)
            throws IOException, InterruptedException {
        super(filename, extension);
    }

    @Override
    public void saveData(PlayerCharacter data) {
        super.saveData(data);
        Globals.settings.put(Settings.Keys.CURRENT_SAVE, getName());
        try {
            Globals.settings.store();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static SaveGameSerial loadActiveSave() {
        try {
            return new SaveGameSerial((String) Globals.settings.get(Settings.Keys.CURRENT_SAVE));
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static boolean hasActiveSave() {
        String currentSave = (String) Globals.settings.get(Settings.Keys.CURRENT_SAVE);
        return currentSave != null && !currentSave.isEmpty();
    }
}
